<script setup lang="ts">
import Header from "@/components/Header/Header.vue";
import Footer from "@/components/Footer.vue";
</script>

<template>
  <Header />
  <slot />
  <Footer />
</template>

<style lang="scss" scoped></style>
