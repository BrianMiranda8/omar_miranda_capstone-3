<script setup lang="ts">
const model = defineModel<string | number>()

// 1. Add the size prop
withDefaults(defineProps<{
  id: string
  label: string
  type?: string
  size?: 'sm' | 'md' | 'large'
}>(), {
  type: 'text',
  size: 'md' // Default to medium
})
</script>

<template>
  <div :class="['floating-input-group', size]">
    <input :type="type" :id="id" v-model="model" class="floating-input" placeholder=" " />
    <label :for="id" class="floating-label">{{ label }}</label>
  </div>
</template>

<style scoped>
.floating-input-group {
  position: relative;
  width: 100%;

  margin-bottom: 1rem;
}

/* --- BASE INPUT & LABEL STYLES --- */
.floating-input {
  width: 100%;
  border: 1px solid #d1d5db;
  border-radius: 6px;
  background-color: #ffffff;
  outline: none;
  transition: border-color 0.2s ease;
  box-sizing: border-box;
}

.floating-input:focus {
  border-color: #3b82f6;
}

.floating-label {
  position: absolute;
  top: 50%;
  transform: translateY(-50%);
  color: #6b7280;
  pointer-events: none;
  transition: all 0.2s ease-out;
}

/* Color adjustments for focused/filled states */
.floating-input:focus~.floating-label,
.floating-input:not(:placeholder-shown)~.floating-label {
  font-weight: 500;
  color: #3b82f6;
}

.floating-input:not(:focus):not(:placeholder-shown)~.floating-label {
  color: #6b7280;
}


/* --- SIZE VARIANTS --- */

/* Medium (Default) */
.floating-input-group.md .floating-input {
  padding: 22px 12px 6px;
  font-size: 16px;
}

.floating-input-group.md .floating-label {
  left: 12px;
  font-size: 16px;
}

.floating-input-group.md .floating-input:focus~.floating-label,
.floating-input-group.md .floating-input:not(:placeholder-shown)~.floating-label {
  top: 12px;
  font-size: 12px;
}

/* Small (sm) */
.floating-input-group.sm .floating-input {
  padding: 18px 10px 4px;
  font-size: 14px;
}

.floating-input-group.sm .floating-label {
  left: 10px;
  font-size: 14px;
}

.floating-input-group.sm .floating-input:focus~.floating-label,
.floating-input-group.sm .floating-input:not(:placeholder-shown)~.floating-label {
  top: 10px;
  font-size: 10px;
}

/* Large (large) */
.floating-input-group.large .floating-input {
  padding: 26px 16px 8px;
  font-size: 18px;
}

.floating-input-group.large .floating-label {
  left: 16px;
  font-size: 18px;
}

.floating-input-group.large .floating-input:focus~.floating-label,
.floating-input-group.large .floating-input:not(:placeholder-shown)~.floating-label {
  top: 14px;
  font-size: 13px;
}
</style>