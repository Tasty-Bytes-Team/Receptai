<script setup lang="ts">
import axios from "axios";
import { Form, useForm, useField } from "vee-validate";
import { toTypedSchema } from "@vee-validate/zod";
import * as zod from "zod";
import { addNotification } from "~/store/store";

interface User {
  id: number | null;
  name: string | null;
  email: string | null;
  avatarUrl: string | null;
}

interface UserCookie {
  token: string;
  expiresIn: number;
  user: User;
}

const config = useRuntimeConfig();

const showConfirmation = ref(false);

const props = defineProps<{
  TastyBytes_user: UserCookie | null;
}>();

const validationSchema = toTypedSchema(
  zod.object({
    name: zod
      .string()
      .min(3, "Name must be at least three characters long.")
      .regex(
        /^[a-zA-Z]{3,}(?: [a-zA-Z]+){0,2}$/,
        "Name can't contain special symbols."
      ),
  })
);

const { handleSubmit, errors } = useForm({
  validationSchema,
});

const { value: name } = useField("name");

name.value = props.TastyBytes_user?.user.name;

const onSubmit = handleSubmit(async () => {
  showConfirmation.value = false;

  if (!props.TastyBytes_user || name.value === props.TastyBytes_user.user.name) {
    return;
  }

  if (props.TastyBytes_user) {
    try {
      await axios.patch(
        `${config.public.baseURL}/api/v1/user/edit/${props.TastyBytes_user.user.id}`,
        {
          newName: name.value,
        },
        {
          headers: { Authorization: `Bearer ${props.TastyBytes_user.token}` },
        }
      );

      const newObject = props.TastyBytes_user;
      newObject.user.name = name.value as string;
      name.value = newObject.user.name;
      addNotification(
        "Name change was successful! Keep in mind that your change might take some time to show everywhere.",
        "Success"
      );
    } catch (e) {
      console.warn(e);

      name.value = props.TastyBytes_user.user.name;
      addNotification(
        "There was an error when changing your name. Please try again.",
        "Error"
      );
    }
  }
});

const onCancel = () => {
  showConfirmation.value = false;

  if (!props.TastyBytes_user){
    return
  }

  name.value = props.TastyBytes_user.user.name;
};
</script>

<template>
  <form class="flex flex-col relative" @submit.prevent="onSubmit">
    <div class="flex gap-2 items-center flex-row">
      <label class="font-medium text-gray-950">Name</label>
      <span class="text-red-600 text-sm">{{ errors.name }}</span>
    </div>

    <div>
      <input
        @focus="showConfirmation = true"
        class="w-full bg-concrete-50 hover:bg-concrete-100 focus:bg-concrete-100 px-2 py-2 focus:border-concrete-300 border-2 border-concrete-50 transition-colors duration-150 rounded-sm text-gray-950 outline-none"
        :class="
          showConfirmation ? '!bg-concrete-100 !border-concrete-300' : null
        "
        name="name"
        placeholder="Full name"
        autocomplete="name"
        v-model="name"
      />
      <div
        v-if="showConfirmation"
        class="absolute right-0 -bottom-8 flex gap-2 z-50"
      >
        <button
          type="submit"
          class="h-7 w-7 bg-gray-200 border border-gray-300 rounded-sm transition-all duration-150 hover:bg-gray-300"
        >
          <Icon
            name="material-symbols:done-rounded"
            size="18px"
            color="black"
          />
        </button>
        <button
          type="button"
          value="cancel"
          @click="onCancel"
          class="h-7 w-7 bg-gray-200 border border-gray-300 rounded-sm transition-all duration-150 hover:bg-gray-300"
        >
          <Icon
            name="material-symbols:close-rounded"
            size="18px"
            color="black"
          />
        </button>
      </div>
    </div>
  </form>
</template>

<style scoped></style>
