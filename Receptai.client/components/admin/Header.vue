<script setup>
import UserBannerVue from '@/components/Header/UserBanner.vue';

const headerNav = [
  {
    to: "#",
    title: "Dashboard",
  },
  {
    to: "#",
    title: "My Recipes",
  },
  {
    to: "#",
    title: "Reviews",
  },
];
</script>

<template>
  <header id="app-header__main" class="bg-white shadow-md">
    <div
      id="app-header__left"
      class="max-w-screen-lg m-auto px-3 flex items-center justify-between"
    >
      <div id="app-header__logo" class="flex items-center justify-between">
        <div class="py-4">
          <NuxtLink to="/">
            <h1 class="font-semibold sm:text-3xl text-2xl sm:w-40 w-36">
              Tasty Bytes
            </h1>
          </NuxtLink>
        </div>
        <div
          id="app-header__links"
          class="w-full justify-center gap-6 ml-6 sm:flex hidden"
        >
          <NuxtLink
            v-for="nav in headerNav"
            :to="nav.to"
            class="hover:underline"
            >{{ nav.title }}</NuxtLink
          >
        </div>
      </div>
      <UserBannerVue />
    </div>
  </header>
</template>

<style lang="scss" scoped></style>
