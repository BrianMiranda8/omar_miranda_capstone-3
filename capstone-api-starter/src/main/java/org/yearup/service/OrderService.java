package org.yearup.service;

import org.springframework.stereotype.Service;
import org.yearup.errors.EmptyCart;
import org.yearup.errors.ProductNotFound;
import org.yearup.errors.UserNotFound;
import org.yearup.models.*;
import org.yearup.repository.*;

import java.time.LocalDate;
import java.util.List;

@Service
public class OrderService {

    ShoppingCartRepository shoppingCartRepository;
    OrderRepository orderRepository;
    ProductRepository productRepository;
    OrderItemRepository orderItemRepository;
    ProfileRepository profileRepository;
    public OrderService(ShoppingCartRepository shoppingCartRepository, OrderRepository orderRepository, ProductRepository productRepository, OrderItemRepository orderItemRepository,ProfileRepository profileRepository) {
        this.shoppingCartRepository = shoppingCartRepository;
        this.orderRepository = orderRepository;
        this.productRepository = productRepository;
        this.orderItemRepository = orderItemRepository;
        this.profileRepository = profileRepository;
    }

    public void checkout(int userId) {

        List<CartItem> shoppingCart = this.shoppingCartRepository.findByUserId(userId);
        Profile profile = this.profileRepository.findById(userId).orElseThrow(()-> new UserNotFound("User id: "+ userId + " not found"));
        if (shoppingCart.isEmpty()){
            throw new EmptyCart("Cart has no items");
        }
        Order preSaveOrder = new Order();

        preSaveOrder.setAddress(profile.getAddress());
        preSaveOrder.setCity(profile.getCity());
        preSaveOrder.setDate(LocalDate.now());
        preSaveOrder.setUserId(profile.getUserId());
        preSaveOrder.setZip(profile.getZip());
        preSaveOrder.setState(profile.getState());

        Order order = this.orderRepository.save(preSaveOrder);

        for (CartItem cartItem : shoppingCart){
            Product product = this.productRepository.findById(cartItem.getProductId()).orElseThrow(()-> new ProductNotFound("Product not found with id: "+cartItem.getProductId()));
            OrderLineItem orderLineItem = new OrderLineItem();
            orderLineItem.setOrderId(order.getOrderId());
            orderLineItem.setProductId(cartItem.getProductId());
            orderLineItem.setQuantity(cartItem.getQuantity());
            orderLineItem.setSalesPrice(product.getPrice());
            this.orderItemRepository.save(orderLineItem);
            this.shoppingCartRepository.deleteById(cartItem.getCartItemId());
        }


    }
}
