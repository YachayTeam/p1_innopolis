package innopolis.entidades;

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
	private Integer idRectipo;

	private String descripcion;

	private String imagen;

	private String tipo;

	//bi-directional many-to-one association to Recurso
	@OneToMany(mappedBy="recursotipo")
	private List<Recurso> recursos;

	//bi-directional many-to-one association to Colorrec
	@ManyToOne
	@JoinColumn(name="idcolorec")
	private Colorrec colorrec;

	public Recursotipo() {
	}

	public Integer getIdRectipo() {
		return this.idRectipo;
	}

	public void setIdRectipo(Integer idRectipo) {
		this.idRectipo = idRectipo;
	}

	public String getDescripcion() {
		return this.descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public String getImagen() {
		return this.imagen;
	}

	public void setImagen(String imagen) {
		this.imagen = imagen;
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

	public Colorrec getColorrec() {
		return this.colorrec;
	}

	public void setColorrec(Colorrec colorrec) {
		this.colorrec = colorrec;
	}

}