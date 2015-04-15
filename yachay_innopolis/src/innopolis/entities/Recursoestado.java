package innopolis.entities;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;


/**
 * The persistent class for the recursoestado database table.
 * 
 */
@Entity
@NamedQuery(name="Recursoestado.findAll", query="SELECT r FROM Recursoestado r")
public class Recursoestado implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="RECURSOESTADO_IDRECEST_GENERATOR", sequenceName="SEQ_RECURSOESTADO", allocationSize=1)
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="RECURSOESTADO_IDRECEST_GENERATOR")
	@Column(name="id_recest")
	private Long idRecest;

	private String estado;

	//bi-directional many-to-one association to Recurso
	@OneToMany(mappedBy="recursoestado")
	private List<Recurso> recursos;

	public Recursoestado() {
	}

	public Long getIdRecest() {
		return this.idRecest;
	}

	public void setIdRecest(Long idRecest) {
		this.idRecest = idRecest;
	}

	public String getEstado() {
		return this.estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}

	public List<Recurso> getRecursos() {
		return this.recursos;
	}

	public void setRecursos(List<Recurso> recursos) {
		this.recursos = recursos;
	}

	public Recurso addRecurso(Recurso recurso) {
		getRecursos().add(recurso);
		recurso.setRecursoestado(this);

		return recurso;
	}

	public Recurso removeRecurso(Recurso recurso) {
		getRecursos().remove(recurso);
		recurso.setRecursoestado(null);

		return recurso;
	}

}