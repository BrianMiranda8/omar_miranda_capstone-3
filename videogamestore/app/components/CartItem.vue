<template>
  <div :key="item.productId" class="cart-item">

    <img :src="`/images/products/${item.product.imageUrl}`" alt="Product Image" class="cart-item-image" />

    <h3 class="cart-item-name">{{ item.product.name }}</h3>

    <p class="cart-item-quantity">
      Quantity: <input v-model="localQuantity" />
    </p>
    <Button @click="removeItem" size="small" variant="outline" color="danger">
      Remove
    </Button>
    <p class="cart-item-price">
      Price: <span>${{ item.product.price }}</span>
    </p>

  </div>
</template>

<script lang="js" setup>
const props = defineProps(['item'])
const cookie = useCookie('user');
const emit = defineEmits(['refresh'])
const localQuantity = ref(props.item.quantity)
watch(
  localQuantity,
  (newQuantity) => {
    if (newQuantity == "") return;
    $fetch('http://localhost:8080/cart/products/' + props.item.product.productId, {
      method: 'PUT',
      body: {
        quantity: newQuantity
      },
      headers: {
        'Content-Type': 'application/json',
        'Authorization': `Bearer ${cookie.value.token}`
      }
    }).then(() => {
      emit('refresh')
    })
  }
)
const removeItem = () => {
  $fetch('http://localhost:8080/cart/' + props.item.product.productId, {
    method: 'DELETE',
    headers: {
      'Content-Type': 'application/json',
      'Authorization': `Bearer ${cookie.value.token}`
    }
  }).then(() => {
    emit('refresh')
  })
}
</script>

<style scoped>
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