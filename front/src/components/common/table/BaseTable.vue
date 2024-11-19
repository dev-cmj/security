<script setup></script>

<template>
  <section class="gridWrap">
    <template v-if="$slots.top">
      <div class="top">
        <slot name="top"></slot>
      </div>
    </template>
    <slot name="grid"></slot>
  </section>
</template>

<style scoped></style>
