<script setup>
import { computed, ref, watch } from "vue";
import { useStore } from "vuex";


const props = defineProps(["menu"]);
const store = useStore();

const clickedPageUrl = ref(null);
const selectedTopMenu = computed(() => store.state.selectedTopMenu);

const clickedTopMenu = menu => {
  store.commit("setSelectedTopMenu", menu);
  store.commit("setSelectedSubMenu", menu);
  init();
};

const init = () => {
  clickedPageUrl.value = selectedTopMenu.value.pageUrl;

  console.log(props.menu.pageUrl , "props.menu.pageUr");
  console.log(clickedPageUrl , "clickedPageUrl");
};



watch(
    () => selectedTopMenu.value,
    // eslint-disable-next-line no-unused-vars
    () => {
      init();
    },
);
// TODO.유정 확인
// init();
</script>

<template>
  <li v-if="props.menu.parent == null || props.menu.depth == 1" :class="{ ac: props.menu.pageUrl == clickedPageUrl }">
<!--    <router-link :to="props.menu.pageUrl" @click.prevent="clickedTopMenu(menu)">{{ props.menu.menuName }}</router-link>-->
    <router-link  class="top" to="" @click.prevent="clickedTopMenu(menu)">{{ props.menu.menuName }}</router-link>
<!--    <div>{{props.menu.menuName}}</div>-->
  </li>
</template>

<style scoped>

</style>