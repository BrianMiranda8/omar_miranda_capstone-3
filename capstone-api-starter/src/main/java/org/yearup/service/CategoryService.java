package org.yearup.service;

import org.springframework.stereotype.Service;
import org.yearup.errors.CategoryNotFound;
import org.yearup.errors.DuplicateCategory;
import org.yearup.models.Category;
import org.yearup.models.Product;
import org.yearup.repository.CategoryRepository;

import javax.swing.text.html.Option;
import java.util.List;
import java.util.Optional;

@Service
public class CategoryService
{
    private final CategoryRepository categoryRepository;

    public CategoryService(CategoryRepository categoryRepository)
    {
        this.categoryRepository = categoryRepository;
    }

    public List<Category> getAllCategories()
    {
        // get all categories
        return this.categoryRepository.findAll();
    }

    public Category getById(int categoryId)
    {
        Optional<Category> category = this.categoryRepository.findById(categoryId);
        return category.orElseThrow(()->new CategoryNotFound("Category with id: "+ categoryId + " not found"));
    }

    public Category create(Category category)
    {
        List<Category> categories = this.categoryRepository.findAllByName(category.getName());
        if (!categories.isEmpty()){
            throw  new DuplicateCategory(category.getName() + " already exits with id of: "+ categories.get(0).getCategoryId());
        }
        return this.categoryRepository.save(category);
    }

    public Category update(int categoryId, Category category)
    {
        Category category1 = this.categoryRepository.findById(categoryId).orElseThrow(()-> new CategoryNotFound("Category with id: "+ categoryId + " not found"));
        category1.setDescription(category.getDescription());
        category1.setName(category.getName());

        return this.categoryRepository.save(category1);
    }

    public void delete(int categoryId)
    {
        this.categoryRepository.deleteById(categoryId);
    }

//    public List<Product> getCategoryProducts(int categoryId){
//
//
//    }
}
