<script setup lang="ts">
import ProfilePicture from "./components/ProfilePicture.vue";
import { addNotification } from "@/store/store";

interface User {
  id: number;
  name: string;
  email: string;
}

interface Navigation {
  to: string;
  title: string;
}

interface UserCookie {
  token: string;
  expiresIn: number;
  user: User;
}

const user_name = ref<string | null>("");
const user_email = ref<string | null>("");
const hover = ref(false);

const TastyBytes_user = useCookie<UserCookie | null>("TastyBytes_user");

if (TastyBytes_user.value?.user) {
  user_name.value = TastyBytes_user.value.user.name;
  user_email.value = TastyBytes_user.value.user.email;
} else {
  TastyBytes_user.value = null;
}

const logout = () => {
  TastyBytes_user.value = null;
  user_name.value = null;
  user_email.value = null;
  navigateTo("/user/login");
  hover.value = false;
  addNotification(
    "You've been successfully logged out. See you next time!",
    "Success"
  );
};

const navigation: Array<Navigation> = [
  {
    to: "/user/dashboard",
    title: "Dashboard",
  },
  {
    to: "/user/dashboard/my-recipes",
    title: "My recipes",
  },
  {
    to: "/user/dashboard/my-recipes/create",
    title: "Create new recipe",
  },
];
</script>

<template>
  <div v-if="user_name" class="flex flex-col items-end">
    <div class="font-bold" @click="hover = !hover">
      <ProfilePicture
        class="hover:shadow-[0px_0px_0_6px_#00000020] transition-shadow duration-100 cursor-pointer"
        :class="hover ? 'shadow-[0px_0px_0_6px_#00000010]' : null"
        :size="8"
        :user_name
      />
    </div>
    <div
      v-if="hover"
      class="absolute z-10 translate-y-9 w-64 bg-white shadow-[0_2px_2px_1px] shadow-concrete-500 rounded-sm border-[1px] border-gray-400"
      @mouseleave="hover = false"
    >
      <div class="border-b-2 border-gray-300">
        <div class="uppercase font-bold text-sm p-3">Account</div>
        <div class="px-4 flex flex-row items-center gap-3 py-2">
          <ProfilePicture :size="10" :user_name />
          <div>
            <div class="font-semibold text-sm">{{ user_name }}</div>
            <div class="text-xs">{{ user_email }}</div>
          </div>
        </div>
        <div class="px-3 py-2 text-sm hover:bg-concrete-100">
          Manage account
        </div>
      </div>
      <div>
        <div class="uppercase font-bold text-sm p-3">Links</div>
        <div
          v-for="nav in navigation"
          @click="
            {
              navigateTo(nav.to);
              hover = false;
            }
          "
          class="px-3 py-2 text-sm hover:bg-concrete-100 cursor-pointer"
        >
          {{ nav.title }}
        </div>
      </div>
      <div
        @click="logout"
        class="px-3 py-2 hover:bg-concrete-100 cursor-pointer border-t-2 border-gray-300 font-semibold text-sm"
      >
        Log out
      </div>
    </div>
  </div>
  <div v-else>
    <NuxtLink to="/user/login">
      <div class="text-right">Login</div>
    </NuxtLink>
  </div>
</template>

<style lang="scss" scoped></style>
