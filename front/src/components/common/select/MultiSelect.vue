<script setup>
import { computed, ref } from "vue";
const emit = defineEmits(["change"]);
const selectedValue = ref(null);

const props = defineProps({
  modelValue: { type: Array, default: () => [] },
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
  emit("change", selectedValue.value.data.tag);
};
</script>
<!--
 Note.
 공부 후 적용 해야 하는 부분
 - TagsElement 컴포넌트에 선택한 tag가 많아 지면 css 부분 깨짐 개선 해야함
 -->

<template>
  <Vueform>
    <TagsElement
      :caret="false"
      size="md"
      aria-hidden="true"
      :native="false"
      name="tag"
      :inline="true"
      :search="searchable"
      placeholder="선택해 주세요."
      no-results-text="No results found"
      ref="selectedValue"
      :default="modelValue"
      :items="localOptions"
      :disabled="disable"
      :style="computedWidth"
      :label-prop="textField"
      :value-prop="keyField"
      :object="true"
      :floating="false"
      @change="handleSelectChange()"
    >
    </TagsElement>
  </Vueform>
</template>

<style></style>
