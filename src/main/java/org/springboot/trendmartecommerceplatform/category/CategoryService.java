package org.springboot.trendmartecommerceplatform.category;

import lombok.AllArgsConstructor;
import org.springboot.trendmartecommerceplatform.Product.ProductRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class CategoryService {
   private final CategoryRepository categoryRepository;
   private final ProductRepository productRepository;

   public List<Category> findAll() {
       return categoryRepository.findAll();
   }

   public Category addNewCategory(Dto dto) {
       Category category = new Category();
       category.setName(dto.getName());
       category.setDescription(dto.getDescription());
       return categoryRepository.save(category);
   }

   public Category updateCategory(Dto dto, long id) {
       Category categoryUpdate = categoryRepository.findById(id).get();
       categoryUpdate.setName(dto.getName());
       categoryUpdate.setDescription(dto.getDescription());
       return categoryRepository.save(categoryUpdate);
   }

   public Category findById(Long id) {
       Category category = categoryRepository.findById(id).get();
       return category;
   }

    public void deleteCategory(Long id) {
        categoryRepository.deleteById(id);
    }

}
