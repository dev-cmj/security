<script setup>
const props = defineProps({
  btnText: { type: String, default: "등록" },
  btnSize: { type: String, default: "xs" },
  btnColor: { type: String, default: "navy" },
});

const btnColorArray = [
  { key: "navy", className: `btn-${props.btnSize}-main` },
  { key: "gray", className: `btn-${props.btnSize}-df` },
  { key: "blue", className: `btn-${props.btnSize}-main-line` },
  { key: "white", className: `btn-${props.btnSize}-df-line` },
];

const selectedClass = btnColorArray.find(item => item.key === props.btnColor)?.className || "";
</script>

<template>
  <button :class="selectedClass">{{ btnText }}</button>
</template>

<style scoped></style>
