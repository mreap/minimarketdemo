CREATE TABLE seg_usuario
(
  id_seg_usuario serial NOT NULL,
  codigo character varying(10) NOT NULL,
  apellidos character varying(50) NOT NULL,
  nombres character varying(50) NOT NULL,
  correo character varying(50) NOT NULL,
  clave character varying(50) NOT NULL,
  activo boolean NOT NULL,
  CONSTRAINT seg_usuario_pk PRIMARY KEY (id_seg_usuario)
);

CREATE TABLE seg_modulo
(
  id_seg_modulo serial NOT NULL,
  nombre_modulo character varying(50) NOT NULL,
  ruta_acceso character varying(100) NOT NULL,
  CONSTRAINT seg_modulo_pk PRIMARY KEY (id_seg_modulo)
);

CREATE TABLE seg_asignacion
(
  id_seg_asignacion serial NOT NULL,
  id_seg_usuario integer NOT NULL,
  id_seg_modulo integer NOT NULL,
  CONSTRAINT seg_asignacion_pk PRIMARY KEY (id_seg_asignacion),
  CONSTRAINT seg_modulo_seg_asignacion_fk FOREIGN KEY (id_seg_modulo)
      REFERENCES public.seg_modulo (id_seg_modulo) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION,
  CONSTRAINT seg_usuario_seg_asignacion_fk FOREIGN KEY (id_seg_usuario)
      REFERENCES public.seg_usuario (id_seg_usuario) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION
);

CREATE TABLE aud_bitacora
(
  id_aud_bitacora serial NOT NULL,
  fecha_evento timestamp without time zone NOT NULL,
  nombre_clase character varying(100) NOT NULL,
  nombre_metodo character varying(100) NOT NULL,
  descripcion_evento character varying(300) NOT NULL,
  id_usuario character varying(100) NOT NULL,
  direccion_ip character varying(100) NOT NULL,
  CONSTRAINT aud_bitacora_pk PRIMARY KEY (id_aud_bitacora)
);

 
