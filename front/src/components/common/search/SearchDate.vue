<script setup>
import { ref, watch, computed } from "vue";
import useAlert from "@/hooks/alert.js";
import { useI18n } from "vue-i18n";

const { t } = useI18n();
const props = defineProps({
  fieldName: String,
  modelValue: {
    type: Array,
    default: () => ["", ""],
  },
});

const emit = defineEmits(["update:modelValue", "errorState"]);
const alert = useAlert();
const startDate = ref(props.modelValue[0]);
const endDate = ref(props.modelValue[1]);
const errorState = ref(false);

const isValidDateRange = computed(() => {
  if (!startDate.value || !endDate.value) return true;
  return new Date(startDate.value) <= new Date(endDate.value);
});

const updateUrlParams = (startDate, endDate) => {
  const url = new URL(window.location);
  url.searchParams.set("startDate", startDate || "");
  url.searchParams.set("endDate", endDate || "");
  window.history.pushState({}, "", url);
};

const validateAndUpdateDate = () => {
  if (!isValidDateRange.value) {
    startDate.value = null;
    endDate.value = "";
    errorState.value = true;
    alert.showWarningAlert("", t("portal.common.date.validation.start.not.after.end"));
    emit("errorState", errorState.value);
  } else {
    errorState.value = false;
  }
  emit("update:modelValue", [startDate.value, endDate.value]);
  updateUrlParams(startDate.value, endDate.value);
};

watch([startDate, endDate], () => {
  validateAndUpdateDate();
});

watch(
  () => props.modelValue,
  newVal => {
    startDate.value = newVal[0];
    endDate.value = newVal[1];
  },
);
</script>

<template>
  <th>{{ props.fieldName }}</th>
  <td>
    <input type="date" class="calendarInput search" v-model="startDate" @change="validateAndUpdateDate" /> ~
    <input type="date" class="calendarInput search" v-model="endDate" @change="validateAndUpdateDate" />
  </td>
</template>
