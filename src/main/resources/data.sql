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
	id, descripcion, valor, fechaVencimiento)
	VALUES
	    (1,'Bicicleta', 230000, '1/11/2024'),
	    (2,'Hamburguesa', 19700, '2/10/2024'),
	    (3,'Linea celular', 16500, '3/10/2024'),
	    (4,'Medialunas', 7000, '4/11/2024'),
	    (5,'Viaje', 300400, '5/12/2024') ON CONFLICT DO NOTHING;

SELECT setval(pg_get_serial_sequence('gasto', 'id'), (SELECT MAX(id) FROM gasto));