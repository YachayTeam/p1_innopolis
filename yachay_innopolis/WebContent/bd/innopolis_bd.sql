/*==============================================================*/
/* DBMS name:      PostgreSQL 8                                 */
/* Created on:     15/04/2015 11:40:44                          */
/*==============================================================*/


drop table EVENTOS;

drop table INSCRIPCIONES;

drop table RECURSO;

drop table RECURSODISPONIBLE;

drop table RECURSOESTADO;

drop table RECURSOTIPO;

drop table SERVICIOSVIRTREGIS;

drop table SOLICICABECERA;

drop table SOLICIDETALLE;

drop table SOLICIESTADO;

drop table TIPOESTADO;

drop table TIPOEVENTO;

drop table TIPOSERVICIO;

/*==============================================================*/
/* Table: EVENTOS                                               */
/*==============================================================*/
create table EVENTOS (
   ID_EVENTO            INT4                 not null,
   ID_SOLCAB            INT4                 null,
   ID_TIPO_EVENTO       INT4                 null,
   NOMBRE               VARCHAR(50)          null,
   DESCRIPCION          VARCHAR(100)         null,
   IMAGEN               VARCHAR(100)         null,
   FECHA_I              DATE                 null,
   FECHA_F              DATE                 null,
   COSTO                MONEY                null,
   LUGAR                VARCHAR(100)         null,
   constraint PK_EVENTOS primary key (ID_EVENTO)
);

/*==============================================================*/
/* Table: INSCRIPCIONES                                         */
/*==============================================================*/
create table INSCRIPCIONES (
   ID_INSCRIPCION       INT4                 not null,
   ID_EVENTO            INT4                 null,
   ID_USUARIO           INT4                 null,
   IMAGEN_PAGO          VARCHAR(100)         null,
   FECHA_INSCRIPCION    DATE                 null,
   DESCRIPCION          VARCHAR(100)         null,
   constraint PK_INSCRIPCIONES primary key (ID_INSCRIPCION)
);

/*==============================================================*/
/* Table: RECURSO                                               */
/*==============================================================*/
create table RECURSO (
   ID_RECURSO           INT4                 not null,
   ID_RECTIPO           INT4                 null,
   ID_RECEST            INT4                 null,
   ID_RECDISPONIBLE     INT4                 null,
   NOMBRE               VARCHAR(200)         null,
   CAPACIDAD            INT4                 null,
   DESCRIPCION          TEXT                 null,
   LUGAR                TEXT                 null,
   IMAGEN               TEXT                 null,
   constraint PK_RECURSO primary key (ID_RECURSO)
);

/*==============================================================*/
/* Table: RECURSODISPONIBLE                                     */
/*==============================================================*/
create table RECURSODISPONIBLE (
<<<<<<< HEAD
   ID_RECDISPONIBLE     INT4                 not null,
   DISPONIBLE           TEXT                 null,
=======
   ID_RECDISPONIBLE     INT8                 not null,
   DISPONIBILIDAD       TEXT                 null,
>>>>>>> branch 'master' of https://github.com/YachayTeam/p1_innopolis.git
   constraint PK_RECURSODISPONIBLE primary key (ID_RECDISPONIBLE)
);

/*==============================================================*/
/* Table: RECURSOESTADO                                         */
/*==============================================================*/
create table RECURSOESTADO (
   ID_RECEST            INT4                 not null,
   ESTADO               VARCHAR(100)         null,
   constraint PK_RECURSOESTADO primary key (ID_RECEST)
);

/*==============================================================*/
/* Table: RECURSOTIPO                                           */
/*==============================================================*/
create table RECURSOTIPO (
   ID_RECTIPO           INT4                 not null,
   TIPO                 VARCHAR(100)         null,
   constraint PK_RECURSOTIPO primary key (ID_RECTIPO)
);

/*==============================================================*/
/* Table: SERVICIOSVIRTREGIS                                    */
/*==============================================================*/
create table SERVICIOSVIRTREGIS (
   ID_SVR               INT4                 not null,
   ID_TP                INT4                 null,
   ID_ESTADO            INT4                 null,
   CEDULA               INT4                 null,
   NOMBRES              VARCHAR(60)          null,
   APELLIDOS            VARCHAR(60)          null,
   CORREO               VARCHAR(254)         null,
   TEMA                 VARCHAR(254)         null,
   constraint PK_SERVICIOSVIRTREGIS primary key (ID_SVR)
);

/*==============================================================*/
/* Table: SOLICICABECERA                                        */
/*==============================================================*/
create table SOLICICABECERA (
   ID_SOLCAB            INT4                 not null,
   ID_SOLEST            INT4                 null,
   DIRECCION            TEXT                 null,
   ACTIVIDAD            TEXT                 null,
   FECHA                DATE                 null,
   HORAINICIO           TIME                 null,
   HORAFIN              TIME                 null,
   constraint PK_SOLICICABECERA primary key (ID_SOLCAB)
);

/*==============================================================*/
/* Table: SOLICIDETALLE                                         */
/*==============================================================*/
create table SOLICIDETALLE (
   ID_SOLDET            INT4                 not null,
   ID_SOLCAB            INT4                 null,
   ID_RECURSO           INT4                 null,
   CAPACIDAD            INT4                 null,
   constraint PK_SOLICIDETALLE primary key (ID_SOLDET)
);

/*==============================================================*/
/* Table: SOLICIESTADO                                          */
/*==============================================================*/
create table SOLICIESTADO (
   ID_SOLEST            INT4                 not null,
   ESTADO               VARCHAR(100)         null,
   constraint PK_SOLICIESTADO primary key (ID_SOLEST)
);

