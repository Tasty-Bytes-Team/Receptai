<script setup lang="ts">
import { Form, useForm, useField } from "vee-validate";
import { toTypedSchema } from "@vee-validate/zod";
import * as zod from "zod";

const emit = defineEmits(["cancel", "confirm"]);

const validationSchema = toTypedSchema(
  zod.object({
    input: zod
      .string()
      .min(1, "This is required")
      .refine((value) => value === "DELETE", "Input has to be „DELETE“"),
  })
);

const { handleSubmit, errors } = useForm({
  validationSchema,
});

const { value: input } = useField("input");

const onSubmit = handleSubmit(async () => {
  emit("confirm");
});
</script>

<template>
  <div
    class="fixed top-0 right-0 left-0 w-full h-full flex justify-center items-center z-50 bg-[#7d7d7d82]"
  >
    <div
      class="relative p-6 text-center max-w-96 bg-white rounded-md shadow sm:p-7"
    >
      <button
        @click="emit('cancel')"
        type="button"
        class="text-concrete-500 hover:text-concrete-800 transition-all duration-100 absolute top-2.5 right-2.5 bg-transparent rounded-md m-1 ml-auto inline-flex items-center hover:bg-gray-200 hover:ring-4 hover:ring-gray-200 hover:rounded-sm outline-none hover:z-10"
      >
        <Icon name="material-symbols:close-rounded" size="22px" />
      </button>
      <Icon
        name="material-symbols:delete-outline"
        class="text-concrete-800"
        size="60px"
      />
      <p class="my-4 text-black">
        Are you sure you want to delete this recipe? To confirm please enter
        <b>DELETE</b>.
      </p>
      <form
        @submit="onSubmit"
        class="flex flex-col justify-center items-center gap-3"
      >
        <div class="w-full text-left">
          <div class="flex items-center gap-2">
            <label class="font-semibold text-sm">Type DELETE to confirm</label>
            <span class="text-red-600 text-sm">{{ errors.input }}</span>
          </div>
          <input
            v-model="input"
            class="outline-none w-full p-2 px-3 placeholder:text-concrete-400 bg-concrete-50 rounded-sm border-2 border-concrete-400 transition-colors duration-150 focus:border-black"
            placeholder="Input"
          />
        </div>
        <button
          type="submit"
          class="w-full py-2 text-sm font-medium text-white bg-red-400 rounded-md hover:bg-red-600 transition-colors duration-75"
        >
          Confirm
        </button>
      </form>
    </div>
  </div>
</template>

<style scoped></style>
