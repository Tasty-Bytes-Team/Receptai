import { expect, test } from "vitest";
import { render } from "@testing-library/vue";
import ProfilePicture from "@/components/Header/components/ProfilePicture.vue";

describe.each([
  {
    name: "Vardenis",
    expected: "VA",
  },
  {
    name: "Vardenis Pavardenis",
    expected: "VP",
  },
  {
    name: "vardenis pavardenis",
    expected: "VP",
  },
  {
    name: "Petras Vardenis Petrauskas",
    expected: "PV",
  },
  {
    name: "...",
    expected: "..",
  },
  {
    name: " ",
    expected: "",
  },
  {
    name: "    ",
    expected: "",
  },
])("Profile picture", ({ name, expected }) => {
  test(`should display abbreviation of ${name}`, () => {
    const { getByTestId } = render(ProfilePicture, {
      props: { user_name: name },
    });

    expect(getByTestId("profile-picture").textContent).toBe(expected);
  });
});
