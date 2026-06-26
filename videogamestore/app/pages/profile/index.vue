<template>
  <div v-if="profileData" class="profile-container">

    <Input id="firstName" label="First Name" size="md" type="text" v-model="profileData.firstName" />

    <Input id="lastName" label="Last Name" size="md" type="text" v-model="profileData.lastName" />
    <Input id="lastName" label="Email" size="md" type="text" v-model="profileData.email" />
    <Input id="lastName" label="Address" size="md" type="text" v-model="profileData.address" />
    <Input id="lastName" label="City" size="md" type="text" v-model="profileData.city" />
    <Input id="lastName" label="State" size="md" type="text" v-model="profileData.state" />
    <Input id="lastName" label="Zip" size="md" type="text" v-model="profileData.zip" />

    <Button size="medium" variant="outline" @click="updateProfileData">
      Update
    </Button>

  </div>
  <div v-else>
    Loading profile...
  </div>
</template>

<script lang="js" setup>
definePageMeta({
  middleware: 'auth'
})


const cookie = useCookie('user')


const { data: profileData } = await useFetch("http://localhost:8080/profile", {
  method: "GET",
  headers: {
    'Content-Type': 'application/json',
    'Authorization': `Bearer ${cookie.value?.token || ''}`
  }
})

const updateProfileData = () => {
  $fetch("http://localhost:8080/profile", {
    method: "PUT",
    body: profileData.value,

    headers: {
      'Content-Type': 'application/json',
      'Authorization': `Bearer ${cookie.value?.token || ''}`
    }
  })
}
</script>

<style scoped>
.profile-container {
  border-radius: 10px;
  margin: auto;
  width: 35%;
  padding: 20px;
  display: flex;
  flex-direction: column;
  gap: 15px;
  background-color: #f5f5f5;

}
</style>