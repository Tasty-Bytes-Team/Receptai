import { reactive } from "vue";
import type { Message, Link } from "@/typescript/types";

export const store = reactive<Message>({
  text: "",
  show: false,
});

export const addNotification = (
  text: string,
  label?: "Error" | "Success",
  links?: Link[]
) => {
  //Delete old notification if it is visible
  resetNotification();

  store.text = text;
  store.show = true;
  label && (store.label = label);
  links && (store.links = links);
};

export const resetNotification = () => {
  store.text = "";
  store.show = false;
  store.label = undefined;
  store.links = undefined;
};
