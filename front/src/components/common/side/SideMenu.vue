<script setup>
import { computed } from "vue";

import { useStore } from "vuex";

const store = useStore();
const selectedTopMenu = computed(() => store.state.selectedTopMenu);

</script>
<template>
  <div id="left_menu" class="lnb">
    <div class="tit">{{ selectedTopMenu.menuName }}</div>
    <ul class="menu">
      <template v-for="menu in selectedTopMenu.subMenuList" :key="menu.id">
        <SideMenu :menu="menu"></SideMenu>
      </template>
    </ul>
  </div>
</template>
