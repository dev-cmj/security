<script setup>
import { computed, ref } from "vue";
const emit = defineEmits(["change"]);
const selectedValue = ref(null);

const props = defineProps({
  modelValue: { type: Object },
  options: { type: Array, default: () => [] },
  addAllOptions: { type: Boolean, default: false },
  searchable: { type: Boolean, default: false },
  width: { type: String, default: "280px" },
  height: { type: String, default: "30px" },
  keyField: { type: String, default: "value" },
  textField: { type: String, default: "label" },
  disable: { type: Boolean, default: false },
});

const computedWidth = computed(() => ({
  width: props.width,
  height: props.height,
}));

const localOptions = computed(() => {
  let allObj = { value: "", label: "전체" };
  const baseOptions = Array.isArray(props.options) ? props.options : [];
  return props.addAllOptions ? [allObj, ...baseOptions] : baseOptions;
});

const handleSelectChange = () => {
  emit("change", selectedValue.value.data.select);
};
</script>
<!--
 Note.
 공부 후 적용 해야 하는 부분
 - SelectElement 컴포넌트에 rules : 'required' 라는걸 제공해주는데 지금 css가 깨져서 해당 부분은 활성화 해놓지 않음
 -->
<template>
  <Vueform :display-errors="false">
    <SelectElement
      size="md"
      aria-hidden="true"
      :native="false"
      name="select"
      :inline="true"
      :search="searchable"
      placeholder="선택해 주세요."
      no-results-text="No results found"
      ref="selectedValue"
      :default="modelValue"
      :items="localOptions"
      :style="computedWidth"
      :disabled="disable"
      :object="true"
      :floating="false"
      :label-prop="textField"
      :value-prop="keyField"
      @change="handleSelectChange()"
    >
    </SelectElement>
  </Vueform>
</template>

<style>
.vf-errors {
  display: flex;
}
</style>
