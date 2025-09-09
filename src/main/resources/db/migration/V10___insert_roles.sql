INSERT INTO academy.role (name, short_description, long_description, date_created, date_modified)
VALUES
    ('student', 'Student', 'Role assigned to users who take courses on the platform.', NOW(), NOW()),
    ('teacher', 'Teacher', 'Role assigned to users who teach courses on the platform.', NOW(), NOW()),
    ('admin', 'Administrator', 'Role with full access to manage the platform.', NOW(), NOW());
