package org.springboot.trendmartecommerceplatform.category;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/category")
@RequiredArgsConstructor
@SecurityRequirement(name = "Auth")
public class CategoryController {

    public final CategoryService categoryService;

    @PostMapping
    @Operation(summary = "Add new category of products")
    @PreAuthorize("hasRole('ADMIN')")
    public Category addCategory(@RequestBody Dto dto) {
        return categoryService.addNewCategory(dto);
    }

    @GetMapping("/{id}")
    @Operation(summary = "admins gets all products category")
    @PreAuthorize("hasRole('ADMIN')")
    public List<Category> getAllCategory(@PathVariable long id) {
       return categoryService.findAll();
    }

    @DeleteMapping("/admin/delete")
    @Operation(summary = "admins delete a category")
    @PreAuthorize("hasRole('ADMIN')")
    public void deleteCategory(@PathVariable long id) {
        categoryService.deleteCategory(id);
    }

    @PatchMapping("/admin/edit/{id}")
    @Operation(summary = "admins edits a category")
    @PreAuthorize("hasRole('ADMIN')")
    public Category editCategory(@RequestBody Dto dto, @PathVariable long id) {
        return categoryService.updateCategory(dto, id);
    }

}

