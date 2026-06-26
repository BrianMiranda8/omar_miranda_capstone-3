<template>
  <div :key="product.productId" class="product-card">
    <div class="card-image-container">

      <img class="card-img" :src="'/images/products/' + product.imageUrl" alt="Product Image" style="height:100px;" />
    </div>
    <h2 class="card-title">{{ product.name }}</h2>
    <p class="card-description">{{ product.description }}</p>
    <p class="card-price">Price: ${{ product.price }}</p>
    <Button variant="outline" @click="addToCart">
      Add To Cart
    </Button>
  </div>



</template>

<script lang="js" setup>
const user = useCookie('user');
const { refreshCartCount } = useCart()
const props = defineProps({
  product: {
    type: Object,
    required: true
  }
})


const addToCart = async () => {

  const data = await $fetch('http://localhost:8080/cart/products/' + props.product.productId, { method: 'POST', headers: { 'Authorization': `Bearer ${user.value.token}` } });
  console.log('Product added to cart:', data);

  refreshCartCount()
};


</script>

<style scoped>
.product-card {
  border: 1px solid #ccc;
  padding: 10px;
  margin: 10px;
  text-align: center;
  border-radius: 10px;
  flex-basis: 200px;
  background-color: #ccc;
  box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
  transition: all 0.3s ease;
  max-height: 350px;

  .card-title {
    font-size: 1.2em;
    font-weight: bold;
    margin-bottom: 5px;
    max-height: 40px;
    overflow: hidden;
  }

  .card-image-container {
    display: flex;
    justify-content: center;
    align-items: center;
    max-height: 150px;
    margin-bottom: 10px;
  }

  .card-description {
    font-size: 0.9em;
    color: #666;
    margin-bottom: 10px;
    height: 40px;
    overflow: hidden;
    text-overflow: ellipsis;
    max-height: 50px;
  }

  .card-price {
    font-size: 1em;
    font-weight: bold;
    margin-top: 10px;
    height: 20px;
  }
}

.product-card:hover {
  cursor: pointer;
  transform: scale(1.05);

}
</style>