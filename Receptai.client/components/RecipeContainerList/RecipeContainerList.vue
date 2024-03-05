<script setup>
import RecipeContainer from "@/components/RecipeContainerComponent/RecipeContainerComponent.vue";
import axios from 'axios'

const recipeList = ref(null);

axios.get('/api/v1/recipe/list').then(res => {
  recipeList.value = res.data
});
</script>

<template>
  <div v-if="recipeList" class="flex flex-wrap">
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
  <div v-else>Loading...</div>
</template>

<style lang="scss" scoped></style>
