<script setup>
defineProps({
  btnText: { type: String, default: "목록으로" },
});
</script>

<template>
  <button class="btn-xs-df-line">
    <i class="ic-list"></i>
    {{ btnText }}
  </button>
</template>

<style scoped></style>
