<script setup lang="ts">
import UserBanner from "./components/UserBanner.vue";
import Logo from "./components/Logo.vue";

interface Navigation {
  to: string;
  title: string;
  highlight?: boolean;
  onlyForMobile?: boolean;
}

defineProps<{
  headerNav: Navigation[];
  headerType?: "ADMIN" | "DEFAULT";
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
    <div class="flex gap-4 items-center">
      <NuxtLink v-if="headerType === 'ADMIN'" to="/user/dashboard/my-recipes/create">

        <button
          class="p-1 px-3 rounded-sm text-black font-medium bg-chilean-heath-200 hover:bg-chilean-heath-300 transition-colors duration-200"
        >
          Create
        </button>
      </NuxtLink>
      <UserBanner />
    </div>
  </div>
</template>

<style scoped></style>
