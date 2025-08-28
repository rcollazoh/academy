
-- Insertar áreas
INSERT INTO nom_area (name, description, active)
VALUES
    ('Industrial', 'Área relacionada con procesos industriales', TRUE),
    ('Veterinaria', 'Área relacionada con la salud animal', TRUE);

-- Insertar prácticas asociadas
INSERT INTO nom_practice (area_id, name, description, active)
VALUES
    ((SELECT id FROM nom_area WHERE name = 'Industrial'), 'Industrial', 'Práctica en procesos industriales', TRUE),
    ((SELECT id FROM nom_area WHERE name = 'Veterinaria'), 'Radiodiagnóstico', 'Práctica en diagnóstico por imagen animal', TRUE),
--     ((SELECT id FROM nom_area WHERE name = 'Médica'), 'Medicina Nuclear', 'Práctica en medicina nuclear', TRUE),
    ((SELECT id FROM nom_area WHERE name = 'Médica'), 'Odontología', 'Práctica en odontología médica', TRUE),
    ((SELECT id FROM nom_area WHERE name = 'Médica'), 'Radioterapia', 'Práctica en tratamientos de radioterapia', TRUE),
    ((SELECT id FROM nom_area WHERE name = 'Médica'), 'Radiodiagnóstico', 'Práctica en diagnóstico por imagen médica', TRUE);
