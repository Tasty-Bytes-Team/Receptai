<script setup lang="ts">
import axios from "axios";
import { Form, useForm, useField } from "vee-validate";
import { toTypedSchema } from "@vee-validate/zod";
import * as zod from "zod";
import { addNotification } from "@/store/store";

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

const config = useRuntimeConfig();
const TastyBytes_user = useCookie<UserCookie | null>("TastyBytes_user");

const errorsText: Ref<string | null> = ref(null);

const validationSchema = toTypedSchema(
  zod
    .object({
      password: zod.string().min(1, "This is required"),
      newPassword: zod
        .string()
        .min(1, "This is required")
        .min(8, "Password must be at least 8 characters long")
        .regex(new RegExp(".*[A-Z].*"), {
          message: "Password must include one capital letter",
        })
        .regex(new RegExp(".*[a-z].*"), {
          message: "Password must include one lowercase letter",
        })
        .regex(new RegExp(".*[0-9].*"), {
          message: "Password must include one number",
        })
        .regex(new RegExp(".*[`~<>?,./!@#$%^&*()\\-_+=\"'|{}\\[\\];:\\\\].*"), {
          message: "Password must include one special letter",
        }),
      newPasswordRepeat: zod.string().min(1, "This is required"),
    })
    .refine((data) => data.newPassword === data.newPasswordRepeat, {
      message: "Passwords do not match",
      path: ["newPasswordRepeat"],
    })
    .refine((data) => data.password !== data.newPassword, {
      message: "You must define a new password.",
      path: ["newPassword"],
    })
);

const { handleSubmit, errors } = useForm({
  validationSchema,
});

const { value: password } = useField("password");
const { value: newPassword } = useField("newPassword");
const { value: newPasswordRepeat } = useField("newPasswordRepeat");

const onSubmit = handleSubmit(async () => {
  errorsText.value = null;

  if (TastyBytes_user.value) {
    try {
      await axios.patch(
        `${config.public.baseURL}/api/v1/user/edit/${TastyBytes_user.value.user.id}`,
        {
          oldPassword: password.value,
          newPassword: newPassword.value,
        },
        {
          headers: { Authorization: `Bearer ${TastyBytes_user.value.token}` },
        }
      );

      window.scrollTo(0, 0);
      password.value = "";
      newPassword.value = "";
      newPasswordRepeat.value = "";
      addNotification("Password change was successful!", "Success");
    } catch (e: any) {
      if (/credentials/.test(e.response.data.message as string)) {
        errorsText.value = "Provided password is incorrect";
        return;
      }

      console.warn(e);
      addNotification(
        "There was an error when changing your password. Please try again.",
        "Error"
      );
    }
  }
});
</script>

<template>
  <div class="max-w-2xl m-auto flex flex-col gap-2 mt-4">
    <div>
        <div class="font-bold text-xl">Change password</div>
        <p class="text-sm font-base">Time me for a refresh? Update your password to keep your data secure.</p>
    </div>
    <div
      class="m-auto border border-concrete-400 rounded-sm p-4 w-full flex flex-col gap-2 shadow-[0_1px_2px_1px_#828282]"
    >
      <form @submit="onSubmit" class="flex flex-col items-start gap-3">
        <div class="w-full text-left">
          <div>
            <input
              type="text"
              name="email"
              value=""
              autocomplete="email"
              class="hidden"
            />
          </div>
          <div class="flex gap-2 items-center flex-row">
            <label class="font-semibold text-sm">Current password</label>
            <span class="text-red-600 text-sm">{{
              errors.password ? errors.password : errorsText ? errorsText : null
            }}</span>
          </div>
          <input
            class="w-full bg-concrete-50 hover:bg-concrete-100 focus:bg-concrete-100 px-2 py-2 focus:border-concrete-300 border-2 border-concrete-50 transition-colors duration-150 rounded-sm text-gray-950 outline-none"
            :class="errorsText ? '!border-red-600' : null"
            type="password"
            @click="errorsText ? (errorsText = null) : null"
            placeholder="Current password"
            v-model="password"
            autocomplete="current-password"
          />
        </div>
        <div class="w-full text-left">
          <div class="flex gap-2 items-center flex-row">
            <label class="font-semibold text-sm">New password</label>
            <span class="text-red-600 text-sm">{{ errors.newPassword }}</span>
          </div>
          <input
            class="w-full bg-concrete-50 hover:bg-concrete-100 focus:bg-concrete-100 px-2 py-2 focus:border-concrete-300 border-2 border-concrete-50 transition-colors duration-150 rounded-sm text-gray-950 outline-none"
            type="password"
            placeholder="New password"
            v-model="newPassword"
            autocomplete="new-password"
          />
        </div>
        <div class="w-full text-left">
          <div class="flex gap-2 items-center flex-row">
            <label class="font-semibold text-sm">Repeat new password</label>
            <span class="text-red-600 text-sm">{{
              errors.newPasswordRepeat
            }}</span>
          </div>
          <input
            class="w-full bg-concrete-50 hover:bg-concrete-100 focus:bg-concrete-100 px-2 py-2 focus:border-concrete-300 focus:border-solid border-2 border-concrete-50 transition-colors duration-150 rounded-sm text-gray-950 outline-none"
            type="password"
            placeholder="New password"
            v-model="newPasswordRepeat"
            autocomplete="new-password"
          />
        </div>
        <button
          class="w-full bg-whiskey-300 py-2 rounded-sm font-medium hover:bg-whiskey-400 transition-colors duration-100"
          type="submit"
        >
          Save changes
        </button>
      </form>
    </div>
  </div>
</template>

<style scoped></style>
