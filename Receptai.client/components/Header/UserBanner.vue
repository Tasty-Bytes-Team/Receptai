<script setup lang="ts">
import ProfilePicture from "./components/ProfilePicture.vue";
import ProfileMenu from "./components/ProfileMenu.vue";

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

const user = ref<User | null>(null);
const hover = ref(false);

const TastyBytes_user = useCookie<UserCookie | null>("TastyBytes_user");

if (TastyBytes_user.value?.user) {
  user.value = TastyBytes_user.value.user;
} else {
  TastyBytes_user.value = null;
}

const logout = () => {
  TastyBytes_user.value = null;
  user.value = null;
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

const vClickOutside = {
  mounted(el: any, binding: { value: (event: Event) => void }) {
    const toggleListener = () => {
      if (hover.value) {
        el.__ClickOutsideHandler__ = (event: Event) => {
          if (!(el === event.target || el.contains(event.target as Node))) {
            binding.value(event);
          }
        };
        document.body.addEventListener("click", el.__ClickOutsideHandler__);
      } else {
        document.body.removeEventListener(
          "click",
          el.__ClickOutsideHandler__ as EventListener
        );
        el.__ClickOutsideHandler__ = undefined;
      }
    };

    toggleListener();

    watch(hover, toggleListener);
  },
  unmounted(el: any) {
    document.body.removeEventListener(
      "click",
      el.__ClickOutsideHandler__ as EventListener
    );
  },
};
</script>

<template>
  <div
    v-if="user"
    class="flex flex-col items-end"
    v-click-outside="() => (hover = false)"
  >
    <div class="font-bold" @click="() => (hover = !hover)">
      <ProfilePicture
        class="hover:shadow-[0px_0px_0_6px_#00000020] hover:scale-105 transition-shadow duration-150 cursor-pointer w-8 h-8"
        :class="hover ? 'shadow-[0px_0px_0_6px_#00000010]' : null"
        :user_name="user.name"
      />
    </div>
    <ProfileMenu
      v-if="hover"
      :user
      :navigation
      @logout="logout"
      @page-exit="hover = false"
    />
  </div>
  <div v-else>
    <NuxtLink to="/user/login">
      <div class="text-right">Login</div>
    </NuxtLink>
  </div>
</template>

<style lang="scss" scoped></style>
