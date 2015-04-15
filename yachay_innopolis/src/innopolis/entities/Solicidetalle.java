package innopolis.entities;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the solicidetalle database table.
 * 
 */
@Entity
@NamedQuery(name="Solicidetalle.findAll", query="SELECT s FROM Solicidetalle s")
public class Solicidetalle implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="SOLICIDETALLE_IDSOLDET_GENERATOR", sequenceName="SEQ_SOLICIDETALLE")
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="SOLICIDETALLE_IDSOLDET_GENERATOR")
	@Column(name="id_soldet")
	private Long idSoldet;

	private Long capacidad;

	//bi-directional many-to-one association to Recurso
	@ManyToOne
	@JoinColumn(name="id_recurso")
	private Recurso recurso;

	//bi-directional many-to-one association to Solicicabecera
	@ManyToOne
	@JoinColumn(name="id_solcab")
	private Solicicabecera solicicabecera;

	public Solicidetalle() {
	}

	public Long getIdSoldet() {
		return this.idSoldet;
	}

	public void setIdSoldet(Long idSoldet) {
		this.idSoldet = idSoldet;
	}

	public Long getCapacidad() {
		return this.capacidad;
	}

	public void setCapacidad(Long capacidad) {
		this.capacidad = capacidad;
	}

	public Recurso getRecurso() {
		return this.recurso;
	}

	public void setRecurso(Recurso recurso) {
		this.recurso = recurso;
	}

	public Solicicabecera getSolicicabecera() {
		return this.solicicabecera;
	}

	public void setSolicicabecera(Solicicabecera solicicabecera) {
		this.solicicabecera = solicicabecera;
	}

}