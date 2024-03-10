<script setup lang="ts">
defineProps<{
  model: string | number | null;
  type?: string | undefined;
  label: string;
  placeholder: string;
}>();
</script>

<template>
  <div class="w-full text-left">
    <label class="font-semibold text-sm">{{ label }}</label>
    <input
      required
      v-if="!type && type !== 'number'"
      :value="model"
      @input="$emit('update:model', ($event.target as HTMLInputElement).value)"
      :placeholder="placeholder"
      class="outline-none w-full p-2 px-3 placeholder:text-concrete-400 bg-concrete-50 rounded-sm border-2 border-concrete-400 transition-colors duration-150 focus:border-black"
    />
    <input
      v-else
      required
      :value="model"
      @input="$emit('update:model', ($event.target as HTMLInputElement).value)"
      :placeholder="placeholder"
      type="number"
      min="0"
      class="outline-none w-full p-2 px-3 placeholder:text-concrete-400 bg-concrete-50 rounded-sm border-2 border-concrete-400 transition-colors duration-150 focus:border-black"
    />
  </div>
</template>

<style scoped></style>
