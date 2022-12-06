CREATE TABLE payment
(
    id         BIGSERIAL                   PRIMARY KEY,
    order_id   bigint                      NOT NULL,
    status     character varying(20)       NOT NULL,
    value      bigint                      NOT NULL,
    version    bigint                      NOT NULL DEFAULT 1,
    created_at timestamp without time zone NOT NULL,
    updated_at timestamp without time zone NOT NULL
);