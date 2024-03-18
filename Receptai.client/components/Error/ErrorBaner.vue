<script setup lang="ts">
    defineProps<{
        errorText: string;
    }>()
</script>

<template>
    <div class="border-2 border-red-600 my-3 p-2 text-red-600">
      <p>{{ errorText }}</p>
    </div>
</template>

<style scoped>

</style>