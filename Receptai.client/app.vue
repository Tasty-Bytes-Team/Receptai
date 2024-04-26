<script setup lang="ts">
import axios from "axios";

import Notification from "@/components/Notification.vue";

// https://stackoverflow.com/a/65177317
// Some code is taken from middleware/auth.ts
// In the perfect world, the middleware would work,
// but it doesn't appear to.

const config = useRuntimeConfig();
const TastyBytes_user = useCookie<UserCookie | null>("TastyBytes_user");

if (TastyBytes_user.value) {
  const userObj: UserCookie = TastyBytes_user.value;

  try {
    axios
      .get(`${config.public.baseURL}/api/v1/user/me`, {
        headers: { Authorization: `Bearer ${userObj.token}` },
      })
      .then((res) => {
        // Jei pavyko, atnaujinam duomenis
        TastyBytes_user.value.user = res.data;
      })
      .catch(async () => {
        // Jei nepavyko, trinam duomenis lauk
        TastyBytes_user.value = null;
      });
  } catch (e) {
    // Jei nepavyko, trinam duomenis lauk
    TastyBytes_user.value = null;
  }
}

</script>

<template>
  <NuxtLoadingIndicator
    color="repeating-linear-gradient(to right,#debc93 0%,#c68249 50%,#995635 100%)"
    class="!opacity-100"
  />
  <NuxtLayout>
    <NuxtPage />
  </NuxtLayout>

  <Notification />
</template>

<style lang="scss" scoped></style>
