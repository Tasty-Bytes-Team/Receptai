<script setup lang="ts">
import type { UserCookie, Recipe } from "@/typescript/types";
import axios from "axios";
import Widget from "./Widgets/WidgetComponent.vue";
import RecipeBannerComponent from "~/components/Banner/RecipeBannerComponent.vue";

definePageMeta({
  layout: "user",
  middleware: "auth",
});

const stopwatch = ref({
  startTime: 0,
  elapsedTime: 0,
  isRunning: false,
  intervalId: null as null | ReturnType<typeof setInterval>,
});

function startStopwatch() {
  if (!stopwatch.value.isRunning) {
    stopwatch.value.startTime =
      new Date().getTime() - stopwatch.value.elapsedTime;
    stopwatch.value.isRunning = true;
    stopwatch.value.intervalId = setInterval(updateStopwatch, 10);
  }
}
function stopStopwatch() {
  stopwatch.value.elapsedTime =
    new Date().getTime() - stopwatch.value.startTime;
  stopwatch.value.isRunning = false;
  stopwatch.value.intervalId && clearInterval(stopwatch.value.intervalId);
  stopwatch.value.intervalId = null;
}

function resetStopwatch() {
  stopStopwatch();
  stopwatch.value.elapsedTime = 0;
}

function updateStopwatch() {
  const currentTime = new Date().getTime();
  const elapsedTime = currentTime - stopwatch.value.startTime;
  stopwatch.value.elapsedTime = elapsedTime;
}

function formatTime(time: number) {
  const seconds = Math.floor((time / 1000) % 60);
  const minutes = Math.floor((time / (1000 * 60)) % 60);
  const hours = Math.floor(time / (1000 * 60 * 60));
  return `${hours.toString().padStart(2, "0")}:${minutes
    .toString()
    .padStart(2, "0")}:${seconds.toString().padStart(2, "0")}`;
}

const config = useRuntimeConfig();
const TastyBytes_user = useCookie<UserCookie>("TastyBytes_user");
const recipeList = ref<Recipe[]>([]);

async function fetchRecipes() {
  if (!TastyBytes_user.value?.token) {
    console.error("User is not logged in or no token available");
    return;
  }

  try {
    const response = await axios.get(
      `${config.public.baseURL}/api/v1/recipe/list?page=0&sortBy=dateCreated&sortAsc=true`,
      {
        headers: {
          Authorization: `Bearer ${TastyBytes_user.value.token}`,
        },
      }
    );

    const userRecipes = response.data.elements.filter(
      (recipe: Recipe) => recipe.author.id === TastyBytes_user.value.user.id
    );

    userRecipes.sort(
      (a: Recipe, b: Recipe) =>
        new Date(b.dateCreated).getTime() - new Date(a.dateCreated).getTime()
    );

    recipeList.value = userRecipes;
  } catch (error) {
    console.error("Error fetching recipes", error);
  }
}

onMounted(fetchRecipes);
</script>

<template>
  <div>
    <div class="max-w-7xl mx-auto px-4 sm:px-6 lg:px-8">
      <div class="mb-4 p-2 border-b-2">
        <h1 class="text-4xl font-bold text-gray-800">Dashboard</h1>
        <p class="text-lg text-gray-600">
          Welcome back, {{ TastyBytes_user?.user.name }}!
        </p>
      </div>

      <div class="grid grid-cols-1 sm:grid-cols-2 lg:grid-cols-3 gap-6">
        <div class="col-span-1 sm:col-span-2 lg:col-span-2">
          <div
            class="bg-white rounded-lg shadow-md p-6 flex items-center justify-center"
          >
            <Widget
              class="border"
              orientation="horizontal"
              title="Your recipe count is"
            >
              <div class="flex items-center justify-center">
                {{ recipeList.length }}
              </div>
            </Widget>
          </div>
          <div class="bg-white rounded-lg shadow-md p-6 mt-6">
            <Widget class="border" title="Your Newest Recipes">
              <div class="grid grid-cols-2 sm:grid-cols-2 md:grid-cols-5 gap-4">
                <template v-if="recipeList.length > 0">
                  <div
                    v-for="recipe in recipeList.slice(0, 5)"
                    :key="recipe.id"
                    class="flex-shrink-0 mt-2"
                  >
                    <RecipeBannerComponent
                      :imageLink="recipe.previewImage"
                      :name="recipe.name"
                      :about="recipe.shortDescription"
                      :link="`/recipes/${recipe.id}`"
                      :height="80"
                      :width="140"
                      :showDarkArea="false"
                    />
                  </div>
                </template>
                <div v-else>No recent recipes found or still loading...</div>
              </div>
            </Widget>
          </div>
        </div>
        <div class="col-span-1">
          <div class="bg-white rounded-lg shadow-md p-6">
            <Widget class="border">
              <div class="flex items-center justify-center">
                <div class="text-4xl font-bold">
                  {{ formatTime(stopwatch.elapsedTime) }}
                </div>
              </div>
              <div class="flex justify-center py-4">
                <button
                  @click="startStopwatch"
                  v-if="!stopwatch.isRunning"
                  class="bg-red-500 text-white py-2 px-4 rounded-lg mr-2"
                >
                  Start
                </button>
                <button
                  @click="stopStopwatch"
                  v-if="stopwatch.isRunning"
                  class="bg-red-500 text-white py-2 px-4 rounded-lg mr-2"
                >
                  Stop
                </button>
                <button
                  @click="resetStopwatch"
                  class="bg-gray-500 text-white py-2 px-4 rounded-lg"
                >
                  Reset
                </button>
              </div>
            </Widget>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<style lang="scss" scoped></style>
