package innopolis.entities;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the serviciosvirtregis database table.
 * 
 */
@Entity
@Table(name="serviciosvirtregis")
@NamedQuery(name="Serviciosvirtregi.findAll", query="SELECT s FROM Serviciosvirtregi s")
public class Serviciosvirtregi implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="SERVICIOSVIRTREGIS_IDSVR_GENERATOR", sequenceName="SEQ_SERVICIOS_VIRTUALES", allocationSize=1)
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="SERVICIOSVIRTREGIS_IDSVR_GENERATOR")
	@Column(name="id_svr")
	private Integer idSvr;

	private String apellidos;

	private Integer cedula;

	private String correo;

	private String nombres;

	private String sms;

	private String tema;

	//bi-directional many-to-one association to Tipoestado
	@ManyToOne
	@JoinColumn(name="id_estado")
	private Tipoestado tipoestado;

	//bi-directional many-to-one association to Tiposervicio
	@ManyToOne
	@JoinColumn(name="id_tp")
	private Tiposervicio tiposervicio;

	public Serviciosvirtregi() {
	}

	public Integer getIdSvr() {
		return this.idSvr;
	}

	public void setIdSvr(Integer idSvr) {
		this.idSvr = idSvr;
	}

	public String getApellidos() {
		return this.apellidos;
	}

	public void setApellidos(String apellidos) {
		this.apellidos = apellidos;
	}

	public Integer getCedula() {
		return this.cedula;
	}

	public void setCedula(Integer cedula) {
		this.cedula = cedula;
	}

	public String getCorreo() {
		return this.correo;
	}

	public void setCorreo(String correo) {
		this.correo = correo;
	}

	public String getNombres() {
		return this.nombres;
	}

	public void setNombres(String nombres) {
		this.nombres = nombres;
	}

	public String getSms() {
		return this.sms;
	}

	public void setSms(String sms) {
		this.sms = sms;
	}

	public String getTema() {
		return this.tema;
	}

	public void setTema(String tema) {
		this.tema = tema;
	}

	public Tipoestado getTipoestado() {
		return this.tipoestado;
	}

	public void setTipoestado(Tipoestado tipoestado) {
		this.tipoestado = tipoestado;
	}

	public Tiposervicio getTiposervicio() {
		return this.tiposervicio;
	}

	public void setTiposervicio(Tiposervicio tiposervicio) {
		this.tiposervicio = tiposervicio;
	}

}