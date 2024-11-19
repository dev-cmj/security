<script setup>
import { defineProps } from "vue";

defineProps({
  isLoggedIn: {
    type: Boolean,
    required: true,
  },
});
</script>

<template>
  <div :class="{ vdiContent: isLoggedIn }">
    <router-view></router-view>
  </div>
</template>

<style scoped></style>
