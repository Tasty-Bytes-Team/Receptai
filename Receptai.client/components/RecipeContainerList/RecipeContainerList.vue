<script setup lang="ts">
import axios from "axios";
import RecipeContainer from "@/components/RecipeContainerComponent/RecipeContainerComponent.vue";

const config = useRuntimeConfig();

interface Recipe {
  id: number;
  name: string;
  shortDescription: string;
  author: Author;
  dateCreated: string;
  dateModified: string | null;
  previewImage: string;
  tutorialVideo?: string;
  ingredients: Ingredients[];
  instructions: string[];
  tags: string[];
  categories: Category[];
  minutesToPrepare: number;
  portions: number;
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
  name: string;
  link: string;
}

const recipeList = ref<Recipe[] | null>(null);
const loading = ref(true);

try {
  axios.get(`${config.public.baseURL}/api/v1/recipe/list`).then((res) => {
    recipeList.value = res.data;
    loading.value = false;
  });
} catch (e) {
  console.error("Error fetching recipe", e);
}
</script>

<template>
  <div v-if="loading">Loading...</div>
  <div v-else class="flex flex-wrap">
    <RecipeContainer
      v-for="item in recipeList"
      :imageLink="item.previewImage"
      :name="item.name"
      :raiting="5"
      :about="item.shortDescription"
      :link="`/recipes/${item.id}`"
      :category="item.categories.length > 0 ? item.categories[0].name : 'Empty'"
      :categoryLink="item.categories.length > 0 ? item.categories[0].link : '#'"
      :prepTime="item.minutesToPrepare"
    />
  </div>
</template>

<style lang="scss" scoped></style>
