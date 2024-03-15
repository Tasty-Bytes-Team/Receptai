<script setup lang="ts">
import { store } from "@/store/store.js";

definePageMeta({
  layout: "admin",
  middleware: "auth",
});
</script>

<template>
  <div>Dashboard</div>
</template>

<style lang="scss" scoped></style>
