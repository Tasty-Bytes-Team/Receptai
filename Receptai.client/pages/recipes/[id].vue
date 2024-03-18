<script setup lang="ts">
import RecipePage from "@/components/RecipePage/RecipePage.vue";

definePageMeta({
  layout: "empty",
});

const route = useRoute();
</script>

<template>
  <RecipePage :id="route.params.id" />
</template>

<style lang="scss" scoped></style>
