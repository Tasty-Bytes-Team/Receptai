<script setup lang="ts">
import type { Feedback } from "@/typescript/types";
import dateWithTime from "@/typescript/dateFormating";
import StarRaiting from "../components/StarRating.vue";

defineProps<{
  feedbackArray: Feedback[] | null;
}>();
</script>

<template>
  <div
    class="m-auto w-full p-4 flex flex-col gap-2 bg-white border-concrete-400 border-2"
  >
    <h3 class="font-semibold text-lg mb-2">All of the reviews left by users</h3>
    <div
      v-if="feedbackArray && feedbackArray.length > 0"
      class="flex flex-col gap-2"
    >
      <div
        v-for="(feedback, index) in feedbackArray"
        :key="index"
        class="p-3 border-2 border-concrete-400 rounded-md bg-concrete-50 shadow-[2px_2px_#00000082]"
      >
        <p class="font-bold">
          {{ feedback.user.name }}
          <span class="font-normal"
            >– {{ dateWithTime(feedback.dateCreated) }}</span
          >
        </p>
        <StarRaiting :setRating="feedback.rating / 2" />
        <h3>{{ feedback.content }}</h3>
      </div>
    </div>
    <div v-else class="text-center font-normal my-2">
      <p>There aren't any reviews yet. Start by writing your own!</p>
    </div>
  </div>
</template>

<style scoped></style>
