<script setup>
import SampleContent from "@/components/sample/SampleContent.vue";
import SampleNav from "@/components/sample/SampleNav.vue";
import MainContent from "@/components/layout/main/MainContent.vue";
import {userManageStore} from "@/stores/user/userStore.js";
import Header from "@/components/layout/header/Header.vue";
import SideMenuNav from "@/components/layout/side/SideMenuNav.vue";

const store = userManageStore();
</script>

<template class="main">
  <Header v-if="store.isLoggedIn()" />
  <SideMenuNav v-if="store.isLoggedIn()" />
  <MainContent :is-logged-in="store.isLoggedIn()"/>
</template>

<style scoped>
.main {
  background: #fff;
}
</style>
