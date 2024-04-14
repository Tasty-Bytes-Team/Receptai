<script setup lang="ts">
const search = ref<null | string>(null);

const onSearch = async () => {
  await navigateTo({
    path: "/search",
    query: {
      s: search.value,
    },
    replace: true,
  });
  search.value = null;
};
</script>

<template>
  <form class="w-full" @submit.prevent="onSearch">
    <label
      for="default-search"
      class="mb-2 text-sm font-medium text-gray-900 sr-only"
      >Search</label
    >
    <div class="relative">
      <input
        v-model="search"
        type="search"
        class="block w-full p-3 text-sm text-gray-900 border-2 border-concrete-300 focus:border-black outline-none rounded-lg bg-gray-50"
        placeholder="Search recipes..."
        required
      />
      <div class="absolute right-2 top-0 h-full flex justify-center">
        <button
          type="submit"
          class="text-black my-2 p-2 bg-whiskey-100 hover:bg-whiskey-200 rounded-lg flex justify-center items-center transition-colors duration-100"
        >
          <Icon name="material-symbols:search-rounded" size="20px" />
        </button>
      </div>
    </div>
  </form>
</template>

<style scoped></style>
