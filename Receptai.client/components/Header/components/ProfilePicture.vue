<script setup lang="ts">
defineProps<{
  user_name: string;
  size: number;
}>();
</script>

<template>
  <div
    class="bg-indigo-300 rounded-full flex justify-center items-center font-semibold"
    :class="`w-${size} h-${size}`"
  >
    {{ user_name.substring(0, 2) }}
  </div>
</template>

<style scoped></style>
