CREATE TABLE student_feedback (
                                  id INT AUTO_INCREMENT PRIMARY KEY,
                                  module_id INT NOT NULL,
                                  learning_question varchar(100),
                                  duration_question varchar(100),
                                  satisfaction_question varchar(100),
                                  error_obs TEXT,
                                  consideration_obs TEXT,
                                  created_at DATETIME DEFAULT CURRENT_TIMESTAMP,
                                  updated_at DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
                                  CONSTRAINT fk_module FOREIGN KEY (module_id)
                                      REFERENCES student_module(id)
                                      ON DELETE CASCADE
                                      ON UPDATE CASCADE
);

CREATE TABLE student_feedback_module (
                                         id INT AUTO_INCREMENT PRIMARY KEY,
                                         feedback_id INT NOT NULL,
                                         module_id INT NOT NULL,
                                         evaluation varchar(100),
                                         question varchar(100),
                                         created_at DATETIME DEFAULT CURRENT_TIMESTAMP,
                                         updated_at DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
                                         CONSTRAINT fk_feedback_ref FOREIGN KEY (feedback_id)
                                             REFERENCES student_feedback(id)
                                             ON DELETE CASCADE
                                             ON UPDATE CASCADE,
                                         CONSTRAINT fk_module_ref FOREIGN KEY (module_id)
                                             REFERENCES config_module(id)
                                             ON DELETE CASCADE
                                             ON UPDATE CASCADE
);
