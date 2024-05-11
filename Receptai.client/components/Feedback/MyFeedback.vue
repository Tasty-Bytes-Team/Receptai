<script setup lang="ts">
import type { Review } from "@/typescript/types";
import dateWithTime from "@/typescript/dateFormating";
import StarRating from "@/components/Feedback/components/StarRating.vue";

defineProps<{
  review: Review;
}>();
</script>

<template>
  <div
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
</template>

<style scoped></style>
