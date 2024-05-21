<script setup lang="ts">
defineProps<{
  id: number;
  name: string;
  description: string | null;
  previewImageUrl: string | null;
}>();

const error = ref(false);
</script>

<template>
  <div
    class="lg:basis-1/4 sm:basis-1/3 xsm:basis-1/2 basis-full"
    data-testid="recipe-container"
  >
    <div class="m-3">
      <NuxtLink :to="`/categories/${id}`">
        <div class="relative">
          <NuxtImg
            v-if="previewImageUrl"
            :src="!error ? previewImageUrl : '/assets/TastyBytes_Fallback.webp'"
            class="m-auto rounded-md lg:h-56 h-48 object-cover aspect-[4/3] w-full bg-concrete-200"
            @error="() => (error = true)"
          />
          <NuxtImg
            v-else
            :src="'/assets/TastyBytes_Fallback.webp'"
            class="m-auto rounded-md lg:h-56 h-48 object-cover aspect-[4/3] w-full bg-concrete-200"
          />
        </div>
        <div class="px-3 py-2">
          <div>
            <h2
              class="font-bold text-2xl py-2 leading-none"
              data-testid="recipe-name"
            >
              {{ name }}
            </h2>
            <p
              v-if="description"
              class="text-sm"
              data-testid="recipe-description"
            >
              {{ description }}
            </p>
          </div>
        </div>
      </NuxtLink>
    </div>
  </div>
</template>

<style scoped></style>
