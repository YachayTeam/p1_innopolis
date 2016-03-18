package innopolis.entidades;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;


/**
 * The persistent class for the tipoestado database table.
 * 
 */
@Entity
@NamedQuery(name="Tipoestado.findAll", query="SELECT t FROM Tipoestado t")
public class Tipoestado implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="TIPOESTADO_IDESTADO_GENERATOR", sequenceName="SEQ_TIPO_ESTADO", allocationSize=1)
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="TIPOESTADO_IDESTADO_GENERATOR")
	@Column(name="id_estado")
	private Integer idEstado;

	private String nombreestado;

	//bi-directional many-to-one association to Serviciosvirtregi
	@OneToMany(mappedBy="tipoestado")
	private List<Serviciosvirtregi> serviciosvirtregis;

	public Tipoestado() {
	}

	public Integer getIdEstado() {
		return this.idEstado;
	}

	public void setIdEstado(Integer idEstado) {
		this.idEstado = idEstado;
	}

	public String getNombreestado() {
		return this.nombreestado;
	}

	public void setNombreestado(String nombreestado) {
		this.nombreestado = nombreestado;
	}

	public List<Serviciosvirtregi> getServiciosvirtregis() {
		return this.serviciosvirtregis;
	}

	public void setServiciosvirtregis(List<Serviciosvirtregi> serviciosvirtregis) {
		this.serviciosvirtregis = serviciosvirtregis;
	}

	public Serviciosvirtregi addServiciosvirtregi(Serviciosvirtregi serviciosvirtregi) {
		getServiciosvirtregis().add(serviciosvirtregi);
		serviciosvirtregi.setTipoestado(this);

		return serviciosvirtregi;
	}

	public Serviciosvirtregi removeServiciosvirtregi(Serviciosvirtregi serviciosvirtregi) {
		getServiciosvirtregis().remove(serviciosvirtregi);
		serviciosvirtregi.setTipoestado(null);

		return serviciosvirtregi;
	}

}