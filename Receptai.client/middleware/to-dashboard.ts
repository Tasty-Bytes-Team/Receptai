import axios from "axios";
import type { UserCookie } from "@/typescript/types";

export default defineNuxtRouteMiddleware(async () => {
  const config = useRuntimeConfig();

  const TastyBytes_user = useCookie<UserCookie | null>("TastyBytes_user");

  if (TastyBytes_user.value) {
    try {
      await axios.get(`${config.public.baseURL}/api/v1/user/me`, {
        headers: { Authorization: `Bearer ${TastyBytes_user.value.token}` },
      });
      return navigateTo("/user/dashboard");
    } catch (error) {
      console.error("Auth", error);
      useCookie("TastyBytes_user").value = null;
      return navigateTo("/user/login");
    }
  }
});
