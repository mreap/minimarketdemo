-- Database generated with pgModeler (PostgreSQL Database Modeler).
-- pgModeler  version: 0.9.1
-- PostgreSQL version: 10.0
-- Project Site: pgmodeler.io
-- Model Author: mrea@utn.edu.ec

-- object: public.seg_usuario_id_seg_usuario_seq | type: SEQUENCE --
 DROP SEQUENCE IF EXISTS public.seg_usuario_id_seg_usuario_seq CASCADE;
CREATE SEQUENCE public.seg_usuario_id_seg_usuario_seq
	INCREMENT BY 1
	MINVALUE 1
	MAXVALUE 2147483647
	START WITH 1
	CACHE 1
	NO CYCLE
	OWNED BY NONE;
-- ddl-end --
ALTER SEQUENCE public.seg_usuario_id_seg_usuario_seq OWNER TO appinf2;
-- ddl-end --

-- object: public.seg_usuario | type: TABLE --
 DROP TABLE IF EXISTS public.seg_usuario CASCADE;
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
ALTER TABLE public.seg_usuario OWNER TO appinf2;
-- ddl-end --

-- object: public.seg_modulo_id_seg_modulo_seq | type: SEQUENCE --
 DROP SEQUENCE IF EXISTS public.seg_modulo_id_seg_modulo_seq CASCADE;
CREATE SEQUENCE public.seg_modulo_id_seg_modulo_seq
	INCREMENT BY 1
	MINVALUE 1
	MAXVALUE 2147483647
	START WITH 1
	CACHE 1
	NO CYCLE
	OWNED BY NONE;
-- ddl-end --
ALTER SEQUENCE public.seg_modulo_id_seg_modulo_seq OWNER TO appinf2;
-- ddl-end --

-- object: public.seg_modulo | type: TABLE --
 DROP TABLE IF EXISTS public.seg_modulo CASCADE;
CREATE TABLE public.seg_modulo(
	id_seg_modulo integer NOT NULL DEFAULT nextval('public.seg_modulo_id_seg_modulo_seq'::regclass),
	nombre_modulo character varying(50) NOT NULL,
	ruta_acceso character varying(100) NOT NULL,
	CONSTRAINT seg_modulo_pk PRIMARY KEY (id_seg_modulo)

);
-- ddl-end --
ALTER TABLE public.seg_modulo OWNER TO appinf2;
-- ddl-end --

-- object: public.seg_asignacion_id_seg_asignacion_seq | type: SEQUENCE --
 DROP SEQUENCE IF EXISTS public.seg_asignacion_id_seg_asignacion_seq CASCADE;
CREATE SEQUENCE public.seg_asignacion_id_seg_asignacion_seq
	INCREMENT BY 1
	MINVALUE 1
	MAXVALUE 2147483647
	START WITH 1
	CACHE 1
	NO CYCLE
	OWNED BY NONE;
-- ddl-end --
ALTER SEQUENCE public.seg_asignacion_id_seg_asignacion_seq OWNER TO appinf2;
-- ddl-end --

-- object: public.seg_asignacion | type: TABLE --
 DROP TABLE IF EXISTS public.seg_asignacion CASCADE;
CREATE TABLE public.seg_asignacion(
	id_seg_asignacion integer NOT NULL DEFAULT nextval('public.seg_asignacion_id_seg_asignacion_seq'::regclass),
	id_seg_usuario integer NOT NULL,
	id_seg_modulo integer NOT NULL,
	CONSTRAINT seg_asignacion_pk PRIMARY KEY (id_seg_asignacion)

);
-- ddl-end --
ALTER TABLE public.seg_asignacion OWNER TO appinf2;
-- ddl-end --

-- object: public.aud_bitacora_id_aud_bitacora_seq | type: SEQUENCE --
 DROP SEQUENCE IF EXISTS public.aud_bitacora_id_aud_bitacora_seq CASCADE;
CREATE SEQUENCE public.aud_bitacora_id_aud_bitacora_seq
	INCREMENT BY 1
	MINVALUE 1
	MAXVALUE 2147483647
	START WITH 1
	CACHE 1
	NO CYCLE
	OWNED BY NONE;
-- ddl-end --
ALTER SEQUENCE public.aud_bitacora_id_aud_bitacora_seq OWNER TO appinf2;
-- ddl-end --

-- object: public.aud_bitacora | type: TABLE --
 DROP TABLE IF EXISTS public.aud_bitacora CASCADE;
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
ALTER TABLE public.aud_bitacora OWNER TO appinf2;
-- ddl-end --

-- object: public.thm_cargo | type: TABLE --
 DROP TABLE IF EXISTS public.thm_cargo CASCADE;
CREATE TABLE public.thm_cargo(
	id_thm_cargo serial NOT NULL,
	nombre_cargo character varying(50) NOT NULL,
	remuneracion_mensual numeric(7,2) NOT NULL,
	CONSTRAINT thm_cargo_pk PRIMARY KEY (id_thm_cargo)

);
-- ddl-end --
ALTER TABLE public.thm_cargo OWNER TO appinf2;
-- ddl-end --

-- object: public.thm_empleado | type: TABLE --
 DROP TABLE IF EXISTS public.thm_empleado CASCADE;
