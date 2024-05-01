<template>
  <div :class="widgetClasses">
    <!-- Widget Header -->
    <div class=" flex justify-center items-center">
      <h3 :class="titleClass">{{ title }}</h3>
    </div>
    <!-- Content Area -->
    <slot></slot>
  </div>
</template>

<script setup>
import { computed } from 'vue';

const props = defineProps({
  title: String,
  orientation: { type: String, default: 'square' },
  class: String
});

const widgetClasses = computed(() => {
  let baseClasses = 'widget bg-white shadow-lg rounded-lg overflow-hidden m-4 transition-all';

  if (props.class) {
    baseClasses += ` ${props.class}`;
  }

  switch (props.orientation) {
    case 'square':
      // Consistently square, slightly larger on bigger screens
      return `${baseClasses} w-32 h-32 lg:w-48 lg:h-48 sm:text-sm md:text-md lg:text-lg`;
    case 'vertical':
      // Taller as screens grow larger, text size grows with height
      return `${baseClasses} w-full sm:w-3/4 md:w-1/2 lg:w-1/3 h-48 sm:h-64 md:h-80 lg:h-96 sm:text-sm md:text-md lg:text-lg`;
    case 'horizontal':
      // Responsive width and height with aggressive text scaling
      return `${baseClasses} w-48 sm:w-32 md:w-48 lg:w-5/6 h-24 sm:h-16 md:h-20 lg:h-24 text-5xl sm:text-3xl md:text-4xl lg:text-6xl`;
    default:
      return baseClasses;
  }
});

const titleClass = computed(() => {
  // Define responsive font sizes directly using Tailwind's responsive prefixes
  return "text-sm sm:text-md md:text-lg lg:text-xl xl:text-2xl font-semibold text-gray-800";
});
</script>
