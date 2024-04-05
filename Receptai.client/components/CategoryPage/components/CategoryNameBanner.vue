<script setup lang="ts">
interface Category {
  id: number;
  name: string;
  description: string | null;
  previewImageUrl: string | null;
}

defineProps<{
  categoryInfo: Category;
}>();
</script>

<template>
  <div
    class="bg-black text-white rounded-md m-1 bg-cover bg-center"
    v-bind:style="
      categoryInfo.previewImageUrl
        ? {
            'background-image': 'url(' + categoryInfo.previewImageUrl + ')',
          }
        : null
    "
  >
    <div
      class="h-full w-full py-10"
      :class="
        categoryInfo.previewImageUrl
          ? 'bg-[#00000070] h-full w-full py-10 rounded-md'
          : null
      "
    >
      <h1 class="text-2xl font-bold text-center">{{ categoryInfo.name }}</h1>
      <p v-if="categoryInfo.description" class="text-center">
        {{ categoryInfo.description }}
      </p>
    </div>
  </div>
</template>

<style scoped></style>
