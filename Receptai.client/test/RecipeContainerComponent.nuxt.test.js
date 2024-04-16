import { expect, test } from "vitest";
import { render } from "@testing-library/vue";
import RecipeContainerComponent from "@/components/RecipeContainerComponent/RecipeContainerComponent.vue";

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
  test("should display all provided data", () => {
    const { getByTestId } = render(RecipeContainerComponent, {
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

    expect(getByTestId("recipe-name").textContent).toBe(
      "Spicy Black Bean and Corn Burgers"
    );
    expect(getByTestId("recipe-description").textContent).toBe(
      "Vegetarian burgers packed with black beans, corn, and a touch of spice."
    );
    expect(getByTestId("recipe-category").textContent).toBe("Burgers");
    expect(getByTestId("recipe-cooking-time").textContent).toBe("10 min.");
    expect(getByTestId("recipe-url").getAttribute("href")).toBe("/recipes/15");
    expect(getByTestId("recipe-image").getAttribute("src")).toBe(
      "https://images.food52.com/jgWjO2TMvaNaXzy9ME4PBYV5jyA=/1930x1286/filters:format(webp)/906a901f-6dad-452a-803e-3087c35e3de6--2013-0820_gena_veggie-burgers-062.jpg"
    );
  });

  test("should throw error when image link is null", () => {
    expect(() =>
      render(RecipeContainerComponent, {
        props: {
          imageLink: null,
          name: "Spicy Black Bean and Corn Burgers",
          rating: 4.5,
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
      }).toThrowError(/invalid prop/)
    );
  });

  test("should display all provided data", async () => {
    const { getByTestId } = render(RecipeContainerComponent, {
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

    expect(getByTestId("recipe-name")).toBeDefined();
    expect(getByTestId("recipe-description")).toBeDefined();
    expect(getByTestId("recipe-category")).toBeDefined();
    expect(getByTestId("recipe-cooking-time")).toBeDefined();
    expect(getByTestId("recipe-url")).toBeDefined();
    expect(getByTestId("recipe-image")).toBeDefined();
  });
});
