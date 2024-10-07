INSERT INTO Usuario(id, email, password, esAdmin) VALUES(null, 'test@unlam.edu.ar', 'test', true);

INSERT INTO Usuario (id, email, nombre, apellido, dni, saldo, password, esAdmin, enSuspencion)
VALUES (null, 'contacto1@unlam.edu.ar', 'Contacto', 'Uno', 23456789, 0.0, 'password1', false, false);

INSERT INTO Usuario (id, email, nombre, apellido, dni, saldo, password, esAdmin, enSuspencion)
VALUES (null, 'contacto2@unlam.edu.ar', 'Contacto', 'Dos', 34567890, 0.0, 'password2', false, false);


UPDATE Usuario SET usuario_id = 1 WHERE id = 2;
UPDATE Usuario SET usuario_id = 1 WHERE id = 3;