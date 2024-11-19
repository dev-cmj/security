<script setup>
import BasicRadio from "@/components/common/radio/BasicRadio.vue";
import { ref } from "vue";

const sampleOptions = ref([
  { name: "radio1", seq: "1" },
  { name: "radio2", seq: "2" },
  { name: "radio3", seq: "3" },
  { name: "radio4", seq: "4" },
]);

const radioValue = ref("1");
const disableRadioValue = ref("4");

const enableCustomRadioChange = () => {
  console.log(radioValue.value, "form radio");
};

const disableCustomRadioChange = () => {
  console.log(disableRadioValue.value, "disabled radio");
};
</script>

<!--
 해당 sample는 radio의 option을 다르게 줘야 하는 경우를 위한 컴포넌트 이다.
 !!!! 예시 이기 때문에 무지성 복붙 으로 사용 금지 !!!!!
 -->
<template>
  <div style="border: 1px solid lightgray; padding: 10px">
    <BasicRadio
      v-model="radioValue"
      :options="sampleOptions"
      text-field="name"
      value-field="seq"
      @change="enableCustomRadioChange"
    ></BasicRadio>
  </div>
  <div style="border: 1px solid lightgray; padding: 10px">
    <BasicRadio
      v-model="disableRadioValue"
      :options="sampleOptions"
      text-field="name"
      value-field="seq"
      :disabled="true"
      @change="disableCustomRadioChange"
    ></BasicRadio>
  </div>
</template>

<style scoped></style>
