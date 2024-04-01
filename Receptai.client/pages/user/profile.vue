<script setup lang="ts">
import axios from "axios";
import { Form, useForm, useField } from "vee-validate";
import { toTypedSchema } from "@vee-validate/zod";
import * as zod from "zod";

import ProfilePicture from "@/components/Header/components/ProfilePicture.vue";
import { addNotification } from "~/store/store";
import PasswordChange from "@/components/admin/ProfilePage/components/PasswordChange.vue";

definePageMeta({
  middleware: "auth",
});
const config = useRuntimeConfig();

interface User {
  id: number | null;
  name: string | null;
  email: string | null;
  avatarUrl: string | null;
}

interface UserCookie {
  token: string;
  expiresIn: number;
  user: User;
}

const user: User = reactive({
  id: null,
  name: null,
  email: null,
  avatarUrl: null,
});
const showConfirmation = ref(false);
const TastyBytes_user = useCookie<UserCookie | null>("TastyBytes_user");

const validationSchema = toTypedSchema(
  zod.object({
    name: zod
      .string()
      .min(3, "Name must be at least three characters long.")
      .regex(
        /^[a-zA-Z]{3,}(?: [a-zA-Z]+){0,2}$/,
        "Name can't contain special symbols."
      ),
  })
);

const { handleSubmit, errors } = useForm({
  validationSchema,
});

const { value: name } = useField("name");

if (TastyBytes_user.value) {
  axios
    .get(`${config.public.baseURL}/api/v1/user/me`, {
      headers: { Authorization: `Bearer ${TastyBytes_user.value.token}` },
    })
    .then((d) => {
      user.id = d.data.id;
      name.value = d.data.name;
      user.name = d.data.name;
      user.email = d.data.email;
      user.avatarUrl = d.data.avatarUrl;
    });
} else {
  TastyBytes_user.value = null;
}

const onSubmit = handleSubmit(async () => {
  showConfirmation.value = false;

  if (name.value === user.name) {
    return;
  }

  if (TastyBytes_user.value) {
    try {
      await axios.patch(
        `${config.public.baseURL}/api/v1/user/edit/${TastyBytes_user.value.user.id}`,
        {
          newName: name.value,
        },
        {
          headers: { Authorization: `Bearer ${TastyBytes_user.value.token}` },
        }
      );

      const newObject = TastyBytes_user.value;
      newObject.user.name = name.value as string;
      user.name = newObject.user.name;
      TastyBytes_user.value = newObject;
      addNotification(
        "Name change was successful! Keep in mind that your change might take some time to show everywhere.",
        "Success"
      );
    } catch (e) {
      console.log(e);

      name.value = user.name;
      addNotification(
        "There was an error when changing your name. Please try again.",
        "Error"
      );
    }
  }
});

const onCancel = () => {
  showConfirmation.value = false;
  name.value = user.name;
};
</script>

<template>
  <div v-if="user.name !== null">
    <div>
      <h1 class="text-3xl font-bold text-center m-5">Profile</h1>
    </div>
    <div v-if="user">
      <div class="flex flex-col justify-center items-center">
        <div
          class="w-full py-10 flex justify-center items-center rounded-md bg-cover bg-[url(/images/food-image.jpg)] shadow-[inset_0_0_0_1000px_#0000004f] bg-center"
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
          <form class="flex flex-col relative" @submit.prevent="onSubmit">
            <div class="flex gap-2 items-center flex-row">
              <label class="font-medium text-gray-950">Name</label>
              <span class="text-red-600 text-sm">{{ errors.name }}</span>
            </div>

            <div>
              <input
                @focus="showConfirmation = true"
                class="w-full bg-concrete-50 hover:bg-concrete-100 focus:bg-concrete-100 px-2 py-2 focus:border-concrete-300 border-2 border-concrete-50 transition-colors duration-150 rounded-sm text-gray-950 outline-none"
                :class="
                  showConfirmation
                    ? '!bg-concrete-100 !border-concrete-300'
                    : null
                "
                name="name"
                placeholder="Full name"
                autocomplete="name"
                v-model="name"
              />
              <div
                v-if="showConfirmation"
                class="absolute right-0 -bottom-8 flex gap-2"
              >
                <button
                  type="submit"
                  class="h-7 w-7 bg-gray-200 border border-gray-300 rounded-sm transition-all duration-150 hover:bg-gray-300"
                >
                  <Icon
                    name="material-symbols:done-rounded"
                    size="18px"
                    color="black"
                  />
                </button>
                <button
                  type="button"
                  value="cancel"
                  @click="onCancel"
                  class="h-7 w-7 bg-gray-200 border border-gray-300 rounded-sm transition-all duration-150 hover:bg-gray-300"
                >
                  <Icon
                    name="material-symbols:close-rounded"
                    size="18px"
                    color="black"
                  />
                </button>
              </div>
            </div>
          </form>
          <div class="flex flex-col">
            <label class="font-medium text-gray-950">Email</label>
            <div
            class="w-full bg-concrete-50 hover:bg-concrete-100 px-2 py-2 transition-colors duration-150 rounded-sm text-gray-950"
            >
              {{ user.email }}
            </div>
          </div>
          <div class="flex flex-col">
            <label class="font-medium text-gray-950">Avatar URL</label>
            <div
            class="w-full bg-concrete-50 hover:bg-concrete-100 px-2 py-2 transition-colors duration-150 rounded-sm text-gray-950"
              :class="user.avatarUrl ? null : '!text-concrete-500'"
            >
              {{ user.avatarUrl ? user.avatarUrl : "Empty" }}
            </div>
          </div>
        </div>
      </div>
      <PasswordChange />
    </div>
  </div>
  <div v-else>Loading...</div>
</template>

<style scoped></style>
