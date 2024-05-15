<script setup lang="ts">
defineProps<{
  model: string;
  label: string;
  placeholder: string;
  notReq?: boolean;
}>();
</script>

<template>
  <div class="w-full text-left">
    <label class="font-semibold text-sm">{{ label }}</label>
    <textarea
      :required="!notReq"
      :value="model"
      @input="$emit('update:model', ($event.target as HTMLInputElement).value)"
      :placeholder="placeholder"
      class="outline-none w-full p-2 px-3 placeholder:text-concrete-400 bg-concrete-50 rounded-sm border-2 border-concrete-400 transition-colors duration-150 focus:border-black"
    ></textarea>
  </div>
</template>

<style scoped></style>
