// Consolidated Interface for Recipe
interface Recipe {
  id: number;
  name: string;
  shortDescription: string;
  previewImage: string;
  tutorialVideo?: string; // Optional tutorial video (null if not provided)
  ingredients: Ingredients[]; // Array of ingredients (defined below)
  instructions: string[];
  tags: string[];
  categories: Category[];
  minutesToPrepare: number;
  portions: number;
}

// Interface for Ingredients Group
interface Ingredients {
  purpose: string;
  ingredients: Ingredient[]; // Array of basic ingredients (defined below)
}

// Interface for Basic Ingredient
interface Ingredient {
  name: string;
  quantity: number;
  unit: string;
}

interface Category {
  name: string;
  link: string;
}

export const useMyFetch = (request: string, opts?: any) => {
  const config = useRuntimeConfig();

  return useFetch<Recipe[] | null>(request, {
    baseURL: config.public.baseURL,
    server: false,
    ...opts,
  });
};
