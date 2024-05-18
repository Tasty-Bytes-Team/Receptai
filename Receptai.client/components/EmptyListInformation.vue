<script setup lang="ts">
defineProps<{
  description: string;
  buttonText?: string;
}>();
</script>

<template>
  <div class="flex flex-col items-center gap-4 my-4">
    <p class="text-center">
      {{ description }}
    </p>
    <button
      v-if="buttonText"
      @click="$emit('button-click')"
      class="p-1.5 px-5 text-lg rounded-sm text-black font-medium bg-chilean-heath-200 hover:bg-chilean-heath-300 transition-colors duration-200"
    >
      {{ buttonText }}
    </button>
  </div>
</template>

<style scoped></style>
