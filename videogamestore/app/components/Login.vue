<template>
  <div class="login-container">
    <div class="login-form">

      <Transition name="form-fade" mode="out-in">

        <div v-if="!showRegister" key="login-panel" class="panel-wrapper">
          <h2>Login</h2>
          <form @submit.prevent="handleLogin">
            <div class="form-group">
              <label for="username">Username:</label>
              <input type="text" name="username" v-model="username" required />
            </div>
            <div class="form-group">
              <label for="password">Password:</label>
              <input type="password" name="password" v-model="password" required />
            </div>
            <div class="form-buttons">
              <Button type="submit">Login</Button>
              <Button variant="solid" color="danger" @click.prevent="$emit('close')">Close</Button>
            </div>
            <div class="toggle-box">
              <Button @click.prevent="toggleRegister" variant="outline">Create an Account</Button>
            </div>
          </form>
        </div>

        <div v-else key="register-panel" class="panel-wrapper">
          <h2>Register</h2>
          <form @submit.prevent="handleRegister">
            <div class="form-group-custom">
              <Input v-model="register.username" label="Username" type="text" id="reg-user" />
              <Input v-model="register.password" label="Password" type="password" id="reg-pass" />
              <Input v-model="register.confirmPassword" label="Re-type Password" type="password" id="reg-pass2" />
            </div>
            <div class="form-buttons">
              <Button type="submit" @click.prevent="handleRegister">Register</Button>
              <Button variant="outline" @click.prevent="toggleRegister">Back to Login</Button>
            </div>
          </form>
        </div>

      </Transition>

      <p v-if="errorMessage" class="error-message">{{ errorMessage }}</p>
    </div>
  </div>
</template>

<script lang="js" setup>
import { ref } from 'vue'

defineEmits(['close'])

const register = ref({
  username: "",
  password: "",
  confirmPassword: "",
  role: "USER"
})
const showRegister = ref(false)

const username = ref('')
const password = ref('')
const errorMessage = ref('')
const userCookie = useCookie('user')

const handleLogin = async () => {
  try {
    errorMessage.value = ''
    const response = await $fetch('http://localhost:8080/login', {
      method: 'POST',
      body: {
        username: username.value,
        password: password.value
      }
    })

    userCookie.value = response

  } catch (error) {
    console.log(error)
    errorMessage.value = 'Invalid username or password.'
  }
}

const handleRegister = async () => {
  if (register.value.username == "" || register.value.password == "" || register.value.password2 == "") {
    return;
  }
  const registerUser = await useFetch('http://localhost:8080/register', {
    method: "POST",
    body: register
  })

  console.log(registerUser)
}

const toggleRegister = () => {
  showRegister.value = !showRegister.value
}
</script>

<style scoped>
.login-container {
  display: flex;
  justify-content: center;
  align-items: center;
  height: 100vh;
  width: 100vw;
  position: fixed;
  top: 0;
  left: 0;
  background-color: rgba(245, 245, 245, 0.9);
  z-index: 10;
}

.login-form {
  background-color: white;
  padding: 30px;
  border-radius: 10px;
  box-shadow: 0 4px 12px rgba(0, 0, 0, 0.15);
  width: 340px;
  overflow: hidden;
}

.panel-wrapper {
  width: 100%;
}

h2 {
  text-align: center;
  margin-bottom: 20px;
}

form {
  display: flex;
  flex-direction: column;
  gap: 12px;
}

.form-group {
  width: 100%;

  label {
    display: block;
    margin-bottom: 5px;
    font-size: 0.9rem;
  }

  input {
    width: 100%;
    padding: 10px;
    border: 1px solid #ccc;
    border-radius: 5px;
    box-sizing: border-box;
  }
}

.form-group-custom {
  display: flex;
  flex-direction: column;
  gap: 15px;
  width: 100%;
}

.form-buttons {
  display: flex;
  justify-content: space-between;
  margin-top: 15px;
  gap: 15px;
  width: 100%;
}

.toggle-box {
  margin-top: 10px;
  display: flex;
  justify-content: center;
  width: 100%;
}

.error-message {
  color: red;
  text-align: center;
  margin-top: 15px;
  font-size: 0.9rem;
}


.form-fade-enter-active {
  transition: all 0.3s ease-out;
}

.form-fade-leave-active {
  transition: all 0.25s ease-in;
}

.form-fade-enter-from {
  opacity: 0;
  transform: translateY(-30px);
}

.form-fade-leave-to {
  opacity: 0;
  transform: translateY(30px);
}
</style>