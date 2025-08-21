package com.nimap.NimapProject.service.ServiceImpl;

import com.nimap.NimapProject.dto.*;
import com.nimap.NimapProject.exception.ResourceNotFoundException;
import com.nimap.NimapProject.model.Category;
import com.nimap.NimapProject.model.Product;
import com.nimap.NimapProject.repository.CategoryRepository;
import com.nimap.NimapProject.repository.ProductRepository;
import com.nimap.NimapProject.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    private  ProductRepository productRepo;

    @Autowired
    private  CategoryRepository categoryRepo;


    @Override
    public PageResponse<ProductDTO> getAll(int page, int size) {
        Page<Product> p = productRepo.findAll(PageRequest.of(page, size));
        return new PageResponse<>(
                p.map(this::toDTO).getContent(), p.getNumber(), p.getSize(),
                p.getTotalElements(), p.getTotalPages(), p.isLast()
        );
    }

    @Override
    public ProductDTO create(CreateProductRequest req) {
        Category cat = categoryRepo.findById(req.getCategoryId())
                .orElseThrow(() -> new ResourceNotFoundException("Category not found: " + req.getCategoryId()));

        Product prod = new Product();
        prod.setName(req.getName());
        prod.setDescription(req.getDescription());
        prod.setPrice(req.getPrice());
        prod.setCategory(cat);

        return toDTO(productRepo.save(prod));
    }

    @Override
    public ProductDTO getById(Long id) {
        Product prod = productRepo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Product not found: " + id));
        return toDTO(prod);
    }

    @Override
    public ProductDTO update(Long id, UpdateProductRequest req) {
        Product prod = productRepo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Product not found: " + id));

        if (req.getName() != null) prod.setName(req.getName());
        if (req.getDescription() != null) prod.setDescription(req.getDescription());
        if (req.getPrice() != null) prod.setPrice(req.getPrice());
        if (req.getCategoryId() != null) {
            Category cat = categoryRepo.findById(req.getCategoryId())
                    .orElseThrow(() -> new ResourceNotFoundException("Category not found: " + req.getCategoryId()));
            prod.setCategory(cat);
        }

        return toDTO(productRepo.save(prod));
    }

    @Override
    public void delete(Long id) {
        if (!productRepo.existsById(id)) throw new ResourceNotFoundException("Product not found: " + id);
        productRepo.deleteById(id);
    }

    private ProductDTO toDTO(Product p) {
        ProductDTO dto = new ProductDTO();
        dto.setId(p.getId());
        dto.setName(p.getName());
        dto.setDescription(p.getDescription());
        dto.setPrice(p.getPrice());
        dto.setCreatedAt(p.getCreatedAt());
        dto.setUpdatedAt(p.getUpdatedAt());

        Category c = p.getCategory();
        CategoryDTO cDto = new CategoryDTO(c.getId(), c.getName(), c.getDescription());
        dto.setCategory(cDto);
        return dto;
    }
}

