package org.yearup.controllers;


import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.yearup.models.CartItem;
import org.yearup.models.ShoppingCart;
import org.yearup.models.ShoppingCartItem;
import org.yearup.models.User;
import org.yearup.repository.ShoppingCartRepository;
import org.yearup.repository.UserRepository;
import org.yearup.service.OrderService;
import org.yearup.service.UserService;

import java.security.Principal;
import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/orders")
@PreAuthorize("isAuthenticated()")
public class OrderController {

    UserService userService;
    OrderService orderService;
    public OrderController(UserService userService, OrderService orderService){

        this.userService = userService;
        this.orderService = orderService;
    }

    @PostMapping("")
    public ResponseEntity<Void> checkout(Principal principal){
        String username = principal.getName();
        User user = this.userService.getByUserName(username);

        this.orderService.checkout(user.getId());

        return  ResponseEntity.noContent().build();

    }
}
