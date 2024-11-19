<script setup>
import SampleContent from "@/components/sample/SampleContent.vue";
import SampleNav from "@/components/sample/SampleNav.vue";
import MainContent from "@/components/layout/main/MainContent.vue";
</script>

<template class="main">
  <!-- TODO.기존 clovirVDI (진행 시 참고, 완료 되면 삭제)-->
  <!--  <Header v-if="isLoggedIn && exist"/>-->
  <!--  <LeftMenu v-if="isLoggedIn && exist" />-->
  <!--  <MainContent v-if="!loading.isMainLoading" :isLoggedIn="isLoggedIn" :class="!exist ? 'exist-content' : ''" />-->

  <!--  <Header></Header>-->
  <!--  <SideMenuNav></SideMenuNav>-->
    <MainContent :is-logged-in="true"></MainContent>

  <!-- 샘플 확인용  -->
<!--  <SampleNav></SampleNav>-->
<!--  <SampleContent></SampleContent>-->
</template>

<style scoped>
.main {
  background: #fff;
}
</style>
