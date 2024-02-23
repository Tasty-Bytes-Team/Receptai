<script setup>
const props = defineProps({
  imageLink: String,
  name: String,
  raiting: Number,
  about: String,
  link: String,
  category: String,
  categoryLink: String,
  prepTime: Number,
});

</script>

<template>
  <div id="recipe-container" class="lg:basis-1/4 sm:basis-1/3 basis-1/2">
    <div class="m-3">
      <NuxtLink :to="link">
        <img id="recipe-container__image" :src="imageLink" class="m-auto rounded-md" />
      </NuxtLink>
      <div id="recipe-container__text" class="px-3 py-2">
        <div id="recipe-container__info" class="text-sm font-normal">
          <NuxtLink id="recipe-container__category" :to="categoryLink">{{
            category
          }}</NuxtLink>
          <span id="recipe-container__prep_time" class="ml-3"
            ><Icon name="majesticons:clock-line" color="black" />
{{ prepTime }} min.</span
          >
        </div>
        <NuxtLink :to="link">
          <div>
            <h2
              id="recipe-container__name"
              class="font-bold text-2xl py-2 leading-none"
            >
              {{ name }}
            </h2>
            <p id="recipe-container__about" class="text-sm">
              {{ about }}
            </p>
          </div>
        </NuxtLink>
      </div>
    </div>
  </div>
</template>

<style lang="scss" scoped></style>
