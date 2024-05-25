CREATE TABLE users (
   id BIGINT AUTO_INCREMENT PRIMARY KEY,
   username VARCHAR(50) UNIQUE NOT NULL,
   password VARCHAR(100) NOT NULL,
   email VARCHAR(100) UNIQUE NOT NULL,
   first_name VARCHAR(50),
   last_name VARCHAR(50),
   enabled BOOLEAN NOT NULL
);

CREATE TABLE roles (
   id BIGINT AUTO_INCREMENT PRIMARY KEY,
   name VARCHAR(50) UNIQUE NOT NULL
);

CREATE TABLE user_roles (
    user_id BIGINT NOT NULL,
    role_id BIGINT NOT NULL,
    PRIMARY KEY (user_id, role_id),
    FOREIGN KEY (user_id) REFERENCES users(id),
    FOREIGN KEY (role_id) REFERENCES roles(id)
);
