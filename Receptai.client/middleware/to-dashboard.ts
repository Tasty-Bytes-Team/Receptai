import axios from "axios";

export default defineNuxtRouteMiddleware(async (to, from) => {
  const TastyBytes_user = useCookie("TastyBytes_user");

  if (TastyBytes_user.value) {
    const userObj: any = TastyBytes_user.value;

    try {
      axios
        .get("/api/v1/user/me", {
          headers: { Authorization: `Bearer ${userObj.token}` },
        })
        return navigateTo("/user/dashboard");
    } catch (e) {
      return;
    }
  } else {
    return;
  }
});
