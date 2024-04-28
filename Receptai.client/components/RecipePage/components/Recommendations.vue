<script setup lang="ts">
import axios from "axios";
import RecipeContainer from "@/components/RecipeContainerComponent/RecipeContainerComponent.vue";
import RecipeContainerShimmer from "@/components/ShimmerLoaders/RecipeContainerShimmer.vue";

interface Recipe {
  id: number;
  name: string;
  shortDescription: string;
  author: Author;
  dateCreated: string;
  dateModified: string | null;
  previewImage: string;
  tutorialVideo: string | null;
  tutorialVideoEmbed: string | null;
  ingredients: Ingredients[];
  instructions: Instruction[];
  tags: Tag[];
  categories: Category[];
  minutesToPrepare: number;
  portions: number;
  averageRating: number;
}

interface Instruction {
  text: string;
}

interface Instruction {
  text: string;
}

interface Instruction {
  text: string;
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

interface Tag {
  id: number;
  iconName: string;
  name: string;
}

const config = useRuntimeConfig();

const shimmerComponentsCount = 4;

const sortBy = ref("dateCreated");
const sortAsc = ref(false);

const allRecipes = ref<Recipe[] | null>(null);
const loading = ref(true);

const getRecipes = async () => {
  loading.value = true;
  
  try {
    await axios
      .get(
        `${config.public.baseURL}/api/v1/recipe/list?&sortBy=${sortBy.value}&sortAsc=${sortAsc.value}`
      )
      .then((res) => {
        allRecipes.value = res.data.elements.slice(0, 4);
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
