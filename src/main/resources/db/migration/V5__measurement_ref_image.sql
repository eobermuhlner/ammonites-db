ALTER TABLE measurement
ADD COLUMN image_id INT;

ALTER TABLE measurement
ADD CONSTRAINT fk_measurement_image_id
FOREIGN KEY (image_id)
REFERENCES image(id);