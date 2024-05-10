<script setup lang="ts">
import axios from "axios";
import RecipePage from "@/components/RecipePage/RecipePage.vue";
import Feedback from "@/components/Feedback/Feedback.vue";
import Recommendations from "@/components/RecipePage/components/Recommendations.vue";
import EmptyListInformation from "@/components/EmptyListInformation.vue";

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
  loading.value = true;

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
  <EmptyListInformation
    v-else-if="!recipe"
    description="Sorry, this recipe couldn't be found. Try viewing all the recipes by
      pressing the button bellow."
    button-text="All recipes"
    @button-click="navigateTo('/recipes')"
  />
  <div v-else>
    <RecipePage :recipe="recipe" />
    <Feedback :recipeId="(route.params.id as string)" />
    <Recommendations :recipeId="(route.params.id as string)" />
  </div>
</template>

<style lang="scss" scoped></style>
