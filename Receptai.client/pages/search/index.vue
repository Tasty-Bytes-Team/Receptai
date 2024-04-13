<script setup lang="ts">
import axios from "axios";
import RecipeContainer from "@/components/RecipeContainerComponent/RecipeContainerComponent.vue";
import Pagination from "@/components/Pagination/Pagination.vue";
import EmptyListInformation from "@/components/EmptyListInformation.vue";
import RecipeContainerShimmer from "@/components/ShimmerLoaders/RecipeContainerShimmer.vue";
import SearchForm from "@/components/search/SearchForm.vue";

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

const route = useRoute();
const config = useRuntimeConfig();

const shimmerComponentsCount = 8;

const recipeList = ref<Recipe[] | null>(null);

const loading = ref(true);

const pageNumber = ref(0);
const sortBy = ref("dateCreated");
const sortAsc = ref(false);

const totalPages = ref(0);
const totalElementCount = ref(0);
const siblings = 2;

const search = ref<string | undefined>(undefined);

search.value = route.query.s?.toString().trim();

const getRecipes = async () => {
  try {
    const res = await axios.get(
      `${config.public.baseURL}/api/v1/recipe/find/${search.value}?page=${pageNumber.value}&sortBy=${sortBy.value}&sortAsc=${sortAsc.value}`
    );
    recipeList.value = res.data.elements;
    totalPages.value = res.data.totalPageCount;
    totalElementCount.value = res.data.totalElementCount;
    loading.value = false;
  } catch (e) {
    console.warn("Error fetching recipes", e);
  }
  window.scrollTo(0, 0);
};

watch(() => route.query, () => {
  search.value = route.query.s?.toString().trim();

  if (search.value === undefined || search.value === ''){
  loading.value = false;
} else {
  getRecipes();
}
})

if (search.value === undefined || search.value === ''){
  loading.value = false;
} else {
  getRecipes();
}
</script>

<template>
  <div>
    <div>
      <h1 class="text-3xl font-bold text-center m-3">Recipes search page</h1>
    </div>
    <div v-if="loading" class="flex flex-wrap">
      <RecipeContainerShimmer v-for="i in shimmerComponentsCount" />
    </div>
    <div v-else-if="search === '' || !search">
      <h3 class="text-lg font-normal text-center m-1">
        Search for something specific to find what you're looking for.
      </h3>
    </div>
    <EmptyListInformation
      v-else-if="recipeList && recipeList.length === 0"
      :description="`Oops, we couldn't find anything for „${search}“.`"
      button-text="View all recipes"
      @button-click="navigateTo('/recipes')"
    />
    <div v-else>
      <div class="m-2">
        <h3 class="text-lg font-normal text-center">
          Searching by: <b>„{{ search }}“</b>
        </h3>
        <h3 class="text-sm font-normal text-center">
          Found <b>{{ totalElementCount }}</b> recipes
        </h3>
        <SearchForm />
      </div>
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

<style scoped></style>
