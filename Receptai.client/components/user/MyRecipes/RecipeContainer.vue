<script setup lang="ts">
import axios from "axios";
import type {
  UserCookie,
  Recipe,
  RecipeContainerColumn,
} from "@/typescript/types";
import { addNotification } from "@/store/store";
import ConfirmBox from "./components/ConfirmBox.vue";
import Image from "./Image.vue";
import StarRating from "@/components/Feedback/components/StarRating.vue";

const config = useRuntimeConfig();
const TastyBytes_user = useCookie<UserCookie | null>("TastyBytes_user");

const props = defineProps<{
  recipes: Recipe[] | null;
  columns: RecipeContainerColumn[];
}>();

const emit = defineEmits(["reload", "changeSort"]);

const confirmBox = ref<boolean>(false);
const toBeDeleted = ref<number | null>(null);

const recipeSelection = props.recipes?.map((recipe) => ({
  id: recipe.id,
  image: recipe.previewImage,
  averageRating: recipe.averageRating,
  name: recipe.name,
  dateCreated: recipe.dateCreated.split("T")[0],
}));

const deleteRecipe = async () => {
  try {
    if (TastyBytes_user.value && toBeDeleted.value != null) {
      await axios.delete(
        `${config.public.baseURL}/api/v1/recipe/delete/${toBeDeleted.value}`,
        {
          headers: { Authorization: `Bearer ${TastyBytes_user.value.token}` },
        }
      );

      emit("reload");
      confirmBox.value = false;
      addNotification(`Your recipe has been deleted!`, "Success");
    } else {
      addNotification(`You are not authorized. Please log in again.`, "Error");
      navigateTo("/user/login");
    }
  } catch (e) {
    console.error(e);
  }
};
</script>

<template>
  <ConfirmBox
    v-if="confirmBox"
    toBeDeletedText="recipe"
    @confirm="deleteRecipe"
    @cancel="confirmBox = !confirmBox"
  />
  <div class="relative overflow-x-auto">
    <table class="w-full text-sm text-left rtl:text-right">
      <thead class="text-sm bg-concrete-100">
        <tr>
          <th
            @click="
              if (item.sortable) {
                $emit('changeSort', item);
              }
            "
            scope="col"
            class="px-3 py-3 border-concrete-300 border-2 min-w-36"
            v-for="item in columns"
          >
            <div class="flex flex-row items-center justify-between gap-1.5">
              {{ item.label }}
              <Icon
                v-if="item.sortable"
                size="16px"
                :name="
                  item.sortBy === 'ASC'
                    ? 'fa-solid:sort-up'
                    : item.sortBy === 'DESC'
                    ? 'fa-solid:sort-down'
                    : 'fa-solid:sort'
                "
                :color="
                  item.sortBy === 'ASC'
                    ? 'black'
                    : item.sortBy === 'DESC'
                    ? 'black'
                    : '#626262'
                "
              />
            </div>
          </th>
        </tr>
      </thead>
      <tbody>
        <tr
          class="border-b-2 border-concrete-200"
          v-for="recipe in recipeSelection"
        >
          <td class="font-bold px-3 py-4">{{ recipe.name }}</td>
          <td class="text-center">
            <Image class="m-auto" :preview-image="recipe.image" />
          </td>
          <td class="font-bold px-3 py-4">
            <StarRating :set-rating="recipe.averageRating / 2" />
          </td>
          <td class="px-3 py-4">{{ recipe.dateCreated }}</td>
          <td class="text-center max-w-40">
            <div class="w-full flex justify-evenly">
              <NuxtLink
                :to="`/recipes/${recipe.id}`"
                target="_blank"
                title="Open in browser"
              >
                <Icon
                  name="material-symbols:globe"
                  class="transition-all duration-150 hover:bg-gray-200 hover:ring-4 hover:ring-gray-200 hover:rounded-sm outline-none hover:z-10"
                  size="24px"
                  color="black"
                />
              </NuxtLink>
              <NuxtLink
                :to="`/user/dashboard/my-recipes/edit/${recipe.id}`"
                title="Edit recipe"
              >
                <Icon
                  name="material-symbols:contract-edit"
                  class="transition-all duration-150 hover:bg-gray-200 hover:ring-4 hover:ring-gray-200 hover:rounded-sm outline-none hover:z-10"
                  size="24px"
                  color="black"
                />
              </NuxtLink>
              <a
                @click="
                  {
                    confirmBox = true;
                    toBeDeleted = recipe.id;
                  }
                "
                title="Delete recipe"
                class="cursor-pointer"
              >
                <Icon
                  name="material-symbols:delete-outline"
                  class="transition-all duration-150 hover:bg-gray-200 hover:ring-4 hover:ring-gray-200 hover:rounded-sm outline-none hover:z-10"
                  size="24px"
                  color="black"
                />
              </a>
            </div>
          </td>
        </tr>
      </tbody>
    </table>
  </div>
</template>

<style scoped></style>
