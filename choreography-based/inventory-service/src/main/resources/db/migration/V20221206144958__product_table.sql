CREATE TABLE product
(
    id         BIGSERIAL                   PRIMARY KEY,
    sku        character varying(20)       NOT NULL,
    quantity   bigint                      NOT NULL,
    version    bigint                      NOT NULL DEFAULT 1,
    created_at timestamp without time zone NOT NULL,
    updated_at timestamp without time zone NOT NULL
);