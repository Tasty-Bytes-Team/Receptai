<script setup lang="ts">
import RecipeContainerList from "@/components/RecipeContainerList/RecipeContainerList.vue";
import Banner from "@/components/Banner/Banner.vue";
import FeaturedBanner from "@/components/HomePage/FeaturedBanner.vue";
import HeadingWithParagraph from "@/components/HomePage/HeadingWithParagraph.vue";
import Categories from "@/components/HomePage/Categories.vue";
import CategorySuggestion from "@/components/HomePage/CategorySuggestion.vue";
</script>

<template>
  <div class="flex flex-col gap-4">
    <FeaturedBanner />
    <div class="flex flex-col gap-2">
      <HeadingWithParagraph heading="Don't Miss! Featured Recipes" paragraph="Get inspired by our carousel showcasing some of our most popular and delicious recipes. Dive into a world of culinary delights and discover your next favorite meal!" />
      <Banner />
    </div>
    <div class="flex flex-col gap-2">
      <CategorySuggestion id="6" heading="Craving a taste of the world?" description="Look no further than our Global Eats category! Explore a
          vibrant collection of recipes inspired by international cuisines. From
          the fiery curries of Thailand to the rich pastas of Italy, we offer a
          passport to deliciousness on your plate. Whether you're a seasoned
          foodie or a curious beginner, these recipes are designed to be
          accessible and exciting." />
    </div>
    <div class="flex flex-col gap-2">
      <HeadingWithParagraph heading="Explore by Category: Something for Every Craving" paragraph="Explore a variety of cuisines and dietary preferences with our four featured recipe categories. From quick and easy weeknight meals to vegetarian delights and decadent desserts, we have something for everyone." />
      <Categories />
    </div>
    <div class="flex flex-col gap-2">
      <HeadingWithParagraph heading="Fresh Inspiration: Our Newest Recipes" paragraph="Stay up-to-date with our latest culinary creations! Check out our eight newest recipes and discover exciting new flavors to tantalize your taste buds." />
      <RecipeContainerList />
    </div>
  </div>
</template>
