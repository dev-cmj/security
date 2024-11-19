import { useLoading } from "../../stores/loading/loadingStore.js";

export const startLoading = () => {
  const loading = useLoading();
  loading.startLoading();
};

export const endLoading = () => {
  const loading = useLoading();
  loading.endLoading();
};
