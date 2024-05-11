<script setup lang="ts">
import axios from "axios";
import type { UserCookie } from "@/typescript/types";
import { addNotification } from "@/store/store";
import StarRating from "../components/StarRating.vue";
import EmptyListInformation from "@/components/EmptyListInformation.vue";

const config = useRuntimeConfig();
const TastyBytes_user = useCookie<UserCookie | null>("TastyBytes_user");
const emit = defineEmits(["newReview"]);

const props = defineProps<{
  recipeId: string;
}>();

const rating = ref<number | null>(null);
const content = ref<string | null>(null);

const onSubmit = async () => {
  if (!TastyBytes_user.value) {
    addNotification(
      "Sorry, you have to be logged in to leave a review.",
      "Error"
    );
    return;
  }

  if (!rating.value || !content.value) {
    addNotification("Raiting and comment fields are required.", "Error");
    return;
  }

  try {
    await axios.post(
      `${config.public.baseURL}/api/v1/feedback/leave/${props.recipeId}`,
      { rating: rating.value * 2, content: content.value },
      {
        headers: { Authorization: `Bearer ${TastyBytes_user.value.token}` },
      }
    );
    addNotification("Your review has been added successfully.", "Success");

    //Reset fields
    rating.value = null;
    content.value = null;

    //Emit event to update review list
    emit("newReview");
  } catch (e) {
    console.error("Error fetching recipe", e);
    if (e instanceof Error) {
      if (e.message.search("has already left")) {
        addNotification(
          "Sorry, one user can only leave one review for the same recipe.",
          "Error"
        );
      } else {
        addNotification(
          "Sorry, something went wrong. Please try again.",
          "Error"
        );
      }
    }
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
      @submit.prevent="onSubmit"
      class="flex flex-col w-full items-center gap-2"
    >
      <div class="flex flex-col w-full m-auto justify-center">
        <label class="font-bold">Rating</label>
        <StarRating v-model="rating" />
      </div>
      <div class="flex flex-col w-full m-auto justify-center">
        <label class="font-bold">Comment</label>
        <textarea
          v-model="content"
          placeholder="Write your experience about making the recipe..."
          class="border-2 border-concrete-400 focus-visible:border-black rounded-sm p-2 w-full outline-none"
          required
        ></textarea>
      </div>
      <button
        type="submit"
        class="bg-whiskey-200 hover:bg-whiskey-300 w-fit px-3 py-1.5 rounded-sm text-md font-medium transition-colors duration-100"
      >
        Post review
      </button>
    </form>
    <EmptyListInformation
      v-else
      description="Sorry, you can only write a review if you are logged in. You can do that by pressing the button below."
      button-text="Log in"
      @button-click="navigateTo('/user/login')"
    />
  </div>
</template>

<style scoped></style>
