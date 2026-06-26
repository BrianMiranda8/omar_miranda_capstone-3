<template>
  <div class="cart-container">

    <CartItem @refresh="() => { refreshCart(); refreshCartCount() }" :item="item" v-for="item in cart.items"
      :key="item.productId" />

    <div class="total-price">
      <div class="total">

        <div>
          Total Amount
        </div>
        <div class="price">
          {{ Intl.NumberFormat("US-en", { style: "currency", currency: "USD" }).format(cart.total) }}
        </div>
      </div>
      <div class="checkout-button">
        <Button :disabled="itemCount == 0" color="danger" @click="checkToClearCart">
          Clear
        </Button>
        <Button :disabled="itemCount == 0" @click="checkout">
          Checkout
        </Button>
      </div>
    </div>
  </div>
  <Toast v-if="showToast" @destroy="showToast = false" type="success" :message="showMessage" />
</template>

<script lang="ts" setup>
const cookie = useCookie('user');
const router = useRouter()
const showToast = ref(false)
const showMessage = ref("")
const { refreshCartCount, itemCount } = useCart()
const { data: cart, refresh: refreshCart } = await useFetch('http://localhost:8080/cart', {
  method: 'GET',
  headers: {
    'Content-Type': 'application/json',
    'Authorization': `Bearer ${cookie.value.token}`
  }
})



const checkout = () => {
  $fetch('http://localhost:8080/orders', {
    method: 'POST',
    headers: {
      'Content-Type': 'application/json',
      'Authorization': `Bearer ${cookie.value.token}`
    }
  }).then(data => {
    refreshCartCount()
    showMessage.value = "Cart has been checked out!"
    showToast.value = true
    refreshCart()
    console.log(data)
  })
}

const clearCart = () => {
  if (itemCount.value == 0) return;
  $fetch('http://localhost:8080/cart', {
    method: 'DELETE',
    headers: {
      'Content-Type': 'application/json',
      'Authorization': `Bearer ${cookie.value.token}`
    }
  })
    .then(data => {
      showToast.value = true;
      showMessage.value = "Cart Has Been Cleared!"
      refreshCartCount()
      refreshCart();
    }).catch((err) => {
      // TODO: catch errors
    })
}

const checkToClearCart = () => {
  if (window.confirm("Are you sure you want to clear your cart ? ")) {
    clearCart()
  }
}
</script>

<style scoped>
.total-price {
  display: flex;
  flex-direction: column;
  width: 100%;
  justify-content: space-between;

  .total {
    display: flex;
    justify-content: space-between;
  }

  div {
    font-weight: bold;
    padding: 10px;
  }

  .checkout-button {
    display: flex;
    justify-content: space-between;
  }

}

.cart-container {
  display: flex;
  flex-direction: column;
  background-color: #f5f5f5;
  border-radius: 10px;
  height: 100%;
  width: 50%;
  margin: 10px auto;

}
</style>