-- liquibase formatted sql
-- changeSet kostusonline:2b0d39c2-846b-5470-bad7-d74bb6804b3c runOnChange:true

-- Правила для рекомендаций.
-- Одна рекомендация - одно правило. Правило состоит из набора методов-query.
-- Таблица содержит записи вида "один UUID рекомендации -> множество методов-query".
CREATE TABLE rules (
    -- id записи метода всегда генерируется автоматически
    id BIGINT PRIMARY KEY GENERATED ALWAYS AS IDENTITY NOT NULL,
    -- Ссылка на рекомендованный продукт
    recommendation_id UUID NOT NULL,
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
    CONSTRAINT fk_recommendation_id FOREIGN key (recommendation_id) REFERENCES recommendations (id)
);

GRANT ALL ON rules TO recommendations_god;