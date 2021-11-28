package minimarketdemo.model.core.entities;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;


/**
 * The persistent class for the seg_modulo database table.
 * 
 */
@Entity
@Table(name="seg_modulo")
@NamedQuery(name="SegModulo.findAll", query="SELECT s FROM SegModulo s")
public class SegModulo implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id_seg_modulo", unique=true, nullable=false)
	private Integer idSegModulo;

	@Column(length=100)
	private String icono;

	@Column(name="nombre_modulo", nullable=false, length=50)
	private String nombreModulo;

	//bi-directional many-to-one association to SegPerfil
	@OneToMany(mappedBy="segModulo",fetch = FetchType.EAGER)
	private List<SegPerfil> segPerfils;

	public SegModulo() {
	}

	public Integer getIdSegModulo() {
		return this.idSegModulo;
	}

	public void setIdSegModulo(Integer idSegModulo) {
		this.idSegModulo = idSegModulo;
	}

	public String getIcono() {
		return this.icono;
	}

	public void setIcono(String icono) {
		this.icono = icono;
	}

	public String getNombreModulo() {
		return this.nombreModulo;
	}

	public void setNombreModulo(String nombreModulo) {
		this.nombreModulo = nombreModulo;
	}

	public List<SegPerfil> getSegPerfils() {
		return this.segPerfils;
	}

	public void setSegPerfils(List<SegPerfil> segPerfils) {
		this.segPerfils = segPerfils;
	}

	public SegPerfil addSegPerfil(SegPerfil segPerfil) {
		getSegPerfils().add(segPerfil);
		segPerfil.setSegModulo(this);

		return segPerfil;
	}

	public SegPerfil removeSegPerfil(SegPerfil segPerfil) {
		getSegPerfils().remove(segPerfil);
		segPerfil.setSegModulo(null);

		return segPerfil;
	}

}