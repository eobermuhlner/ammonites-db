ALTER TABLE ammonite
ADD COLUMN image_id INT;

ALTER TABLE ammonite
ADD CONSTRAINT fk_ammonite_image_id
FOREIGN KEY (image_id)
REFERENCES image(id);