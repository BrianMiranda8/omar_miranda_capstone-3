package org.yearup.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.yearup.dtos.UpdateProductRequest;
import org.yearup.errors.ProductNotFound;
import org.yearup.models.CartItem;
import org.yearup.models.Product;
import org.yearup.models.ShoppingCart;
import org.yearup.models.ShoppingCartItem;
import org.yearup.repository.ShoppingCartRepository;

import java.util.HashMap;
import java.util.List;
import java.util.Optional;

@Service
public class ShoppingCartService
{
    // a shopping cart is built from cart rows plus a product lookup for each row
    private final ShoppingCartRepository shoppingCartRepository;
    private final ProductService productService;

    public ShoppingCartService(ShoppingCartRepository shoppingCartRepository, ProductService productService)
    {
        this.shoppingCartRepository = shoppingCartRepository;
        this.productService = productService;
    }

    public ShoppingCart getByUserId(int userId)
    {
        List<CartItem> cartItems = this.shoppingCartRepository.findByUserId(userId);
        ShoppingCart shoppingCart = new ShoppingCart();
        HashMap<Integer, ShoppingCartItem> shoppingCartItemMap = new HashMap<>();

        for (CartItem item : cartItems){

            // Get product from item product id
            Product product = this.productService.getById(item.getProductId());

            //Create shopping cart item
            ShoppingCartItem shoppingCartItem = new ShoppingCartItem();
            shoppingCartItem.setProduct(product);
            shoppingCartItem.setQuantity(item.getQuantity());

            shoppingCartItemMap.put(product.getProductId(), shoppingCartItem);

            shoppingCart.setItems(shoppingCartItemMap);
        }
        return shoppingCart;
    }


    public void  addCartItem(int userId, int productId){
        Optional<CartItem> cartItemOptional = this.shoppingCartRepository.findByUserIdAndProductId(userId,productId);

        if (cartItemOptional.isPresent()){
            CartItem cartItem = cartItemOptional.get();
            cartItem.setQuantity(cartItem.getQuantity() + 1);
            this.shoppingCartRepository.save(cartItem);

            return  ;
        }
        CartItem cartItem = new CartItem();
        cartItem.setProductId(productId);
        cartItem.setUserId(userId);
        this.shoppingCartRepository.save(cartItem);

    }

    public void updateItem(int userId, int productId, UpdateProductRequest updateProductRequest){
        this.shoppingCartRepository.findByUserIdAndProductId(userId, productId)
                .ifPresentOrElse((p) -> {
            p.setQuantity(updateProductRequest.getQuantity());
            this.shoppingCartRepository.save(p);
        }, ()-> { throw new ProductNotFound("product with id of: "+ productId + " not found");
        });

    }

    // Create a transaction for delete operation to ensure that all rows are removed if not revert
    @Transactional
    public void deleteCart(int userId){
        this.shoppingCartRepository.deleteByUserId(userId);
    }

    public void deleteByUserAndProductId(int userId, int id) {
        this.shoppingCartRepository.deleteByUserIdAndProductId(userId, id);
    }
}
