<script setup>
import BaseInput from "@/components/common/base/BaseInput.vue";
import { defineModel } from "vue";

const props = defineProps({
  title: { type: String },
  placeholder: { type: String },
  id: { type: String },
  require: { type: Boolean },
  caption: { type: String },
  errorCaption: { type: Boolean },
  readonly: { type: Boolean },
  inputWidth: { type: String },
  type: { type: String },
});
const value = defineModel({ default: "" });
</script>
<template>
  <tr>
    <BaseInput
      :title="props.title"
      :placeholder="props.placeholder"
      :id="props.id"
      :type="props.type"
      :thClass="props.require ? 'req' : ''"
      :readonly="props.readonly"
      :input-width="props.inputWidth"
      v-model="value"
    >
      <span v-if="props.caption" class="caption" :class="props.errorCaption ? 'error' : ''" style="display: inline">{{
        props.caption
      }}</span>
    </BaseInput>
  </tr>
</template>

<style scoped></style>
