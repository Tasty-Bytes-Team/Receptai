<script setup lang="ts">
import MobileHeader from "./MobileHeader.vue";
import DefaultHeader from "./DefaultHeader.vue";

interface Navigation {
  to: string;
  title: string;
}

defineProps<{
  headerType?: "ADMIN" | "DEFAULT";
}>();

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

const adminHeaderNav: Array<Navigation> = [
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
    <MobileHeader :header-nav="headerType === 'ADMIN' ? adminHeaderNav : headerNav" />
    <DefaultHeader :header-nav="headerType === 'ADMIN' ? adminHeaderNav : headerNav" />
  </header>
</template>

<style lang="scss" scoped></style>
