<script setup lang="ts">
defineProps<{
  description: string;
  buttonText: string;
}>();
</script>

<template>
  <div class="flex flex-col items-center gap-3">
    <p>
      {{ description }}
    </p>
    <button
      @click="$emit('button-click')"
      class="p-1 px-4 text-lg rounded-sm text-black font-medium bg-chilean-heath-200 hover:bg-chilean-heath-300 transition-colors duration-200"
    >
      {{ buttonText }}
    </button>
  </div>
</template>

<style scoped></style>
