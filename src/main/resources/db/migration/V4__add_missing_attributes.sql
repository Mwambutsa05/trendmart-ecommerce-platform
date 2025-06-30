ALTER TABLE product_urls
DROP
CONSTRAINT fk_product_urls_on_product;

CREATE TABLE product_image_urls
(
    product_id BIGINT NOT NULL,
    image_urls VARCHAR(255)
);

ALTER TABLE stock
    ADD category VARCHAR(255);

ALTER TABLE stock
    ADD description VARCHAR(255);

ALTER TABLE stock
    ADD name VARCHAR(255);

ALTER TABLE product
    ADD original_price DECIMAL;

ALTER TABLE product
    ADD quantity VARCHAR(255);

ALTER TABLE product_image_urls
    ADD CONSTRAINT fk_product_imageurls_on_product FOREIGN KEY (product_id) REFERENCES product (id);

DROP TABLE product_urls CASCADE;

ALTER TABLE product
DROP
COLUMN image_url;