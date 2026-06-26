<template>
  <Header />

  <main>

    <NuxtPage />
  </main>
</template>

<script lang="ts" setup>
</script>

<style>
:root {
  --toast-standard: #475569;
  --toast-info: #0284c7;
  --toast-success: #16a34a;
  --toast-warning: #d97706;
  --toast-error: #dc2626;
}

body {
  margin: 0;
  padding: 0;
  font-family: Arial, sans-serif;
}
</style>