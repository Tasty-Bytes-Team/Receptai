<script setup lang="ts">
import axios from "axios";
import type { User, UserCookie } from "@/typescript/types";
import { Form, useForm, useField } from "vee-validate";
import { toTypedSchema } from "@vee-validate/zod";
import * as zod from "zod";

import ProfilePicture from "@/components/Header/components/ProfilePicture.vue";
import { addNotification } from "~/store/store";
import PasswordChange from "@/components/admin/ProfilePage/components/PasswordChange.vue";
import ErrorBaner from "@/components/Error/ErrorBaner.vue";
import ChangeName from "@/components/admin/ProfilePage/components/ChangeName.vue";
import ChangeAvatarUrl from "@/components/admin/ProfilePage/components/ChangeAvatarUrl.vue";

definePageMeta({
  middleware: "auth",
});

const config = useRuntimeConfig();

const user: User = reactive({
  id: 0,
  name: "",
  email: "",
  avatarUrl: null,
  roles: []
});
const showConfirmation = ref(false);
const TastyBytes_user = useCookie<UserCookie | null>("TastyBytes_user");

const error: Ref<boolean> = ref(false);
const errorText: Ref<string> = ref("");

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

const onSubmit = handleSubmit(async (data) => {
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
      console.warn(e);

      name.value = user.name;
      errorText.value =
        "There was an error when changing your name. Please try again.";
      error.value = true;
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
            :userName="user.name"
            :userUrl="user.avatarUrl"
          />
        </div>
      </div>
      <div class="max-w-2xl m-auto flex flex-col gap-2 mt-4">
        <div class="font-bold text-xl">User details</div>
        <div
          class="m-auto border border-concrete-400 rounded-sm p-4 w-full flex flex-col gap-4 shadow-[0_1px_2px_1px_#828282]"
        >
          <ErrorBaner v-if="error" :errorText />
          <ChangeName :TastyBytes_user="TastyBytes_user" />
          <div class="flex flex-col">
            <label class="font-medium text-gray-950">Email</label>
            <div
              class="w-full bg-concrete-50 hover:bg-concrete-100 px-2 py-2 transition-colors duration-150 rounded-sm text-gray-950"
            >
              {{ user.email }}
            </div>
          </div>
          <ChangeAvatarUrl :TastyBytes_user="TastyBytes_user" />
        </div>
      </div>
      <PasswordChange />
    </div>
  </div>
  <div v-else>Loading...</div>
</template>

<style scoped></style>
