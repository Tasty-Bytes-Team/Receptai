<script setup lang="ts">
import axios from "axios";
import UserReview from "./components/UserReview.vue";
import AllReviews from "./components/AllReviews.vue";

interface Feedback {
  content: string;
  rating: number;
  user: User;
}

interface User {
  id: number;
  name: string;
  avatarUrl: string | null;
}

const config = useRuntimeConfig();

const props = defineProps<{
  recipeId: string;
}>();

const feedbackArray = ref<Feedback[] | null>(null);

const getFeedback = async () => {
  try {
    await axios
      .get(`${config.public.baseURL}/api/v1/feedback/list/${props.recipeId}`)
      .then((res) => {
        console.log(res.data.elements);
        feedbackArray.value = res.data.elements;
      });
  } catch (e) {
    console.error("Error fetching recipe", e);
  }
};

await getFeedback();
</script>

<template>
  <div
    class="max-w-screen-lg m-auto my-2 p-4 border-2 border-concrete-300 rounded-sm bg-concrete-100"
  >
    <h3 class="font-semibold text-xl mb-3">Reviews</h3>
    <div class="flex flex-col gap-2 w-full">
      <UserReview :recipeId="recipeId" @new-review="getFeedback" />
      <AllReviews :feedbackArray />
    </div>
  </div>
</template>

<style scoped></style>
