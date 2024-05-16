<template>
  <div
    class="bg-gradient-to-br from-whiskey-100 to-whiskey-50 rounded-md p-4 shadow-md border"
  >
    <h3 class="text-2xl font-semibold mb-3">{{ heading }}</h3>
    <div class="flex flex-row flex-wrap items-center gap-[6%]">
      <div class="sm:basis-[47%] basis-full">
        <NuxtImg
          v-if="category?.previewImageUrl"
          :src="
            !error
              ? category?.previewImageUrl
              : '/assets/TastyBytes_Fallback.webp'
          "
          class="m-auto rounded-md lg:h-56 h-48 object-cover aspect-[4/3] w-full bg-concrete-200"
          @error="() => (error = true)"
        />
      </div>
      <div class="sm:basis-[47%] basis-full">
        <p class="p-2 leading-5">
          {{ description }}
        </p>
      </div>
    </div>
  </div>
</template>

<script setup lang="ts">
import axios from "axios";
import type { Category } from "@/typescript/types";

const config = useRuntimeConfig();

const props = defineProps<{
    id: string;
    heading: string;
    description: string;
}>();

const category = ref<Category | null>(null);
const loading = ref(true);
const error = ref(false);

const getCategory = async () => {
  loading.value = true;
  try {
    await axios
      .get(`${config.public.baseURL}/api/v1/category/${props.id}`)
      .then((res) => {
        category.value = res.data;
        loading.value = false;
      });
  } catch (e) {
    console.warn("Error fetching category", e);
  }
};

getCategory();
</script>

<style scoped></style>
