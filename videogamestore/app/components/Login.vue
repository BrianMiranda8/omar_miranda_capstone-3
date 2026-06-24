<script setup lang="js">


defineEmits(['close'])

const username = ref('')
const password = ref('')
const errorMessage = ref('')
const userCookie = useCookie('user')

const handleLogin = async () => {
  try {
    // Reset error message on new attempt
    errorMessage.value = ''


    const response = await $fetch('http://localhost:8080/login', {
      method: 'POST',
      body: {
        username: username.value,
        password: password.value
      }
    })


    userCookie.value = response

    emit('close')

  } catch (error) {
    errorMessage.value = 'Invalid username or password.'
  }
}
</script>


<style scoped>
.login-container {
  display: flex;
  justify-content: center;
  align-items: center;
  height: 100%;
  width: 100%;
  position: absolute;
  top: 0;
  background-color: #f5f5f5;
  z-index: 10;
}

.login-form {
  background-color: white;
  padding: 20px;
  border-radius: 10px;
  box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
  width: 300px;

  h2 {
    text-align: center;
    margin-bottom: 20px;
  }

  .form-group {
    margin-bottom: 15px;

    label {
      display: block;
      margin-bottom: 5px;
    }

    input {
      width: 100%;
      padding: 8px;
      border: 1px solid #ccc;
      border-radius: 5px;
    }
  }

  .form-buttons {
    display: flex;
    justify-content: space-around;
    margin-top: 20px;
  }

  .error-message {
    color: red;
    text-align: center;
    margin-top: 10px;
    text-align: center;
    width: 100%;
  }
}
</style>

<template>
  <div class="login-container">
    <div class="login-form">
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
      </form>
      <p v-if="errorMessage" class="error-message">{{ errorMessage }}</p>
    </div>
  </div>
</template>
