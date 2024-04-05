<script setup lang="ts">
import axios from "axios";
import RecipeContainer from "@/components/admin/MyRecipes/RecipeContainer.vue";
import Pagination from "@/components/Pagination/Pagination.vue";

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
const key = ref(0);
const error = ref(false);

const pageNumber = ref(0);
const sortBy = ref("dateCreated");
const sortAsc = ref(false);

const loading = ref(true);

const totalPages = ref(0);
const siblings = 2;

const getData = async () => {
  try {
    await axios
      .get(
        `${config.public.baseURL}/api/v1/user/recipes?page=${pageNumber.value}&sortBy=${sortBy.value}&sortAsc=${sortAsc.value}`,
        {
          headers: { Authorization: `Bearer ${TastyBytes_user.value?.token}` },
        }
      )
      .then((res) => {
        recipes.value = res.data.elements;
        totalPages.value = res.data.totalPageCount;
        key.value++;
        loading.value = false;
      });
  } catch (e) {
    console.warn("Error fetching my recipes", e);
  }

  window.scrollTo(0, 0);
};

getData();
</script>

<template>
  <div>
    <h1 class="text-3xl font-bold text-center m-3">My Recipes</h1>
    <div v-if="loading">Loading...</div>
    <div
      v-else-if="recipes && recipes.length === 0"
      class="flex flex-col items-center gap-3"
    >
      <p class="text-center">
        Your recipe box is currently empty. Why not add a new recipe today?
      </p>
      <button
        @click="navigateTo('/user/dashboard/my-recipes/create')"
        class="p-1 px-4 text-lg rounded-sm text-black font-medium bg-chilean-heath-200 hover:bg-chilean-heath-300 transition-colors duration-200"
      >
        Create a new recipe
      </button>
    </div>
    <div v-else>
      <div class="flex flex-col gap-2">
        <RecipeContainer :key="key" @reload="getData()" :recipes />
        <div class="w-full text-center" v-if="totalPages > 0">
          <Pagination
            @change="getData"
            v-model="pageNumber"
            :totalPages
            :siblings
          />
        </div>
      </div>
    </div>
  </div>
</template>

<style scoped></style>
