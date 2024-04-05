<script setup lang="ts">
import axios from "axios";
import CategoryComponent from "@/components/CategoryPage/CategoryComponent/CategoryComponent.vue";
import Pagination from "@/components/Pagination/Pagination.vue";

interface Category {
  id: number;
  name: string;
  description: string | null;
  previewImageUrl: string | null;
}

const config = useRuntimeConfig();

const categoriesList = ref<Category[] | null>(null);
const loading = ref(true);

const pageNumber = ref(0);

const totalPages = ref(0);
const siblings = 2;

const getCategories = async () => {
  try {
    await axios
      .get(
        `${config.public.baseURL}/api/v1/category/list?page=${pageNumber.value}`
      )
      .then((res) => {
        categoriesList.value = res.data.elements;
        totalPages.value = res.data.totalPageCount;
        loading.value = false;
      });
  } catch (e) {
    console.warn("Error fetching categories", e);
  }

  window.scrollTo(0, 0);
};

getCategories();
</script>

<template>
  <div>
    <div>
      <h1 class="text-3xl font-bold text-center m-3">Categories</h1>
    </div>
    <div v-if="loading">Loading...</div>
    <div
      v-else-if="categoriesList && categoriesList.length === 0"
      class="flex flex-col items-center gap-3"
    >
      <p>
        This is your launchpad for discovering everything we have to offer!
        While our categories are currently under construction and we haven't
        quite filled the shelves yet, exciting things are coming soon.
      </p>
      <button
        @click="navigateTo('/')"
        class="p-1 px-4 text-lg rounded-sm text-black font-medium bg-chilean-heath-200 hover:bg-chilean-heath-300 transition-colors duration-200"
      >
        Home page
      </button>
    </div>
    <div v-else>
      <div class="flex flex-wrap">
        <CategoryComponent
          v-for="cat in categoriesList"
          :id="cat.id"
          :name="cat.name"
          :description="cat.description"
          :preview-image-url="cat.previewImageUrl"
        />
        <div class="w-full text-center" v-if="totalPages > 0">
          <Pagination
            @change="getCategories"
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
