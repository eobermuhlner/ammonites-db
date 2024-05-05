INSERT INTO ammonite (taxonomy_subclass, taxonomy_family, taxonomy_subfamily, taxonomy_genus, taxonomy_subgenus, taxonomy_species, description, comment, strata)
VALUES
('Perisphinctaceae', 'Perisphinctidae', 'Perisphinctinae', 'Perisphinctes', 'Perisphinctes', 'Perisphinctes (P.) martelli', '', 'Schlegelmilch, Malm, Image: 19.2', 'ox 1 c'),
('Perisphinctaceae', 'Perisphinctidae', 'Perisphinctinae', 'Perisphinctes', 'Perisphinctes', 'Perisphinctes (P.) siegfriedi', '', 'Schlegelmilch, Malm, Image: 19.3', 'ox 1 c'),
('Perisphinctaceae', 'Perisphinctidae', 'Perisphinctinae', 'Perisphinctes', 'Perisphinctes', 'Perisphinctes (P.) panthieri', '', 'Schlegelmilch, Malm, Image: 19.4', 'ox 1 c'),
('Perisphinctaceae', 'Perisphinctidae', 'Perisphinctinae', 'Perisphinctes', 'Perisphinctes', 'Perisphinctes (P.) waltonensis', '', 'Schlegelmilch, Malm, Image: 20.1', 'ox 1 f'),
('Perisphinctaceae', 'Perisphinctidae', 'Perisphinctinae', 'Perisphinctes', 'Perisphinctes', 'Perisphinctes (P.) chloroolithicus', '', 'Schlegelmilch, Malm, Image: 20.2', 'ox 1 c, ox 1 d');

INSERT INTO measurement (diameter_side, diameter_cross, proportion_n, proportion_h, proportion_b, proportion_q, count_z, comment, ammonite_id)
SELECT 28, null, 60, 24, null, 0.81, 30, 'Schlegelmilch', a.id
FROM ammonite a
WHERE a.taxonomy_species = 'Perisphinctes (P.) martelli';

INSERT INTO measurement (diameter_side, diameter_cross, proportion_n, proportion_h, proportion_b, proportion_q, count_z, comment, ammonite_id)
SELECT 26, null, 54, 25, null, null, 49, 'Schlegelmilch', a.id
FROM ammonite a
WHERE a.taxonomy_species = 'Perisphinctes (P.) siegfriedi';

INSERT INTO measurement (diameter_side, diameter_cross, proportion_n, proportion_h, proportion_b, proportion_q, count_z, comment, ammonite_id)
SELECT 29, null, 53, 28, null, 1.13, 42, 'Schlegelmilch', a.id
FROM ammonite a
WHERE a.taxonomy_species = 'Perisphinctes (P.) panthieri';

INSERT INTO measurement (diameter_side, diameter_cross, proportion_n, proportion_h, proportion_b, proportion_q, count_z, comment, ammonite_id)
SELECT 32, null, 53, 28, null, 1.02, 41, 'Schlegelmilch', a.id
FROM ammonite a
WHERE a.taxonomy_species = 'Perisphinctes (P.) panthieri';

INSERT INTO measurement (diameter_side, diameter_cross, proportion_n, proportion_h, proportion_b, proportion_q, count_z, comment, ammonite_id)
SELECT 27, null, 53, 25, null, null, 31, 'Schlegelmilch', a.id
FROM ammonite a
WHERE a.taxonomy_species = 'Perisphinctes (P.) waltonensis';

INSERT INTO measurement (diameter_side, diameter_cross, proportion_n, proportion_h, proportion_b, proportion_q, count_z, comment, ammonite_id)
SELECT 38, null, 57, 24, null, 1.2, 36, 'Schlegelmilch', a.id
FROM ammonite a
WHERE a.taxonomy_species = 'Perisphinctes (P.) waltonensis';

INSERT INTO measurement (diameter_side, diameter_cross, proportion_n, proportion_h, proportion_b, proportion_q, count_z, comment, ammonite_id)
SELECT 8.8, null, 45, 34, null, 1.21, 52, 'Schlegelmilch', a.id
FROM ammonite a
WHERE a.taxonomy_species = 'Perisphinctes (P.) chloroolithicus';

INSERT INTO measurement (diameter_side, diameter_cross, proportion_n, proportion_h, proportion_b, proportion_q, count_z, comment, ammonite_id)
SELECT 20, null, 53, 27, null, 0.96, 60, 'Schlegelmilch', a.id
FROM ammonite a
WHERE a.taxonomy_species = 'Perisphinctes (P.) chloroolithicus';

INSERT INTO measurement (diameter_side, diameter_cross, proportion_n, proportion_h, proportion_b, proportion_q, count_z, comment, ammonite_id)
SELECT 44, null, 59, 24, null, 0.63, 12, 'Schlegelmilch', a.id
FROM ammonite a
WHERE a.taxonomy_species = 'Perisphinctes (P.) chloroolithicus';
