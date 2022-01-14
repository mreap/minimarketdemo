-- Database generated with pgModeler (PostgreSQL Database Modeler).
-- pgModeler  version: 0.9.1
-- PostgreSQL version: 10.0
-- Project Site: pgmodeler.io
-- Model Author: ---


-- Database creation must be done outside a multicommand file.
-- These commands were put in this file only as a convenience.
-- -- object: mipymes | type: DATABASE --
-- -- DROP DATABASE IF EXISTS mipymes;
-- CREATE DATABASE mipymes
-- 	ENCODING = 'UTF8'
-- 	LC_COLLATE = 'es_EC.UTF-8'
-- 	LC_CTYPE = 'es_EC.UTF-8'
-- 	TABLESPACE = pg_default;
-- -- ddl-end --
-- 

-- object: public.seg_usuario_id_seg_usuario_seq | type: SEQUENCE --
-- DROP SEQUENCE IF EXISTS public.seg_usuario_id_seg_usuario_seq CASCADE;
CREATE SEQUENCE public.seg_usuario_id_seg_usuario_seq
	INCREMENT BY 1
	MINVALUE 1
	MAXVALUE 2147483647
	START WITH 1
	CACHE 1
	NO CYCLE
	OWNED BY NONE;
-- ddl-end --

-- object: public.seg_usuario | type: TABLE --
-- DROP TABLE IF EXISTS public.seg_usuario CASCADE;
CREATE TABLE public.seg_usuario(
	id_seg_usuario integer NOT NULL DEFAULT nextval('public.seg_usuario_id_seg_usuario_seq'::regclass),
	codigo character varying(10) NOT NULL,
	apellidos character varying(50) NOT NULL,
	nombres character varying(50) NOT NULL,
	correo character varying(50) NOT NULL,
	clave character varying(50) NOT NULL,
	activo boolean NOT NULL,
	CONSTRAINT seg_usuario_pk PRIMARY KEY (id_seg_usuario)

);
-- ddl-end --

-- object: public.seg_modulo_id_seg_modulo_seq | type: SEQUENCE --
-- DROP SEQUENCE IF EXISTS public.seg_modulo_id_seg_modulo_seq CASCADE;
CREATE SEQUENCE public.seg_modulo_id_seg_modulo_seq
	INCREMENT BY 1
	MINVALUE 1
	MAXVALUE 2147483647
	START WITH 1
	CACHE 1
	NO CYCLE
	OWNED BY NONE;
-- ddl-end --

-- object: public.seg_modulo | type: TABLE --
-- DROP TABLE IF EXISTS public.seg_modulo CASCADE;
CREATE TABLE public.seg_modulo(
	id_seg_modulo integer NOT NULL DEFAULT nextval('public.seg_modulo_id_seg_modulo_seq'::regclass),
	nombre_modulo character varying(50) NOT NULL,
	icono character varying(100),
	CONSTRAINT seg_modulo_pk PRIMARY KEY (id_seg_modulo),
	CONSTRAINT uk_nombre_modulo UNIQUE (nombre_modulo)

);
-- ddl-end --

-- object: public.seg_asignacion_id_seg_asignacion_seq | type: SEQUENCE --
-- DROP SEQUENCE IF EXISTS public.seg_asignacion_id_seg_asignacion_seq CASCADE;
CREATE SEQUENCE public.seg_asignacion_id_seg_asignacion_seq
	INCREMENT BY 1
	MINVALUE 1
	MAXVALUE 2147483647
	START WITH 1
	CACHE 1
	NO CYCLE
	OWNED BY NONE;
-- ddl-end --

-- object: public.seg_asignacion | type: TABLE --
-- DROP TABLE IF EXISTS public.seg_asignacion CASCADE;
CREATE TABLE public.seg_asignacion(
	id_seg_asignacion integer NOT NULL DEFAULT nextval('public.seg_asignacion_id_seg_asignacion_seq'::regclass),
	id_seg_usuario integer NOT NULL,
	id_seg_perfil integer NOT NULL,
	CONSTRAINT seg_asignacion_pk PRIMARY KEY (id_seg_asignacion),
	CONSTRAINT uk_asignacion UNIQUE (id_seg_usuario,id_seg_perfil)

);
-- ddl-end --

