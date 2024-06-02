-- Create the user seq
CREATE SEQUENCE IF NOT EXISTS user_seq START WITH 1 INCREMENT BY 1;

-- Create the user table
CREATE TABLE users (
    user_id BIGINT PRIMARY KEY DEFAULT NEXTVAL('user_seq'),
    first_name VARCHAR(255) NOT NULL,
    last_name VARCHAR(255),
    email_address VARCHAR(255) UNIQUE NOT NULL,
    contact_number VARCHAR(255) UNIQUE NOT NULL,
    password VARCHAR(255) NOT NULL,
    is_enabled BOOLEAN DEFAULT FALSE,
    is_email_verified BOOLEAN DEFAULT FALSE,
    is_number_verified BOOLEAN DEFAULT FALSE,
    created_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL,
    last_modified_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL
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
    created_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL,
    last_modified_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL
);

-- Create the user_roles join table
 CREATE TABLE user_roles (
     user_id BIGINT NOT NULL,
     role_id BIGINT NOT NULL,
     PRIMARY KEY (user_id, role_id),
     FOREIGN KEY (user_id) REFERENCES users(user_id),
     FOREIGN KEY (role_id) REFERENCES roles(role_id)
 );

-- Create the category seq
 CREATE SEQUENCE IF NOT EXISTS category_seq START WITH 1 INCREMENT BY 1;

 -- Create table `category`
 CREATE TABLE category (
     category_id BIGINT PRIMARY KEY DEFAULT NEXTVAL('category_seq'),
     category_uuid VARCHAR(255) NOT NULL UNIQUE,
     category_name VARCHAR(255) NOT NULL,
     category_description TEXT,
     created_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL,
     last_modified_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL
 );

 -- Create index on `category_name` for faster lookup
 CREATE INDEX idx_category_name ON category(category_name);
 CREATE INDEX idx_category_uuid ON category(category_uuid);

-- Create the address seq
 CREATE SEQUENCE address_seq START WITH 1 INCREMENT BY 1;

-- Create the address table
 CREATE TABLE address (
     address_id BIGINT PRIMARY KEY DEFAULT NEXTVAL('address_seq'),
     house_number VARCHAR(255) NOT NULL,
     street VARCHAR(255) NOT NULL,
     landmark VARCHAR(255) NOT NULL,
     city VARCHAR(255) NOT NULL,
     state VARCHAR(255) NOT NULL,
     postal_code INT NOT NULL,
     created_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL,
     last_modified_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL
 );

-- Create the vendor seq
 CREATE SEQUENCE vendor_seq START WITH 1 INCREMENT BY 1;

-- Create the vendor table
 CREATE TABLE vendors (
     vendor_id BIGINT PRIMARY KEY DEFAULT NEXTVAL('vendor_seq'),
     vendor_name VARCHAR(255) NOT NULL,
     vendor_email VARCHAR(255) NOT NULL,
     vendor_mobile VARCHAR(255) NOT NULL,
     address_id BIGINT NOT NULL,
     created_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL,
     last_modified_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL,
     CONSTRAINT fk_vendor_address FOREIGN KEY (address_id) REFERENCES address(address_id)
 );

 -- Create index on `vendor_name` for faster lookup
 CREATE INDEX idx_vendor_name ON vendors(vendor_name);

 -- Create the product seq
 CREATE SEQUENCE IF NOT EXISTS product_seq START WITH 1 INCREMENT BY 1;

 -- Create the product table
 CREATE TABLE products (
     product_id BIGINT PRIMARY KEY DEFAULT NEXTVAL('product_seq'),
     product_name VARCHAR(255) NOT NULL,
     product_description TEXT,
     product_uuid VARCHAR(255) NOT NULL UNIQUE,
     product_price DOUBLE PRECISION NOT NULL,
     discount DOUBLE PRECISION,
     discounted_price DOUBLE PRECISION,
     is_available BOOLEAN DEFAULT TRUE,
     stock INT NOT NULL,
     category_id BIGINT,
     vendor_id BIGINT,
     created_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL,
     last_modified_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL,
     FOREIGN KEY (category_id) REFERENCES category(category_id),
     FOREIGN KEY (vendor_id) REFERENCES vendors(vendor_id)
 );

 -- Create index on `product_name` for faster lookup
 CREATE INDEX idx_product_name ON products(product_name);
 CREATE INDEX idx_product_uuid ON products(product_uuid);

 -- Create the image seq
 CREATE SEQUENCE img_seq START WITH 1 INCREMENT BY 1;

 -- Create the image table
 CREATE TABLE images (
     image_id BIGINT PRIMARY KEY DEFAULT NEXTVAL('img_seq'),
     image_name VARCHAR(255),
     image_size BIGINT,
     image_type VARCHAR(255),
     product_id BIGINT,
     created_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL,
     last_modified_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL,
     CONSTRAINT fk_image_product FOREIGN KEY (product_id) REFERENCES products(product_id)
 );

