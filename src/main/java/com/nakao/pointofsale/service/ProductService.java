package com.nakao.pos.service;

import com.nakao.pos.dao.ProductDAO;
import com.nakao.pos.exception.*;
import com.nakao.pos.model.Product;
import com.nakao.pos.observer.LowStockEvent;
import com.nakao.pos.repository.ProductRepository;
import com.nakao.pos.repository.StockReplenishmentRepository;
import com.nakao.pos.util.IdentifierGenerator;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.context.ApplicationEventPublisher;
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
public class ProductService {

    private final ProductRepository productRepository;
    private final ProductDAO productDAO;
    private final StockReplenishmentRepository stockReplenishmentRepository;
    private final ApplicationEventPublisher applicationEventPublisher;

    public List<Product> getProducts(Integer pageNumber, Integer pageSize) {
        Pageable pageable = PageRequest.of(pageNumber, pageSize);
        Page<Product> page = productRepository.findAll(pageable);
        return page.getContent();
    }

    public Product getProductBySku(String sku) {
        return productRepository.findById(sku)
                .orElseThrow(() -> new NotFoundException("Product not found with SKU: " + sku));
    }

    public void createProduct(Product product) {
        product.setSku(IdentifierGenerator.generateIdentifier(Product.SKU_PATTERN));
        uniqueIdVerification(product.getSku());
        productDAO.insert(product);
    }

    public void updateProduct(String sku, Product product) {
        Product updatedProduct = getProductBySku(sku);
        BeanUtils.copyProperties(product, updatedProduct);
        updatedProduct.setSku(sku);
        productRepository.save(updatedProduct);
    }

    public void deleteProduct(String sku) {
        if (productRepository.existsById(sku)) {
            if (isValidProductDeletion(getProductBySku(sku).getSku())) {
                productRepository.deleteById(sku);
            } else {
                throw new DeletionException("Unable to delete Product with SKU: " + sku);
            }
        }
        else {
            throw new NotFoundException("Product not found with SKU: " + sku);
        }
    }

    public Boolean checkForReplenishment(String sku) {
        Product product = getProductBySku(sku);

        if (product.getStock() < product.getMinStock()) {
            LowStockEvent lowStockEvent = new LowStockEvent(product);
            applicationEventPublisher.publishEvent(lowStockEvent);
            return true;
        }

        return false;
    }

    private void uniqueIdVerification(String sku) {
        if (productRepository.existsById(sku)) {
            throw new UniqueIdentifierGenerationException("Error generating unique identifier for Product");
        }
    }

    private Boolean isValidProductDeletion(String sku) {
        return stockReplenishmentRepository.countByProductSku(sku) == 0;
    }

}

