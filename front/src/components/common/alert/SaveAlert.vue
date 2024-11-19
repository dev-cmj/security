<script setup>
import { useAlertStore } from "@/stores/alert/alertStore.js";

const alertStore = useAlertStore();

const hide = () => {
  const action = alertStore.saveAlert.action;
  if (action) {
    action();
  }
  alertStore.hideSaveAlert();
};
</script>

<template>
  <b-modal id="saveAlert" v-model="alertStore.saveAlert.visible" style="z-index: 2000" title="">
    <div class="main-msg" id="validateAlertLabel">
      <div style="width: 30px">
        <img src="@/assets/images/ic_check.svg" />
      </div>
      <span class="msg">{{ alertStore.saveAlert.title }}</span>
    </div>
    <div class="custom-msg">
      <span class="msg">{{ alertStore.saveAlert.message }}</span>
    </div>
    <template #footer>
      <button name="complete" type="button" class="btn-s-df" @click="hide">확인</button>
    </template>
  </b-modal>
</template>

<style scoped>
span {
  font-size: 14px;
}

.main-msg {
  display: flex;
  justify-content: center;
  padding-bottom: 20px;
}

.custom-msg {
  text-align: center;
}
</style>
