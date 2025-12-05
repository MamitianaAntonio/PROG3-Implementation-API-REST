-- create Product table
CREATE TABLE Product (
    id INT PRIMARY KEY,
    name VARCHAR(255),
    price NUMERIC(10,2),
    creation_datetime TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- create Product_category
CREATE TABLE Product_category (
    id INT PRIMARY KEY,
    name VARCHAR(255),
    product_id int,
    CONSTRAINT fk_product
    FOREIGN KEY (product_id)
        REFERENCES Product(id)
        ON DELETE CASCADE
);
