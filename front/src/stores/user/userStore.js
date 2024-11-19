import { defineStore } from "pinia";
import { ref } from "vue";

export const userManageStore = defineStore("userStore", () => {

  const currentUser = ref(null);
  const loggedIn = ref(false);
  const needToChangePassword = ref(false);

  const setCurrentUser = () => {
    // service api 호출
  };

  const login = () => {
    loggedIn.value = true;
  };

  const logout = () => {
    loggedIn.value = false;
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
    setCurrentUser,
    login,
    logout,
    setNeedToChangePassword,
    getCurrentUser,
    isLoggedIn,
    getNeedToChangePassword,
  };
});
