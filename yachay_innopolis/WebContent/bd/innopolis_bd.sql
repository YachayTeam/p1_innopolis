/*==============================================================*/
/* User: PUBLIC                                                 */
/*==============================================================*/
/*==============================================================*/
/* Table: CONTADORES                                            */
/*==============================================================*/
create table CONTADORES (
   ID_CONTADOR          INT4                 not null,
   TIPO                 TEXT                 null,
   VALOR                INT4                 null,
   constraint PK_CONTADORES primary key (ID_CONTADOR)
);

/*==============================================================*/
/* Table: ESTADOTIPOSERVICIO                                    */
/*==============================================================*/
create table ESTADOTIPOSERVICIO (
   ID_ETS               INT4                 not null,
   ETS                  TEXT                 null,
   constraint PK_ESTADOTIPOSERVICIO primary key (ID_ETS)
);

/*==============================================================*/
/* Table: EVENTOS                                               */
/*==============================================================*/
create table PUBLIC.EVENTOS (
   ID_EVENTO            INT4                 not null,
   ID_SOLCAB            INT4                 null,
   ID_TIPO_EVENTO       INT4                 null,
   NOMBRE               TEXT                 null,
   DESCRIPCION          TEXT                 null,
   IMAGEN               TEXT                 null,
   FECHA_INICIO         DATE                 null,
   FECHA_FIN            DATE                 null,
   COSTO                FLOAT4               null,
   LUGAR                TEXT                 null,
   CANTIDAD             INT4                 null,
   constraint PK_EVENTOS primary key (ID_EVENTO)
);

/*==============================================================*/
/* Table: INSCRIPCIONES                                         */
/*==============================================================*/
create table PUBLIC.INSCRIPCIONES (
   ID_INSCRIPCION       INT4                 not null,
   ID_EVENTO            INT4                 null,
   ID_USUARIO           INT4                 null,
   IMAGEN_PAGO          TEXT                 null,
   FECHA_INSCRIPCION    TIMESTAMP            null,
   OBSERVACION          TEXT                 null,
   NOMBRE               TEXT                 null,
   APELLIDO             TEXT                 null,
   CORREO               TEXT                 null,
   ESTADO               TEXT                 null,
   SMS                  TEXT                 null,
   constraint PK_INSCRIPCIONES primary key (ID_INSCRIPCION)
);

/*==============================================================*/
/* Table: RECURSO                                               */
/*==============================================================*/
create table PUBLIC.RECURSO (
   ID_RECURSO           INT4                 not null,
   ID_RECTIPO           INT4                 null,
   ID_RECDISPONIBLE     INT4                 null,
   NOMBRE               TEXT                 null,
   CAPACIDAD            INT4                 null,
   DESCRIPCION          TEXT                 null,
   LUGAR                TEXT                 null,
   IMAGEN               TEXT                 null,
   constraint PK_RECURSO primary key (ID_RECURSO)
);

/*==============================================================*/
/* Table: RECURSODISPONIBLE                                     */
/*==============================================================*/
create table PUBLIC.RECURSODISPONIBLE (
   ID_RECDISPONIBLE     INT4                 not null,
   DISPONIBLE           TEXT                 null,
   constraint PK_RECURSODISPONIBLE primary key (ID_RECDISPONIBLE)
);

/*==============================================================*/
/* Table: RECURSOSACTIVOS                                       */
/*==============================================================*/
create table RECURSOSACTIVOS (
   ID_RECACT            NUMERIC              not null,
   ID_SOLICITUD         INT4                 null,
   FECHA		        DATE                 null,
   HORA_INICIO          TIME                 null,
   HORA_FIN             TIME                 null,
   ID_RECURSO           INT4                 null,
   constraint PK_RECURSOSACTIVOS primary key (ID_RECACT)
);

/*==============================================================*/
/* Table: RECURSOTIPO                                           */
/*==============================================================*/
create table PUBLIC.RECURSOTIPO (
   ID_RECTIPO           INT4                 not null,
   TIPO                 TEXT                 null,
   constraint PK_RECURSOTIPO primary key (ID_RECTIPO)
);

/*==============================================================*/
/* Table: SERVICIOSVIRTREGIS                                    */
/*==============================================================*/
create table PUBLIC.SERVICIOSVIRTREGIS (
   ID_SVR               INT4                 not null,
   ID_TP                INT4                 null,
   ID_ESTADO            INT4                 null,
   CEDULA               INT4                 null,
   NOMBRES              TEXT                 null,
   APELLIDOS            TEXT                 null,
   CORREO               TEXT                 null,
   TEMA                 TEXT                 null,
   SMS                  TEXT                 null,
   constraint PK_SERVICIOSVIRTREGIS primary key (ID_SVR)
);

