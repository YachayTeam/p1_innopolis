package innopolis.entidades;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;


/**
 * The persistent class for the tiposervicio database table.
 * 
 */
@Entity
@NamedQuery(name="Tiposervicio.findAll", query="SELECT t FROM Tiposervicio t")
public class Tiposervicio implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="TIPOSERVICIO_IDTP_GENERATOR", sequenceName="SEQ_TIPO_SERVICIO", allocationSize=1)
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="TIPOSERVICIO_IDTP_GENERATOR")
	@Column(name="id_tp")
	private Integer idTp;

	@Column(name="nombre_servicio")
	private String nombreServicio;

	private String url;

	//bi-directional many-to-one association to Serviciosvirtregi
	@OneToMany(mappedBy="tiposervicio")
	private List<Serviciosvirtregi> serviciosvirtregis;

	//bi-directional many-to-one association to Estadotiposervicio
	@ManyToOne
	@JoinColumn(name="id_ets")
	private Estadotiposervicio estadotiposervicio;

	public Tiposervicio() {
	}

	public Integer getIdTp() {
		return this.idTp;
	}

	public void setIdTp(Integer idTp) {
		this.idTp = idTp;
	}

	public String getNombreServicio() {
		return this.nombreServicio;
	}

	public void setNombreServicio(String nombreServicio) {
		this.nombreServicio = nombreServicio;
	}

	public String getUrl() {
		return this.url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public List<Serviciosvirtregi> getServiciosvirtregis() {
		return this.serviciosvirtregis;
	}

	public void setServiciosvirtregis(List<Serviciosvirtregi> serviciosvirtregis) {
		this.serviciosvirtregis = serviciosvirtregis;
	}

	public Serviciosvirtregi addServiciosvirtregi(Serviciosvirtregi serviciosvirtregi) {
		getServiciosvirtregis().add(serviciosvirtregi);
		serviciosvirtregi.setTiposervicio(this);

		return serviciosvirtregi;
	}

	public Serviciosvirtregi removeServiciosvirtregi(Serviciosvirtregi serviciosvirtregi) {
		getServiciosvirtregis().remove(serviciosvirtregi);
		serviciosvirtregi.setTiposervicio(null);

		return serviciosvirtregi;
	}

	public Estadotiposervicio getEstadotiposervicio() {
		return this.estadotiposervicio;
	}

	public void setEstadotiposervicio(Estadotiposervicio estadotiposervicio) {
		this.estadotiposervicio = estadotiposervicio;
	}

}