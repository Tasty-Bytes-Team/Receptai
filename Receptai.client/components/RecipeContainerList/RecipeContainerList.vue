<script setup>
import RecipeContainer from "@/components/RecipeContainerComponent/RecipeContainerComponent.vue";

const { data: recipeList } = await useMyFetch("/api/v1/recipe/list");
</script>

<template>
  <div class="flex flex-wrap">
    <RecipeContainer
      v-for="item in recipeList"
      :imageLink="item.previewImage"
      :name="item.name"
      :raiting="item.raiting"
      :about="item.shortDescription"
      :link="`/recipes/${item.id}`"
      :category="item.categories.length > 0 ? item.categories[0].name : 'Empty'"
      :categoryLink="item.categories.length > 0 ? item.categories[0].link : '#'"
      :prepTime="item.minutesToPrepare"
    />
  </div>
</template>

<style lang="scss" scoped></style>
