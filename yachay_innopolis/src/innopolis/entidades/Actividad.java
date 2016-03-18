package innopolis.entidades;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;


/**
 * The persistent class for the actividad database table.
 * 
 */
@Entity
@NamedQuery(name="Actividad.findAll", query="SELECT a FROM Actividad a")
public class Actividad implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="ACTIVIDAD_IDACTIVIDAD_GENERATOR" )
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="ACTIVIDAD_IDACTIVIDAD_GENERATOR")
	@Column(name="id_actividad")
	private Integer idActividad;

	private String actividad;

	private String url;

	//bi-directional many-to-one association to Inter
	@OneToMany(mappedBy="actividad")
	private List<Inter> inters;

	public Actividad() {
	}

	public Integer getIdActividad() {
		return this.idActividad;
	}

	public void setIdActividad(Integer idActividad) {
		this.idActividad = idActividad;
	}

	public String getActividad() {
		return this.actividad;
	}

	public void setActividad(String actividad) {
		this.actividad = actividad;
	}

	public String getUrl() {
		return this.url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public List<Inter> getInters() {
		return this.inters;
	}

	public void setInters(List<Inter> inters) {
		this.inters = inters;
	}

	public Inter addInter(Inter inter) {
		getInters().add(inter);
		inter.setActividad(this);

		return inter;
	}

	public Inter removeInter(Inter inter) {
		getInters().remove(inter);
		inter.setActividad(null);

		return inter;
	}

}