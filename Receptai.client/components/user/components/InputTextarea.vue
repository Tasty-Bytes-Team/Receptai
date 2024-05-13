<script setup lang="ts">
import { ErrorMessage, Field } from "vee-validate";

defineProps<{
  name: string;
  label: string;
  placeholder: string;
}>();
</script>

<template>
<div>
        <div class="flex items-center gap-2">
          <label class="font-semibold text-sm">{{label}}</label>
          <ErrorMessage :name="name" class="text-red-600 text-sm" />
        </div>
        <Field
          :name="name"
          :placeholder="placeholder"
          as="textarea"
          class="outline-none w-full p-2 px-3 placeholder:text-concrete-400 bg-concrete-50 rounded-sm border-2 border-concrete-400 transition-colors duration-150 focus:border-black"
        />
      </div>
</template>

<style scoped>

</style>