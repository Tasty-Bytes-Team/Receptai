<template>
  <h1 class="text-3xl font-bold text-center m-3">All Tags</h1>
  <div class="text-center mb-2">
    <RouterLink to="/user/admin/dashboard/all-tags/create">
      <button
        class="p-1.5 px-5 text-lg rounded-sm text-black font-medium bg-chilean-heath-200 hover:bg-chilean-heath-300 transition-colors duration-200"
      >
        Create tag
      </button>
    </RouterLink>
  </div>
  <div>
    <TagsContainer />
  </div>
</template>

<script setup lang="ts">
import TagsContainer from "@/components/admin/AllTags/TagsContainer.vue";

definePageMeta({
  layout: "admin",
  middleware: "admin",
});
</script>

<style scoped></style>
