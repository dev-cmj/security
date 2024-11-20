import { defineStore } from "pinia";
import { ref } from "vue";
import axios from "axios";
import router from "@/router/clovirVdiRouter.js";

export const userManageStore = defineStore("userStore", () => {
  const currentUser = ref(null);
  const loggedIn = ref(false);
  const needToChangePassword = ref(false);

  const login = async (username, password) => {
    try {
      axios.defaults.withCredentials = true;
      await axios.post("/api/auth/login", {
        username,
        password,
      });

      loggedIn.value = true;
      needToChangePassword.value = false; // 임시로 false 처리 (추후 service api 호출)

      if (needToChangePassword.value) {
        await router.push("/clovirvdi/member/changePassword");
      } else {
        await router.push("/");
      }

      return true;
    } catch (error) {
      throw error;
    }
  };

  const logout = async () => {
    try {
      await axios.post("/api/auth/logout");
      loggedIn.value = false;
      currentUser.value = null;
      await router.push("/login");

    } catch (error) {
      throw error;
    }
  };

  const setLoggedIn = () => {
    loggedIn.value = true;
  };

  const setLoggedOut = () => {
    loggedIn.value = false;
  }

  const setCurrentUser = (user) => {
    currentUser.value = user;
  };

  const setNeedToChangePassword = () => {
    // service api 호출
  };

  const getCurrentUser = () => {
    return currentUser;
  };

  const isLoggedIn = () => {
    return loggedIn.value;
  };

  const getNeedToChangePassword = () => {
    return needToChangePassword.value;
  };

  return {
    login,
    logout,
    setLoggedIn,
    setLoggedOut,
    setCurrentUser,
    setNeedToChangePassword,
    getCurrentUser,
    isLoggedIn,
    getNeedToChangePassword,
  };
});
