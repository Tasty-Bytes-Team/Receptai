<script setup lang="ts">
import { ErrorMessage, Field } from "vee-validate";

defineProps<{
  name: string;
  label: string;
  placeholder: string;
  type?: "number";
}>();
</script>

<template>
  <div v-if="type === 'number'">
    <div class="flex items-center gap-2">
      <label class="font-semibold text-sm">{{ label }}</label>
      <ErrorMessage :name="name" class="text-red-600 text-sm" />
    </div>
    <Field
      :name="name"
      :placeholder="placeholder"
      type="number"
      min="1"
      class="outline-none w-full p-2 px-3 placeholder:text-concrete-400 bg-concrete-50 rounded-sm border-2 border-concrete-400 transition-colors duration-150 focus:border-black"
    />
  </div>
  <div v-else>
    <div class="flex items-center gap-2">
      <label class="font-semibold text-sm">{{ label }}</label>
      <ErrorMessage :name="name" class="text-red-600 text-sm" />
    </div>
    <Field
      :name="name"
      :placeholder="placeholder"
      class="outline-none w-full p-2 px-3 placeholder:text-concrete-400 bg-concrete-50 rounded-sm border-2 border-concrete-400 transition-colors duration-150 focus:border-black"
    />
  </div>
</template>

<style scoped></style>
