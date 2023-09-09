INSERT INTO conceptos (id, hs_minimo, hs_maximo, laborable, nombre)
VALUES (1, 6, 8, true, 'Turno Normal');
INSERT INTO conceptos (id, hs_minimo, hs_maximo, laborable, nombre)
VALUES (2, 2, 6, true, 'Turno Extra');
INSERT INTO conceptos (id, hs_minimo, hs_maximo, laborable, nombre)
VALUES (3, null, null, false, 'Dia Libre');

-- SQL de pruebas
INSERT INTO empleados (id, nro_documento, nombre, apellido, email, fecha_nacimiento, fecha_ingreso, fecha_creacion)
VALUES (10, 35749947, 'Silvio', 'SGGiovacchini', 'silvio@gmail.com', '1991-10-10', '2022-10-10', '2023-01-10');

INSERT INTO empleados (id, nro_documento, nombre, apellido, email, fecha_nacimiento, fecha_ingreso, fecha_creacion)
VALUES (11, 40276969, 'Leandro', 'Giovacchini', 'leogiova4@gmail.com', '1998-01-26', '2022-01-01', '2021-01-10');
INSERT INTO empleados (id, nro_documento, nombre, apellido, email, fecha_nacimiento, fecha_ingreso, fecha_creacion)
VALUES (12, 38828888, 'Pablo', 'Vannella', 'pablovannelli@gmail.com', '1992-08-18', '2008-08-08', '2008-08-09');

INSERT INTO conceptos (id, hs_minimo, hs_maximo, laborable, nombre)
VALUES (4, 12, 18, true, 'Turno RecontraExtra');
