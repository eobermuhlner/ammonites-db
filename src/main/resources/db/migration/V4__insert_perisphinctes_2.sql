INSERT INTO ammonite (taxonomy_subclass, taxonomy_family, taxonomy_subfamily, taxonomy_genus, taxonomy_subgenus, taxonomy_species, description, strata)
VALUES
('Perisphinctaceae', 'Perisphinctidae', 'Perisphinctinae', 'Perisphinctes', 'Properisphinctes', 'Properisphinctes gresslei', '', 'ox 1 b'),
('Perisphinctaceae', 'Perisphinctidae', 'Perisphinctinae', 'Perisphinctes', 'Properisphinctes', 'Properisphinctes sorlinensis', '', 'ox 1 b'),
('Perisphinctaceae', 'Perisphinctidae', 'Perisphinctinae', 'Perisphinctes', 'Properisphinctes', 'Properisphinctes neglectus', '', 'ox 1 b'),
('Perisphinctaceae', 'Perisphinctidae', 'Perisphinctinae', 'Perisphinctes', 'Properisphinctes', 'Properisphinctes thurmanni', '', 'ox 1 b');

INSERT INTO measurement (diameter_side, diameter_cross, proportion_n, proportion_h, proportion_b, proportion_q, count_z, comment, ammonite_id)
SELECT 4.7, null, 45, 32, null, 0.8, 50, 'Schlegelmilch', a.id
FROM ammonite a
WHERE a.taxonomy_species = 'Properisphinctes gresslei';

INSERT INTO measurement (diameter_side, diameter_cross, proportion_n, proportion_h, proportion_b, proportion_q, count_z, comment, ammonite_id)
SELECT 3.2, null, 35, 37, null, 0.9, 42, 'Schlegelmilch', a.id
FROM ammonite a
WHERE a.taxonomy_species = 'Properisphinctes sorlinensis';

INSERT INTO measurement (diameter_side, diameter_cross, proportion_n, proportion_h, proportion_b, proportion_q, count_z, comment, ammonite_id)
SELECT 5.1, null, 44, 32, null, 0.82, 52, 'Schlegelmilch', a.id
FROM ammonite a
WHERE a.taxonomy_species = 'Properisphinctes sorlinensis';

INSERT INTO measurement (diameter_side, diameter_cross, proportion_n, proportion_h, proportion_b, proportion_q, count_z, comment, ammonite_id)
SELECT 7.1, null, 47, 30, null, 0.81, 55, 'Schlegelmilch', a.id
FROM ammonite a
WHERE a.taxonomy_species = 'Properisphinctes sorlinensis';

INSERT INTO measurement (diameter_side, diameter_cross, proportion_n, proportion_h, proportion_b, proportion_q, count_z, comment, ammonite_id)
SELECT 4, null, 41, 35, null, 1.14, 52, 'Schlegelmilch', a.id
FROM ammonite a
WHERE a.taxonomy_species = 'Properisphinctes neglectus';

INSERT INTO measurement (diameter_side, diameter_cross, proportion_n, proportion_h, proportion_b, proportion_q, count_z, comment, ammonite_id)
SELECT 8, null, 36, 36, null, 1.2, 53, 'Schlegelmilch', a.id
FROM ammonite a
WHERE a.taxonomy_species = 'Properisphinctes thurmanni';
