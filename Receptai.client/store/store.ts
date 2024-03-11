// store.js
import { reactive } from 'vue'

interface Message {
  text: string;
  show: boolean;
}

export const store = reactive<Message>({
  text: "",
  show: false
});