<template>
  <Transition name="toast" appear @after-leave="$emit('destroy')">
    <div v-if="isOpen" class="toast" role="alert">
      <span v-if="icon" class="toast-icon" aria-hidden="true">{{ icon }}</span>

      <p class="toast-message">{{ message }}</p>

      <button v-if="dismissible" @click="close" class="toast-close" aria-label="Close notification">
        &times;
      </button>
    </div>
  </Transition>
</template>

<script lang="ts" setup>
import { ref, computed, onMounted, onBeforeUnmount } from 'vue'

export interface ToastProps {
  message: string
  type?: 'standard' | 'success' | 'warning' | 'error' | 'info'
  duration?: number // in ms. Set to 0 for a persistent toast
  dismissible?: boolean
}

const props = withDefaults(defineProps<ToastProps>(), {
  type: 'standard',
  duration: 4000,
  dismissible: true
})

defineEmits<{
  (e: 'destroy'): void
}>()

const isOpen = ref(false)
let timer: ReturnType<typeof setTimeout> | null = null

const icons: Record<string, string> = {
  success: '✓',
  warning: '⚠',
  error: '✕',
  info: 'ℹ'
}

const icon = computed(() => icons[props.type] ?? '')
const typeColor = computed(() => `var(--toast-${props.type}, #334155)`)

const close = () => {
  isOpen.value = false
}

onMounted(() => {
  isOpen.value = true
  if (props.duration > 0) {
    timer = setTimeout(close, props.duration)
  }
})

onBeforeUnmount(() => {
  if (timer) clearTimeout(timer)
})
</script>

<style scoped>
.toast {
  display: flex;
  align-items: center;
  gap: 12px;
  min-width: 280px;
  max-width: 420px;
  padding: 14px 16px;
  border-radius: 8px;
  color: #ffffff;
  background-color: v-bind('typeColor');
  box-shadow: 0 10px 15px -3px rgba(0, 0, 0, 0.1), 0 4px 6px -4px rgba(0, 0, 0, 0.1);
  font-family: system-ui, -apple-system, sans-serif;
  font-size: 14px;
  pointer-events: auto;
  position: absolute;
  bottom: 50px;
  right: 50px;
}

.toast-icon {
  font-weight: 700;
  font-size: 16px;
}

.toast-message {
  margin: 0;
  flex-grow: 1;
  line-height: 1.4;
}

.toast-close {
  background: transparent;
  border: none;
  color: currentColor;
  opacity: 0.7;
  font-size: 18px;
  cursor: pointer;
  padding: 4px;
  display: flex;
  align-items: center;
}

.toast-close:hover {
  opacity: 1;
}

/* Vue <Transition> animation hooks */
.toast-enter-active,
.toast-leave-active {
  transition: all 0.25s cubic-bezier(0.16, 1, 0.3, 1);
}

.toast-enter-from {
  opacity: 0;
  transform: translateY(16px) scale(0.95);
}

.toast-leave-to {
  opacity: 0;
  transform: translateY(-16px) scale(0.95);
}
</style>