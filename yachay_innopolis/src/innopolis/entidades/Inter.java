package innopolis.entidades;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the inter database table.
 * 
 */
@Entity
@NamedQuery(name="Inter.findAll", query="SELECT i FROM Inter i")
public class Inter implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="INTER_IDINTER_GENERATOR", sequenceName="SEQ_INTER", allocationSize=1)
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="INTER_IDINTER_GENERATOR")
	@Column(name="id_inter")
	private Integer idInter;

	//bi-directional many-to-one association to Actividad
	@ManyToOne
	@JoinColumn(name="id_actividad")
	private Actividad actividad;

	//bi-directional many-to-one association to Tipo
	@ManyToOne
	@JoinColumn(name="id_tipo")
	private Tipo tipo;

	public Inter() {
	}

	public Integer getIdInter() {
		return this.idInter;
	}

	public void setIdInter(Integer idInter) {
		this.idInter = idInter;
	}

	public Actividad getActividad() {
		return this.actividad;
	}

	public void setActividad(Actividad actividad) {
		this.actividad = actividad;
	}

	public Tipo getTipo() {
		return this.tipo;
	}

	public void setTipo(Tipo tipo) {
		this.tipo = tipo;
	}

}