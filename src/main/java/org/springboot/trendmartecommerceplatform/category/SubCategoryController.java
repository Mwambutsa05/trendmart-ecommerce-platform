package org.springboot.trendmartecommerceplatform.category;

import lombok.RequiredArgsConstructor;
import org.springboot.trendmartecommerceplatform.category.SubCategoryRequest;
import org.springboot.trendmartecommerceplatform.category.SubCategoryResponse;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/subcategories")
@RequiredArgsConstructor
public class SubCategoryController {

    private final SubCategoryService service;

    @PostMapping
    public SubCategoryResponse create(@RequestBody SubCategoryRequest dto) {
        return service.create(dto);
    }

    @GetMapping
    public List<SubCategoryResponse> list() {
        return service.getAll();
    }
}
