
-- Alter the category table to add the image_id column
ALTER TABLE category
ADD COLUMN image_id BIGINT,
ADD CONSTRAINT fk_image
FOREIGN KEY (image_id) REFERENCES images(image_id);

-- Alter the sub_category table to add the image_id column
ALTER TABLE sub_category
ADD COLUMN image_id BIGINT,
ADD CONSTRAINT fk_image
FOREIGN KEY (image_id) REFERENCES images(image_id);