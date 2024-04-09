<template>
    <NuxtLink :to="link" class="recipe-banner relative" @mouseover="showInfo = true" @mouseleave="showInfo = false">
        <NuxtImg :src="!error ? imageLink : '/assets/TastyBytes_Fallback.webp'"
            class="m-auto rounded-md lg:h-24 h-16 object-cover w-full bg-concrete-200" @error="() => (error = true)"
            data-testid="recipe-image" />
        <div v-if="showInfo"
            class="absolute inset-0 flex flex-col justify-center items-center bg-black bg-opacity-50 rounded-md transition-opacity duration-300">
            <h2 :class="{ 'text-sm': showInfo }" class="text-white text-lg font-bold mb-1" data-testid="recipe-name"
                :style="{ paddingLeft: '5px' }">{{ truncateText(name) }}</h2>
            <p :class="{ 'text-xs': showInfo }" class="text-white text-sm text-center px-2"
                data-testid="recipe-description">{{ truncateText(about) }}</p>
        </div>
    </NuxtLink>
</template>

<script setup lang="ts">
import { ref } from 'vue';
import { defineProps } from '@vue/runtime-core';

defineProps({
    imageLink: String,
    name: String,
    about: String,
    link: String,
});

const error = ref(false);
const showInfo = ref(false);


function truncateText(text: string | undefined): string {
    const maxLength = 50;
    if (!text) return '';
    if (text.length > maxLength) {
        return text.slice(0, maxLength) + '...';
    } else {
        return text;
    }
}
</script>

<style scoped>
.recipe-banner {
    margin-right: 16px;
}
</style>