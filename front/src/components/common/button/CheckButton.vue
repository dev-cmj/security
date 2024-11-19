<script setup>
defineProps({
  btnText: { type: String, default: "수정" },
});
</script>

<!--
 Note.
 '수정' 으로 기본 으로 하되,
 다른 용어로 버튼을 사용 하고 싶은 경우 btnText 를 통해 변경
 -->

<template>
  <button class="ok">{{ btnText }}</button>
</template>

<style scoped></style>
