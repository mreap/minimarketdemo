package minimarketdemo.model.core.entities;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;


/**
 * The persistent class for the seg_perfil database table.
 * 
 */
@Entity
@Table(name="seg_perfil")
@NamedQuery(name="SegPerfil.findAll", query="SELECT s FROM SegPerfil s")
public class SegPerfil implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id_seg_perfil", unique=true, nullable=false)
	private Integer idSegPerfil;

	@Column(name="nombre_perfil", nullable=false, length=50)
	private String nombrePerfil;

	@Column(name="ruta_acceso", nullable=false, length=100)
	private String rutaAcceso;

	//bi-directional many-to-one association to SegAsignacion
	@OneToMany(mappedBy="segPerfil")
	private List<SegAsignacion> segAsignacions;

	//bi-directional many-to-one association to SegModulo
	@ManyToOne
	@JoinColumn(name="id_seg_modulo", nullable=false)
	private SegModulo segModulo;

	public SegPerfil() {
	}

	public Integer getIdSegPerfil() {
		return this.idSegPerfil;
	}

	public void setIdSegPerfil(Integer idSegPerfil) {
		this.idSegPerfil = idSegPerfil;
	}

	public String getNombrePerfil() {
		return this.nombrePerfil;
	}

	public void setNombrePerfil(String nombrePerfil) {
		this.nombrePerfil = nombrePerfil;
	}

	public String getRutaAcceso() {
		return this.rutaAcceso;
	}

	public void setRutaAcceso(String rutaAcceso) {
		this.rutaAcceso = rutaAcceso;
	}

	public List<SegAsignacion> getSegAsignacions() {
		return this.segAsignacions;
	}

	public void setSegAsignacions(List<SegAsignacion> segAsignacions) {
		this.segAsignacions = segAsignacions;
	}

	public SegAsignacion addSegAsignacion(SegAsignacion segAsignacion) {
		getSegAsignacions().add(segAsignacion);
		segAsignacion.setSegPerfil(this);

		return segAsignacion;
	}

	public SegAsignacion removeSegAsignacion(SegAsignacion segAsignacion) {
		getSegAsignacions().remove(segAsignacion);
		segAsignacion.setSegPerfil(null);

		return segAsignacion;
	}

	public SegModulo getSegModulo() {
		return this.segModulo;
	}

	public void setSegModulo(SegModulo segModulo) {
		this.segModulo = segModulo;
	}

}