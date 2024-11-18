-- Insertar múltiples usuarios en una sola línea con ON CONFLICT DO NOTHING
INSERT INTO Usuario(id, email, nombre, apellido, dni, saldo, password, esAdmin, enSuspension)
VALUES
   (1,'test@unlam.edu.ar', 'Juan', 'Perez', 38498777, 10500.0, 'test', true, false),
   (2,'contacto1@unlam.edu.ar', 'Diego', 'Uno', 23456789, 500.0, 'password1', false, false),
   (3,'contacto2@unlam.edu.ar', 'Mariano', 'Dos', 34567890, 700.0, 'password2', false, false),
   (4,'contacto3@unlam.edu.ar', 'Marcos', 'Tres', 16558244, 8000.0, 'password3', false, false),
   (5,'contacto4@unlam.edu.ar', 'Pedro', 'Cuatro', 38498798, 9888.0, 'password4', false, false),
   (6,'contacto5@unlam.edu.ar', 'Alejandro', 'Cinco', 2345644, 745.0, 'password5', false, false),
   (7,'contacto6@unlam.edu.ar', 'Ivan', 'Seis', 34567666, 7888.0, 'password6', false, false)
ON CONFLICT DO NOTHING;

INSERT INTO GestorDeGastos(
	id, user_id)
	VALUES (1, 1),(2, 2),(3, 3),(4, 4),(5, 5),(6, 6),(7, 7) ON CONFLICT DO NOTHING;

INSERT INTO Gasto(
	id, descripcion, valor, gestor_id, fechaVencimiento)
	VALUES
	    (1,'Bicicleta', 230000, 1, '1/11/2024'),
	    (2,'Hamburguesa', 19700, 1, '2/10/2024'),
	    (3,'Linea celular', 16500, 1, '3/10/2024'),
	    (4,'Medialunas', 7000, 1, '4/11/2024'),
	    (5,'Viaje', 300400, 1, '5/12/2024') ON CONFLICT DO NOTHING;

SELECT setval(pg_get_serial_sequence('gasto', 'id'), (SELECT MAX(id) FROM gasto));