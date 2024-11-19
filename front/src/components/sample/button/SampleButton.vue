<script setup>
import ExcelDownLoadButton from "@/components/common/button/ExcelDownLoadButton.vue";
import ButtonGroup from "@/components/common/button/ButtonGroup.vue";
import MainTable from "@/components/layout/main/MainTable.vue";
import BasicButton from "@/components/common/button/BasicButton.vue";
import PlusButton from "@/components/common/button/PlusButton.vue";
import CheckButton from "@/components/common/button/CheckButton.vue";
import ListButton from "@/components/common/button/ListButton.vue";
</script>

<template>
  <MainTable>
    <template #top>
      <ButtonGroup>
        <PlusButton></PlusButton>
        <PlusButton btn-text="생성"></PlusButton>

        <CheckButton></CheckButton>
        <CheckButton btn-text="완료"></CheckButton>

        <BasicButton btn-color="white"></BasicButton>
        <BasicButton btn-color="white" btn-text="취소"></BasicButton>

        <ListButton></ListButton>
        <ListButton btn-text="이전으로"></ListButton>

        <ExcelDownLoadButton api-url="/api/test"></ExcelDownLoadButton>
      </ButtonGroup>
    </template>
    <template #grid>
      <div style="height: 100px">
        <div style="margin-right: 10px; display: inline-block">
          <BasicButton btn-text="기본"></BasicButton>
        </div>
        <div style="margin-right: 10px; display: inline-block">
          <BasicButton btn-size="s" btn-text="s 사이즈"></BasicButton>
        </div>
        <div style="margin-right: 10px; display: inline-block">
          <BasicButton btn-size="m" btn-text="m 사이즈"></BasicButton>
        </div>
        <div style="margin-right: 10px; display: inline-block">
          <BasicButton btn-size="l" btn-text="l 사이즈"></BasicButton>
        </div>
        <div style="margin-right: 10px; display: inline-block">
          <BasicButton btn-size="xl" btn-text="xl 사이즈"></BasicButton>
        </div>
      </div>
      <div style="height: 100px">
        <div style="margin-right: 10px; display: inline-block">
          <BasicButton btn-size="xs" btn-color="gray" btn-text="xs + gray"></BasicButton>
        </div>
        <div style="margin-right: 10px; display: inline-block">
          <BasicButton btn-size="s" btn-color="gray" btn-text="s + gray"></BasicButton>
        </div>
        <div style="margin-right: 10px; display: inline-block">
          <BasicButton btn-size="m" btn-color="gray" btn-text="m + gray"></BasicButton>
        </div>
        <div style="margin-right: 10px; display: inline-block">
          <BasicButton btn-size="l" btn-color="gray" btn-text="l + gray"></BasicButton>
        </div>
        <div style="margin-right: 10px; display: inline-block">
          <BasicButton btn-size="xl" btn-color="gray" btn-text="xl + gray"></BasicButton>
        </div>
      </div>
      <div style="height: 100px">
        <div style="margin-right: 10px; display: inline-block">
          <BasicButton btn-size="xs" btn-color="white" btn-text="xs + white"></BasicButton>
        </div>
        <div style="margin-right: 10px; display: inline-block">
          <BasicButton btn-size="s" btn-color="white" btn-text="s + white"></BasicButton>
        </div>
        <div style="margin-right: 10px; display: inline-block">
          <BasicButton btn-size="m" btn-color="white" btn-text="m + white"></BasicButton>
        </div>
        <div style="margin-right: 10px; display: inline-block">
          <BasicButton btn-size="l" btn-color="white" btn-text="l + white"></BasicButton>
        </div>
        <div style="margin-right: 10px; display: inline-block">
          <BasicButton btn-size="xl" btn-color="white" btn-text="xl + white"></BasicButton>
        </div>
      </div>
      <div>
        <div style="margin-right: 10px; display: inline-block">
          <BasicButton btn-size="xs" btn-color="blue" btn-text="xs + blue"></BasicButton>
        </div>
        <div style="margin-right: 10px; display: inline-block">
          <BasicButton btn-size="s" btn-color="blue" btn-text="s + blue"></BasicButton>
        </div>
        <div style="margin-right: 10px; display: inline-block">
          <BasicButton btn-size="m" btn-color="blue" btn-text="m + blue"></BasicButton>
        </div>
        <div style="margin-right: 10px; display: inline-block">
          <BasicButton btn-size="l" btn-color="blue" btn-text="l + blue"></BasicButton>
        </div>
        <div style="margin-right: 10px; display: inline-block">
          <BasicButton btn-size="xl" btn-color="blue" btn-text="xl + blue"></BasicButton>
        </div>
      </div>
    </template>
  </MainTable>
</template>

<style scoped></style>
