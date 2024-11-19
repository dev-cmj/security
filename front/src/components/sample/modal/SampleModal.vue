<script setup>
import BaseModal from "@/components/common/modal/BaseModal.vue";
import { useModalStore } from "@/components/sample/modal/SampleModalStore.js";

const store = useModalStore();

const test = () => {
  console.log(store.modalVisible, "test11");
};
</script>

<template>
  <BaseModal v-model:visible="store.modalVisible" modal-class="test1" title="test" @on-show="test">
    <template #content>
      <div class="notice-content-box">testtest</div>
    </template>
    <template #footer> </template>
  </BaseModal>
</template>

<style scoped></style>
