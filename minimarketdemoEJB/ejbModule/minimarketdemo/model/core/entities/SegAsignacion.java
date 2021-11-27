package minimarketdemo.model.core.entities;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the seg_asignacion database table.
 * 
 */
@Entity
@Table(name="seg_asignacion")
@NamedQuery(name="SegAsignacion.findAll", query="SELECT s FROM SegAsignacion s")
public class SegAsignacion implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id_seg_asignacion", unique=true, nullable=false)
	private Integer idSegAsignacion;

	//bi-directional many-to-one association to SegPerfil
	@ManyToOne
	@JoinColumn(name="id_seg_perfil", nullable=false)
	private SegPerfil segPerfil;

	//bi-directional many-to-one association to SegUsuario
	@ManyToOne
	@JoinColumn(name="id_seg_usuario", nullable=false)
	private SegUsuario segUsuario;

	public SegAsignacion() {
	}

	public Integer getIdSegAsignacion() {
		return this.idSegAsignacion;
	}

	public void setIdSegAsignacion(Integer idSegAsignacion) {
		this.idSegAsignacion = idSegAsignacion;
	}

	public SegPerfil getSegPerfil() {
		return this.segPerfil;
	}

	public void setSegPerfil(SegPerfil segPerfil) {
		this.segPerfil = segPerfil;
	}

	public SegUsuario getSegUsuario() {
		return this.segUsuario;
	}

	public void setSegUsuario(SegUsuario segUsuario) {
		this.segUsuario = segUsuario;
	}

}