<template>
    <tr class="border-b-2 border-concrete-200">
      <td class="text-center p-3">
        {{ category.id }}
      </td>
      <td>
        <Image v-if="category.previewImageUrl" class="m-auto" :preview-image="category.previewImageUrl" />
        <div v-else>
          Empty
        </div>
      </td>
      <td class="text-center p-3 font-semibold">
        {{ category.name }}
      </td>
      <td class="text-center p-3">
        {{ category.description }}
      </td>
    </tr>
  </template>
  
  <script setup lang="ts">
  import type { Category } from "@/typescript/types";
  import Image from "@/components/user/MyRecipes/Image.vue";
  
  defineProps<{
    category: Category;
  }>();
</script>
  
  <style scoped></style>
  