package org.springboot.trendmartecommerceplatform.category;

import lombok.RequiredArgsConstructor;
import org.springboot.trendmartecommerceplatform.category.SubCategoryRequest;
import org.springboot.trendmartecommerceplatform.category.SubCategoryResponse;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class SubCategoryService {

    private final SubCategoryRepository subCategoryRepository;
    private final CategoryRepository categoryRepository;


    public SubCategoryResponse create(SubCategoryRequest dto) {
        Category category = categoryRepository.findById(dto.getCategoryId())
                .orElseThrow(() -> new RuntimeException("Category not found"));

        SubCategory saved = subCategoryRepository.save(
                SubCategory.builder()
                        .name(dto.getName())
                        .category(category)
                        .build()
        );

        return toResponse(saved);
    }

    public List<SubCategoryResponse> getAll() {
        return subCategoryRepository.findAll()
                .stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }


    private SubCategoryResponse toResponse(SubCategory subCategory) {
        SubCategoryResponse res = new SubCategoryResponse();
        res.setId(subCategory.getId());
        res.setName(subCategory.getName());
        res.setCategoryName(subCategory.getCategory().getName());
        return res;
    }
}
