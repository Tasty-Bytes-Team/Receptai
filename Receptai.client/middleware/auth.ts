import axios from "axios";
import type { UserCookie } from "@/typescript/types";

export default defineNuxtRouteMiddleware(async (to, from) => {
  const config = useRuntimeConfig();

  const TastyBytes_user = useCookie<UserCookie | null>("TastyBytes_user");

  if (!TastyBytes_user.value) {
    return navigateTo("/user/login");
  }

  try {
    axios.get(`${config.public.baseURL}/api/v1/user/me`, {
      headers: { Authorization: `Bearer ${TastyBytes_user.value.token}` },
    });
  } catch (error) {
    console.log("Auth", error);
    useCookie("TastyBytes_user").value = null;
    return navigateTo("/user/login");
  }
});
