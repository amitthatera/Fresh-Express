-- Create the user seq
CREATE SEQUENCE IF NOT EXISTS user_seq START WITH 1 INCREMENT BY 1;

-- Create the user table
CREATE TABLE users (
    user_id BIGSERIAL PRIMARY KEY,
    first_name VARCHAR(255) NOT NULL,
    last_name VARCHAR(255),
    email_address VARCHAR(255) UNIQUE NOT NULL,
    contact_number VARCHAR(255) UNIQUE NOT NULL,
    password VARCHAR(255) NOT NULL,
    created_date TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    last_modified_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    is_enabled BOOLEAN DEFAULT FALSE,
    is_email_verified BOOLEAN DEFAULT FALSE,
    is_number_verified BOOLEAN DEFAULT FALSE
);

-- Create indexes
CREATE INDEX idx_user_email ON users(email_address);
CREATE INDEX idx_user_contact ON users(contact_number);

-- Create the role seq
CREATE SEQUENCE IF NOT EXISTS role_seq START WITH 1 INCREMENT BY 1;

-- Create the roles table
CREATE TABLE roles (
    role_id BIGINT NOT NULL PRIMARY KEY DEFAULT NEXTVAL('role_seq'),
    role_name VARCHAR(255),
    role_description TEXT,
    created_date TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    last_modified_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Create the user_roles join table
 CREATE TABLE user_roles (
     user_id BIGINT NOT NULL,
     role_id BIGINT NOT NULL,
     PRIMARY KEY (user_id, role_id),
     FOREIGN KEY (user_id) REFERENCES users(user_id),
     FOREIGN KEY (role_id) REFERENCES roles(role_id)
 );

 -- Create table `category`
 CREATE TABLE category (
     category_id VARCHAR(255) PRIMARY KEY,
     category_name VARCHAR(255) NOT NULL,
     category_description TEXT,
     created_date TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
     last_modified_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP
 );

 -- Create index on `category_name` for faster lookup
 CREATE INDEX idx_category_name ON category(category_name);

 -- Create the vendor seq
 CREATE SEQUENCE IF NOT EXISTS vendor_seq START WITH 1 INCREMENT BY 1;

 -- Create table `vendors`
 CREATE TABLE vendors (
     vendor_id BIGINT PRIMARY KEY DEFAULT NEXTVAL('vendor_seq'),
     vendor_name VARCHAR(255) NOT NULL,
     vendor_email VARCHAR(255) NOT NULL,
     vendor_mobile VARCHAR(255) NOT NULL,
     vendor_address VARCHAR(255) NOT NULL,
     created_date TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
     last_modified_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP
 );

 -- Create index on `vendor_name` for faster lookup
 CREATE INDEX idx_vendor_name ON vendors(vendor_name);

-- Create table `products`
 CREATE TABLE products (
     product_id VARCHAR(255) PRIMARY KEY,
     product_name VARCHAR(255) NOT NULL,
     product_description TEXT,
     product_price DECIMAL(10,2) NOT NULL,
     discount DECIMAL(5,2),
     is_available BOOLEAN,
     stock INT NOT NULL,
     created_date TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
     last_modified_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
     category_id VARCHAR(255),
     vendor_id BIGINT,
     FOREIGN KEY (category_id) REFERENCES category(category_id),
     FOREIGN KEY (vendor_id) REFERENCES vendors(vendor_id)
 );

 -- Create index on `product_name` for faster lookup
 CREATE INDEX idx_product_name ON products(product_name);