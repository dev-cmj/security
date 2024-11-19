<script setup>



const goHome = async () => {
  // const homeUrl = computed(() => store.getters.getDefaultHomeUrl);
  // await router.push(homeUrl.value);
};

</script>

<template>
  <a @click="goHome" class="logo-form">
    <img src="../../../assets/images/logo.png" class="logo-img" alt="ClovirVDI"/>
  </a>
</template>

<style scoped>
.logo-form{
  cursor: pointer;
}

.logo-img{
  margin-top: 4px; 
  width: 170px;
}

</style>