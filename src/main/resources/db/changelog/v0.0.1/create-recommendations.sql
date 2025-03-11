-- liquibase formatted sql

-- changeSet kostusonline:2e07d3ea-10b7-53c0-8a5d-142b7f9ab117 runOnChange:true
CREATE TABLE IF NOT EXISTS recommendations (
    id UUID PRIMARY KEY,
    name VARCHAR(128) NOT NULL CHECK (
        LENGTH(name) BETWEEN 1 AND 128
    ),
    description VARCHAR(4096) NOT NULL CHECK (
        LENGTH(description) BETWEEN 1 AND 4096
    )
);

GRANT ALL ON recommendations TO recommendations_god;