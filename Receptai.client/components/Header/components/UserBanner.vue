<script setup lang="ts">
import type { User, UserCookie, HeaderNavigation } from "@/typescript/types";
import ProfilePicture from "./ProfilePicture.vue";
import ProfileMenu from "./ProfileMenu.vue";

import { addNotification } from "@/store/store";

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

const navigation: Array<HeaderNavigation> = [
  {
    to: "/user/dashboard",
    title: "Dashboard",
  },
  {
    to: "/user/dashboard/my-recipes",
    title: "My recipes",
  },
  {
    to: "/user/dashboard/community-feedback",
    title: "Community Feedback",
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
        class="hover:ring-[6px] hover:ring-gray-200 hover:scale-105 transition-all duration-150 cursor-pointer w-8 h-8"
        :class="hover ? 'ring-4 ring-gray-100' : null"
        :userName="user.name"
        :userUrl="user.avatarUrl"
      />
    </div>
    <ProfileMenu
      v-if="hover"
      :user
      :navigation
      :is-admin="TastyBytes_user?.user.roles.includes('ROLE_ADMIN')"
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
