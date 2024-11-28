-- USUARIOS
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

-- GASTOS
INSERT INTO Gasto(
	id, descripcion, valor, fechaVencimiento, usuario_id)
	VALUES
	    (1,'Bicicleta', 230000, '1/11/2024', 1),
	    (2,'Hamburguesa', 19700, '2/10/2024', 1),
	    (3,'Linea celular', 16500, '3/10/2024', 1),
	    (4,'Medialunas', 7000, '4/11/2024', 1),
	    (5,'Viaje', 300400, '5/12/2024', 1) ON CONFLICT DO NOTHING;

SELECT setval(pg_get_serial_sequence('gasto', 'id'), (SELECT MAX(id) FROM gasto));

-- PROYECTOS
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

    (5, 2, 'Construcción de viviendas accesibles para comunidades vulnerables.',
    true, 500000, 2000000, 'Falta de inversores', '2025-01-31', '2024-10-26', 'Construcción de viviendas sustentables', 5)
ON CONFLICT DO NOTHING;

SELECT setval(pg_get_serial_sequence('proyecto_inversion', 'id'), (SELECT MAX(id) FROM proyecto_inversion));

-- INVERSIONES

INSERT INTO public.inversor_proyecto(
    id, monto, id_proyecto, id_usuario)
VALUES
    (1, 150000, 1, 1),
    (2, 120000, 1, 2),
    (3, 180000, 2, 3),
    (4, 90000, 2, 4),
    (5, 200000, 3, 5),
    (6, 130000, 3, 6),
    (7, 110000, 4, 7),
    (8, 95000, 4, 1),
    (9, 125000, 5, 2),
    (10, 145000, 5, 3),
    (11, 175000, 1, 4),
    (12, 105000, 2, 5),
    (13, 90000, 3, 6),
    (14, 135000, 4, 7),
    (15, 115000, 5, 1),
    (16, 155000, 1, 2),
    (17, 85000, 2, 3),
    (18, 185000, 3, 4),
    (19, 95000, 4, 5),
    (20, 125000, 5, 6),
    (21, 145000, 1, 7)
ON CONFLICT DO NOTHING;
SELECT setval(pg_get_serial_sequence('inversor_proyecto', 'id'), (SELECT MAX(id) FROM inversor_proyecto));