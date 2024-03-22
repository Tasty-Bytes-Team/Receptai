<script setup lang="ts"></script>

<template>
  <div
    class="fixed top-0 right-0 left-0 w-full h-full flex justify-center items-center z-50 bg-[#7d7d7d82]"
  >
    <div class="relative p-6 text-center bg-white rounded-md shadow sm:p-7">
      <button
        @click="$emit('cancel')"
        type="button"
        class="text-concrete-500 hover:text-concrete-800 transition-all duration-100 absolute top-2.5 right-2.5 bg-transparent rounded-md m-1 ml-auto inline-flex items-center hover:bg-gray-200 hover:ring-4 hover:ring-gray-200 hover:rounded-sm outline-none hover:z-10"
      >
        <Icon name="material-symbols:close-rounded" size="22px" />
      </button>
      <Icon
        name="material-symbols:delete-outline"
        class="text-concrete-800"
        size="60px"
      />
      <p class="my-4 text-black">
        Are you sure you want to delete this recipe?
      </p>
      <div class="flex justify-center items-center gap-3">
        <button
          @click="$emit('cancel')"
          type="button"
          class="w-full py-2 text-sm font-medium text-concrete-600 bg-white rounded-md border-2 border-concrete-300 hover:bg-concrete-100 hover:text-concrete-900 transition-colors duration-75"
        >
          No, cancel
        </button>
        <button
          @click="$emit('confirm')"
          type="submit"
          class="w-full py-2 text-sm font-medium text-white bg-red-600 rounded-md hover:bg-red-700 border-red-600 border-2 transition-colors duration-75"
        >
          Yes, I'm sure
        </button>
      </div>
    </div>
  </div>
</template>

<style scoped></style>
