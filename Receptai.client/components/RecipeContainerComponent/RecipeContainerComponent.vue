<script setup lang="ts">
defineProps({
  imageLink: String,
  name: String,
  raiting: Number,
  about: String,
  link: String,
  category: String,
  categoryLink: String,
  prepTime: Number,
});

const error = ref(false);
</script>

<template>
  <div class="lg:basis-1/4 sm:basis-1/3 basis-1/2">
    <div class="m-3">
      <NuxtLink :to="link">
        <NuxtImg
          :src="!error ? imageLink : '/assets/TastyBytes_Fallback.webp'"
          class="m-auto rounded-md lg:h-56 h-48 object-cover"
          @error="() => (error = true)"
        />
      </NuxtLink>
      <div class="px-3 py-2">
        <div class="text-sm font-normal">
          <NuxtLink :to="categoryLink">{{ category }}</NuxtLink>
          <span class="ml-3">
            <Icon name="majesticons:clock-line" color="black" />
            {{ prepTime }} min.
          </span>
        </div>
        <NuxtLink :to="link">
          <div>
            <h2 class="font-bold text-2xl py-2 leading-none">
              {{ name }}
            </h2>
            <p class="text-sm">
              {{ about }}
            </p>
          </div>
        </NuxtLink>
      </div>
    </div>
  </div>
</template>

<style lang="scss" scoped></style>
