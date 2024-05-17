<script setup lang="ts">
import type { Recipe } from "@/typescript/types";
import NutritionTable from "@/components/RecipePage/components/NutritionTable.vue";
import Badge from "@/components/RecipePage/components/Badge.vue";
import InfoBadge from "@/components/RecipePage/components/InfoBadge.vue";
import CookingInstructions from "@/components/RecipePage/components/CookingInstructions.vue";
import Ingredients from "@/components/RecipePage/components/Ingredients.vue";
import printDownload from "@/typescript/printRecipe";
import StarRating from "../Feedback/components/StarRating.vue";

defineProps<{
  recipe: Recipe;
}>();

const error = ref(false);
</script>

<template>
  <div>
    <div class="max-w-screen-lg m-auto my-5 px-2">
      <p v-if="recipe.categories.length > 0">
        <NuxtLink class="hover:underline font-extrabold" to="/">Home</NuxtLink>
        >
        <NuxtLink
          class="hover:underline font-medium"
          :to="`/recipe-category/${recipe.categories[0].id}`"
          >{{ recipe.categories[0].name }}</NuxtLink
        >
        > {{ recipe.name }}
      </p>
      <p v-else>Home > {{ recipe.name }}</p>
    </div>
    <div class="bg-[#f7f7f7] border-y-2">
      <div class="max-w-screen-lg m-auto my-5 px-2">
        <div class="flex flex-wrap items-center">
          <div class="lg:basis-2/5 md:basis-1/2 basis-full">
            <div class="p-4">
              <NuxtImg
                :src="
                  !error
                    ? recipe.previewImage
                    : '/assets/TastyBytes_Fallback.webp'
                "
                class="rounded-md border-2 border-[#c4c4c4] shadow-md max-h-96 m-auto object-cover aspect-[4/3]"
                @error="() => (error = true)"
              />
            </div>
          </div>
          <div class="lg:basis-3/5 md:basis-1/2 basis-full">
            <div class="p-4">
              <div>
                <StarRating
                  v-if="recipe.averageRating > 0"
                  :setRating="recipe.averageRating / 2"
                />
                <h1 class="font-bold text-4xl">{{ recipe.name }}</h1>
                <div class="text-sm font-light">
                  <span class="font-medium">{{ recipe.author.name }}, </span
                  ><span>{{ recipe.dateCreated.split("T")[0] }}</span>
                </div>
              </div>
              <div class="m-2">
                <button
                  class="p-2 rounded-sm flex flex-row gap-2 bg-concrete-200 hover:bg-white border transition-colors duration-100"
                  @click.prevent="printDownload(recipe)"
                >
                  <Icon name="fa6-solid:print" color="black" size="24px" />
                  <span class="font-medium">Print recipe</span>
                </button>
              </div>
              <p>
                {{ recipe.shortDescription }}
              </p>
              <div class="my-3" v-if="false">
                <NutritionTable />
              </div>
            </div>
            <div class="flex flex-wrap">
              <InfoBadge
                icon="majesticons:clock-line"
                text="Preparation:"
                :info="`${recipe.minutesToPrepare} min.`"
              />
              <InfoBadge
                icon="tabler:tools-kitchen-2"
                text="Portions:"
                :info="recipe.portions.toString()"
              />
              <Badge
                v-for="tag in recipe.tags"
                :icon="tag.iconName"
                :text="tag.name"
              />
            </div>
          </div>
        </div>
      </div>
    </div>
    <div class="max-w-screen-lg m-auto my-3 px-2">
      <div class="flex flex-wrap">
        <div class="my-3 md:basis-2/5 basis-full">
          <Ingredients :ingredients="recipe.ingredients" />
        </div>
        <div class="my-3 md:basis-3/5 basis-full">
          <CookingInstructions :cookingInstructions="recipe.instructions" />
        </div>
      </div>
    </div>
    <div
      v-if="recipe.tutorialVideoEmbed"
      class="max-w-screen-lg m-auto my-2 px-2"
    >
      <h3 class="font-semibold text-2xl mb-3">
        Searching for video instructions?
        <span class="font-normal">Find them here!</span>
      </h3>
      <iframe
        class="w-4/6 lg:h-[400px] sm:h-[300px] h-[200px] m-auto"
        :src="recipe.tutorialVideoEmbed"
        frameborder="0"
        allowfullscreen
      ></iframe>
    </div>
  </div>
</template>

<style lang="scss" scoped></style>
