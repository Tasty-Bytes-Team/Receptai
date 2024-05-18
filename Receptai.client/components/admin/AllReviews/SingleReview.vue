<template>
  <tr class="border-b-2 border-concrete-200">
    <td class="text-center p-3">
      {{ review.user.name }} (ID: {{ review.user.id }})
    </td>
    <td class="text-center p-3">
      <StarRating :set-rating="review.rating / 2" />
      ({{ review.rating / 2 }} out of 5)
    </td>
    <td class="text-center p-3">
      {{ review.content }}
    </td>
    <td class="text-center p-3">
      {{ dateWithTime(review.dateCreated) }}
    </td>
  </tr>
</template>

<script setup lang="ts">
import type { ReviewInformation } from "@/typescript/types";
import dateWithTime from "@/typescript/dateFormating";
import StarRating from "@/components/Feedback/components/StarRating.vue";

defineProps<{
  review: ReviewInformation;
}>();
</script>

<style scoped></style>
