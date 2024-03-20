<script setup lang="ts">
import Notification from "@/components/Notification.vue";
</script>

<template>
  <NuxtLoadingIndicator
    color="repeating-linear-gradient(to right,#debc93 0%,#c68249 50%,#995635 100%)"
    class="!opacity-100"
  />
  <NuxtLayout>
    <NuxtPage />
  </NuxtLayout>

  <Notification />
</template>

<style lang="scss" scoped></style>
