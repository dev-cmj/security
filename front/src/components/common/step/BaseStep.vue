<script setup>
// eslint-disable-next-line no-unused-vars
const props = defineProps({
  steps: {
    type: Array,
    required: true,
  },
  currentStep: {
    type: Number,
    required: true,
  },
  setCurrentStep: {
    type: Function,
    required: true,
  },
});
</script>

<template>
  <div class="step">
    <div
      class="item"
      v-for="step in steps"
      :key="step.id"
      :class="{ focus: step.id === currentStep, complete: step.id < currentStep }"
    >
      <span class="numbering">{{ step.id }}</span>
      <span class="text">{{ step.label }}</span>
    </div>
  </div>
</template>

<style scoped></style>
