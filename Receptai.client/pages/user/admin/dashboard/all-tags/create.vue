<script setup lang="ts">
import axios from "axios";
import type { UserCookie } from "@/typescript/types";
import { Form, type GenericObject } from "vee-validate";
import { toTypedSchema } from "@vee-validate/zod";
import * as zod from "zod";

import { addNotification } from "@/store/store";

import ErrorBaner from "@/components/Error/ErrorBaner.vue";
import InputField from "@/components/user/components/InputField.vue";

interface CreateTag {
  name: string;
  iconName: string;
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
    iconName: zod.string().min(1, "Icon is required"),
  })
);

let initialValues: CreateTag = {
  name: "",
  iconName: "",
};

const onSubmit = async (values: GenericObject) => {
  error.value = false;
  errorText.value = "";

  if (TastyBytes_user.value) {
    try {
      const res = await axios.post(
        `${config.public.baseURL}/api/v1/tag/create`,
        values,
        {
          headers: { Authorization: `Bearer ${TastyBytes_user.value.token}` },
        }
      );

      await navigateTo("/user/admin/dashboard/all-tags");

      addNotification(`Your tag ${values.name} has been added!`, "Success");
    } catch (e) {
      console.error("Create tag", e);

      errorText.value = "Oops! There was an error while creating your tag.";
      error.value = true;

      window?.scrollTo(0, 0);

      addNotification(
        "Uh oh! We couldn't create this tag. Please try again.",
        "Error"
      );
    }
  }
};
</script>

<template>
  <div>
    <h1 class="text-3xl font-bold text-center m-5">Create Tag</h1>
    <ErrorBaner v-if="error" :errorText="errorText" />
    <Form
      v-slot="{ values }"
      :initial-values="initialValues"
      :validation-schema="validationSchema"
      @submit="onSubmit"
      class="flex flex-col gap-3"
    >
      <InputField
        name="name"
        label="Name (required)"
        placeholder="What's the tag's name?"
      />
      <div class="bg-gray-100 rounded-md w-full p-3">
        <p class="font-medium text-sm">
          Icon should be selected from the list provided in this website:
          <a
            href="https://icones.js.org/"
            target="_blank"
            style="color: #0043c8"
            >icones.js.org</a
          >.
        </p>
      </div>
      <InputField
        name="iconName"
        label="Icon name (required)"
        placeholder="Which icon best describes your tag?"
      />
      <div
        v-if="values.iconName && values.iconName.length > 4"
        class="bg-gray-100 rounded-md w-full p-3 flex flex-row items-center gap-3 flex-wrap"
      >
        <p class="font-medium text-sm">Your selected icon:</p>
        <Icon :name="values.iconName" size="24px" color="black" />
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
