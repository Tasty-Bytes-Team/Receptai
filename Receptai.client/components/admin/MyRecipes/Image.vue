<script setup lang="ts">
defineProps<{
  previewImage: string;
}>();

const error = ref(false);
</script>
<template>
  <NuxtImg
    :src="!error ? previewImage : '/assets/TastyBytes_Fallback.webp'"
    class="aspect-[4/3] max-h-24 w-full object-cover"
    @error="() => (error = true)"
  />
</template>

<style scoped></style>
