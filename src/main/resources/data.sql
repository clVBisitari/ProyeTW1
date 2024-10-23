INSERT INTO Usuario(id, email,nombre, apellido, dni, saldo, password, esAdmin, enSuspension) VALUES(null, 'test@unlam.edu.ar','Juan','Perez',38498777,10500.0, 'test', true,false);

INSERT INTO Usuario (id, email, nombre, apellido, dni, saldo, password, esAdmin, enSuspension)
VALUES (null, 'contacto1@unlam.edu.ar', 'Diego', 'Uno', 23456789, 500.0, 'password1', false, false);

INSERT INTO Usuario (id, email, nombre, apellido, dni, saldo, password, esAdmin, enSuspension)
VALUES (null, 'contacto2@unlam.edu.ar', 'Mariano', 'Dos', 34567890, 700.0, 'password2', false, false);

INSERT INTO Usuario (id, email, nombre, apellido, dni, saldo, password, esAdmin, enSuspension)
VALUES (null, 'contacto3@unlam.edu.ar', 'Marcos', 'tres', 16558244, 8000.0, 'password3', false, false);

INSERT INTO Usuario (id, email, nombre, apellido, dni, saldo, password, esAdmin, enSuspension)
VALUES (null, 'contacto4@unlam.edu.ar', 'Pedro', 'cuatro', 38498798, 9888.0, 'password4', false, false);

INSERT INTO Usuario (id, email, nombre, apellido, dni, saldo, password, esAdmin, enSuspension)
VALUES (null, 'contacto5@unlam.edu.ar', 'Alejandro', 'cinco', 2345644, 745.0, 'password5', false, false);

INSERT INTO Usuario (id, email, nombre, apellido, dni, saldo, password, esAdmin, enSuspension)
VALUES (null, 'contacto6@unlam.edu.ar', 'Ivan', 'seis', 34567666, 7888.0, 'password6', false, false);


UPDATE Usuario SET usuario_id = 1 WHERE id = 2;
UPDATE Usuario SET usuario_id = 1 WHERE id = 3;

UPDATE Usuario SET usuario_id = 2 WHERE id = 5;
UPDATE Usuario SET usuario_id = 2 WHERE id = 7;

UPDATE Usuario SET usuario_id = 3 WHERE id = 4;
UPDATE Usuario SET usuario_id = 3 WHERE id = 6;