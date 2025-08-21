package com.nimap.NimapProject.controlles;

import com.nimap.NimapProject.dto.CategoryDTO;
import com.nimap.NimapProject.dto.CreateCategoryRequest;
import com.nimap.NimapProject.dto.PageResponse;
import com.nimap.NimapProject.dto.UpdateCategoryRequest;
import com.nimap.NimapProject.service.ServiceImpl.CategoryServiceImpl;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/categories")
public class CategoryController {
    @Autowired
    private CategoryServiceImpl service;


    @GetMapping
    public ResponseEntity<PageResponse<CategoryDTO>> getAll(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        return ResponseEntity.ok(service.getAll(page, size));
    }


    @PostMapping
    public ResponseEntity<CategoryDTO> create(@Valid @RequestBody CreateCategoryRequest req) {
        return ResponseEntity.ok(service.create(req));
    }


    @GetMapping("/{id}")
    public ResponseEntity<CategoryDTO> getById(@PathVariable Long id) {
        return ResponseEntity.ok(service.getById(id));
    }


    @PutMapping("/{id}")
    public ResponseEntity<CategoryDTO> update(@PathVariable Long id,
                                              @Valid @RequestBody UpdateCategoryRequest req) {
        return ResponseEntity.ok(service.update(id, req));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}
