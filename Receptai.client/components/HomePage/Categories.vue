<script setup lang="ts">
import axios from "axios";
import type { Category } from "@/typescript/types";
import CategoryComponent from "@/components/CategoryPage/CategoryComponent/CategoryComponent.vue";
import Pagination from "@/components/Pagination/Pagination.vue";
import EmptyListInformation from "@/components/EmptyListInformation.vue";
import CategoryContainerShimmer from "@/components/ShimmerLoaders/CategoryContainerShimmer.vue";

const config = useRuntimeConfig();

const shimmerComponentsCount = 4;

const categoriesList = ref<Category[] | null>(null);
const loading = ref(true);

const getCategories = async () => {
  try {
    await axios
      .get(`${config.public.baseURL}/api/v1/category/list`)
      .then((res) => {
        categoriesList.value = res.data.elements.slice(-4);
        loading.value = false;
      });
  } catch (e) {
    console.warn("Error fetching categories", e);
  }
};

getCategories();
</script>

<template>
  <div>
    <div v-if="loading" class="flex flex-wrap">
      <CategoryContainerShimmer v-for="i in shimmerComponentsCount" />
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
      </div>
    </div>
  </div>
</template>

<style scoped></style>