-- object: public.aud_bitacora_id_aud_bitacora_seq | type: SEQUENCE --
-- DROP SEQUENCE IF EXISTS public.aud_bitacora_id_aud_bitacora_seq CASCADE;
CREATE SEQUENCE public.aud_bitacora_id_aud_bitacora_seq
	INCREMENT BY 1
	MINVALUE 1
	MAXVALUE 2147483647
	START WITH 1
	CACHE 1
	NO CYCLE
	OWNED BY NONE;
-- ddl-end --

-- object: public.aud_bitacora | type: TABLE --
-- DROP TABLE IF EXISTS public.aud_bitacora CASCADE;
CREATE TABLE public.aud_bitacora(
	id_aud_bitacora integer NOT NULL DEFAULT nextval('public.aud_bitacora_id_aud_bitacora_seq'::regclass),
	fecha_evento timestamp NOT NULL,
	nombre_clase character varying(100) NOT NULL,
	nombre_metodo character varying(100) NOT NULL,
	descripcion_evento character varying(300) NOT NULL,
	id_usuario character varying(100) NOT NULL,
	direccion_ip character varying(100) NOT NULL,
	CONSTRAINT aud_bitacora_pk PRIMARY KEY (id_aud_bitacora)

);
-- ddl-end --

-- object: public.thm_cargo_id_thm_cargo_seq | type: SEQUENCE --
-- DROP SEQUENCE IF EXISTS public.thm_cargo_id_thm_cargo_seq CASCADE;
CREATE SEQUENCE public.thm_cargo_id_thm_cargo_seq
	INCREMENT BY 1
	MINVALUE 1
	MAXVALUE 2147483647
	START WITH 1
	CACHE 1
	NO CYCLE
	OWNED BY NONE;
-- ddl-end --

-- object: public.thm_cargo | type: TABLE --
-- DROP TABLE IF EXISTS public.thm_cargo CASCADE;
CREATE TABLE public.thm_cargo(
	id_thm_cargo integer NOT NULL DEFAULT nextval('public.thm_cargo_id_thm_cargo_seq'::regclass),
	nombre_cargo character varying(50) NOT NULL,
	remuneracion_mensual numeric(7,2) NOT NULL,
	CONSTRAINT thm_cargo_pk PRIMARY KEY (id_thm_cargo),
	CONSTRAINT uk_thm_cargo UNIQUE (nombre_cargo)

);
-- ddl-end --

-- Appended SQL commands --
INSERT INTO public.thm_cargo (nombre_cargo,remuneracion_mensual) VALUES ('Director financiero',1300.00);
INSERT INTO public.thm_cargo (nombre_cargo,remuneracion_mensual) VALUES ('Bodeguero',890.00);
-- ddl-end --

-- object: public.thm_empleado_id_thm_empleado_seq | type: SEQUENCE --
-- DROP SEQUENCE IF EXISTS public.thm_empleado_id_thm_empleado_seq CASCADE;
CREATE SEQUENCE public.thm_empleado_id_thm_empleado_seq
	INCREMENT BY 1
	MINVALUE 1
	MAXVALUE 2147483647
	START WITH 1
	CACHE 1
	NO CYCLE
	OWNED BY NONE;
-- ddl-end --

-- object: public.thm_empleado | type: TABLE --
-- DROP TABLE IF EXISTS public.thm_empleado CASCADE;
CREATE TABLE public.thm_empleado(
	id_thm_empleado integer NOT NULL DEFAULT nextval('public.thm_empleado_id_thm_empleado_seq'::regclass),
	id_thm_cargo integer NOT NULL,
	id_seg_usuario integer NOT NULL,
	horas_trabajadas smallint NOT NULL,
	horas_extra smallint NOT NULL,
	cuota_prestamo numeric(7,2) NOT NULL,
	CONSTRAINT thm_empleado_pk PRIMARY KEY (id_thm_empleado),
	CONSTRAINT uk_empleado_usuario UNIQUE (id_seg_usuario)

);
-- ddl-end --

-- object: public.thm_rol_cabecera_id_thm_rol_cabecera_seq | type: SEQUENCE --
-- DROP SEQUENCE IF EXISTS public.thm_rol_cabecera_id_thm_rol_cabecera_seq CASCADE;
CREATE SEQUENCE public.thm_rol_cabecera_id_thm_rol_cabecera_seq
	INCREMENT BY 1
	MINVALUE 1
	MAXVALUE 2147483647
	START WITH 1
	CACHE 1
	NO CYCLE
	OWNED BY NONE;
