<script setup lang="ts">
import axios from "axios";

import youtube_parser from "@/typescript/youtubeId";
import NutritionTable from "@/components/RecipePage/components/NutritionTable.vue";
import Badge from "@/components/RecipePage/components/Badge.vue";
import InfoBadge from "@/components/RecipePage/components/InfoBadge.vue";
import CookingInstructions from "@/components/RecipePage/components/CookingInstructions.vue";
import Ingredients from "@/components/RecipePage/components/Ingredients.vue";

const props = defineProps<{
  id: string | string[];
}>();

const config = useRuntimeConfig();

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
  tags: Tag[];
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

interface Tag {
  id: number;
  iconName: string;
  name: string;
}

const recipe = ref<Recipe>(Object.create(null));
const video = ref<string | null>(null);

const error = ref(false);
const loading = ref(true);

try {
  axios
    .get(`${config.public.baseURL}/api/v1/recipe/get/${props.id}`)
    .then((res) => {
      recipe.value = res.data;
      video.value = recipe.value?.tutorialVideo
        ? youtube_parser(recipe.value.tutorialVideo)
        : null;
      loading.value = false;
    });
} catch (e) {
  console.error("Error fetching recipe", e);
}
</script>

<template>
  <div v-if="loading" class="max-w-screen-lg m-auto my-5 px-2">Loading...</div>
  <div v-else>
    <div class="max-w-screen-lg m-auto my-5 px-2">
      <p v-if="recipe.categories.length > 0">
        Home > {{ recipe.categories[0].name }} > {{ recipe.name }}
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
              <div class="mb-3">
                <h1 class="font-bold text-4xl">{{ recipe.name }}</h1>
                <div class="text-sm font-light">
                  <span class="font-medium">{{ recipe.author.name }}, </span
                  ><span>{{ recipe.dateCreated.split("T")[0] }}</span>
                </div>
              </div>
              <p>
                {{ recipe.shortDescription }}
              </p>
              <div class="my-3">
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
    <div v-if="video" class="max-w-screen-lg m-auto my-2 px-2">
      <h3 class="font-semibold text-xl mb-3">
        Searching for video instructions?
        <span class="font-normal">Find them here!</span>
      </h3>
      <iframe
        class="w-4/6 lg:h-[400px] sm:h-[300px] h-[200px] m-auto"
        :src="`https://www.youtube.com/embed/${video}`"
        title="LASAGNA/LAZANYA (quick and easy)"
        frameborder="0"
        allow="accelerometer; autoplay; clipboard-write; encrypted-media; gyroscope; picture-in-picture; web-share"
        allowfullscreen
      ></iframe>
    </div>
  </div>
</template>

<style lang="scss" scoped></style>
