import axios from "axios";

export default defineNuxtRouteMiddleware(async (to, from) => {
  const TastyBytes_user = useCookie("TastyBytes_user");

  if (TastyBytes_user.value) {
    const userObj:any = TastyBytes_user.value;

    try{
      axios
      .get("/api/v1/user/me", {
        headers: { Authorization: `Bearer ${userObj.token}` },
      })
      .then(() => {
        return;
      });
    } catch(e){
      return navigateTo("/user/login");;
    }
  } else {
    return navigateTo("/user/login");
  }
});
