import { expect, test } from "vitest";
import { render, fireEvent } from "@testing-library/vue";
import profileMenu from "@/components/Header/components/ProfileMenu.vue";

describe("Profile menu component", () => {
  test("should display all provided data", () => {
    const { getByText } = render(profileMenu, {
      props: {
        user: {
          id: 1,
          name: "Vardenis",
          email: "email@email.com",
        },
        navigation: [
          {
            to: "/user/dashboard",
            title: "Dashboard",
          },
          {
            to: "/user/dashboard/my-recipes",
            title: "My recipes",
          },
          {
            to: "/user/dashboard/my-recipes/create",
            title: "Create new recipe",
          },
        ],
      },
    });

    expect(getByText("Vardenis")).toBeDefined();
    expect(getByText("email@email.com")).toBeDefined();
    expect(getByText("Dashboard")).toBeDefined();
    expect(getByText("My recipes")).toBeDefined();
    expect(getByText("Create new recipe")).toBeDefined();
  });

  test("should not display unprovided data", () => {
    const { queryByText } = render(profileMenu, {
      props: {
        user: {
          id: 1,
          name: "Vardenis",
          email: "email@email.com",
        },
        navigation: [
          {
            to: "/user/dashboard",
            title: "Dashboard",
          },
          {
            to: "/user/dashboard/my-recipes",
            title: "My recipes",
          },
          {
            to: "/user/dashboard/my-recipes/create",
            title: "Create new recipe",
          },
        ],
      },
    });

    expect(queryByText("Name")).not.toBe();
    expect(queryByText("Email")).not.toBe();
    expect(queryByText("Link")).not.toBe();
  });

  test("should emit events on text click", async () => {
    const navigation = [
      {
        to: "/user/dashboard",
        title: "Dashboard",
      },
      {
        to: "/user/dashboard/my-recipes",
        title: "My recipes",
      },
      {
        to: "/user/dashboard/my-recipes/create",
        title: "Create new recipe",
      },
    ];
    const { getByText, emitted } = render(profileMenu, {
      props: {
        user: {
          id: 1,
          name: "Vardenis",
          email: "email@email.com",
        },
        navigation: navigation,
      },
    });

    const manageAccount = getByText("Manage account");
    const login = getByText("Log out");

    await fireEvent.click(manageAccount);
    await fireEvent.click(login);

    navigation.forEach(async (nav) => {
      const clickableText = getByText(nav.title);
      await fireEvent.click(clickableText);
    });

    expect(emitted()).toHaveProperty("page-exit");
    expect(emitted()).toHaveProperty("logout");

    expect(emitted("page-exit").length).toBe(navigation.length + 1);
  });
});
