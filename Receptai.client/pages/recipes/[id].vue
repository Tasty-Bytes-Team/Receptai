<script setup>
definePageMeta({
  layout: 'empty',
})

    import RecipePage from '@/components/RecipePage/RecipePage.vue';

    const route = useRoute()
</script>

<template>
    <div>
        <RecipePage :id="route.params.id" />
    </div>
</template>

<style lang="scss" scoped>

</style>