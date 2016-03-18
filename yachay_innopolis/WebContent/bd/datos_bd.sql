/**********RECURSOS*********
insert into recursotipo values (1, 'virtual');
insert into recursotipo  values (2,'fisico');
insert into recursotipo  values (3, 'sala');
*/
insert into recursodisponible values (1, 'Activado');
insert into recursodisponible values (2, 'Desactivado');

insert into saladisponible values (1, 'Activado');
insert into saladisponible values (2, 'Desactivado');
/*
insert into recurso values (default, 3, 1, 'Fab-Lab', 20, 'Descripcion fab-lab', 'Yachay',null);
insert into recurso values (default, 3, 1, 'Sala Samsung', 10, 'Descripcion samsung', 'Yachay',null);
insert into recurso values (default, 3, 1, 'Cafetería', 15, 'Descripcion cafetería', 'Yachay',null);
*/
/**********Servicios Virtuales**********/

insert into estadotiposervicio values (1, 'Activado');
insert into estadotiposervicio values (2, 'Desactivado');

insert into tiposervicio values (default, 1,'Global Entrepreur Program','https://www-304.ibm.com/partnerworld/wps/servlet/ContentHandler/isv_com_smp_startup');
insert into tiposervicio  values (default, 1,'Bluemix','http://www.ibm.com/cloud-computing/bluemix/');
insert into tiposervicio  values (default, 1,'IaaS para Emprendedores','http://www.softlayer.com/catalyst');

insert into tipoestado values (1, 'Pendiente');
insert into tipoestado values (2, 'Aprobado');
insert into tipoestado values (3, 'Negado');

/*insert into serviciosvirtregis values (default, 1, 1, 1002646659, 'luis', 'correa', 'luiscorrea1988@hotmail.com','estudiosnsdjsndjsndjsndjsndsj','No Notificado');
*/

/**********SOLICITUD**********/

INSERT INTO soliciestado VALUES (1, 'pendiente');
INSERT INTO soliciestado VALUES (2, 'finalizado');
INSERT INTO soliciestado VALUES (3, 'aprobado');
INSERT INTO soliciestado VALUES (4, 'negado');

/*INSERT INTO solicicabecera VALUES (1, 1, 'ojon', 'ojos', 'ojos', 'ojos', '2015-07-07', '12:00', '15:00','no notificado');
INSERT INTO solicicabecera VALUES (2, 1, 'ojon', 'ojos', 'ojos', 'ojos', '2015-07-07', '12:00', '15:00','no notificado');
INSERT INTO solicicabecera VALUES (3, 1, 'ojon', 'ojos', 'ojos', 'ojos', '2015-07-07', '12:00', '15:00','no notificado');

INSERT INTO solicidetalle VALUES (default, 1, 1, 20);
INSERT INTO solicidetalle VALUES (default, 2, 2, 200);
INSERT INTO solicidetalle VALUES (default, 3, 3, 2000);
INSERT INTO solicidetalle VALUES (default, 1, 2, 20000);

INSERT INTO recursosactivos VALUES (default, 1, '2015-07-07', '12:00', '15:00', 1);
INSERT INTO recursosactivos VALUES (default, 1, '2015-07-07', '12:00', '15:00', 1);
INSERT INTO recursosactivos VALUES (default, 1, '2015-07-07', '12:00', '15:00', 1);

update contadores set valor=4 where id_contador=1;*/

/**********Colores Eventos**********/
insert into coloreve values (1, 'Azul','0000FF');
insert into coloreve values (2, 'Amarillo','FFFF00');
insert into coloreve values (3, 'Rojo','FF0000');
insert into coloreve values (4, 'Morado','800080');
insert into coloreve values (5, 'Magenta','FF00FF');
insert into coloreve values (6, 'Negro','000000');
insert into coloreve values (7, 'Blanco','FFFFFF');
insert into coloreve values (8, 'Lima','00FF00');
insert into coloreve values (9, 'Cyan','00FFFF');
insert into coloreve values (10, 'Verde','008000');
insert into coloreve values (11, 'Gris','808080');
insert into coloreve values (12, 'Granate','800000');
insert into coloreve values (13, 'Cafe','808000');
insert into coloreve values (14, 'Azul-Oscuro','008080');

