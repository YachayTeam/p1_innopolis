package innopolis.entities;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;


/**
 * The persistent class for the soliciestado database table.
 * 
 */
@Entity
@NamedQuery(name="Soliciestado.findAll", query="SELECT s FROM Soliciestado s")
public class Soliciestado implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="SOLICIESTADO_IDSOLEST_GENERATOR", sequenceName="SEQ_SOLICIESTADO")
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="SOLICIESTADO_IDSOLEST_GENERATOR")
	@Column(name="id_solest")
	private Long idSolest;

	private String estado;

	//bi-directional many-to-one association to Solicicabecera
	@OneToMany(mappedBy="soliciestado")
	private List<Solicicabecera> solicicabeceras;

	public Soliciestado() {
	}

	public Long getIdSolest() {
		return this.idSolest;
	}

	public void setIdSolest(Long idSolest) {
		this.idSolest = idSolest;
	}

	public String getEstado() {
		return this.estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}

	public List<Solicicabecera> getSolicicabeceras() {
		return this.solicicabeceras;
	}

	public void setSolicicabeceras(List<Solicicabecera> solicicabeceras) {
		this.solicicabeceras = solicicabeceras;
	}

	public Solicicabecera addSolicicabecera(Solicicabecera solicicabecera) {
		getSolicicabeceras().add(solicicabecera);
		solicicabecera.setSoliciestado(this);

		return solicicabecera;
	}

	public Solicicabecera removeSolicicabecera(Solicicabecera solicicabecera) {
		getSolicicabeceras().remove(solicicabecera);
		solicicabecera.setSoliciestado(null);

		return solicicabecera;
	}

}