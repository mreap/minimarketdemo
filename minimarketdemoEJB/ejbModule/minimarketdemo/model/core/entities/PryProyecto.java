package minimarketdemo.model.core.entities;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;
import java.util.List;


/**
 * The persistent class for the pry_proyecto database table.
 * 
 */
@Entity
@Table(name="pry_proyecto")
@NamedQuery(name="PryProyecto.findAll", query="SELECT p FROM PryProyecto p")
public class PryProyecto implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id_pry_proyecto", unique=true, nullable=false)
	private Integer idPryProyecto;

	@Column(nullable=false)
	private Integer avance;

	@Column(nullable=false, length=1)
	private String estado;

	@Temporal(TemporalType.DATE)
	@Column(name="fecha_fin", nullable=false)
	private Date fechaFin;

	@Temporal(TemporalType.DATE)
	@Column(name="fecha_inicio", nullable=false)
	private Date fechaInicio;

	@Column(nullable=false, length=100)
	private String nombre;

	//bi-directional many-to-one association to PryTarea
	@OneToMany(mappedBy="pryProyecto")
	private List<PryTarea> pryTareas;

	public PryProyecto() {
	}

	public Integer getIdPryProyecto() {
		return this.idPryProyecto;
	}

	public void setIdPryProyecto(Integer idPryProyecto) {
		this.idPryProyecto = idPryProyecto;
	}

	public Integer getAvance() {
		return this.avance;
	}

	public void setAvance(Integer avance) {
		this.avance = avance;
	}

	public String getEstado() {
		return this.estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
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

	public List<PryTarea> getPryTareas() {
		return this.pryTareas;
	}

	public void setPryTareas(List<PryTarea> pryTareas) {
		this.pryTareas = pryTareas;
	}

	public PryTarea addPryTarea(PryTarea pryTarea) {
		getPryTareas().add(pryTarea);
		pryTarea.setPryProyecto(this);

		return pryTarea;
	}

	public PryTarea removePryTarea(PryTarea pryTarea) {
		getPryTareas().remove(pryTarea);
		pryTarea.setPryProyecto(null);

		return pryTarea;
	}

}