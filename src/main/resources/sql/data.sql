-- Category insertions
INSERT INTO category (id, name) VALUES ('CAT000001', 'Building');
INSERT INTO category (id, name) VALUES ('CAT000002', 'Plumbing');
INSERT INTO category (id, name) VALUES ('CAT000003', 'Paint');
INSERT INTO category (id, name) VALUES ('CAT000004', 'Gardening');
INSERT INTO category (id, name) VALUES ('CAT000005', 'Power Tools');

-- Product insertions
INSERT INTO product (sku, name, category_id, stock, min_stock, acquisition_cost, selling_price)
VALUES ('SKU00000001', 'Hammer', 'CAT000001', 50, 10, 10.00, 20.00);

INSERT INTO product (sku, name, category_id, stock, min_stock, acquisition_cost, selling_price)
VALUES ('SKU00000002', 'Nails (100 pcs)', 'CAT000001', 200, 50, 2.50, 5.00);

INSERT INTO product (sku, name, category_id, stock, min_stock, acquisition_cost, selling_price)
VALUES ('SKU00000003', 'Wrench Set', 'CAT000001', 30, 5, 25.00, 45.00);

INSERT INTO product (sku, name, category_id, stock, min_stock, acquisition_cost, selling_price)
VALUES ('SKU00000004', 'PVC Pipe (1 meter)', 'CAT000002', 100, 20, 3.00, 6.00);

INSERT INTO product (sku, name, category_id, stock, min_stock, acquisition_cost, selling_price)
VALUES ('SKU00000005', 'Faucet', 'CAT000002', 40, 10, 15.00, 30.00);

INSERT INTO product (sku, name, category_id, stock, min_stock, acquisition_cost, selling_price)
VALUES ('SKU00000006', 'Blue Paint (1 liter)', 'CAT000003', 80, 15, 8.00, 15.00);

INSERT INTO product (sku, name, category_id, stock, min_stock, acquisition_cost, selling_price)
VALUES ('SKU00000007', 'Brush Set', 'CAT000003', 60, 10, 5.00, 12.00);

INSERT INTO product (sku, name, category_id, stock, min_stock, acquisition_cost, selling_price)
VALUES ('SKU00000008', 'Gardening Gloves', 'CAT000004', 90, 20, 4.50, 10.00);

INSERT INTO product (sku, name, category_id, stock, min_stock, acquisition_cost, selling_price)
VALUES ('SKU00000009', 'Pruning Shears', 'CAT000004', 25, 5, 12.00, 25.00);

INSERT INTO product (sku, name, category_id, stock, min_stock, acquisition_cost, selling_price)
VALUES ('SKU00000010', 'Drill', 'CAT000005', 15, 3, 60.00, 120.00);

INSERT INTO product (sku, name, category_id, stock, min_stock, acquisition_cost, selling_price)
VALUES ('SKU00000011', 'Circular Saw', 'CAT000005', 8, 2, 85.00, 160.00);

INSERT INTO product (sku, name, category_id, stock, min_stock, acquisition_cost, selling_price)
VALUES ('SKU00000012', 'Screwdriver Set', 'CAT000001', 40, 8, 18.00, 35.00);

INSERT INTO product (sku, name, category_id, stock, min_stock, acquisition_cost, selling_price)
VALUES ('SKU00000013', 'Copper Pipe (2 meters)', 'CAT000002', 60, 12, 6.50, 13.00);

INSERT INTO product (sku, name, category_id, stock, min_stock, acquisition_cost, selling_price)
VALUES ('SKU00000014', 'Red Paint (1 liter)', 'CAT000003', 70, 10, 9.00, 18.00);

INSERT INTO product (sku, name, category_id, stock, min_stock, acquisition_cost, selling_price)
VALUES ('SKU00000015', 'Garden Hose', 'CAT000004', 120, 25, 12.00, 25.00);

