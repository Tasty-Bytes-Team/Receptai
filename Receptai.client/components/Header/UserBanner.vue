<script setup>
import axios from "axios";

const TastyBytes_user = useCookie("TastyBytes_user");

const user_name = ref(null);
const user_email = ref(null);

if (TastyBytes_user.value) {
  user_name.value = TastyBytes_user.value.name;
  user_email.value = TastyBytes_user.value.email;
}

const logout = () => {
  const TastyBytes_user = useCookie("TastyBytes_user");
  TastyBytes_user.value = null;
  window.location.reload(true);
};
</script>

<template>
  <div v-if="user_name" class="flex gap-6">
    <NuxtLink to="/user/dashboard" class="font-bold">{{user_name}}</NuxtLink>
    <div @click="logout" class="cursor-pointer">Logout</div>
  </div>
  <div v-else>
    <NuxtLink to="/user/login">
      <div id="app-header__right" class="text-right">Login</div>
    </NuxtLink>
  </div>
</template>

<style lang="scss" scoped></style>
