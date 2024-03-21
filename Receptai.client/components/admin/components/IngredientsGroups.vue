<script setup lang="ts">
import { FieldArray, type FieldEntry } from "vee-validate";

import InputField from "./InputField.vue";
import DeleteButton from "./DeleteButton.vue";
import AddButton from "./AddButton.vue";
import Ingredient from "./Ingredients.vue";

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
  all.forEach((element) => {
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
    <h3 class="text-center text-lg font-semibold">Cooking Ingredients</h3>
    <FieldArray
      name="ingredients"
      v-slot="{ fields, push, remove }"
      class="justify-around flex-col gap-2 flex"
    >
      <button type="button" @click="allOpen ? closeAll(fields) : openAll()">
        {{ allOpen ? "Close all" : "Open all" }}
      </button>
      <fieldset
        v-for="(field, groupIndex) in fields"
        :key="field.key"
        class="border-2 border-black p-3 flex flex-col gap-2"
      >
        <div class="flex flex-row justify-between">
          <div class="flex gap-3 items-center">
            <h5 class="font-semibold text-md uppercase">
              Ingredients group {{ groupIndex + 1 }}
            </h5>
            <DeleteButton
              @delete="remove(groupIndex)"
              v-if="fields.length > 1"
            />
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
        <div
          v-show="!collapsed.includes(field.key as number)"
          class="flex flex-col gap-2"
        >
          <InputField
            :name="`ingredients[${groupIndex}].purpose`"
            label="Purpose (required)"
            :placeholder="`What are we making with group ${
              groupIndex + 1
            } ingredients?`"
          />
          <Ingredient :groupIndex="groupIndex" />
        </div>
      </fieldset>
      <AddButton
        color="green"
        text="Add new group"
        @add="
          {
            push({
              purpose: '',
              ingredients: [{ name: '', quantity: null, unit: '' }],
            });
          }
        "
      />
    </FieldArray>
  </div>
</template>

<style scoped></style>
