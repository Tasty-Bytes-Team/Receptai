<script setup lang="ts">
import axios from "axios";
import type {
  UserCookie,
  Recipe,
  RecipeContainerColumn,
} from "@/typescript/types";
import RecipeContainer from "@/components/admin/AllRecipes/RecipeContainer.vue";
import Pagination from "@/components/Pagination/Pagination.vue";
import EmptyListInformation from "@/components/EmptyListInformation.vue";

definePageMeta({
  layout: "admin",
  middleware: "admin",
});

const config = useRuntimeConfig();

const TastyBytes_user = useCookie<UserCookie | null>("TastyBytes_user");

const recipes = ref<Recipe[] | null>(null);
const key = ref(0);

const pageNumber = ref(0);

const activeSortKey = ref("dateCreated");
const isSortAscending = ref(false);

const previouslySortedColumn = ref<null | string>(null);

const loading = ref(true);

const totalPages = ref(0);
const siblings = 2;

const sortableColumns: RecipeContainerColumn[] = [
  {
    key: "id",
    label: "ID",
    sortable: false,
  },
  {
    key: "image",
    label: "Image",
    sortable: false,
  },
  {
    key: "name",
    label: "Name",
    sortable: true,
    sortBy: "DEFAULT",
    curr: false,
  },
  {
    key: "author",
    label: "Author",
    sortable: false,
  },
  {
    key: "dateCreated",
    label: "Creaton date",
    sortable: true,
    sortBy: "DEFAULT",
    curr: false,
  },
  {
    key: "dateModified",
    label: "Update date",
    sortable: false,
  },
  {
    key: "action",
    label: "Action",
    sortable: false,
  },
];

const getData = async () => {
  try {
    await axios
      .get(
        `${config.public.baseURL}/api/v1/recipe/list?page=${pageNumber.value}&sortBy=${activeSortKey.value}&sortAsc=${isSortAscending.value}`,
        {
          headers: { Authorization: `Bearer ${TastyBytes_user.value?.token}` },
        }
      )
      .then((res) => {
        recipes.value = res.data.elements;
        totalPages.value = res.data.totalPageCount;
        key.value++;
        loading.value = false;
      });
  } catch (e) {
    console.warn("Error fetching my recipes", e);
  }

  window.scrollTo(0, 0);
};

const updateDataSort = (item: RecipeContainerColumn) => {
  if (
    previouslySortedColumn.value &&
    previouslySortedColumn.value !== item.key
  ) {
    const found = sortableColumns.find(
      (col) => col.key === previouslySortedColumn.value
    );

    if (found) {
      found.curr = false;
      found.sortBy = "DEFAULT";
    }
  }

  previouslySortedColumn.value = item.key;
  if (item.curr === true) {
    switch (item.sortBy) {
      case "DESC":
        item.sortBy = "ASC";
        break;
      case "ASC":
        item.sortBy = "DESC";
        break;
      case "DEFAULT":
        item.sortBy = "DESC";
        break;
    }
  } else {
    item.curr = true;
    item.sortBy = "DESC";
  }

  activeSortKey.value = item.key;
  isSortAscending.value =
    item.sortBy === "DESC" ? false : item.sortBy === "ASC" ? true : false;

  pageNumber.value = 0;
  getData();
};

getData();
</script>

<template>
  <div>
    <h1 class="text-3xl font-bold text-center m-3">All Recipes</h1>
    <div v-if="loading">
      <div role="status" class="flex justify-center items-center my-2">
        <img
          src="/assets/loader.svg"
          alt="Recipe loader"
          class="w-9 h-9 animate-spin"
        />
        <span class="sr-only">Loading...</span>
      </div>
    </div>
    <EmptyListInformation
      v-else-if="recipes && recipes.length === 0"
      description="Your recipe box is currently empty. Why not add a new recipe today?"
      button-text="Create a new recipe"
      @button-click="navigateTo('/user/dashboard/my-recipes/create')"
    />
    <div v-else>
      <div class="flex flex-col gap-2">
        <RecipeContainer
          :key="key"
          :columns="sortableColumns"
          @reload="getData()"
          @change-sort="updateDataSort"
          :recipes
        />
        <div class="w-full text-center" v-if="totalPages > 0">
          <Pagination
            @change="getData"
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
