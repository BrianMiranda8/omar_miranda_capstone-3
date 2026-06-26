<script setup lang="js">
import { ref } from 'vue';

const selectedCategory = ref();
const selectedMaxPrice = ref();
const selectedMinPrice = ref();
const selectedSubCategory = ref();

const { data: categories } = await useFetch('http://localhost:8080/categories');
const { data: genres } = await useFetch('http://localhost:8080/products/subCategories');
const queryParams = computed(() => {
  const params = {};

  if (selectedCategory.value) params.cat = selectedCategory.value;
  if (selectedMaxPrice.value) params.maxPrice = selectedMaxPrice.value;
  if (selectedMinPrice.value) params.minPrice = selectedMinPrice.value;
  if (selectedSubCategory.value) params.subCategory = selectedSubCategory.value;

  return params;
});

const { data: products, pending } = await useFetch('http://localhost:8080/products', {
  query: queryParams
});


</script>

<style lang="css" scoped>
@media (max-width: 650px) {
  .product-list {
    align-items: center;
  }
}

/* Keeping your existing styles */
.product-list {
  display: flex;
  flex-wrap: wrap;
  justify-content: center;
  background-color: #f5f5f5;
  border-radius: 10px;
}

.product-list-container {
  padding: 20px;
  display: flex;
  flex-direction: column;
  gap: 10px;
}

.search-filters {
  flex-wrap: wrap;
  display: flex;
  width: 100%;
  justify-content: space-between;
  margin-bottom: 20px;
  background-color: #f5f5f5;

  border-radius: 10px;
  padding: 10px;
  max-height: 300px;
  gap: 10px;
  top: 75px;

  .category-title {
    font-size: 1.5em;
    font-weight: bold;
    margin: 0px;
    padding: 5px 10px
  }

  .category-buttons {
    display: flex;
    gap: 10px;
  }

  .sub-category-buttons {

    max-width: 400px;
    display: flex;
    gap: 10px;
    overflow-x: auto;
  }

  .min-max {
    display: flex;
    flex-direction: column;
    gap: 10px;
    justify-content: space-between;
  }

  .min-max-inputs {
    display: flex;
    gap: 10px;
  }
}

.is-loading {
  opacity: 0.4;
  pointer-events: none;
  transition: opacity 0.2s ease-in-out;
}

.product-not-found {
  text-align: center;
}
</style>

<template>


  <div class="product-list-container">

    <div class="search-filters">

      <div class="search-categories">

        <h1 class="category-title">Categories</h1>

        <div class="category-buttons">

          <Button size="small" :variant="selectedCategory === '' ? 'solid' : 'outline'"
            @click="selectedCategory = null">
            All
          </Button>
          <Button size="small" :variant="selectedCategory === category.categoryId ? 'solid' : 'outline'"
            v-for="category in categories" :key="category.categoryId" :value="category.categoryId"
            @click="selectedCategory = category.categoryId">
            {{ category.name }}
          </Button>
        </div>

      </div>

      <div class="seach-categories">

        <h1 class="category-title">Price Range</h1>

        <div class="min-max-inputs">
          <Input id="minPrice" label="Min Price" size="sm" v-model="selectedMinPrice" type="number" />
          <Input id="maxPrice" label="Max Price" size="sm" v-model="selectedMaxPrice" type="number" />
        </div>

      </div>

      <div class="search-categories">

        <h1 class="category-title">Genres</h1>

        <div class="sub-category-buttons">

          <Button size="small" :variant="selectedSubCategory === '' ? 'solid' : 'outline'"
            @click="selectedSubCategory = ''">
            All
          </Button>
          <Button size="small" :variant="selectedSubCategory === genre ? 'solid' : 'outline'" v-for="genre in genres"
            :key="genre" :value="genre" @click="selectedSubCategory = genre">
            {{ genre }}
          </Button>
        </div>
      </div>
    </div>

    <div>

      <div v-if="products.length === 0" class="product-not-found">
        No Products Found
      </div>
      <div v-else class="product-list" :class="{ 'is-loading': pending }">
        <ProductCard v-for="product in products" :key="product.productId" :product="product" />
      </div>
    </div>

  </div>
</template>