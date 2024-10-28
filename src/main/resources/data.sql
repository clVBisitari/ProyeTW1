-- Insertar usuarios sin especificar el campo 'id' para permitir autogeneración
INSERT INTO Usuario(email, nombre, apellido, dni, saldo, password, esAdmin, enSuspension)
VALUES ('test@unlam.edu.ar', 'Juan', 'Perez', 38498777, 10500.0, 'test', true, false);

INSERT INTO Usuario(email, nombre, apellido, dni, saldo, password, esAdmin, enSuspension)
VALUES ('contacto1@unlam.edu.ar', 'Diego', 'Uno', 23456789, 500.0, 'password1', false, false);

INSERT INTO Usuario(email, nombre, apellido, dni, saldo, password, esAdmin, enSuspension)
VALUES ('contacto2@unlam.edu.ar', 'Mariano', 'Dos', 34567890, 700.0, 'password2', false, false);

INSERT INTO Usuario(email, nombre, apellido, dni, saldo, password, esAdmin, enSuspension)
VALUES ('contacto3@unlam.edu.ar', 'Marcos', 'Tres', 16558244, 8000.0, 'password3', false, false);

INSERT INTO Usuario(email, nombre, apellido, dni, saldo, password, esAdmin, enSuspension)
VALUES ('contacto4@unlam.edu.ar', 'Pedro', 'Cuatro', 38498798, 9888.0, 'password4', false, false);

INSERT INTO Usuario(email, nombre, apellido, dni, saldo, password, esAdmin, enSuspension)
VALUES ('contacto5@unlam.edu.ar', 'Alejandro', 'Cinco', 2345644, 745.0, 'password5', false, false);

INSERT INTO Usuario(email, nombre, apellido, dni, saldo, password, esAdmin, enSuspension)
VALUES ('contacto6@unlam.edu.ar', 'Ivan', 'Seis', 34567666, 7888.0, 'password6', false, false);

-- Actualizar 'usuario_id' solo si ya están creados en la base de datos
UPDATE Usuario SET usuario_id = 1 WHERE email = 'contacto1@unlam.edu.ar';
UPDATE Usuario SET usuario_id = 1 WHERE email = 'contacto2@unlam.edu.ar';

UPDATE Usuario SET usuario_id = 2 WHERE email = 'contacto4@unlam.edu.ar';
UPDATE Usuario SET usuario_id = 2 WHERE email = 'contacto6@unlam.edu.ar';

UPDATE Usuario SET usuario_id = 3 WHERE email = 'contacto3@unlam.edu.ar';
UPDATE Usuario SET usuario_id = 3 WHERE email = 'contacto5@unlam.edu.ar';
