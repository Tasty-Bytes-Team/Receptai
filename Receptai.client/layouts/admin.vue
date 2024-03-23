<script setup lang="ts">
import Header from "@/components/Header/Header.vue";
import Footer from "@/components/admin/Footer.vue";
</script>

<template>
  <Header header-type="ADMIN" />
  <div class="max-w-screen-lg m-auto my-5 px-2">
    <slot />
  </div>
  <Footer />
</template>
