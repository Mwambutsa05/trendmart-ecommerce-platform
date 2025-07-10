CREATE SEQUENCE IF NOT EXISTS otp_verification_seq START WITH 1 INCREMENT BY 50;

CREATE TABLE otp_verification
(
    id          BIGINT NOT NULL,
    email       VARCHAR(255),
    code        VARCHAR(255),
    expiry_date TIMESTAMP WITHOUT TIME ZONE,
    used        BOOLEAN,
    CONSTRAINT pk_otp_verification PRIMARY KEY (id)
);

ALTER TABLE users
    ADD verified BOOLEAN;

ALTER TABLE users
    ALTER COLUMN verified SET NOT NULL;