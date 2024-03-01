<script setup>
const currYear = new Date();
</script>

<template>
  <footer>
    <div id="app-footer__bottom-main" class="p-3 bg-[#e2e2e2]">
      <div id="app-footer__bottom" class="max-w-screen-lg m-auto">
        <span id="app-footer__name" class="font-medium text-sm">KTU, Programų sistemų inžinerija</span>
        <br />
        <span id="app-footer__copyright" class="font-light text-sm"
          >© {{ currYear.getUTCFullYear() }} Tasty Bytes. All rights reserved.</span
        >
      </div>
    </div>
  </footer>
</template>

<style lang="scss" scoped></style>
