export const useCart = () => {
  const user = useCookie('user');
  const itemCount = useState('cart-item-count', () => 0);
  const refreshCartCount = async () => {
    const data = await $fetch('http://localhost:8080/cart',
      { method: 'GET', headers: { 'Authorization': `Bearer ${user.value.token}` } });

    const total = Object.keys(data.items).length;
    itemCount.value = total;
  }
  return {
    itemCount,
    refreshCartCount
  }
}