/**********Colores Recursos**********/
insert into Colorrec values (1, 'Azul','0000FF');
insert into Colorrec values (2, 'Amarillo','FFFF00');
insert into Colorrec values (3, 'Rojo','FF0000');
insert into Colorrec values (4, 'Morado','800080');
insert into Colorrec values (5, 'Magenta','FF00FF');
insert into Colorrec values (6, 'Negro','000000');
insert into Colorrec values (7, 'Blanco','FFFFFF');
insert into Colorrec values (8, 'Lima','00FF00');
insert into Colorrec values (9, 'Cyan','00FFFF');
insert into Colorrec values (10, 'Verde','008000');
insert into Colorrec values (11, 'Gris','808080');
insert into Colorrec values (12, 'Granate','800000');
insert into Colorrec values (13, 'Cafe','808000');
insert into Colorrec values (14, 'Azul-Oscuro','008080');

/**********Colores Recursos**********/
insert into Colorsala values (1, 'Azul','0000FF');
insert into Colorsala values (2, 'Amarillo','FFFF00');
insert into Colorsala values (3, 'Rojo','FF0000');
insert into Colorsala values (4, 'Morado','800080');
insert into Colorsala values (5, 'Magenta','FF00FF');
insert into Colorsala values (6, 'Negro','000000');
insert into Colorsala values (7, 'Blanco','FFFFFF');
insert into Colorsala values (8, 'Lima','00FF00');
insert into Colorsala values (9, 'Cyan','00FFFF');
insert into Colorsala values (10, 'Verde','008000');
insert into Colorsala values (11, 'Gris','808080');
insert into Colorsala values (12, 'Granate','800000');
insert into Colorsala values (13, 'Cafe','808000');
insert into Colorsala values (14, 'Azul-Oscuro','008080');
/**********Usuarios**********/

insert into tipoestadousr values (1, 'Pendiente');
insert into tipoestadousr values (2, 'Activado');
insert into tipoestadousr values (3, 'Desactivado');

INSERT INTO actividad VALUES (1, '1. Gestión de Usuarios', 'administracionusuarios.xhtml');
INSERT INTO actividad VALUES (2, '2. Gestión de evento, sala, recurso', 'recurso.xhtml');
INSERT INTO actividad VALUES (3, '3. Solicitud de Eventos', 'soleven.xhtml');
INSERT INTO actividad VALUES (4, '4. Solicitud de Recursos Adicionales', 'soldet.xhtml');
INSERT INTO actividad VALUES (5, '5. Aprobación de Recursos Adicionales', 'solicitudes.xhtml');
/*INSERT INTO actividad VALUES (6, '6. Creación Tipos Evento', 'evetipo.xhtml');*/
INSERT INTO actividad VALUES (6, '6. Aprobación de Eventos', 'eventos.xhtml');
/*INSERT INTO actividad VALUES (8, '8. Gestión de Salas', 'salas.xhtml');*/
INSERT INTO actividad VALUES (7, '7. Gestión de Servicios Virtuales', 'crudservicio.xhtml');
INSERT INTO actividad VALUES (8,'8. Aprobación Servicios Virtuales', 'aprovadorserviciovirtual.xhtml');
INSERT INTO actividad VALUES (9,'9. Registro a Servicios Virtuales', 'serviciovirtual.xhtml');
INSERT INTO actividad VALUES (10,'10. Aprobación de Inscripciones', 'inscripciones.xhtml');
INSERT INTO actividad VALUES (11,'11. Visualización de Servicios Virtuales', 'visualicacionservicios.xhtml');

INSERT INTO tipo VALUES (default, 'root', 'Administrador General del Sistema');
INSERT INTO tipo VALUES (default, 'pendiente', 'tipo para usuarios registrados fuera del sistema');

INSERT INTO inter VALUES (default, 1, 1);
INSERT INTO inter VALUES (default, 1, 2);
INSERT INTO inter VALUES (default, 1, 3);
INSERT INTO inter VALUES (default, 1, 4);
INSERT INTO inter VALUES (default, 1, 5);
INSERT INTO inter VALUES (default, 1, 6);
INSERT INTO inter VALUES (default, 1, 7);
INSERT INTO inter VALUES (default, 1, 8);
INSERT INTO inter VALUES (default, 1, 9);
INSERT INTO inter VALUES (default, 1, 10);
INSERT INTO inter VALUES (default, 1, 11);
/*INSERT INTO inter VALUES (default, 1, 12);
INSERT INTO inter VALUES (default, 1, 13);*/

INSERT INTO usuario  VALUES (1, 2, 1,'root' , 'root', 'root', 'root@correo.com', 'root' , 'Juz2xAZXhrH/1qLbW91EXA==' , 'Ninguno', 'Notificado','false','yachay','funcionario');/*admin.12345*/
update contadores set valor=2 where id_contador=2;
