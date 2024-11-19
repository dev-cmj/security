<script setup>
import { defineModel } from "vue";

defineProps({
  name: { type: String, require: true },
  textField: { type: String, default: "textField" },
  valueField: { type: String, default: "valueField" },
  options: {
    type: Array,
    default: () => [
      { textField: "예", valueField: true },
      { textField: "아니오", valueField: false },
    ],
  },
  disabled: { type: Boolean, default: false },
});

const emit = defineEmits(["change"]);
const selected = defineModel({ default: null });

const handleChange = selectedValue => {
  selected.value = selectedValue;
  emit("change");
};
</script>
<!--
 Note.
 options, textField, valueField 값을 지정 해주지 않으면
 기본적 으로 많이 사용 되는 [ 예, 아니오 ] radio 버튼 으로 생긴다.
 다른 요소가 필요 하면 위 값들을 지정 해주면 된다.
 sample/SampleCustomRadio 참고 하면서 진행
 -->
<template>
  <div class="box">
    <template v-for="option in options" :key="option[valueField]">
      <label class="checkbox">
        <input
          type="radio"
          :disabled="disabled"
          :name="name"
          @click="handleChange(option[valueField])"
          :value="option[valueField]"
          v-model="selected"
        />
        <span>{{ option[textField] }}</span>
      </label>
    </template>
  </div>
</template>

<style scoped>
.checkbox {
  margin-right: 14px;
}
.box {
  display: inline-block;
  min-width: 280px;
}
</style>
