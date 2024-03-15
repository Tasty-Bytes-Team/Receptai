<script setup lang="ts">
import axios from "axios";
import { Field, Form, ErrorMessage, useForm, useField } from "vee-validate";
import { toTypedSchema } from "@vee-validate/zod";
import * as zod from "zod";

import { store } from "@/store/store";

import ErrorBaner from "@/components/Error/ErrorBaner.vue";

const config = useRuntimeConfig();

definePageMeta({
  middleware: "to-dashboard",
});

const error: Ref<boolean> = ref(false);
const errorText: Ref<string> = ref("");

const validationSchema = toTypedSchema(
  zod
    .object({
      name: zod.string().min(1, "This is required"),
      email: zod
        .string()
        .min(1, "This is required")
        .email({ message: "Must be a valid email" }),
      password: zod
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
      repeatPassword: zod.string().min(1, "This is required"),
    })
    .refine((data) => data.password === data.repeatPassword, {
      message: "Passwords do not match",
      path: ["repeatPassword"],
    })
);

const { handleSubmit, errors } = useForm({
  validationSchema,
});

const { value: name } = useField("name");
const { value: email } = useField("email");
const { value: password } = useField("password");
const { value: repeatPassword } = useField("repeatPassword");

const onSubmit = handleSubmit(async () => {
  error.value = false;

  try {
    await axios.post(`${config.public.baseURL}/api/v1/user/register`, {
      name: name.value,
      email: email.value,
      password: password.value,
    });

    navigateTo("/user/login");

    store.text =
      "Welcome to Tasty Bytes! Your account has been successfully created. Now, please login.";
    store.show = true;
    store.label = "Success";
  } catch (e) {
    console.error("Error during registration:", e);
    errorText.value = "An error occurred during registration.";
    error.value = true;

    window?.scrollTo(0, 0);

    store.text =
      "Oops! Something went wrong with your registration. Please try again.";
    store.show = true;
    store.label = "Error";
  }
});
</script>

<template>
  <div class="text-center w-96 m-auto">
    <h1 class="text-3xl font-bold uppercase mb-4">Register</h1>
    <ErrorBaner v-if="error" :error-text="errorText" />
    <form @submit="onSubmit" class="flex flex-col items-start gap-3">
      <div class="w-full text-left">
        <div class="flex gap-2 items-center flex-row">
          <label class="font-semibold text-sm">Name</label>
          <span class="text-red-600 text-sm">{{ errors.name }}</span>
        </div>
        <input
          class="outline-none w-full p-2 px-3 placeholder:text-concrete-400 bg-concrete-50 rounded-sm border-2 border-concrete-400 transition-colors duration-150 focus:border-black"
          type="text"
          placeholder="Name"
          v-model="name"
          autocomplete="name"
        />
      </div>
      <div class="w-full text-left">
        <div class="flex gap-2 items-center flex-row">
          <label class="font-semibold text-sm">Email</label>
          <span class="text-red-600 text-sm">{{ errors.email }}</span>
        </div>
        <input
          class="outline-none w-full p-2 px-3 placeholder:text-concrete-400 bg-concrete-50 rounded-sm border-2 border-concrete-400 transition-colors duration-150 focus:border-black"
          type="email"
          placeholder="Email"
          v-model="email"
          autocomplete="email"
        />
      </div>
      <div class="w-full text-left">
        <div class="flex gap-2 items-center flex-row">
          <label class="font-semibold text-sm">Password</label>
          <span class="text-red-600 text-sm">{{ errors.password }}</span>
        </div>
        <input
          class="outline-none w-full p-2 px-3 placeholder:text-concrete-400 bg-concrete-50 rounded-sm border-2 border-concrete-400 transition-colors duration-150 focus:border-black"
          placeholder="Password"
          type="password"
          v-model="password"
          autocomplete="new-password"
        />
      </div>
      <div class="w-full text-left">
        <div class="flex gap-2 items-center flex-row">
          <label class="font-semibold text-sm">Re-enter password</label>
          <span class="text-red-600 text-sm">{{ errors.repeatPassword }}</span>
        </div>
        <input
          class="outline-none w-full p-2 px-3 placeholder:text-concrete-400 bg-concrete-50 rounded-sm border-2 border-concrete-400 transition-colors duration-150 focus:border-black"
          type="password"
          placeholder="Password"
          v-model="repeatPassword"
          autocomplete="new-password"
        />
      </div>
      <button
        type="submit"
        class="bg-whiskey-300 p-2 w-full rounded-sm shadow-[3px_3px_0_0_#bdbdbd] font-semibold transition-colors duration-200 hover:bg-whiskey-400"
      >
        Register
      </button>
    </form>
    <div>
      <div class="separator my-4">Already have an account?</div>
      <NuxtLink to="/user/login">
        <button
          type="submit"
          class="bg-concrete-800 text-white p-2 w-[50%] min-w-20 rounded-full drop-shadow-md font-normal text-sm transition-colors duration-200 hover:bg-concrete-900"
        >
          Log in
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
