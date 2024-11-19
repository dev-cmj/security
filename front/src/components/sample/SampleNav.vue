<script setup></script>

<template>
  <div class="sample-nav top">
    <router-link to="/"> 샘플 목록 </router-link>
  </div>
  <div class="sample-nav list-item">
    <router-link to="/radio"> radio 샘플</router-link>
  </div>
  <div class="sample-nav list-item">
    <router-link to="/button"> button 샘플</router-link>
  </div>
  <div class="sample-nav list-item">
    <router-link to="/select"> select 샘플</router-link>
  </div>
  <div class="sample-nav list-item">
    <router-link to="/tree"> tree 샘플</router-link>
  </div>
  <div class="sample-nav list-item">
    <router-link to="/modal"> modal 샘플</router-link>
  </div>
  <div class="sample-nav list-item">
    <router-link to="/alert"> alert 샘플</router-link>
  </div>
  <div class="sample-nav list-item">
    <router-link to="/loading"> loading 샘플</router-link>
  </div>
</template>

<style scoped>
.sample-nav {
  border: 2px solid lightgray;
  width: 80px;
  padding: 20px;
  margin-left: 20px;
  text-align: center;
}
.top {
  margin-top: 20px;
}
.list-item {
  border-top: none;
}
</style>
