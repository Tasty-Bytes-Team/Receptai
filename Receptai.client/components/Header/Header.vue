<script setup lang="ts">
import type { HeaderNavigation } from "@/typescript/types";
import MobileHeader from "./MobileHeader.vue";
import DefaultHeader from "./DefaultHeader.vue";

defineProps<{
  showSearch?: boolean;
  showCreate?: boolean;
  headerNav: HeaderNavigation[];
}>();
</script>

<template>
  <header class="bg-white shadow-md">
    <MobileHeader
      :show-create-button="showCreate"
      :show-search-button="showSearch"
      :header-nav="headerNav"
    />
    <DefaultHeader
      :show-create="showCreate"
      :show-search="showSearch"
      :header-nav="headerNav"
    />
  </header>
</template>

<style lang="scss" scoped></style>
