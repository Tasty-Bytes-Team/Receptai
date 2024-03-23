<script setup lang="ts">
import UserBanner from "./UserBanner.vue";
import Logo from "./Logo.vue";

interface Navigation {
  to: string;
  title: string;
}

const showMobileMenu = ref(false);

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
</script>

<template>
  <header class="bg-white shadow-md">
    <div
      class="sm:hidden max-w-screen-lg m-auto px-3 flex items-center justify-between"
    >
      <div class="w-10">
        <div>
          <Icon
            @click="showMobileMenu = !showMobileMenu"
            name="material-symbols:menu-rounded"
            class="transition-all duration-150 hover:bg-gray-200 hover:ring-4 hover:ring-gray-200 hover:rounded-sm outline-none hover:z-10 cursor-pointer"
            size="26px"
            color="black"
          />
        </div>
      </div>
      <div>
        <Logo class="!m-0" />
      </div>
      <div class="w-10">
        <UserBanner />
      </div>
    </div>
    <div v-show="showMobileMenu" class="sm:hidden w-full">
      <ul
        class="font-medium flex flex-col p-3 border border-concrete-100 rounded-sm bg-concrete-50"
      >
        <li v-for="nav in headerNav">
          <a
            @click="
              {
                navigateTo(nav.to);
                showMobileMenu = false;
              }
            "
            class="transition-all duration-100 block py-2 px-3 text-concrete-800 hover:text-concrete-950 rounded-sm hover:bg-concrete-100 cursor-pointer"
          >
            {{ nav.title }}
          </a>
        </li>
      </ul>
    </div>
    <div
      class="sm:flex hidden max-w-screen-lg m-auto px-3 items-center justify-between"
    >
      <div class="flex items-center justify-between">
        <Logo />
        <div class="w-full justify-center gap-6 sm:flex hidden">
          <NuxtLink
            v-for="nav in headerNav"
            :to="nav.to"
            class="hover:underline"
            >{{ nav.title }}</NuxtLink
          >
        </div>
      </div>
      <UserBanner />
    </div>
  </header>
</template>

<style lang="scss" scoped></style>
