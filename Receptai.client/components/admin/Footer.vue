<script setup lang="ts">
const currYear: Date = new Date();
</script>

<template>
  <footer>
    <div class="p-3 bg-[#e2e2e2]">
      <div class="max-w-screen-lg m-auto">
        <span class="font-medium text-sm">KTU, Programų sistemų inžinerija</span>
        <br />
        <span class="font-light text-sm">© {{ currYear.getUTCFullYear() }} Tasty Bytes. All rights
          reserved.</span>
      </div>
    </div>
  </footer>
</template>

<style lang="scss" scoped></style>
