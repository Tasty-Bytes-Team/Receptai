<script setup lang="ts">
import axios from "axios";
import RecipeContainer from "@/components/RecipeContainerComponent/RecipeContainerComponent.vue";
import Pagination from "@/components/Pagination/Pagination.vue";

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

const pageNumber = ref(0);
const sortBy = ref("dateCreated");
const sortAsc = ref(false);

const totalPages = ref(0);
const siblings = 2;

const getRecipes = async () => {
  try {
    await axios
      .get(
        `${config.public.baseURL}/api/v1/recipe/list?page=${pageNumber.value}&sortBy=${sortBy.value}&sortAsc=${sortAsc.value}`
      )
      .then((res) => {
        recipeList.value = res.data.elements;
        totalPages.value = res.data.totalPageCount;
        loading.value = false;
      });
  } catch (e) {
    console.error("Error fetching recipe", e);
  }

  window.scrollTo(0, 0);
};

getRecipes();
</script>

<template>
  <div>
    <h1 class="text-3xl font-bold text-center m-5">Receptai</h1>
  </div>
  <div>
    <div v-if="loading">Loading...</div>
    <div v-else class="flex flex-wrap">
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
        :categoryLink="
          item.categories.length > 0 ? item.categories[0].link : '#'
        "
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
</template>

<style lang="scss" scoped></style>