/*==============================================================*/
/* Table: SOLICICABECERA                                        */
/*==============================================================*/
create table PUBLIC.SOLICICABECERA (
   ID_SOLCAB            INT4                 not null,
   ID_SOLEST            INT4                 null,
   DIRECCION            TEXT                 null,
   ACTIVIDAD            TEXT                 null,
   OBJETIVO             TEXT                 null,
   JUSTIFICACION        TEXT                 null,
   FECHA                DATE                 null,
   SMS                  TEXT                 null,
   CAPACIDAD            INT4                 null,
   constraint PK_SOLICICABECERA primary key (ID_SOLCAB)
);

/*==============================================================*/
/* Table: SOLICIDETALLE                                         */
/*==============================================================*/
create table PUBLIC.SOLICIDETALLE (
   ID_SOLDET            INT4                 not null,
   ID_SOLCAB            INT4                 null,
   ID_RECURSO           INT4                 null,
   HORA_INICIO          TIME                 null,
   HORA_FIN             TIME                 null,
   CAPACIDAD            INT4                 null,
   constraint PK_SOLICIDETALLE primary key (ID_SOLDET)
);

/*==============================================================*/
/* Table: SOLICIESTADO                                          */
/*==============================================================*/
create table PUBLIC.SOLICIESTADO (
   ID_SOLEST            INT4                 not null,
   ESTADO               TEXT                 null,
   constraint PK_SOLICIESTADO primary key (ID_SOLEST)
);

/*==============================================================*/
/* Table: TIPOESTADO                                            */
/*==============================================================*/
create table PUBLIC.TIPOESTADO (
   ID_ESTADO            INT4                 not null,
   NOMBREESTADO         TEXT                 null,
   constraint PK_TIPOESTADO primary key (ID_ESTADO)
);

/*==============================================================*/
/* Table: TIPOESTADOUSR                                         */
/*==============================================================*/
create table TIPOESTADOUSR (
   ID_TIPOESTADOUSR     INT4                 not null,
   NOMBREESTADO         TEXT                 null,
   constraint PK_TIPOESTADOUSR primary key (ID_TIPOESTADOUSR)
);

/*==============================================================*/
/* Table: TIPOEVENTO                                            */
/*==============================================================*/
create table PUBLIC.TIPOEVENTO (
   ID_TIPO_EVENTO       INT4                 not null,
   TIPO                 TEXT                 null,
   DESCRIPCION          TEXT                 null,
   constraint PK_TIPOEVENTO primary key (ID_TIPO_EVENTO)
);

/*==============================================================*/
/* Table: TIPOLOGIN                                             */
/*==============================================================*/
create table TIPOLOGIN (
   ID_TIPOLOGIN         INT4                 not null,
   TIPOLOGIN            TEXT                 null,
   DESCRIPCION          TEXT                 null,
   constraint PK_TIPOLOGIN primary key (ID_TIPOLOGIN)
);

/*==============================================================*/
/* Table: TIPOSERVICIO                                          */
/*==============================================================*/
create table PUBLIC.TIPOSERVICIO (
   ID_TP                INT4                 not null,
   ID_ETS               INT4                 null,
   NOMBRE_SERVICIO      TEXT                 null,
   constraint PK_TIPOSERVICIO primary key (ID_TP)
);

/*==============================================================*/
/* Table: TIPOUSR                                               */
/*==============================================================*/
create table TIPOUSR (
   ID_TIPUSR            INT4                 not null,
   ID_TIPOLOGIN         INT4                 null,
   ID_USR               INT4                 null,
   constraint PK_TIPOUSR primary key (ID_TIPUSR)
);

/*==============================================================*/
/* Table: USUARIO                                               */
/*==============================================================*/
create table USUARIO (
   ID_USR               INT4                 not null,
   ID_TIPOESTADOUSR     INT4                 null,
   ID_VALIDAR           INT4                 null,
   NOMBRE               TEXT                 null,
   APELLIDO             TEXT                 null,
   CORREO               TEXT                 null,
   ALIAS                TEXT                 null,
   PASSWORD             TEXT                 null,
   INTERES              TEXT                 null,
   constraint PK_USUARIO primary key (ID_USR)
);

