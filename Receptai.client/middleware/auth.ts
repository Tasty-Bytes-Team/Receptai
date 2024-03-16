import axios from "axios";

interface User {
  id: number;
  name: string;
  email: string;
}
interface UserCookie {
  token: string;
  expiresIn: number;
  user: User;
}

export default defineNuxtRouteMiddleware(async () => {
  const config = useRuntimeConfig();

  const TastyBytes_user = useCookie<UserCookie | null>("TastyBytes_user");

  if (TastyBytes_user.value) {
    const userObj: UserCookie = TastyBytes_user.value;

    try {
      axios
        .get(`${config.public.baseURL}/api/v1/user/me`, {
          headers: { Authorization: `Bearer ${userObj.token}` },
        })
        .then(() => {
          return;
        })
        .catch(async () => {
          return navigateTo("/user/login");
        });
    } catch (e) {
      console.log("Auth", e);
      TastyBytes_user.value = null;
      return navigateTo("/user/login");
    }
  } else {
    return navigateTo("/user/login");
  }
});
