<script setup lang="ts">
const currYear: Date = new Date();

interface Link {
  name: string;
  link: string;
}

interface Navigation {
  name: string;
  links: Link[];
}

const NavigationTabs: Navigation[] = [
  {
    name: "Navigation",
    links: [
      {
        name: "Link 1",
        link: "/",
      },
      {
        name: "Link 2",
        link: "/",
      },
    ],
  },
  {
    name: "Navigation",
    links: [
      {
        name: "Link 1",
        link: "/",
      },
      {
        name: "Link 2",
        link: "/",
      },
    ],
  },
  {
    name: "Navigation",
    links: [
      {
        name: "Link 1",
        link: "/",
      },
      {
        name: "Link 2",
        link: "/",
      },
    ],
  },
  {
    name: "Navigation",
    links: [
      {
        name: "Link 1",
        link: "/",
      },
      {
        name: "Link 2",
        link: "/",
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
