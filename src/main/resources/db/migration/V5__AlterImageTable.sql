-- Alter the images table to add the image_url column

ALTER TABLE images
ADD COLUMN image_url VARCHAR(255);