CREATE TABLE public.thm_empleado(
	id_thm_empleado serial NOT NULL,
	id_thm_cargo integer NOT NULL,
	id_seg_usuario integer NOT NULL,
	horas_trabajadas smallint NOT NULL,
	horas_extra smallint NOT NULL,
	cuota_prestamo numeric(7,2) NOT NULL,
	CONSTRAINT thm_empleado_pk PRIMARY KEY (id_thm_empleado),
	CONSTRAINT uk_empleado_usuario UNIQUE (id_seg_usuario)

);
-- ddl-end --
ALTER TABLE public.thm_empleado OWNER TO appinf2;
-- ddl-end --

-- object: public.thm_rol_cabecera | type: TABLE --
 DROP TABLE IF EXISTS public.thm_rol_cabecera CASCADE;
CREATE TABLE public.thm_rol_cabecera(
	id_thm_rol_cabecera serial PRIMARY KEY,
	id_thm_empleado integer NOT NULL,
	periodo_rol character varying(6) NOT NULL,
	nombre_cargo character varying(50) NOT NULL,
	horas_trabajadas smallint NOT NULL,
	horas_extras smallint NOT NULL,
	subtotal_ingresos numeric(7,2) NOT NULL,
	subtotal_ingresos_adicionales numeric(7,2) NOT NULL,
	subtotal_egresos numeric(7,2) NOT NULL,
	total numeric(7,2) NOT NULL
);
-- ddl-end --
ALTER TABLE public.thm_rol_cabecera OWNER TO appinf2;
-- ddl-end --

-- object: public.thm_rol_detalle | type: TABLE --
 DROP TABLE IF EXISTS public.thm_rol_detalle CASCADE;
CREATE TABLE public.thm_rol_detalle(
	id_thm_rol_detalle serial NOT NULL PRIMARY KEY,
	id_thm_rol_cabecera integer NOT NULL,
	tipo_detalle character varying(2) NOT NULL,
	descripcion character varying(100) NOT NULL,
	valor numeric(7,2) NOT NULL,
	orden smallint NOT NULL
);
-- ddl-end --
ALTER TABLE public.thm_rol_detalle OWNER TO appinf2;
-- ddl-end --

-- object: seg_modulo_seg_asignacion_fk | type: CONSTRAINT --
 ALTER TABLE public.seg_asignacion DROP CONSTRAINT IF EXISTS seg_modulo_seg_asignacion_fk CASCADE;
ALTER TABLE public.seg_asignacion ADD CONSTRAINT seg_modulo_seg_asignacion_fk FOREIGN KEY (id_seg_modulo)
REFERENCES public.seg_modulo (id_seg_modulo) MATCH SIMPLE
ON DELETE NO ACTION ON UPDATE NO ACTION;
-- ddl-end --

-- object: seg_usuario_seg_asignacion_fk | type: CONSTRAINT --
 ALTER TABLE public.seg_asignacion DROP CONSTRAINT IF EXISTS seg_usuario_seg_asignacion_fk CASCADE;
ALTER TABLE public.seg_asignacion ADD CONSTRAINT seg_usuario_seg_asignacion_fk FOREIGN KEY (id_seg_usuario)
REFERENCES public.seg_usuario (id_seg_usuario) MATCH SIMPLE
ON DELETE NO ACTION ON UPDATE NO ACTION;
-- ddl-end --

-- object: fk_empleado_cargo | type: CONSTRAINT --
 ALTER TABLE public.thm_empleado DROP CONSTRAINT IF EXISTS fk_empleado_cargo CASCADE;
ALTER TABLE public.thm_empleado ADD CONSTRAINT fk_empleado_cargo FOREIGN KEY (id_thm_cargo)
REFERENCES public.thm_cargo (id_thm_cargo) MATCH FULL
ON DELETE NO ACTION ON UPDATE NO ACTION;
-- ddl-end --

-- object: fk_empleado_usuario | type: CONSTRAINT --
 ALTER TABLE public.thm_empleado DROP CONSTRAINT IF EXISTS fk_empleado_usuario CASCADE;
ALTER TABLE public.thm_empleado ADD CONSTRAINT fk_empleado_usuario FOREIGN KEY (id_seg_usuario)
REFERENCES public.seg_usuario (id_seg_usuario) MATCH FULL
ON DELETE NO ACTION ON UPDATE NO ACTION;
-- ddl-end --

-- object: fk_cab_empleado | type: CONSTRAINT --
 ALTER TABLE public.thm_rol_cabecera DROP CONSTRAINT IF EXISTS fk_cab_empleado CASCADE;
ALTER TABLE public.thm_rol_cabecera ADD CONSTRAINT fk_cab_empleado FOREIGN KEY (id_thm_empleado)
REFERENCES public.thm_empleado (id_thm_empleado) MATCH FULL
ON DELETE NO ACTION ON UPDATE NO ACTION;
-- ddl-end --

-- object: fk_det_cab_rol | type: CONSTRAINT --
 ALTER TABLE public.thm_rol_detalle DROP CONSTRAINT IF EXISTS fk_det_cab_rol CASCADE;
ALTER TABLE public.thm_rol_detalle ADD CONSTRAINT fk_det_cab_rol FOREIGN KEY (id_thm_rol_cabecera)
REFERENCES public.thm_rol_cabecera (id_thm_rol_cabecera) MATCH FULL
ON DELETE NO ACTION ON UPDATE NO ACTION;
-- ddl-end --


