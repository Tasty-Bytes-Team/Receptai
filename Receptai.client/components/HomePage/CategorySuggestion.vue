<template>
    <RouterLink :to="`/recipe-category/${recipeID}`">
  <div
    class="bg-gradient-to-br from-whiskey-100 to-whiskey-50 rounded-md p-4 shadow-md border"
  >
    <h3 class="text-2xl font-semibold mb-3">{{ heading }}</h3>
    <div class="flex flex-row flex-wrap items-center gap-[6%]">
      <div class="sm:basis-[47%] basis-full">
        <NuxtImg
          :src="
              url
          "
          class="m-auto rounded-md lg:h-56 h-48 object-cover aspect-[4/3] w-full bg-concrete-200"
        />
      </div>
      <div class="sm:basis-[47%] basis-full">
        <p class="p-2 leading-5">
          {{ description }}
        </p>
      </div>
    </div>
  </div>
</RouterLink>
</template>

<script setup lang="ts">
defineProps<{
    recipeID: string;
    url: string;
    heading: string;
    description: string;
}>();
</script>

<style scoped></style>
