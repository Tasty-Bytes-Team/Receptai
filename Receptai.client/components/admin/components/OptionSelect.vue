<script setup lang="ts">
import { ErrorMessage, Field } from "vee-validate";

interface Category {
  id: number;
  name: string;
  primary: boolean;
}

defineProps<{
  name: string;
  label: string;
  placeholder: string;
  categoryList: Category[] | null;
}>();
</script>

<template>
      <div class="w-full text-left">
        <div class="flex items-center gap-2">
          <label class="font-semibold text-sm">{{ label }}</label>
          <ErrorMessage :name="name" class="text-red-600 text-sm" />
        </div>
        <Field
          as="select"
          :name="name"
          class="outline-none w-full p-2 px-3 placeholder:text-concrete-400 bg-concrete-50 rounded-sm border-2 border-concrete-400 transition-colors duration-150 focus:border-black"
        >
          <option disabled value="">{{ placeholder }}</option>
          <option
            v-for="category in categoryList"
            :key="category.id"
            :value="category.id"
          >
            {{ category.name }}
          </option>
        </Field>
      </div>
</template>

<style scoped>

</style>