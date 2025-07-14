CREATE TABLE address
(
    id          BIGINT AUTO_INCREMENT NOT NULL,
    street      VARCHAR(255) NULL,
    city        VARCHAR(255) NULL,
    state       VARCHAR(255) NULL,
    country     VARCHAR(255) NULL,
    postal_code VARCHAR(255) NULL,
    is_default  BIT(1) NULL,
    user_id     BIGINT NULL,
    CONSTRAINT pk_address PRIMARY KEY (id)
);

CREATE TABLE cart
(
    id         BIGINT AUTO_INCREMENT NOT NULL,
    user_id    BIGINT NULL,
    created_at datetime NULL,
    CONSTRAINT pk_cart PRIMARY KEY (id)
);

CREATE TABLE cart_item
(
    id         BIGINT AUTO_INCREMENT NOT NULL,
    cart_id    BIGINT NULL,
    product_id BIGINT NULL,
    quantity   INT NULL,
    CONSTRAINT pk_cartitem PRIMARY KEY (id)
);

CREATE TABLE category
(
    id            BIGINT AUTO_INCREMENT NOT NULL,
    name          VARCHAR(255) NULL,
    `description` VARCHAR(255) NULL,
    CONSTRAINT pk_category PRIMARY KEY (id)
);

CREATE TABLE discount
(
    id            BIGINT AUTO_INCREMENT NOT NULL,
    title         VARCHAR(255) NULL,
    `description` VARCHAR(255) NULL,
    discount_percentage DOUBLE NULL,
    active        BIT(1) NULL,
    start_date    date NULL,
    end_date      date NULL,
    CONSTRAINT pk_discount PRIMARY KEY (id)
);

CREATE TABLE order_item
(
    id         BIGINT AUTO_INCREMENT NOT NULL,
    product_id BIGINT NOT NULL,
    quantity   INT NULL,
    price      DECIMAL NULL,
    order_id   BIGINT NOT NULL,
    CONSTRAINT pk_orderitem PRIMARY KEY (id)
);

CREATE TABLE order_tracking
(
    id            BIGINT AUTO_INCREMENT NOT NULL,
    timestamp     datetime NULL,
    `description` VARCHAR(255) NULL,
    location      VARCHAR(255) NULL,
    updated_by    VARCHAR(255) NULL,
    order_id      BIGINT       NOT NULL,
    status        VARCHAR(255) NOT NULL,
    CONSTRAINT pk_ordertracking PRIMARY KEY (id)
);

CREATE TABLE orders
(
    id             BIGINT AUTO_INCREMENT NOT NULL,
    user_id        BIGINT       NOT NULL,
    total_amount   DECIMAL NULL,
    payment_method VARCHAR(255) NULL,
    created_at     datetime NULL,
    address_id     BIGINT NULL,
    status         VARCHAR(255) NOT NULL,
    CONSTRAINT pk_orders PRIMARY KEY (id)
);

CREATE TABLE otp_verification
(
    id          BIGINT NOT NULL,
    email       VARCHAR(255) NULL,
    code        VARCHAR(255) NULL,
    expiry_date datetime NULL,
    used        BIT(1) NULL,
    CONSTRAINT pk_otp_verification PRIMARY KEY (id)
);

CREATE TABLE payments
(
    id             BIGINT AUTO_INCREMENT NOT NULL,
    transaction_id VARCHAR(255) NULL,
    tx_ref         VARCHAR(255) NULL,
    status         VARCHAR(255) NULL,
    amount         INT NOT NULL,
    currency       VARCHAR(255) NULL,
    email          VARCHAR(255) NULL,
    first_name     VARCHAR(255) NULL,
    last_name      VARCHAR(255) NULL,
    payment_time   datetime NULL,
    CONSTRAINT pk_payments PRIMARY KEY (id)
);

CREATE TABLE product
(
    id             BIGINT AUTO_INCREMENT NOT NULL,
    name           VARCHAR(255) NULL,
    `description`  VARCHAR(255) NULL,
    price          DECIMAL NULL,
    original_price DECIMAL NULL,
    sku_code       VARCHAR(255) NULL,
    brand          VARCHAR(255) NULL,
    quantity       VARCHAR(255) NULL,
    created_at     datetime NULL,
    category_id    BIGINT NULL,
    stock_id       BIGINT NULL,
    discount_id    BIGINT NULL,
    CONSTRAINT pk_product PRIMARY KEY (id)
);

CREATE TABLE product_image_urls
(
    product_id BIGINT NOT NULL,
    image_urls VARCHAR(255) NULL
);

CREATE TABLE reviews
(
    id         BIGINT   NOT NULL,
    rating     INT      NOT NULL,
    comment    VARCHAR(255) NULL,
    created_at datetime NOT NULL,
    user_id    BIGINT NULL,
    product_id BIGINT NULL,
    CONSTRAINT pk_reviews PRIMARY KEY (id)
);

