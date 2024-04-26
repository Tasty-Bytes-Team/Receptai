<script setup lang="ts">
const props = defineProps<{
  setRating?: number;
}>();

const readOnly = ref(false);
const stars = ref([
  {
    rating: 1,
    active: false,
  },
  {
    rating: 2,
    active: false,
  },
  {
    rating: 3,
    active: false,
  },
  {
    rating: 4,
    active: false,
  },
  {
    rating: 5,
    active: false,
  },
]);

const model = defineModel<number | null>();

const updateModel = (rating: number) => {
  model.value = rating;
};

const updateStarRaiting = (rating: number) => {
  stars.value.map((star) => {
    star.rating <= rating ? (star.active = true) : (star.active = false);
  });
};

if (props.setRating != undefined) {
  readOnly.value = true;
  updateStarRaiting(props.setRating);
}

watch(model, (newVal, oldVal) => {
  let raitingToSet = 0;
  if (newVal){
    raitingToSet = newVal;
  }

  updateStarRaiting(raitingToSet);
})
</script>

<template>
  <div>
    <Icon
      v-if="!readOnly"
      v-for="star1 in stars"
      @click="readOnly ? null : updateModel(star1.rating)"
      :name="star1.active ? 'streamline:star-1-solid' : 'streamline:star-1'"
      :key="star1.rating"
      class="cursor-pointer"
      size="18px"
      :class="star1.active ? '!text-yellow-400' : null"
    />
    <Icon
      v-else
      v-for="star2 in stars"
      :name="star2.active ? 'streamline:star-1-solid' : 'streamline:star-1'"
      :key="star2.rating"
      :class="star2.active ? '!text-yellow-400' : null"
      size="18px"
    />
  </div>
</template>

<style scoped></style>
