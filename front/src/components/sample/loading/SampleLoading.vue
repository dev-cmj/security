<script setup>
import { useLoading } from "@/stores/loading/loadingStore.js";

const loading = useLoading();
const loadingStart = () => {
  loading.startLoading();
};
</script>

<template>
  <button style="border: 1px solid lightgray; height: 50px; margin: 30px" @click="loadingStart">loading on</button>
</template>

<style scoped></style>
