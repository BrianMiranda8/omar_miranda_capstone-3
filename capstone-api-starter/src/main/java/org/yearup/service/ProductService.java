package org.yearup.service;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.yearup.dtos.ProductSearchRequest;
import org.yearup.errors.ProductNotFound;
import org.yearup.models.Product;
import org.yearup.repository.ProductRepository;

import java.util.List;

@Service
public class ProductService
{
    private final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository)
    {
        this.productRepository = productRepository;
    }

    public List<Product> search(ProductSearchRequest productSearchRequest)
    {

        // where 1=1
        // Programmatic way of creating a query
        Specification<Product> spec = Specification.where((root,query,cb)-> cb.conjunction());


        if (productSearchRequest.getCat() != null){

            // root is the entity we are using for this query ie; From Products
            // query is the top level query ie (at this point) Select * from Products where 1=1
            // criteriaBuilder is a factory that will help you create conditional checks ie
            // where categoryID = productSearchRequest.getCategoryId
           spec = spec.and(((root, query, criteriaBuilder)
                   -> criteriaBuilder.equal(root.get("categoryId"), productSearchRequest.getCat())));
        }
        if (productSearchRequest.getSubCategory() != null){
            spec = spec.and(((root, query, criteriaBuilder)
                    -> criteriaBuilder.equal(root.get("subCategory"), productSearchRequest.getSubCategory())));
        }

        if (productSearchRequest.getMaxPrice() != null){
            spec = spec.and(((root, query, criteriaBuilder)
                    -> criteriaBuilder.lessThanOrEqualTo(root.get("price"), productSearchRequest.getMaxPrice()) ));
        }

        if (productSearchRequest.getMinPrice() != null){
            spec = spec.and(((root, query, criteriaBuilder)
                    -> criteriaBuilder.greaterThanOrEqualTo(root.get("price"), productSearchRequest.getMinPrice()) ));
        }

        return this.productRepository.findAll(spec);
    }

    public List<Product> listByCategoryId(int categoryId)
    {

        return productRepository.findByCategoryId(categoryId);
    }

    public Product getById(int productId)
    {
        return productRepository.findById(productId)
                .orElseThrow(()->new ProductNotFound("Product with id: "+ productId + " not found"));
    }

    public Product create(Product product)
    {

        return productRepository.save(product);
    }

    public Product update(int productId, Product product)
    {
        Product existing = productRepository.findById(productId).orElseThrow(
                () -> new ProductNotFound("Product with id: " + productId + " not found")
        );
        existing.setName(product.getName());
        existing.setPrice(product.getPrice());
        existing.setCategoryId(product.getCategoryId());
        existing.setDescription(product.getDescription());
        existing.setSubCategory(product.getSubCategory());
        existing.setFeatured(product.isFeatured());
        existing.setImageUrl(product.getImageUrl());
        existing.setStock(product.getStock());
        return productRepository.save(existing);
    }

    public void delete(int productId)
    {
        productRepository.deleteById(productId);
    }
}
