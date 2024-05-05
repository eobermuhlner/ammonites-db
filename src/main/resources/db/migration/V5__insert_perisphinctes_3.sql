INSERT INTO ammonite (taxonomy_subclass, taxonomy_family, taxonomy_subfamily, taxonomy_genus, taxonomy_subgenus, taxonomy_species, description, strata)
VALUES
('Perisphinctaceae', 'Perisphinctidae', 'Perisphinctinae', 'Perisphinctes', 'Dichotomosphinctes', 'Perisphinctes (D.) bouranensis', '', 'ox 1 b'),
('Perisphinctaceae', 'Perisphinctidae', 'Perisphinctinae', 'Perisphinctes', 'Dichotomosphinctes', 'Perisphinctes (D.) montfalconensis', '', 'ox 1 c'),
('Perisphinctaceae', 'Perisphinctidae', 'Perisphinctinae', 'Perisphinctes', 'Dichotomosphinctes', 'Perisphinctes (D.) wartae', '', 'ox 1 b, ox 1 c'),
('Perisphinctaceae', 'Perisphinctidae', 'Perisphinctinae', 'Perisphinctes', 'Dichotomosphinctes', 'Perisphinctes (D.) antecedens', '', 'ox 1 c, ox 1 d'),
('Perisphinctaceae', 'Perisphinctidae', 'Perisphinctinae', 'Perisphinctes', 'Dichotomosphinctes', 'Perisphinctes (D.) vermicularis', '', 'ox 1 c');

INSERT INTO measurement (diameter_side, diameter_cross, proportion_n, proportion_h, proportion_b, proportion_q, count_z, comment, ammonite_id)
SELECT 9, null, 47, 33, null, 1.65, 70, 'Schlegelmilch', a.id
FROM ammonite a
WHERE a.taxonomy_species = 'Perisphinctes (D.) bouranensis';

INSERT INTO measurement (diameter_side, diameter_cross, proportion_n, proportion_h, proportion_b, proportion_q, count_z, comment, ammonite_id)
SELECT 6, null, 48, 28, null, 1, 46, 'Schlegelmilch', a.id
FROM ammonite a
WHERE a.taxonomy_species = 'Perisphinctes (D.) montfalconensis';

INSERT INTO measurement (diameter_side, diameter_cross, proportion_n, proportion_h, proportion_b, proportion_q, count_z, comment, ammonite_id)
SELECT 2.5, null, 45, 33, null, 0.75, 46, 'Schlegelmilch', a.id
FROM ammonite a
WHERE a.taxonomy_species = 'Perisphinctes (D.) montfalconensis';

INSERT INTO measurement (diameter_side, diameter_cross, proportion_n, proportion_h, proportion_b, proportion_q, count_z, comment, ammonite_id)
SELECT 5.5, null, 43, 31, null, 1.04, 47, 'Schlegelmilch', a.id
FROM ammonite a
WHERE a.taxonomy_species = 'Perisphinctes (D.) montfalconensis';

INSERT INTO measurement (diameter_side, diameter_cross, proportion_n, proportion_h, proportion_b, proportion_q, count_z, comment, ammonite_id)
SELECT 16, null, 51, 27, null, 1.56, 80, 'Schlegelmilch', a.id
FROM ammonite a
WHERE a.taxonomy_species = 'Perisphinctes (D.) wartae';

INSERT INTO measurement (diameter_side, diameter_cross, proportion_n, proportion_h, proportion_b, proportion_q, count_z, comment, ammonite_id)
SELECT 10, null, 50, 27, null, 1.3, 63, 'Schlegelmilch', a.id
FROM ammonite a
WHERE a.taxonomy_species = 'Perisphinctes (D.) wartae';

INSERT INTO measurement (diameter_side, diameter_cross, proportion_n, proportion_h, proportion_b, proportion_q, count_z, comment, ammonite_id)
SELECT 13.5, null, 50, 27, null, 1.43, 69, 'Schlegelmilch', a.id
FROM ammonite a
WHERE a.taxonomy_species = 'Perisphinctes (D.) wartae';

INSERT INTO measurement (diameter_side, diameter_cross, proportion_n, proportion_h, proportion_b, proportion_q, count_z, comment, ammonite_id)
SELECT 13, null, 55, 25, null, 1.04, 58, 'Schlegelmilch', a.id
FROM ammonite a
WHERE a.taxonomy_species = 'Perisphinctes (D.) antecedens';

INSERT INTO measurement (diameter_side, diameter_cross, proportion_n, proportion_h, proportion_b, proportion_q, count_z, comment, ammonite_id)
SELECT 10, null, 50, 29, null, 1.21, 52, 'Schlegelmilch', a.id
FROM ammonite a
WHERE a.taxonomy_species = 'Perisphinctes (D.) antecedens';

INSERT INTO measurement (diameter_side, diameter_cross, proportion_n, proportion_h, proportion_b, proportion_q, count_z, comment, ammonite_id)
SELECT 13, null, 52, 26, null, 1.24, 50, 'Schlegelmilch', a.id
FROM ammonite a
WHERE a.taxonomy_species = 'Perisphinctes (D.) antecedens';

INSERT INTO measurement (diameter_side, diameter_cross, proportion_n, proportion_h, proportion_b, proportion_q, count_z, comment, ammonite_id)
SELECT 5, null, 50, 26, null, 0.72, 45, 'Schlegelmilch', a.id
FROM ammonite a
WHERE a.taxonomy_species = 'Perisphinctes (D.) vermicularis';

INSERT INTO measurement (diameter_side, diameter_cross, proportion_n, proportion_h, proportion_b, proportion_q, count_z, comment, ammonite_id)
SELECT 6, null, 53, 24, null, 1.0, 50, 'Schlegelmilch', a.id
FROM ammonite a
WHERE a.taxonomy_species = 'Perisphinctes (D.) vermicularis';
