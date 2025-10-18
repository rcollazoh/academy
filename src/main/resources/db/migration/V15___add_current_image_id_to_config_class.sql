-- V2025_10_17__add_current_image_id_to_student_class.sql

ALTER TABLE student_class
    ADD COLUMN current_image_id INT NOT NULL DEFAULT 0;

-- Asegura que todos los registros existentes tengan el valor 0
UPDATE student_class
SET current_image_id = 0;
