<script setup lang="ts">
    defineProps<{
        color: string;
    }>()
</script>

<template>
  <button
    class="w-full my-2 px-4 py-2 text-sm rounded-sm font-medium transition-colors duration-200"
    :class="color === 'green' ? 'bg-[#7aff9c] hover:bg-[#51ff71]' : 'bg-[#f3fd86] hover:bg-[#f2ff64]'"
    type="button"
    @click="$emit('add')"
  >
    Add new step
  </button>
</template>

<style scoped></style>
