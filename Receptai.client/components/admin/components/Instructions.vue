<script setup lang="ts">
import { FieldArray, type FieldEntry } from "vee-validate";

import InputTextarea from "./InputTextarea.vue";
import DeleteButton from "./DeleteButton.vue";
import AddButton from "./AddButton.vue";

const collapsed = ref<number[]>([]);
const allOpen = ref(true);

const toggleCollapse = (idx: number, length: number) => {
  const findIndex = collapsed.value.indexOf(idx);

  if (findIndex === -1) {
    collapsed.value.push(idx);
  } else {
    collapsed.value.splice(findIndex, 1);
  }

  if (length === collapsed.value.length) {
    allOpen.value = false;
  } else {
    allOpen.value = true;
  }
};

const closeAll = (all: FieldEntry[]) => {
  all.forEach((element: FieldEntry) => {
    const findIndex = collapsed.value.indexOf(element.key as number);

    if (findIndex === -1) {
      collapsed.value.push(element.key as number);
    }
  });

  allOpen.value = false;
};

const openAll = () => {
  collapsed.value = [];
  allOpen.value = true;
};
</script>

<template>
  <div class="px-3 flex flex-col gap-2">
    <h3 class="text-center text-lg font-semibold">Cooking Instructions</h3>
    <FieldArray
      name="instructions"
      v-slot="{ fields, push, remove }"
      class="justify-around flex-col gap-2 flex"
    >
      <button type="button" @click="allOpen ? closeAll(fields) : openAll()">
        {{ allOpen ? "Close all" : "Open all" }}
      </button>
      <div
        v-for="(field, idx) in fields"
        :key="field.key"
        class="border-2 border-black p-3 flex flex-col gap-2"
      >
        <div class="flex flex-row justify-between">
          <div class="flex gap-3 items-center">
            <h5 class="font-semibold text-md uppercase">Step {{ idx + 1 }}</h5>
            <DeleteButton @delete="remove(idx)" v-if="fields.length > 1" />
          </div>
          <button
            type="button"
            @click="toggleCollapse(field.key as number, fields.length)"
            class="bg-concrete-200 rounded-md p-1"
          >
            <Icon
              :name="
                !collapsed.includes(field.key as number)
                  ? 'material-symbols:keyboard-arrow-up'
                  : 'material-symbols:keyboard-arrow-down-rounded'
              "
              size="20px"
              color="black"
            />
          </button>
        </div>

        <InputTextarea
          v-show="!collapsed.includes(field.key as number)"
          :name="`instructions[${idx}]`"
          label="Instructions (required)"
          :placeholder="`What are we doing in STEP ${idx + 1}?`"
        />
      </div>
      <AddButton
        color="green"
        text="Add new step"
        @add="
          () => {
            push('');
          }
        "
      />
    </FieldArray>
  </div>
</template>

<style scoped></style>
