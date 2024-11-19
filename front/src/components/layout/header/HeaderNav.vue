<script setup>
import { computed } from "vue";
import { useStore } from "vuex";
import HeaderMenu from "@/components/common/header/HeaderMenu.vue";
const store = useStore();
const menus = computed(() => store.getters.getMenus);
</script>

<template>
  <ul class="menu">
    <template v-for="menu in menus" :key="menu.id">
      <HeaderMenu :menu="menu"></HeaderMenu>
    </template>
  </ul>
</template>

<style scoped></style>
