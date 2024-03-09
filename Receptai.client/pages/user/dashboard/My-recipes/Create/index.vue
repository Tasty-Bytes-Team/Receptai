<script setup lang="ts">
import axios from "axios";

import InputWithLabel from "@/components/admin/RecipeCreate/InputWithLabel.vue";
import TextareaWithLabel from "@/components/admin/RecipeCreate/TextareaWithLabel.vue";
import ErrorBaner from "@/components/Error/ErrorBaner.vue";
import DeleteButton from "@/components/admin/RecipeCreate/DeleteButton.vue";
import AddButton from "@/components/admin/RecipeCreate/AddButton.vue";

const config = useRuntimeConfig();

definePageMeta({
  layout: "admin",
  middleware: "auth",
});

interface Recipe {
  name: string;
  shortDescription: string;
  previewImage: string;
  tutorialVideo: string;
  ingredients: Ingredients[];
  instructions: string[];
  minutesToPrepare: number | null;
  portions: number | null;
  categoryId: number | string;
  tagIds: number[] | string[];
}

interface Ingredients {
  purpose: string;
  ingredients: Ingredient[];
}

interface Ingredient {
  name: string;
  quantity: number | null;
  unit: string;
}

interface Category {
  id: number;
  name: string;
  primary: boolean;
}

interface Tag {
  id: number;
  name: string;
  iconName: string;
}

interface User {
  name: string;
  email: string;
}
interface UserCookie {
  token: string;
  expiresIn: number;
  user: User;
}

const TastyBytes_user = useCookie<UserCookie | null>("TastyBytes_user");

const object = reactive<Recipe>({
  name: "",
  shortDescription: "",
  previewImage: "",
  tutorialVideo: "",
  instructions: [""],
  ingredients: [
    { purpose: "", ingredients: [{ name: "", quantity: null, unit: "" }] },
  ],
  minutesToPrepare: null,
  portions: null,
  tagIds: [""],
  categoryId: "",
});

const categoryList = ref<Category[] | null>(null);
const tagList = ref<Tag[] | null>(null);

const error: Ref<boolean> = ref(false);
const errorText: Ref<string> = ref("");

try {
  const response_category = await axios.get(
    `${config.public.baseURL}/api/v1/category/list`
  );

  const response_tag = await axios.get(
    `${config.public.baseURL}/api/v1/tag/list`
  );

  categoryList.value = response_category.data;
  tagList.value = response_tag.data;
} catch (e) {
  console.log("Get category and tag list", e);
  errorText.value = "Could not get categories and tags list. Please try again.";
  error.value = true;
}

const addNewIngriedientsGroup = () => {
  object.ingredients.push({
    purpose: "",
    ingredients: [{ name: "", quantity: null, unit: "" }],
  });
};

const removeNewIngriedientsGroup = (index: number) => {
  object.ingredients.splice(index, 1);
};

const addIngriedient = (groupIndex: number) => {
  object.ingredients[groupIndex].ingredients.push({
    name: "",
    quantity: null,
    unit: "",
  });
};

const removeIngriedient = (groupIndex: number, index: number) => {
  object.ingredients[groupIndex].ingredients.splice(index, 1);
};

const addInstruction = () => {
  object.instructions.push("");
};

const removeInstruction = (index: number) => {
  object.instructions.splice(index, 1);
};

const handleSubmit = async () => {
  error.value = false;
  errorText.value = "";
  if (TastyBytes_user.value) {
    try {
      await axios.post(
        `${config.public.baseURL}/api/v1/recipe/create`,
        object,
        {
          headers: { Authorization: `Bearer ${TastyBytes_user.value.token}` },
        }
      );
      await navigateTo("/user/dashboard/my-recipes");
    } catch (e) {
      console.log("Create recipe", e);
      errorText.value = "Could not create a recipe. Please try again.";
      error.value = true;
    }
  }
};
</script>

