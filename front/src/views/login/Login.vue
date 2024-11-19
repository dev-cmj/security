<!-- eslint-disable vue/multi-word-component-names -->
<script setup>

import {ref} from "vue";
import axios from "axios";
import IdSaveBox from "@/views/login/IdSaveBox.vue";
// import FindIdModal from "@/components/common/layout/login/FindIdModal.vue";
// import FindPasswordModal from "@/components/common/layout/login/FindPasswordModal.vue";
// import SelectTenantModal from "@/clovirvdi/tenant/SelectTenantModal.vue";
import {useI18n} from "vue-i18n";
import {userManageStore} from "@/stores/user/userStore.js";

const store = userManageStore();
let i18n = useI18n();

let loginId = ref("");
let password = ref("");
let findIdModalHide = ref(false);
let findPasswordModalHide = ref(false);


const login = async () => {
  if (loginId.value === "") {
    alert("아이디를 입력해주세요.");
    return;
  }

  try {
    axios.defaults.withCredentials = true; // 쿠키를 전송하기 위해
    const response = await axios.post(`http://localhost:8080/api/login`, {
      username: loginId.value,
      password: password.value
    });
  } catch (e) {
   alert(e);
  }
};

const setLoginId = savedId => {
  loginId.value = savedId;
};

const showFindId = () => {
  findIdModalHide.value = true;
};

const showFindPassword = () => {
  findPasswordModalHide.value = true;
};

</script>

<template>
  <div class="loginForm">
    <div class="login_input">
      <div class="login_logo">
        <img src="@/assets/images/logo.png" style="width: 240px"/>
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
          <input
              v-model="password"
              @keyup.enter="login"
              type="password"
              autocomplete="new-password"
              name="password"
              id="password"
              class="login_input_text input-lg font16px"
              placeholder="비밀번호"
              style="clear: both"
          />
          <div id="bottom-box">
            <IdSaveBox :login-id="loginId" @set-login-id="setLoginId"/>
          </div>
          <div id="loginSubmit" class="login-btn" @click="login()">로그인</div>
        </form>
        <div class="login_info"></div>
        <div
            type="button"
            @click="showFindId"
            class="findID_button findUser_button"
            style="float: left; margin-top: 20px; margin-left: 40px; color: #929594"
        >
          아이디 찾기
        </div>
        <div style="float: left; margin-top: 18px; color: #929594; margin-left: 19px">|</div>
        <div
            type="button"
            @click="showFindPassword"
            class="findPWD_button findUser_button"
            style="float: right; margin-top: 20px; margin-right: 40px; color: #929594"
        >
          비밀번호 찾기
        </div>
      </div>
    </div>
    <div class="login_bottom"></div>
  </div>
<!--  <FindIdModal v-model="findIdModalHide"/>-->
<!--  <FindPasswordModal v-model="findPasswordModalHide"/>-->
</template>

<style scoped>
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
  padding: 0px 0px 0px 0px;
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

.login_bottom {
  width: 400px;
  margin: 30px auto 0;
  position: relative;
}

.login_bottom div {
  color: #696969;
  display: inline-block;
  font-size: 10px;
  position: absolute;
  top: 10px;
  left: 130px;
}

.login_info {
  font-size: 12px;
  color: #3c404a;
  margin-top: 40px;
}

.font16px {
  font-size: 16px;
}

#bottom-box {
  display: flex;
  justify-content: space-between;
  margin-top: 20px;
}
</style>
