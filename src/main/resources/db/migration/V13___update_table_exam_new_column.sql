-- Vx__update_exam_table.sql

-- Eliminar columna obsoleta
ALTER TABLE config_exam
DROP COLUMN recourse_url;

-- Agregar columna para mínimo de preguntas correctas
ALTER TABLE config_exam
    ADD COLUMN min_questions INT NOT NULL DEFAULT 0;

-- Agregar columna para duración del examen en minutos
ALTER TABLE config_exam
    ADD COLUMN duration_minutes INT NOT NULL DEFAULT 0;