-- ddl-end --

-- object: public.thm_rol_cabecera | type: TABLE --
-- DROP TABLE IF EXISTS public.thm_rol_cabecera CASCADE;
CREATE TABLE public.thm_rol_cabecera(
	id_thm_rol_cabecera integer NOT NULL DEFAULT nextval('public.thm_rol_cabecera_id_thm_rol_cabecera_seq'::regclass),
	id_thm_empleado integer NOT NULL,
	id_thm_periodo_rol integer NOT NULL,
	nombre_cargo character varying(50) NOT NULL,
	horas_trabajadas smallint NOT NULL,
	horas_extras smallint NOT NULL,
	subtotal_ingresos numeric(7,2) NOT NULL,
	subtotal_ingresos_adicionales numeric(7,2) NOT NULL,
	subtotal_egresos numeric(7,2) NOT NULL,
	total numeric(7,2) NOT NULL,
	CONSTRAINT thm_rol_cabecera_pkey PRIMARY KEY (id_thm_rol_cabecera)

);
-- ddl-end --

-- object: public.thm_rol_detalle_id_thm_rol_detalle_seq | type: SEQUENCE --
-- DROP SEQUENCE IF EXISTS public.thm_rol_detalle_id_thm_rol_detalle_seq CASCADE;
CREATE SEQUENCE public.thm_rol_detalle_id_thm_rol_detalle_seq
	INCREMENT BY 1
	MINVALUE 1
	MAXVALUE 2147483647
	START WITH 1
	CACHE 1
	NO CYCLE
	OWNED BY NONE;
-- ddl-end --

-- object: public.thm_rol_detalle | type: TABLE --
-- DROP TABLE IF EXISTS public.thm_rol_detalle CASCADE;
CREATE TABLE public.thm_rol_detalle(
	id_thm_rol_detalle integer NOT NULL DEFAULT nextval('public.thm_rol_detalle_id_thm_rol_detalle_seq'::regclass),
	id_thm_rol_cabecera integer NOT NULL,
	tipo_detalle character varying(2) NOT NULL,
	descripcion character varying(100) NOT NULL,
	valor numeric(7,2) NOT NULL,
	orden smallint NOT NULL,
	CONSTRAINT thm_rol_detalle_pkey PRIMARY KEY (id_thm_rol_detalle)

);
-- ddl-end --

-- object: public.pry_proyecto_id_pry_proyecto_seq | type: SEQUENCE --
-- DROP SEQUENCE IF EXISTS public.pry_proyecto_id_pry_proyecto_seq CASCADE;
CREATE SEQUENCE public.pry_proyecto_id_pry_proyecto_seq
	INCREMENT BY 1
	MINVALUE 1
	MAXVALUE 2147483647
	START WITH 1
	CACHE 1
	NO CYCLE
	OWNED BY NONE;
-- ddl-end --

-- object: public.pry_proyecto | type: TABLE --
-- DROP TABLE IF EXISTS public.pry_proyecto CASCADE;
CREATE TABLE public.pry_proyecto(
	id_pry_proyecto integer NOT NULL DEFAULT nextval('public.pry_proyecto_id_pry_proyecto_seq'::regclass),
	nombre character varying(100) NOT NULL,
	fecha_inicio date NOT NULL,
	fecha_fin date NOT NULL,
	estado character varying(1) NOT NULL,
	avance smallint NOT NULL,
	CONSTRAINT pry_proyecto_pkey PRIMARY KEY (id_pry_proyecto)

);
-- ddl-end --

-- object: public.pry_tarea_id_pry_tarea_seq | type: SEQUENCE --
-- DROP SEQUENCE IF EXISTS public.pry_tarea_id_pry_tarea_seq CASCADE;
CREATE SEQUENCE public.pry_tarea_id_pry_tarea_seq
	INCREMENT BY 1
	MINVALUE 1
	MAXVALUE 2147483647
	START WITH 1
	CACHE 1
	NO CYCLE
	OWNED BY NONE;
-- ddl-end --

