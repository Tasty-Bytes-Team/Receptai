<script setup lang="ts">
import UserBanner from "./components/UserBanner.vue";
import Logo from "./components/Logo.vue";
import { routeLocationKey } from "vue-router";

interface Navigation {
  to: string;
  title: string;
  highlight?: boolean;
  onlyForMobile?: boolean;
}

defineProps<{
  headerNav: Navigation[];
  headerType?: "ADMIN" | "DEFAULT";
}>();
const route = useRoute();

const updateHref = (): String => {
  return window.location.href.toString().split(window.location.host)[1];
};

const showMobileMenu = ref(false);
const windowHref = ref<String>(updateHref());

watch(
  () => route.fullPath,
  () => {
    showMobileMenu.value = false;
    windowHref.value = updateHref();
  }
);
</script>

<template>
  <div
    class="sm:hidden max-w-screen-lg m-auto px-3 flex items-center justify-between"
  >
    <div class="flex-1">
      <Icon
        @click="showMobileMenu = !showMobileMenu"
        name="material-symbols:menu-rounded"
        class="transition-all duration-150 hover:bg-gray-200 hover:ring-4 hover:ring-gray-200 hover:rounded-sm outline-none hover:z-10 cursor-pointer"
        size="26px"
        color="black"
      />
    </div>
    <Logo class="!m-0" />
    <div class="flex-1">
      <div class="flex gap-3 items-center justify-end">
        <NuxtLink
          v-if="headerType === 'ADMIN'"
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
  </div>
  <div v-show="showMobileMenu" class="sm:hidden w-full">
    <ul
      class="font-medium flex flex-col p-3 border border-concrete-100 rounded-sm bg-concrete-50"
    >
      <li v-for="nav in headerNav">
        <a
          @click="navigateTo(nav.to)"
          class="transition-all duration-100 block py-2 px-3 text-concrete-800 hover:text-concrete-950 rounded-sm hover:bg-concrete-100 cursor-pointer"
          :class="
            windowHref === nav.to
              ? 'border-r-8 !border-primary !bg-whiskey-100'
              : null
          "
        >
          {{ nav.title }}
        </a>
      </li>
    </ul>
  </div>
</template>

<style scoped></style>
