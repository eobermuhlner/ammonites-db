INSERT INTO ammonite (taxonomy_subclass, taxonomy_family, taxonomy_subfamily, taxonomy_genus, taxonomy_subgenus, taxonomy_species, description, strata)
VALUES
('Perisphinctaceae', 'Perisphinctidae', 'Perisphinctinae', 'Perisphinctes', 'Neomorphoceras', 'Neomorphoceras chapuisi', '', 'ox 1 c'),
('Perisphinctaceae', 'Perisphinctidae', 'Perisphinctinae', 'Perisphinctes', 'Neomorphoceras', 'Neomorphoceras colinii', '', 'ox 1 c');

INSERT INTO measurement (diameter_side, diameter_cross, proportion_n, proportion_h, proportion_b, proportion_q, count_z, comment, ammonite_id)
SELECT 1.3, null, 44, 29, null, 0.6, null, 'Schlegelmilch', a.id
FROM ammonite a
WHERE a.taxonomy_species = 'Neomorphoceras chapuisi';

INSERT INTO measurement (diameter_side, diameter_cross, proportion_n, proportion_h, proportion_b, proportion_q, count_z, comment, ammonite_id)
SELECT 3.3, null, 51, 24, null, 1.14, null, 'Schlegelmilch', a.id
FROM ammonite a
WHERE a.taxonomy_species = 'Neomorphoceras colinii';
