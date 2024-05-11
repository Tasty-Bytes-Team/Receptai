<script setup lang="ts">
import axios from "axios";
import type { UserCookie } from "~/typescript/types";
import { Form, useForm, useField } from "vee-validate";
import { toTypedSchema } from "@vee-validate/zod";
import * as zod from "zod";

import { addNotification } from "@/store/store";
import ErrorBaner from "@/components/Error/ErrorBaner.vue";

const config = useRuntimeConfig();

definePageMeta({
  middleware: "to-dashboard",
});

const error: Ref<boolean> = ref(false);
const errorText: Ref<string> = ref("");

const validationSchema = toTypedSchema(
  zod.object({
    email: zod
      .string()
      .min(1, "This is required")
      .email({ message: "Must be a valid email" }),
    password: zod.string().min(1, "This is required"),
  })
);

const { handleSubmit, errors } = useForm({
  validationSchema,
});

const { value: email } = useField("email");
const { value: password } = useField("password");

const onSubmit = handleSubmit(async () => {
  error.value = false;
  errorText.value = "";

  try {
    const response = await axios.post(
      `${config.public.baseURL}/api/v1/user/login`,
      {
        email: email.value,
        password: password.value,
      }
    );

    const data: UserCookie = response.data;

    const TastyBytes_user = useCookie<UserCookie>("TastyBytes_user", {
      maxAge: data.expiresIn,
    });

    TastyBytes_user.value = data;

    navigateTo("/user/dashboard");

    addNotification("Welcome back! You're now logged in.", "Success", [
      { text: "My recipes", link: "/user/dashboard/my-recipes", type: "Black" },
      {
        text: "Create recipe",
        link: "/user/dashboard/my-recipes/create",
        type: "Gray",
      },
    ]);
  } catch (e: any) {
    if (/credentials/.test(e.response.data.message)) {
      errorText.value =
        "The email or password you entered is incorrect. Please check your credentials and try again.";
      error.value = true;
      window.scrollTo(0, 0);
      return;
    }

    console.warn("Error during login", e);
    errorText.value = "An error occurred during login.";
    error.value = true;
    window.scrollTo(0, 0);
  }
});
</script>

<template>
  <div class="text-center max-w-96 m-auto">
    <h1 class="text-3xl font-bold uppercase mb-3">Log in</h1>
    <ErrorBaner v-if="error" :errorText="errorText" />
    <form @submit="onSubmit" class="flex flex-col items-start gap-3">
      <div class="w-full text-left">
        <div class="flex gap-2 items-center flex-row">
          <label class="font-semibold text-sm">Email</label>
          <span class="text-red-600 text-sm">{{ errors.email }}</span>
        </div>
        <input
          class="outline-none w-full p-2 px-3 placeholder:text-concrete-400 bg-concrete-50 rounded-sm border-2 border-concrete-400 transition-colors duration-150 focus:border-black"
          name="email"
          type="email"
          placeholder="Email"
          autocomplete="email"
          v-model="email"
        />
      </div>
      <div class="w-full text-left">
        <div class="flex gap-2 items-center flex-row">
          <label class="font-semibold text-sm">Password</label>
          <span class="text-red-600 text-sm">{{ errors.password }}</span>
        </div>
        <input
          class="outline-none w-full p-2 px-3 placeholder:text-concrete-400 bg-concrete-50 rounded-sm border-2 border-concrete-400 transition-colors duration-150 focus:border-black"
          name="password"
          type="password"
          placeholder="Password"
          autocomplete="current-password"
          v-model="password"
        />
      </div>
      <button
        type="submit"
        class="bg-whiskey-300 p-2 w-full rounded-sm shadow-[3px_3px_0_0_#bdbdbd] font-semibold transition-colors duration-200 hover:bg-whiskey-400"
      >
        Log in
      </button>
    </form>
    <div>
      <div class="separator my-5">New to Tasty Bytes?</div>
      <NuxtLink to="/user/register">
        <button
          type="submit"
          class="bg-concrete-800 text-white p-2 w-[50%] min-w-20 rounded-full drop-shadow-md font-normal text-sm transition-colors duration-200 hover:bg-concrete-900"
        >
          Register
        </button>
      </NuxtLink>
    </div>
  </div>
</template>

<style scoped>
.separator {
  display: flex;
  align-items: center;
  text-align: center;
}

.separator::before,
.separator::after {
  content: "";
  flex: 1;
  border-bottom: 2px solid #000;
}

.separator:not(:empty)::before {
  margin-right: 0.25em;
}

.separator:not(:empty)::after {
  margin-left: 0.25em;
}
</style>
