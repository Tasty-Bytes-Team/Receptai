<script setup lang="ts">
import axios from "axios";
import StarRaiting from "../components/StarRaiting.vue";

interface Feedback {
  content: string;
  rating: number;
  user: User;
}

interface User {
  id: number | null;
  name: string | null;
  email: string | null;
  avatarUrl: string | null;
}

interface UserCookie {
  token: string;
  expiresIn: number;
  user: User;
}

const config = useRuntimeConfig();
const TastyBytes_user = useCookie<UserCookie | null>("TastyBytes_user");

const props = defineProps<{
  recipeId: string;
}>();

const feedback = ref<Feedback | null>(null);

const getFeedback = async () => {
  try {
    await axios
      .get(`${config.public.baseURL}/api/v1/feedback/list/${props.recipeId}`)
      .then((res) => {
        console.log(res.data.elements);
        feedback.value = res.data.elements;
      });
  } catch (e) {
    console.error("Error fetching recipe", e);
  }
};
</script>

<template>
  <div
    class="flex flex-col justify-center border-2 border-concrete-400 bg-white p-4"
  >
    <h3 class="font-semibold text-lg mb-2">Made it? Leave a review!</h3>
    <form
      v-if="TastyBytes_user"
      class="flex flex-col w-full items-center gap-2"
    >
      <div class="flex flex-col w-full m-auto justify-center">
        <label class="font-bold">Raiting</label>
        <StarRaiting />
      </div>
      <div class="flex flex-col w-full m-auto justify-center">
        <label class="font-bold">Comment</label>
        <textarea
          placeholder="Write your experience about making the recipe..."
          class="border-2 border-black rounded-sm p-2 w-full"
        ></textarea>
      </div>
      <button
        class="bg-whiskey-300 w-fit px-3 py-1.5 rounded-sm text-md font-medium"
      >
        Post review
      </button>
    </form>
    <div v-else class="flex flex-col items-center text-center gap-2">
      <p>
        Sorry, you can only write a review if you are logged in. You can do that
        by pressing the button below.
      </p>
      <button
        @click="navigateTo('/user/login')"
        class="p-1 px-3 rounded-sm text-black font-medium bg-chilean-heath-200 hover:bg-chilean-heath-300 transition-colors duration-200"
      >
        Log in
      </button>
    </div>
  </div>
</template>

<style scoped></style>
