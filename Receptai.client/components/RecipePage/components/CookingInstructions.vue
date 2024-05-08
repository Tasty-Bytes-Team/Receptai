<script setup lang="ts">
interface Instruction {
  text: string;
}

defineProps<{
  cookingInstructions: Instruction[];
}>();
</script>

<template>
  <div class="mx-2">
    <h3 class="font-semibold text-2xl">Making instructions</h3>
    <ul>
      <li v-for="(instruction, index) in cookingInstructions" class="py-3">
        <h5 class="font-semibold text-lg">STEP {{ index + 1 }}</h5>
        <p>
          {{ instruction.text }}
        </p>
      </li>
    </ul>
  </div>
</template>

<style lang="scss" scoped></style>
