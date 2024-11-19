import { defineStore } from "pinia";
import { ref } from "vue";

export const useLoading = defineStore("loadingStore", () => {
  const isLoading = ref(false);
  /**
   * mainLoading은 App.vue의 MainContent 로딩 용도로 사용
   * 페이지 로딩 시 기본적으로 호출하는 api(다국어 등등)가 끝나기 전에는 MainContent가 렌더링 되지 않도록..
   */
  const isMainLoading = ref(false);

  const startLoading = () => {
    isLoading.value = true;
  };
  const endLoading = () => {
    isLoading.value = false;
  };

  const startMainLoading = () => {
    isMainLoading.value = true;
  };
  const endMainLoading = () => {
    isMainLoading.value = false;
  };

  return {
    isLoading,
    isMainLoading,
    startLoading,
    startMainLoading,
    endLoading,
    endMainLoading,
  };
});
