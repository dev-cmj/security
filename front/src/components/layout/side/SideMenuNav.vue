<script setup>
import { computed, ref, watch } from "vue";

import { useStore } from "vuex";
const store = useStore();
const selectedTopMenu = computed(() => store.state.selectedTopMenu);
const selectedSubMenu = computed(() => store.state.selectedSubMenu);
const selectedMenuUrl = ref(null);
const clickedLeftMenu = menu => {
  store.commit("setSelectedSubMenu", menu);
  init();
};

const init = () => {
  selectedMenuUrl.value = selectedSubMenu.value.pageUrl;
};


watch(
  () => selectedSubMenu.value,
  // eslint-disable-next-line no-unused-vars
  newSelectedMenu => {
    init();
  },
);

init();
</script>
<template>
  <div id="left_menu" class="lnb">
    <div class="tit">{{ selectedTopMenu.menuName }}</div>
    <ul class="menu">
      <template v-for="menu in selectedTopMenu.subMenuList" :key="menu.id">
        <li class="menu__{{menu.id}}">
          <router-link
            :to="menu.pageUrl"
            :class="{ selectedMenu: menu.pageUrl == selectedMenuUrl }"
            @click.prevent="clickedLeftMenu(menu)"
            >{{ menu.menuName }}</router-link
          >
        </li>
      </template>
    </ul>
  </div>
</template>
