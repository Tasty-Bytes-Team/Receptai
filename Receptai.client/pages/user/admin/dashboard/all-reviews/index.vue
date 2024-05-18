<script setup lang="ts">
import ReviewsContainer from '@/components/admin/AllReviews/ReviewsContainer.vue';

definePageMeta({
  layout: "admin",
  middleware: "admin",
});
</script>

<template>
  <h1 class="text-3xl font-bold text-center m-3">All Reviews</h1>
  <div>
    <ReviewsContainer />
  </div>
</template>

<style scoped></style>
