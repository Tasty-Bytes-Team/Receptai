<script setup lang="ts">
import axios from "axios";
import CategoryComponent from "@/components/CategoryPage/CategoryComponent/CategoryComponent.vue";

const config = useRuntimeConfig();

interface Category {
  id: number;
  name: string;
  description: string | null;
  previewImageUrl: string | null;
}

const categories = ref<Category[] | null>(null);

try {
  await axios
    .get(`${config.public.baseURL}/api/v1/category/list`)
    .then((res) => {
      categories.value = res.data.elements;
    });
} catch (e) {
  console.error("Error fetching category", e);
}
</script>

<template>
  <div class="flex flex-wrap">
    <CategoryComponent
      v-for="cat in categories"
      :id="cat.id"
      :name="cat.name"
      :description="cat.description"
      :preview-image-url="cat.previewImageUrl"
    />
  </div>
</template>

<style scoped></style>
