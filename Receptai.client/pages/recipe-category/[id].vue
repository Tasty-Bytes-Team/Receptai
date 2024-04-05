<script setup lang="ts">
import axios from "axios";
import RecipeContainer from "@/components/RecipeContainerComponent/RecipeContainerComponent.vue";
import Pagination from "@/components/Pagination/Pagination.vue";

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
  id: number;
  name: string;
  description: string | null;
  previewImageUrl: string | null;
}

const config = useRuntimeConfig();
const route = useRoute();

const recipeList = ref<Recipe[] | null>(null);
const categoryInfo = ref<Category | null>(null);

const loading = ref(true);

const pageNumber = ref(0);

const totalPages = ref(0);
const siblings = 2;

const getCategory = async () => {
  try {
    await axios
      .get(`${config.public.baseURL}/api/v1/category/${route.params.id}`)
      .then((res) => {
        categoryInfo.value = res.data;
      });
  } catch (e) {
    console.error("Error fetching recipes", e);
  }
};

const getRecipes = async () => {
  try {
    await axios
      .get(
        `${config.public.baseURL}/api/v1/category/${route.params.id}/recipes`
      )
      .then((res) => {
        recipeList.value = res.data.elements;
        totalPages.value = res.data.totalPageCount;
        loading.value = false;
      });
  } catch (e) {
    console.error("Error fetching recipes", e);
  }

  window.scrollTo(0, 0);
};

await getCategory();
getRecipes();
</script>

<template>
  <div>
    <div
      v-if="categoryInfo"
      class="bg-gray-800 text-white rounded-md m-6 bg-cover bg-center"
      v-bind:style="
        categoryInfo.previewImageUrl
          ? {
              'background-image': 'url(' + categoryInfo.previewImageUrl + ')',
            }
          : null
      "
    >
      <div
        class="h-full w-full py-10"
        :class="
          categoryInfo.previewImageUrl
            ? 'bg-[#00000070] h-full w-full py-10 rounded-md'
            : null
        "
      >
        <h1 class="text-2xl font-bold text-center">{{ categoryInfo.name }}</h1>
        <p v-if="categoryInfo.description" class="text-center">
          {{ categoryInfo.description }}
        </p>
      </div>
    </div>

    <div v-if="loading">Loading...</div>
    <div
      v-else-if="recipeList && recipeList.length === 0"
      class="flex flex-col items-center gap-3"
    >
      <p>
        While there aren't any recipes here yet, explore our full range of
        categories below to find something delicious.
      </p>
      <button
        @click="navigateTo('/recipe-category')"
        class="p-1 px-4 text-lg rounded-sm text-black font-medium bg-chilean-heath-200 hover:bg-chilean-heath-300 transition-colors duration-200"
      >
        All categories
      </button>
    </div>
    <div v-else>
      <div class="flex flex-wrap">
        <RecipeContainer
          v-for="item in recipeList"
          :imageLink="item.previewImage"
          :name="item.name"
          :raiting="5"
          :about="item.shortDescription"
          :link="`/recipes/${item.id}`"
          :category="
            item.categories.length > 0 ? item.categories[0].name : 'Empty'
          "
          :categoryId="item.categories[0].id"
          :prepTime="item.minutesToPrepare"
        />
        <div class="w-full text-center">
          <Pagination
            @change="getRecipes"
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
