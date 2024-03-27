<script setup lang="ts">
import axios from "axios";

import ProfilePicture from "@/components/Header/components/ProfilePicture.vue";

definePageMeta({
  middleware: "auth",
});
const config = useRuntimeConfig();

interface User {
  id: number;
  name: string;
  email: string;
  avatarUrl: string | null;
}

interface UserCookie {
  token: string;
  expiresIn: number;
  user: User;
}

const user = ref<User | null>(null);
const TastyBytes_user = useCookie<UserCookie | null>("TastyBytes_user");

const imagesURL = [
  "https://upload.wikimedia.org/wikipedia/commons/c/c1/Indian-Food-wikicont.jpg",
];

if (TastyBytes_user.value) {
  axios
    .get(`${config.public.baseURL}/api/v1/user/me`, {
      headers: { Authorization: `Bearer ${TastyBytes_user.value.token}` },
    })
    .then((d) => {
      user.value = d.data;
    });
} else {
  TastyBytes_user.value = null;
}
</script>

<template>
  <div>
    <h1 class="text-3xl font-bold text-center m-5">Profile</h1>
  </div>
  <div v-if="user">
    <div class="flex flex-col justify-center items-center">
      <div
        class="w-full py-10 flex justify-center items-center rounded-md bg-cover bg-[url(https://th.bing.com/th/id/R.3d35003509104e7b7e0b8e2ac4d1ca2c?rik=dYArdd5RRivs9A&riu=http%3a%2f%2fwww.yurtopic.com%2ffood%2fcooking%2fimages%2fworld-of-gourmet%2fDelicious-field-salad2.jpg&ehk=2NTSPZUV8VCbauRbIZ6stV3ClmYd%2bTHctu7uswVH%2fIk%3d&risl=&pid=ImgRaw&r=0)] shadow-[inset_0_0_0_1000px_#0000004f] bg-center"
      >
        <ProfilePicture
          class="w-20 h-20 border-[3px] border-white text-3xl shadow-[2px_2px_3px_1px_#ffffff82]"
          :user_name="user.name"
        />
      </div>
    </div>
    <div class="max-w-2xl m-auto flex flex-col gap-2 mt-4">
      <div class="font-bold text-xl">User details</div>
      <div
        class="m-auto border border-concrete-400 rounded-sm p-4 w-full flex flex-col gap-2 shadow-[0_1px_2px_1px_#828282]"
      >
        <div class="flex flex-col">
          <label class="font-medium text-gray-950">Full name</label>
          <div
            class="font-normal px-2 py-2 hover:bg-concrete-200 transition-colors duration-150 rounded-sm"
          >
            {{ user.name }}
          </div>
        </div>
        <div class="flex flex-col">
          <label class="font-medium text-gray-950">Email</label>
          <div
            class="font-normal px-2 py-2 hover:bg-concrete-200 transition-colors duration-150 rounded-sm text-gray-950"
          >
            {{ user.email }}
          </div>
        </div>
        <div class="flex flex-col">
          <label class="font-medium text-gray-950">Avatar URL</label>
          <div
            class="font-normal px-2 py-2 hover:bg-concrete-200 transition-colors duration-150 rounded-sm"
            :class="user.avatarUrl ? null : 'text-concrete-500'"
          >
            {{ user.avatarUrl ? user.avatarUrl : "Empty" }}
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<style scoped></style>
