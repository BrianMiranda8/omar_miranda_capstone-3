<template>
  <div class="cart-container">
    <div v-for="item in cart.items" :key="item.productId" class="cart-item">

      <img :src="`/images/products/${item.product.imageUrl}`" alt="Product Image" class="cart-item-image" />

      <h3 class="cart-item-name">{{ item.product.name }}</h3>

      <p class="cart-item-quantity">Quantity: {{ item.quantity }}</p>

      <p class="cart-item-price">
        Price: <span>${{ item.product.price }}</span>
      </p>

    </div>
    <div class="total-price">
      <div class="total">

        <div>
          Total View
        </div>
        <div class="price">
          {{ Intl.NumberFormat("US-en", { style: "currency", currency: "USD" }).format(cart.total) }}
        </div>
      </div>
      <div class="checkout-button">
        <Button @click="checkout">
          Checkout
        </Button>
      </div>
    </div>
  </div>
</template>

<script lang="ts" setup>
const cookie = useCookie('user');
const router = useRouter()
const { data: cart } = await useFetch('http://localhost:8080/cart', {
  method: 'GET',
  headers: {
    'Content-Type': 'application/json',
    'Authorization': `Bearer ${cookie.value.token}`
  }
})
const checkout = () => {
  useFetch('http://localhost:8080/orders', {
    method: 'POST',
    headers: {
      'Content-Type': 'application/json',
      'Authorization': `Bearer ${cookie.value.token}`
    }
  }).then(data => { router.replace('/') })
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
    justify-content: end;
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

.cart-item {
  display: grid;
  min-height: 100px;

  grid-template-columns: 150px 1fr auto;

  grid-template-areas:
    "image name     price"
    "image quantity .";

  column-gap: 20px;
  row-gap: 5px;
  padding: 15px;
  border-bottom: 1px solid #ccc;
  align-items: center;
}

.cart-item-image {
  grid-area: image;
  width: 100%;
  object-fit: cover;
}

.cart-item-name {
  grid-area: name;
  margin: 0;
  align-self: end;
}

.cart-item-quantity {
  grid-area: quantity;
  margin: 0;
  color: #666;
  align-self: start;
}

.cart-item-price {
  grid-area: price;
  margin: 0;
  align-self: end;
}

.cart-item-price span {
  font-weight: bold;
}
</style>