/*==============================================================*/
/* Table: VALIDARUSR                                            */
/*==============================================================*/
create table VALIDARUSR (
   ID_VALIDAR           INT4                 not null,
   VALIDAR              TEXT                 null,
   constraint PK_VALIDARUSR primary key (ID_VALIDAR)
);

alter table EVENTOS
   add constraint FK_EVENTOS_REFERENCE_SOLICICA foreign key (ID_SOLCAB)
      references SOLICICABECERA (ID_SOLCAB)
      on delete restrict on update restrict;

alter table EVENTOS
   add constraint FK_EVENTOS_REFERENCE_TIPOEVEN foreign key (ID_TIPO_EVENTO)
      references TIPOEVENTO (ID_TIPO_EVENTO)
      on delete restrict on update restrict;

alter table INSCRIPCIONES
   add constraint FK_INSCRIPC_REFERENCE_EVENTOS foreign key (ID_EVENTO)
      references EVENTOS (ID_EVENTO)
      on delete restrict on update restrict;

alter table RECURSO
   add constraint FK_RECURSO_REFERENCE_RECURSOD foreign key (ID_RECDISPONIBLE)
      references RECURSODISPONIBLE (ID_RECDISPONIBLE)
      on delete restrict on update restrict;

alter table RECURSO
   add constraint FK_RECURSO_REFERENCE_RECURSOT foreign key (ID_RECTIPO)
      references RECURSOTIPO (ID_RECTIPO)
      on delete restrict on update restrict;

alter table SERVICIOSVIRTREGIS
   add constraint FK_SERVICIO_REFERENCE_TIPOESTA foreign key (ID_ESTADO)
      references TIPOESTADO (ID_ESTADO)
      on delete restrict on update restrict;

alter table SERVICIOSVIRTREGIS
   add constraint FK_SERVICIO_REFERENCE_TIPOSERV foreign key (ID_TP)
      references TIPOSERVICIO (ID_TP)
      on delete restrict on update restrict;

alter table SOLICICABECERA
   add constraint FK_SOLICICA_REFERENCE_SOLICIES foreign key (ID_SOLEST)
      references SOLICIESTADO (ID_SOLEST)
      on delete restrict on update restrict;

alter table SOLICIDETALLE
   add constraint FK_SOLICIDE_REFERENCE_RECURSO foreign key (ID_RECURSO)
      references RECURSO (ID_RECURSO)
      on delete restrict on update restrict;

alter table SOLICIDETALLE
   add constraint FK_SOLICIDE_REFERENCE_SOLICICA foreign key (ID_SOLCAB)
      references SOLICICABECERA (ID_SOLCAB)
      on delete restrict on update restrict;

alter table TIPOSERVICIO
   add constraint FK_TIPOSERV_REFERENCE_ESTADOTI foreign key (ID_ETS)
      references ESTADOTIPOSERVICIO (ID_ETS)
      on delete restrict on update restrict;

alter table TIPOUSR
   add constraint FK_TIPOUSR_REFERENCE_TIPOLOGI foreign key (ID_TIPOLOGIN)
      references TIPOLOGIN (ID_TIPOLOGIN)
      on delete restrict on update restrict;

alter table TIPOUSR
   add constraint FK_TIPOUSR_REFERENCE_USUARIO foreign key (ID_USR)
      references USUARIO (ID_USR)
      on delete restrict on update restrict;

alter table USUARIO
   add constraint FK_USUARIO_REFERENCE_TIPOESTA foreign key (ID_TIPOESTADOUSR)
      references TIPOESTADOUSR (ID_TIPOESTADOUSR)
      on delete restrict on update restrict;

alter table USUARIO
   add constraint FK_USUARIO_REFERENCE_VALIDARU foreign key (ID_VALIDAR)
      references VALIDARUSR (ID_VALIDAR)
      on delete restrict on update restrict;

	  
/*==============================================================*/
/* SECUENCIAS                                           */
/*==============================================================*/
  CREATE SEQUENCE public.seq_recurso
   INCREMENT 1
   START 1;
   
   ALTER TABLE recurso
   ALTER COLUMN id_recurso SET DEFAULT nextval('seq_recurso');
ALTER TABLE recurso
  DROP CONSTRAINT fk_recurso_reference_recursod;
ALTER TABLE recurso
  DROP CONSTRAINT fk_recurso_reference_recursot;
