package org.springboot.trendmartecommerceplatform.category;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity<Category> addCategory(@RequestBody Dto dto) {
        Category newCategory = categoryService.addNewCategory(dto);
        return ResponseEntity.ok(newCategory);
    }

    @GetMapping("/{id}")
    @Operation(summary = "admins gets all products category")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<Category>> getAllCategory(@PathVariable long id) {
        List<Category> categories = categoryService.findAll();
       return ResponseEntity.ok(categories);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "admins delete a category")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<String> deleteCategory(@PathVariable long id) {
       categoryService.deleteCategory(id);
       return ResponseEntity.ok("Deleted");
    }

    @PatchMapping("/{id}")
    @Operation(summary = "admins edits a category")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Category> editCategory(@RequestBody Dto dto, @PathVariable long id) {
        Category updateCategory = categoryService.updateCategory(dto, id);
        return ResponseEntity.ok(updateCategory);
    }

}

