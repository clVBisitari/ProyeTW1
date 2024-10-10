-- Insertar el primer usuario (admin)
INSERT INTO Usuario (id, email, nombre, apellido, dni, saldo, password, esAdmin, enSuspencion)
VALUES (null, 'admin@user.com', 'Admin', 'Cero', 123456789, 91865.7, 'admin', true, false);

-- Insertar el segundo usuario (Diego)
INSERT INTO Usuario (id, email, nombre, apellido, dni, saldo, password, esAdmin, enSuspencion)
VALUES (null, 'diego@user.com', 'Diego', 'Uno', 23456789, 0.0, 'diego', false, false);

-- Insertar el tercer usuario (Mariano)
INSERT INTO Usuario (id, email, nombre, apellido, dni, saldo, password, esAdmin, enSuspencion)
VALUES (null, 'mariano@user.com', 'Mariano', 'Dos', 34567890, 0.0, 'mariano', false, false);

-- Actualizar los usuarios para relacionarlos con el primer usuario (admin)
UPDATE Usuario SET usuario_id = 1 WHERE id = 2;
UPDATE Usuario SET usuario_id = 1 WHERE id = 3;
