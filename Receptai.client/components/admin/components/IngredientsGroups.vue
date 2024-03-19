<script setup lang="ts">
import { FieldArray } from "vee-validate";

import InputField from "./InputField.vue";
import DeleteButton from "./DeleteButton.vue";
import AddButton from "./AddButton.vue";
import Ingredient from "./Ingredients.vue";
</script>

<template>
  <div class="px-3 flex flex-col gap-2">
    <h3 class="text-center text-lg font-semibold">Cooking Ingredients</h3>
    <FieldArray
      name="ingredients"
      v-slot="{ fields, push, remove }"
      class="justify-around flex-col gap-2 flex"
    >
      <fieldset
        v-for="(field, groupIndex) in fields"
        :key="field.key"
        class="border-2 border-black p-3 flex flex-col gap-2"
      >
        <div class="flex gap-3 items-center">
          <h5 class="font-semibold text-md uppercase">
            Ingredients group {{ groupIndex + 1 }}
          </h5>
          <DeleteButton @delete="remove(groupIndex)" v-if="fields.length > 1" />
        </div>
        <div>
          <InputField
            :name="`ingredients[${groupIndex}].purpose`"
            label="Purpose (required)"
            :placeholder="`What are we making with group ${
              groupIndex + 1
            } ingredients?`"
          />
        </div>
        <Ingredient :groupIndex="groupIndex" />
      </fieldset>
      <AddButton
        color="green"
        text="Add new group"
        @add="
          push({
            purpose: '',
            ingredients: [{ name: '', quantity: null, unit: '' }],
          })
        "
      />
    </FieldArray>
  </div>
</template>

<style scoped></style>
./DeleteButton.vue./AddButton.vue./Ingredients.vue