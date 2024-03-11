<script setup lang="ts">

import { store } from '@/store/store.js'

definePageMeta({
  layout: "admin",
  middleware: "auth",
});



</script>

<template>
  <button @click="store.text = 'Account created'; store.show = true">Show</button>
  <div>Dashboard</div>
</template>

<style lang="scss" scoped></style>
