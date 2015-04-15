package innopolis.entities;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;


/**
 * The persistent class for the recursodisponible database table.
 * 
 */
@Entity
@NamedQuery(name="Recursodisponible.findAll", query="SELECT r FROM Recursodisponible r")
public class Recursodisponible implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="RECURSODISPONIBLE_IDRECDISPONIBLE_GENERATOR", sequenceName="SEQ_RECURSODISPONIBLE", allocationSize=1)
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="RECURSODISPONIBLE_IDRECDISPONIBLE_GENERATOR")
	@Column(name="id_recdisponible")
	private Long idRecdisponible;

	private String disponibilidad;

	//bi-directional many-to-one association to Recurso
	@OneToMany(mappedBy="recursodisponible")
	private List<Recurso> recursos;

	public Recursodisponible() {
	}

	public Long getIdRecdisponible() {
		return this.idRecdisponible;
	}

	public void setIdRecdisponible(Long idRecdisponible) {
		this.idRecdisponible = idRecdisponible;
	}

	public String getDisponibilidad() {
		return this.disponibilidad;
	}

	public void setDisponibilidad(String disponibilidad) {
		this.disponibilidad = disponibilidad;
	}

	public List<Recurso> getRecursos() {
		return this.recursos;
	}

	public void setRecursos(List<Recurso> recursos) {
		this.recursos = recursos;
	}

	public Recurso addRecurso(Recurso recurso) {
		getRecursos().add(recurso);
		recurso.setRecursodisponible(this);

		return recurso;
	}

	public Recurso removeRecurso(Recurso recurso) {
		getRecursos().remove(recurso);
		recurso.setRecursodisponible(null);

		return recurso;
	}

}