INSERT INTO ammonite (taxonomy_subclass, taxonomy_family, taxonomy_subfamily, taxonomy_genus, taxonomy_subgenus, taxonomy_species, description, comment, strata)
VALUES
('Haplocerataceae', 'Haploceratidae', 'Taramelliceratinae', 'Creniceras', null, 'Creniceras lophotum', '', 'Schlegelmilch, Malm, Image: 12.9', 'ox 1 c, ox 1 d, ox 1 e, ox 1 f'),
('Haplocerataceae', 'Haploceratidae', 'Taramelliceratinae', 'Creniceras', null, 'Creniceras dentatum', '', 'Schlegelmilch, Malm, Image: 12.10', 'ki 1 c, ki 1 d, ki 2 a, ki 2 b');

INSERT INTO measurement (diameter_side, diameter_cross, proportion_n, proportion_h, proportion_b, proportion_q, count_z, comment, ammonite_id)
SELECT 1.2, null, 28, 42, null, 2.1, null, 'Schlegelmilch', a.id
FROM ammonite a
WHERE a.taxonomy_species = 'Creniceras lophotum';

INSERT INTO measurement (diameter_side, diameter_cross, proportion_n, proportion_h, proportion_b, proportion_q, count_z, comment, ammonite_id)
SELECT 1.8, null, 26, 45, null, 2.3, null, 'Schlegelmilch', a.id
FROM ammonite a
WHERE a.taxonomy_species = 'Creniceras lophotum';

INSERT INTO measurement (diameter_side, diameter_cross, proportion_n, proportion_h, proportion_b, proportion_q, count_z, comment, ammonite_id)
SELECT 1.0, null, 15, 55, null, 1.72, null, 'Schlegelmilch', a.id
FROM ammonite a
WHERE a.taxonomy_species = 'Creniceras dentatum';

INSERT INTO measurement (diameter_side, diameter_cross, proportion_n, proportion_h, proportion_b, proportion_q, count_z, comment, ammonite_id)
SELECT 1.5, null, 13, 56, null, 2.06, null, 'Schlegelmilch', a.id
FROM ammonite a
WHERE a.taxonomy_species = 'Creniceras dentatum';

INSERT INTO measurement (diameter_side, diameter_cross, proportion_n, proportion_h, proportion_b, proportion_q, count_z, comment, ammonite_id)
SELECT 2.0, null, 21, 44, null, 1.64, null, 'Schlegelmilch', a.id
FROM ammonite a
WHERE a.taxonomy_species = 'Creniceras dentatum';

INSERT INTO measurement (diameter_side, diameter_cross, proportion_n, proportion_h, proportion_b, proportion_q, count_z, comment, ammonite_id)
SELECT 2.2, null, 23, 42, null, 1.62, null, 'Schlegelmilch', a.id
FROM ammonite a
WHERE a.taxonomy_species = 'Creniceras dentatum';
