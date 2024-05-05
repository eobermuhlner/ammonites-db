INSERT INTO ammonite (taxonomy_subclass, taxonomy_family, taxonomy_subfamily, taxonomy_genus, taxonomy_subgenus, taxonomy_species, description, strata)
VALUES
('Perisphinctaceae', 'Perisphinctidae', 'Perisphinctinae', 'Perisphinctes', 'Dichotomoceras', 'Perisphinctes (D.) bifurcatus', '', 'ox 1 d'),
('Perisphinctaceae', 'Perisphinctidae', 'Perisphinctinae', 'Perisphinctes', 'Dichotomoceras', 'Perisphinctes (D.) crassus', '', 'ox 1 d'),
('Perisphinctaceae', 'Perisphinctidae', 'Perisphinctinae', 'Perisphinctes', 'Dichotomoceras', 'Perisphinctes (D.) bifurcatoides', '', 'ox 1 d'),
('Perisphinctaceae', 'Perisphinctidae', 'Perisphinctinae', 'Perisphinctes', 'Dichotomoceras', 'Perisphinctes (D.) microplicatilis', '', 'ox 1 c, ox 1 d'),
('Perisphinctaceae', 'Perisphinctidae', 'Perisphinctinae', 'Perisphinctes', 'Dichotomoceras', 'Perisphinctes (D.) prowitteanus', '', 'ox 1 d');

INSERT INTO measurement (diameter_side, diameter_cross, proportion_n, proportion_h, proportion_b, proportion_q, count_z, comment, ammonite_id)
SELECT 5.5, null, 40, 34, null, 1.12, 33, 'Schlegelmilch', a.id
FROM ammonite a
WHERE a.taxonomy_species = 'Perisphinctes (D.) bifurcatus';

INSERT INTO measurement (diameter_side, diameter_cross, proportion_n, proportion_h, proportion_b, proportion_q, count_z, comment, ammonite_id)
SELECT 8, null, 42, 35, null, 1.3, 42, 'Schlegelmilch', a.id
FROM ammonite a
WHERE a.taxonomy_species = 'Perisphinctes (D.) bifurcatus';

INSERT INTO measurement (diameter_side, diameter_cross, proportion_n, proportion_h, proportion_b, proportion_q, count_z, comment, ammonite_id)
SELECT 9.0, null, 47, 30, null, 0.93, 41, 'Schlegelmilch', a.id
FROM ammonite a
WHERE a.taxonomy_species = 'Perisphinctes (D.) crassus';

INSERT INTO measurement (diameter_side, diameter_cross, proportion_n, proportion_h, proportion_b, proportion_q, count_z, comment, ammonite_id)
SELECT 6.5, null, 47, 30, null, 0.80, 35, 'Schlegelmilch', a.id
FROM ammonite a
WHERE a.taxonomy_species = 'Perisphinctes (D.) crassus';

INSERT INTO measurement (diameter_side, diameter_cross, proportion_n, proportion_h, proportion_b, proportion_q, count_z, comment, ammonite_id)
SELECT 14, null, 48, 29, null, 1.56, 35, 'Schlegelmilch', a.id
FROM ammonite a
WHERE a.taxonomy_species = 'Perisphinctes (D.) bifurcatoides';

INSERT INTO measurement (diameter_side, diameter_cross, proportion_n, proportion_h, proportion_b, proportion_q, count_z, comment, ammonite_id)
SELECT 5.3, null, 42, 36, null, null, 83, 'Schlegelmilch', a.id
FROM ammonite a
WHERE a.taxonomy_species = 'Perisphinctes (D.) bifurcatoides';

INSERT INTO measurement (diameter_side, diameter_cross, proportion_n, proportion_h, proportion_b, proportion_q, count_z, comment, ammonite_id)
SELECT 7.2, null, 40, 36, null, null, 102, 'Schlegelmilch', a.id
FROM ammonite a
WHERE a.taxonomy_species = 'Perisphinctes (D.) bifurcatoides';

INSERT INTO measurement (diameter_side, diameter_cross, proportion_n, proportion_h, proportion_b, proportion_q, count_z, comment, ammonite_id)
SELECT 11.5, null, 45, 29, null, 1.61, 56, 'Schlegelmilch', a.id
FROM ammonite a
WHERE a.taxonomy_species = 'Perisphinctes (D.) bifurcatoides';

INSERT INTO measurement (diameter_side, diameter_cross, proportion_n, proportion_h, proportion_b, proportion_q, count_z, comment, ammonite_id)
SELECT 2.9, null, 38, 39, null, 1.5, 39, 'Schlegelmilch', a.id
FROM ammonite a
WHERE a.taxonomy_species = 'Perisphinctes (D.) microplicatilis';

INSERT INTO measurement (diameter_side, diameter_cross, proportion_n, proportion_h, proportion_b, proportion_q, count_z, comment, ammonite_id)
SELECT 3.7, null, 38, 35, null, 0.9, 34, 'Schlegelmilch', a.id
FROM ammonite a
WHERE a.taxonomy_species = 'Perisphinctes (D.) microplicatilis';

INSERT INTO measurement (diameter_side, diameter_cross, proportion_n, proportion_h, proportion_b, proportion_q, count_z, comment, ammonite_id)
SELECT 3.4, null, 38, 38, null, 1.1, 34, 'Schlegelmilch', a.id
FROM ammonite a
WHERE a.taxonomy_species = 'Perisphinctes (D.) microplicatilis';

INSERT INTO measurement (diameter_side, diameter_cross, proportion_n, proportion_h, proportion_b, proportion_q, count_z, comment, ammonite_id)
SELECT 2.2, null, 41, 35, null, 0.67, 29, 'Schlegelmilch', a.id
FROM ammonite a
WHERE a.taxonomy_species = 'Perisphinctes (D.) prowitteanus';
