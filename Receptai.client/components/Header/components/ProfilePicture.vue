<script setup lang="ts">
defineProps<{
  user_name: string;
}>();
</script>

<template>
  <div
    class="bg-indigo-300 rounded-full flex justify-center items-center font-semibold"
    data-testid="profile-picture"
  >
    {{
      /[a-zA-Z].*\s.*[a-zA-Z]/.test(user_name)
        ? user_name
            .trim()
            .split(" ")
            .slice(0, 2)
            .map((word) => word[0].toUpperCase())
            .join("")
        : user_name.trim().substring(0, 2).toUpperCase()
    }}
  </div>
</template>

<style scoped></style>
