<script setup>
import {ref} from "vue";
import HeaderMenu from "@/components/common/header/HeaderMenu.vue";
import {userManageStore} from "@/stores/user/userStore.js";
import BasicButton from "@/components/common/button/BasicButton.vue";

const userStore = userManageStore();

const menus = [
  {id: 1, name: "Home", link: "/", parent: null, depth: 1},
  {id: 2, name: "About", link: "/about", parent: null, depth: 1},
  {id: 3, name: "Services", link: "/services", parent: null, depth: 1},
  {id: 4, name: "Contact", link: "/contact", parent: null, depth: 1},
];

const clickedPageUrl = ref("");
const handleMenuClick = (url) => {
  clickedPageUrl.value = url;
};
</script>

<template>
  <ul class="menu">
    <template v-for="menu in menus" :key="menu.id">
      <HeaderMenu :menu="menu" :clicked-page-url="clickedPageUrl" @menu-click="handleMenuClick"/>
    </template>
    <BasicButton @click="userStore.logout"
                 v-if="userStore.isLoggedIn()"
                 btn-text="Logout">
    </BasicButton>
  </ul>
</template>

<style scoped>
.menu {
  list-style: none;
  padding: 0;
}

.menu > li {
  margin-bottom: 10px;
}
</style>