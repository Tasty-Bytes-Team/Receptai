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
import InputField from "@/components/admin/components/InputField.vue";
import InputTextarea from "@/components/admin/components/InputTextarea.vue";
import OptionSelect from "@/components/admin/components/OptionSelect.vue";
import MultipleOptionSelect from "@/components/admin/components/MultipleOptionSelect.vue";
import IngredientsGroups from "@/components/admin/components/IngredientsGroups.vue";
import Instructions from "@/components/admin/components/Instructions.vue";

const config = useRuntimeConfig();

definePageMeta({
  layout: "admin",
  middleware: "auth",
});

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

const validationSchema = toTypedSchema(
  zod.object({
    name: zod.string().min(1, "Name is required"),
    shortDescription: zod.string().min(1, "Description is required"),
    previewImage: zod.string().url("Preview image must be a valid URL"),
    tutorialVideo: zod
      .string()
      .transform((val) => (val === "" ? null : val))
      .refine(
        (str) =>
          str === null
            ? true
            : /.*(?:youtu.be\/|v\/|u\/\w\/|embed\/|watch\?v=)([^#\&\?]*).*/.test(
                str
              ),
        "You need to provide a valid YouTube video URL"
      )
      .nullable()
      .optional(),
    instructions: zod.array(
      zod.string().min(1, "Cooking instructions is required")
    ),
    ingredients: zod.array(
      zod.object({
        purpose: zod.string().min(1, "Purpose is required"),
        ingredients: zod.array(
          zod.object({
            name: zod.string().min(1, "Required"),
            quantity: zod.number({ invalid_type_error: "Required" }),
            unit: zod.string().min(1, "Required"),
          })
        ),
      })
    ),
    minutesToPrepare: zod.number({
      invalid_type_error: "Preparation time is required",
    }),
    portions: zod.number({ invalid_type_error: "Servings is required" }),
    tagIds: zod
      .number({ invalid_type_error: "Tag is required" })
      .array()
      .min(0, "Required"),
    categoryId: zod.number({ invalid_type_error: "Category is required" }),
  })
);

const initialValues = {
  name: "",
  shortDescription: "",
  previewImage: "",
  tutorialVideo: null,
  instructions: [""],
  ingredients: [
    { purpose: "", ingredients: [{ name: "", quantity: null, unit: "" }] },
  ],
  minutesToPrepare: null,
  portions: null,
  tagIds: [""],
  categoryId: "",
};

const categoryList = ref<Category[] | null>(null);
const tagList = ref<Tag[] | null>(null);

const error: Ref<boolean> = ref(false);
const errorText: Ref<string> = ref("");

try {
  const [responseCategory, responseTag] = await Promise.all([
    axios.get(`${config.public.baseURL}/api/v1/category/list`),
    axios.get(`${config.public.baseURL}/api/v1/tag/list`),
  ]);

  categoryList.value = responseCategory.data.elements;
  tagList.value = responseTag.data.elements;
} catch (e) {
  console.log("Get category and tag list", e);

  errorText.value = "Could not get categories and tags list. Please try again.";
  error.value = true;

  window?.scrollTo(0, 0);
}

const onSubmit = async (values: GenericObject) => {
  error.value = false;
  errorText.value = "";
  if (TastyBytes_user.value) {
    try {
      const res = await axios.post(
        `${config.public.baseURL}/api/v1/recipe/create`,
        values,
        {
          headers: { Authorization: `Bearer ${TastyBytes_user.value.token}` },
        }
      );

      await navigateTo("/user/dashboard/my-recipes");

      addNotification(
        `Your delicious recipe ${values.name} has been added! Time to share or enjoy.`,
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
      console.log("Create recipe", e);
      errorText.value = "Could not create a recipe. Please try again.";
      error.value = true;

      window?.scrollTo(0, 0);

      addNotification(
        "There was a problem creating your recipe. Please try again.",
        "Error"
      );
    }
  }
};
</script>

<template>
  <div>
    <h1 class="text-3xl font-bold text-center m-5">Create Recipe</h1>
    <ErrorBaner v-if="error" :errorText="errorText" />
    <Form
      :initial-values="initialValues"
      :validation-schema="validationSchema"
      @submit="onSubmit"
      class="flex flex-col gap-3"
    >
      <InputField
        name="name"
        label="Name (required)"
        placeholder="What's the recipe's name?"
      />
      <InputTextarea
        name="shortDescription"
        label="Short description (required)"
        placeholder="How would you describe your recipe?"
      />
      <OptionSelect
        name="categoryId"
        label="Category (required)"
        placeholder="Please select one category"
        :categoryList="categoryList"
      />
      <MultipleOptionSelect
        name="tagIds"
        label="Tag (required)"
        :tagList="tagList"
      />
      <InputField
        name="minutesToPrepare"
        label="Preparation time (in minutes) (required)"
        placeholder="How long does it take to prepare?"
        type="number"
      />
      <InputField
        name="portions"
        label="Servings (in minutes) (required)"
        placeholder="How many people does this recipe serve?"
        type="number"
      />
      <InputField
        name="previewImage"
        label="Image link (required)"
        placeholder="Where could we find a picture of your recipe?"
      />
      <InputField
        name="tutorialVideo"
        label="Making tutorial video link (from youtube.com) (optional)"
        placeholder="Do you have a tutorial video to share?"
      />
      <IngredientsGroups />
      <Instructions />
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
