<script setup>
import NutritionTable from "./components/NutritionTable.vue";
import Badge from "./components/Badge.vue";
import InfoBadge from "./components/InfoBadge.vue";
import CookingInstructions from "./components/CookingInstructions.vue";
import Ingredients from "./components/Ingredients.vue";

const props = defineProps({
  id: String,
});


const cookingInstructions = [
  {
    text: "To make the tomato sauce, heat the olive oil in a saucepan. Add the onions, garlic and carrot. Cook for 5-7 mins over a medium heat until softened. Turn up the heat a little and stir in the tomato purée. Cook for 1 min, pour in the white wine, then cook for 5 mins until this has reduced by two-thirds. Pour over the chopped tomatoes and add the basil leaves. Bring to the boil then simmer for 20 mins. Leave to cool then whizz in a food processor. Will keep, cooled, in the fridge for up to three days or frozen for three months.",
  },
  {
    text: "To make the tomato sauce, heat the olive oil in a saucepan. Add the onions, garlic and carrot. Cook for 5-7 mins over a medium heat until softened. Turn up the heat a little and stir in the tomato purée. Cook for 1 min, pour in the white wine, then cook for 5 mins until this has reduced by two-thirds. Pour over the chopped tomatoes and add the basil leaves. Bring to the boil then simmer for 20 mins. Leave to cool then whizz in a food processor. Will keep, cooled, in the fridge for up to three days or frozen for three months.",
  },
];

const ingredientsList = {
  main: [
    { name: "red peppers", quantity: 3, unit: "cut into large chunks" },
    { name: "aubergines", quantity: 2, unit: "cut into ½ cm slices" },
  ],
  additional: [
    {
      name: "Tomato Souce",
      ingredients: [
        { name: "red peppers", quantity: 3, unit: "large chunks" },
        { name: "aubergines", quantity: 2, unit: "½ cm slices" },
      ],
    },
    {
      name: "White Souce",
      ingredients: [
        { name: "red peppers", quantity: 3, unit: "large chunks" },
        { name: "aubergines", quantity: 2, unit: "½ cm slices" },
      ],
    },
  ],
};

console.log(props.id);
</script>

<template>
  <div>
    <div class="max-w-screen-lg m-auto my-5 px-2">
      <p>Home > Category > Vegetarian lasagne</p>
    </div>
    <div class="bg-[#f7f7f7] border-y-2">
      <div class="max-w-screen-lg m-auto my-5 px-2">
        <div class="flex flex-wrap items-center">
          <div class="lg:basis-1/3 md:basis-1/2 basis-full">
            <div class="p-4">
              <img
                src="/images/Lasagne-square-FS-79.webp"
                class="rounded-md border-2 border-[#c4c4c4] shadow-md max-h-96 m-auto"
              />
            </div>
          </div>
          <div class="lg:basis-2/3 md:basis-1/2 basis-full">
            <div class="p-4">
              <div class="mb-3">
                <h1 class="font-bold text-4xl">Vegetarian lasagne</h1>
                <div class="text-sm font-light">
                  <span class="font-medium">Vardenis Pavardenis, </span
                  ><span>2024-02-23</span>
                </div>
              </div>
              <p>
                Make our easy vegetable lasagne using just a few ingredients.
                You can use ready-made tomato sauce and white sauce, or batch
                cook the sauces and freeze them.
              </p>
              <div class="my-3">
                <NutritionTable />
              </div>
            </div>
            <div class="flex flex-wrap">
              <InfoBadge
                icon="majesticons:clock-line"
                text="Preparation:"
                info="30 min."
              />
              <InfoBadge
                icon="tabler:tools-kitchen-2"
                text="Portions:"
                info="4"
              />
              <Badge
                icon="fluent-emoji-high-contrast:broccoli"
                text="Vegetarian"
              />
              <Badge icon="ph:snowflake" text="Freezable" />
              <Badge icon="twemoji:flag-italy" text="Italic food travels" />
            </div>
          </div>
        </div>
      </div>
    </div>
    <div class="max-w-screen-lg m-auto my-3 px-2">
      <div class="flex flex-wrap">
        <div class="my-3 md:basis-1/3 basis-full">
          <Ingredients :ingredients="ingredientsList" />
        </div>
        <div class="my-3 md:basis-2/3 basis-full">
          <CookingInstructions :cookingInstructions="cookingInstructions" />
        </div>
      </div>
    </div>
    <div class="max-w-screen-lg m-auto my-2 px-2">
      <h3 class="font-semibold text-xl mb-3">Searching for video instructions? <span class="font-normal">Find them here!</span></h3>
      <iframe class="w-4/6 lg:h-[400px] sm:h-[300px] h-[200px] m-auto" src="https://www.youtube.com/embed/VkQajdYciW0" title="LASAGNA/LAZANYA (quick and easy)" frameborder="0" allow="accelerometer; autoplay; clipboard-write; encrypted-media; gyroscope; picture-in-picture; web-share" allowfullscreen></iframe>
    </div>
  </div>
</template>

<style lang="scss" scoped></style>