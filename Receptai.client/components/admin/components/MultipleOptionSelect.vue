<script setup lang="ts">
import { ErrorMessage, Field } from "vee-validate";

interface Tag {
  id: number;
  name: string;
  iconName: string;
}

defineProps<{
  name: string;
  label: string;
  tagList: Tag[] | null;
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
      v-slot="{ value }"
      multiple
      class="outline-none w-full p-2 px-3 placeholder:text-concrete-400 bg-concrete-50 rounded-sm border-2 border-concrete-400 transition-colors duration-150 focus:border-black"
    >
      <option
        v-for="tag in tagList"
        :key="tag.id"
        :value="tag.id"
        :selected="value && value.includes(tag.id)"
      >
        {{ tag.name }}
      </option>
    </Field>
  </div>
</template>

<style scoped></style>
