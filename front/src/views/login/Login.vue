<!-- eslint-disable vue/multi-word-component-names -->
<script setup>

import {ref} from "vue";
import IdSaveBox from "@/views/login/IdSaveBox.vue";
import FindIdModal from "@/views//login/FindIdModal.vue";
import FindPasswordModal from "@/views//login/FindPasswordModal.vue";
import {userManageStore} from "@/stores/user/userStore.js";
import JSEncrypt from 'jsencrypt';

import { FontAwesomeIcon } from "@fortawesome/vue-fontawesome";
import { faEye, faEyeSlash } from "@fortawesome/free-solid-svg-icons";
import axios from "axios";
import router from "@/router/clovirVdiRouter.js";
import SignUpModal from "@/views/login/SignUpModal.vue"; // Import the icons

const store = userManageStore();

let loginId = ref("");
let password = ref("");
let findIdModalHide = ref(false);
let findPasswordModalHide = ref(false);
let findSignUpModalHide = ref(false);
let passwordVisible = ref(false); // New state for password visibility
let publicKey = ref("");

const getPublicKey = async () => {
  const response = await axios.get('/api/auth/public-key');
  publicKey = response.data;
};

const login = async () => {

  try {
    if (loginId.value === "") {
      alert("아이디를 입력해주세요.");
      return;
    }

    if (password.value === "") {
      alert("비밀번호를 입력해주세요.");
      return;
    }

    const encrypt = new JSEncrypt();
    await getPublicKey();
    encrypt.setPublicKey(publicKey);
    const encryptedPassword = encrypt.encrypt(password.value);
    await store.login(loginId.value, encryptedPassword);

  } catch (e) {
    const result = e.response?.data;
    if (result) {
      alert(result.message);
    } else {
      alert("로그인에 실패하였습니다.");
    }
    password.value = "";
  }
};

const setLoginId = savedId => {
  loginId.value = savedId;
};

const showFindId = () => {
  findIdModalHide.value = true;
};

const showSignUp = () => {
  findSignUpModalHide.value = true;
};

const showFindPassword = () => {
  findPasswordModalHide.value = true;
};

const togglePasswordVisibility = () => {
  passwordVisible.value = !passwordVisible.value;
};
</script>

<template>
  <div class="loginForm">
    <div class="login_input">
      <div class="login_logo">
        <img src="@/assets/images/logo.png" style="width: 240px" alt=""/>
      </div>
      <div class="login_contents">
        <form>
          <input
              v-model="loginId"
              @keyup.enter="login"
              autocomplete="off"
              type="text"
              name="loginId"
              id="loginId"
              class="login_input_text input-lg font16px"
              placeholder="아이디"
          />
          <div class="password-container">
            <div class="password-container">
              <input
                  v-model="password"
                  @keyup.enter="login"
                  :type="passwordVisible ? 'text' : 'password'"
                  autocomplete="new-password"
                  name="password"
                  id="password"
                  class="login_input_text input-lg font16px"
                  placeholder="비밀번호"
              />
              <FontAwesomeIcon style="width:18px;"
                  :icon="passwordVisible ? faEyeSlash : faEye"
                  class="password-toggle"
                  @click="togglePasswordVisibility"
              />
            </div>
            <i class="fa-solid fa-eye"></i>
          </div>

          <div id="bottom-box">
            <IdSaveBox :login-id="loginId" @set-login-id="setLoginId"/>
          </div>
          <div id="loginSubmit" class="login-btn" @click="login()">로그인</div>
        </form>
        <div class="login_info"></div>

        <div class="login_bottom">
          <div class="findUser_button" @click="showSignUp">회원가입</div>
          <div class="separator">|</div>
          <div class="findUser_button" @click="showFindId">아이디 찾기</div>
          <div class="separator">|</div>
          <div class="findUser_button" @click="showFindPassword">비밀번호 찾기</div>
        </div>
      </div>
    </div>
    <div class="login_bottom"></div>
  </div>
    <FindIdModal v-model="findIdModalHide"/>
    <FindPasswordModal v-model="findPasswordModalHide"/>
    <SignUpModal v-model="findSignUpModalHide"/>
</template>

<style scoped>
.password-container {
  align-items: center;
  position: relative;
}

.password-toggle {
  background: none;
  border: none;
  cursor: pointer;
  position: absolute;
  right: 10px;
  top: 50%;
  transform: translateY(-50%);
  font-size: 16px;
}


