<script setup lang="ts">
defineProps<{
  pageNumber: number;
  siblings: number;
  page: number;
}>();
</script>

<template>
  <li v-if="page <= pageNumber + 1 + siblings && page >= pageNumber - 1">
    <button
      @click="$emit('onSelectedPage')"
      class="flex items-center justify-center px-4 h-10 leading-tight text-gray-800 bg-white border border-gray-300 hover:bg-whiskey-50"
      :class="page === pageNumber + 1 ? '!bg-whiskey-200' : null"
    >
      {{ page }}
    </button>
  </li>
</template>

<style scoped></style>
