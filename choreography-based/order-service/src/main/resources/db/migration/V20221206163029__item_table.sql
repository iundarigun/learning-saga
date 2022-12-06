CREATE TABLE order_item
(
    id         BIGSERIAL                   PRIMARY KEY,
    sku        character varying(100)      NOT NULL,
    order_id   bigint                      NOT NULL,
    quantity   bigint                      NOT NULL,
    version    bigint                      NOT NULL DEFAULT 1,
    created_at timestamp without time zone NOT NULL,
    updated_at timestamp without time zone NOT NULL
);