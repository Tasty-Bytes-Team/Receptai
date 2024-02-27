<script setup>

const email = ref();
const password = ref();

const passwordCheck = () => {
    if(password.value.match(/[a-z]+/) && password.value.match(/[A-Z]+/) && password.value.match(/[0-9]+/) && password.value.match(/[$@#&!?*-~.,/;:]+/)){
        return true;
    } else {
        return false
    }
}

const handleSubmit = () => {
    console.log(passwordCheck())
}

</script>

<template>
    <div>
        Login
        <form @submit.prevent="handleSubmit">
        <input placeholder="Email" type="email" required v-model="email" autocomplete="email" />
        <input placeholder="Password" type="password" required minlength="6" v-model="password" autocomplete="current-password" />
        <button type="submit">Submit</button>
    </form>
    </div>
    </template>

<style lang="scss" scoped>

</style>