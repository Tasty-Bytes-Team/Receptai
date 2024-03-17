<script setup lang="ts">
import axios from "axios";

import {
  ErrorMessage,
  FieldArray,
  Form,
  Field,
  type GenericObject,
} from "vee-validate";
import { toTypedSchema } from "@vee-validate/zod";
import * as zod from "zod";

import { addNotification } from "@/store/store";

import ErrorBaner from "@/components/Error/ErrorBaner.vue";
import DeleteButton from "@/components/admin/RecipeCreate/DeleteButton.vue";
import AddButton from "@/components/admin/RecipeCreate/AddButton.vue";

interface GetRecipe {
  id: number;
  name: string;
  author: { id: number; name: string };
  shortDescription: string;
  previewImage: string;
  tutorialVideo: string;
  ingredients: Ingredients[];
  instructions: Instructions[];
  minutesToPrepare: number | null;
  portions: number | null;
  categories: Category[];
  tags: Tag[];
}

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

interface Instructions {
  text: string;
}

interface UserCookie {
  token: string;
  expiresIn: number;
  user: User;
}

interface User {
  id: number;
  name: string;
  email: string;
}

const config = useRuntimeConfig();
const TastyBytes_user = useCookie<UserCookie | null>("TastyBytes_user");

const props = defineProps<{
  recipeData: GetRecipe | null;
}>();

const categoryList = ref<Category[] | null>(null);
const tagList = ref<Tag[] | null>(null);

const error: Ref<boolean> = ref(false);
const errorText: Ref<string> = ref("");

const validationSchema = toTypedSchema(
  zod.object({
    name: zod.string().min(1, "Required"),
    shortDescription: zod.string().min(1, "Required"),
    previewImage: zod.string().url("Preview image must be a valid URL"),
    tutorialVideo: zod.union([
      zod.literal(""),
      zod.string().trim().url("Tutorial video must be a valid URL"),
    ]),
    instructions: zod.array(zod.string().min(1, "Required")),
    ingredients: zod.array(
      zod.object({
        purpose: zod.string().min(1, "Required"),
        ingredients: zod.array(
          zod.object({
            name: zod.string().min(1, "Required"),
            quantity: zod.number({ invalid_type_error: "Required" }),
            unit: zod.string().min(1, "Required"),
          })
        ),
      })
    ),
    minutesToPrepare: zod.number({ invalid_type_error: "Required" }),
    portions: zod.number({ invalid_type_error: "Required" }),
    tagIds: zod
      .number({ invalid_type_error: "Required" })
      .array()
      .min(0, "Required"),
    categoryId: zod.number({ invalid_type_error: "Required" }),
  })
);

let initialValues: Recipe = {
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
  tagIds: ["1"],
  categoryId: "",
};

try {
  const [responseCategory, responseTag] = await Promise.all([
    axios.get(`${config.public.baseURL}/api/v1/category/list`),
    axios.get(`${config.public.baseURL}/api/v1/tag/list`),
  ]);

  if (props.recipeData) {
    initialValues.name = props.recipeData.name;
    initialValues.shortDescription = props.recipeData.shortDescription;
    initialValues.previewImage = props.recipeData.previewImage;
    initialValues.tutorialVideo = props.recipeData.tutorialVideo ? props.recipeData.tutorialVideo : "";
    initialValues.instructions = props.recipeData.instructions.map(
      (instructions: Instructions) => instructions.text
    );
    initialValues.ingredients = props.recipeData.ingredients;
    initialValues.minutesToPrepare = props.recipeData.minutesToPrepare;
    initialValues.portions = props.recipeData.portions;
    initialValues.tagIds = props.recipeData.tags?.map(
      (tagIds: Tag) => tagIds.id
    );
    initialValues.categoryId = props.recipeData.categories?.[0].id;
  }

  categoryList.value = responseCategory.data;
  tagList.value = responseTag.data;
} catch (e) {
  console.log(e);

  errorText.value = "Couldn't load recipe data. Please try again.";
  error.value = true;

  window?.scrollTo(0, 0);
}

