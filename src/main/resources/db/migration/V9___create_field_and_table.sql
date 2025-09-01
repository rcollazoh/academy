ALTER TABLE config_exam
    ADD COLUMN title VARCHAR(255) NOT NULL AFTER id;

ALTER TABLE config_class
    ADD COLUMN order_num int NOT NULL AFTER id;

CREATE TABLE student_exam
(
    id         INT AUTO_INCREMENT PRIMARY KEY,
    config_exam_id INT NOT NULL,
    student_module_id INT NOT NULL,
    viewed BOOLEAN NOT NULL DEFAULT FALSE,
    status VARCHAR(20) DEFAULT 'PENDING',
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

-- 2. Crear índice (recomendado para claves foráneas)
ALTER TABLE student_exam
    ADD INDEX idx_config_exam_id (config_exam_id);

-- 3. Agregar la clave foránea
ALTER TABLE student_exam
    ADD CONSTRAINT fk_student_exam_config_exam
        FOREIGN KEY (config_exam_id)
            REFERENCES config_exam (id)
            ON DELETE CASCADE
            ON UPDATE CASCADE;

-- 2. Crear índice (recomendado para claves foráneas)
ALTER TABLE student_exam
    ADD INDEX idx_student_module_id (student_module_id);

-- 3. Agregar la clave foránea
ALTER TABLE student_exam
    ADD CONSTRAINT fk_student_exam_module
        FOREIGN KEY (student_module_id)
            REFERENCES student_module(id)
            ON DELETE CASCADE
            ON UPDATE CASCADE;

ALTER TABLE config_exam
    ADD COLUMN recourse_url VARCHAR(255);
