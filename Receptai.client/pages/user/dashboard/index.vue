<script setup lang="ts">
import { ref, onMounted } from 'vue';
import type { UserCookie, Recipe } from '@/typescript/types';
import axios from 'axios';
import Widget from "./Widgets/WidgetComponent.vue";
import RecipeBannerComponent from '@/components/Banner/RecipeBannerComponent.vue';
definePageMeta({
  layout: "user",
  middleware: "auth",
});

const currentTime = ref(new Date().toLocaleTimeString());

const config = useRuntimeConfig();
const TastyBytes_user = useCookie<UserCookie>('TastyBytes_user');
const recipeList = ref<Recipe[]>([]);

async function fetchRecipes() {
  if (!TastyBytes_user.value?.token) {
    console.error('User is not logged in or no token available');
    return;
  }

  try {
    const response = await axios.get(`${config.public.baseURL}/api/v1/recipe/list?page=0&sortBy=dateCreated&sortAsc=true`, {
      headers: {
        Authorization: `Bearer ${TastyBytes_user.value.token}`
      }
    });
    // Filter the recipes client-side based on the logged-in user ID
    recipeList.value = response.data.elements.filter((recipe: Recipe) => recipe.author.id === TastyBytes_user.value.user.id);
  } catch (error) {
    console.error("Error fetching recipes", error);
  }
}

onMounted(fetchRecipes);

onMounted(fetchRecipes);

function updateCurrentTime() {
  currentTime.value = new Date().toLocaleTimeString();
}

onMounted(() => {
  setInterval(updateCurrentTime, 1000);
});
</script>

<template>
  <div class="container mx-auto py-8">
    <h1 class="text-3xl font-bold mb-4">Dashboard</h1>
    <h1 class="text-sm lg:text-2xl md:text-lg sm:text-sm">Welcome back, {{ TastyBytes_user.user.name }}!</h1>
    <div class="grid sm:grid-cols-2 lg:grid-cols-3 gap-4">
      <!-- Placeholder div to push content to the right on all sizes -->
      <div class="py-8 col-span-1 lg:col-span-2">
        All needed info here
      </div>
      <div class="col-span-1">
        <!-- Current Time Widget -->
        <Widget class="border" titleSize="2xl" title="Current Time" orientation="horizontal">
          <div class="font-roboto-mono font-bold flex justify-center items-center">{{ currentTime }}</div>
        </Widget>

        <!-- Newest Recipe Widget -->
        <Widget class="border" title="Your Newest Recipe" orientation="square">
          <div v-if="recipeList.length > 0">
            <div class="flex-shrink-0 mt-2 ">
              <RecipeBannerComponent :imageLink="recipeList[0].previewImage" :name="recipeList[0].name"
                :about="recipeList[0].shortDescription" :link="`/recipes/${recipeList[0].id}`" />
            </div>
          </div>
          <div v-else>
            No recent recipes found or still loading...
          </div>
        </Widget>
      </div>
    </div>
  </div>
</template>

<style lang="scss" scoped></style>
