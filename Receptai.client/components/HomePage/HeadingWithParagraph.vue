<template>
  <div class="flex flex-col gap-0">
    <h3 class="font-semibold text-2xl">{{ heading }}</h3>
    <p class="font-normal text-sm">
      {{ paragraph }}
    </p>
  </div>
</template>

<script setup lang="ts">
defineProps<{
    heading: string;
    paragraph: string;
}>();
</script>

<style scoped></style>
