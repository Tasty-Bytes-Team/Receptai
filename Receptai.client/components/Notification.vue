<script setup lang="ts">
import { store } from "@/store/store.ts";

interface Message {
  text: string;
}

let timeoutId: ReturnType<typeof setTimeout> | null = null;

function cancelTimeout() {
  if (timeoutId) {
    clearTimeout(timeoutId);
    timeoutId = null;
    store.show = false;
  }
}

watch(store, (newVal, oldVal) => {
  if (newVal.show === true) {
    if (timeoutId) {
      clearTimeout(timeoutId); // Clear any existing timeout
    }
    timeoutId = setTimeout(() => {
      store.show = false;
    }, 8000);
  } else if (newVal.show === false && timeoutId) {
    cancelTimeout(); // Clear timeout if store.show becomes false
  }
});
</script>

<template>
  <div
    v-if="store.show"
    id="toast-default"
    class="flex items-center w-full max-w-xs p-4 text-gray-500 bg-white rounded-lg shadow fixed bottom-2 right-2"
    role="alert"
  >
    <div class="ms-3 text-sm font-normal">{{ store.text }}</div>
    <button
      @click="cancelTimeout()"
      type="button"
      class="ms-auto -mx-1.5 -my-1.5 bg-white text-gray-400 hover:text-gray-900 rounded-lg focus:ring-2 focus:ring-gray-300 p-1.5 hover:bg-gray-100 inline-flex items-center justify-center h-8 w-8"
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
