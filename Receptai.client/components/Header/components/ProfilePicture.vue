<script setup lang="ts">
const props = defineProps<{
  user_name: string;
}>();
</script>

<template>
  <div
    class="bg-indigo-300 rounded-full flex justify-center items-center font-semibold"
  >
    {{ user_name.substring(0, 2) }}
  </div>
</template>

<style scoped></style>
