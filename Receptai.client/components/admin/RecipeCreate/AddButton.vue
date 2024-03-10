<script setup lang="ts">
    defineProps<{
        color: string;
    }>()
</script>

<template>
  <button
    class="w-full my-2 px-4 py-2 text-sm rounded-sm font-medium transition-colors duration-200"
    :class="color === 'green' ? 'bg-green-mist-200 hover:bg-green-mist-300' : 'bg-whiskey-200 hover:bg-whiskey-300'"
    type="button"
    @click="$emit('add')"
  >
    Add new step
  </button>
</template>

<style scoped></style>
