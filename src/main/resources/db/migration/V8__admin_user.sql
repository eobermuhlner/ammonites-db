
-- Insert initial roles
INSERT INTO roles (name) VALUES ('ROLE_USER');
INSERT INTO roles (name) VALUES ('ROLE_ADMIN');

-- Insert initial admin user
INSERT INTO users (username, password, enabled) VALUES ('admin', '$2a$10$mOYPnEdvUtvDlgoEQ3vP8OA.Fe.o8VsnBXgUNoplMv6LGtQL6K5tG', true);

-- Assign admin role to admin user
INSERT INTO user_roles (user_id, role_id) VALUES (
     (SELECT id FROM users WHERE username = 'admin'),
     (SELECT id FROM roles WHERE name = 'ROLE_ADMIN')
 );