ALTER TABLE recurso
  ADD CONSTRAINT fk_recurso_reference_recursod FOREIGN KEY (id_recdisponible)
      REFERENCES recursodisponible (id_recdisponible) MATCH SIMPLE
      ON UPDATE RESTRICT ON DELETE RESTRICT;
ALTER TABLE recurso
  ADD CONSTRAINT fk_recurso_reference_recursot FOREIGN KEY (id_rectipo)
      REFERENCES recursotipo (id_rectipo) MATCH SIMPLE
      ON UPDATE RESTRICT ON DELETE RESTRICT;
      
        
      
      CREATE SEQUENCE public.seq_tipoevento
   INCREMENT 1
   START 1;
   
   ALTER TABLE tipoevento
   ALTER COLUMN id_tipo_evento SET DEFAULT nextval('seq_tipoevento');
   
   CREATE SEQUENCE public.seq_eventos
   INCREMENT 1
   START 1;

   ALTER TABLE eventos
   ALTER COLUMN id_evento SET DEFAULT nextval('seq_eventos');
ALTER TABLE eventos
  DROP CONSTRAINT fk_eventos_reference_solicica;
ALTER TABLE eventos
  DROP CONSTRAINT fk_eventos_reference_tipoeven;
ALTER TABLE eventos
  ADD CONSTRAINT fk_eventos_reference_solicica FOREIGN KEY (id_solcab)
      REFERENCES solicicabecera (id_solcab) MATCH SIMPLE
      ON UPDATE RESTRICT ON DELETE RESTRICT;
ALTER TABLE eventos
  ADD CONSTRAINT fk_eventos_reference_tipoeven FOREIGN KEY (id_tipo_evento)
      REFERENCES tipoevento (id_tipo_evento) MATCH SIMPLE
      ON UPDATE RESTRICT ON DELETE RESTRICT;
      
CREATE SEQUENCE public.seq_recursotipo
   INCREMENT 1
   START 1;
   
ALTER TABLE recursotipo
   ALTER COLUMN id_rectipo SET DEFAULT nextval('seq_recursotipo');

   CREATE SEQUENCE public.seq_recursoactivo
   INCREMENT 1
   START 1;
   
   ALTER TABLE recursosactivos
   ALTER COLUMN id_recact SET DEFAULT nextval('seq_recursoactivo');
   
   CREATE SEQUENCE public.seq_soliciestado
   INCREMENT 1
   START 5;
   
   ALTER TABLE soliciestado
   ALTER COLUMN id_solest SET DEFAULT nextval('seq_soliciestado');
   
   /*CREATE SEQUENCE public.seq_solicicabecera
   INCREMENT 1
   START 1;
   
   ALTER TABLE solicicabecera
   ALTER COLUMN id_solcab SET DEFAULT nextval('seq_solicicabecera');
ALTER TABLE solicicabecera
  DROP CONSTRAINT fk_solicica_reference_solicies;
ALTER TABLE solicicabecera
  ADD CONSTRAINT fk_solicica_reference_solicies FOREIGN KEY (id_solest)
      REFERENCES soliciestado (id_solest) MATCH SIMPLE
      ON UPDATE RESTRICT ON DELETE RESTRICT;*/
      
      CREATE SEQUENCE public.seq_solicidetalle
   INCREMENT 1
   START 1;
   
   ALTER TABLE solicidetalle
   ALTER COLUMN id_soldet SET DEFAULT nextval('seq_solicidetalle');
ALTER TABLE solicidetalle
  DROP CONSTRAINT fk_solicide_reference_recurso;
ALTER TABLE solicidetalle
  DROP CONSTRAINT fk_solicide_reference_solicica;
ALTER TABLE solicidetalle
  ADD CONSTRAINT fk_solicide_reference_recurso FOREIGN KEY (id_recurso)
      REFERENCES recurso (id_recurso) MATCH SIMPLE
      ON UPDATE RESTRICT ON DELETE RESTRICT;
ALTER TABLE solicidetalle
  ADD CONSTRAINT fk_solicide_reference_solicica FOREIGN KEY (id_solcab)
      REFERENCES solicicabecera (id_solcab) MATCH SIMPLE
      ON UPDATE RESTRICT ON DELETE RESTRICT;
 
      CREATE SEQUENCE public.seq_inscripciones
   INCREMENT 1
   START 1;
   
   ALTER TABLE inscripciones
   ALTER COLUMN id_inscripcion SET DEFAULT nextval('seq_inscripciones');
ALTER TABLE inscripciones
  DROP CONSTRAINT fk_inscripc_reference_eventos;
