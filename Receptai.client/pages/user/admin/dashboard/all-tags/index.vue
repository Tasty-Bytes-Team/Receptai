<template>
  <h1 class="text-3xl font-bold text-center m-3">All Tags</h1>
  <div>
    <TagsContainer />
  </div>
</template>

<script setup lang="ts">
import axios from "axios";
import type { UserCookie, Tag } from "@/typescript/types";
import TagsContainer from "@/components/admin/AllTags/TagsContainer.vue";

definePageMeta({
  layout: "admin",
  middleware: "admin",
});
</script>

<style scoped></style>
