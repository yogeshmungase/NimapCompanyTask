package com.nimap.NimapProject.service;

import com.nimap.NimapProject.dto.CreateProductRequest;
import com.nimap.NimapProject.dto.PageResponse;
import com.nimap.NimapProject.dto.ProductDTO;
import com.nimap.NimapProject.dto.UpdateProductRequest;

public interface ProductService {
    PageResponse<ProductDTO> getAll(int page, int size);
    ProductDTO create(CreateProductRequest req);
    ProductDTO getById(Long id);
    ProductDTO update(Long id, UpdateProductRequest req);
    void delete(Long id);
}