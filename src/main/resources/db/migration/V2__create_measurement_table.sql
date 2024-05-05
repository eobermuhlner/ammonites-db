CREATE TABLE measurement (
    id INT AUTO_INCREMENT PRIMARY KEY,
    diameter_side DOUBLE,
    diameter_cross DOUBLE,
    proportion_n DOUBLE,
    proportion_h DOUBLE,
    proportion_b DOUBLE,
    proportion_q DOUBLE,
    count_z DOUBLE,
    comment TEXT,
    ammonite_id INT,
    FOREIGN KEY (ammonite_id) REFERENCES ammonite(id)
);