-- object: public.pry_tarea | type: TABLE --
-- DROP TABLE IF EXISTS public.pry_tarea CASCADE;
CREATE TABLE public.pry_tarea(
	id_pry_tarea integer NOT NULL DEFAULT nextval('public.pry_tarea_id_pry_tarea_seq'::regclass),
	nombre character varying(100) NOT NULL,
	fecha_inicio date NOT NULL,
	fecha_fin date NOT NULL,
	avance smallint NOT NULL,
	id_seg_usuario integer NOT NULL,
	id_pry_proyecto integer,
	CONSTRAINT pry_tarea_pkey PRIMARY KEY (id_pry_tarea)

);
-- ddl-end --

-- object: public.seg_perfil | type: TABLE --
-- DROP TABLE IF EXISTS public.seg_perfil CASCADE;
CREATE TABLE public.seg_perfil(
	id_seg_perfil serial NOT NULL,
	nombre_perfil character varying(50) NOT NULL,
	ruta_acceso character varying(100) NOT NULL,
	id_seg_modulo integer NOT NULL,
	CONSTRAINT seg_perfil_pk PRIMARY KEY (id_seg_perfil)

);
-- ddl-end --

-- object: public.thm_periodo_rol | type: TABLE --
-- DROP TABLE IF EXISTS public.thm_periodo_rol CASCADE;
CREATE TABLE public.thm_periodo_rol(
	id_thm_periodo_rol serial NOT NULL,
	nombre_periodo_rol character varying(7) NOT NULL,
	generado boolean NOT NULL,
	CONSTRAINT thm_periodo_rol_pk PRIMARY KEY (id_thm_periodo_rol),
	CONSTRAINT uk_periodo_rol UNIQUE (nombre_periodo_rol)

);
-- ddl-end --
ALTER TABLE public.thm_periodo_rol OWNER TO postgres;
-- ddl-end --

-- Appended SQL commands --
INSERT INTO public.thm_periodo_rol (nombre_periodo_rol,generado) VALUES ('2021-10',false);
INSERT INTO public.thm_periodo_rol (nombre_periodo_rol,generado) VALUES ('2021-11',false);
INSERT INTO public.thm_periodo_rol (nombre_periodo_rol,generado) VALUES ('2021-12',false);
INSERT INTO public.thm_periodo_rol (nombre_periodo_rol,generado) VALUES ('2022-01',false);
INSERT INTO public.thm_periodo_rol (nombre_periodo_rol,generado) VALUES ('2022-02',false);
-- ddl-end --

-- object: public.vw_thm_consulta_rol | type: VIEW --
-- DROP VIEW IF EXISTS public.vw_thm_consulta_rol CASCADE;
CREATE VIEW public.vw_thm_consulta_rol
AS 

SELECT trc.id_thm_rol_cabecera,
    tpr.nombre_periodo_rol,
    trc.id_thm_empleado,
    trc.total,
    te.horas_extra,
    su.apellidos
   FROM thm_rol_cabecera trc,
    thm_periodo_rol tpr,
    thm_empleado te,
    seg_usuario su
  WHERE ((tpr.id_thm_periodo_rol=trc.id_thm_periodo_rol) AND (trc.id_thm_empleado = te.id_thm_empleado) AND (te.id_seg_usuario = su.id_seg_usuario));
-- ddl-end --
ALTER VIEW public.vw_thm_consulta_rol OWNER TO postgres;
-- ddl-end --

-- object: seg_perfil_seg_asignacion_fk | type: CONSTRAINT --
-- ALTER TABLE public.seg_asignacion DROP CONSTRAINT IF EXISTS seg_perfil_seg_asignacion_fk CASCADE;
ALTER TABLE public.seg_asignacion ADD CONSTRAINT seg_perfil_seg_asignacion_fk FOREIGN KEY (id_seg_perfil)
REFERENCES public.seg_perfil (id_seg_perfil) MATCH SIMPLE
ON DELETE NO ACTION ON UPDATE NO ACTION;
-- ddl-end --

-- object: seg_usuario_seg_asignacion_fk | type: CONSTRAINT --
-- ALTER TABLE public.seg_asignacion DROP CONSTRAINT IF EXISTS seg_usuario_seg_asignacion_fk CASCADE;
ALTER TABLE public.seg_asignacion ADD CONSTRAINT seg_usuario_seg_asignacion_fk FOREIGN KEY (id_seg_usuario)
REFERENCES public.seg_usuario (id_seg_usuario) MATCH SIMPLE
ON DELETE NO ACTION ON UPDATE NO ACTION;
-- ddl-end --

