<script setup lang="ts">
const windowWidth = ref(window.innerWidth);

window.addEventListener("resize", () => {
  windowWidth.value = window.innerWidth;
});
</script>

<template>
  <div class="py-4 mr-5">
    <NuxtLink to="/">
      <h1 class="font-semibold sm:text-3xl text-2xl whitespace-nowrap">
        {{ windowWidth > 380 ? "Tasty Bytes" : "TB" }}
      </h1>
    </NuxtLink>
  </div>
</template>

<style scoped></style>
