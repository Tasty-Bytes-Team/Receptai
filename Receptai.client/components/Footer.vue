<script setup>
const currYear = new Date();

const NavigationTabs = [
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
  }
];
</script>

<template>
  <footer>
    <div id="app-footer__top-main" class="bg-[#f3f3f3]">
      <div id="app-footer__all_navigation" class="max-w-screen-lg m-auto px-3 py-6 flex justify-evenly">
        <div id="app-footer__one_navigation" v-for="navigation in NavigationTabs" :key="navigation.name">
          <h5 id="app-footer__navigation_name" class="font-bold text-lg">{{ navigation.name }}</h5>
          <ul>
            <li id="app-footer__navigation_link" v-for="link in navigation.links" :key="link.name">
              <NuxtLink :to="link.link">{{ link.name }}</NuxtLink>
            </li>
          </ul>
        </div>
      </div>
    </div>
    <div id="app-footer__bottom-main" class="p-3 bg-[#e2e2e2]">
      <div id="app-footer__bottom" class="max-w-screen-lg m-auto">
        <span id="app-footer__name" class="font-medium text-sm">KTU, Programų sistemų inžinerija</span>
        <br />
        <span id="app-footer__copyright" class="font-light text-sm"
          >© {{ currYear.getUTCFullYear() }} Tasty Bytes. Visos teisės
          saugomos.</span
        >
      </div>
    </div>
  </footer>
</template>

<style lang="scss" scoped></style>
