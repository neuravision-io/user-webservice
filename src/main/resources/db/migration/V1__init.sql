-- V1__initial_migration.sql

-- Create schema if not exists
CREATE SCHEMA IF NOT EXISTS user_schema;

-- Create extension for UUID generation if not exists
CREATE EXTENSION IF NOT EXISTS "uuid-ossp";

CREATE TABLE user_schema.users (
                      id UUID PRIMARY KEY,
                      email VARCHAR(255) UNIQUE NOT NULL,
                      first_name VARCHAR(255) NOT NULL,
                      last_name VARCHAR(255) NOT NULL,
                      company_name VARCHAR(255) NOT NULL,
                      court VARCHAR(255)
);
