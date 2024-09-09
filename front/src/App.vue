<script setup>
import MainContent from "@/components/common/layout/MainContent.vue";
import {useStore} from "vuex";
import {computed} from "vue";

const store = useStore();
// const isLoggedIn = computed(() => store.getters.isLoggedIn);

</script>

<template>
  <MainContent/>
</template>

<style scoped>
</style>
