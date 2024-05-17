<script setup lang="ts">
import FeaturedNumber from "@/components/admin/Dashboard/FeaturedNumber.vue";
import type { UserCookie } from "@/typescript/types";
import axios from "axios";

definePageMeta({
  layout: "admin",
  middleware: ["admin"],
});

const config = useRuntimeConfig();

const TastyBytes_user = useCookie<UserCookie | null>("TastyBytes_user");

const totalRecipeCount = ref(0);
const totalReviewCount = ref(0);
const totalUserCount = ref(0);

const getDashboardInfo = async () => {
  try {
    await axios
      .get(`${config.public.baseURL}/api/v1/user/admin/dashboard`, {
        headers: { Authorization: `Bearer ${TastyBytes_user.value?.token}` },
      })
      .then((res) => {
        totalRecipeCount.value = res.data.totalRecipeCount;
        totalReviewCount.value = res.data.totalReviewCount;
        totalUserCount.value = res.data.totalUserCount;
      });
  } catch (e) {
    console.error("Error fetching my recipes", e);
  }
};

getDashboardInfo();
</script>

<template>
  <div>
    <div class="mb-4 p-2 border-b-2">
      <h1 class="text-4xl font-bold text-gray-800">Admin dashboard</h1>
      <p class="text-lg text-gray-600">
        Welcome back, {{ TastyBytes_user?.user.name }}!
      </p>
    </div>
    <div class="flex flex-col gap-4 p-2">
      <h2 class="text-2xl font-semibold text-gray-800">Featured numbers</h2>
      <div class="flex flex-row flex-wrap justify-around gap-4">
        <FeaturedNumber
          heading="Total recipes"
          :featured-number="totalRecipeCount"
          button-text="All recipes"
          button-link="/user/admin/dashboard/all-recipes"
        />
        <FeaturedNumber
          heading="Total Reviews"
          :featured-number="totalReviewCount"
        />
        <FeaturedNumber
          heading="Total Users"
          :featured-number="totalUserCount"
        />
      </div>
    </div>
  </div>
</template>

<style scoped></style>
