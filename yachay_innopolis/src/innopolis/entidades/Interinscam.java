package innopolis.entidades;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the interinscam database table.
 * 
 */
@Entity
@NamedQuery(name="Interinscam.findAll", query="SELECT i FROM Interinscam i")
public class Interinscam implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="INTERINSCAM_IDINTERINSCAM_GENERATOR", sequenceName="SEQ_INTERINSCAM", allocationSize=1)
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="INTERINSCAM_IDINTERINSCAM_GENERATOR")
	@Column(name="id_interinscam")
	private Integer idInterinscam;

	//bi-directional many-to-one association to Camposnuevo
	@ManyToOne
	@JoinColumn(name="id_campo")
	private Camposnuevo camposnuevo;

	//bi-directional many-to-one association to Inscripcione
	@ManyToOne
	@JoinColumn(name="id_inscripcion")
	private Inscripcione inscripcione;

	public Interinscam() {
	}

	public Integer getIdInterinscam() {
		return this.idInterinscam;
	}

	public void setIdInterinscam(Integer idInterinscam) {
		this.idInterinscam = idInterinscam;
	}

	public Camposnuevo getCamposnuevo() {
		return this.camposnuevo;
	}

	public void setCamposnuevo(Camposnuevo camposnuevo) {
		this.camposnuevo = camposnuevo;
	}

	public Inscripcione getInscripcione() {
		return this.inscripcione;
	}

	public void setInscripcione(Inscripcione inscripcione) {
		this.inscripcione = inscripcione;
	}

}