<script setup>
import { useAlertStore } from "@/stores/alert/alertStore.js";

const alertStore = useAlertStore();

const showSave = () => {
  alertStore.showSaveAlert("저장 되었습니다~!", "저장 메세지");
};

const showWarn = () => {
  alertStore.showWarningAlert("경고!!", "경고 메세지");
};

const showDelete = () => {
  alertStore.showDeleteAlert("삭제!!", "삭제 메세지");
};
</script>

<template>
  <div class="alert-item">
    <button @click="showSave">Save</button>
  </div>
  <div class="alert-item">
    <button @click="showWarn">Warn</button>
  </div>
  <div class="alert-item">
    <button @click="showDelete">Delete</button>
  </div>
</template>

<style scoped>
.alert-item {
  height: 30px;
  border: 1px solid lightgray;
  margin: 10px;
}
</style>
