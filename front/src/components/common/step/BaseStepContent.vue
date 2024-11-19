<script setup>
defineProps({
  step: {
    type: Number,
    required: true,
  },
  currentStep: {
    type: Number,
    required: true,
  },
});
</script>

<template>
  <div v-show="step === currentStep">
    <slot></slot>
  </div>
</template>
