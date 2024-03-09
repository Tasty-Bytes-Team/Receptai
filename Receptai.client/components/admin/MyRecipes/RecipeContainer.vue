<script setup lang="ts">
interface Recipe {
  id: number;
  name: string;
  shortDescription: string;
  author: Author;
  dateCreated: string;
  dateModified: string | null;
  previewImage: string;
  tutorialVideo?: string;
  ingredients: Ingredients[];
  instructions: string[];
  tags: string[];
  categories: Category[];
  minutesToPrepare: number;
  portions: number;
}

interface Author {
  name: string;
}

interface Ingredients {
  purpose: string;
  ingredients: Ingredient[];
}

interface Ingredient {
  name: string;
  quantity: number;
  unit: string;
}

interface Category {
  name: string;
  link: string;
}

defineProps<{
  recipe: Recipe;
}>();

const error = ref(false);
</script>

<template>
  <NuxtLink :to="`/recipes/${recipe.id}`" target="_blank">
    <div
      class="flex flex-row gap-3 border-2 border-gray-400 bg-[#f5f5f5] rounded-md p-2 items-center shadow-[2px_2px_0_0_#919191] hover:shadow-[4px_4px_0_0_#444444] transition-shadow duration-200"
    >
      <NuxtImg
        :src="!error ? recipe.previewImage : '/assets/TastyBytes_Fallback.webp'"
        class="rounded-md aspect-[4/3] border-2 border-[#c4c4c4] shadow-md max-h-24 object-cover"
        @error="() => (error = true)"
      />
      <div class="flex flex-col justify-center">
        <div>
          <h2 class="font-bold text-lg">{{ recipe.name }}</h2>
        </div>
        <div class="flex flex-row gap-2">
          <h5 class="text-xs font-normal" v-for="category in recipe.categories">
            {{ category.name }}
          </h5>
          <h5 class="text-xs font-normal">
            {{ recipe.dateCreated.split("T")[0] }}
          </h5>
        </div>
        <div>
          <p class="text-sm">{{ recipe.shortDescription }}</p>
        </div>
      </div>
    </div>
  </NuxtLink>
</template>

<style scoped></style>
