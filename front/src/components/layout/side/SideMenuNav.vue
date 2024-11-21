<script setup>
import { computed, ref, watch } from "vue";

import { useStore } from "vuex";
const selectedMenuUrl = ref(null);

const init = () => {
};


</script>
<template>
  <div id="left_menu" class="lnb">
<!--    <div class="tit">{{ selectedTopMenu.menuName }}</div>-->
<!--    <ul class="menu">-->
<!--      <template v-for="menu in selectedTopMenu.subMenuList" :key="menu.id">-->
<!--        <li class="menu__{{menu.id}}">-->
<!--          <router-link-->
<!--            :to="menu.pageUrl"-->
<!--            :class="{ selectedMenu: menu.pageUrl == selectedMenuUrl }"-->
<!--            @click.prevent="clickedLeftMenu(menu)"-->
<!--            >{{ menu.menuName }}</router-link-->
<!--          >-->
<!--        </li>-->
<!--      </template>-->
<!--    </ul>-->
  </div>
</template>
