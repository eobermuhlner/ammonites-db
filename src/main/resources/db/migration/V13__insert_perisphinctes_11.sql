INSERT INTO ammonite (taxonomy_subclass, taxonomy_family, taxonomy_subfamily, taxonomy_genus, taxonomy_subgenus, taxonomy_species, description, comment, strata)
VALUES
('Perisphinctaceae', 'Perisphinctidae', 'Perisphinctinae', 'Perisphinctes', 'Orthosphinctes', 'Orthosphinctes (O.) polygyratus', '', 'Schlegelmilch, Malm, Image: 21.9', 'ox 1 f?, ox 2 a, ox 2 b, ki 1 a'),
('Perisphinctaceae', 'Perisphinctidae', 'Perisphinctinae', 'Perisphinctes', 'Orthosphinctes', 'Orthosphinctes (O.) laufensis', '', 'Schlegelmilch, Malm, Image: 22.1', 'ox 1 f, ox 2 a, ox 2 b'),
('Perisphinctaceae', 'Perisphinctidae', 'Perisphinctinae', 'Perisphinctes', 'Orthosphinctes', 'Orthosphinctes (O.) virgulatus', '', 'Schlegelmilch, Malm, Image: 22.2', 'ox 2 a, ox 2 b'),
('Perisphinctaceae', 'Perisphinctidae', 'Perisphinctinae', 'Perisphinctes', 'Orthosphinctes', 'Orthosphinctes (O.) wemodingensis', '', 'Schlegelmilch, Malm, Image: 22.3', 'ox 2 a, ox 2 b, ki 1 a'),
('Perisphinctaceae', 'Perisphinctidae', 'Perisphinctinae', 'Perisphinctes', 'Orthosphinctes', 'Orthosphinctes (O.) tizianiformis', '', 'Schlegelmilch, Malm, Image: 22.4', 'ox 1 f, ox 2 a, ox 2 b, ki 1 a'),
('Perisphinctaceae', 'Perisphinctidae', 'Perisphinctinae', 'Perisphinctes', 'Orthosphinctes', 'Orthosphinctes (O.) freybergi', '', 'Schlegelmilch, Malm, Image: 22.5', 'ox 2 b, ki 1 a'),
('Perisphinctaceae', 'Perisphinctidae', 'Perisphinctinae', 'Perisphinctes', 'Orthosphinctes', 'Orthosphinctes (O.) suevicus', '', 'Schlegelmilch, Malm, Image: 23.1', 'ox 2 a, ox 2 b');

INSERT INTO measurement (diameter_side, diameter_cross, proportion_n, proportion_h, proportion_b, proportion_q, count_z, comment, ammonite_id)
SELECT 10.7, null, 50, 31, null, null, 140, 'Schlegelmilch', a.id
FROM ammonite a
WHERE a.taxonomy_species = 'Orthosphinctes (O.) polygyratus';

INSERT INTO measurement (diameter_side, diameter_cross, proportion_n, proportion_h, proportion_b, proportion_q, count_z, comment, ammonite_id)
SELECT 4, null, 45, 32, null, null, 85, 'Schlegelmilch', a.id
FROM ammonite a
WHERE a.taxonomy_species = 'Orthosphinctes (O.) polygyratus';

INSERT INTO measurement (diameter_side, diameter_cross, proportion_n, proportion_h, proportion_b, proportion_q, count_z, comment, ammonite_id)
SELECT 6, null, 46, 31, null, null, 96, 'Schlegelmilch', a.id
FROM ammonite a
WHERE a.taxonomy_species = 'Orthosphinctes (O.) polygyratus';

INSERT INTO measurement (diameter_side, diameter_cross, proportion_n, proportion_h, proportion_b, proportion_q, count_z, comment, ammonite_id)
SELECT 8, null, 47, 30, null, 1.02, 110, 'Schlegelmilch', a.id
FROM ammonite a
WHERE a.taxonomy_species = 'Orthosphinctes (O.) polygyratus';

INSERT INTO measurement (diameter_side, diameter_cross, proportion_n, proportion_h, proportion_b, proportion_q, count_z, comment, ammonite_id)
SELECT 10, null, 48, 30, null, 1.37, 118, 'Schlegelmilch', a.id
FROM ammonite a
WHERE a.taxonomy_species = 'Orthosphinctes (O.) polygyratus';

INSERT INTO measurement (diameter_side, diameter_cross, proportion_n, proportion_h, proportion_b, proportion_q, count_z, comment, ammonite_id)
SELECT 12, null, 48, 30, null, 2.0, 138, 'Schlegelmilch', a.id
FROM ammonite a
WHERE a.taxonomy_species = 'Orthosphinctes (O.) polygyratus';