-- Supplier insertions
INSERT INTO supplier (name, address, contact) VALUES ('Supplier A', '123 Main Street', 'supplierA@example.com');
INSERT INTO supplier (name, address, contact) VALUES ('Supplier B', '456 Park Avenue', 'supplierB@example.com');
INSERT INTO supplier (name, address, contact) VALUES ('Supplier C', '789 Elm Road', 'supplierC@example.com');
INSERT INTO supplier (name, address, contact) VALUES ('Supplier D', '101 Oak Avenue', 'supplierD@example.com');
INSERT INTO supplier (name, address, contact) VALUES ('Supplier E', '222 Maple Lane', 'supplierE@example.com');

-- Stock Replenishment insertions
INSERT INTO stock_replenishment (id, delivery_date, product_sku, product_quantity, supplier_id, status)
VALUES ('REPL000001', '2023-07-20', 'SKU00000001', 50, 1, 'PENDING');

INSERT INTO stock_replenishment (id, delivery_date, product_sku, product_quantity, supplier_id, status)
VALUES ('REPL000002', '2023-07-22', 'SKU00000002', 100, 2, 'PENDING');

INSERT INTO stock_replenishment (id, delivery_date, product_sku, product_quantity, supplier_id, status)
VALUES ('REPL000003', '2023-07-25', 'SKU00000001', 30, 3, 'DELIVERED');

INSERT INTO stock_replenishment (id, delivery_date, product_sku, product_quantity, supplier_id, status)
VALUES ('REPL000004', '2023-07-26', 'SKU00000003', 70, 4, 'PENDING');

INSERT INTO stock_replenishment (id, delivery_date, product_sku, product_quantity, supplier_id, status)
VALUES ('REPL000005', '2023-07-28', 'SKU00000002', 80, 5, 'DELIVERED');

-- Employee insertions
INSERT INTO employee (id, first_name, last_name, email, password, phone)
VALUES ('EMP000001', 'John', 'Doe', 'john.doe@example.com', 'password123', '555-1234');

INSERT INTO employee (id, first_name, last_name, email, password, phone)
VALUES ('EMP000002', 'Jane', 'Smith', 'jane.smith@example.com', 'secret321', '555-5678');

INSERT INTO employee (id, first_name, last_name, email, password, phone)
VALUES ('EMP000003', 'Robert', 'Johnson', 'robert.johnson@example.com', 'qwerty', '555-9876');

-- Customer insertions
INSERT INTO customer (id, first_name, last_name, phone, address, birthday)
VALUES ('CUS000001', 'Alice', 'Brown', '555-1111', '123 Main St', '1990-05-15');

INSERT INTO customer (id, first_name, last_name, phone, address, birthday)
VALUES ('CUS000002', 'Michael', 'Lee', '555-2222', '456 Park Ave', '1985-09-30');

INSERT INTO customer (id, first_name, last_name, phone, address, birthday)
VALUES ('CUS000003', 'Emily', 'Johnson', '555-3333', '789 Elm Rd', '1995-12-10');


-- Order insertions
INSERT INTO orders (id, date, net, tax, total, payment_method, status, customer_id, employee_id)
VALUES ('ORDER000001', '2023-07-20', 100.00, 10.00, 110.00, 'CASH', 'IN_PROGRESS', 'CUS000001', 'EMP000001');

INSERT INTO orders (id, date, net, tax, total, payment_method, status, customer_id, employee_id)
VALUES ('ORDER000002', '2023-07-22', 200.00, 20.00, 220.00, 'CREDIT_CARD', 'IN_PROGRESS', 'CUS000002', 'EMP000002');

-- Order Item insertions
INSERT INTO order_item (id, product_sku, order_id)
VALUES ('ITEM000001', 'SKU00000001', 'ORDER000001');

INSERT INTO order_item (id, product_sku, order_id)
VALUES ('ITEM000002', 'SKU00000002', 'ORDER000001');

INSERT INTO order_item (id, product_sku, order_id)
VALUES ('ITEM000003', 'SKU00000003', 'ORDER000001');

INSERT INTO order_item (id, product_sku, order_id)
VALUES ('ITEM000004', 'SKU00000004', 'ORDER000002');

INSERT INTO order_item (id, product_sku, order_id)
VALUES ('ITEM000005', 'SKU00000005', 'ORDER000002');

INSERT INTO order_item (id, product_sku, order_id)
VALUES ('ITEM000006', 'SKU00000006', 'ORDER000002');



