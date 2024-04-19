<script setup lang="ts">
import axios from "axios";
import RecipeContainer from "@/components/RecipeContainerComponent/RecipeContainerComponent.vue";
import Pagination from "@/components/Pagination/Pagination.vue";
import EmptyListInformation from "@/components/EmptyListInformation.vue";
import RecipeContainerShimmer from "@/components/ShimmerLoaders/RecipeContainerShimmer.vue";
import RecipeSortAndFilter from "@/components/admin/components/RecipeSortAndFilter.vue";

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

const shimmerComponentsCount = 8;

const recipeList = ref<Recipe[] | null>(null);
const loading = ref(true);

const pageNumber = ref(0);
const totalElements = ref(0);
const elementsPerPage = ref(0);
const currentElementCount = ref(0);
const selectionValue = ref("DateDesc");

watch(selectionValue, () => {
  switch (selectionValue.value) {
    case "nameDesc":
      sortBy.value = "dateCreated";
      sortAsc.value = false;
      getRecipes();
      break;
    case "nameAsc":
      sortBy.value = "dateCreated";
      sortAsc.value = true;
      getRecipes();
      break;
    case "DateDesc":
      sortBy.value = "name";
      sortAsc.value = false;
      getRecipes();
      break;
    case "DateAsc":
      sortBy.value = "name";
      sortAsc.value = true;
      getRecipes();
      break;
  }
});

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
        totalElements.value = res.data.totalElementCount;
        elementsPerPage.value = res.data.elementsPerPage;
        currentElementCount.value = res.data.currentElementCount;
        loading.value = false;
      });
  } catch (e) {
    console.error("Error fetching recipes", e);
  }

  window.scrollTo(0, 0);
};

getRecipes();
</script>

<template>
  <div>
    <div>
      <h1 class="text-3xl font-bold text-center m-3">Recipes</h1>
    </div>
    <div v-if="loading" class="flex flex-wrap">
      <RecipeContainerShimmer v-for="i in shimmerComponentsCount" />
    </div>
    <EmptyListInformation
      v-else-if="recipeList && recipeList.length === 0"
      description="This page is dedicated to housing all the delicious recipes you can find
        on my website! While there aren't any recipes listed here just yet, stay
        tuned! I'm constantly adding new culinary creations, and soon this will
        be your one-stop shop for finding tasty dishes to whip up in the
        kitchen."
      button-text="Home page"
      @button-click="navigateTo('/')"
    />
    <div v-else>
      <RecipeSortAndFilter :pageNumber :elementsPerPage :currentElementCount :totalElements v-model="selectionValue"  />
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
        <div class="w-full text-center" v-if="totalPages > 0">
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

<style lang="scss" scoped></style>
