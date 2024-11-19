import { defineStore } from "pinia";
import { ref } from "vue";

export const useModalStore = defineStore("sampleModalStore", () => {
  const modalVisible = ref(false);

  const showModal = () => {
    modalVisible.value = true;
  };

  const hideModal = () => {
    modalVisible.value = false;
  };

  return {
    showModal,
    hideModal,
    modalVisible,
  };
});
