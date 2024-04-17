<template>
    <NuxtLink :to="link" class="recipe-banner relative mr-4">
        <NuxtImg :src="!error ? imageLink : '/assets/TastyBytes_Fallback.webp'"
            class="m-auto rounded-md h-32 object-cover w-full bg-concrete-200" @error="() => (error = true)"
            data-testid="recipe-image" />
    </NuxtLink>
</template>

<script setup lang="ts">
defineProps({
    imageLink: String,
    name: String,
    about: String,
    link: String,
    length: Number,
});

const error = ref(false);
</script>