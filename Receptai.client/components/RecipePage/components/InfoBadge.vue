<script setup lang="ts">
defineProps<{
  text: string;
  info: string;
  icon: string;
}>();
</script>

<template>
  <div
    class="border-2 border-black rounded-md m-2 p-1 bg-[#f4f4f4] hover:bg-[#e9e9e9] transition-colors duration-200"
  >
    <Icon :name="icon" color="black" size="18px" />
    {{ text }} <b>{{ info }}</b>
  </div>
</template>

<style lang="scss" scoped></style>
