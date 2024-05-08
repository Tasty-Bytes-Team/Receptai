<script setup lang="ts">
import axios from "axios";
import RecipeContainer from "@/components/RecipeContainerComponent/RecipeContainerComponent.vue";
import Pagination from "@/components/Pagination/Pagination.vue";
import CategoryNameBanner from "@/components/CategoryPage/components/CategoryNameBanner.vue";
import EmptyListInformation from "@/components/EmptyListInformation.vue";
import RecipeContainerShimmer from "@/components/ShimmerLoaders/RecipeContainerShimmer.vue";

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
  averageRating: number;
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

const shimmerComponentsCount = 8;

const recipeList = ref<Recipe[] | null>(null);
const categoryInfo = ref<Category | null>(null);

const loading = ref(true);
const loadingTimeout = ref(false);

const pageNumber = ref(0);

const totalPages = ref(0);
const siblings = 2;

const getCategory = async () => {
  try {
    await axios
      .get(`${config.public.baseURL}/api/v1/category/${route.params.id}`)
      .then((res) => (categoryInfo.value = res.data));
  } catch (e) {
    console.warn("Error fetching recipes", e);
  }
};

const getRecipes = async () => {
  loading.value = true;
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
    console.warn("Error fetching recipes", e);
  } finally {
    window.scrollTo(0, 0);
    loading.value = false;
  }
};

if (process.env.NODE_ENV === "development") {
  loadingTimeout.value = true;
  setTimeout(() => {
    loadingTimeout.value = false;
  }, 300);
}

await getCategory();
getRecipes();
</script>

<template>
  <div>
    <CategoryNameBanner v-if="categoryInfo" :category-info="categoryInfo" />
    <div v-if="loading || loadingTimeout">
      <div class="flex flex-wrap">
        <RecipeContainerShimmer v-for="i in shimmerComponentsCount" />
      </div>
    </div>
    <div v-else-if="recipeList && recipeList.length === 0">
      <CategoryNameBanner v-if="categoryInfo" :category-info="categoryInfo" />
      <EmptyListInformation
        description="While there aren't any recipes here yet, we invite you to explore our
        full range of categories by clicking button below."
        button-text="All categories"
        @button-click="navigateTo('/recipe-category')"
      />
    </div>
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
