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
</style>
<template>
  <div class="navbar">
    <div class="navbar-left">

      <img @click="navigateTo('/')" class="header-img" style="width: 100px; " src="/images/logo.svg"
        alt="Discover Nuxt">
      <Button v-if="cookie" variant="ghost" color="white" @click="navigateTo('/profile')">Profile</Button>
    </div>
    <div style="display: flex; gap: 10px;">
      <Button v-if="!cookie" variant="ghost" color="white" @click="toggleForm"> Sign In </Button>

      <Button v-else variant="ghost" color="white" @click="logout">
        <span style="font-weight: bold;">
          {{ cookie.user.username }}
        </span>
        Sign Out
      </Button>
      <div v-if="cookie" class="cart-button">
        <span class="cart-total">{{ cartTotal }}</span>
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
const cartTotal = ref(0);
const showLoginForm = ref(false);
const cartTrigger = useUseCartTrigger();

watch(cartTrigger, () => {
  getCartTotal();
});


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

const getCartTotal = async () => {
  const user = useCookie('user');
  const data = await $fetch('http://localhost:8080/cart',
    { method: 'GET', headers: { 'Authorization': `Bearer ${user.value.token}` } });

  const total = Object.keys(data.items).length;
  cartTotal.value = total;
}
getCartTotal();
</script>