label[required]:after {
  content: " *";
  color: red;
}

input[type="text"] {
  width: 280px;
}

.modal-footer > * {
  margin: unset;
}

.loginForm {
  position: absolute;
  top: 50%;
  left: 50%;
  transform: translate(-50%, -50%);
}

.login_input {
  width: 400px;
  margin: 0 auto;
  border-radius: 8px;
  border: 1px solid #dadce0;
}

.login_input > ul {
  padding: 0;
}

.login_input > div {
  margin-top: 40px;
}

.login-btn:active:hover {
  cursor: pointer;
  background-color: #003569 !important;
  font-weight: bold;
}

.login-btn {
  cursor: pointer;
  width: 280px;
  height: 30px;
  border-radius: 8px;
  background-color: #003569;
  box-shadow: none !important;
  padding: 0;
  border: none !important;
  text-align: center;
  color: #ffffff !important;
  font-size: 16px;
  font-weight: 500;
  line-height: 1.9;
  letter-spacing: 1px;
  margin: 30px 0 0;
}

.login_logo {
  width: 400px;
  text-align: center;
  margin: 0 auto;
  padding: 0 0 0 0;
}

.login_input ul {
  list-style: none;
  float: left;
  padding: 0;
  margin: 0;
}

.login_input ul li {
  list-style: none;
  float: right;
  clear: both;
}

#login-form > div > div.login_input > div > ul > li:nth-child(2) > p {
  width: 265px;
  height: 39px;
  margin: 48px 142px 35px 0px;
  font-size: 35px;
  font-weight: bold;
  font-stretch: normal;
  font-style: normal;
  line-height: normal;
  letter-spacing: normal;
  text-align: center;
  color: #42a7f2;
}

.login_input .checkbox {
  float: left;
  width: 18px;
  height: 18px;
  border-radius: 3px;
  border: solid 2px #929594;
  background-color: #929594;
  margin: 4px 10px 0px 0px;
}

.login_input .checkbox_text {
  width: 100px;
  height: 16px;
  font-size: 12px;
  font-weight: normal;
  font-stretch: normal;
  font-style: normal;
  line-height: 19px;
  letter-spacing: normal;
  text-align: left;
  color: #929594;
  margin: 0;
}

.login_input_text {
  margin: 0 77px 10px 0;
}

.login_contents {
  padding-left: 59px;
  padding-right: 59px;
}

.font16px {
  font-size: 16px;
}

#bottom-box {
  display: flex;
  justify-content: space-between;
  margin-top: 20px;
}

.findUser_button {
  cursor: pointer;
  font-size: 14px;
  color: #696969;
  text-decoration: none;
  margin: 20px 10px;
  display: inline-block;
}

.findUser_button:hover {
  text-decoration: underline;
  color: #003569;
}

.findID_button,
.findPWD_button {
  margin: 0 20px;
  display: inline-block;
}

.loginForm {
  position: absolute;
  top: 50%;
  left: 50%;
  transform: translate(-50%, -50%);
  padding: 40px;
  background-color: #ffffff;
  border-radius: 10px;
  width: 400px;
}

.login_logo img {
  width: 240px;
  margin: 0 auto;
  display: block;
}

.login_contents {
  margin-top: 20px;
}

.login_input_text {
  width: 100%;
  padding: 10px;
  margin: 10px 0;
  border: 1px solid #ccc;
  border-radius: 5px;
  font-size: 14px;
}

.login-btn {
  cursor: pointer;
  width: 100%;
  height: 40px;
  border-radius: 5px;
  background-color: #003569;
  color: #ffffff;
  border: none;
  font-size: 16px;
  font-weight: bold;
  margin-top: 20px;
  transition: background-color 0.3s ease;
}

.login-btn:hover {
  background-color: #002855;
}

.password-container {
  position: relative;
  margin-bottom: 20px;
}

.password-toggle {
  position: absolute;
  top: 50%;
  right: 10px;
  transform: translateY(-50%);
  font-size: 16px;
  cursor: pointer;
  color: #ccc;
}

.password-toggle:hover {
  color: #333;
}

#bottom-box {
  margin-top: 10px;
  display: flex;
  justify-content: space-between;
}

.login_bottom {
  text-align: center;
  margin-top: 20px;
}

.login_bottom div {
  font-size: 12px;
  color: #696969;
  display: inline-block;
}

.login_bottom .separator {
  margin: 0 10px;
  color: #ccc;
}
</style>
