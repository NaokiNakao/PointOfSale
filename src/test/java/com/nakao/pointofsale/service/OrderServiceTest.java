package com.nakao.pointofsale.service;

import com.nakao.pointofsale.enumeration.EmployeeRole;
import com.nakao.pointofsale.exception.BusinessLogicException;
import com.nakao.pointofsale.model.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.RoundingMode;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
@ExtendWith(SpringExtension.class)
@Transactional
public class OrderServiceTest {

    private final OrderService orderService;
    private final CategoryService categoryService;
    private final ProductService productService;
    private final EmployeeService employeeService;

    private String product1Sku;
    private String product2Sku;
    private String product3Sku;
    private String employeeId;

    @Autowired
    public OrderServiceTest(OrderService orderService,
                            CategoryService categoryService,
                            ProductService productService,
                            EmployeeService employeeService) {
        this.orderService = orderService;
        this.categoryService = categoryService;
        this.productService = productService;
        this.employeeService = employeeService;
    }

    @BeforeEach
    public void setUp() {
        Category category = Category.builder().name("Test Category").build();
        String categoryId = categoryService.createCategory(category);

        product1Sku = productService.createProduct(Product.builder()
                .name("Test Product 1")
                .categoryId(categoryId)
                .stock(100)
                .minStock(10)
                .acquisitionCost(BigDecimal.valueOf(15.33))
                .sellingPrice(BigDecimal.valueOf(19.99))
                .build());

        product2Sku = productService.createProduct(Product.builder()
                .sku("PROD-456")
                .name("Test Product 2")
                .categoryId(categoryId)
                .stock(100)
                .minStock(10)
                .acquisitionCost(BigDecimal.valueOf(15.33))
                .sellingPrice(BigDecimal.valueOf(29.99))
                .build());

        product3Sku = productService.createProduct(Product.builder()
                .sku("PROD-789")
                .name("Test Product 3")
                .categoryId(categoryId)
                .stock(0)
                .minStock(10)
                .acquisitionCost(BigDecimal.valueOf(15.33))
                .sellingPrice(BigDecimal.valueOf(39.99))
                .build());

        Employee employee = Employee.builder()
                .firstName("John")
                .lastName("Smith")
                .email("johnsmith@test.com")
                .password("password")
                .phone("123 456789")
                .role(EmployeeRole.CASHIER.getValue())
                .build();
        employeeId = employeeService.createEmployee(employee);
    }

    @Test
    public void addItem_UpdatesTotalWhenAdded() {
        Order order = Order.builder().employeeId(employeeId).build();
        String orderId = orderService.createOrder(order);

        OrderItem orderItem1 = OrderItem.builder()
                .orderId(orderId)
                .productSku(product1Sku)
                .build();
        orderService.addItem(orderId, orderItem1);

        OrderItem orderItem2 = OrderItem.builder()
                .orderId(orderId)
                .productSku(product2Sku)
                .build();
        orderService.addItem(orderId, orderItem2);

        Product product1 = productService.getProductBySku(product1Sku);
        Product product2 = productService.getProductBySku(product2Sku);

        BigDecimal expectedOrderNet = product1.getSellingPrice().add(product2.getSellingPrice());
        BigDecimal taxRate = BigDecimal.valueOf(0.10);
        BigDecimal expectedOrderTax = expectedOrderNet.multiply(taxRate).setScale(2, RoundingMode.HALF_UP);
        BigDecimal expectedOrderTotal = expectedOrderNet.add(expectedOrderTax);

        assertThat(orderService.getOrderById(orderId).getNet()).isEqualTo(expectedOrderNet);
        assertThat(orderService.getOrderById(orderId).getTax()).isEqualTo(expectedOrderTax);
        assertThat(orderService.getOrderById(orderId).getTotal()).isEqualTo(expectedOrderTotal);
    }

    @Test
    public void addItem_NonAvailableProduct() {
        Order order = Order.builder().employeeId(employeeId).build();
        String orderId = orderService.createOrder(order);

        OrderItem orderItem = OrderItem.builder()
                .orderId(orderId)
                .productSku(product3Sku)
                .build();

        assertThrows(BusinessLogicException.class, () ->
                orderService.addItem(orderId, orderItem));
    }

}
