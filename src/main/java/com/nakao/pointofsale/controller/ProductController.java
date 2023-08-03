package com.nakao.pointofsale.controller;

import com.nakao.pointofsale.model.Product;
import com.nakao.pointofsale.service.ProductService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
    public ResponseEntity<String> createProduct(@RequestBody @Valid Product product) {
        String productSku = productService.createProduct(product);
        return new ResponseEntity<>("Product created: " + productSku, HttpStatus.CREATED);
    }

    @PutMapping("/{sku}")
    public ResponseEntity<String> updateProduct(@PathVariable String sku, @RequestBody @Valid Product product) {
        productService.updateProduct(sku, product);
        return new ResponseEntity<>("Product updated", HttpStatus.OK);
    }

    @DeleteMapping("/{sku}")
    public ResponseEntity<Void> deleteProduct(@PathVariable String sku) {
        productService.deleteProduct(sku);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}
