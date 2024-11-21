<script setup>
import BaseModal from "@/components/common/modal/BaseModal.vue";
import {defineModel, ref, watchEffect} from "vue";
import axios from "axios";

const hide = defineModel({default: false});

const formData = ref({
  id: "",
  password: "",
  confirmPassword: "",
  email: "",
});

const error = ref(null);

const submit = async () => {
  // 입력값 검증
  if (formData.value.id === "") {
    alert("아이디를 입력해주세요.");
    return;
  }
  if (formData.value.password === "") {
    alert("비밀번호를 입력해주세요.");
    return;
  }
  if (formData.value.password !== formData.value.confirmPassword) {
    alert("비밀번호와 비밀번호 확인이 일치하지 않습니다.");
    return;
  }
  if (formData.value.email === "") {
    alert("이메일을 입력해주세요.");
    return;
  }

  // 서버로 데이터 전송
  try {
    await axios.post("/api/member/signup", {
      id: formData.value.id,
      password: formData.value.password,
      email: formData.value.email,
    });
    alert("회원가입이 완료되었습니다.");
    hide.value = false;
  } catch (e) {
    alert("회원가입 중 오류가 발생했습니다. 다시 시도해주세요.");
  }
};

// 모달 초기화
const resetForm = () => {
  formData.value = {
    id: "",
    password: "",
    confirmPassword: "",
    email: "",
  };
};

watchEffect(() => {
  if (hide.value) {
    resetForm();
  }
});
</script>

<template>
  <BaseModal v-model="hide" title="회원가입">
    <template #content>
      <!-- 아이디 -->
      <div class="mb-3" style="margin-top: 14px">
        <label for="id" class="form-label" required>아이디</label>
        <input
            type="text"
            v-model="formData.id"
            class="form-control"
            name="id"
            placeholder="아이디"
        />
      </div>

      <!-- 비밀번호 -->
      <div class="mb-3" style="margin-top: 14px">
        <label for="password" class="form-label" required>비밀번호</label>
        <input
            type="password"
            v-model="formData.password"
            class="form-control"
            name="password"
            placeholder="비밀번호"
        />
      </div>

      <!-- 비밀번호 확인 -->
      <div class="mb-3">
        <label for="confirmPassword" class="form-label" required>비밀번호 확인</label>
        <input
            type="password"
            v-model="formData.confirmPassword"
            class="form-control"
            name="confirmPassword"
            placeholder="비밀번호 확인"
        />
      </div>

      <!-- 이메일 -->
      <div class="mb-3">
        <label for="email" class="form-label" required>이메일</label>
        <input
            type="email"
            v-model="formData.email"
            class="form-control"
            name="email"
            placeholder="이메일"
        />
      </div>
    </template>
    <template #footer
    ><div class="submit-block">
      <div class="submit-button form-control" type="button" @click="submit">
        회원가입
      </div>
    </div>
    </template>
  </BaseModal>
</template>

<style scoped>
/* 공통 스타일 */
.form-label {
  font-weight: bold;
}

label[required]:after {
  content: " *";
  color: red;
}

.submit-button {
  cursor: pointer;
  width: 440px;
  height: 30px;
  border-radius: 8px;
  background-color: #003569;
  box-shadow: none !important;
  padding: 0;
  margin: 0;
  border: none !important;
  text-align: center;
  color: #ffffff !important;
  font-size: 16px;
  font-weight: 500;
  line-height: 1.9;
  letter-spacing: 0.8px;
}

input {
  width: 435px !important;
  max-width: none;
}

</style>
