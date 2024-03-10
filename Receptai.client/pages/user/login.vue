<script setup lang="ts">
import axios from "axios";

import ErrorBaner from "@/components/Error/ErrorBaner.vue";

const config = useRuntimeConfig();

definePageMeta({
  middleware: "to-dashboard",
});

interface User {
  name: string;
  email: string;
}

interface UserCookie {
  token: string;
  expiresIn: number;
  user: User;
}

const email: Ref<string> = ref("");
const password: Ref<string> = ref("");

const error: Ref<boolean> = ref(false);
const errorText: Ref<string> = ref("");

const handleSubmit = async () => {
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
  } catch (e) {
    console.error("Error during login", e);
    errorText.value = "An error occurred during login.";
    error.value = true;
  }
};
</script>

<template>
  <div class="text-center w-96 m-auto">
    <h1 class="text-3xl font-bold uppercase mb-3">Log in</h1>
    <ErrorBaner v-if="error" :errorText="errorText" />
    <form
      @submit.prevent="handleSubmit"
      class="flex flex-col items-start gap-3"
    >
      <div class="w-full text-left">
        <label class="font-semibold text-sm">Email</label>
        <input
          class="outline-none w-full p-2 px-3 placeholder:text-concrete-400 bg-concrete-50 rounded-sm border-2 border-concrete-400 transition-colors duration-150 focus:border-black"
          type="email"
          placeholder="Email"
          required
          v-model="email"
          autocomplete="email"
        />
      </div>
      <div class="w-full text-left">
        <label class="font-semibold text-sm">Password</label>
        <input
          class="outline-none w-full p-2 px-3 placeholder:text-concrete-400 bg-concrete-50 rounded-sm border-2 border-concrete-400 transition-colors duration-150 focus:border-black"
          type="password"
          placeholder="Password"
          required
          v-model="password"
          autocomplete="current-password"
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
