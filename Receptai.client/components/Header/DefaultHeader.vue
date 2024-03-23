<script setup lang="ts">
import UserBanner from "./components/UserBanner.vue";
import Logo from "./components/Logo.vue";

interface Navigation {
  to: string;
  title: string;
}

defineProps<{
  headerNav: Navigation[];
}>();
</script>

<template>
  <div
    class="sm:flex hidden max-w-screen-lg m-auto px-3 items-center justify-between"
  >
    <div class="flex items-center justify-between">
      <Logo />
      <div class="w-full justify-center gap-6 sm:flex hidden">
        <NuxtLink
          v-for="nav in headerNav"
          :to="nav.to"
          class="hover:underline"
          >{{ nav.title }}</NuxtLink
        >
      </div>
    </div>
    <UserBanner />
  </div>
</template>

<style scoped></style>
