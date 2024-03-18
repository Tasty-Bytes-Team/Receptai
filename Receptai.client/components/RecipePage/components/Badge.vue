<script setup lang="ts">
defineProps<{
  text: string;
  icon: string;
}>();
</script>

<template>
  <div
    class="border-2 border-black rounded-md m-2 p-1 bg-[#f4f4f4] hover:bg-[#e9e9e9] transition-colors duration-200"
  >
    <Icon :name="icon" class="!w-auto !h-auto" color="black" size="18px" />
    {{ text }}
  </div>
</template>

<style lang="scss" scoped></style>
