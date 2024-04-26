<script setup lang="ts">
const props = defineProps<{
  setRaiting?: number;
}>();

const readOnly = ref(false);
const stars = ref([
  {
    raiting: 1,
    active: false,
  },
  {
    raiting: 2,
    active: false,
  },
  {
    raiting: 3,
    active: false,
  },
  {
    raiting: 4,
    active: false,
  },
  {
    raiting: 5,
    active: false,
  },
]);

const updateStarRaiting = (raiting: number) => {
  stars.value.map((star) => {
    star.raiting <= raiting ? (star.active = true) : (star.active = false);
  });
};

if (props.setRaiting != undefined) {
  readOnly.value = true;
  updateStarRaiting(props.setRaiting);
}
</script>

<template>
  <div>
    <Icon
      v-if="!readOnly"
      v-for="star1 in stars"
      @click="readOnly ? null : updateStarRaiting(star1.raiting)"
      :name="star1.active ? 'streamline:star-1-solid' : 'streamline:star-1'"
      :key="star1.raiting"
      class="cursor-pointer"
      :class="star1.active ? '!text-yellow-400' : null"
    />
    <Icon
      v-else
      v-for="star2 in stars"
      :name="star2.active ? 'streamline:star-1-solid' : 'streamline:star-1'"
      :key="star2.raiting"
      :class="star2.active ? '!text-yellow-400' : null"
    />
  </div>
</template>

<style scoped></style>
