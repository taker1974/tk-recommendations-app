--liquibase formatted sql

-- Счётчик срабатываний условия "да" для продукта в процессе формирования рекомендаций.
-- Запись для существующего продукта есть всегда, даже если не было срабатываний.
-- Если срабатываний не было, то счётчик равен 0.
-- Таблица содержит записи вида "один UUID продукта -> один счётчик 0..n".

--changeSet kostusonline:f3ee3d64-c5f9-595b-9931-55b2aae69999 runOnChange:true
CREATE TABLE IF NOT EXISTS product_hits_counters (
    -- id записи всегда генерируется автоматически
    id BIGINT PRIMARY KEY GENERATED ALWAYS AS IDENTITY NOT NULL,
    -- Ссылка на продукт
    product_id UUID NOT NULL,
    -- счётчик срабатываний условия "да"
    hits_count BIGINT NOT NULL CHECK (hits_count >= 0),
    CONSTRAINT fk_product_id FOREIGN key (product_id) REFERENCES products (id) ON DELETE CASCADE
);

--changeSet kostusonline:9fdd1aeb-3457-5975-8936-20d403a44b0a runOnChange:true
GRANT ALL ON product_hits_counters TO recommendations_god;

--changeSet kostusonline:ed8240c5-8078-557a-8acc-a8f646cc4765 runOnChange:true
INSERT INTO
    product_hits_counters (product_id, hits_count)
SELECT p.id, 0
FROM
    products p
    LEFT JOIN product_hits_counters phc ON p.id = phc.product_id
WHERE
    phc.product_id IS NULL;

-- Для PostgreSQL
--changeSet kostusonline:e6943abe-98c3-5350-bf0f-54af6e3feeb1 runOnChange:true
--preconditions onFail:MARK_RAN
--precondition-sql-check expectedResult:0 SELECT COUNT(*) FROM information_schema.table_constraints WHERE table_name = 'product_hits_counters' AND constraint_name = 'unique_product_id'
ALTER TABLE product_hits_counters
ADD CONSTRAINT unique_product_id UNIQUE (product_id);