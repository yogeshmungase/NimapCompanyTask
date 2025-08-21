package com.nimap.NimapProject.service.ServiceImpl;

import com.nimap.NimapProject.dto.CategoryDTO;
import com.nimap.NimapProject.dto.CreateCategoryRequest;
import com.nimap.NimapProject.dto.PageResponse;
import com.nimap.NimapProject.dto.UpdateCategoryRequest;
import com.nimap.NimapProject.exception.ResourceNotFoundException;
import com.nimap.NimapProject.model.Category;
import com.nimap.NimapProject.repository.CategoryRepository;
import com.nimap.NimapProject.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

@Service
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    private CategoryRepository categoryRepo;

    @Override
    public PageResponse<CategoryDTO> getAll(int page, int size) {
        Page<Category> p = categoryRepo.findAll(PageRequest.of(page, size));
        return new PageResponse<>(
                p.map(this::toDTO).getContent(), p.getNumber(), p.getSize(),
                p.getTotalElements(), p.getTotalPages(), p.isLast()
        );
    }

    @Override
    public CategoryDTO create(CreateCategoryRequest req) {
        Category c = new Category();
        c.setName(req.getName());
        c.setDescription(req.getDescription());
        return toDTO(categoryRepo.save(c));
    }

    @Override
    public CategoryDTO getById(Long id) {
        Category c = categoryRepo.findById(id).orElseThrow(() -> new ResourceNotFoundException("Category not found: " + id));
        return toDTO(c);
    }

    @Override
    public CategoryDTO update(Long id, UpdateCategoryRequest req) {
        Category c = categoryRepo.findById(id).orElseThrow(() -> new ResourceNotFoundException("Category not found: " + id));
        if (req.getName() != null) c.setName(req.getName());
        if (req.getDescription() != null) c.setDescription(req.getDescription());
        return toDTO(categoryRepo.save(c));
    }

    @Override
    public void delete(Long id) {
        if (!categoryRepo.existsById(id)) throw new ResourceNotFoundException("Category not found: " + id);
        categoryRepo.deleteById(id);
    }

    private CategoryDTO toDTO(Category c) {
        return new CategoryDTO(c.getId(), c.getName(), c.getDescription());
    }
}
