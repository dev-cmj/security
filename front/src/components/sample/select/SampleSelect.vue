<script setup>
import BasicSelect from "@/components/common/select/BasicSelect.vue";
import FormSelect from "@/components/common/select/FormSelect.vue";
import { ref } from "vue";
import MultiSelect from "@/components/common/select/MultiSelect.vue";
import FormMultiSelect from "@/components/common/select/FormMultiSelect.vue";
import BasicButtonSelect from "@/components/common/select/BasicButtonSelect.vue";

const infraManages = ref([
  {
    itemId: "InfraAdd",
    itemKey: "InfraAdd",
  },
  {
    itemId: "InfraChange",
    itemKey: "InfraChange",
  },
  {
    itemId: "InfraDelete",
    itemKey: "InfraDelete",
  },
  {
    itemId: "InfraDelete",
    itemKey: "InfraDelete",
  },
  {
    itemId: "InfraUnMapping",
    itemKey: "InfraUnMapping",
  },
]);

const items = ref([
  { id: "1", value: "vuejs", label: "Vue.js", name: "뷰", desc: "Vue.js framework", count: 10 },
  { id: "2", value: "react", label: "React", name: "리액트", desc: "React library", count: 15 },
  { id: "3", value: "angularjs", label: "AngularJS", name: "앵귤러", desc: "AngularJS framework", count: 20 },
  { id: "4", value: "svelte", label: "Svelte", name: "스벨트", desc: "Svelte framework", count: 5 },
  { id: "5", value: "backbone", label: "Backbone.js", name: "백본", desc: "Backbone.js library", count: 8 },
  { id: "6", value: "ember", label: "Ember.js", name: "엠버", desc: "Ember.js framework", count: 12 },
  // ...기본 데이터 6개
]);
const addAll = () => {
  for (let i = 7; i <= 200; i++) {
    items.value.push({
      id: `${i}`,
      value: `framework${i}`,
      label: `Framework ${i}`,
      name: `프레임워크 ${i}`,
      desc: `Description for Framework ${i}`,
      count: Math.floor(Math.random() * 100) + 1, // 1부터 100까지 랜덤 숫자
    });
  }
};

const case1 = ref({ id: "1", value: "vuejs", label: "Vue.js", name: "뷰", desc: "Vue.js framework", count: 10 });
const case10 = ref({ id: "6", value: "ember", label: "Ember.js", name: "엠버", desc: "Ember.js framework", count: 12 });
const case2 = ref({ value: "react", label: "React", name: "리액트" });
const case3 = ref([items.value[0]]);

const case1Change = changeValue => {
  case1.value = changeValue;
  console.log(case1.value, "case1 change 시 확인");
};
const case10Change = changeValue => {
  case10.value = changeValue;
  console.log(case10.value, "case10 change 시 확인");
};

const case2Change = changeValue => {
  case2.value = changeValue;
  console.log(case2.value, "case2 change 시 확인");
};

const case3Change = changeValue => {
  case3.value = changeValue;
  console.log(case3.value, "case3 change 시 확인");
};

addAll();
</script>

<template>
  <div class="select-desc">그룹 선택</div>
  <div class="select-sample-item">
    <BasicButtonSelect
      :items="infraManages"
      _key="itemId"
      _value="itemKey"
      width="130px"
      div-style="width:130px"
      border="1px solid #003569"
      title="인프라 선택"
    />
  </div>

  <div class="select-desc">단일 선택</div>
  <div class="select-sample-item">
    <BasicSelect :options="items"></BasicSelect>
    <BasicSelect :options="items" :disable="true"></BasicSelect>

    <BasicSelect
      :options="items"
      v-model="case10"
      key-field="id"
      text-field="count"
      @update:modelValue="case10Change"
    ></BasicSelect>
    <BasicSelect :options="items" v-model="case1" @change="case1Change"></BasicSelect>
    <BasicSelect :options="items" :searchable="true"></BasicSelect>
    <BasicSelect :options="items" :add-all-options="true"></BasicSelect>
    <BasicSelect :options="items" width="200px" height="200px"></BasicSelect>
  </div>
  <div class="select-sample-item">
    <FormSelect :options="items" v-model="case2" @change="case2Change"></FormSelect>
  </div>
  <div class="select-sample-item">
    <FormSelect :options="items" :required="true"></FormSelect>
  </div>

  <div class="select-desc">멀티 선택</div>
  <div class="select-sample-item">
    <MultiSelect :options="items"></MultiSelect>
    <MultiSelect :options="items" :disable="true"></MultiSelect>
    <MultiSelect :options="items" :required="true"></MultiSelect>
    <MultiSelect :options="items" :searchable="true"></MultiSelect>
    <MultiSelect :options="items" width="200px" height="200px"></MultiSelect>
  </div>
  <div class="select-sample-item">
    <FormMultiSelect :options="items" :searchable="true" v-model="case3" @change="case3Change"></FormMultiSelect>
  </div>
  <div class="select-sample-item">
    <FormMultiSelect :options="items" :searchable="true" :required="true"></FormMultiSelect>
  </div>
</template>

<style scoped>
.select-sample-item {
  margin-bottom: 10px;
}
.select-desc {
  border-bottom: 1px solid lightgray;
  height: 30px;
  font-size: 17px;
  font-weight: bold;
  margin-bottom: 30px;
  margin-top: 30px;
}
</style>
