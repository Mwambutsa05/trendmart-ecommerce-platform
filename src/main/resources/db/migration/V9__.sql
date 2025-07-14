ALTER TABLE product_image_urls
DROP
CONSTRAINT fk_product_imageurls_on_product;

ALTER TABLE users
    ADD enabled BOOLEAN;

ALTER TABLE users
    ALTER COLUMN enabled SET NOT NULL;

DROP TABLE product_image_urls CASCADE;

DROP SEQUENCE reviews_seq CASCADE;