import { expect, test } from "vitest";
import { renderSuspended, mountSuspended } from "@nuxt/test-utils/runtime";
import { screen, render, cleanup } from "@testing-library/vue";
import RecipeContainerComponent from "../components/RecipeContainerComponent/RecipeContainerComponent.vue";

export const mockNuxtImg = {
  template: "<img :src='src' />",
  props: ["src"],
};

export const mockNuxtLink = {
  template: "<a :href='to'><slot /></a>",
  props: {
    to: {
      type: String,
      required: true,
    },
  },
};

describe("Recipe container component", () => {
  afterEach(() => {
    cleanup();
  });

  test("should display all provided data", async () => {
    render(RecipeContainerComponent, {
      props: {
        imageLink:
          "https://images.food52.com/jgWjO2TMvaNaXzy9ME4PBYV5jyA=/1930x1286/filters:format(webp)/906a901f-6dad-452a-803e-3087c35e3de6--2013-0820_gena_veggie-burgers-062.jpg",
        name: "Spicy Black Bean and Corn Burgers",
        raiting: 4.5,
        about:
          "Vegetarian burgers packed with black beans, corn, and a touch of spice.",
        link: "/recipes/15",
        category: "Burgers",
        categoryLink: "/category/burgers",
        prepTime: 10,
      },
      global: {
        stubs: {
          NuxtImg: mockNuxtImg,
          NuxtLink: mockNuxtLink,
        },
      },
    });

    expect(screen.getByTestId("recipe-name").textContent).toBe(
      "Spicy Black Bean and Corn Burgers"
    );
    expect(screen.getByTestId("recipe-description").textContent).toBe(
      "Vegetarian burgers packed with black beans, corn, and a touch of spice."
    );
    expect(screen.getByTestId("recipe-category").textContent).toBe("Burgers");
    expect(screen.getByTestId("recipe-cooking-time").textContent).toBe(
      "10 min."
    );
    expect(screen.getByTestId("recipe-url").getAttribute("href")).toBe(
      "/recipes/15"
    );
    expect(screen.getByTestId("recipe-image").getAttribute("src")).toBe(
      "https://images.food52.com/jgWjO2TMvaNaXzy9ME4PBYV5jyA=/1930x1286/filters:format(webp)/906a901f-6dad-452a-803e-3087c35e3de6--2013-0820_gena_veggie-burgers-062.jpg"
    );
  });

  test("should validate prop types", async () => {
    expect(() =>
      render(RecipeContainerComponent, {
        imageLink: null,
        name: "Spicy Black Bean and Corn Burgers",
        raiting: 4.5,
        about:
          "Vegetarian burgers packed with black beans, corn, and a touch of spice.",
        link: "/recipes/15",
        category: "Burgers",
        categoryLink: "/category/burgers",
        prepTime: 10,
      })
    ).toThrowError(/input.*string/);
  });

  test("should display all provided data", async () => {
    render(RecipeContainerComponent, {
      props: {
        imageLink:
          "https://images.food52.com/jgWjO2TMvaNaXzy9ME4PBYV5jyA=/1930x1286/filters:format(webp)/906a901f-6dad-452a-803e-3087c35e3de6--2013-0820_gena_veggie-burgers-062.jpg",
        name: "Spicy Black Bean and Corn Burgers",
        raiting: 4.5,
        about:
          "Vegetarian burgers packed with black beans, corn, and a touch of spice.",
        link: "/recipes/15",
        category: "Burgers",
        categoryLink: "/category/burgers",
        prepTime: 10,
      },
      global: {
        stubs: {
          NuxtImg: mockNuxtImg,
          NuxtLink: mockNuxtLink,
        },
      },
    });

    expect(screen.getAllByTestId("recipe-name")).toBeDefined();
    expect(screen.getAllByTestId("recipe-description")).toBeDefined();
    expect(screen.getAllByTestId("recipe-category")).toBeDefined();
    expect(screen.getAllByTestId("recipe-cooking-time")).toBeDefined();
    expect(screen.getAllByTestId("recipe-url")).toBeDefined();
    expect(screen.getAllByTestId("recipe-image")).toBeDefined();
  });
});

// describe.each([{
//     imageLink:
//       "https://images.food52.com/jgWjO2TMvaNaXzy9ME4PBYV5jyA=/1930x1286/filters:format(webp)/906a901f-6dad-452a-803e-3087c35e3de6--2013-0820_gena_veggie-burgers-062.jpg",
//     name: "Spicy Black Bean and Corn Burgers",
//     raiting: 4.5,
//     about:
//       "Vegetarian burgers packed with black beans, corn, and a touch of spice.",
//     link: "/recipes/15",
//     category: "Burgers",
//     categoryLink: "/category/burgers",
//     prepTime: 10,
//   }])('describe', ({imageLink, name, raiting, about, link, category, categoryLink, prepTime }) => {
//     test("test", () => {
//         console.log(name)
//     })
//   })
