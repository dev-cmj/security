<script setup>
import { computed, createVNode, defineProps, useSlots } from "vue";
import SearchEmpty from "@/components/common/search/SearchEmpty.vue";

// 검색조건은 하나의 row에 2개씩 기본으로 들어감
const props = defineProps({
  size: {
    type: Number,
    default: 2,
  },
  tableWrapBorderTop: { type: String },
});

const setBorder = () => {
  let styles = "";
  if (props.tableWrapBorderTop) {
    styles += `border-top: ${props.tableWrapBorderTop};`;
  }
  return styles;
};

const emit = defineEmits(["fetch"]);
const slots = useSlots();
const fetch = () => {
  emit("fetch");
};

// 전달받은 검색조건 요소를 하나의 row의 2개씩 넣을 수 있도록 그룹화
const groupedSearchList = computed(() => {
  const searchList = slots.default ? slots.default() : [];
  const groupedList = [];

  // 전달된 검색 요소를 2개씩 그룹화
  for (let i = 0; i < searchList.length; i += props.size) {
    groupedList.push(searchList.slice(i, i + props.size));
  }

  // 마지막 그룹이 홀수인 경우 SearchEmpty 컴포넌트 추가
  const lastGroup = groupedList[groupedList.length - 1];
  if (lastGroup && lastGroup.length < props.size) {
    lastGroup.push(createVNode(SearchEmpty));
  }

  return groupedList;
});
</script>

<template>
  <section class="mainSearch">
    <div class="tableWrap" :style="setBorder()">
      <table>
        <tbody>
          <tr v-for="(searchGroup, index) in groupedSearchList" :key="index">
            <template v-for="(searchComponent, childIndex) in searchGroup" :key="searchComponent.key || childIndex">
              <component :is="searchComponent" />
            </template>
          </tr>
        </tbody>
      </table>
    </div>
    <button @click="fetch" class="searchBtn">조회</button>
  </section>
</template>

<style></style>
