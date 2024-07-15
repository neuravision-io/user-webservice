-- Create schema if not exists
CREATE SCHEMA IF NOT EXISTS user_schema;

-- Create extension for UUID generation if not exists
CREATE EXTENSION IF NOT EXISTS "uuid-ossp";

-- Create table if not exists
DO $$
    BEGIN
        IF NOT EXISTS (SELECT 1 FROM information_schema.tables WHERE table_schema = 'user_schema' AND table_name = 'users') THEN
            CREATE TABLE user_schema.users
            (
                id           UUID PRIMARY KEY,
                email        VARCHAR(255) UNIQUE NOT NULL,
                first_name   VARCHAR(255)        NOT NULL,
                last_name    VARCHAR(255)        NOT NULL,
                company_name VARCHAR(255)        NOT NULL,
                court        VARCHAR(255),
                is_ready     BOOLEAN             NOT NULL
            );
        END IF;
    END $$;
