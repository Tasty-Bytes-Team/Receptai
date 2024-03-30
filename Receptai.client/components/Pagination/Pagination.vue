<script setup lang="ts">
import PageNumber from "./components/PageNumber.vue";
import HidenPagesDots from "./components/HidenPagesDots.vue";

const props = defineProps<{
  totalPages: number;
  siblings: number;
}>();

const emit = defineEmits(["change"]);

const pageNumber = defineModel<number>({ required: true, default: 0 });

const onLast = () => {
  pageNumber.value = props.totalPages - 1;
  emit("change");
};

const onFirst = () => {
  pageNumber.value = 0;
  emit("change");
};

const onSelectedPage = (page: number) => {
  pageNumber.value = page - 1;
  emit("change");
};
</script>

<template>
  <nav aria-label="Page navigation" v-if="totalPages > 1">
    <ul class="inline-flex -space-x-px text-base h-10">
      <li>
        <button
          @click="onFirst"
          class="flex items-center gap-2 justify-center px-4 h-10 ms-0 leading-tight text-gray-800 bg-white border border-e-0 border-gray-300 rounded-s-lg hover:bg-gray-100 hover:text-gray-800"
          :class="pageNumber + 1 <= 1 ? '!bg-gray-200 !text-gray-900' : null"
          :disabled="pageNumber + 1 <= 1"
        >
          <Icon
            name="material-symbols:keyboard-double-arrow-left-rounded"
            color="#374151"
            size="18px"
          />
          <span>First</span>
        </button>
      </li>
      <HidenPagesDots v-if="pageNumber + 1 - siblings > 1" />
      <PageNumber
        v-for="page in totalPages"
        :pageNumber
        :siblings
        :page
        @onSelectedPage="onSelectedPage(page)"
      />
      <HidenPagesDots v-if="pageNumber + 1 + siblings < totalPages" />
      <li>
        <button
          @click="onLast"
          class="flex items-center gap-2 justify-center px-4 h-10 leading-tight text-gray-800 bg-white border border-gray-300 rounded-e-lg hover:bg-gray-100 hover:text-gray-900"
          :class="
            pageNumber + 1 >= totalPages ? '!bg-gray-200 !text-gray-800' : null
          "
          :disabled="pageNumber + 1 >= totalPages"
        >
          <span>Last</span>
          <Icon
            name="material-symbols:keyboard-double-arrow-right-rounded"
            color="#374151"
            size="18px"
          />
        </button>
      </li>
    </ul>
  </nav>
</template>

<style scoped></style>
