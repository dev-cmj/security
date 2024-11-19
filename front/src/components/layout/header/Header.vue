<!-- eslint-disable vue/multi-word-component-names -->
<script setup>
import Logo from "@/components/layout/header/Logo.vue";
</script>
<!--
 Note. 현재는 메뉴를 뿌려 주는 데이터 값을 가져올 수 없어
 기존 VDI 5.0 화면 으로 가져 온거임
 해당 작업 진행 하는 사람은 작업 완료 후 주석 삭제 요망
 -->
<template>
  <div class="menu-layout-3">
    <div class="gnb">
      <Logo></Logo>
      <!--      <HeaderNav></HeaderNav>-->
      <!--      <LocaleDropdown />-->
      <!--      <UserTaskAlarm></UserTaskAlarm>-->
      <!--      <UserInfo></UserInfo>-->
    </div>
  </div>
</template>
