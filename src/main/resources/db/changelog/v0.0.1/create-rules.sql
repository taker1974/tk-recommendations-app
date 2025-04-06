-- liquibase formatted sql

-- Правила для рекомендаций.
-- Одна рекомендация - одно правило. Правило состоит из набора методов-query.
-- Таблица содержит записи вида "один UUID рекомендации -> множество методов-query".

-- changeSet kostusonline:8cf3a4b2-4b2e-5c30-857c-3e32e4b41b56 runOnChange:true
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

-- changeSet kostusonline:ab6dc993-38b8-5a78-81af-8c62b541411d runOnChange:true
GRANT ALL ON rules TO recommendations_god;