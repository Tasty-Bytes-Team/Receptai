<script setup>
definePageMeta({
  middleware: 'to-dashboard'
});
import axios from "axios";

const email = ref("");
const name = ref("");
const password = ref("");
const repeatPassword = ref("");

const passwordsMatch = ref(true);

let error = ref(false);
let errorText = ref("");

const passwordCheck = () =>
  password.value.match(/[a-z]+/) &&
  password.value.match(/[A-Z]+/) &&
  password.value.match(/[0-9]+/) &&
  password.value.match(/[$@#&!?*-~.,/;:]+/);

  const handleSubmit = async () => {
  if (!passwordCheck()) {
    error.value = true;
    errorText.value = 'Password must be at least 8 characters long and include a lowercase letter, an uppercase letter, a number, and a special symbol.';
    return;
  }

  if (password.value !== repeatPassword.value) {
    passwordsMatch.value = false;
    error.value = true;
    errorText.value = 'Passwords must match.';
    return;
  }

  error.value = false;

  try {
    const response = await axios.post('/api/v1/user/register', {
      name: name.value,
      email: email.value,
      password: password.value,
    }, {
      headers: {
        'Content-Type': 'application/json',
      },
    });

    if (response.status === 200) {
      console.log('Registration successful:', response.data);
      await navigateTo("/user/login")
    } else {
      error.value = true;
      errorText.value = 'Registration failed: ' + response.data.message || 'An error occurred.';
    }
  } catch (error) {
    console.error('Error during registration:', error);
    error.value = true;
    errorText.value = 'An error occurred during registration.';
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