CREATE TABLE stock
(
    id            BIGINT AUTO_INCREMENT NOT NULL,
    name          VARCHAR(255) NULL,
    `description` VARCHAR(255) NULL,
    category      VARCHAR(255) NULL,
    quantity      INT NOT NULL,
    created_at    datetime NULL,
    CONSTRAINT pk_stock PRIMARY KEY (id)
);

CREATE TABLE sub_category
(
    id          BIGINT AUTO_INCREMENT NOT NULL,
    name        VARCHAR(255) NULL,
    category_id BIGINT NULL,
    CONSTRAINT pk_subcategory PRIMARY KEY (id)
);

CREATE TABLE users
(
    id            BIGINT AUTO_INCREMENT NOT NULL,
    first_name    VARCHAR(255) NULL,
    last_name     VARCHAR(255) NULL,
    username      VARCHAR(255) NULL,
    email         VARCHAR(255) NULL,
    phone_number  VARCHAR(255) NULL,
    password      VARCHAR(255) NULL,
    date_of_birth datetime NULL,
    verified      BIT(1) NOT NULL,
    enabled       BIT(1) NOT NULL,
    `role`        VARCHAR(255) NULL,
    CONSTRAINT pk_users PRIMARY KEY (id)
);

CREATE TABLE wishlist_item
(
    id         BIGINT AUTO_INCREMENT NOT NULL,
    user_id    BIGINT NULL,
    product_id BIGINT NULL,
    price      DECIMAL NULL,
    added_at   datetime NULL,
    CONSTRAINT pk_wishlistitem PRIMARY KEY (id)
);

ALTER TABLE address
    ADD CONSTRAINT uc_address_user UNIQUE (user_id);

ALTER TABLE cart
    ADD CONSTRAINT uc_cart_user UNIQUE (user_id);

ALTER TABLE product
    ADD CONSTRAINT uc_product_discount UNIQUE (discount_id);

ALTER TABLE product
    ADD CONSTRAINT uc_product_stock UNIQUE (stock_id);

ALTER TABLE address
    ADD CONSTRAINT FK_ADDRESS_ON_USER FOREIGN KEY (user_id) REFERENCES users (id);

ALTER TABLE cart_item
    ADD CONSTRAINT FK_CARTITEM_ON_CART FOREIGN KEY (cart_id) REFERENCES cart (id);

ALTER TABLE cart_item
    ADD CONSTRAINT FK_CARTITEM_ON_PRODUCT FOREIGN KEY (product_id) REFERENCES product (id);

ALTER TABLE cart
    ADD CONSTRAINT FK_CART_ON_USER FOREIGN KEY (user_id) REFERENCES users (id);

ALTER TABLE order_item
    ADD CONSTRAINT FK_ORDERITEM_ON_ORDER FOREIGN KEY (order_id) REFERENCES orders (id);

ALTER TABLE order_item
    ADD CONSTRAINT FK_ORDERITEM_ON_PRODUCT FOREIGN KEY (product_id) REFERENCES product (id);

ALTER TABLE orders
    ADD CONSTRAINT FK_ORDERS_ON_ADDRESS FOREIGN KEY (address_id) REFERENCES address (id);

ALTER TABLE orders
    ADD CONSTRAINT FK_ORDERS_ON_USER FOREIGN KEY (user_id) REFERENCES users (id);

ALTER TABLE order_tracking
    ADD CONSTRAINT FK_ORDERTRACKING_ON_ORDER FOREIGN KEY (order_id) REFERENCES orders (id);

ALTER TABLE product
    ADD CONSTRAINT FK_PRODUCT_ON_CATEGORY FOREIGN KEY (category_id) REFERENCES category (id);

ALTER TABLE product
    ADD CONSTRAINT FK_PRODUCT_ON_DISCOUNT FOREIGN KEY (discount_id) REFERENCES discount (id);

ALTER TABLE product
    ADD CONSTRAINT FK_PRODUCT_ON_STOCK FOREIGN KEY (stock_id) REFERENCES stock (id);

ALTER TABLE reviews
    ADD CONSTRAINT FK_REVIEWS_ON_PRODUCT FOREIGN KEY (product_id) REFERENCES product (id);

ALTER TABLE reviews
    ADD CONSTRAINT FK_REVIEWS_ON_USER FOREIGN KEY (user_id) REFERENCES users (id);

ALTER TABLE sub_category
    ADD CONSTRAINT FK_SUBCATEGORY_ON_CATEGORY FOREIGN KEY (category_id) REFERENCES category (id);

ALTER TABLE wishlist_item
    ADD CONSTRAINT FK_WISHLISTITEM_ON_PRODUCT FOREIGN KEY (product_id) REFERENCES product (id);

ALTER TABLE wishlist_item
    ADD CONSTRAINT FK_WISHLISTITEM_ON_USER FOREIGN KEY (user_id) REFERENCES users (id);

ALTER TABLE product_image_urls
    ADD CONSTRAINT fk_product_imageurls_on_product FOREIGN KEY (product_id) REFERENCES product (id);