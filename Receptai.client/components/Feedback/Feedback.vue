<script setup lang="ts">
import axios from "axios";
import StarRaiting from "./components/StarRaiting.vue";
import UserReview from "./components/UserReview.vue";

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
    <UserReview :recipeId="recipeId" />
    <div class="max-w-lg m-auto flex flex-col gap-2">
      <div
        v-for="feedback in feedbackArray"
        class="p-3 border-2 border-black rounded-md bg-gray-100"
      >
        <p>{{ feedback.user.name }}</p>
        <StarRaiting :setRaiting="feedback.rating / 2" />
        <h3>{{ feedback.content }}</h3>
      </div>
    </div>
    <StarRating />
  </div>
</template>

<style scoped></style>
