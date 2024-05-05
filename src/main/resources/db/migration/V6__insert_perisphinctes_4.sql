INSERT INTO ammonite (taxonomy_subclass, taxonomy_family, taxonomy_subfamily, taxonomy_genus, taxonomy_subgenus, taxonomy_species, description, comment, strata)
VALUES
('Perisphinctaceae', 'Perisphinctidae', 'Perisphinctinae', 'Perisphinctes', 'Kranaosphinctes', 'Perisphinctes (K.) plicatilis', '', 'Schlegelmilch, Malm, Image: 18.2', 'ox 1 c'),
('Perisphinctaceae', 'Perisphinctidae', 'Perisphinctinae', 'Perisphinctes', 'Kranaosphinctes', 'Perisphinctes (K.) elisabethae', '', 'Schlegelmilch, Malm, Image: 18.3', 'ox 1 c'),
('Perisphinctaceae', 'Perisphinctidae', 'Perisphinctinae', 'Perisphinctes', 'Kranaosphinctes', 'Perisphinctes (K.) gigantoplex', '', 'Schlegelmilch, Malm, Image: 18.4', 'ox 1 f?'),
('Perisphinctaceae', 'Perisphinctidae', 'Perisphinctinae', 'Perisphinctes', 'Kranaosphinctes', 'Perisphinctes (K.) berlieri', '', 'Schlegelmilch, Malm, Image: 19.1', 'ox 1 f, ox 2 a');

INSERT INTO measurement (diameter_side, diameter_cross, proportion_n, proportion_h, proportion_b, proportion_q, count_z, comment, ammonite_id)
SELECT 10, null, 41, 34, null, 1.21, 68, 'Schlegelmilch', a.id
FROM ammonite a
WHERE a.taxonomy_species = 'Perisphinctes (K.) plicatilis';

INSERT INTO measurement (diameter_side, diameter_cross, proportion_n, proportion_h, proportion_b, proportion_q, count_z, comment, ammonite_id)
SELECT 5.0, null, 36, 38, null, 1.02, 49, 'Schlegelmilch', a.id
FROM ammonite a
WHERE a.taxonomy_species = 'Perisphinctes (K.) plicatilis';

INSERT INTO measurement (diameter_side, diameter_cross, proportion_n, proportion_h, proportion_b, proportion_q, count_z, comment, ammonite_id)
SELECT 20, null, 55, 25, null, 1.14, 69, 'Schlegelmilch', a.id
FROM ammonite a
WHERE a.taxonomy_species = 'Perisphinctes (K.) plicatilis';

INSERT INTO measurement (diameter_side, diameter_cross, proportion_n, proportion_h, proportion_b, proportion_q, count_z, comment, ammonite_id)
SELECT 32, null, 55, 29, null, 1.3, 59, 'Schlegelmilch', a.id
FROM ammonite a
WHERE a.taxonomy_species = 'Perisphinctes (K.) plicatilis';

INSERT INTO measurement (diameter_side, diameter_cross, proportion_n, proportion_h, proportion_b, proportion_q, count_z, comment, ammonite_id)
SELECT 8.5, null, 38, 35, null, 1.3, 65, 'Schlegelmilch', a.id
FROM ammonite a
WHERE a.taxonomy_species = 'Perisphinctes (K.) elisabethae';

INSERT INTO measurement (diameter_side, diameter_cross, proportion_n, proportion_h, proportion_b, proportion_q, count_z, comment, ammonite_id)
SELECT 8, null, 45, 32, null, 1.35, 68, 'Schlegelmilch', a.id
FROM ammonite a
WHERE a.taxonomy_species = 'Perisphinctes (K.) elisabethae';

INSERT INTO measurement (diameter_side, diameter_cross, proportion_n, proportion_h, proportion_b, proportion_q, count_z, comment, ammonite_id)
SELECT 11, null, 46, 31, null, 1.4, 77, 'Schlegelmilch', a.id
FROM ammonite a
WHERE a.taxonomy_species = 'Perisphinctes (K.) elisabethae';

INSERT INTO measurement (diameter_side, diameter_cross, proportion_n, proportion_h, proportion_b, proportion_q, count_z, comment, ammonite_id)
SELECT 46, null, 58, 22, null, 0.8, 21, 'Schlegelmilch', a.id
FROM ammonite a
WHERE a.taxonomy_species = 'Perisphinctes (K.) gigantoplex';

INSERT INTO measurement (diameter_side, diameter_cross, proportion_n, proportion_h, proportion_b, proportion_q, count_z, comment, ammonite_id)
SELECT 13, null, 41, 34, null, 1.39, 48, 'Schlegelmilch', a.id
FROM ammonite a
WHERE a.taxonomy_species = 'Perisphinctes (K.) berlieri';
