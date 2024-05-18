<template>
  <tr class="border-b-2 border-concrete-200">
    <td class="text-center p-3">
      {{ tag.id }}
    </td>
    <td class="text-center p-3 font-semibold text-base">
      {{ tag.name }}
    </td>
    <td class="text-center p-3">
      <Icon :name="tag.iconName" size="24px" color="black" />
    </td>
  </tr>
</template>

<script setup lang="ts">
import type { Tag } from "@/typescript/types";

defineProps<{
  tag: Tag;
}>();
</script>

<style scoped></style>
