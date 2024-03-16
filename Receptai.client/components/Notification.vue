<script setup lang="ts">
import { store, resetNotification } from "@/store/store.ts";

let timeoutId: ReturnType<typeof setTimeout> | null = null;

function cancelTimeout() {
  if (timeoutId) {
    clearTimeout(timeoutId);
    resetNotification();
  }
}

watch(store, (newVal, oldVal) => {
  if (newVal.show === true) {
    if (timeoutId) {
      clearTimeout(timeoutId);
    }
    timeoutId = setTimeout(() => {
      resetNotification();
    }, 6000);
  } else if (newVal.show === false && timeoutId) {
    cancelTimeout();
  }
});
</script>

<template>
  <div
    v-if="store.show"
    id="toast-default"
    class="flex items-center w-full max-w-xs p-4 text-gray-600 bg-white rounded-lg shadow-md shadow-[#00000085] fixed bottom-2 right-2 border-2 border-black"
    :class="
      store.label === 'Error'
        ? '!border-red-600 !shadow-[#d9000085]'
        : store.label === 'Success'
        ? '!border-emerald-500 !shadow-emerald-400'
        : null
    "
    role="alert"
  >
    <div class="flex flex-col items-start gap-1 w-10/12">
      <span class="text-md font-normal">{{ store.text }}</span>
      <div v-if="store.links" class="w-full flex gap-2">
        <button
          v-for="link in store.links"
          class="px-6 py-1 rounded-md bg-black text-white text-sm font-medium w-full hover:bg-concrete-900 transition-colors duration-150"
          :class="
            link.type === 'Gray'
              ? '!bg-concrete-100 !text-black hover:!bg-concrete-200'
              : null
          "
          @click="navigateTo(link.link)"
        >
          {{ link.text }}
        </button>
      </div>
    </div>
    <button
      @click="cancelTimeout()"
      type="button"
      class="ms-auto -mx-1.5 -my-1.5 bg-white text-gray-500 hover:text-gray-900 rounded-lg focus:ring-2 focus:ring-gray-300 p-1.5 hover:bg-gray-100 inline-flex items-center justify-center h-8 w-8"
      :class="
        store.label === 'Error'
          ? 'hover:!shadow-sm hover:!shadow-[#d9000085] focus:!ring-red-600'
          : store.label === 'Success'
          ? 'hover:!shadow-sm hover:!shadow-emerald-400 focus:!ring-emerald-500'
          : null
      "
      data-dismiss-target="#toast-default"
      aria-label="Close"
    >
      <span class="sr-only">Close</span>
      <svg
        class="w-3 h-3"
        aria-hidden="true"
        xmlns="http://www.w3.org/2000/svg"
        fill="none"
        viewBox="0 0 14 14"
      >
        <path
          stroke="currentColor"
          stroke-linecap="round"
          stroke-linejoin="round"
          stroke-width="2"
          d="m1 1 6 6m0 0 6 6M7 7l6-6M7 7l-6 6"
        />
      </svg>
    </button>
  </div>
</template>

<style scoped></style>
