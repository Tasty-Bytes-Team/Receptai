<script setup lang="ts">
import Header from "@/components/Header/Header.vue";
import Footer from "@/components/Footer.vue";
import type { HeaderNavigation } from "@/typescript/types";

const headerNav: Array<HeaderNavigation> = [
  {
    to: "/about-us",
    title: "About us",
  },
  {
    to: "/recipes",
    title: "Recipes",
  },
  {
    to: "/categories",
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
