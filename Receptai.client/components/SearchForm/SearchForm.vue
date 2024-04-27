<script setup lang="ts">
const search = ref<null | string>(null);

const onSearch = async () => {
  const toSearch = search.value?.toString().trim().toLowerCase();

  if (toSearch && toSearch.length > 0) {
    await navigateTo({
      path: "/search",
      query: {
        s: toSearch,
      },
      replace: true,
    });
    search.value = null;
  }
};
</script>

<template>
  <form class="w-full" @submit.prevent="onSearch">
    <label
      for="default-search"
      class="mb-2 text-sm font-medium text-gray-900 sr-only"
      >Search</label
    >
    <div
      class="flex flex-row p-1 gap-1 text-gray-900 border-2 border-concrete-300 focus-within:border-black rounded-md bg-gray-50"
    >
      <input
        v-model="search"
        type="search"
        class="outline-none w-full"
        placeholder="Search recipes..."
      />
      <button
        type="submit"
        class="text-black w-9 aspect-square bg-whiskey-200 hover:bg-whiskey-300 rounded-lg flex justify-center items-center transition-colors duration-100"
      >
        <Icon name="material-symbols:search-rounded" size="20px" />
      </button>
    </div>
  </form>
</template>

<style scoped></style>
