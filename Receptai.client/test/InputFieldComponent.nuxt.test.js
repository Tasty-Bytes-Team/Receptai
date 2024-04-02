import { expect, test } from "vitest";
import {
  screen,
  render,
  cleanup,
  fireEvent,
  getByRole,
} from "@testing-library/vue";
import InputField from "@/components/admin/components/InputField.vue";

describe("Input field", () => {
  test("should display all prop values with text input", async () => {
    const { getByText, getByPlaceholderText } = render(InputField, {
      props: {
        name: "test_text",
        label: "Input text",
        placeholder: "Please input",
      },
    });

    const input = getByPlaceholderText("Please input");

    expect(getByText("Input text")).toBeDefined();
    expect(input.placeholder).toBe("Please input");
    expect(input.type).toBe("text");
    expect(input.type).not.toBe("number");
    expect(input.name).toBe("test_text");
  });

  test("should display all prop values with number input", async () => {
    const { getByText, getByPlaceholderText } = render(InputField, {
      props: {
        name: "test_number",
        label: "Input number",
        placeholder: "Please input number",
        type: "number",
      },
    });

    const input = getByPlaceholderText("Please input number");

    expect(getByText("Input number")).toBeDefined();
    expect(input.placeholder).toBe("Please input number");
    expect(input.type).toBe("number");
    expect(input.type).not.toBe("text");
    expect(input.min).toBe("1");
    expect(input.name).toBe("test_number");
  });

  test("should be able to accept text as input", async () => {
    const { getByPlaceholderText } = render(InputField, {
      props: {
        name: "test_text2",
        label: "Test Input text",
        placeholder: "Please test input",
      },
    });

    const input = getByPlaceholderText("Please test input");
    await fireEvent.update(input, "Test");
    expect(input.value).toBe("Test");
  });

  test("should be able to accept number as input", async () => {
    const { getByPlaceholderText } = render(InputField, {
      props: {
        name: "test_number2",
        label: "Test Input number",
        placeholder: "Please test number input",
        type: "number",
      },
    });

    const input = getByPlaceholderText("Please test number input");
    await fireEvent.update(input, 123);
    expect(input.value).toBe("123");
  });

  test("should not accept text as number input", async () => {
    const { getByPlaceholderText } = render(InputField, {
      props: {
        name: "test_number2",
        label: "Test Input number",
        placeholder: "Please test number input",
        type: "number",
      },
    });

    const input = getByPlaceholderText("Please test number input");
    await fireEvent.update(input, "asdasd");
    expect(input.value).toBe("");

    await fireEvent.update(input, 111);
    expect(input.value).toBe("111");
  });
});
