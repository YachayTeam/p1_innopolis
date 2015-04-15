package innopolis.entities;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;


/**
 * The persistent class for the recursotipo database table.
 * 
 */
@Entity
@NamedQuery(name="Recursotipo.findAll", query="SELECT r FROM Recursotipo r")
public class Recursotipo implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="RECURSOTIPO_IDRECTIPO_GENERATOR", sequenceName="SEQ_RECURSOTIPO", allocationSize=1)
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="RECURSOTIPO_IDRECTIPO_GENERATOR")
	@Column(name="id_rectipo")
	private Long idRectipo;

	private String tipo;

	//bi-directional many-to-one association to Recurso
	@OneToMany(mappedBy="recursotipo")
	private List<Recurso> recursos;

	public Recursotipo() {
	}

	public Long getIdRectipo() {
		return this.idRectipo;
	}

	public void setIdRectipo(Long idRectipo) {
		this.idRectipo = idRectipo;
	}

	public String getTipo() {
		return this.tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	public List<Recurso> getRecursos() {
		return this.recursos;
	}

	public void setRecursos(List<Recurso> recursos) {
		this.recursos = recursos;
	}

	public Recurso addRecurso(Recurso recurso) {
		getRecursos().add(recurso);
		recurso.setRecursotipo(this);

		return recurso;
	}

	public Recurso removeRecurso(Recurso recurso) {
		getRecursos().remove(recurso);
		recurso.setRecursotipo(null);

		return recurso;
	}

}