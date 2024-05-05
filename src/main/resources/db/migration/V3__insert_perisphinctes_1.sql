INSERT INTO ammonite (taxonomy_subclass, taxonomy_family, taxonomy_subfamily, taxonomy_genus, taxonomy_subgenus, taxonomy_species, description, strata)
VALUES
('Perisphinctaceae', 'Perisphinctidae', 'Perisphinctinae', 'Perisphinctes', 'Alligaticeras', 'Alligaticeras consociatum', '', 'ox 1 a, ox 1 b'),
('Perisphinctaceae', 'Perisphinctidae', 'Perisphinctinae', 'Perisphinctes', 'Alligaticeras', 'Alligaticeras mairei', '', 'ox 1 a, ox 1 b'),
('Perisphinctaceae', 'Perisphinctidae', 'Perisphinctinae', 'Perisphinctes', 'Properisphinctes', 'Properisphinctes bernensis', '', 'ox 1 b'),
('Perisphinctaceae', 'Perisphinctidae', 'Perisphinctinae', 'Perisphinctes', 'Properisphinctes', 'Properisphinctes hermonis', '', 'ox 1 b');

INSERT INTO measurement (diameter_side, diameter_cross, proportion_n, proportion_h, proportion_b, proportion_q, count_z, comment, ammonite_id)
SELECT 10.8, null, 44, 31, null, 1.07, 57, 'Schlegelmilch', a.id
FROM ammonite a
WHERE a.taxonomy_species = 'Alligaticeras consociatum';

INSERT INTO measurement (diameter_side, diameter_cross, proportion_n, proportion_h, proportion_b, proportion_q, count_z, comment, ammonite_id)
SELECT 9.3, null, 41, 33, null, 1.07, 57, 'Schlegelmilch', a.id
FROM ammonite a
WHERE a.taxonomy_species = 'Alligaticeras consociatum';

INSERT INTO measurement (diameter_side, diameter_cross, proportion_n, proportion_h, proportion_b, proportion_q, count_z, comment, ammonite_id)
SELECT 2, null, 40, 34, null, 1.1, 60, 'Schlegelmilch', a.id
FROM ammonite a
WHERE a.taxonomy_species = 'Alligaticeras mairei';

INSERT INTO measurement (diameter_side, diameter_cross, proportion_n, proportion_h, proportion_b, proportion_q, count_z, comment, ammonite_id)
SELECT 3.5, null, 43, 31, null, 1.1, 52, 'Schlegelmilch', a.id
FROM ammonite a
WHERE a.taxonomy_species = 'Alligaticeras mairei';

INSERT INTO measurement (diameter_side, diameter_cross, proportion_n, proportion_h, proportion_b, proportion_q, count_z, comment, ammonite_id)
SELECT 5.5, null, 47, 31, null, 1.4, 55, 'Schlegelmilch', a.id
FROM ammonite a
WHERE a.taxonomy_species = 'Alligaticeras mairei';

INSERT INTO measurement (diameter_side, diameter_cross, proportion_n, proportion_h, proportion_b, proportion_q, count_z, comment, ammonite_id)
SELECT 2.5, null, 47, 28, null, 0.75, 32, 'Schlegelmilch', a.id
FROM ammonite a
WHERE a.taxonomy_species = 'Properisphinctes bernensis';

INSERT INTO measurement (diameter_side, diameter_cross, proportion_n, proportion_h, proportion_b, proportion_q, count_z, comment, ammonite_id)
SELECT 1.6, null, 42, 31, null, 0.56, 31, 'Schlegelmilch', a.id
FROM ammonite a
WHERE a.taxonomy_species = 'Properisphinctes bernensis';

INSERT INTO measurement (diameter_side, diameter_cross, proportion_n, proportion_h, proportion_b, proportion_q, count_z, comment, ammonite_id)
SELECT 3, null, 47, 32, null, 0.7, 45, 'Schlegelmilch', a.id
FROM ammonite a
WHERE a.taxonomy_species = 'Properisphinctes bernensis';

INSERT INTO measurement (diameter_side, diameter_cross, proportion_n, proportion_h, proportion_b, proportion_q, count_z, comment, ammonite_id)
SELECT 4, null, 44, 38, null, 0.86, 49, 'Schlegelmilch', a.id
FROM ammonite a
WHERE a.taxonomy_species = 'Properisphinctes bernensis';

