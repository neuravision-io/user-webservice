/*V2__add_avatars_table.sql*/
-- Create table avatars
CREATE TABLE IF NOT EXISTS user_schema.avatars
(
    id          UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
    keycloak_id VARCHAR(255) NOT NULL UNIQUE,
    image       OID          NOT NULL
);


-- Create an index on keycloak_id
CREATE UNIQUE INDEX IF NOT EXISTS idx_keycloak_id ON user_schema.avatars (keycloak_id);
