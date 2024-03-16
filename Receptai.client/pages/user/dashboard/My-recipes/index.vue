<script setup lang="ts">
import axios from "axios";
import RecipeContainer from "@/components/admin/MyRecipes/RecipeContainer.vue";

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

definePageMeta({
  layout: "admin",
  middleware: "auth",
});

const config = useRuntimeConfig();

const TastyBytes_user = useCookie<UserCookie | null>("TastyBytes_user");

const recipes = ref<Recipe[] | null>(null);
const error = ref(false);

try {
  const response = await axios.get(
    `${config.public.baseURL}/api/v1/user/recipes`,
    {
      headers: { Authorization: `Bearer ${TastyBytes_user.value?.token}` },
    }
  );
  recipes.value = response.data;
} catch (e) {
  console.log(e);
}
</script>

<template>
  <h1 class="text-3xl font-bold text-center m-5">My Recipes</h1>
  <div class="flex flex-col gap-2">
    <RecipeContainer v-if="recipes?.length !== 0" :recipes="recipes" />
    <div v-else class="flex flex-col items-center gap-2">
      <p class="font-medium text-lg text-center">
        Your recipe box is currently empty. Why not add a new recipe today?
      </p>
      <NuxtLink to="/user/dashboard/my-recipes/create">
        <button
          class="text-base py-2 px-8 rounded-sm text-black font-medium bg-chilean-heath-200 hover:bg-chilean-heath-300 transition-colors duration-200"
        >
          Create a new recipe
        </button>
      </NuxtLink>
    </div>
  </div>
</template>

<style scoped></style>
