-- 1. Eliminar la foreign key existente (primero necesitas saber su nombre)
-- Puedes obtenerlo con: SHOW CREATE TABLE academy.student_class;

ALTER TABLE academy.student_class
    DROP FOREIGN KEY student_class_ibfk_1; -- Reemplaza con el nombre real de la FK

-- 2. Eliminar el índice si existe (puede tener otro nombre)
ALTER TABLE academy.student_class
    DROP INDEX student_course_id; -- Reemplaza con el nombre real del índice

-- 3. Eliminar la columna antigua
ALTER TABLE academy.student_class
    DROP COLUMN student_course_id;

-- 4. Agregar la nueva columna
ALTER TABLE academy.student_class
    ADD COLUMN student_cmodule_id INT NOT NULL;

-- 5. Crear el nuevo índice (opcional pero recomendado)
ALTER TABLE academy.student_class
    ADD INDEX idx_student_cmodule_id (student_cmodule_id);

-- 6. Agregar la nueva foreign key
ALTER TABLE academy.student_class
    ADD CONSTRAINT fk_student_class_module
        FOREIGN KEY (student_cmodule_id)
            REFERENCES academy.student_module(id)
            ON DELETE CASCADE ON UPDATE CASCADE;
