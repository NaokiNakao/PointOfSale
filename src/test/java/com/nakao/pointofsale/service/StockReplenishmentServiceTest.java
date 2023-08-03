package com.nakao.pointofsale.service;

import com.nakao.pointofsale.enumeration.StockReplenishmentStatus;
import com.nakao.pointofsale.exception.BusinessLogicException;
import com.nakao.pointofsale.model.Category;
import com.nakao.pointofsale.model.Product;
import com.nakao.pointofsale.model.StockReplenishment;
import com.nakao.pointofsale.model.Supplier;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
@ExtendWith(SpringExtension.class)
@Transactional
public class StockReplenishmentServiceTest {

    private final StockReplenishmentService stockReplenishmentService;
    private final CategoryService categoryService;
    private final ProductService productService;
    private final SupplierService supplierService;

    private String productSku;
    private Long supplierId;

    @Autowired
    public StockReplenishmentServiceTest(StockReplenishmentService stockReplenishmentService,
                                         CategoryService categoryService,
                                         ProductService productService,
                                         SupplierService supplierService) {
        this.stockReplenishmentService = stockReplenishmentService;
        this.categoryService = categoryService;
        this.productService = productService;
        this.supplierService = supplierService;
    }

    @BeforeEach
    public void setUp() {
        Category category = Category.builder().name("Test Category").build();
        String categoryId = categoryService.createCategory(category);

        Product product = Product.builder()
                .name("Test Product")
                .categoryId(categoryId)
                .stock(50)
                .minStock(20)
                .acquisitionCost(BigDecimal.valueOf(9.99))
                .sellingPrice(BigDecimal.valueOf(13.54))
                .build();
        productSku = productService.createProduct(product);

        Supplier supplier = Supplier.builder()
                .name("Test Supplier")
                .address("Test Address")
                .contact("supplier@test.com")
                .build();
        supplierId = supplierService.createSupplier(supplier);
    }

    @Test
    public void replenishmentProcessing_Success() {
        StockReplenishment stockReplenishment = StockReplenishment.builder()
                .deliveryDate(LocalDate.now())
                .productSku(productSku)
                .productQuantity(50)
                .supplierId(supplierId)
                .status(StockReplenishmentStatus.PENDING.getValue())
                .build();

        String stockReplenishmentId = stockReplenishmentService.createStockReplenishment(stockReplenishment);
        Integer afterProcessingProductQuantity = productService.getProductBySku(productSku).getStock() +
                stockReplenishment.getProductQuantity();

        stockReplenishmentService.replenishmentProcessing(stockReplenishmentId);

        assertThat(productService.getProductBySku(productSku).getStock()).isEqualTo(afterProcessingProductQuantity);
        assertThat(stockReplenishmentService.getStockReplenishmentById(stockReplenishmentId).getStatus())
                .isEqualTo(StockReplenishmentStatus.DELIVERED.getValue());
    }

    @Test
    public void replenishmentProcessing_Failed() {
        StockReplenishment stockReplenishment = StockReplenishment.builder()
                .deliveryDate(LocalDate.now())
                .productSku(productSku)
                .productQuantity(50)
                .supplierId(supplierId)
                .status(StockReplenishmentStatus.DELIVERED.getValue())
                .build();

        String stockReplenishmentId = stockReplenishmentService.createStockReplenishment(stockReplenishment);

        assertThrows(BusinessLogicException.class, () ->
                stockReplenishmentService.replenishmentProcessing(stockReplenishmentId));
    }

}
