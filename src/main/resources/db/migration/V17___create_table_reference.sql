-- INSERT INTO `academy`.`config_module` (`name`, `description`, `order_num`, `is_common`, `course_id`) VALUES ('Referencias', 'Referencias del modulo', '6', '0', '6');

CREATE TABLE config_reference (
                                  id INT AUTO_INCREMENT PRIMARY KEY,
                                  order_num INT,
                                  title VARCHAR(150),
                                  link TINYTEXT,
                                  module_id INT,
                                  created_at DATETIME,
                                  updated_at DATETIME,
                                  INDEX idx_module_id (module_id),
                                  CONSTRAINT fk_config_reference_module
                                      FOREIGN KEY (module_id)
                                          REFERENCES config_module(id)
                                          ON DELETE SET NULL
                                          ON UPDATE CASCADE
);

INSERT INTO config_reference (order_num, title, link, module_id, created_at, updated_at) VALUES
                                                                                             (1, 'Ministerio de Salud. Trámites e información en materia de Protección y Seguridad Radiológica', 'https://www.ministeriodesalud.go.cr/index.php/tramites/empresas?view=article&id=2105&catid=36', 37, NOW(), NOW()),
                                                                                             (2, 'Protección radiológica y seguridad en medicina veterinaria. OIEA.', 'https://www.iaea.org/publications/13481/radiation-protection-and-safety-in-veterinary-medicine', 37, NOW(), NOW()),
                                                                                             (3, 'Protección radiológica en la práctica veterinaria. ICRP 153.', 'https://www.icrp.org/publication.asp?id=ICRP%20Publication%20153', 37, NOW(), NOW()),
                                                                                             (4, 'Protección radiológica en medicina. ICRP 105.', 'https://www.icrp.org/publication.asp?id=ICRP%20Publication%20105', 37, NOW(), NOW()),
                                                                                             (5, 'Normas básicas internacionales de seguridad. GSR Part 3. OIEA.', 'https://www.iaea.org/publications/8930/radiation-protection-and-safety-of-radiation-sources-international-basic-safety-standards', 37, NOW(), NOW()),
                                                                                             (6, 'Protección radiológica y seguridad en los usos médicos de la radiación ionizante. SSG-46. OIEA.', 'https://www.iaea.org/publications/11102/radiation-protection-and-safety-in-medical-uses-of-ionizing-radiation', 37, NOW(), NOW()),
                                                                                             (7, 'Preparación y respuesta para casos de emergencia nuclear o radiológica', 'https://www.iaea.org/es/publications/11004/preparacion-y-respuesta-para-casos-de-emergencia-nuclear-o-radiologica', 37, NOW(), NOW()),
                                                                                             (8, 'Cultura de la seguridad en las organizaciones, instalaciones y actividades vinculadas al uso de fuentes de radiación ionizante. TECDOC-1995. OIEA.', 'https://www.iaea.org/es/publications/15071/cultura-de-la-seguridad-en-las-organizaciones-instalaciones-y-actividades-vinculadas-al-uso-de-fuentes-de-radiacion-ionizante', 37, NOW(), NOW()),
                                                                                             (9, 'Liderazgo y gestión en pro de la seguridad. GSR Part 2. OIEA.', 'https://www.iaea.org/publications/11070/leadership-and-management-for-safety', 37, NOW(), NOW());
