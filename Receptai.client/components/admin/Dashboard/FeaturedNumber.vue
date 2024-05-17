<template>
  <div>
    <div
      class="bg-gray-200 border min-w-64 min-h-36 border-gray-300 flex flex-col justify-center items-center rounded-md hover:bg-gray-300 transition-colors duration-100"
    >
      <h3 class="font-semibold text-4xl">
        {{ featuredNumber }}
      </h3>
      <p class="font-light">
        {{ heading }}
      </p>
    </div>
    <RouterLink
      v-if="buttonText && buttonLink"
      class="w-full h-full"
      :to="buttonLink"
    >
      <button
        :to="buttonLink"
        class="w-full mt-1 p-1 bg-whiskey-200 rounded-md hover:bg-whiskey-300 duration-100 transition-colors"
      >
        {{ buttonText }}
      </button>
    </RouterLink>
  </div>
</template>

<script setup lang="ts">
defineProps<{
  heading: string;
  featuredNumber: number;
  buttonText?: string;
  buttonLink?: string;
}>();
</script>

<style scoped></style>
