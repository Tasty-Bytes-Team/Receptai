<script setup lang="ts">
import axios from "axios";
import type { Recipe } from "@/typescript/types";
import RecipeContainer from "@/components/RecipeContainerComponent/RecipeContainerComponent.vue";
import Pagination from "@/components/Pagination/Pagination.vue";
import RecipeContainerShimmer from "@/components/ShimmerLoaders/RecipeContainerShimmer.vue";
import RecipeSortAndFilter from "@/components/admin/components/RecipeSortAndFilter.vue";
import sortOptionSelector from "@/typescript/sortOptionSelector.ts";

const config = useRuntimeConfig();

const shimmerComponentsCount = 12;
const loading = ref(true);

const recipeList = ref<Recipe[] | null>(null);

const pageNumber = ref(0);
const totalElements = ref(0);
const elementsPerPage = ref(0);
const currentElementCount = ref(0);
const selectionValue = ref("DateDesc");

const sortBy = ref("dateCreated");
const sortAsc = ref(false);

const totalPages = ref(0);
const siblings = 2;

watch(selectionValue, async () => {
  const resultsArray = sortOptionSelector(selectionValue.value);
  if (resultsArray) {
    sortBy.value = resultsArray[0] as string;
    sortAsc.value = resultsArray[1] as boolean;

    await getRecipes();
  }
});

const getRecipes = async () => {
  loading.value = true;

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
      });
  } catch (e) {
    console.error("Error fetching recipes", e);
  } finally {
    window.scrollTo(0, 0);
    loading.value = false;
  }
};

getRecipes();
</script>

<template>
  <div>
    <div>
      <h1 class="text-3xl font-bold text-center m-3">Recipes</h1>
    </div>
    <RecipeSortAndFilter
      :pageNumber
      :elementsPerPage
      :currentElementCount
      :totalElements
      v-model="selectionValue"
    />
    <div v-if="loading">
      <div class="flex flex-wrap">
        <RecipeContainerShimmer v-for="i in shimmerComponentsCount" />
      </div>
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
      <div class="flex flex-wrap">
        <RecipeContainer
          v-for="item in recipeList"
          :imageLink="item.previewImage"
          :name="item.name"
          :rating="item.averageRating"
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
