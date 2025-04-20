-- liquibase formatted sql

-- Счётчик срабатываний условия "да" для продукта в процессе формирования рекомендаций.
-- Запись для существующего продукта есть всегда, даже если не было срабатываний.
-- Если срабатываний не было, то счётчик равен 0.
-- Таблица содержит записи вида "один UUID продукта -> один счётчик 0..n".

-- changeSet kostusonline:affc7834-c084-5759-9db0-38ec11865a82 runOnChange:true

CREATE TABLE product_hits_counters (
    -- id записи всегда генерируется автоматически
    id BIGINT PRIMARY KEY GENERATED ALWAYS AS IDENTITY NOT NULL,
    -- Ссылка на продукт
    product_id UUID NOT NULL,
    -- счётчик срабатываний условия "да"
    hits_count BIGINT NOT NULL CHECK (hits_count >= 0),
    CONSTRAINT fk_product_id FOREIGN key (product_id) REFERENCES products (id) ON DELETE CASCADE
);

-- changeSet kostusonline:9fdd1aeb-3457-5975-8936-20d403a44b0a runOnChange:true
GRANT ALL ON product_hits_counters TO recommendations_god;

-- changeSet kostusonline:ed8240c5-8078-557a-8acc-a8f646cc4765 runOnChange:true
INSERT INTO
    product_hits_counters (product_id, hits_count)
SELECT p.id, 0
FROM
    products p
    LEFT JOIN product_hits_counters phc ON p.id = phc.product_id
WHERE
    phc.product_id IS NULL;