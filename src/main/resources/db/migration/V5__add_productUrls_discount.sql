ALTER TABLE product
    ADD discount_id BIGINT;

ALTER TABLE discount
    ADD end_date date;

ALTER TABLE discount
    ADD start_date date;

ALTER TABLE address
    ADD CONSTRAINT uc_address_user UNIQUE (user_id);

ALTER TABLE product
    ADD CONSTRAINT uc_product_discount UNIQUE (discount_id);

ALTER TABLE product
    ADD CONSTRAINT FK_PRODUCT_ON_DISCOUNT FOREIGN KEY (discount_id) REFERENCES discount (id);

ALTER TABLE discount
    DROP COLUMN created_at;

ALTER TABLE discount
    DROP COLUMN discount_percentage;

ALTER TABLE discount
    ADD discount_percentage DOUBLE PRECISION;

ALTER TABLE product
    ALTER COLUMN price TYPE DECIMAL USING (price::DECIMAL);