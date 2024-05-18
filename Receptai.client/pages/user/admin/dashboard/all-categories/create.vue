<script setup lang="ts">
import axios from "axios";
import type { UserCookie } from "@/typescript/types";
import { Form, type GenericObject } from "vee-validate";
import { toTypedSchema } from "@vee-validate/zod";
import * as zod from "zod";

import { addNotification } from "@/store/store";

import ErrorBaner from "@/components/Error/ErrorBaner.vue";
import InputField from "@/components/user/components/InputField.vue";
import InputTextarea from "@/components/user/components/InputTextarea.vue";

interface CreateCategory {
  name: string;
  description: string;
  previewImageUrl: string;
}

definePageMeta({
  layout: "admin",
  middleware: "admin",
});

const config = useRuntimeConfig();
const TastyBytes_user = useCookie<UserCookie | null>("TastyBytes_user");

const error: Ref<boolean> = ref(false);
const errorText: Ref<string> = ref("");

const validationSchema = toTypedSchema(
  zod.object({
    name: zod.string().min(1, "Name is required"),
    description: zod.string().min(1, "Description is required"),
    previewImageUrl: zod.string().url("Preview image must be a valid URL"),
  })
);

let initialValues: CreateCategory = {
  name: "",
  description: "",
  previewImageUrl: "",
};

const onSubmit = async (values: GenericObject) => {
  error.value = false;
  errorText.value = "";

  if (TastyBytes_user.value) {
    try {
      const res = await axios.post(
        `${config.public.baseURL}/api/v1/category/create`,
        values,
        {
          headers: { Authorization: `Bearer ${TastyBytes_user.value.token}` },
        }
      );

      await navigateTo("/user/admin/dashboard/all-categories");

      addNotification(
        `Your category ${values.name} has been added!`,
        "Success"
      );
    } catch (e) {
      console.error("Create category", e);

      errorText.value =
        "Oops! There was an error while creating your category.";
      error.value = true;

      window?.scrollTo(0, 0);

      addNotification(
        "Uh oh! We couldn't create this category. Please try again.",
        "Error"
      );
    }
  }
};
</script>

<template>
  <div>
    <h1 class="text-3xl font-bold text-center m-5">Create Category</h1>
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
        placeholder="What's the categorie's name?"
      />
      <InputTextarea
        name="description"
        label="Short description (required)"
        placeholder="How would you describe this category?"
      />
      <InputField
        name="previewImageUrl"
        label="Preview image url (required)"
        placeholder="What image best describes your category?"
      />
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
