INSERT INTO ammonite (taxonomy_subclass, taxonomy_family, taxonomy_subfamily, taxonomy_genus, taxonomy_subgenus, taxonomy_species, description, strata)
VALUES
('Perisphinctaceae', 'Perisphinctidae', 'Perisphinctinae', 'Perisphinctes', 'Subdistophinctes', 'Subdistophinctes lucingae', '', 'ox 1 c, ox 1 f?'),
('Perisphinctaceae', 'Perisphinctidae', 'Perisphinctinae', 'Perisphinctes', 'Subdistophinctes', 'Subdistophinctes mindowe', '', 'ox 1 c');

INSERT INTO measurement (diameter_side, diameter_cross, proportion_n, proportion_h, proportion_b, proportion_q, count_z, comment, ammonite_id)
SELECT 7.0, null, 35, 40, null, null, 82, 'Schlegelmilch', a.id
FROM ammonite a
WHERE a.taxonomy_species = 'Subdistophinctes lucingae';

INSERT INTO measurement (diameter_side, diameter_cross, proportion_n, proportion_h, proportion_b, proportion_q, count_z, comment, ammonite_id)
SELECT 3, null, 33, 40, null, 1.0, 41, 'Schlegelmilch', a.id
FROM ammonite a
WHERE a.taxonomy_species = 'Subdistophinctes lucingae';

INSERT INTO measurement (diameter_side, diameter_cross, proportion_n, proportion_h, proportion_b, proportion_q, count_z, comment, ammonite_id)
SELECT 7.2, null, 33, 40, null, 1.14, 73, 'Schlegelmilch', a.id
FROM ammonite a
WHERE a.taxonomy_species = 'Subdistophinctes lucingae';

INSERT INTO measurement (diameter_side, diameter_cross, proportion_n, proportion_h, proportion_b, proportion_q, count_z, comment, ammonite_id)
SELECT 8, null, 40, 36, null, 1.05, 77, 'Schlegelmilch', a.id
FROM ammonite a
WHERE a.taxonomy_species = 'Subdistophinctes lucingae';

INSERT INTO measurement (diameter_side, diameter_cross, proportion_n, proportion_h, proportion_b, proportion_q, count_z, comment, ammonite_id)
SELECT 8.2, null, 40, 37, null, null, 90, 'Schlegelmilch', a.id
FROM ammonite a
WHERE a.taxonomy_species = 'Subdistophinctes mindowe';

INSERT INTO measurement (diameter_side, diameter_cross, proportion_n, proportion_h, proportion_b, proportion_q, count_z, comment, ammonite_id)
SELECT 5, null, 33, 38, null, 1.3, 63, 'Schlegelmilch', a.id
FROM ammonite a
WHERE a.taxonomy_species = 'Subdistophinctes mindowe';

INSERT INTO measurement (diameter_side, diameter_cross, proportion_n, proportion_h, proportion_b, proportion_q, count_z, comment, ammonite_id)
SELECT 9, null, 38, 36, null, 1.4, 90, 'Schlegelmilch', a.id
FROM ammonite a
WHERE a.taxonomy_species = 'Subdistophinctes mindowe';

INSERT INTO measurement (diameter_side, diameter_cross, proportion_n, proportion_h, proportion_b, proportion_q, count_z, comment, ammonite_id)
SELECT 12.7, null, 40, 35, null, 1.32, 110, 'Schlegelmilch', a.id
FROM ammonite a
WHERE a.taxonomy_species = 'Subdistophinctes mindowe';
