<script setup lang="ts">
defineProps<{
  userName: string;
  userUrl?: string | null;
}>();
</script>

<template>
  <div
    class="bg-indigo-300 rounded-full flex justify-center items-center font-semibold bg-cover"
    :style="
      userUrl
        ? {
            'background-image': `linear-gradient(rgba(255, 255, 255, 0.5), rgba(255, 255, 255, 0.5)), url(${userUrl})`,
          }
        : null
    "
    data-testid="profile-picture"
  >
    {{
      /[a-zA-Z].*\s.*[a-zA-Z]/.test(userName)
        ? userName
            .trim()
            .split(" ")
            .slice(0, 2)
            .map((word) => word[0].toUpperCase())
            .join("")
        : userName.trim().substring(0, 2).toUpperCase()
    }}
  </div>
</template>

<style scoped></style>
