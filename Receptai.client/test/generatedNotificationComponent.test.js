import { describe, it, expect } from "vitest";
import { mount } from "@vue/test-utils";
import YourComponent from "@/components/EmptyListInformation.vue"; // Adjust the import path to where your component is located

describe("Empty List information component test", () => {
  it("renders props.description when passed", () => {
    const description = "Test Description";
    const wrapper = mount(YourComponent, {
      props: { description, buttonText: "Click Me" },
    });
    expect(wrapper.text()).toContain(description);
  });

  it("emits button-click event when button is clicked", async () => {
    const wrapper = mount(YourComponent, {
      props: { description: "Test", buttonText: "Click Me" },
    });
    await wrapper.find("button").trigger("click");
    expect(wrapper.emitted()).toHaveProperty("button-click");
    expect(wrapper.emitted()["button-click"]).toHaveLength(1);
  });
});
