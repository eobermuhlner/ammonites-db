INSERT INTO ammonite (taxonomy_subclass, taxonomy_family, taxonomy_subfamily, taxonomy_genus, taxonomy_subgenus, taxonomy_species, description, comment, strata)
VALUES
('Perisphinctaceae', 'Perisphinctidae', 'Perisphinctinae', 'Perisphinctes', 'Microbiplices', 'Microbiplices microbiplex', '', 'Schlegelmilch, Malm, Image: 21.3', 'ox 1 f, ox 1 c?');

INSERT INTO measurement (diameter_side, diameter_cross, proportion_n, proportion_h, proportion_b, proportion_q, count_z, comment, ammonite_id)
SELECT 3.1, null, 50, 32, null, 0.8, 24, 'Schlegelmilch', a.id
FROM ammonite a
WHERE a.taxonomy_species = 'Microbiplices microbiplex';

INSERT INTO measurement (diameter_side, diameter_cross, proportion_n, proportion_h, proportion_b, proportion_q, count_z, comment, ammonite_id)
SELECT 2.9, null, 44, 31, null, 0.75, 24, 'Schlegelmilch', a.id
FROM ammonite a
WHERE a.taxonomy_species = 'Microbiplices microbiplex';

INSERT INTO measurement (diameter_side, diameter_cross, proportion_n, proportion_h, proportion_b, proportion_q, count_z, comment, ammonite_id)
SELECT 3.6, null, 46, 34, null, 0.9, 27, 'Schlegelmilch', a.id
FROM ammonite a
WHERE a.taxonomy_species = 'Microbiplices microbiplex';

INSERT INTO measurement (diameter_side, diameter_cross, proportion_n, proportion_h, proportion_b, proportion_q, count_z, comment, ammonite_id)
SELECT 5.1, null, 46, 32, null, 1.0, 26, 'Schlegelmilch', a.id
FROM ammonite a
WHERE a.taxonomy_species = 'Microbiplices microbiplex';
