<script setup>
definePageMeta({
  layout: "admin",
  middleware: [
    function (to, from){
      return navigateTo('/')
    }
  ]
});

</script>

<template>
  <div>Dashboard</div>
</template>

<style lang="scss" scoped></style>
