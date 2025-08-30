CREATE TABLE config_parameter (
                                  id INT PRIMARY KEY AUTO_INCREMENT,
                                  name VARCHAR(150) NOT NULL,
                                  value VARCHAR(255),
                                  value_json TEXT,
                                  description TEXT,
                                  editable BOOLEAN DEFAULT TRUE,
                                  deletable BOOLEAN DEFAULT TRUE,
                                  created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                                  updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
                                  is_price BOOLEAN DEFAULT FALSE
);

-- Insert initial parameters
INSERT INTO config_parameter (
    name, value, value_json, description, editable, deletable, created_at, updated_at, is_price
) VALUES
      (
          'USUARIO_CORREO_EMISOR',
          'pradacademy.cr@gmail.com',
          NULL,
          'Sender email address for system notifications',
          TRUE,
          TRUE,
          CURRENT_TIMESTAMP,
          CURRENT_TIMESTAMP,
          FALSE
      ),
      (
          'CLAVE_APP_CORREO_EMISOR',
          'bjso xndv eqzc vrdc',
          NULL,
          'App password for sender email account',
          TRUE,
          TRUE,
          CURRENT_TIMESTAMP,
          CURRENT_TIMESTAMP,
          FALSE
      );
