-- liquibase formatted sql

-- Правила для рекомендования продуктов.
-- Один продукт - одно правило. Правило состоит из набора методов-query.
-- Таблица содержит записи вида "один UUID продукта -> множество методов-query".

-- changeSet kostusonline:33fbd772-1ec6-596a-9a4d-b6f250609f3e runOnChange:true
CREATE TABLE product_rules (
    -- id записи метода всегда генерируется автоматически
    id BIGINT PRIMARY KEY GENERATED ALWAYS AS IDENTITY NOT NULL,
    -- Ссылка на продукт
    product_id UUID NOT NULL,
    -- Идентификатор метода в коде.
    query VARCHAR(128) NOT NULL CHECK (
        LENGTH(query) BETWEEN 1 AND 128
    ),
    -- Аргументы метода в виде строки "аргумент1;аргумент2;...;аргументN".
    arguments VARCHAR(512) NOT NULL CHECK (
        LENGTH(arguments) BETWEEN 1 AND 512
    ),
    -- Отрицание результата вызова метода.
    negate BOOLEAN NOT NULL,
    CONSTRAINT fk_product_id FOREIGN key (product_id) REFERENCES products (id)
);

-- changeSet kostusonline:c7c2ca59-5818-5949-8d85-f6ca8b6cf902 runOnChange:true
GRANT ALL ON product_rules TO recommendations_god;