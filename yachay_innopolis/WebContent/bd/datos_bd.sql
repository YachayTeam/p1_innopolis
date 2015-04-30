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

INSERT INTO soliciestado VALUES (default, 'pendiente');
INSERT INTO soliciestado VALUES (default, 'finalizado');
INSERT INTO soliciestado VALUES (default, 'aprobado');
INSERT INTO soliciestado VALUES (default, 'negado');

INSERT INTO solicicabecera VALUES (default, 1, 'ojon', 'ojos', 'ojos', 'ojos', '2015-07-07', '12:00', '15:00');
INSERT INTO solicicabecera VALUES (default, 1, 'ojon', 'ojos', 'ojos', 'ojos', '2015-07-07', '12:00', '15:00');
INSERT INTO solicicabecera VALUES (default, 1, 'ojon', 'ojos', 'ojos', 'ojos', '2015-07-07', '12:00', '15:00');

INSERT INTO solicidetalle VALUES (default, 1, 1, 20);
INSERT INTO solicidetalle VALUES (default, 2, 2, 200);
INSERT INTO solicidetalle VALUES (default, 3, 3, 2000);
INSERT INTO solicidetalle VALUES (default, 1, 2, 20000);

INSERT INTO recursosactivos VALUES (default, 1, '2015-07-07', '12:00', '15:00', 1);
INSERT INTO recursosactivos VALUES (default, 1, '2015-07-07', '12:00', '15:00', 1);
INSERT INTO recursosactivos VALUES (default, 1, '2015-07-07', '12:00', '15:00', 1);

