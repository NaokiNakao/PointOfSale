package com.nakao.pos.controller;

import com.nakao.pos.model.Product;
import com.nakao.pos.service.ProductService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author Naoki Nakao on 7/18/2023
 * @project POS
 */

@RestController
@RequestMapping("/api/v1/products")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;

    @GetMapping
    public ResponseEntity<List<Product>> getProducts(@RequestParam(defaultValue = "0") Integer page,
                                                @RequestParam(defaultValue = "10") Integer size) {
        List<Product> products = productService.getProducts(page, size);
        return new ResponseEntity<>(products, HttpStatus.OK);
    }

    @GetMapping("/{sku}")
    public ResponseEntity<Product> getProductBySku(@PathVariable String sku) {
        Product product = productService.getProductBySku(sku);
        return new ResponseEntity<>(product, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<String > createProduct(@RequestBody @Valid Product product) {
        productService.createProduct(product);
        return new ResponseEntity<>("Product created", HttpStatus.CREATED);
    }

    @PutMapping("/{sku}")
    public ResponseEntity<String > updateProduct(@PathVariable String sku, @RequestBody @Valid Product product) {
        productService.updateProduct(sku, product);
        return new ResponseEntity<>("Product updated", HttpStatus.OK);
    }

    @DeleteMapping("/{sku}")
    public ResponseEntity<Void> deleteProduct(@PathVariable String sku) {
        productService.deleteProduct(sku);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping("/{sku}/check-status")
    public ResponseEntity<String> checkStockLevel(@PathVariable String sku) {
        String message;

        if (productService.checkForReplenishment(sku)) {
            message = "Stock replenishment for " + sku + " is needed. Notification sent";
        }
        else {
            message = "Stock level for " + sku + " is sufficient";
        }

        return new ResponseEntity<>(message, HttpStatus.OK);
    }

}
