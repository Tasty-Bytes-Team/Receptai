<script setup lang="ts"></script>

<template>
  <button
    class="bg-[#ffa8a8] px-3 py-[6px] text-sm rounded-sm font-medium hover:bg-[#ff7b7b] transition-colors duration-200"
    type="button"
    @click="$emit('delete')"
  >
    Remove
  </button>
</template>

<style scoped></style>
