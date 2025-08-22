CREATE TABLE IF NOT EXISTS app_product_available_quantities (
                                                                id BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY,
                                                                app_product_id BIGINT NOT NULL,
                                                                available_quantities INT NOT NULL,
                                                                CONSTRAINT fk_app_product_qty_product
                                                                FOREIGN KEY (app_product_id) REFERENCES app_product(id)
    ON DELETE CASCADE ON UPDATE CASCADE,
    CONSTRAINT uq_app_product_qty UNIQUE (app_product_id, available_quantities)
    );

-- helpful index for lookups by product
CREATE INDEX idx_app_product_qty_product ON app_product_available_quantities(app_product_id);
