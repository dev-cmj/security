import { defineStore } from "pinia";
import { ref } from "vue";

export const useAlertStore = defineStore("alertStore", () => {
  const initialState = {
    warningAlert: {
      title: "",
      message: "",
      visible: false,
      action: null,
    },
    saveAlert: {
      title: "",
      message: "",
      visible: false,
      action: null,
    },
    deleteAlert: {
      title: "",
      message: "",
      visible: false,
      action: null,
    },
  };

  const warningAlert = ref({ ...initialState.warningAlert });
  const saveAlert = ref({ ...initialState.saveAlert });
  const deleteAlert = ref({ ...initialState.deleteAlert });

  const showWarningAlert = (title, message) => {
    warningAlert.value.title = title;
    warningAlert.value.message = message;
    warningAlert.value.visible = true;
  };

  const showWarningAlertWithAction = (title, message, action) => {
    warningAlert.value.title = title;
    warningAlert.value.message = message;
    warningAlert.value.action = action;
    warningAlert.value.visible = true;
  };

  const hideWarningAlert = () => {
    warningAlert.value.title = "";
    warningAlert.value.message = "";
    warningAlert.value.action = null;
    warningAlert.value.visible = false;
  };

  const showSaveAlert = (title, message) => {
    saveAlert.value.title = title;
    saveAlert.value.message = message;
    saveAlert.value.visible = true;
  };

  const showSaveAlertWithAction = (title, message, action) => {
    saveAlert.value.title = title;
    saveAlert.value.message = message;
    saveAlert.value.action = action;
    saveAlert.value.visible = true;
  };

  const hideSaveAlert = () => {
    saveAlert.value.title = "";
    saveAlert.value.message = "";
    saveAlert.value.action = null;
    saveAlert.value.visible = false;
  };

  const showDeleteAlert = (title, message) => {
    deleteAlert.value.title = title;
    deleteAlert.value.message = message;
    deleteAlert.value.visible = true;
  };

  const showDeleteAlertWithAction = (title, message, action) => {
    deleteAlert.value.title = title;
    deleteAlert.value.message = message;
    deleteAlert.value.action = action;
    deleteAlert.value.visible = true;
  };
  const hideDeleteAlert = () => {
    deleteAlert.value.title = "";
    deleteAlert.value.message = "";
    deleteAlert.value.action = null;
    deleteAlert.value.visible = false;
  };

  return {
    warningAlert,
    saveAlert,
    deleteAlert,
    showWarningAlert,
    showWarningAlertWithAction,
    hideWarningAlert,
    showSaveAlert,
    showSaveAlertWithAction,
    hideSaveAlert,
    showDeleteAlert,
    showDeleteAlertWithAction,
    hideDeleteAlert,
  };
});
