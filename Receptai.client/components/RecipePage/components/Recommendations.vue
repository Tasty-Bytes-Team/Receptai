<script setup lang="ts">
import axios from "axios";
import type { Recipe } from "@/typescript/types";
import RecipeContainer from "@/components/RecipeContainerComponent/RecipeContainerComponent.vue";
import RecipeContainerShimmer from "@/components/ShimmerLoaders/RecipeContainerShimmer.vue";

const config = useRuntimeConfig();

const shimmerComponentsCount = 4;

const props = defineProps<{
  recipeId: string
}>();

const allRecipes = ref<Recipe[] | null>(null);
const loading = ref(true);

const getRecipes = async () => {
  loading.value = true;
  
  try {
    await axios
      .get(
        `${config.public.baseURL}/api/v1/recipe/${props.recipeId}/recommended`
      )
      .then((res) => {
        allRecipes.value = res.data.elements.sort(() => 0.5 - Math.random()).slice(0, 4);
      });
  } catch (e) {
    console.error("Error fetching recipe", e);
  } finally {
    loading.value = false;
  }
};

getRecipes();
</script>

<template>
  <div class="max-w-screen-lg m-auto my-3 px-2">
    <h3 class="font-semibold text-2xl mb-2">You may also like</h3>
    <div v-if="loading" class="flex flex-wrap flex-row">
      <RecipeContainerShimmer
        class="lg:!basis-1/4 xsm:!basis-1/2 !basis-full"
        v-for="i in shimmerComponentsCount"
      />
    </div>
    <div v-else class="flex flex-wrap flex-row">
      <RecipeContainer
        class="lg:!basis-1/4 xsm:!basis-1/2 !basis-full"
        v-for="item in allRecipes"
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
    </div>
  </div>
</template>

<style scoped></style>
