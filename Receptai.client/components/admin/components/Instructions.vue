<script setup lang="ts">
import { FieldArray } from "vee-validate";

import InputTextarea from "./InputTextarea.vue";
import DeleteButton from "./DeleteButton.vue";
import AddButton from "./AddButton.vue";
</script>

<template>
  <div class="px-3 flex flex-col gap-2">
    <h3 class="text-center text-lg font-semibold">Cooking Instructions</h3>
    <FieldArray
      name="instructions"
      v-slot="{ fields, push, remove }"
      class="justify-around flex-col gap-2 flex"
    >
      <div
        v-for="(field, idx) in fields"
        :key="field.key"
        class="border-2 border-black p-3 flex flex-col gap-2"
      >
        <div class="flex gap-3 items-center">
          <h5 class="font-semibold text-md uppercase">Step {{ idx + 1 }}</h5>
          <DeleteButton @delete="remove(idx)" v-if="fields.length > 1" />
        </div>
        <InputTextarea
          :name="`instructions[${idx}]`"
          label="Instructions (required)"
          :placeholder="`What are we doing in STEP ${idx + 1}?`"
        />
      </div>
      <AddButton color="green" text="Add new step" @add="push('')" />
    </FieldArray>
  </div>
</template>

<style scoped></style>
./DeleteButton.vue./AddButton.vue