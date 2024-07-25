 CREATE SEQUENCE IF NOT EXISTS sub_category_seq START WITH 1 INCREMENT BY 1;

 -- Create table `category`
 CREATE TABLE sub_category (
     sub_category_id BIGINT PRIMARY KEY DEFAULT NEXTVAL('category_seq'),
     sub_category_uuid VARCHAR(255) NOT NULL UNIQUE,
     sub_category_name VARCHAR(255) NOT NULL,
     sub_category_description TEXT,
     category_id BIGINT,
     created_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL,
     last_modified_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL,
     FOREIGN KEY (category_id) REFERENCES  category(category_id)
 );

-- Alter the products table to remove the category_id column
ALTER TABLE products
DROP COLUMN category_id;

-- Alter the products table to add the sub_category_id column
ALTER TABLE products
ADD COLUMN sub_category_id BIGINT,
ADD CONSTRAINT fk_sub_category
FOREIGN KEY (sub_category_id) REFERENCES sub_category(sub_category_id);