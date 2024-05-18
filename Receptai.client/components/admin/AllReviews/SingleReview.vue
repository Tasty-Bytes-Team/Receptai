<template>
  <tr class="border-b-2 border-concrete-200">
    <td class="text-center p-3">
      {{ review.id }}
    </td>
    <td class="text-center p-3 font-semibold">
      {{ review.user.name }} (ID: {{ review.user.id }})
    </td>
    <td class="text-center p-3">
      <StarRating :set-rating="reviewStars" />
      ({{ reviewStars }} out of 5)
    </td>
    <td class="text-center p-3">
      {{ review.content }}
    </td>
    <td class="text-center p-3">
      {{ dateWithTime(review.dateCreated) }}
    </td>
    <td class="text-center p-3">
      <button
        @click="$emit('delete', review.id)"
        title="Delete recipe"
        class="cursor-pointer"
      >
        <Icon
          name="material-symbols:delete-outline"
          class="transition-all duration-150 hover:bg-gray-200 hover:ring-4 hover:ring-gray-200 hover:rounded-sm outline-none hover:z-10"
          size="24px"
          color="black"
        />
      </button>
    </td>
  </tr>
</template>

<script setup lang="ts">
import type { ReviewInformation } from "@/typescript/types";
import dateWithTime from "@/typescript/dateFormating";
import StarRating from "@/components/Feedback/components/StarRating.vue";

const props = defineProps<{
  review: ReviewInformation;
}>();

const reviewStars = computed(() => props.review.rating / 2);
</script>

<style scoped></style>
