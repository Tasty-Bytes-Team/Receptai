<script setup>
import axios from "axios";

const email = ref("");
const password = ref("");

let error = ref(false);
let errorText = ref("");

const handleSubmit = async () => {
  error.value = false; // Clear existing errors before making a new attempt

  try {
    const response = await axios.post("/api/v1/user/login", {
      email: email.value, // Coerce to string
      password: password.value, // Coerce to string
    }, {
      headers: {
        'Content-Type': 'application/json',
      },
    });

    if (response.status === 200) {
      console.log('Login successful:', response.data);
      localStorage.setItem("TastyBytes_user", JSON.stringify(response.data));
      console.log(JSON.parse(localStorage.getItem("TastyBytes_user")))
      console.log(localStorage.getItem("TastyBytes"))
      await navigateTo("/user/dashboard")
    } else {
      error.value = true;
      errorText.value = "Login failed: " + response.data.message || "An error occurred.";
    }
  } catch (error) {
    console.error('Error during login:', error);
    error.value = true;
    errorText.value = "An error occurred during login.";
  }
};
</script>

<template>
  <div class="text-center w-96 m-auto">
    <h1 class="text-3xl font-bold uppercase mb-3">Log in</h1>
    <div v-if="error" class="border-2 border-red-600 my-3 p-2 text-red-600">
      <p>{{ errorText }}</p>
    </div>
    <form
      @submit.prevent="handleSubmit"
      class="flex flex-col items-start gap-3"
    >
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
          class="outline-none w-full p-2 px-5 bg-[#f9f9f9] rounded-sm border-2 border-[#cbcbcb] transition-colors duration-150 focus:border-black"
          type="password"
          required
          v-model="password"
          autocomplete="current-password"
        />
      </div>
      <button
        type="submit"
        class="bg-[#f8ceb7] p-2 w-full rounded-lg drop-shadow-md font-semibold transition-colors duration-200 hover:bg-[#f0bb9e]"
      >
        Log in
      </button>
    </form>
    <div>
      <div class="separator my-5">New to Tasty Bytes?</div>
      <NuxtLink to="/user/register">
        <button
          type="submit"
          class="bg-white p-2 w-full border-2 border-[#c5c5c5] rounded-lg drop-shadow-md font-normal text-sm transition-colors duration-200 hover:bg-[#efefef]"
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
