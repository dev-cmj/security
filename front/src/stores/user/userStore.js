import { defineStore } from "pinia";
import { ref } from "vue";
import axios from "axios";

export const userManageStore = defineStore("userStore", () => {

  const currentUser = ref(null);
  const loggedIn = ref(false);
  const needToChangePassword = ref(false);

  const setCurrentUser = () => {
    // service api 호출
  };

  const login = async (username, password) => {

    try {
      axios.defaults.withCredentials = true; // 쿠키를 전송하기 위해
      const response = await axios.post(`http://localhost:8080/api/login`, {
        username,
        password,
      });

      loggedIn.value = true;
      needToChangePassword.value = false;
    } catch (e) {
      const message = e.response?.data.message;
      console.log("로그인 실패", e)
      if (message !== undefined) {
        alert(message);
      } else {
        alert("로그인에 실패하였습니다.");
      }
    }

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
