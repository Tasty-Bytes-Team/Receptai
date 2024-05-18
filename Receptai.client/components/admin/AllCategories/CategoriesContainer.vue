<template>
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
    v-else-if="categories && categories.length === 0"
    description="Your tag list is currently empty. Why not add some tags today?"
  />
  <div v-else class="relative overflow-x-auto">
    <table class="w-full text-sm text-left rtl:text-right">
      <thead class="text-sm bg-concrete-100">
        <tr>
          <th
            scope="col"
            class="px-3 py-3 border-concrete-300 border-2 text-center min-w-14"
          >
            ID
          </th>
          <th
            scope="col"
            class="px-3 py-3 border-concrete-300 border-2 text-center min-w-36"
          >
            Preview image
          </th>
          <th
            scope="col"
            class="px-3 py-3 border-concrete-300 border-2 text-center min-w-36"
          >
            Name
          </th>
          <th
            scope="col"
            class="px-3 py-3 border-concrete-300 border-2 text-center min-w-36"
          >
            Description
          </th>
        </tr>
      </thead>
      <tbody>
        <SingleCategory v-for="category in categories" :category="category" />
      </tbody>
    </table>
    <div class="w-full text-center" v-if="totalPages > 0">
      <Pagination
        @change="getCategories"
        v-model="pageNumber"
        :totalPages
        :siblings
      />
    </div>
  </div>
</template>

<script setup lang="ts">
import SingleCategory from "./SingleCategory.vue";
import axios from "axios";
import Pagination from "@/components/Pagination/Pagination.vue";
import type { Category } from "@/typescript/types";
import EmptyListInformation from "@/components/EmptyListInformation.vue";

const config = useRuntimeConfig();

const categories = ref<Category[] | null>(null);
const loading = ref(true);

const pageNumber = ref(0);
const totalPages = ref(0);
const siblings = 2;

const getCategories = async () => {
  try {
    const result = await axios.get(
      `${config.public.baseURL}/api/v1/category/list?page=${pageNumber.value}`
    );
    categories.value = result.data.elements;
    totalPages.value = result.data.totalPageCount;
    loading.value = false;
  } catch (e) {
    console.error(e);
  }
};

getCategories();
</script>

<style scoped></style>
