/**********RECURSOS**********/
insert into recursotipo values (default, 'virtual');
insert into recursotipo  values (default, 'fisico');
insert into recursotipo  values (default, 'sala');

insert into recursodisponible values (default, 'Activado');
insert into recursodisponible values (default, 'Desactivado');

insert into recurso values (default, 3, 1, 'Fab-Lab', 20, 'Descripcion fab-lab', 'Yachay',null);
insert into recurso values (default, 3, 1, 'Sala Samsung', 10, 'Descripcion samsung', 'Yachay',null);
insert into recurso values (default, 3, 1, 'Cafetería', 15, 'Descripcion cafetería', 'Yachay',null);

/**********SOLICITUD**********/

INSERT INTO soliciestado VALUES (1, 'pendiente');
INSERT INTO soliciestado VALUES (2, 'finalizado');
INSERT INTO soliciestado VALUES (3, 'aprobado');
INSERT INTO soliciestado VALUES (4, 'negado');

INSERT INTO solicicabecera VALUES (1, 1, 'ojon', 'ojos', '2015-07-07', '12:00:00', '15:00:00');
INSERT INTO solicicabecera VALUES (2, 1, 'ojon', 'ojos', '2015-07-07', '12:00:00', '15:00:00');
INSERT INTO solicicabecera VALUES (3, 1, 'ojon', 'ojos', '2015-07-07', '12:00:00', '15:00:00');

INSERT INTO solicidetalle VALUES (1, 1, 1, 20);
INSERT INTO solicidetalle VALUES (2, 2, 2, 200);
INSERT INTO solicidetalle VALUES (3, 3, 3, 2000);
INSERT INTO solicidetalle VALUES (4, 1, 2, 20000);

INSERT INTO recursosactivos VALUES (1, 1, '2015-07-07', '12:00:00', '15:00:00', 1);
INSERT INTO recursosactivos VALUES (2, 1, '2015-07-07', '12:00:00', '15:00:00', 1);
INSERT INTO recursosactivos VALUES (3, 1, '2015-07-07', '12:00:00', '15:00:00', 1);

