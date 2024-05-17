<script setup lang="ts">
import axios from "axios";
import type { Recipe } from "@/typescript/types";
import RecipeBannerComponent from "@/components/Banner/RecipeBannerComponent.vue";

const recipeList = ref<Recipe[]>([]);
const bannerOffset = ref(0);
const bannerComponentWidth = ref(0);
const bannerComponentHeight = ref(0);
const bannerWidth = ref(0);
let dragStartX = 0;
let isDragging = false;
let moveBannersInterval: NodeJS.Timeout | null = null;

async function fetchRecipes() {
    try {
        const response = await axios.get("/api/v1/recipe/list?page=0&sortBy=dateCreated&sortAsc=false");
        recipeList.value = response.data.elements.slice(0, 20);
    } catch (error) {
        console.error("Error fetching recipes", error);
    }
}

fetchRecipes();

onMounted(() => {
    updateBannerSize();
    window.addEventListener('resize', updateBannerSize);
    moveBannersInterval = setInterval(nextSlide, 2000);
});

onBeforeUnmount(() => {
    window.removeEventListener('resize', updateBannerSize);
    if (moveBannersInterval) {
        clearInterval(moveBannersInterval);
    }
});

function updateBannerSize() {
    const bannerContainer = document.querySelector('.w-full.mx-auto.overflow-hidden');
    if (bannerContainer) {
        bannerWidth.value = bannerContainer.clientWidth;
        updateBannerComponentSize();
    }
}

function updateBannerComponentSize() {
    const windowWidth = window.innerWidth;
    if (windowWidth > 768) {
        bannerComponentWidth.value = 192;
        bannerComponentHeight.value = 120;
    } else {
        bannerComponentWidth.value = 192 * 0.75;
        bannerComponentHeight.value = 120 * 0.75;
    }
}

function startDrag(event: MouseEvent | TouchEvent): void {
    isDragging = true;
    dragStartX = getEventX(event);

    if (moveBannersInterval) {
        clearInterval(moveBannersInterval);
        moveBannersInterval = null;
    }
}

function drag(event: MouseEvent | TouchEvent): void {
    if (!isDragging) return;
    const currentX = getEventX(event);
    const delta = currentX - dragStartX;
    const maxOffset = recipeList.value.length * -bannerComponentWidth.value + bannerWidth.value;

    if (bannerOffset.value + delta > 0) {
        bannerOffset.value = 0;
    } else if (bannerOffset.value + delta < maxOffset) {
        bannerOffset.value = maxOffset;
    } else {
        bannerOffset.value += delta;
    }
    dragStartX = currentX;
}

function endDrag(): void {
    isDragging = false;
    if (!moveBannersInterval) {
        moveBannersInterval = setInterval(nextSlide, 2000);
    }
}

function nextSlide(): void {
    const maxOffset = recipeList.value.length * -bannerComponentWidth.value + bannerWidth.value;
    const nextOffset = bannerOffset.value - bannerComponentWidth.value;
    bannerOffset.value = bannerOffset.value == maxOffset ? 0 : bannerOffset.value > 0 ? 0 : Math.max(maxOffset, nextOffset);
}

function prevSlide(): void {
    bannerOffset.value = Math.min(bannerOffset.value + bannerComponentWidth.value, 0);
}

function getEventX(event: MouseEvent | TouchEvent): number {
    return event instanceof MouseEvent ? event.clientX : event.touches[0].clientX;
}

const bannerTransformStyle = computed(() => `translateX(${bannerOffset.value}px)`);
</script>

<template>
    <div class="relative" @mousedown.prevent="startDrag" @touchstart.prevent="startDrag" @mousemove="drag"
        @touchmove="drag" @mouseup="endDrag" @touchend="endDrag" @mouseleave="endDrag" @touchcancel="endDrag">
        <div class="w-full mx-auto overflow-hidden rounded-lg border border-gray-800 bg-gray-50" ref="bannerContainer">
            <div class="flex"
                :style="{ transform: bannerTransformStyle, transition: isDragging ? 'none' : 'transform 0.5s ease' }">
                <div v-for="item in recipeList" :key="item.id" class="flex-shrink-0 mt-4">
                    <template v-if="!isDragging">
                        <RecipeBannerComponent :imageLink="item.previewImage" :name="item.name"
                            :about="item.shortDescription" :link="`/recipes/${item.id}`" :width="bannerComponentWidth" :height="bannerComponentHeight" />
                    </template>
                    <template v-else>
                        <RecipeBannerComponent :imageLink="item.previewImage" :name="item.name"
                            :about="item.shortDescription" :width="bannerComponentWidth" :height="bannerComponentHeight" />
                    </template>
                </div>
            </div>
        </div>
        <!-- Navigation Arrows -->
        <div class="absolute top-1/2 -left-12 transform -translate-y-1/2 cursor-pointer" @click="prevSlide">
            <svg class="w-6 h-6 text-black" fill="none" viewBox="0 0 24 24" stroke="currentColor">
                <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M15 19l-7-7 7-7" />
            </svg>
        </div>
        
        <div class="absolute top-1/2 -right-12 transform -translate-y-1/2 cursor-pointer" @click="nextSlide">
            <svg class="w-6 h-6 text-black" fill="none" viewBox="0 0 24 24" stroke="currentColor">
                <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M9 5l7 7-7 7" />
            </svg>
        </div>
    </div>
</template>
