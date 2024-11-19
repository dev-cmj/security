<script setup>
const props = defineProps({
  params: Object,
});
const handleClick = () => {
  if (props.params.onClick) {
    props.params.onClick(props.params);
  }
};
</script>
<template>
  <div @click="handleClick" class="clickable-cell">
    {{ params.value }}
  </div>
</template>
<style>
.clickable-cell {
  cursor: pointer;
  text-decoration: none;
}

.clickable-cell:hover {
  text-decoration: underline;
}
</style>
