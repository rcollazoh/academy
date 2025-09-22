INSERT INTO `academy`.`config_class` (
    `order_num`,
    `title`,
    `description`,
    `module_id`
)
SELECT
    1,
    'Introducción',
    'Clase introductoria del curso',
    cm.id
FROM config_module cm
WHERE cm.name = 'Módulo 0: Introducción';