INSERT INTO measurement (diameter_side, diameter_cross, proportion_n, proportion_h, proportion_b, proportion_q, count_z, comment, ammonite_id)
SELECT 7.7, null, 43, 30, null, 1.6, 50, 'Schlegelmilch', a.id
FROM ammonite a
WHERE a.taxonomy_species = 'Orthosphinctes (O.) laufensis';

INSERT INTO measurement (diameter_side, diameter_cross, proportion_n, proportion_h, proportion_b, proportion_q, count_z, comment, ammonite_id)
SELECT 3.3, null, 35, 41, null, 1.27, null, 'Schlegelmilch', a.id
FROM ammonite a
WHERE a.taxonomy_species = 'Orthosphinctes (O.) virgulatus';

INSERT INTO measurement (diameter_side, diameter_cross, proportion_n, proportion_h, proportion_b, proportion_q, count_z, comment, ammonite_id)
SELECT 10, null, 48, 27.2, null, 1.5, 102, 'Schlegelmilch', a.id
FROM ammonite a
WHERE a.taxonomy_species = 'Orthosphinctes (O.) wemodingensis';

INSERT INTO measurement (diameter_side, diameter_cross, proportion_n, proportion_h, proportion_b, proportion_q, count_z, comment, ammonite_id)
SELECT 10, null, 48, 28.5, null, null, 44, 'Schlegelmilch', a.id
FROM ammonite a
WHERE a.taxonomy_species = 'Orthosphinctes (O.) wemodingensis';

INSERT INTO measurement (diameter_side, diameter_cross, proportion_n, proportion_h, proportion_b, proportion_q, count_z, comment, ammonite_id)
SELECT 10, null, 47, 31, null, 1.3, 143, 'Schlegelmilch', a.id
FROM ammonite a
WHERE a.taxonomy_species = 'Orthosphinctes (O.) tizianiformis';

INSERT INTO measurement (diameter_side, diameter_cross, proportion_n, proportion_h, proportion_b, proportion_q, count_z, comment, ammonite_id)
SELECT 6, null, 42, 34, null, null, 111, 'Schlegelmilch', a.id
FROM ammonite a
WHERE a.taxonomy_species = 'Orthosphinctes (O.) tizianiformis';

INSERT INTO measurement (diameter_side, diameter_cross, proportion_n, proportion_h, proportion_b, proportion_q, count_z, comment, ammonite_id)
SELECT 8, null, 45, 33, null, null, 134, 'Schlegelmilch', a.id
FROM ammonite a
WHERE a.taxonomy_species = 'Orthosphinctes (O.) tizianiformis';

INSERT INTO measurement (diameter_side, diameter_cross, proportion_n, proportion_h, proportion_b, proportion_q, count_z, comment, ammonite_id)
SELECT 11, null, 42, 33, null, null, 136, 'Schlegelmilch', a.id
FROM ammonite a
WHERE a.taxonomy_species = 'Orthosphinctes (O.) freybergi';

INSERT INTO measurement (diameter_side, diameter_cross, proportion_n, proportion_h, proportion_b, proportion_q, count_z, comment, ammonite_id)
SELECT 6, null, 36.5, 38, null, null, 59, 'Schlegelmilch', a.id
FROM ammonite a
WHERE a.taxonomy_species = 'Orthosphinctes (O.) freybergi';

INSERT INTO measurement (diameter_side, diameter_cross, proportion_n, proportion_h, proportion_b, proportion_q, count_z, comment, ammonite_id)
SELECT 8, null, 40, 33.5, null, null, 72, 'Schlegelmilch', a.id
FROM ammonite a
WHERE a.taxonomy_species = 'Orthosphinctes (O.) freybergi';

INSERT INTO measurement (diameter_side, diameter_cross, proportion_n, proportion_h, proportion_b, proportion_q, count_z, comment, ammonite_id)
SELECT 13, null, 45.5, 32.5, null, null, 87, 'Schlegelmilch', a.id
FROM ammonite a
WHERE a.taxonomy_species = 'Orthosphinctes (O.) freybergi';

INSERT INTO measurement (diameter_side, diameter_cross, proportion_n, proportion_h, proportion_b, proportion_q, count_z, comment, ammonite_id)
SELECT 11.5, null, 47, 30, null, 1.25, 39, 'Schlegelmilch', a.id
FROM ammonite a
WHERE a.taxonomy_species = 'Orthosphinctes (O.) suevicus';

INSERT INTO measurement (diameter_side, diameter_cross, proportion_n, proportion_h, proportion_b, proportion_q, count_z, comment, ammonite_id)
SELECT 15.5, null, 47, 29, null, 1.26, 140, 'Schlegelmilch', a.id
FROM ammonite a
WHERE a.taxonomy_species = 'Orthosphinctes (O.) suevicus';
