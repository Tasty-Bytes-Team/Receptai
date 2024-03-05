<script setup>
    const props = defineProps({
        ingredients: Object
    })

</script>

<template>
    <div class="mx-2">
        <h2 class="font-semibold text-2xl">Ingredients</h2>
        <div v-for="oneIngCat in ingredients">
            <h3 class="font-semibold text-lg">{{ oneIngCat.purpose }}</h3>
        <ul class="list-disc pl-10">
            <li v-for="ingredient in oneIngCat.ingredients">{{ ingredient.name }}, {{ ingredient.quantity }} {{ ingredient.unit }}</li>
        </ul>
        </div>
        
    </div>
</template>

<style lang="scss" scoped>

</style>