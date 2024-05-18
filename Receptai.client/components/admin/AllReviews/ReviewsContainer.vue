<template>
  <ConfirmBox
    v-if="confirmBox"
    toBeDeletedText="review"
    @confirm="deleteReview"
    @cancel="confirmBox = !confirmBox"
  />
  <div v-if="loading">
    <div role="status" class="flex justify-center items-center my-2">
      <img src="/assets/loader.svg" alt="Loader" class="w-9 h-9 animate-spin" />
      <span class="sr-only">Loading...</span>
    </div>
  </div>
  <EmptyListInformation
    v-else-if="reviews && reviews.length === 0"
    description="Review list is currently empty."
  />
  <div v-else class="relative overflow-x-auto">
    <table class="w-full text-sm text-left rtl:text-right">
      <thead class="text-sm bg-concrete-100">
        <tr>
          <th
            scope="col"
            class="px-3 py-3 border-concrete-300 border-2 text-center min-w-14"
          >
            ID
          </th>
          <th
            scope="col"
            class="px-3 py-3 border-concrete-300 border-2 text-center min-w-28"
          >
            User
          </th>
          <th
            scope="col"
            class="px-3 py-3 border-concrete-300 border-2 text-center min-w-32"
          >
            Rating
          </th>
          <th
            scope="col"
            class="px-3 py-3 border-concrete-300 border-2 text-center min-w-48"
          >
            Review content
          </th>
          <th
            scope="col"
            class="px-3 py-3 border-concrete-300 border-2 text-center min-w-28"
          >
            Creation date
          </th>
          <th
            scope="col"
            class="px-3 py-3 border-concrete-300 border-2 text-center min-w-20"
          >
            Action
          </th>
        </tr>
      </thead>
      <tbody>
        <SingleReview
          v-for="review in reviews"
          :key="review.id"
          @delete="
            (value) => {
              confirmBox = true;
              toBeDeleted = value;
            }
          "
          :review="review"
        />
      </tbody>
    </table>
    <div class="w-full text-center" v-if="totalPages > 0">
      <Pagination
        @change="getReviews"
        v-model="pageNumber"
        :totalPages
        :siblings
      />
    </div>
  </div>
</template>

<script setup lang="ts">
import SingleReview from "./SingleReview.vue";
import axios from "axios";
import Pagination from "@/components/Pagination/Pagination.vue";
import type { ReviewInformation, UserCookie } from "@/typescript/types";
import EmptyListInformation from "@/components/EmptyListInformation.vue";
import ConfirmBox from "@/components/user/MyRecipes/components/ConfirmBox.vue";
import { addNotification } from "@/store/store";

const config = useRuntimeConfig();
const TastyBytes_user = useCookie<UserCookie | null>("TastyBytes_user");

const confirmBox = ref<boolean>(false);
const toBeDeleted = ref<number | null>(null);

const reviews = ref<ReviewInformation[] | null>(null);
const loading = ref(true);

const pageNumber = ref(0);
const totalPages = ref(0);
const siblings = 2;

const getReviews = async () => {
  try {
    const result = await axios.get(
      `${config.public.baseURL}/api/v1/feedback/list?page=${pageNumber.value}`
    );
    reviews.value = result.data.elements;
    totalPages.value = result.data.totalPageCount;
    loading.value = false;
  } catch (e) {
    console.error(e);
  }
};

const deleteReview = async () => {
  if (toBeDeleted.value === null) {
    confirmBox.value = false;
    return;
  }

  try {
    if (TastyBytes_user.value) {
      await axios.delete(
        `${config.public.baseURL}/api/v1/feedback/delete/${toBeDeleted.value}`,
        {
          headers: { Authorization: `Bearer ${TastyBytes_user.value.token}` },
        }
      );

      confirmBox.value = false;
      addNotification(`Review has been deleted!`, "Success");

      await getReviews();
    } else {
      addNotification(
        `You are not authorized to delete this review. Please log in again.`,
        "Error"
      );
      navigateTo("/user/login");
    }
  } catch (e) {
    console.error(e);
  }
};

getReviews();
</script>

<style scoped></style>
