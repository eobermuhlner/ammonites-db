INSERT INTO ammonite (taxonomy_subclass, taxonomy_family, taxonomy_subfamily, taxonomy_genus, taxonomy_subgenus, taxonomy_species, description, strata)
VALUES
('Perisphinctaceae', 'Perisphinctidae', 'Perisphinctinae', 'Perisphinctes', 'Larcheria', 'Larcheria gredingensis', '', 'ox 1 f'),
('Perisphinctaceae', 'Perisphinctidae', 'Perisphinctinae', 'Perisphinctes', 'Larcheria', 'Larcheria dorni', '', 'ox 1 c');

INSERT INTO measurement (diameter_side, diameter_cross, proportion_n, proportion_h, proportion_b, proportion_q, count_z, comment, ammonite_id)
SELECT 13, null, 39, 35, null, 1.8, null, 'Schlegelmilch', a.id
FROM ammonite a
WHERE a.taxonomy_species = 'Larcheria gredingensis';

INSERT INTO measurement (diameter_side, diameter_cross, proportion_n, proportion_h, proportion_b, proportion_q, count_z, comment, ammonite_id)
SELECT 10.5, null, 25, 33, null, 1.59, 39, 'Schlegelmilch', a.id
FROM ammonite a
WHERE a.taxonomy_species = 'Larcheria dorni';
