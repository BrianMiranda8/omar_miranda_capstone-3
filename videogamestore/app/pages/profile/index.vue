<template>
  <div>


  </div>
</template>

<script lang="ts" setup>
definePageMeta({
  middleware: 'auth'
})

const cookie = useCookie('user')
const { data } = await useFetch("http://localhost:8080/profile", {
  method: "GET",
  headers: {
    'Content-Type': 'application/json',
    'Authorization': `Bearer ${cookie.value.token}`
  }
})

console.log(data)
</script>

<style></style>