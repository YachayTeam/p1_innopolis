/**********RECURSOS**********/
insert into recursotipo values (default, 'virtual');
insert into recursotipo  values (default, 'fisico');
insert into recursotipo  values (default, 'sala');

insert into recursodisponible values (1, 'Activado');
insert into recursodisponible values (2, 'Desactivado');

insert into recurso values (default, 3, 1, 'Fab-Lab', 20, 'Descripcion fab-lab', 'Yachay',null);
insert into recurso values (default, 3, 1, 'Sala Samsung', 10, 'Descripcion samsung', 'Yachay',null);
insert into recurso values (default, 3, 1, 'Cafetería', 15, 'Descripcion cafetería', 'Yachay',null);

/**********Servicios Virtuales**********/

insert into tiposervicio values (default, 'Global Entrepreur Program');
insert into tiposervicio  values (default, 'Bluemix');
insert into tiposervicio  values (default, 'IaaS para Emprendedores');

insert into tipoestado values (default, 'Pendiente');
insert into tipoestado values (default, 'Aprobado');
insert into tipoestado values (default, 'Negado');

insert into serviciosvirtregis values (default, 1, 1, 1002646659, 'luis', 'correa', 'luiscorrea1988@hotmail.com','estudiosnsdjsndjsndjsndjsndsj',' No Notificado');


/**********SOLICITUD**********/

INSERT INTO soliciestado VALUES (1, 'pendiente');
INSERT INTO soliciestado VALUES (2, 'finalizado');
INSERT INTO soliciestado VALUES (3, 'aprobado');
INSERT INTO soliciestado VALUES (4, 'negado');

INSERT INTO solicicabecera VALUES (1, 1, 'ojon', 'ojos', 'ojos', 'ojos', '2015-07-07', '12:00', '15:00','no notificado');
INSERT INTO solicicabecera VALUES (2, 1, 'ojon', 'ojos', 'ojos', 'ojos', '2015-07-07', '12:00', '15:00','no notificado');
INSERT INTO solicicabecera VALUES (3, 1, 'ojon', 'ojos', 'ojos', 'ojos', '2015-07-07', '12:00', '15:00','no notificado');

INSERT INTO solicidetalle VALUES (default, 1, 1, 20);
INSERT INTO solicidetalle VALUES (default, 2, 2, 200);
INSERT INTO solicidetalle VALUES (default, 3, 3, 2000);
INSERT INTO solicidetalle VALUES (default, 1, 2, 20000);

INSERT INTO recursosactivos VALUES (default, 1, '2015-07-07', '12:00', '15:00', 1);
INSERT INTO recursosactivos VALUES (default, 1, '2015-07-07', '12:00', '15:00', 1);
INSERT INTO recursosactivos VALUES (default, 1, '2015-07-07', '12:00', '15:00', 1);

update contadores set valor=4 where id_contador=1;


/**********Usuarios**********/

insert into tipoestadousr values (default, 'Activado');
insert into tipoestadousr values (default, 'Desactivado');

insert into tipologin values (default, 'administrador','Administrador');
insert into tipologin values (default, 'emprendedor','emprende');
insert into tipologin values (default, 'aprobador','Aprueba');
insert into tipologin values (default, 'general','General');

insert into usuario values (1, 1, 'root', 'root', 'root@admin.com', 'root', '2a2313a13dbf376a71cd2fd3f585f2b6');/*admin.12345*/
insert into tipousr values (default, 1, 1);
