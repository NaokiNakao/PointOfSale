package com.nakao.pos.service;

import com.nakao.pos.dao.CategoryDAO;
import com.nakao.pos.exception.DeletionException;
import com.nakao.pos.exception.NotFoundException;
import com.nakao.pos.exception.UniqueIdentifierGenerationException;
import com.nakao.pos.model.Category;
import com.nakao.pos.repository.CategoryRepository;
import com.nakao.pos.repository.ProductRepository;
import com.nakao.pos.util.IdentifierGenerator;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author Naoki Nakao on 7/18/2023
 * @project POS
 */

@Service
@Transactional
@RequiredArgsConstructor
public class CategoryService {

    private final CategoryRepository categoryRepository;
    private final CategoryDAO categoryDAO;
    private final ProductRepository productRepository;

    public List<Category> getCategories(Integer pageNumber, Integer pageSize) {
        Pageable pageable = PageRequest.of(pageNumber, pageSize);
        Page<Category> page = categoryRepository.findAll(pageable);
        return page.getContent();
    }

    public Category getCategoryById(String id) {
        return categoryRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Category not found with ID: " + id));
    }

    public void createCategory(Category category) {
        category.setId(IdentifierGenerator.generateIdentifier(Category.ID_PATTERN));
        uniqueIdVerification(category.getId());
        categoryDAO.insert(category);
    }

    public void updateCategory(String id, Category category) {
        Category updatedCategory = getCategoryById(id);
        BeanUtils.copyProperties(category, updatedCategory);
        updatedCategory.setId(id);
        categoryRepository.save(updatedCategory);
    }

    public void deleteCategory(String id) {
        if (categoryRepository.existsById(id)) {
            if (isValidCategoryDeletion(id)) {
                categoryRepository.deleteById(id);
            } else {
                throw new DeletionException("Unable to delete Category with ID: " + id);
            }
        }
        else {
            throw new NotFoundException("Category not found with ID: " + id);
        }
    }

    private void uniqueIdVerification(String id) {
        if (categoryRepository.existsById(id)) {
            throw new UniqueIdentifierGenerationException("Error generating unique identifier for Category");
        }
    }

    private Boolean isValidCategoryDeletion(String id) {
        return productRepository.countByCategoryId(id) == 0;
    }

}
