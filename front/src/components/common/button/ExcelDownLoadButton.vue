<script setup>
import axios from "axios";
import { saveAs } from "file-saver";
import dateUtils from "@/utils/date/currentDateUtils.js";

const props = defineProps({
  apiUrl: {
    type: String,
    required: true,
  },
  searchCondition: {
    type: Object,
    default: () => ({}),
  },
  fileName: {
    type: String,
    default: "download.xlsx",
  },
});

const downloadExcel = async () => {
  try {
    const response = await axios.post(props.apiUrl, props.searchCondition, {
      headers: {
        "Content-Type": "application/json",
      },
      responseType: "blob",
    });
    const blob = new Blob([response.data], {
      type: "application/vnd.ms-excel",
    });
    saveAs(blob, `${props.fileName}_${dateUtils.getCurrentTimestampToString()}.xlsx`);
  } catch (e) {
    console.error(e);
  }
};
</script>

<template>
  <button class="excel" @click="downloadExcel"></button>
</template>

<style scoped></style>
