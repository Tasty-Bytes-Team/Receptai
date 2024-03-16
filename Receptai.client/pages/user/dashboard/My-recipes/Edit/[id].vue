<script setup lang="ts">
import axios from "axios";

import RecipeEditingPage from "@/components/admin/RecipeEdit/RecipeEditingPage.vue";

interface GetRecipe {
  id: number;
  name: string;
  author: { id: number; name: string };
  shortDescription: string;
  previewImage: string;
  tutorialVideo: string;
  ingredients: Ingredients[];
  instructions: Instructions[];
  minutesToPrepare: number | null;
  portions: number | null;
  categories: Category[];
  tags: Tag[];
}

interface Ingredients {
  purpose: string;
  ingredients: Ingredient[];
}

interface Ingredient {
  name: string;
  quantity: number | null;
  unit: string;
}

interface Category {
  id: number;
  name: string;
  primary: boolean;
}

interface Tag {
  id: number;
  name: string;
  iconName: string;
}

interface Instructions {
  text: string;
}

interface UserCookie {
  token: string;
  expiresIn: number;
  user: User;
}

interface User {
  id: number;
  name: string;
  email: string;
}

definePageMeta({
  layout: "admin",
  middleware: "auth",
});

const config = useRuntimeConfig();
const route = useRoute();

const recipeData = ref<GetRecipe | null>(null);

try {
  const recipe = await axios.get(
    `${config.public.baseURL}/api/v1/recipe/get/${route.params.id}`
  );
  recipeData.value = recipe.data;
} catch (e) {
  console.log(e);
}

const TastyBytes_user = useCookie<UserCookie | null>("TastyBytes_user");
</script>

<template>
  <RecipeEditingPage
    :recipe-data="recipeData"
    v-if="TastyBytes_user?.user.id === recipeData?.author.id"
  />
  <div v-else class="flex flex-col items-center gap-2">
    <p class="font-medium text-lg text-center">
      Sorry, this is not your recipe. You can only edit your own recipes.
    </p>
    <button
      @click="navigateTo('/user/dashboard/my-recipes')"
      class="text-base py-2 px-8 rounded-sm text-black font-medium bg-chilean-heath-200 hover:bg-chilean-heath-300 transition-colors duration-200"
    >
      My Recipes
    </button>
  </div>
</template>

<style scoped></style>
