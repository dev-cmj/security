<script setup>
import { defineProps, defineEmits } from "vue";
import { useRouter } from "vue-router";
import { userManageStore } from "@/stores/user/userStore.js"; // UserStore 가져오기

// Props와 Emits 정의
defineProps({
  menu: {
    type: Object,
    required: true,
  },
  clickedPageUrl: {
    type: [String, null],
    default: null,
  },
});

const emit = defineEmits(["menu-click"]);
const userStore = userManageStore();
const router = useRouter();
</script>

<template>
  <li @click="handleClick" :class="{ ac: menu.link === clickedPageUrl }">
    <a>{{ menu.name }}</a>
  </li>
</template>

<style scoped>
li {
  cursor: pointer;
}

li.ac {
  font-weight: bold;
  color: #007bff;
}

a {
  text-decoration: none;
  color: inherit;
}
</style>
