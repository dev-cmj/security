<script setup>
import BasicSelect from "@/components/common/select/BasicSelect.vue";
const emit = defineEmits(["change"]);

defineProps({
  modelValue: { type: Object },
  options: { type: Array, default: () => [] },
  addAllOptions: { type: Boolean, default: false },
  searchable: { type: Boolean, default: false },
  width: { type: String, default: "280px" },
  height: { type: String, default: "30px" },
  keyField: { type: String, default: "value" },
  textField: { type: String, default: "label" },
  disable: { type: Boolean, default: false },
  required: { type: Boolean, default: false },
});

const handleSelectChange = newValue => {
  emit("change", newValue);
};
</script>

<template>
  <tr>
    <th :class="required ? 'req' : ''">Form Select</th>
    <td>
      <div class="flexWrap">
        <BasicSelect
          :modelValue="modelValue"
          :options="options"
          :add-all-options="addAllOptions"
          :searchable="searchable"
          :width="width"
          :height="height"
          :disable="disable"
          :label-prop="textField"
          :value-prop="keyField"
          @change="handleSelectChange"
        ></BasicSelect>
      </div>
    </td>
  </tr>
</template>

<style scoped></style>
