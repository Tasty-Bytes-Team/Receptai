<script setup lang="ts">
import Notification from '@/components/Notification.vue';
</script>

<template>
  <NuxtLoadingIndicator class="!opacity-100" />
  <NuxtLayout>
    <NuxtPage />
  </NuxtLayout>

  <Notification />
</template>

<style lang="scss" scoped></style>
