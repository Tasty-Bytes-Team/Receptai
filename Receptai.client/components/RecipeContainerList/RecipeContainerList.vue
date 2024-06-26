<script setup lang="ts">
import axios from "axios";
import type { Recipe } from "@/typescript/types";
import RecipeContainer from "@/components/RecipeContainerComponent/RecipeContainerComponent.vue";
import RecipeContainerShimmer from "@/components/ShimmerLoaders/RecipeContainerShimmer.vue";

const config = useRuntimeConfig();

const shimmerComponentsCount = 8;

const recipeList = ref<Recipe[] | null>(null);
const loading = ref(true);

const pageNumber = ref(0);
const sortBy = ref("dateCreated");
const sortAsc = ref(false);

try {
  axios
    .get(
      `${config.public.baseURL}/api/v1/recipe/list?page=${pageNumber.value}&sortBy=${sortBy.value}&sortAsc=${sortAsc.value}`
    )
    .then((res) => {
      recipeList.value = res.data.elements.slice(0, 8);
      loading.value = false;
    });
} catch (e) {
  console.error("Error fetching recipe", e);
}
</script>

<template>
  <div v-if="loading" class="flex flex-wrap">
    <RecipeContainerShimmer v-for="i in shimmerComponentsCount" />
  </div>
  <div v-else class="flex flex-wrap">
    <RecipeContainer
      v-for="item in recipeList"
      :imageLink="item.previewImage"
      :name="item.name"
      :rating="item.averageRating"
      :about="item.shortDescription"
      :link="`/recipes/${item.id}`"
      :category="item.categories.length > 0 ? item.categories[0].name : 'Empty'"
      :categoryId="item.categories[0].id"
      :prepTime="item.minutesToPrepare"
    />
  </div>
</template>

<style lang="scss" scoped></style>
