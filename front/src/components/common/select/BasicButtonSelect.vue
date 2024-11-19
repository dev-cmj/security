<script setup>
import { defineModel, onMounted, ref, watch } from "vue";

const props = defineProps({
  id: String,
  class: String,
  items: Array,
  _key: String,
  _value: String,
  width: String,
  minWidth: String,
  height: String,
  border: String,
  onHide: Function,
  divStyle: String,
  title: String,
});
const emit = defineEmits(["change"]);
const selectedItem = ref({ id: "", value: "" });

let selectedKey = defineModel({ default: null });

const handleSelect = item => {
  selectedKey.value = item[props._key];
  emit("change", item);
};

const setSize = () => {
  let styles = "";
  if (props.width) {
    styles += `width: ${props.width}; `;
  }
  if (props.minWidth) {
    styles += `min-width: ${props.minWidth}; `;
  }
  if (props.height) {
    styles += `height: ${props.height};`;
  }
  if (props.border) {
    styles += `border: ${props.border};`;
  }
  return styles;
};

onMounted(() => {
  if (!selectedItem.value) {
    selectedItem.value = props.title || "Select an item";
  }
});

watch(
  () => props.title,
  newTitle => {
    if (newTitle) {
      selectedItem.value = newTitle;
    }
  },
);
</script>

<template>
  <div class="dropdown" :style="props.divStyle">
    <b-dropdown
      :id="props.id"
      :text="title ? title : selectedItem.value"
      :class="props.class"
      :style="setSize()"
      @hide="props.onHide"
    >
      <slot name="dropdown-search"></slot>
      <b-dropdown-item
        v-for="item in props.items"
        :key="item[props._key]"
        :value="item[props._value]"
        style="margin-top: 4px"
        @click="handleSelect(item)"
      >
        {{ item[props._value] }}
      </b-dropdown-item>
    </b-dropdown>
  </div>
</template>

<style scoped></style>
