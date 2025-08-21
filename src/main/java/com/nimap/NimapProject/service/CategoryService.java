package com.nimap.NimapProject.service;

import com.nimap.NimapProject.dto.CategoryDTO;
import com.nimap.NimapProject.dto.CreateCategoryRequest;
import com.nimap.NimapProject.dto.PageResponse;
import com.nimap.NimapProject.dto.UpdateCategoryRequest;



public interface CategoryService {
    PageResponse<CategoryDTO> getAll(int page, int size);
    CategoryDTO create(CreateCategoryRequest req);
    CategoryDTO getById(Long id);
    CategoryDTO update(Long id, UpdateCategoryRequest req);
    void delete(Long id);
}
