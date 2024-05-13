<script setup lang="ts">
import Header from "@/components/Header/Header.vue";
import Footer from "@/components/Footer.vue";
import type { HeaderNavigation } from "@/typescript/types";

const headerNav: Array<HeaderNavigation> = [
  {
    to: "/about",
    title: "About",
  },
  {
    to: "/recipes",
    title: "Recipes",
  },
  {
    to: "/recipe-category",
    title: "Categories",
  },
];
</script>

<template>
  <Header :show-create="false" :show-search="true" :header-nav="headerNav" />
  <div class="max-w-screen-lg m-auto my-5 px-2">
    <slot />
  </div>
  <Footer />
</template>