ALTER TABLE inscripciones
  ADD CONSTRAINT fk_inscripc_reference_eventos FOREIGN KEY (id_evento)
      REFERENCES eventos (id_evento) MATCH SIMPLE
      ON UPDATE RESTRICT ON DELETE RESTRICT;
      
 CREATE SEQUENCE public.seq_servicios_virtuales
   INCREMENT 1
   START 1;

   CREATE SEQUENCE public.seq_tipo_estado
   INCREMENT 1
   START 1;

   CREATE SEQUENCE public.seq_tipo_servicio
   INCREMENT 1
   START 1;

   ALTER TABLE serviciosvirtregis
   ALTER COLUMN id_svr SET DEFAULT nextval('seq_servicios_virtuales');
ALTER TABLE serviciosvirtregis
  DROP CONSTRAINT fk_servicio_reference_tipoesta;
ALTER TABLE serviciosvirtregis
  DROP CONSTRAINT fk_servicio_reference_tiposerv;
ALTER TABLE serviciosvirtregis
  ADD CONSTRAINT fk_servicio_reference_tipoesta FOREIGN KEY (id_estado)
      REFERENCES tipoestado (id_estado) MATCH SIMPLE
      ON UPDATE RESTRICT ON DELETE RESTRICT;
ALTER TABLE serviciosvirtregis
  ADD CONSTRAINT fk_servicio_reference_tiposerv FOREIGN KEY (id_tp)
      REFERENCES tiposervicio (id_tp) MATCH SIMPLE
      ON UPDATE RESTRICT ON DELETE RESTRICT;
	  
	  ALTER TABLE tipoestado
   ALTER COLUMN id_estado SET DEFAULT nextval('seq_tipo_estado');
   
   ALTER TABLE tiposervicio
   ALTER COLUMN id_tp SET DEFAULT nextval('seq_tipo_servicio');

  CREATE SEQUENCE public.seq_recursos_disponibles
   INCREMENT 1
   START 3;
ALTER TABLE recursodisponible
   ALTER COLUMN id_recdisponible SET DEFAULT nextval('seq_recursos_disponibles');

CREATE SEQUENCE public.seq_contadores
   INCREMENT 1
   START 1;

ALTER TABLE contadores
   ALTER COLUMN id_contador SET DEFAULT nextval('seq_contadores');
   
CREATE SEQUENCE public.seq_tipoestadousr
   INCREMENT 1
   START 1
   MINVALUE 1;
   
ALTER TABLE tipoestadousr
   ALTER COLUMN id_tipoestadousr SET DEFAULT nextval('seq_tipoestadousr');

CREATE SEQUENCE public.seq_tipousr
   INCREMENT 1
   START 1
   MINVALUE 1;

ALTER TABLE tipousr
   ALTER COLUMN id_tipusr SET DEFAULT nextval('seq_tipousr');
ALTER TABLE tipousr
  DROP CONSTRAINT fk_tipousr_reference_tipologi;
ALTER TABLE tipousr
  DROP CONSTRAINT fk_tipousr_reference_usuario;
ALTER TABLE tipousr
  ADD CONSTRAINT fk_tipousr_reference_tipologi FOREIGN KEY (id_tipologin)
      REFERENCES tipologin (id_tipologin) MATCH SIMPLE
      ON UPDATE RESTRICT ON DELETE RESTRICT;
ALTER TABLE tipousr
  ADD CONSTRAINT fk_tipousr_reference_usuario FOREIGN KEY (id_usr)
      REFERENCES usuario (id_usr) MATCH SIMPLE
      ON UPDATE RESTRICT ON DELETE RESTRICT;
      
CREATE SEQUENCE public.seq_tipologin
   INCREMENT 1
   START 1
   MINVALUE 1;

ALTER TABLE tipologin
   ALTER COLUMN id_tipologin SET DEFAULT nextval('seq_tipologin');
   
CREATE SEQUENCE public.seq_estadotiposervicio
   INCREMENT 1
   START 1
   MINVALUE 1;

ALTER TABLE estadotiposervicio
   ALTER COLUMN id_ets SET DEFAULT nextval('seq_estadotiposervicio');

CREATE SEQUENCE public.seq_validarusr
   INCREMENT 1
   START 1
   MINVALUE 1;

ALTER TABLE validarusr
   ALTER COLUMN id_validar SET DEFAULT nextval('seq_validarusr');

insert into contadores values (default, 'solicitud cabecera',0);
insert into contadores values (default, 'usuario',1);