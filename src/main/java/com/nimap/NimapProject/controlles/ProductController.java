package com.nimap.NimapProject.controlles;

import com.nimap.NimapProject.dto.CreateProductRequest;
import com.nimap.NimapProject.dto.PageResponse;
import com.nimap.NimapProject.dto.ProductDTO;
import com.nimap.NimapProject.dto.UpdateProductRequest;
import com.nimap.NimapProject.service.ServiceImpl.ProductServiceImpl;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/products")
public class ProductController {

    @Autowired
    private  ProductServiceImpl service;


  @GetMapping
    public ResponseEntity<PageResponse<ProductDTO>> getAll(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        return ResponseEntity.ok(service.getAll(page, size));
    }


    @PostMapping
    public ResponseEntity<ProductDTO> create(@Valid @RequestBody CreateProductRequest req) {
        return ResponseEntity.ok(service.create(req));
    }


    @GetMapping("/{id}")
    public ResponseEntity<ProductDTO> getById(@PathVariable Long id) {
        return ResponseEntity.ok(service.getById(id));
    }


    @PutMapping("/{id}")
    public ResponseEntity<ProductDTO> update(@PathVariable Long id,
                                             @Valid @RequestBody UpdateProductRequest req) {
        return ResponseEntity.ok(service.update(id, req));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}