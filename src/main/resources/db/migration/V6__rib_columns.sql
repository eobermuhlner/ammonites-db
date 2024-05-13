ALTER TABLE measurement RENAME COLUMN count_z TO count_primary_ribs;

ALTER TABLE measurement ADD COLUMN count_secondary_ribs DOUBLE;

ALTER TABLE measurement ADD COLUMN rib_division_ratio DOUBLE;
