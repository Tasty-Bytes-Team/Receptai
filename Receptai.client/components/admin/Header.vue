<script setup lang="ts">
import UserBannerVue from "@/components/Header/UserBanner.vue";

interface Navigation {
  to: string;
  title: string;
}

const headerNav: Array<Navigation> = [
  {
    to: "/user/dashboard",
    title: "Dashboard",
  },
  {
    to: "/user/dashboard/my-recipes",
    title: "My Recipes",
  },
];
</script>

<template>
  <header class="bg-white shadow-md">
    <div class="max-w-screen-lg m-auto px-3 flex items-center justify-between">
      <div class="flex items-center justify-between">
        <div class="py-4">
          <NuxtLink to="/">
            <h1 class="font-semibold sm:text-3xl text-2xl sm:w-40 w-36">
              Tasty Bytes
            </h1>
          </NuxtLink>
        </div>
        <div
          class="w-full justify-center gap-6 ml-6 items-center sm:flex hidden"
        >
          <NuxtLink
            v-for="nav in headerNav"
            :to="nav.to"
            class="hover:underline"
            >{{ nav.title }}</NuxtLink
          >
        </div>
      </div>
      <div class="flex gap-4 items-center">
        <NuxtLink to="/user/dashboard/my-recipes/create">
          <button
            class="p-1 px-3 rounded-sm text-black font-medium bg-chilean-heath-200 hover:bg-chilean-heath-300 transition-colors duration-200"
          >
            Create
          </button>
        </NuxtLink>
        <UserBannerVue />
      </div>
    </div>
  </header>
</template>

<style lang="scss" scoped></style>
