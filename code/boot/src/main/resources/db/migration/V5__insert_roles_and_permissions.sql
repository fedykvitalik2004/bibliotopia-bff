INSERT INTO permissions (name) VALUES
('books:create'),
('books:read'),
('books:update'),
('books:delete');

INSERT INTO roles (name) VALUES
('Admin'),
('Customer');

INSERT INTO role_permissions (role_id, permission_id) VALUES
(1, 1), -- Admin -> books:create
(1, 2), -- Admin -> books:read
(1, 3), -- Admin -> books:update
(1, 4); -- Admin -> books:delete

INSERT INTO role_permissions (role_id, permission_id) VALUES
(2, 2); -- Customer -> books:read