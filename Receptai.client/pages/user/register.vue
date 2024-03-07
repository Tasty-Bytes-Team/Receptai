<script setup lang="ts">
import axios from "axios";
import passwordCheck from "@/typescript/passwordCheck";

const config = useRuntimeConfig();

definePageMeta({
  middleware: "to-dashboard",
});

const email: Ref<string> = ref("");
const name: Ref<string> = ref("");
const password: Ref<string> = ref("");
const repeatPassword: Ref<string> = ref("");

const passwordsMatch: Ref<boolean> = ref(true);

const error: Ref<boolean> = ref(false);
const errorText: Ref<string> = ref("");

const handleSubmit = async () => {
  if (!passwordCheck(password.value)) {
    errorText.value =
      "Password must be at least 8 characters long and include a lowercase letter, an uppercase letter, a number, and a special symbol.";
    error.value = true;
    return;
  }

  if (password.value !== repeatPassword.value) {
    passwordsMatch.value = false;
    errorText.value = "Passwords must match.";
    error.value = true;
    return;
  }

  passwordsMatch.value = true;
  error.value = false;

  try {
    await axios.post(`${config.public.baseURL}/api/v1/user/register`, {
      name: name.value,
      email: email.value,
      password: password.value,
    });

    navigateTo("/user/login");
  } catch (e) {
    console.error("Error during registration:", e);
    errorText.value = "An error occurred during registration.";
    error.value = true;
  }
};
</script>

<template>
  <div class="text-center w-96 m-auto">
    <h1 class="text-3xl font-bold uppercase mb-4">Register</h1>
    <div v-if="error" class="border-2 border-red-600 my-3 p-2 text-red-600">
      <p>{{ errorText }}</p>
    </div>
    <form
      @submit.prevent="handleSubmit"
      class="flex flex-col items-start gap-3"
    >
      <div class="w-full text-left">
        <label class="font-semibold text-sm">Name</label>
        <input
          class="outline-none w-full p-2 px-5 bg-[#f9f9f9] rounded-sm border-2 border-[#cbcbcb] transition-colors duration-150 focus:border-black"
          type="text"
          required
          v-model="name"
          autocomplete="name"
        />
      </div>
      <div class="w-full text-left">
        <label class="font-semibold text-sm">Email</label>
        <input
          class="outline-none w-full p-2 px-5 bg-[#f9f9f9] rounded-sm border-2 border-[#cbcbcb] transition-colors duration-150 focus:border-black"
          type="email"
          required
          v-model="email"
          autocomplete="email"
        />
      </div>
      <div class="w-full text-left">
        <label class="font-semibold text-sm">Password</label>
        <input
          class="outline-none w-full p-2 px-5 placeholder:text-[#666666] bg-[#f9f9f9] rounded-sm border-2 transition-colors duration-150 focus:border-black"
          :class="!passwordsMatch ? 'border-red-600' : 'border-[#cbcbcb]'"
          placeholder="At least six character"
          type="password"
          required
          minlength="8"
          v-model="password"
          autocomplete="new-password"
        />
        <label class="text-sm"
          >Password must include at least one capital letter, number and special
          symbol ($@#&!?*-~.,/;:).</label
        >
      </div>
      <div class="w-full text-left">
        <label>Re-enter password</label>
        <input
          class="outline-none w-full p-2 px-5 bg-[#f9f9f9] rounded-sm border-2 transition-colors duration-150 focus:border-black"
          :class="!passwordsMatch ? 'border-red-600' : 'border-[#cbcbcb]'"
          type="password"
          required
          v-model="repeatPassword"
          autocomplete="new-password"
        />
      </div>
      <button
        type="submit"
        class="bg-[#f8ceb7] p-2 w-full rounded-lg drop-shadow-md font-semibold transition-colors duration-200 hover:bg-[#f0bb9e]"
      >
        Register
      </button>
    </form>
    <div>
      <div class="separator my-4">Already have an account?</div>
      <NuxtLink to="/user/login">
        <button
          type="submit"
          class="bg-white p-2 w-full border-2 border-[#c5c5c5] rounded-lg drop-shadow-md font-normal text-sm transition-colors duration-200 hover:bg-[#efefef]"
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
