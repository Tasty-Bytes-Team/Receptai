<script setup lang="ts"></script>

<template>
  <button
    class="bg-red-300 px-3 py-[6px] text-sm rounded-sm font-medium hover:bg-red-500 transition-colors duration-200"
    type="button"
    @click="$emit('delete')"
  >
    Remove
  </button>
</template>

<style scoped></style>
