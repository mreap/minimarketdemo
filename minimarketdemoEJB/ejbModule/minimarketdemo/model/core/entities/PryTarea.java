package minimarketdemo.model.core.entities;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;


/**
 * The persistent class for the pry_tarea database table.
 * 
 */
@Entity
@Table(name="pry_tarea")
@NamedQuery(name="PryTarea.findAll", query="SELECT p FROM PryTarea p")
public class PryTarea implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id_pry_tarea", unique=true, nullable=false)
	private Integer idPryTarea;

	@Column(nullable=false)
	private Integer avance;

	@Temporal(TemporalType.DATE)
	@Column(name="fecha_fin", nullable=false)
	private Date fechaFin;

	@Temporal(TemporalType.DATE)
	@Column(name="fecha_inicio", nullable=false)
	private Date fechaInicio;

	@Column(nullable=false, length=100)
	private String nombre;

	//bi-directional many-to-one association to PryProyecto
	@ManyToOne
	@JoinColumn(name="id_pry_proyecto")
	private PryProyecto pryProyecto;

	//bi-directional many-to-one association to SegUsuario
	@ManyToOne
	@JoinColumn(name="id_seg_usuario", nullable=false)
	private SegUsuario segUsuario;

	public PryTarea() {
	}

	public Integer getIdPryTarea() {
		return this.idPryTarea;
	}

	public void setIdPryTarea(Integer idPryTarea) {
		this.idPryTarea = idPryTarea;
	}

	public Integer getAvance() {
		return this.avance;
	}

	public void setAvance(Integer avance) {
		this.avance = avance;
	}

	public Date getFechaFin() {
		return this.fechaFin;
	}

	public void setFechaFin(Date fechaFin) {
		this.fechaFin = fechaFin;
	}

	public Date getFechaInicio() {
		return this.fechaInicio;
	}

	public void setFechaInicio(Date fechaInicio) {
		this.fechaInicio = fechaInicio;
	}

	public String getNombre() {
		return this.nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public PryProyecto getPryProyecto() {
		return this.pryProyecto;
	}

	public void setPryProyecto(PryProyecto pryProyecto) {
		this.pryProyecto = pryProyecto;
	}

	public SegUsuario getSegUsuario() {
		return this.segUsuario;
	}

	public void setSegUsuario(SegUsuario segUsuario) {
		this.segUsuario = segUsuario;
	}

}