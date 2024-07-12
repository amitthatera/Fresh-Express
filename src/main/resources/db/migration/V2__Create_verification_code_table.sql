-- Create table for ActivationCode entity
CREATE TABLE activation_code (
        id BIGSERIAL PRIMARY KEY,
        token VARCHAR(255) UNIQUE NOT NULL,
        created_at TIMESTAMP NOT NULL,
        expires_at TIMESTAMP NOT NULL,
        validated_at TIMESTAMP,
        user_id BIGINT NOT NULL,
        CONSTRAINT fk_user FOREIGN KEY (user_id) REFERENCES "users"(user_id) ON DELETE CASCADE
);