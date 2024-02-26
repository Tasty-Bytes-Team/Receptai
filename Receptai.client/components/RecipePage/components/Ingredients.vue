<script setup>
    const props = defineProps({
        ingredients: Object
    })

</script>

<template>
    <div class="mx-2">
        <h2 class="font-semibold text-2xl">Ingredients</h2>
        <ul>
          <li v-for="ingredient in ingredients.main">{{ ingredient.quantity }} {{ ingredient.name }}, {{ ingredient.unit }}</li>
        </ul>
        <div v-for="subcat in ingredients.additional">
            <h3 class="font-semibold text-lg">{{ subcat.name }}</h3>
        <ul>
            <li v-for="ingredient in subcat.ingredients">{{ ingredient.quantity }} {{ ingredient.name }}, {{ ingredient.unit }}</li>
        </ul>
        </div>
        
    </div>
</template>

<style lang="scss" scoped>

</style>