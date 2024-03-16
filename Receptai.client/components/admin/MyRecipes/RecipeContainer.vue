<script setup lang="ts">
import Image from "./Image.vue";

interface Recipe {
  id: number;
  name: string;
  shortDescription: string;
  author: Author;
  dateCreated: string;
  dateModified: string | null;
  previewImage: string;
  tutorialVideo?: string;
  ingredients: Ingredients[];
  instructions: string[];
  tags: string[];
  categories: Category[];
  minutesToPrepare: number;
  portions: number;
}

interface Author {
  name: string;
}

interface Ingredients {
  purpose: string;
  ingredients: Ingredient[];
}

interface Ingredient {
  name: string;
  quantity: number;
  unit: string;
}

interface Category {
  name: string;
  link: string;
}

const props = defineProps<{
  recipes: Recipe[] | null;
}>();

const recipeSelection = props.recipes?.map((recipe) => ({
  id: recipe.id,
  image: recipe.previewImage,
  name: recipe.name,
  dateCreated: recipe.dateCreated.split("T")[0],
}));

const columns = [
  {
    key: "id",
    label: "ID",
    sortable: true,
  },
  {
    key: "image",
    label: "Image",
  },
  {
    key: "name",
    label: "Name",
    sortable: true,
  },
  {
    key: "dateCreated",
    label: "Creaton date",
    sortable: true,
  },
  {
    key: "action",
    label: "Action",
    sortable: true,
  },
];
</script>

<template>
  <div class="relative overflow-x-auto">
    <table class="w-full text-sm text-left rtl:text-right">
      <thead class="text-sm bg-concrete-100">
        <tr>
          <th
            scope="col"
            class="px-3 py-3 border-concrete-300 border-2"
            v-for="item in columns"
          >
            {{ item.label }}
          </th>
        </tr>
      </thead>
      <tbody>
        <tr
          class="border-b-2 border-concrete-200"
          v-for="recipe in recipeSelection"
        >
          <td class="text-center">{{ recipe.id }}</td>
          <td class="text-center">
            <Image class="m-auto" :preview-image="recipe.image" />
          </td>
          <td class="font-bold px-3 py-4">{{ recipe.name }}</td>
          <td class="px-3 py-4">{{ recipe.dateCreated }}</td>
          <td class="text-center">
            <NuxtLink
              :to="`/recipes/${recipe.id}`"
              target="_blank"
              title="Open in browser"
              ><Icon name="material-symbols:globe" size="24px" color="black"
            /></NuxtLink>
            <NuxtLink
              :to="`/user/dashboard/my-recipes/edit/${recipe.id}`"
              title="Edit recipe"
              ><Icon name="material-symbols:contract-edit" size="24px" color="black"
            /></NuxtLink>
          </td>
        </tr>
      </tbody>
    </table>
  </div>
</template>

<style scoped></style>