<template>
  <h1 class="text-3xl font-bold text-center m-5">Create Recipe</h1>
  <ErrorBaner v-if="error" :errorText="errorText" />
  <form class="flex flex-col gap-3" @submit.prevent="handleSubmit">
    <InputWithLabel
      :model="object.name"
      @update:model="object.name = $event"
      label="Name"
      placeholder="What's the recipe's name?"
    />
    <TextareaWithLabel
      :model="object.shortDescription"
      @update:model="object.shortDescription = $event"
      label="Short description"
      placeholder="How would you describe your recipe?"
    />
    <div class="w-full text-left">
      <label class="font-semibold text-sm">Category</label>
      <select
        required
        v-model="object.categoryId"
        class="outline-none w-full p-2 px-3 placeholder:text-[#9a9a9a] bg-[#f9f9f9] rounded-sm border-2 border-[#9a9a9a] transition-colors duration-150 focus:border-black"
      >
        <option disabled value="">Please select one category</option>
        <option
          v-for="category in categoryList"
          :key="category.id"
          :value="category.id"
        >
          {{ category.name }}
        </option>
      </select>
    </div>

    <div class="w-full text-left">
      <label class="font-semibold text-sm">Tag</label>
      <select
        required
        v-model="object.tagIds[0]"
        class="outline-none w-full p-2 px-3 placeholder:text-[#9a9a9a] bg-[#f9f9f9] rounded-sm border-2 border-[#9a9a9a] transition-colors duration-150 focus:border-black"
      >
        <option disabled value="">Please select one tag</option>
        <option v-for="tag in tagList" :key="tag.id" :value="tag.id">
          {{ tag.name }}
        </option>
      </select>
    </div>

    <InputWithLabel
      :model="object.minutesToPrepare"
      @update:model="object.minutesToPrepare = parseInt($event)"
      type="number"
      label="Preparation time (in minutes)"
      placeholder="How long does it take to prepare?"
    />
    <InputWithLabel
      :model="object.portions"
      @update:model="object.portions = parseInt($event)"
      type="number"
      label="Servings"
      placeholder="How many people does this recipe serve?"
    />
    <InputWithLabel
      :model="object.previewImage"
      @update:model="object.previewImage = $event"
      label="Image link"
      placeholder="Where could we find a picture of your recipe?"
    />
    <InputWithLabel
      :model="object.tutorialVideo"
      @update:model="object.tutorialVideo = $event"
      label="Making tutorial video link (from youtube.com)"
      placeholder="Do you have a tutorial video to share?"
    />
    <div class="px-3 flex flex-col gap-2">
      <h3 class="text-center text-lg font-semibold">Cooking Ingredients</h3>
      <div
        class="border-2 border-black p-3 flex flex-col gap-2"
        v-for="(ingredientGroup, groupIndex) in object.ingredients"
      >
        <div class="flex gap-3 items-center">
          <h5 class="font-semibold text-base uppercase">
            Ingredients group {{ groupIndex + 1 }}
          </h5>
          <DeleteButton
            @delete="removeNewIngriedientsGroup(groupIndex)"
            v-if="object.ingredients.length > 1"
          />
        </div>
        <InputWithLabel
          :model="ingredientGroup.purpose"
          @update:model="ingredientGroup.purpose = $event"
          label="Purpose"
          :placeholder="`What are we making with group ${
            groupIndex + 1
          } ingredients?`"
        />
        <div>
          <label class="font-semibold text-sm">Ingredients</label>
          <div class="justify-around flex items-center">
            <ul class="w-full list-disc pl-5">
              <li
                v-for="(ingredient, index) in ingredientGroup.ingredients"
                class="mb-3"
              >
                <div class="flex flex-col gap-2 sm:flex-row">
                  <InputWithLabel
                    :model="ingredient.name"
                    @update:model="ingredient.name = $event"
                    placeholder="Name"
                    label="Name"
                  />
                  <InputWithLabel
                    :model="ingredient.quantity"
                    @update:model="ingredient.quantity = parseInt($event)"
                    type="number"
                    placeholder="Quantity"
                    label="Quantity"
                  />
                  <InputWithLabel
                    :model="ingredient.unit"
                    @update:model="ingredient.unit = $event"
                    placeholder="Unit"
                    label="Unit"
                  />
                  <DeleteButton
                    @delete="removeIngriedient(groupIndex, index)"
                    v-if="ingredientGroup.ingredients.length > 1"
                  />
                </div>
              </li>
            </ul>
          </div>
          <AddButton color="yellow" @add="addIngriedient(groupIndex)" />
        </div>
      </div>
      <AddButton color="green" @add="addNewIngriedientsGroup" />
    </div>
    <div class="px-3 flex flex-col gap-2">
      <h3 class="text-center text-lg font-semibold">Cooking Instructions</h3>
      <div class="justify-around flex-col gap-2 flex">
        <div
          v-for="(instruction, index) in object.instructions"
          class="border-2 border-black p-3 flex flex-col gap-2"
        >
          <div class="flex gap-3 items-center">
            <h5 class="font-semibold text-md uppercase">
              Step {{ index + 1 }}
            </h5>
            <DeleteButton
              @delete="removeInstruction(index)"
              v-if="object.instructions.length > 1"
            />
          </div>
          <TextareaWithLabel
            :model="object.instructions[index]"
            @update:model="object.instructions[index] = $event"
            label="Instructions"
            :placeholder="`What are we doing in STEP ${index + 1}?`"
          />
        </div>
        <AddButton color="green" @add="addInstruction" />
      </div>
    </div>
    <button
      class="bg-black text-white p-2 w-full rounded-md drop-shadow-md font-medium transition-colors duration-200 hover:bg-[#292929]"
      type="submit"
    >
      Submit
    </button>
  </form>
</template>

<style scoped></style>
