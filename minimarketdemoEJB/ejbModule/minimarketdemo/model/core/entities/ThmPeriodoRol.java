package minimarketdemo.model.core.entities;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;


/**
 * The persistent class for the thm_periodo_rol database table.
 * 
 */
@Entity
@Table(name="thm_periodo_rol")
@NamedQuery(name="ThmPeriodoRol.findAll", query="SELECT t FROM ThmPeriodoRol t")
public class ThmPeriodoRol implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id_thm_periodo_rol", unique=true, nullable=false)
	private Integer idThmPeriodoRol;

	@Column(nullable=false)
	private Boolean generado;

	@Column(name="nombre_periodo_rol", nullable=false, length=7)
	private String nombrePeriodoRol;

	//bi-directional many-to-one association to ThmRolCabecera
	@OneToMany(mappedBy="thmPeriodoRol")
	private List<ThmRolCabecera> thmRolCabeceras;

	public ThmPeriodoRol() {
	}

	public Integer getIdThmPeriodoRol() {
		return this.idThmPeriodoRol;
	}

	public void setIdThmPeriodoRol(Integer idThmPeriodoRol) {
		this.idThmPeriodoRol = idThmPeriodoRol;
	}

	public Boolean getGenerado() {
		return this.generado;
	}

	public void setGenerado(Boolean generado) {
		this.generado = generado;
	}

	public String getNombrePeriodoRol() {
		return this.nombrePeriodoRol;
	}

	public void setNombrePeriodoRol(String nombrePeriodoRol) {
		this.nombrePeriodoRol = nombrePeriodoRol;
	}

	public List<ThmRolCabecera> getThmRolCabeceras() {
		return this.thmRolCabeceras;
	}

	public void setThmRolCabeceras(List<ThmRolCabecera> thmRolCabeceras) {
		this.thmRolCabeceras = thmRolCabeceras;
	}

	public ThmRolCabecera addThmRolCabecera(ThmRolCabecera thmRolCabecera) {
		getThmRolCabeceras().add(thmRolCabecera);
		thmRolCabecera.setThmPeriodoRol(this);

		return thmRolCabecera;
	}

	public ThmRolCabecera removeThmRolCabecera(ThmRolCabecera thmRolCabecera) {
		getThmRolCabeceras().remove(thmRolCabecera);
		thmRolCabecera.setThmPeriodoRol(null);

		return thmRolCabecera;
	}

}