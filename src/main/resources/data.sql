-- Insertar múltiples usuarios en una sola línea con ON CONFLICT DO NOTHING
INSERT INTO Usuario(email, nombre, apellido, dni, saldo, password,estaActivo, esAdmin, enSuspension)
VALUES
   ('test@unlam.edu.ar', 'Juan', 'Perez', 38498777, 10500.0, 'test',true, true, false),
   ('contacto1@unlam.edu.ar', 'Diego', 'Uno', 23456789, 500.0, 'password1',true, false, false),
   ('contacto2@unlam.edu.ar', 'Mariano', 'Dos', 34567890, 700.0, 'password2',true, false, false),
   ('contacto3@unlam.edu.ar', 'Marcos', 'Tres', 16558244, 8000.0, 'password3',true, false, false),
   ('contacto4@unlam.edu.ar', 'Pedro', 'Cuatro', 38498798, 9888.0, 'password4',true, false, false),
   ('contacto5@unlam.edu.ar', 'Alejandro', 'Cinco', 2345644, 745.0, 'password5',true, false, false),
   ('contacto6@unlam.edu.ar', 'Ivan', 'Seis', 34567666, 7888.0, 'password6',true, false, false)
ON CONFLICT DO NOTHING;

INSERT INTO Gasto(
	id, descripcion, valor, fechaVencimiento, usuario_id)
	VALUES
	    (1,'Bicicleta', 230000, '1/11/2024', 1),
	    (2,'Hamburguesa', 19700, '2/10/2024', 1),
	    (3,'Linea celular', 16500, '3/10/2024', 1),
	    (4,'Medialunas', 7000, '4/11/2024', 1),
	    (5,'Viaje', 300400, '5/12/2024', 1) ON CONFLICT DO NOTHING;

SELECT setval(pg_get_serial_sequence('gasto', 'id'), (SELECT MAX(id) FROM gasto));

INSERT INTO proyecto_inversion(id,
    cantidadreportes, description, ensuspension, montorecaudado, montorequerido, motivosuspension, plazoparafinal, plazoparainicio, titulo, titular_id)
VALUES
    (1, 5, 'Adquisición de un vehículo usado para realizar reparaciones exhalizar automóviles en desuso, fomentando la economía circular.',
    false, 800000, 1000000, NULL, '2024-12-31', '2024-10-26', 'Compra de auto usado para refacción y reventa', 1),

    (2, 3, 'Este proyecto consiste en la renovación y n el mercado de artículos de diseño.',
    false, 900000, 1200000, NULL, '2024-11-30', '2024-10-26', 'Renovación y reventa de muebles antiguos', 2),

    (3, 8, 'Creación y desarrollo de una innovadora aplicación móvil que facilite uitiva y funcionalidades avanzadas para el usuario.',
    false, 950000, 1500000, NULL, '2024-12-15', '2024-10-26', 'Desarrollo de una aplicación móvil', 3),

    (4, 6, 'Compra de bicicletas eléctricas para su posterior alquiler en de carbono en las ciudades.',
    false, 850000, 1300000, NULL, '2024-12-20', '2024-10-26', 'Compra y alquiler de bicicletas eléctricas', 4),

    (5, 2, 'Construcción de viviendas económicas y ecológicamente sostenibles utilizando materiales reciclados y técnicas de construcción modernatacionales accesibles para comunidades vulnerables.',
    true, 500000, 2000000, 'Falta de inversores', '2025-01-31', '2024-10-26', 'Construcción de viviendas sustentables', 5)
ON CONFLICT DO NOTHING;

SELECT setval(pg_get_serial_sequence('proyecto_inversion', 'id'), (SELECT MAX(id) FROM gasto));

-- ALTER TABLE proyecto_inversion ADD CONSTRAINT unique_proyecto UNIQUE (titulo, titular_id);
--
-- INSERT INTO proyecto_inversion(titulo, description, ensuspension, montorecaudado, montorequerido, motivosuspension, plazoparafinal, plazoparainicio, titular_id)
-- VALUES
--         ('Zapatillas con materiales reciclados', 'Zapatillas construidas a partir de materiales reusables', false, 123456.75, 1400000.00, null, '2026-01-01', '2024-11-22', 1 ),
--         ('App alquiler de carpas - Mendoza', 'Tenemos varios campings por todo Mendoza y alquilaremos con nuestra app', false, 145000.00, 1500000.00, '2026-01-01', null, '2024-11-22', 1 )
-- ON CONFLICT DO NOTHING;