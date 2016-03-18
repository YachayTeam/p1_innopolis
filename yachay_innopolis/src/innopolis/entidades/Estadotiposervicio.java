package innopolis.entidades;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;


/**
 * The persistent class for the estadotiposervicio database table.
 * 
 */
@Entity
@NamedQuery(name="Estadotiposervicio.findAll", query="SELECT e FROM Estadotiposervicio e")
public class Estadotiposervicio implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="ESTADOTIPOSERVICIO_IDETS_GENERATOR", sequenceName="SEQ_ESTADOTIPOSERVICIO", allocationSize=1)
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="ESTADOTIPOSERVICIO_IDETS_GENERATOR")
	@Column(name="id_ets")
	private Integer idEts;

	private String ets;

	//bi-directional many-to-one association to Tiposervicio
	@OneToMany(mappedBy="estadotiposervicio")
	private List<Tiposervicio> tiposervicios;

	public Estadotiposervicio() {
	}

	public Integer getIdEts() {
		return this.idEts;
	}

	public void setIdEts(Integer idEts) {
		this.idEts = idEts;
	}

	public String getEts() {
		return this.ets;
	}

	public void setEts(String ets) {
		this.ets = ets;
	}

	public List<Tiposervicio> getTiposervicios() {
		return this.tiposervicios;
	}

	public void setTiposervicios(List<Tiposervicio> tiposervicios) {
		this.tiposervicios = tiposervicios;
	}

	public Tiposervicio addTiposervicio(Tiposervicio tiposervicio) {
		getTiposervicios().add(tiposervicio);
		tiposervicio.setEstadotiposervicio(this);

		return tiposervicio;
	}

	public Tiposervicio removeTiposervicio(Tiposervicio tiposervicio) {
		getTiposervicios().remove(tiposervicio);
		tiposervicio.setEstadotiposervicio(null);

		return tiposervicio;
	}

}