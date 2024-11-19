import { defineStore } from "pinia";
import { ref } from "vue";

export const userManageStore = defineStore("userStore", () => {
  const initialState = {
    menus: [],
    selectedTopMenu: {},
    defaultHomeUrl: "",
    selectedSubMenu: {},
  };

  const menus = ref(initialState.menus);
  const selectedTopMenu = ref(initialState.selectedTopMenu);
  const defaultHomeUrl = ref(initialState.defaultHomeUrl);
  const selectedSubMenu = ref(initialState.selectedSubMenu);

  const getAllMenus = () => {
    return menus.value;
  };

  const setAllMenus = () => {
    // service에서 Api 호출
  };

  const getSelectedTopMenu = () => {
    return selectedTopMenu;
  };

  const setSelectedTopMenu = () => {
    // service에서 Api 호출
  };

  const getDefaultHomeUrl = () => {
    return defaultHomeUrl.value;
  };

  const setDefaultHomeUrl = () => {
    // service에서 Api 호출
  };

  const getSelectedSubMenu = () => {
    return selectedSubMenu;
  };

  const setSelectedSubMenu = () => {
    // service에서 Api 호출
  };

  return {
    getAllMenus,
    setAllMenus,
    getSelectedTopMenu,
    setSelectedTopMenu,
    getDefaultHomeUrl,
    setDefaultHomeUrl,
    getSelectedSubMenu,
    setSelectedSubMenu,
  };
});
