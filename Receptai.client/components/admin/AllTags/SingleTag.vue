<template>
  <tr class="border-b-2 border-concrete-200">
    <td class="text-center p-3">
      {{ tag.id }}
    </td>
    <td class="text-center p-3 font-semibold">
      {{ tag.name }}
    </td>
    <td class="text-center p-3">
      <Icon :name="tag.iconName" color="black" />
    </td>
    <td class="text-center p-3">DELETE</td>
  </tr>
</template>

<script setup lang="ts">
import type { Tag } from "@/typescript/types";

const props = defineProps<{
  tag: Tag;
}>();

console.log(props);
</script>

<style scoped></style>
