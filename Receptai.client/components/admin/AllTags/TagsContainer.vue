<template>
  <div v-if="loading">
    <div role="status" class="flex justify-center items-center my-2">
      <img
        src="/assets/loader.svg"
        alt="Recipe loader"
        class="w-9 h-9 animate-spin"
      />
      <span class="sr-only">Loading...</span>
    </div>
  </div>
  <div v-else class="relative overflow-x-auto">
    <table class="w-full text-sm text-left rtl:text-right">
      <thead class="text-sm bg-concrete-100">
        <tr>
          <th
            scope="col"
            class="px-3 py-3 border-concrete-300 border-2 text-center"
          >
            ID
          </th>
          <th
            scope="col"
            class="px-3 py-3 border-concrete-300 border-2 text-center"
          >
            Tag name
          </th>
          <th
            scope="col"
            class="px-3 py-3 border-concrete-300 border-2 text-center"
          >
            Tag icon
          </th>
          <th
            scope="col"
            class="px-3 py-3 border-concrete-300 border-2 text-center"
          >
            Action
          </th>
        </tr>
      </thead>
      <tbody>
        <SingleTag v-for="tag in tags" :tag="tag" />
      </tbody>
    </table>
    <div class="w-full text-center" v-if="totalPages > 0">
      <Pagination
        @change="getTags"
        v-model="pageNumber"
        :totalPages
        :siblings
      />
    </div>
  </div>
</template>

<script setup lang="ts">
import SingleTag from "./SingleTag.vue";
import axios from "axios";
import Pagination from "~/components/Pagination/Pagination.vue";
import type { Tag, UserCookie } from "@/typescript/types";

const config = useRuntimeConfig();
const TastyBytes_user = useCookie<UserCookie | null>("TastyBytes_user");

const tags = ref<Tag[] | null>(null);
const loading = ref(true);

const pageNumber = ref(0);
const totalPages = ref(0);
const siblings = 2;

const getTags = async () => {
  try {
    const result = await axios.get(`${config.public.baseURL}/api/v1/tag/list`);
    tags.value = result.data.elements;
    totalPages.value = result.data.totalPageCount;
  } catch (e) {
    console.log(e);
  } finally {
    loading.value = false;
  }
};

getTags();
</script>

<style scoped></style>
