<script setup lang="ts">
import UserBanner from './UserBanner.vue';
import Logo from './Logo.vue'

interface Navigation {
  to: string;
  title: string;
}

const headerNav: Array<Navigation> = [
  {
    to: "/about",
    title: "About",
  },
  {
    to: "/recipes",
    title: "Recipes",
  },
];
</script>

<template>
  <header class="bg-white shadow-md">
    <div class="max-w-screen-lg m-auto px-3 flex items-center justify-between">
      <div class="flex items-center justify-between">
        <Logo />
        <div class="w-full justify-center gap-6 sm:flex hidden">
          <NuxtLink v-for="nav in headerNav" :to="nav.to" class="hover:underline">{{ nav.title }}</NuxtLink>
        </div>
      </div>
      <UserBanner />
    </div>
  </header>
</template>

<style lang="scss" scoped></style>
