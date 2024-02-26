<script setup>
import RecipeContainer from "@/components/RecipeContainerComponent/RecipeContainerComponent.vue";

const items = [
  {
    imageLink: "/_nuxt/assets/images/Lasagne-square-FS-79.webp",
    name: "Vegetarian lasagne",
    raiting: 3,
    about: "Make our easy vegetable lasagne using just a few ingredients. You can use ready-made tomato sauce and white sauce, or batch cook the sauces and freeze them.",
    link: "/recipes/1",
    category: "Vegetarian",
    categoryLink: "/recipes/veg",
    prepTime: 30,
  },
  {
    imageLink: "/_nuxt/assets/images/Lasagne-square-FS-79.webp",
    name: "Vegetarian lasagne",
    raiting: 3,
    about: "Make our easy vegetable lasagne using just a few ingredients. You can use ready-made tomato sauce and white sauce, or batch cook the sauces and freeze them.",
    link: "/recipes/1",
    category: "Vegetarian",
    categoryLink: "/recipes/veg",
    prepTime: 30,
  },
  {
    imageLink: "/_nuxt/assets/images/Lasagne-square-FS-79.webp",
    name: "Vegetarian lasagne",
    raiting: 3,
    about: "Make our easy vegetable lasagne using just a few ingredients. You can use ready-made tomato sauce and white sauce, or batch cook the sauces and freeze them.",
    link: "/recipes/1",
    category: "Vegetarian",
    categoryLink: "/recipes/veg",
    prepTime: 30,
  },
  {
    imageLink: "/_nuxt/assets/images/Lasagne-square-FS-79.webp",
    name: "Vegetarian lasagne",
    raiting: 3,
    about: "Make our easy vegetable lasagne using just a few ingredients. You can use ready-made tomato sauce and white sauce, or batch cook the sauces and freeze them.",
    link: "/recipes/1",
    category: "Vegetarian",
    categoryLink: "/recipes/veg",
    prepTime: 30,
  },
  {
    imageLink: "/_nuxt/assets/images/Lasagne-square-FS-79.webp",
    name: "Vegetarian lasagne",
    raiting: 3,
    about: "Make our easy vegetable lasagne using just a few ingredients. You can use ready-made tomato sauce and white sauce, or batch cook the sauces and freeze them.",
    link: "/recipes/1",
    category: "Vegetarian",
    categoryLink: "/recipes/veg",
    prepTime: 30,
  }
];
</script>

<template>
  <div class="flex flex-wrap">
    <RecipeContainer
      v-for="item in items"
      :imageLink="item.imageLink"
      :name="item.name"
      :raiting="item.raiting"
      :about="item.about"
      :link="item.link"
      :category="item.category"
      :categoryLink="item.categoryLink"
      :prepTime="item.prepTime"
    />
  </div>
</template>

<style lang="scss" scoped></style>
