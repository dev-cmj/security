<script setup>
const props = defineProps({
  params: Object,
});
</script>
<template>
  <div class="cell-tooltip">
    {{ params.value }}
  </div>
</template>
<style>
.cell-tooltip {
  max-width: 250px;
  padding: 8px;
  background-color: #f0f2f5;
  color: #333;
  border-radius: 4px;
  box-shadow: 0 2px 5px rgba(0, 0, 0, 0.15);
  font-size: 14px;
  line-height: 1.4;
  white-space: pre-wrap;
  word-wrap: break-word;
  border: 1px solid #dcdcdc;
}
</style>
