<script setup lang="ts">
import ProfilePicture from "./ProfilePicture.vue";

interface User {
  id: number;
  name: string;
  email: string;
  avatarUrl: string | null;
}

interface Navigation {
  to: string;
  title: string;
}

defineProps<{
  user: User;
  navigation: Navigation[];
}>();
</script>

<template>
  <div
    class="absolute z-10 translate-y-9 xsm:translate-x-0 translate-x-[10px] max-w-64 w-full bg-white shadow-[0_2px_2px_1px] shadow-concrete-500 rounded-sm border-[1px] border-gray-400"
  >
    <div class="my-1">
      <div class="uppercase font-bold text-sm p-3">Account</div>
      <div class="px-4 flex flex-row items-center gap-3 py-2">
        <ProfilePicture :userName="user.name" :userUrl="user.avatarUrl" class="w-10 h-10" />
        <div>
          <div class="font-semibold text-sm">{{ user.name }}</div>
          <div class="text-xs">{{ user.email }}</div>
        </div>
      </div>
      <div
        class="px-3 py-2 text-sm hover:bg-concrete-100 cursor-pointer"
        @click="
          {
            navigateTo('/user/profile');
            $emit('page-exit');
          }
        "
      >
        Manage account
      </div>
    </div>
    <div class="border-b-2 border-t-2 border-gray-300">
      <div class="uppercase font-bold text-sm p-3">Links</div>
      <div
        v-for="nav in navigation"
        @click="
          {
            navigateTo(nav.to);
            $emit('page-exit');
          }
        "
        class="px-3 py-2 text-sm hover:bg-concrete-100 cursor-pointer"
      >
        {{ nav.title }}
      </div>
    </div>
    <div
      @click="$emit('logout')"
      class="px-3 py-2 my-1 hover:bg-concrete-100 cursor-pointer font-semibold text-sm"
    >
      Log out
    </div>
  </div>
</template>

<style scoped></style>
