<script setup lang="ts">
import axios from "axios";
import RecipeBannerComponent from "@/components/Banner/RecipeBannerComponent.vue";

const config = useRuntimeConfig();

interface Recipe {
    id: number;
    name: string;
    shortDescription: string;
    previewImage: string;
}

const recipeList = ref<Recipe[] | null>(null);

try {
    axios
        .get(
            `${config.public.baseURL}/api/v1/recipe/list?page=0&sortBy=dateCreated&sortAsc=false`
        )
        .then((res) => {
            recipeList.value = res.data.elements.slice(0, 5);
        });
} catch (e) {
    console.error("Error fetching recipes", e);
}
</script>

<template>
    <div v-if="recipeList" class="banner flex flex-wrap">
        <RecipeBannerComponent v-for="item in recipeList" :key="item.id" :imageLink="item.previewImage"
            :name="item.name" :about="item.shortDescription" :link="`/recipes/${item.id}`" />
    </div>
    <div v-else>
        <p>Failed to load recipes.</p>
    </div>

</template>
<style scoped>
.banner {
    background-color: #f3f4f6;
    border-radius: 12px;
    border: 2px solid #333;
    padding: 20px;
    justify-content: center;
}
</style>