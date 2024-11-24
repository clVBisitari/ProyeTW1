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

-- ALTER TABLE proyecto_inversion ADD CONSTRAINT unique_proyecto UNIQUE (titulo, titular_id);
--
-- INSERT INTO proyecto_inversion(titulo, description, ensuspension, montorecaudado, montorequerido, motivosuspension, plazoparafinal, plazoparainicio, titular_id)
-- VALUES
--         ('Zapatillas con materiales reciclados', 'Zapatillas construidas a partir de materiales reusables', false, 123456.75, 1400000.00, null, '2026-01-01', '2024-11-22', 1 ),
--         ('App alquiler de carpas - Mendoza', 'Tenemos varios campings por todo Mendoza y alquilaremos con nuestra app', false, 145000.00, 1500000.00, '2026-01-01', null, '2024-11-22', 1 )
-- ON CONFLICT DO NOTHING;

SELECT setval(pg_get_serial_sequence('gasto', 'id'), (SELECT MAX(id) FROM gasto));