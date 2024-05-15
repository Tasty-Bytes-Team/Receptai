<script setup lang="ts">
import Header from "@/components/Header/Header.vue";
import Footer from "@/components/user/Footer.vue";
import type { HeaderNavigation } from "@/typescript/types";

const headerNav: Array<HeaderNavigation> = [
  {
    to: "/user/admin/dashboard",
    title: "Admin dashboard",
  },
  {
    to: "/user/admin/dashboard/all-recipes",
    title: "All recipes",
  },
];
</script>

<template>
  <Header :show-create="false" :show-search="false" :header-nav="headerNav" />
  <div class="max-w-screen-lg m-auto my-5 px-2">
    <slot></slot>
  </div>
  <Footer />
</template>
