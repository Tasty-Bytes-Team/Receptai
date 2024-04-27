<script setup lang="ts">
import StarRating from '../Feedback/components/StarRating.vue';

defineProps({
  imageLink: String,
  name: String,
  rating: Number,
  about: String,
  link: String,
  category: String,
  categoryId: Number,
  prepTime: Number,
});

const error = ref(false);
</script>

<template>
  <div
    class="lg:basis-1/4 sm:basis-1/3 xsm:basis-1/2 basis-full"
    data-testid="recipe-container"
  >
    <div class="m-3">
      <NuxtLink :to="link" data-testid="recipe-url">
        <div class="relative">
          <NuxtImg
            :src="!error ? imageLink : '/assets/TastyBytes_Fallback.webp'"
            class="m-auto rounded-md lg:h-56 h-48 object-cover aspect-[4/3] w-full bg-concrete-200"
            @error="() => (error = true)"
            data-testid="recipe-image"
          />
          <span
            class="ml-3 absolute bottom-2 right-2 bg-white px-2 py-1 rounded-md font-bold text-sm shadow-[3px_3px_0_0_#353535a1]"
          >
            <Icon name="majesticons:clock-line" color="black" />
            <span data-testid="recipe-cooking-time">{{ prepTime }} min.</span>
          </span>
        </div>
      </NuxtLink>
      <div class="px-3 py-2">
        <div class="text-sm font-normal">
          <NuxtLink
            :to="`/recipe-category/${categoryId}`"
            class="hover:underline duration-150 transition-all"
            data-testid="recipe-category"
            >{{ category }}</NuxtLink
          >
        </div>
        <NuxtLink :to="link">
          <div>
            <h2
              class="font-bold text-2xl py-2 leading-none"
              data-testid="recipe-name"
            >
              {{ name }}
            </h2>
            <StarRating v-if="rating && rating > 0" :setRating="rating/2" />
            <p class="text-sm" data-testid="recipe-description">
              {{ about }}
            </p>
          </div>
        </NuxtLink>
      </div>
    </div>
  </div>
</template>

<style lang="scss" scoped></style>
