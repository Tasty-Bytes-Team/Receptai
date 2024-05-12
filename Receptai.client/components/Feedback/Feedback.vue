<script setup lang="ts">
import axios from "axios";
import type { Feedback } from "@/typescript/types";
import UserReview from "./components/UserReview.vue";
import AllReviews from "./components/AllReviews.vue";
import Pagination from "@/components/Pagination/Pagination.vue";

const config = useRuntimeConfig();

const props = defineProps<{
  recipeId: string;
}>();

const feedbackArray = ref<Feedback[] | null>(null);

const loading = ref(true);

//Pagination
const siblings = 2;

const pageNumber = ref(0);
const totalElements = ref(0);
const elementsPerPage = ref(0);
const currentElementCount = ref(0);
const totalPages = ref(0);

const getFeedback = async () => {
  loading.value = true;

  try {
    await axios
      .get(
        `${config.public.baseURL}/api/v1/feedback/list/${props.recipeId}?page=${pageNumber.value}`
      )
      .then((res) => {
        feedbackArray.value = res.data.elements;
        totalPages.value = res.data.totalPageCount;
        totalElements.value = res.data.totalElementCount;
        elementsPerPage.value = res.data.elementsPerPage;
        currentElementCount.value = res.data.currentElementCount;
      });
  } catch (e) {
    console.error("Error fetching recipe", e);
  } finally {
    loading.value = false;
  }
};

getFeedback();
</script>

<template>
  <div
    class="max-w-screen-lg m-auto my-2 p-4 border-2 border-concrete-300 rounded-sm bg-concrete-100"
  >
    <h3 class="font-semibold text-2xl mb-3">Reviews</h3>
    <div class="flex flex-col gap-2 w-full">
      <UserReview :recipeId="recipeId" @new-review="getFeedback" />
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
      <div v-else>
        <AllReviews :feedbackArray />
        <div class="w-full text-center" v-if="totalPages > 0">
          <Pagination
            @change="getFeedback"
            v-model="pageNumber"
            :totalPages
            :siblings
          />
        </div>
      </div>
    </div>
  </div>
</template>

<style scoped></style>
