<script setup>
import { computed, defineModel } from "vue";

const props = defineProps({
  title: { type: String },
  placeholder: { type: String },
  id: { type: String },
  inputClass: { type: String },
  thClass: { type: String },
  readonly: { type: Boolean },
  inputWidth: { type: String },
  type: { type: String },
});
const value = defineModel({ default: "" });
const emit = defineEmits(["enter"]);
const onEnter = () => {
  emit("enter");
};
const inputWidth = computed(() => {
  return `width: ${props.inputWidth}; max-width: ${props.inputWidth};`;
});
</script>
<template>
  <th :class="props.thClass">{{ props.title }}</th>
  <td>
    <div>
      <input
        @keyup.enter="onEnter"
        v-model="value"
        :type="props.type"
        :id="props.id"
        :readonly="props.readonly"
        :class="props.inputClass"
        :placeholder="props.placeholder"
        :style="inputWidth"
      />
      <slot></slot>
    </div>
  </td>
</template>

<style></style>
