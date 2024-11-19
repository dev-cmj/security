<!-- eslint-disable vue/multi-word-component-names -->
<script setup>
import { ref } from "vue";
import BasicRadio from "@/components/common/radio/BasicRadio.vue";
import FormRadio from "@/components/common/radio/FormRadio.vue";
import SampleCustomRadio from "@/components/sample/radio/SampleCustomRadio.vue";

const formRadioValue = ref(false);
const basicRadioValue = ref(false);

const formRadioChange = () => {
  console.log(formRadioValue.value, "form radio");
};

const basicRadioChange = () => {
  console.log(basicRadioValue.value, "form radio");
};
</script>
<!--TODO. 해당 Sample 폴더 맟 하위에 생긴 컴포넌트는 기존 5.0 이관 후 화면에서 테스트용 및 참고용이다.
          어느 정도 컴포넌트 사용이 정리된 후에는 이 폴더 자체를 삭제 해야 한다.  -->

<template>
  <div class="case-title">Radio 컴포넌트 (무지성 복붙 금지 !!!!)</div>
  <div class="case-box">
    <div class="desc-text">case1. basic radio component : 기본 radio 컴포넌트</div>
    <div class="case-item">
      <BasicRadio v-model="basicRadioValue" @change="basicRadioChange"></BasicRadio>
    </div>
  </div>

  <div class="case-box">
    <div class="desc-text">case2. form radio component : table 태그 내에서 사용</div>
    <div class="case-item">
      <FormRadio title="formRadio" v-model="formRadioValue" @change="formRadioChange"></FormRadio>
    </div>
  </div>

  <div class="case-box">
    <div class="desc-text">
      case3. custom radio component : 특수한 radio 만들 때 참고 (radio 요소가 많을 때, 따로 설정이 필요한 경우 )
    </div>
    <div class="case-item">
      <SampleCustomRadio></SampleCustomRadio>
    </div>
  </div>
</template>

<style scoped>
.case-title {
  font-size: 20px;
  font-weight: bold;
}
.desc-text {
  font-size: 14px;
  font-weight: bold;
  border: 2px solid lightblue;
  width: 800px;
  height: 50px;
  text-align: center;
  padding: 10px;
  margin-bottom: 10px;
  margin-top: 10px;
  margin-left: 10px;
  line-height: 2;
}
.case-box {
  border: 1px solid black;
  margin-top: 10px;
  margin-bottom: 10px;
  height: 100%;
}
.case-item {
  margin-left: 10px;
  margin-bottom: 20px;
  padding-top: 10px;
  padding-left: 5px;
}
</style>
