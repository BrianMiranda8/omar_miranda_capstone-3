package org.yearup.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import org.yearup.dtos.ProductSearchRequest;
import org.yearup.models.Product;
import org.yearup.service.ProductService;

import java.util.List;

@RestController
@RequestMapping("products")
@CrossOrigin
public class ProductsController {
    private final ProductService productService;

    public ProductsController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping("")
    @PreAuthorize("permitAll()")
    public ResponseEntity<List<Product>> search(ProductSearchRequest productSearchRequest) {
        List<Product> products = productService.search(productSearchRequest);

        return ResponseEntity.ok(products);
    }

    @GetMapping("{id}")
    @PreAuthorize("permitAll()")
    public Product getById(@PathVariable int id) {
        return productService.getById(id);

    }

    @PostMapping()
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<Product> addProduct(@RequestBody Product product) {
        Product saved = productService.create(product);
        return ResponseEntity.status(HttpStatus.CREATED).body(saved);
    }

    @PutMapping("{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public Product updateProduct(@PathVariable int id, @RequestBody Product product) {

        return productService.update(id, product);
    }

    @DeleteMapping("{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<Void> deleteProduct(@PathVariable int id) {

        productService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
