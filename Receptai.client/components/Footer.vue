<script setup lang="ts">
import type { Link, Navigation } from '@/typescript/types';
const currYear: Date = new Date();

const NavigationTabs: Navigation[] = [
  {
    name: "Useful links",
    links: [
      {
        name: "About us",
        link: "/about",
      },
      {
        name: "All recipes",
        link: "/recipes",
      },
      {
        name: "All categories",
        link: "/categories",
      },
    ],
  },
  {
    name: "Popular categories",
    links: [
      {
        name: "Weeknight Wonders",
        link: "#",
      },
      {
        name: "Global Eats",
        link: "#",
      },
      {
        name: "Plant-Powered Plates",
        link: "#",
      },
    ],
  },
  {
    name: "Popular recipes",
    links: [
      {
        name: "Pizza Margherita",
        link: "#",
      },
      {
        name: "Oven-baked ribs",
        link: "#",
      },
      {
        name: "Best Hamburger Ever",
        link: "#",
      },
    ],
  },
];
</script>

<template>
  <footer>
    <div class="bg-concrete-100">
      <div
        class="max-w-screen-lg m-auto px-3 py-6 flex justify-evenly flex-wrap"
      >
        <div
          class="mx-4 my-2"
          v-for="navigation in NavigationTabs"
          :key="navigation.name"
        >
          <h5 class="font-bold text-lg">
            {{ navigation.name }}
          </h5>
          <ul>
            <li v-for="link in navigation.links" :key="link.name">
              <NuxtLink :to="link.link">{{ link.name }}</NuxtLink>
            </li>
          </ul>
        </div>
      </div>
    </div>
    <div class="p-3 bg-concrete-200">
      <div class="max-w-screen-lg m-auto">
        <span class="font-medium text-sm"
          >KTU, Programų sistemų inžinerija</span
        >
        <br />
        <span class="font-light text-sm"
          >© {{ currYear.getUTCFullYear() }} Tasty Bytes. All rights
          reserved.</span
        >
      </div>
    </div>
  </footer>
</template>

<style lang="scss" scoped></style>
