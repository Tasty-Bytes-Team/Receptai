<script setup lang="ts">
defineProps<{
  model: string;
  label: string;
  placeholder: string;
}>();
</script>

<template>
  <div class="w-full text-left">
    <label class="font-semibold text-sm">{{ label }}</label>
    <textarea
      required
      :value="model"
      @input="$emit('update:model', ($event.target as HTMLInputElement).value)"
      :placeholder="placeholder"
      class="outline-none w-full p-2 px-3 placeholder:text-[#9a9a9a] bg-[#f9f9f9] rounded-sm border-2 border-[#9a9a9a] transition-colors duration-150 focus:border-black"
    ></textarea>
  </div>
</template>

<style scoped></style>