const onSubmit = async (values: GenericObject) => {
  error.value = false;
  errorText.value = "";

  if (TastyBytes_user.value) {
    try {
      const res = await axios.put(
        `${config.public.baseURL}/api/v1/recipe/edit/${props.recipeData?.id}`,
        values,
        {
          headers: { Authorization: `Bearer ${TastyBytes_user.value.token}` },
        }
      );

      await navigateTo("/user/dashboard/my-recipes");

      addNotification(
        `Your recipe ${values.name} has been updated!`,
        "Success",
        [
          {
            text: "View recipe",
            link: `/recipes/${res.data.id}`,
            type: "Black",
          },
        ]
      );
    } catch (e) {
      console.log("Edit recipe", e);

      errorText.value = "Oops! There was an error saving your changes.";
      error.value = true;

      window?.scrollTo(0, 0);

      addNotification(
        "Uh oh! We couldn't update this recipe. Please try again.",
        "Error"
      );
    }
  }
};
</script>

<template>
  <div>
    <h1 class="text-3xl font-bold text-center m-5">Edit Recipe</h1>
    <ErrorBaner v-if="error" :errorText="errorText" />
    <Form
      :initial-values="initialValues"
      :validation-schema="validationSchema"
      @submit="onSubmit"
      class="flex flex-col gap-3"
    >
      <div>
        <div class="flex items-center gap-2">
          <label class="font-semibold text-sm">Name</label>
          <ErrorMessage name="name" class="text-red-600 text-sm" />
        </div>
        <Field
          name="name"
          placeholder="What's the recipe's name?"
          class="outline-none w-full p-2 px-3 placeholder:text-concrete-400 bg-concrete-50 rounded-sm border-2 border-concrete-400 transition-colors duration-150 focus:border-black"
        />
      </div>
      <div>
        <div class="flex items-center gap-2">
          <label class="font-semibold text-sm">Short description</label>
          <ErrorMessage name="shortDescription" class="text-red-600 text-sm" />
        </div>
        <Field
          name="shortDescription"
          placeholder="How would you describe your recipe?"
          as="textarea"
          class="outline-none w-full p-2 px-3 placeholder:text-concrete-400 bg-concrete-50 rounded-sm border-2 border-concrete-400 transition-colors duration-150 focus:border-black"
        />
      </div>

      <div class="w-full text-left">
        <div class="flex items-center gap-2">
          <label class="font-semibold text-sm">Category</label>
          <ErrorMessage name="categoryId" class="text-red-600 text-sm" />
        </div>
        <Field
          as="select"
          name="categoryId"
          class="outline-none w-full p-2 px-3 placeholder:text-concrete-400 bg-concrete-50 rounded-sm border-2 border-concrete-400 transition-colors duration-150 focus:border-black"
        >
          <option disabled value="">Please select one category</option>
          <option
            v-for="category in categoryList"
            :key="category.id"
            :value="category.id"
          >
            {{ category.name }}
          </option>
        </Field>
      </div>

      <div class="w-full text-left">
        <div class="flex items-center gap-2">
          <label class="font-semibold text-sm">Tag</label>
          <ErrorMessage name="tagIds" class="text-red-600 text-sm" />
        </div>
        <Field
          as="select"
          name="tagIds"
          v-slot="{ value }"
          multiple
          class="outline-none w-full p-2 px-3 placeholder:text-concrete-400 bg-concrete-50 rounded-sm border-2 border-concrete-400 transition-colors duration-150 focus:border-black"
        >
          <option
            v-for="tag in tagList"
            :key="tag.id"
            :value="tag.id"
            :selected="value && value.includes(tag.id)"
          >
            {{ tag.name }}
          </option>
        </Field>
      </div>

      <div>
        <div class="flex items-center gap-2">
          <label class="font-semibold text-sm"
            >Preparation time (in minutes)</label
          >
          <ErrorMessage name="minutesToPrepare" class="text-red-600 text-sm" />
        </div>
        <Field
          name="minutesToPrepare"
          type="number"
          min="1"
          placeholder="How long does it take to prepare?"
          class="outline-none w-full p-2 px-3 placeholder:text-concrete-400 bg-concrete-50 rounded-sm border-2 border-concrete-400 transition-colors duration-150 focus:border-black"
        />
      </div>
      <div>
        <div class="flex items-center gap-2">
          <label class="font-semibold text-sm">Servings</label>
          <ErrorMessage name="portions" class="text-red-600 text-sm" />
        </div>
        <Field
          name="portions"
          type="number"
          min="1"
          placeholder="How many people does this recipe serve?"
          class="outline-none w-full p-2 px-3 placeholder:text-concrete-400 bg-concrete-50 rounded-sm border-2 border-concrete-400 transition-colors duration-150 focus:border-black"
        />
      </div>
      <div>
        <div class="flex items-center gap-2">
          <label class="font-semibold text-sm">Image link</label>
          <ErrorMessage name="previewImage" class="text-red-600 text-sm" />
        </div>
        <Field
          name="previewImage"
          placeholder="Where could we find a picture of your recipe?"
          class="outline-none w-full p-2 px-3 placeholder:text-concrete-400 bg-concrete-50 rounded-sm border-2 border-concrete-400 transition-colors duration-150 focus:border-black"
        />
      </div>
      <div>
        <div class="flex items-center gap-2">
          <label class="font-semibold text-sm"
            >Making tutorial video link (from youtube.com)</label
          >
          <ErrorMessage name="tutorialVideo" class="text-red-600 text-sm" />
        </div>
        <Field
          name="tutorialVideo"
          placeholder="Do you have a tutorial video to share?"
          class="outline-none w-full p-2 px-3 placeholder:text-concrete-400 bg-concrete-50 rounded-sm border-2 border-concrete-400 transition-colors duration-150 focus:border-black"
        />
      </div>
      <div class="px-3 flex flex-col gap-2">
        <h3 class="text-center text-lg font-semibold">Cooking Ingredients</h3>
        <FieldArray
          name="ingredients"
          v-slot="{ fields, push, remove }"
          class="justify-around flex-col gap-2 flex"
        >
          <fieldset
            v-for="(field, groupIndex) in fields"
            :key="field.key"
            class="border-2 border-black p-3 flex flex-col gap-2"
          >
            <div class="flex gap-3 items-center">
              <h5 class="font-semibold text-md uppercase">
                Ingredients group {{ groupIndex + 1 }}
              </h5>
              <DeleteButton
                @delete="remove(groupIndex)"
                v-if="fields.length > 1"
              />
            </div>
            <div>
              <div class="flex items-center gap-2">
                <label class="font-semibold text-sm">Purpose</label>
                <ErrorMessage
                  :name="`ingredients[${groupIndex}].purpose`"
                  class="text-red-600 text-sm"
                />
              </div>
              <Field
                :name="`ingredients[${groupIndex}].purpose`"
                :placeholder="`What are we making with group ${
                  groupIndex + 1
                } ingredients?`"
                class="outline-none w-full p-2 px-3 placeholder:text-concrete-400 bg-concrete-50 rounded-sm border-2 border-concrete-400 transition-colors duration-150 focus:border-black"
              />
            </div>
            <label class="font-semibold text-sm">Ingredients</label>
            <FieldArray
              :name="`ingredients[${groupIndex}].ingredients`"
              v-slot="{ fields, push, remove }"
              class="justify-around flex items-center"
            >
              <ul class="w-full list-disc pl-5">
                <li
                  v-for="(field, index) in fields"
                  :key="field.key"
                  class="mb-3"
                >
                  <div class="flex flex-col gap-2 sm:flex-row">
                    <div class="w-full">
                      <div class="flex items-center gap-2">
                        <label class="font-semibold text-sm">Name</label>
                        <ErrorMessage
                          :name="`ingredients[${groupIndex}].ingredients[${index}].name`"
                          class="text-red-600 text-sm"
                        />
                      </div>
                      <Field
                        :name="`ingredients[${groupIndex}].ingredients[${index}].name`"
                        placeholder="Name"
                        class="outline-none w-full p-2 px-3 placeholder:text-concrete-400 bg-concrete-50 rounded-sm border-2 border-concrete-400 transition-colors duration-150 focus:border-black"
                      />
                    </div>
                    <div class="w-full">
                      <div class="flex items-center gap-2">
                        <label class="font-semibold text-sm">Quantity</label>
                        <ErrorMessage
                          :name="`ingredients[${groupIndex}].ingredients[${index}].quantity`"
                          class="text-red-600 text-sm"
                        />
                      </div>
                      <Field
                        :name="`ingredients[${groupIndex}].ingredients[${index}].quantity`"
                        placeholder="Quantity"
                        min="1"
                        type="number"
                        class="outline-none w-full p-2 px-3 placeholder:text-concrete-400 bg-concrete-50 rounded-sm border-2 border-concrete-400 transition-colors duration-150 focus:border-black"
                      />
                    </div>
                    <div class="w-full">
                      <div class="flex items-center gap-2">
                        <label class="font-semibold text-sm">Unit</label>
                        <ErrorMessage
                          :name="`ingredients[${groupIndex}].ingredients[${index}].unit`"
                          class="text-red-600 text-sm"
                        />
                      </div>
                      <Field
                        :name="`ingredients[${groupIndex}].ingredients[${index}].unit`"
                        placeholder="Unit"
                        class="outline-none w-full p-2 px-3 placeholder:text-concrete-400 bg-concrete-50 rounded-sm border-2 border-concrete-400 transition-colors duration-150 focus:border-black"
                      />
                    </div>
                    <DeleteButton
                      @delete="remove(index)"
                      v-if="fields.length > 1"
                    />
                  </div>
                </li>
              </ul>
              <AddButton
                color="yellow"
                text="Add new ingredient"
                @add="
                  push({
                    name: '',
                    quantity: null,
                    unit: '',
                  })
                "
              />
            </FieldArray>
          </fieldset>
          <AddButton
            color="green"
            text="Add new group"
            @add="
              push({
                purpose: '',
                ingredients: [{ name: '', quantity: null, unit: '' }],
              })
            "
          />
        </FieldArray>
      </div>
      <div class="px-3 flex flex-col gap-2">
        <h3 class="text-center text-lg font-semibold">Cooking Instructions</h3>
        <FieldArray
          name="instructions"
          v-slot="{ fields, push, remove }"
          class="justify-around flex-col gap-2 flex"
        >
          <fieldset
            v-for="(field, idx) in fields"
            :key="field.key"
            class="border-2 border-black p-3 flex flex-col gap-2"
          >
            <div class="flex gap-3 items-center">
              <h5 class="font-semibold text-md uppercase">
                Step {{ idx + 1 }}
              </h5>
              <DeleteButton @delete="remove(idx)" v-if="fields.length > 1" />
            </div>
            <div>
              <div class="flex items-center gap-2">
                <label class="font-semibold text-sm">Instructions</label>
                <ErrorMessage
                  :name="`instructions[${idx}]`"
                  class="text-red-600 text-sm"
                />
              </div>
              <Field
                as="textarea"
                :name="`instructions[${idx}]`"
                :placeholder="`What are we doing in STEP ${idx + 1}?`"
                class="outline-none w-full p-2 px-3 placeholder:text-concrete-400 bg-concrete-50 rounded-sm border-2 border-concrete-400 transition-colors duration-150 focus:border-black"
              />
            </div>
          </fieldset>
          <AddButton color="green" text="Add new step" @add="push('')" />
        </FieldArray>
      </div>
      <button
        class="bg-concrete-700 text-white hover:bg-concrete-900 p-2 w-full rounded-sm shadow-[3px_3px_0_0_#bdbdbd] font-medium transition-colors duration-200"
        type="submit"
      >
        Submit
      </button>
    </Form>
  </div>
</template>

<style scoped></style>
