<script setup>
defineProps({
  btnText: { type: String, default: "등록" },
});
</script>

<!--
 Note.
 '등록' 으로 기본 으로 하되,
 '생성' 등 다른 용어로 버튼을 사용 하고 싶은 경우
 btnText 를 통해 변경
 -->

<template>
  <button class="new">{{ btnText }}</button>
</template>

<style scoped></style>
