<script setup>
import BaseModal from "@/components/common/modal/BaseModal.vue";
import { defineModel, ref, watchEffect } from "vue";
import useAlert from "@/hooks/alert.js";
import { useI18n } from "vue-i18n";
import axios from "axios";

const alert = useAlert();
const i18n = useI18n();
let hide = defineModel({ default: false });
const username = ref("");
const email = ref("");

const submit = async () => {
  if (username.value === "") return alert.showWarningAlert("", i18n.t("login.empty.email"));
  if (email.value === "") return alert.showWarningAlert("", i18n.t("login.empty.name"));

  try {
    await axios.post("/api/member/recovery/findMyId", { userName: username.value, email: email.value });
    alert.showWarningAlert("", i18n.t("application.member.find.id.success"));
  } catch (e) {
    const title = e.response.data.title;
    alert.showWarningAlert("", title);
  }

  hide.value = false;
};

watchEffect(() => {
  if (hide.value) {
    username.value = "";
    email.value = "";
  }
});
</script>

<template>
  <BaseModal v-model="hide" title="아이디 찾기">
    <template #content>
      <div class="mb-3" style="margin-top: 14px">
        <label for="name" class="form-label" required>{{ $t("login.name") }}</label>
        <input type="text" v-model="username" class="form-control" name="userName" :placeholder="$t('login.name')" />
      </div>
      <div class="mb-3">
        <label for="email" class="form-label" required>{{ $t("login.email") }}</label>
        <input
          type="email"
          class="form-control"
          name="email"
          v-model="email"
          aria-describedby="emailHelp"
          :placeholder="$t('login.email')"
        /></div
    ></template>
    <template #footer
      ><div class="submit-block">
        <div class="submit-button form-control" type="button" @click="submit">
          {{ $t("alerts.confirm") }}
        </div>
      </div>
    </template>
  </BaseModal>
</template>

<style scoped>
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
