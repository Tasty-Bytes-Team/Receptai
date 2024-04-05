<script setup lang="ts">
import axios from 'axios'
import CategoryComponent from '@/components/CategoryPage/CategoryComponent/CategoryComponent.vue'

const config = useRuntimeConfig();

interface Category{
    id: number;
    name: string;
    primary: boolean;
}

const categories = ref<Category[] | null>(null);

try {
    await axios
      .get(
        `${config.public.baseURL}/api/v1/category/list`
      )
      .then((res) => {
        categories.value = res.data;
      });
  } catch (e) {
    console.error("Error fetching category", e);
  }
</script>

<template>
    <div class="flex flex-wrap">
      <CategoryComponent v-for="cat in categories" :id="cat.id" :name="cat.name" :primary="cat.primary"  />
    </div>
</template>

<style scoped>

</style>