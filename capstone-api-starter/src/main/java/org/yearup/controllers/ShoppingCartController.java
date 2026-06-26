package org.yearup.controllers;

import jakarta.annotation.security.PermitAll;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.yearup.dtos.UpdateProductRequest;
import org.yearup.models.ShoppingCart;
import org.yearup.models.ShoppingCartItem;
import org.yearup.models.User;
import org.yearup.service.ShoppingCartService;
import org.yearup.service.UserService;

import java.net.URI;
import java.security.Principal;

// convert this class to a REST controller
// only logged in users should have access to these actions
@RestController
@RequestMapping("cart")
@CrossOrigin
@PreAuthorize("hasAnyRole('USER', 'ADMIN')")
public class ShoppingCartController
{
    // a shopping cart controller depends on the service layer
    private ShoppingCartService shoppingCartService;
    private UserService userService;

    public ShoppingCartController(ShoppingCartService shoppingCartService, UserService userService) {
        this.shoppingCartService = shoppingCartService;
        this.userService = userService;
    }

    // each method in this controller requires a Principal object as a parameter
    @GetMapping("")
    public ShoppingCart getCart(Principal principal)
    {
        String userName = principal.getName();
        User user = userService.getByUserName(userName);
        int userId = user.getId();

        ShoppingCart shoppingCart = this.shoppingCartService.getByUserId(userId);

        return shoppingCart;
    }

    // add a POST method to add a product to the cart - the url should be
    // https://localhost:8080/cart/products/15  (15 is the productId to be added)
    // return the updated cart with status 201 Created
    @PostMapping("products/{id}")
    public ResponseEntity<ShoppingCart> addShoppingCart(@PathVariable int id, Principal principal){
        String username = principal.getName();

        User user = userService.getByUserName(username);
        int userid = user.getId();

        this.shoppingCartService.addCartItem(userid, id);
        ShoppingCart shoppingCart = this.shoppingCartService.getByUserId(userid);
        URI location = URI.create("/cart/products/"+id);
        return ResponseEntity.created(location).body(shoppingCart);
    }

    // add a PUT method to update an existing product in the cart - the url should be
    // https://localhost:8080/cart/products/15  (15 is the productId to be updated)
    // the BODY should be a ShoppingCartItem - quantity is the only value that will be updated; return the cart (200 OK)
    @PutMapping("products/{id}")
    public ResponseEntity<ShoppingCart> updateShoppingCartItem(Principal principal, @PathVariable int id, @RequestBody UpdateProductRequest updateProductRequest){
        // create user service
        String username = principal.getName();
        User user = userService.getByUserName(username);
        int userId = user.getId();

        this.shoppingCartService.updateItem(userId, id, updateProductRequest);


        ShoppingCart shoppingCart = this.shoppingCartService.getByUserId(userId);
        return ResponseEntity.ok(shoppingCart);

    }

    // add a DELETE method to clear all products from the current users cart
    // https://localhost:8080/cart  - return the (now empty) cart so the front end can refresh it (200 OK)

    @DeleteMapping("")
    public ResponseEntity<ShoppingCart> deleteShoppingCart(Principal principal){

        String username = principal.getName();
        User user = userService.getByUserName(username);
        int userId = user.getId();

        this.shoppingCartService.deleteCart(userId);
        ShoppingCart shoppingCart = this.shoppingCartService.getByUserId(userId);

        return ResponseEntity.ok(shoppingCart);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ShoppingCart> removeSingleItem(Principal principal, @PathVariable int id){
        String username = principal.getName();
        User user = userService.getByUserName(username);
        int userId = user.getId();

        this.shoppingCartService.deleteByUserAndProductId(userId, id);
        ShoppingCart shoppingCart = this.shoppingCartService.getByUserId(id);

        return  ResponseEntity.ok(shoppingCart);
    }
}
