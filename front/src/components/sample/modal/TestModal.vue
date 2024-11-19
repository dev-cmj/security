<script setup>
import { useModalStore } from "@/components/sample/modal/SampleModalStore.js";
import SampleModal from "@/components/sample/modal/SampleModal.vue";

const store = useModalStore();

const openModal = () => {
  store.showModal();

  console.log(store.modalVisible, "open");
};

const closeModal = () => {
  store.hideModal();

  console.log(store.modalVisible, "close");
};
</script>

<template>
  <div style="height: 50px; border: 1px solid lightgray">
    <button @click="openModal">Open</button>
  </div>
  <div style="height: 50px; border: 1px solid lightgray">
    <button @click="closeModal">Close</button>
  </div>
  <SampleModal v-model="store.modalVisible"></SampleModal>
</template>

<style>
.clovir-modal .modal-dialog {
  width: 1000px !important;
  max-width: 100%;
}
</style>
