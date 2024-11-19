<script setup>
import { defineModel } from "vue";
import BasicRadio from "@/components/common/radio/BasicRadio.vue";

defineProps({
  title: { type: String },
  name: { type: String, require: true },
  textField: { type: String, default: "textField" },
  valueField: { type: String, default: "valueField" },
  keyField: { type: String, default: "valueField" },
  options: { type: Array },
});
const emit = defineEmits(["change"]);
const select = defineModel({ default: null });
const handleChange = () => {
  emit("change");
};
</script>

<template>
  <tr>
    <th>{{ title }}</th>
    <td>
      <div class="flexWrap">
        <BasicRadio
          v-model="select"
          @change="handleChange"
          :name="name"
          :options="options"
          :key-field="keyField"
          :text-field="textField"
          :value-field="valueField"
        />
      </div>
    </td>
  </tr>
</template>

<style scoped></style>
