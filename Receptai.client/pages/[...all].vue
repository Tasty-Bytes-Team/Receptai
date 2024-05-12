<script setup lang="ts">
import EmptyListInformation from "@/components/EmptyListInformation.vue";
</script>

<template>
  <div>
    <h1 class="font-bold text-3xl text-center">Whoops! Looks like we missed something</h1>
    <EmptyListInformation
      description="It seems the page you're looking for got lost in the oven (or maybe it never existed). Don't worry, these things happen!"
      button-text="Home page"
      @button-click="navigateTo('/')"
    />
  </div>
</template>

<style scoped></style>
