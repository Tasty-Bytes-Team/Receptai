<script setup lang="ts">
import type { HeaderNavigation } from "@/typescript/types";
import MobileHeader from "./MobileHeader.vue";
import DefaultHeader from "./DefaultHeader.vue";

defineProps<{
  headerType?: "ADMIN" | "DEFAULT";
}>();

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

const adminHeaderNav: Array<HeaderNavigation> = [
  {
    to: "/user/dashboard",
    title: "Dashboard",
  },
  {
    to: "/user/dashboard/my-recipes",
    title: "My Recipes",
  },
  {
    to: "/user/dashboard/recipes-feedback",
    title: "My Recipes feedback",
  }
];
</script>

<template>
  <header class="bg-white shadow-md">
    <MobileHeader
      :header-type
      :header-nav="headerType === 'ADMIN' ? adminHeaderNav : headerNav"
    />
    <DefaultHeader
      :header-type
      :header-nav="headerType === 'ADMIN' ? adminHeaderNav : headerNav"
    />
  </header>
</template>

<style lang="scss" scoped></style>
