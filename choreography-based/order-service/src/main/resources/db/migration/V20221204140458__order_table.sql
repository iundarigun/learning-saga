CREATE TABLE "order"
(
    id         BIGSERIAL                   PRIMARY KEY,
    user_id    character varying(100)      NOT NULL,
    status     character varying(20)       NOT NULL,
    value      bigint                      NOT NULL,
    version    bigint                      NOT NULL DEFAULT 1,
    created_at timestamp without time zone NOT NULL,
    updated_at timestamp without time zone NOT NULL
);