package com.nakao.pointofsale.service;

import com.nakao.pointofsale.dao.ProductDAO;
import com.nakao.pointofsale.exception.DeletionException;
import com.nakao.pointofsale.exception.NotFoundException;
import com.nakao.pointofsale.exception.UniqueIdentifierGenerationException;
import com.nakao.pointofsale.model.Product;
import com.nakao.pointofsale.event.lowstock.LowStockEvent;
import com.nakao.pointofsale.repository.ProductRepository;
import com.nakao.pointofsale.repository.StockReplenishmentRepository;
import com.nakao.pointofsale.util.IdentifierGenerator;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

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

    public String createProduct(Product product) {
        product.setSku(IdentifierGenerator.generateIdentifier(Product.SKU_PATTERN));
        uniqueIdVerification(product.getSku());
        productDAO.insert(product);
        return product.getSku();
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

    /**
     * Scheduled method that checks for products with stock levels below the minimum stock threshold
     * and publishes a low stock event for each such product.
     * <p>
     * This method is scheduled to run at a specific time using a cron expression.
     */
    @Scheduled(cron = "0 0 17 * * ?")
    public void checkForReplenishment() {
        List<Product> products = productRepository.findAll();

        for (Product product : products) {
            if (product.getStock() < product.getMinStock()) {
                LowStockEvent lowStockEvent = new LowStockEvent(product);
                applicationEventPublisher.publishEvent(lowStockEvent);
            }
        }
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

