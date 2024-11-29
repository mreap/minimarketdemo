package minimarketdemo.model.core.entities;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;


/**
 * The persistent class for the est_ciudad database table.
 * 
 */
@Entity
@Table(name="est_ciudad")
@NamedQuery(name="EstCiudad.findAll", query="SELECT e FROM EstCiudad e")
public class EstCiudad implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(unique=true, nullable=false)
	private Integer id;

	@Column(name="codigo_postal", nullable=false, length=10)
	private String codigoPostal;

	@Column(nullable=false, length=100)
	private String nombre;

	//bi-directional many-to-one association to EstEstudiante
	@OneToMany(mappedBy="estCiudad")
	private List<EstEstudiante> estEstudiantes;

	public EstCiudad() {
	}

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getCodigoPostal() {
		return this.codigoPostal;
	}

	public void setCodigoPostal(String codigoPostal) {
		this.codigoPostal = codigoPostal;
	}

	public String getNombre() {
		return this.nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public List<EstEstudiante> getEstEstudiantes() {
		return this.estEstudiantes;
	}

	public void setEstEstudiantes(List<EstEstudiante> estEstudiantes) {
		this.estEstudiantes = estEstudiantes;
	}

	public EstEstudiante addEstEstudiante(EstEstudiante estEstudiante) {
		getEstEstudiantes().add(estEstudiante);
		estEstudiante.setEstCiudad(this);

		return estEstudiante;
	}

	public EstEstudiante removeEstEstudiante(EstEstudiante estEstudiante) {
		getEstEstudiantes().remove(estEstudiante);
		estEstudiante.setEstCiudad(null);

		return estEstudiante;
	}

}