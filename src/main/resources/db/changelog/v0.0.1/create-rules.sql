-- liquibase formatted sql

-- changeSet kostusonline:2b0d39c2-846b-5470-bad7-d74bb6804b3c runOnChange:true
CREATE TABLE rules (
    id BIGINT PRIMARY KEY GENERATED ALWAYS AS IDENTITY NOT NULL,
    recommendation_id UUID NOT NULL,
    query VARCHAR(128) NOT NULL CHECK (
        LENGTH(query) BETWEEN 1 AND 128
    ),
    arguments VARCHAR(512) NOT NULL CHECK (
        LENGTH(arguments) BETWEEN 1 AND 512
    ),
    negate BOOLEAN NOT NULL,
    CONSTRAINT fk_rule_sets_id FOREIGN key (rule_sets_id) REFERENCES rule_sets (id)
);

GRANT ALL ON rules TO recommendations_god;
