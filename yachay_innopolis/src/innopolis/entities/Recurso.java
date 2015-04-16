package innopolis.entities;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;


/**
 * The persistent class for the recurso database table.
 * 
 */
@Entity
@NamedQuery(name="Recurso.findAll", query="SELECT r FROM Recurso r")
public class Recurso implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="RECURSO_IDRECURSO_GENERATOR", sequenceName="SEQ_RECURSO",allocationSize=1)
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="RECURSO_IDRECURSO_GENERATOR")
	@Column(name="id_recurso")
	private Integer idRecurso;

	private Integer capacidad;

	private String descripcion;

	private String imagen;

	private String lugar;

	private String nombre;

	//bi-directional many-to-one association to Recursodisponible
	@ManyToOne
	@JoinColumn(name="id_recdisponible")
	private Recursodisponible recursodisponible;

	//bi-directional many-to-one association to Recursotipo
	@ManyToOne
	@JoinColumn(name="id_rectipo")
	private Recursotipo recursotipo;

	//bi-directional many-to-one association to Solicidetalle
	@OneToMany(mappedBy="recurso")
	private List<Solicidetalle> solicidetalles;

	public Recurso() {
	}

	public Integer getIdRecurso() {
		return this.idRecurso;
	}

	public void setIdRecurso(Integer idRecurso) {
		this.idRecurso = idRecurso;
	}

	public Integer getCapacidad() {
		return this.capacidad;
	}

	public void setCapacidad(Integer capacidad) {
		this.capacidad = capacidad;
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

	public String getLugar() {
		return this.lugar;
	}

	public void setLugar(String lugar) {
		this.lugar = lugar;
	}

	public String getNombre() {
		return this.nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public Recursodisponible getRecursodisponible() {
		return this.recursodisponible;
	}

	public void setRecursodisponible(Recursodisponible recursodisponible) {
		this.recursodisponible = recursodisponible;
	}

	public Recursotipo getRecursotipo() {
		return this.recursotipo;
	}

	public void setRecursotipo(Recursotipo recursotipo) {
		this.recursotipo = recursotipo;
	}

	public List<Solicidetalle> getSolicidetalles() {
		return this.solicidetalles;
	}

	public void setSolicidetalles(List<Solicidetalle> solicidetalles) {
		this.solicidetalles = solicidetalles;
	}

	public Solicidetalle addSolicidetalle(Solicidetalle solicidetalle) {
		getSolicidetalles().add(solicidetalle);
		solicidetalle.setRecurso(this);

		return solicidetalle;
	}

	public Solicidetalle removeSolicidetalle(Solicidetalle solicidetalle) {
		getSolicidetalles().remove(solicidetalle);
		solicidetalle.setRecurso(null);

		return solicidetalle;
	}

}