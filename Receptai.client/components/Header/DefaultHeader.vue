<script setup lang="ts">
import type { HeaderNavigation } from "@/typescript/types";
import UserBanner from "./components/UserBanner.vue";
import Logo from "./components/Logo.vue";
import SearchForm from "@/components/SearchForm/SearchForm.vue";

defineProps<{
  headerNav: HeaderNavigation[];
  showSearch?: boolean;
  showCreate?: boolean;
}>();
const route = useRoute();

const updateHref = (): String => {
  return window.location.href.toString().split(window.location.host)[1];
};

const windowHref = ref<String>(updateHref());

watch(
  () => route.fullPath,
  () => {
    windowHref.value = updateHref();
  }
);
</script>

<template>
  <div
    class="md:flex hidden max-w-screen-lg m-auto px-3 items-center justify-between gap-6"
  >
    <div class="flex items-center justify-between w-full">
      <Logo />
      <div class="w-full gap-4 sm:flex hidden justify-start">
        <NuxtLink
          v-for="nav in headerNav"
          :to="nav.to"
          class="hover:underline whitespace-nowrap"
          :class="windowHref === nav.to ? 'underline' : null"
          >{{ nav.title }}</NuxtLink
        >
      </div>
    </div>
    <div class="flex gap-4 items-center w-full justify-end">
      <SearchForm v-if="showSearch" />
      <NuxtLink
        v-if="showCreate"
        to="/user/dashboard/my-recipes/create"
      >
        <button
          class="p-1 px-3 rounded-sm text-black font-medium bg-chilean-heath-200 hover:bg-chilean-heath-300 transition-colors duration-200"
        >
          Create
        </button>
      </NuxtLink>
      <UserBanner />
    </div>
  </div>
</template>

<style scoped></style>
