<script setup lang="ts">
import axios from "axios";
import type { UserCookie, Review } from "@/typescript/types";
import MyFeedback from "@/components/Feedback/MyFeedback.vue";
import Pagination from "@/components/Pagination/Pagination.vue";
import EmptyListInformation from "@/components/EmptyListInformation.vue";

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

const getFeedback = async () => {
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
      });
  } catch (e) {
    console.warn("Error fetching my recipes", e);
  } finally {
    loading.value = false;
  }

  window.scrollTo(0, 0);
};

getFeedback();
</script>

<template>
  <h1 class="text-3xl font-bold text-center m-3">Community Feedback</h1>
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
  <EmptyListInformation
    v-else-if="reviews && reviews.length <= 0"
    description="Looks like your recipes haven't gotten any reviews yet! Be the first to share your culinary creations and inspire others. In the meantime, why not check out some recipes and leave a review for someone else?"
    button-text="Recipes"
    @button-click="navigateTo('/recipes')"
  />
  <div v-else class="flex flex-col gap-2">
    <MyFeedback v-for="review in reviews" :review="review" />
    <div class="w-full text-center" v-if="totalPages > 0">
      <Pagination
        @change="getFeedback"
        v-model="pageNumber"
        :totalPages
        :siblings
      />
    </div>
  </div>
</template>

<style scoped></style>
