package minimarketdemo.model.core.entities;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;


/**
 * The persistent class for the est_estudiante database table.
 * 
 */
@Entity
@Table(name="est_estudiante")
@NamedQuery(name="EstEstudiante.findAll", query="SELECT e FROM EstEstudiante e")
public class EstEstudiante implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(unique=true, nullable=false)
	private Integer id;

	@Column(nullable=false, length=100)
	private String apellido;

	@Column(nullable=false, length=100)
	private String email;

	@Temporal(TemporalType.DATE)
	@Column(name="fecha_nacimiento")
	private Date fechaNacimiento;

	@Column(nullable=false, length=100)
	private String nombre;

	//bi-directional many-to-one association to EstCiudad
	@ManyToOne
	@JoinColumn(name="ciudad_id", nullable=false)
	private EstCiudad estCiudad;

	public EstEstudiante() {
		this.estCiudad = new EstCiudad();
	}

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getApellido() {
		return this.apellido;
	}

	public void setApellido(String apellido) {
		this.apellido = apellido;
	}

	public String getEmail() {
		return this.email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Date getFechaNacimiento() {
		return this.fechaNacimiento;
	}

	public void setFechaNacimiento(Date fechaNacimiento) {
		this.fechaNacimiento = fechaNacimiento;
	}

	public String getNombre() {
		return this.nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public EstCiudad getEstCiudad() {
		return this.estCiudad;
	}

	public void setEstCiudad(EstCiudad estCiudad) {
		this.estCiudad = estCiudad;
	}

}