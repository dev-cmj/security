<script setup>
import { defineProps } from "vue";
import Header from "@/components/layout/header/Header.vue";
import SideMenuNav from "@/components/layout/side/SideMenuNav.vue";

defineProps({
  isLoggedIn: {
    type: Boolean,
    required: true,
  },
});
</script>

<template>
<!--  <div :class="{ vdiContent: isLoggedIn }">-->
    <router-view></router-view>
<!--  </div>-->
</template>

<style scoped></style>
