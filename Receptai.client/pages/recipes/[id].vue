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
        loading.value = false;
      });
  } catch (e) {
    console.error("Error fetching recipe", e);
  }
};

await getRecipe();
</script>

<template>
  <div v-if="!recipe || loading">Loading...</div>
  <div v-else>
    <RecipePage :recipe="recipe" />
    <Feedback :recipeId="route.params.id as string" />
  </div>
</template>

<style lang="scss" scoped></style>
