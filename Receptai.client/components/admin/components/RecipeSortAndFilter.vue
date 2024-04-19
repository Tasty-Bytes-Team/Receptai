<script setup lang="ts">
const model = defineModel();

defineProps<{
  pageNumber: number;
  elementsPerPage: number;
  currentElementCount: number;
  totalElements: number;
}>();

const selectionOptions = [
  {
    value: "nameDesc",
    name: "Name from A to Z",
  },
  {
    value: "nameAsc",
    name: "Name from Z to A",
  },
  {
    value: "DateDesc",
    name: "Newest first",
  },
  {
    value: "DateAsc",
    name: "Oldest first",
  },
];
</script>

<template>
  <div class="border-2 border-black bg-concrete-100">
    <div class="flex flex-row border-b-2 border-black">
      <div class="flex flex-1 gap-2 items-center p-2">
        <Icon name="solar:filter-bold" color="black" size="26px" />
        Filter
      </div>
      <div class="flex flex-1 gap-2 items-center p-2 border-l-2 border-black">
        <Icon name="solar:sort-vertical-linear" color="black" size="26px" />
        <div class="w-full">
          <select v-model="model" class="outline-none bg-concrete-100 w-full">
            <option v-for="option in selectionOptions" :value="option.value">
              {{ option.name }}
            </option>
          </select>
        </div>
      </div>
    </div>
    <div class="p-2">
      Showing:
      <b
        >{{ pageNumber * elementsPerPage + 1 }} -
        {{ pageNumber * elementsPerPage + currentElementCount }}</b
      >
      out of <b>{{ totalElements }}</b> recipes
    </div>
  </div>
</template>

<style scoped></style>
