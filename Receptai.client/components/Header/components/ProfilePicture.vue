<script setup lang="ts">
const props = defineProps<{
  userName: string;
  userUrl: string | null;
}>();

const profilePicture = computed(() => {
  return props.userUrl ? `bg-[url(${props.userUrl})] bg-cover` : null;
});
</script>

<template>
  <div
    class="bg-indigo-300 rounded-full flex justify-center items-center font-semibold"
    :class="profilePicture"
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
