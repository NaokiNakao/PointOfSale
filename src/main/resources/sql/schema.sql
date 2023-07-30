DROP DATABASE IF EXISTS pos;
DROP SCHEMA IF EXISTS pos;

CREATE SCHEMA pos;
USE pos;

-- Category table

CREATE TABLE category (
    id   VARCHAR(9) PRIMARY KEY,
    name VARCHAR(50) NOT NULL
);

-- Product table

CREATE TABLE product (
    sku              VARCHAR(13) PRIMARY KEY,
    name             VARCHAR(50) NOT NULL,
    category_id      VARCHAR(9) NOT NULL,
    stock            INTEGER DEFAULT 0,
    min_stock        INTEGER DEFAULT 0,
    acquisition_cost NUMERIC(10, 2) DEFAULT 0.00,
    selling_price    NUMERIC(10, 2) DEFAULT 0.00,
    FOREIGN KEY (category_id) REFERENCES category(id) ON DELETE RESTRICT ON UPDATE CASCADE
);

-- Supplier table

CREATE TABLE supplier (
    id      BIGINT AUTO_INCREMENT PRIMARY KEY,
    name    VARCHAR(50) NOT NULL,
    address VARCHAR(50) NOT NULL,
    contact VARCHAR(255) NOT NULL
);

-- Restock table

CREATE TABLE stock_replenishment (
    id               VARCHAR(40) PRIMARY KEY,
    delivery_date    DATE,
    product_sku      VARCHAR(13) NOT NULL,
    product_quantity INTEGER DEFAULT 0,
    supplier_id      BIGINT NOT NULL,
    status           VARCHAR(15) NOT NULL DEFAULT 'PENDING',
    CONSTRAINT CK_stock_replenishment_status CHECK ( status IN ('PENDING', 'DELIVERED') ),
    FOREIGN KEY (product_sku) REFERENCES product(sku) ON DELETE RESTRICT ON UPDATE CASCADE,
    FOREIGN KEY (supplier_id) REFERENCES supplier(id) ON DELETE RESTRICT ON UPDATE CASCADE
);

-- Employee table

CREATE TABLE employee (
    id         VARCHAR(9) PRIMARY KEY,
    first_name VARCHAR (50) NOT NULL,
    last_name  VARCHAR(50) NOT NULL,
    email      VARCHAR(255) NOT NULL,
    password   VARCHAR(255) NOT NULL,
    phone      VARCHAR(20),
    role       VARCHAR(20) NOT NULL DEFAULT 'CASHIER',
    CONSTRAINT UQ_employee_email UNIQUE (email),
    CONSTRAINT CK_employee_role CHECK ( role IN ('CASHIER', 'MANAGER', 'ADMIN') )
);

-- Customer table

CREATE TABLE customer (
    id         VARCHAR(13) PRIMARY KEY,
    first_name VARCHAR (50) NOT NULL,
    last_name  VARCHAR(50) NOT NULL,
    phone      VARCHAR(20),
    address    VARCHAR(100),
    birthday   DATE
);

-- Order table

CREATE TABLE orders (
    id             VARCHAR(40) PRIMARY KEY,
    date           DATE,
    net            DECIMAL(10, 2) DEFAULT 0.00,
    tax            DECIMAL(10, 2) DEFAULT 0.00,
    total          DECIMAL(10, 2) DEFAULT 0.00,
    payment_method VARCHAR(15) NOT NULL DEFAULT 'CASH',
    status         VARCHAR(15) NOT NULL DEFAULT 'IN_PROGRESS',
    customer_id    VARCHAR(9),
    employee_id    VARCHAR(9) NOT NULL,
    FOREIGN KEY (customer_id) REFERENCES customer(id) ON DELETE RESTRICT ON UPDATE CASCADE,
    FOREIGN KEY (employee_id) REFERENCES employee(id) ON DELETE NO ACTION ON UPDATE CASCADE,
    CONSTRAINT CK_orders_payment_method CHECK ( payment_method IN ('CASH', 'CREDIT_CARD') ),
    CONSTRAINT CK_orders_status CHECK ( status IN ('IN_PROGRESS', 'PROCESSED') )
);

-- order Item table

CREATE TABLE order_item (
    id          VARCHAR(40) PRIMARY KEY,
    product_sku VARCHAR(13) NOT NULL,
    order_id    VARCHAR(40) NOT NULL,
    FOREIGN KEY (product_sku) REFERENCES product(sku) ON DELETE RESTRICT ON UPDATE CASCADE,
    FOREIGN KEY (order_id) REFERENCES orders(id) ON DELETE CASCADE ON UPDATE CASCADE
);






