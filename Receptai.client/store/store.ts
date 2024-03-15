// store.js
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