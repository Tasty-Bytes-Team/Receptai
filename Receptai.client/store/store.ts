import { reactive } from 'vue'

interface Message {
  text: string;
  show: boolean;
  label?: "Error" | "Success";
  links?: Link[];
}

interface Link{
  text: string;
  link: string;
  type?: "Black" | "Gray";
}

export const store = reactive<Message>({
  text: "",
  show: false
});

export const addNotification = (text: string, label?: "Error" | "Success", links?: Link[]) => {
  store.text = text;
  store.show = true;
  label ? store.label = label : null;
  links ? store.links = links : null;
}

export const resetNotification = () => {
  store.text = "";
  store.show = false;
  store.label = undefined;
  store.links = undefined;
}