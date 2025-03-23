-- liquibase formatted sql

-- changeSet kostusonline:3c89daa5-7b10-5da8-8092-977d53e87066 runOnChange:true
CREATE TABLE IF NOT EXISTS methods (
    id SERIAL PRIMARY KEY,
    name VARCHAR(64) NOT NULL CHECK (
        LENGTH(name) BETWEEN 1 AND 64
    ),
    description VARCHAR(256) NOT NULL CHECK (
        LENGTH(description) BETWEEN 1 AND 256
    )
);

GRANT ALL ON methods TO recommendations_god;