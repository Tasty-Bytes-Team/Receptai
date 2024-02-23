<script setup>
const headerNav = [
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
  <header id="app-header__main" class="bg-white shadow-md">
    <div
      id="app-header__left"
      class="max-w-screen-lg m-auto px-3 flex items-center justify-between"
    >
      <div id="app-header__logo" class="flex items-center justify-between">
        <div class="py-4">
          <NuxtLink to="/">
            <h1 class="font-semibold text-3xl w-40">Tasty Bytes</h1>
          </NuxtLink>
        </div>
        <div
          id="app-header__links"
          class="w-full flex justify-center gap-6 ml-6"
        >
          <NuxtLink v-for="nav in headerNav" :to="nav.to" class="hover:underline">{{
            nav.title
          }}</NuxtLink>
        </div>
      </div>
      <div id="app-header__right" class="text-right">Login / Register</div>
    </div>
  </header>
</template>

<style lang="scss" scoped></style>
