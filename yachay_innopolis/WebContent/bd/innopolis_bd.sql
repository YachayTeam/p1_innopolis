/*==============================================================*/
/* DBMS name:      PostgreSQL 8                                 */
/* Created on:     14/04/2015 11:04:30                          */
/*==============================================================*/


drop table EVENTOS;

drop table RECURSO;

drop table RECURSOESTADO;

drop table RECURSOTIPO;

drop table SOLICICABECERA;

drop table SOLICIDETALLE;

drop table SOLICIESTADO;

drop table TIPOEVENTO;

/*==============================================================*/
/* Table: EVENTOS                                               */
/*==============================================================*/
create table EVENTOS (
   ID_EVENTO            INT8                 not null,
   ID_SOLCAB            INT8                 null,
   ID_TIPO_EVENTO       INT8                 null,
   NOMBRE               TEXT                 null,
   DESCRIPCION          TEXT                 null,
   IMAGEN               TEXT                 null,
   FECHA_I              DATE                 null,
   FECHA_F              DATE                 null,
   COSTO                MONEY                null,
   LUGAR                TEXT                 null,
   constraint PK_EVENTOS primary key (ID_EVENTO)
);

/*==============================================================*/
/* Table: RECURSO                                               */
/*==============================================================*/
create table RECURSO (
   ID_RECURSO           INT8                 not null,
   ID_RECTIPO           INT8                 null,
   ID_RECEST            INT8                 null,
   NOMBRE               VARCHAR(200)         null,
   CAPACIDAD            INT8                 null,
   DESCRIPCION          TEXT                 null,
   LUGAR                TEXT                 null,
   constraint PK_RECURSO primary key (ID_RECURSO)
);

/*==============================================================*/
/* Table: RECURSOESTADO                                         */
/*==============================================================*/
create table RECURSOESTADO (
   ID_RECEST            INT8                 not null,
   ESTADO               VARCHAR(100)         null,
   constraint PK_RECURSOESTADO primary key (ID_RECEST)
);

/*==============================================================*/
/* Table: RECURSOTIPO                                           */
/*==============================================================*/
create table RECURSOTIPO (
   ID_RECTIPO           INT8                 not null,
   TIPO                 VARCHAR(100)         null,
   constraint PK_RECURSOTIPO primary key (ID_RECTIPO)
);

/*==============================================================*/
/* Table: SOLICICABECERA                                        */
/*==============================================================*/
create table SOLICICABECERA (
   ID_SOLCAB            INT8                 not null,
   ID_SOLEST            INT8                 null,
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
   ID_SOLDET            INT8                 not null,
   ID_SOLCAB            INT8                 null,
   ID_RECURSO           INT8                 null,
   CAPACIDAD            INT8                 null,
   constraint PK_SOLICIDETALLE primary key (ID_SOLDET)
);

/*==============================================================*/
/* Table: SOLICIESTADO                                          */
/*==============================================================*/
create table SOLICIESTADO (
   ID_SOLEST            INT8                 not null,
   ESTADO               VARCHAR(100)         null,
   constraint PK_SOLICIESTADO primary key (ID_SOLEST)
);

/*==============================================================*/
/* Table: TIPOEVENTO                                            */
/*==============================================================*/
create table TIPOEVENTO (
   ID_TIPO_EVENTO       INT8                 not null,
   TIPO                 TEXT                 null,
   DESCRIPCION          TEXT                 null,
   constraint PK_TIPOEVENTO primary key (ID_TIPO_EVENTO)
);

alter table EVENTOS
   add constraint FK_EVENTOS_REFERENCE_TIPOEVEN foreign key (ID_TIPO_EVENTO)
      references TIPOEVENTO (ID_TIPO_EVENTO)
      on delete restrict on update restrict;

alter table EVENTOS
   add constraint FK_EVENTOS_REFERENCE_SOLICICA foreign key (ID_SOLCAB)
      references SOLICICABECERA (ID_SOLCAB)
      on delete restrict on update restrict;

alter table RECURSO
   add constraint FK_RECURSO_REFERENCE_RECURSOT foreign key (ID_RECTIPO)
      references RECURSOTIPO (ID_RECTIPO)
      on delete restrict on update restrict;

alter table RECURSO
   add constraint FK_RECURSO_REFERENCE_RECURSOE foreign key (ID_RECEST)
      references RECURSOESTADO (ID_RECEST)
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
   
      
      

   
   
   

   
