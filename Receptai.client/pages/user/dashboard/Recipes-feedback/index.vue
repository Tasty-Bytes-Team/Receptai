<script setup lang="ts">
import axios from "axios";
import type { UserCookie, Review } from "@/typescript/types";
import dateWithTime from "@/typescript/dateFormating";
import StarRating from "@/components/Feedback/components/StarRating.vue";

definePageMeta({
  layout: "admin",
  middleware: "auth",
});

const config = useRuntimeConfig();

const TastyBytes_user = useCookie<UserCookie | null>("TastyBytes_user");

const reviews = ref<Review[] | null>(null);

const pageNumber = ref(0);

const loading = ref(true);

const totalPages = ref(0);
const siblings = 2;

const getData = async () => {
  try {
    await axios
      .get(
        `${config.public.baseURL}/api/v1/user/recipes/feedback?page=${pageNumber.value}`,
        {
          headers: { Authorization: `Bearer ${TastyBytes_user.value?.token}` },
        }
      )
      .then((res) => {
        reviews.value = res.data.elements;
        totalPages.value = res.data.totalPageCount;
        loading.value = false;
        console.log(reviews.value);
      });
  } catch (e) {
    console.warn("Error fetching my recipes", e);
  }

  window.scrollTo(0, 0);
};

getData();
</script>

<template>
  <h1 class="text-3xl font-bold text-center m-3">Recipes Feedback</h1>
  <div v-if="loading">
    <div role="status" class="flex justify-center items-center my-2">
      <img
        src="/assets/loader.svg"
        alt="Recipe loader"
        class="w-9 h-9 animate-spin"
      />
      <span class="sr-only">Loading...</span>
    </div>
  </div>
  <div class="flex flex-col gap-2">
    <div
      v-for="review in reviews"
      class="p-3 border-2 border-concrete-400 rounded-md bg-concrete-50 shadow-[2px_2px_#00000082]"
    >
      <div class="flex flex-col">
        <div class="font-bold text-xl">
          <RouterLink target="_blank" :to="`/recipes/${review.recipe.id}`">
            {{ review.recipe.name }}
          </RouterLink>
        </div>
        <div>
          By
          <span class="font-medium">
            {{ review.user.name }}
          </span>
          on
          <span class="font-medium">
            {{ dateWithTime(review.dateCreated) }}
          </span>
        </div>
        <StarRating :set-rating="review.rating / 2" />
        <div>
          {{ review.content }}
        </div>
      </div>
    </div>
  </div>
</template>

<style scoped></style>
