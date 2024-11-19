<script setup>
import SaveAlert from "@/components/common/alert/SaveAlert.vue";
import WarningAlert from "@/components/common/alert/WarningAlert.vue";
import DeleteAlert from "@/components/common/alert/DeleteAlert.vue";
import Loading from "@/components/common/loading/Loading.vue";
</script>

<template>
  <div class="vdiContent">
    <router-view></router-view>
  </div>
  <SaveAlert></SaveAlert>
  <DeleteAlert></DeleteAlert>
  <WarningAlert></WarningAlert>
  <Loading></Loading>
</template>

<style scoped></style>