/*==============================================================*/
/* Table: TIPOESTADO                                            */
/*==============================================================*/
create table TIPOESTADO (
   ID_ESTADO            INT4                 not null,
   NOMBREESTADO         VARCHAR(60)          null,
   constraint PK_TIPOESTADO primary key (ID_ESTADO)
);

/*==============================================================*/
/* Table: TIPOEVENTO                                            */
/*==============================================================*/
create table TIPOEVENTO (
   ID_TIPO_EVENTO       INT4                 not null,
   TIPO                 VARCHAR(25)          null,
   DESCRIPCION          VARCHAR(100)         null,
   constraint PK_TIPOEVENTO primary key (ID_TIPO_EVENTO)
);

/*==============================================================*/
/* Table: TIPOSERVICIO                                          */
/*==============================================================*/
create table TIPOSERVICIO (
   ID_TP                INT4                 not null,
   NOMBRE_SERVICIO      VARCHAR(60)          null,
   constraint PK_TIPOSERVICIO primary key (ID_TP)
);

alter table EVENTOS
   add constraint FK_EVENTOS_REFERENCE_TIPOEVEN foreign key (ID_TIPO_EVENTO)
      references TIPOEVENTO (ID_TIPO_EVENTO)
      on delete restrict on update restrict;

alter table EVENTOS
   add constraint FK_EVENTOS_REFERENCE_SOLICICA foreign key (ID_SOLCAB)
      references SOLICICABECERA (ID_SOLCAB)
      on delete restrict on update restrict;

<<<<<<< HEAD
=======
alter table RECURSO
   add constraint FK_RECURSO_REFERENCE_RECURSOD foreign key (ID_RECDISPONIBLE)
      references RECURSODISPONIBLE (ID_RECDISPONIBLE)
      on delete restrict on update restrict;
      
>>>>>>> branch 'master' of https://github.com/YachayTeam/p1_innopolis.git
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

alter table RECURSO
   add constraint FK_RECURSO_REFERENCE_RECURSOE foreign key (ID_RECEST)
      references RECURSOESTADO (ID_RECEST)
      on delete restrict on update restrict;

alter table SERVICIOSVIRTREGIS
   add constraint FK_SERVICIO_REFERENCE_TIPOSERV foreign key (ID_TP)
      references TIPOSERVICIO (ID_TP)
      on delete restrict on update restrict;

alter table SERVICIOSVIRTREGIS
   add constraint FK_SERVICIO_REFERENCE_TIPOESTA foreign key (ID_ESTADO)
      references TIPOESTADO (ID_ESTADO)
      on delete restrict on update restrict;

alter table SOLICICABECERA
   add constraint FK_SOLICICA_REFERENCE_SOLICIES foreign key (ID_SOLEST)
      references SOLICIESTADO (ID_SOLEST)
      on delete restrict on update restrict;

alter table SOLICIDETALLE
   add constraint FK_SOLICIDE_REFERENCE_SOLICICA foreign key (ID_SOLCAB)
      references SOLICICABECERA (ID_SOLCAB)
      on delete restrict on update restrict;

alter table SOLICIDETALLE
   add constraint FK_SOLICIDE_REFERENCE_RECURSO foreign key (ID_RECURSO)
      references RECURSO (ID_RECURSO)
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
  DROP CONSTRAINT fk_recurso_reference_recursoe;
ALTER TABLE recurso
  DROP CONSTRAINT fk_recurso_reference_recursot;
ALTER TABLE recurso
  ADD CONSTRAINT fk_recurso_reference_recursod FOREIGN KEY (id_recdisponible)
      REFERENCES recursodisponible (id_recdisponible) MATCH SIMPLE
      ON UPDATE RESTRICT ON DELETE RESTRICT;
ALTER TABLE recurso
  ADD CONSTRAINT fk_recurso_reference_recursoe FOREIGN KEY (id_recest)
      REFERENCES recursoestado (id_recest) MATCH SIMPLE
      ON UPDATE RESTRICT ON DELETE RESTRICT;
ALTER TABLE recurso
  ADD CONSTRAINT fk_recurso_reference_recursot FOREIGN KEY (id_rectipo)
      REFERENCES recursotipo (id_rectipo) MATCH SIMPLE
      ON UPDATE RESTRICT ON DELETE RESTRICT;
      
      CREATE SEQUENCE public.seq_recursodisponible
   INCREMENT 1
   START 1;
   
   ALTER TABLE recursodisponible
   ALTER COLUMN id_recdisponible SET DEFAULT nextval('seq_recursodisponible');

      
      
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

   CREATE SEQUENCE public.seq_recursoestado
   INCREMENT 1
   START 1;
   
   ALTER TABLE recursoestado
   ALTER COLUMN id_recest SET DEFAULT nextval('seq_recursoestado');
   
   CREATE SEQUENCE public.seq_soliciestado
   INCREMENT 1
   START 1;
   
   ALTER TABLE soliciestado
   ALTER COLUMN id_solest SET DEFAULT nextval('seq_soliciestado');
   
   CREATE SEQUENCE public.seq_solicicabecera
   INCREMENT 1
   START 1;
   
   ALTER TABLE solicicabecera
   ALTER COLUMN id_solcab SET DEFAULT nextval('seq_solicicabecera');
ALTER TABLE solicicabecera
  DROP CONSTRAINT fk_solicica_reference_solicies;
ALTER TABLE solicicabecera
  ADD CONSTRAINT fk_solicica_reference_solicies FOREIGN KEY (id_solest)
      REFERENCES soliciestado (id_solest) MATCH SIMPLE
      ON UPDATE RESTRICT ON DELETE RESTRICT;
      
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
   START 1;
ALTER TABLE recursodisponible
   ALTER COLUMN id_recdisponible SET DEFAULT nextval('seq_recursos_disponibles');

      
      
