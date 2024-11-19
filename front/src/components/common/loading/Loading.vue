<!-- eslint-disable vue/multi-word-component-names -->
<script setup>
import { useLoading } from "@/stores/loading/loadingStore.js";
import { VueSpinnerTail } from "vue3-spinners";

const loading = useLoading();
</script>

<template>
  <div v-if="loading.isLoading" class="loading-overlay">
    <div class="loading-box">
      <VueSpinnerTail size="100" />
      <div class="loading-text">Loading...</div>
    </div>
  </div>
</template>

<style scoped>
.loading-overlay {
  position: fixed;
  top: 0;
  left: 0;
  width: 100%;
  height: 100%;
  background-color: rgba(128, 128, 128, 0.4);
  display: flex;
  align-items: center;
  justify-content: center;
  z-index: 999;
}

.loading-box {
  width: 200px;
  height: 200px;
  background-color: white;
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: center;
  z-index: 1000;
  box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
  border-radius: 5px;
}

.loading-text {
  margin-top: 10px;
  font-size: 14px;
  color: #333;
}
</style>
