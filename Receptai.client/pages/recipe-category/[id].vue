<script setup lang="ts">
import axios from "axios";
import RecipeContainer from "@/components/RecipeContainerComponent/RecipeContainerComponent.vue";
import Pagination from "@/components/Pagination/Pagination.vue";
import CategoryNameBanner from "@/components/CategoryPage/components/CategoryNameBanner.vue";
import EmptyListInformation from "@/components/EmptyListInformation.vue";

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
    <CategoryNameBanner v-if="categoryInfo" :category-info="categoryInfo" />
    <div v-if="loading">Loading...</div>
    <EmptyListInformation
      v-else-if="recipeList && recipeList.length === 0"
      description="While there aren't any recipes here yet, we invite you to explore our
        full range of categories by clicking button below."
      button-text="All categories"
      @button-click="navigateTo('/recipe-category')"
    />
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
