import axios from "axios";

interface User {
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
        .catch(async () => {
          TastyBytes_user.value = null;
          return navigateTo("/user/login");
        });
      return navigateTo("/user/dashboard");
    } catch (e) {
      console.log("To-dashboard", e);
      return;
    }
  } else {
    return;
  }
});
