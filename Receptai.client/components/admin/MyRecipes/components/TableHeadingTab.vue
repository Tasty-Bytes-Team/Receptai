<script setup lang="ts">
import type { RecipeContainerColumn } from '~/typescript/types';

defineProps<{
  columns: RecipeContainerColumn[];
}>();

const hover = ref(false);
</script>

<template>
  <th
    scope="col"
    @pointerenter="hover=true"
    @pointerleave="hover=false"
    class="px-3 py-3 border-concrete-300 border-2"
    :class="item.label === 'ID' ? 'min-w-14' : 'min-w-36'"
    v-for="item in columns"
  >
    <div class="flex flex-row items-center gap-1.5">
      {{ item.label }}
      <Icon
        v-show="hover"
        v-if="item.sortable"
        size="16px"
        name="heroicons-solid:sort-ascending"
        color="black"
      />
    </div>
  </th>
</template>

<style scoped></style>
