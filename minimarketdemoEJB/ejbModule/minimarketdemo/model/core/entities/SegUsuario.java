package minimarketdemo.model.core.entities;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;


/**
 * The persistent class for the seg_usuario database table.
 * 
 */
@Entity
@Table(name="seg_usuario")
@NamedQuery(name="SegUsuario.findAll", query="SELECT s FROM SegUsuario s")
public class SegUsuario implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id_seg_usuario", unique=true, nullable=false)
	private Integer idSegUsuario;

	@Column(nullable=false)
	private Boolean activo;

	@Column(nullable=false, length=50)
	private String apellidos;

	@Column(nullable=false, length=50)
	private String clave;

	@Column(nullable=false, length=10)
	private String codigo;

	@Column(nullable=false, length=50)
	private String correo;

	@Column(nullable=false, length=50)
	private String nombres;

	//bi-directional many-to-one association to PryTarea
	@OneToMany(mappedBy="segUsuario")
	private List<PryTarea> pryTareas;

	//bi-directional many-to-one association to SegAsignacion
	@OneToMany(mappedBy="segUsuario")
	private List<SegAsignacion> segAsignacions;

	//bi-directional many-to-one association to ThmEmpleado
	@OneToMany(mappedBy="segUsuario")
	private List<ThmEmpleado> thmEmpleados;

	public SegUsuario() {
	}

	public Integer getIdSegUsuario() {
		return this.idSegUsuario;
	}

	public void setIdSegUsuario(Integer idSegUsuario) {
		this.idSegUsuario = idSegUsuario;
	}

	public Boolean getActivo() {
		return this.activo;
	}

	public void setActivo(Boolean activo) {
		this.activo = activo;
	}

	public String getApellidos() {
		return this.apellidos;
	}

	public void setApellidos(String apellidos) {
		this.apellidos = apellidos;
	}

	public String getClave() {
		return this.clave;
	}

	public void setClave(String clave) {
		this.clave = clave;
	}

	public String getCodigo() {
		return this.codigo;
	}

	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}

	public String getCorreo() {
		return this.correo;
	}

	public void setCorreo(String correo) {
		this.correo = correo;
	}

	public String getNombres() {
		return this.nombres;
	}

	public void setNombres(String nombres) {
		this.nombres = nombres;
	}

	public List<PryTarea> getPryTareas() {
		return this.pryTareas;
	}

	public void setPryTareas(List<PryTarea> pryTareas) {
		this.pryTareas = pryTareas;
	}

	public PryTarea addPryTarea(PryTarea pryTarea) {
		getPryTareas().add(pryTarea);
		pryTarea.setSegUsuario(this);

		return pryTarea;
	}

	public PryTarea removePryTarea(PryTarea pryTarea) {
		getPryTareas().remove(pryTarea);
		pryTarea.setSegUsuario(null);

		return pryTarea;
	}

	public List<SegAsignacion> getSegAsignacions() {
		return this.segAsignacions;
	}

	public void setSegAsignacions(List<SegAsignacion> segAsignacions) {
		this.segAsignacions = segAsignacions;
	}

	public SegAsignacion addSegAsignacion(SegAsignacion segAsignacion) {
		getSegAsignacions().add(segAsignacion);
		segAsignacion.setSegUsuario(this);

		return segAsignacion;
	}

	public SegAsignacion removeSegAsignacion(SegAsignacion segAsignacion) {
		getSegAsignacions().remove(segAsignacion);
		segAsignacion.setSegUsuario(null);

		return segAsignacion;
	}

	public List<ThmEmpleado> getThmEmpleados() {
		return this.thmEmpleados;
	}

	public void setThmEmpleados(List<ThmEmpleado> thmEmpleados) {
		this.thmEmpleados = thmEmpleados;
	}

	public ThmEmpleado addThmEmpleado(ThmEmpleado thmEmpleado) {
		getThmEmpleados().add(thmEmpleado);
		thmEmpleado.setSegUsuario(this);

		return thmEmpleado;
	}

	public ThmEmpleado removeThmEmpleado(ThmEmpleado thmEmpleado) {
		getThmEmpleados().remove(thmEmpleado);
		thmEmpleado.setSegUsuario(null);

		return thmEmpleado;
	}

}