<script setup lang="ts">
import axios from "axios";
import RecipePage from "@/components/RecipePage/RecipePage.vue";
import Feedback from "@/components/Feedback/Feedback.vue";

interface Recipe {
  id: number;
  name: string;
  shortDescription: string;
  author: Author;
  dateCreated: string;
  dateModified: string | null;
  previewImage: string;
  tutorialVideo: string | null;
  tutorialVideoEmbed: string | null;
  ingredients: Ingredients[];
  instructions: Instruction[];
  tags: Tag[];
  categories: Category[];
  minutesToPrepare: number;
  portions: number;
  averageRating: number;
}

interface Instruction {
  text: string;
}

interface Instruction {
    text: string;
}

interface Author {
  name: string;
}

interface Ingredients {
  purpose: string;
  ingredients: Ingredient[];
}

interface Ingredient {
  name: string;
  quantity: number;
  unit: string;
}

interface Category {
  id: number;
  name: string;
  description: string | null;
  previewImageUrl: string | null;
}

interface Tag {
  id: number;
  iconName: string;
  name: string;
}

definePageMeta({
  layout: "empty",
});

const config = useRuntimeConfig();
const route = useRoute();

const recipe = ref<Recipe | null>(null);
const loading = ref(true);

const getRecipe = async () => {
  try {
    await axios
      .get(`${config.public.baseURL}/api/v1/recipe/get/${route.params.id}`)
      .then((res) => {
        recipe.value = res.data;
      });
  } catch (e) {
    console.error("Error fetching recipe", e);
  } finally {
    loading.value = false;
  }
};

await getRecipe();
</script>

<template>
  <div v-if="loading">
    <div role="status" class="flex justify-center items-center my-2">
      <img
        src="/assets/loader.svg"
        alt="Recipe loader"
        class="w-9 h-9 animate-spin"
      />
      <span class="sr-only">Loading...</span>
    </div>
  </div>
  <div
    v-else-if="!recipe"
    class="flex flex-col justify-center items-center gap-3 my-10"
  >
    <p>
      Sorry, this recipe couldn't be found. Try viewing all the recipes by
      pressing the button bellow.
    </p>
    <button
      @click="navigateTo('/recipes')"
      class="bg-whiskey-200 hover:bg-whiskey-300 w-fit px-3 py-1.5 rounded-sm text-md font-medium transition-colors duration-100"
    >
      All recipes
    </button>
  </div>
  <div v-else>
    <RecipePage :recipe="recipe" />
    <Feedback :recipeId="route.params.id as string"></Feedback>
  </div>
</template>

<style lang="scss" scoped></style>
