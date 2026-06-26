<style scoped>
.navbar {
  display: flex;
  align-items: center;
  padding: 10px;
  background-color: black;
  justify-content: space-between;
  border-radius: 10px;
  position: sticky;
  top: 10px;
  z-index: 10;
  margin: 10px 10px;

}

.login-container {
  display: flex;
  justify-content: center;
  align-items: center;
  height: 100%;
  width: 100%;
  background-color: #f5f5f5;
  top: 0;
  position: absolute;

}

.fade-enter-active,
.fade-leave-active {
  transition: opacity 0.3s ease;
}

.fade-enter-from,
.fade-leave-to {
  opacity: 0;
}

.header-img {
  cursor: pointer;
}

.navbar-left {
  display: flex;
  align-items: center;
  gap: 10px;
}

.cart-button {
  position: relative;

  .cart-total {
    position: absolute;
    top: -5px;
    right: -5px;
    background-color: red;
    color: white;
    border-radius: 50%;
    width: 20px;
    height: 20px;
    display: flex;
    justify-content: center;
    align-items: center;
    font-size: 12px;
  }
}

a {
  color: #64748b;
  text-decoration: none;
  transition: color 0.2s;
}

a:hover {
  color: #0284c7;
}

.router-link-exact-active {
  color: white;

  border-bottom: 1px solid white;
}
</style>
<template>
  <div class="navbar">
    <div class="navbar-left">

      <img @click="navigateTo('/')" class="header-img" style="width: 100px; " src="/images/logo.svg"
        alt="Discover Nuxt">
      <!-- <Button v-if="cookie" variant="ghost" color="white" @click="navigateTo('/profile')">Profile</Button> -->
      <NuxtLink to="/profile">Profile</NuxtLink>
      <NuxtLink to="/">Products</NuxtLink>
    </div>
    <div style="display: flex; gap: 10px;">
      <Button v-if="!cookie" variant="ghost" color="white" @click="toggleForm"> Sign In </Button>
      <div v-else>

        <span style="font-weight: bold;color: white;">
          Welcome {{ cookie.user.username }} !
        </span>
        <Button variant="ghost" color="white" @click="logout">
          Sign Out
        </Button>
      </div>

      <div v-if="cookie" class="cart-button">
        <span class="cart-total">{{ itemCount }}</span>
        <Button variant="ghost" color="white" @click="navigateTo('/cart')"> Cart </Button>
      </div>

    </div>
  </div>

  <Transition name="fade">
    <div v-if="showLoginForm" class="login-container">
      <Login @close="toggleForm()" />
    </div>
  </Transition>
</template>


<script setup lang="js">
const cookie = useCookie('user');
const showLoginForm = ref(false);

const { itemCount, refreshCartCount } = useCart()


refreshCartCount()

const toggleForm = () => {
  showLoginForm.value = !showLoginForm.value

  if (showLoginForm.value) {
    document.body.style.overflow = 'hidden'
  } else {
    document.body.style.overflow = ''
  }
}


const logout = () => {
  cookie.value = null
  navigateTo('/')
}



</script>