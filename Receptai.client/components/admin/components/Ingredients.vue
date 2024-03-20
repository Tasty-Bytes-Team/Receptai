<script setup lang="ts">
import { FieldArray } from "vee-validate";

import InputField from "./InputField.vue";
import DeleteButton from "./DeleteButton.vue";
import AddButton from "./AddButton.vue";

defineProps<{
  groupIndex: number;
}>();
</script>

<template>
  <div>
  <label class="font-semibold text-sm">Ingredients</label>
  <FieldArray
    :name="`ingredients[${groupIndex}].ingredients`"
    v-slot="{ fields, push, remove }"
    class="justify-around flex items-center"
  >
    <ul class="w-full list-disc pl-5">
      <li v-for="(field, index) in fields" :key="field.key" class="mb-3">
        <div class="flex flex-col gap-2 sm:flex-row">
          <div class="w-full">
            <InputField
              :name="`ingredients[${groupIndex}].ingredients[${index}].name`"
              label="Name (required)"
              placeholder="Name"
            />
          </div>
          <div class="w-full">
            <InputField
              :name="`ingredients[${groupIndex}].ingredients[${index}].quantity`"
              label="Quantity (required)"
              placeholder="Quantity"
              type="number"
            />
          </div>
          <div class="w-full">
            <InputField
              :name="`ingredients[${groupIndex}].ingredients[${index}].unit`"
              label="Unit (required)"
              placeholder="Unit"
            />
          </div>
          <DeleteButton @delete="remove(index)" v-if="fields.length > 1" />
        </div>
      </li>
    </ul>
    <AddButton
      color="yellow"
      text="Add new ingredient"
      @add="
        push({
          name: '',
          quantity: null,
          unit: '',
        })
      "
    />
  </FieldArray>
</div>
</template>

<style scoped></style>