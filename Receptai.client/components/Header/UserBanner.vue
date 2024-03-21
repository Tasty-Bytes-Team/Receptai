<script setup lang="ts">
import { addNotification } from "@/store/store";

interface User {
  id: number;
  name: string;
  email: string;
}

interface UserCookie {
  token: string;
  expiresIn: number;
  user: User;
}

const user_name = ref("");
const user_email = ref("");

const TastyBytes_user = useCookie<UserCookie | null>("TastyBytes_user");

if (TastyBytes_user.value?.user) {
  user_name.value = TastyBytes_user.value.user.name;
  user_email.value = TastyBytes_user.value.user.email;
} else {
  TastyBytes_user.value = null;
}

const logout = () => {
  TastyBytes_user.value = null;
  navigateTo("/user/login");
  addNotification(
    "You've been successfully logged out. See you next time!",
    "Success"
  );
};
</script>

<template>
  <div v-if="user_name" class="flex flex-col items-end">
    <NuxtLink to="/user/dashboard" class="font-bold">{{ user_name }}</NuxtLink>
    <div @click="logout" class="cursor-pointer">Logout</div>
  </div>
  <div v-else>
    <NuxtLink to="/user/login">
      <div class="text-right">Login</div>
    </NuxtLink>
  </div>
</template>

<style lang="scss" scoped></style>
