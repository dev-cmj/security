<script setup>
import { defineModel } from "vue";

const props = defineProps({
  readonly: { type: Boolean },
  title: { type: String },
});
const value = defineModel({ default: "" });
</script>

<template>
  <tr>
    <th>{{ props.title }}</th>
    <td>
      <textarea v-model="value" :readonly="props.readonly"></textarea>
    </td>
  </tr>
</template>

<style scoped></style>
