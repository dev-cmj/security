<script setup>
import { ref } from "vue";

const props = defineProps({
  title: String,
  id: String,
  modalClass: String,
});
const emit = defineEmits(["onHide", "onShow"]);

const hide = ref(false);

const onHide = () => {
  emit("onHide");
};
const onShow = event => {
  emit("onShow", event);
};
</script>

<template>
  <b-modal
    :id="id"
    v-model="hide"
    :title="title"
    class="clovir-modal"
    :class="props.modalClass"
    @hide="onHide"
    @show="onShow"
  >
    <slot name="top"></slot>
    <div class="tableWrap">
      <table>
        <tbody>
          <slot name="content"></slot>
        </tbody>
      </table>
    </div>
    <template #footer>
      <slot name="footer"></slot>
    </template>
  </b-modal>
</template>
