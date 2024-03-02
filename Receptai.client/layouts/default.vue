<script setup>
import Header from "@/components/Header/Header.vue";
import Footer from "@/components/Footer.vue";
</script>

<template>
  <Header />
  <div class="max-w-screen-lg m-auto my-5 px-2">
    <slot />
  </div>
  <Footer />
</template>