-- object: fk_empleado_cargo | type: CONSTRAINT --
-- ALTER TABLE public.thm_empleado DROP CONSTRAINT IF EXISTS fk_empleado_cargo CASCADE;
ALTER TABLE public.thm_empleado ADD CONSTRAINT fk_empleado_cargo FOREIGN KEY (id_thm_cargo)
REFERENCES public.thm_cargo (id_thm_cargo) MATCH FULL
ON DELETE NO ACTION ON UPDATE NO ACTION;
-- ddl-end --

-- object: fk_empleado_usuario | type: CONSTRAINT --
-- ALTER TABLE public.thm_empleado DROP CONSTRAINT IF EXISTS fk_empleado_usuario CASCADE;
ALTER TABLE public.thm_empleado ADD CONSTRAINT fk_empleado_usuario FOREIGN KEY (id_seg_usuario)
REFERENCES public.seg_usuario (id_seg_usuario) MATCH FULL
ON DELETE NO ACTION ON UPDATE NO ACTION;
-- ddl-end --

-- object: fk_cab_empleado | type: CONSTRAINT --
-- ALTER TABLE public.thm_rol_cabecera DROP CONSTRAINT IF EXISTS fk_cab_empleado CASCADE;
ALTER TABLE public.thm_rol_cabecera ADD CONSTRAINT fk_cab_empleado FOREIGN KEY (id_thm_empleado)
REFERENCES public.thm_empleado (id_thm_empleado) MATCH FULL
ON DELETE NO ACTION ON UPDATE NO ACTION;
-- ddl-end --

-- object: fk_periodo_rol_cab_rol | type: CONSTRAINT --
-- ALTER TABLE public.thm_rol_cabecera DROP CONSTRAINT IF EXISTS fk_periodo_rol_cab_rol CASCADE;
ALTER TABLE public.thm_rol_cabecera ADD CONSTRAINT fk_periodo_rol_cab_rol FOREIGN KEY (id_thm_periodo_rol)
REFERENCES public.thm_periodo_rol (id_thm_periodo_rol) MATCH FULL
ON DELETE NO ACTION ON UPDATE NO ACTION;
-- ddl-end --

-- object: fk_det_cab_rol | type: CONSTRAINT --
-- ALTER TABLE public.thm_rol_detalle DROP CONSTRAINT IF EXISTS fk_det_cab_rol CASCADE;
ALTER TABLE public.thm_rol_detalle ADD CONSTRAINT fk_det_cab_rol FOREIGN KEY (id_thm_rol_cabecera)
REFERENCES public.thm_rol_cabecera (id_thm_rol_cabecera) MATCH FULL
ON DELETE NO ACTION ON UPDATE NO ACTION;
-- ddl-end --

-- object: pry_tarea_id_seg_usuario_fkey | type: CONSTRAINT --
-- ALTER TABLE public.pry_tarea DROP CONSTRAINT IF EXISTS pry_tarea_id_seg_usuario_fkey CASCADE;
ALTER TABLE public.pry_tarea ADD CONSTRAINT pry_tarea_id_seg_usuario_fkey FOREIGN KEY (id_seg_usuario)
REFERENCES public.seg_usuario (id_seg_usuario) MATCH SIMPLE
ON DELETE NO ACTION ON UPDATE NO ACTION;
-- ddl-end --

-- object: fk_tarea_proyecto | type: CONSTRAINT --
-- ALTER TABLE public.pry_tarea DROP CONSTRAINT IF EXISTS fk_tarea_proyecto CASCADE;
ALTER TABLE public.pry_tarea ADD CONSTRAINT fk_tarea_proyecto FOREIGN KEY (id_pry_proyecto)
REFERENCES public.pry_proyecto (id_pry_proyecto) MATCH SIMPLE
ON DELETE NO ACTION ON UPDATE NO ACTION;
-- ddl-end --

-- object: fk_perfil_modulo | type: CONSTRAINT --
-- ALTER TABLE public.seg_perfil DROP CONSTRAINT IF EXISTS fk_perfil_modulo CASCADE;
ALTER TABLE public.seg_perfil ADD CONSTRAINT fk_perfil_modulo FOREIGN KEY (id_seg_modulo)
REFERENCES public.seg_modulo (id_seg_modulo) MATCH FULL
ON DELETE NO ACTION ON UPDATE NO ACTION;
-- ddl-end --


