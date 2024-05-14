<script setup lang="ts">
import axios from "axios";
import type { Recipe } from "@/typescript/types";
import RecipeEditingPage from "@/components/admin/RecipeEdit/RecipeEditingPage.vue";

definePageMeta({
  layout: "admin",
  middleware: "admin",
});

const config = useRuntimeConfig();
const route = useRoute();

const recipeData = ref<Recipe | null>(null);

try {
  const recipe = await axios.get(
    `${config.public.baseURL}/api/v1/recipe/get/${route.params.id}`
  );

  recipeData.value = recipe.data;
} catch (e) {
  console.log(e);
}
</script>

<template>
  <RecipeEditingPage :recipe-data="recipeData" />
</template>

<style scoped></style>
