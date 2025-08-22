CREATE TABLE IF NOT EXISTS app_product (
                                           id BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY,
                                           available INT,
                                           category VARCHAR(100),
    description TEXT,
    image_url VARCHAR(500),
    price DECIMAL(10,2),
    product_name VARCHAR(255),
    decante VARCHAR(255),
    available_quantities TEXT,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
    );
