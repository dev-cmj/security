<script setup>
import { useAlertStore } from "@/stores/alert/alertStore.js";

let alert = useAlertStore();
const hide = () => {
  const action = alert.warningAlert.action;
  if (action) {
    action();
  }
  alert.hideWarningAlert();
};
</script>

<template>
  <b-modal id="validateAlert" v-model="alert.warningAlert.visible" style="z-index: 2000" title="">
    <div class="main-msg" id="validateAlertLabel">
      <div style="width: 30px">
        <img src="@/assets/images/ic_delete.svg" />
      </div>
      <span class="msg">{{ alert.warningAlert.title }}</span>
    </div>
    <div class="custom-msg" style="overflow-y: auto; max-height: 300px">
      <span class="msg">{{ alert.warningAlert.message }}</span>
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
