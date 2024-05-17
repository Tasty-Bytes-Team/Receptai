<template>
  <NuxtLink
    :to="link"
    class="recipe-banner relative mr-4 transition-all duration-300 group"
  >
    <NuxtImg
      :src="!error ? imageLink : '/assets/TastyBytes_Fallback.webp'"
      :style="{ width: `${width}px`, height: `${height}px` }"
      class="m-auto rounded-md object-cover bg-concrete-200"
      @error="() => (error = true)"
      data-testid="recipe-image"
    />
    <div
      v-if="showDarkArea"
      :style="{ width: `${width}px`, height: `${height}px` }"
      class="absolute top-0 left-0 bg-black/70 opacity-0 group-hover:opacity-100 transition-opacity duration-300 flex flex-col items-start justify-between p-4"
    >
      <h3 class="text-white text-base font-bold truncate max-w-[170px]">
        {{ truncateText(name, 25) }}
      </h3>
      <p class="text-white text-sm mt-2 max-h-[60px] overflow-hidden text-ellipsis">
        {{ truncateText(about, 80) }}
      </p>
    </div>
  </NuxtLink>
</template>

<script setup lang="ts">
const props = defineProps({
  imageLink: String,
  link: String,
  about: String,
  name: String,
  error: Boolean,
  width: Number,
  height: Number,
  showDarkArea: { type: Boolean, default: true}
});

const error = ref(false);

function truncateText(text: string | undefined, maxLength: number): string {
  if (!text) {
    return '';
  }
  if (text.length <= maxLength) {
    return text;
  }
  return `${text.slice(0, maxLength - 3)}...`;
}
</script>
