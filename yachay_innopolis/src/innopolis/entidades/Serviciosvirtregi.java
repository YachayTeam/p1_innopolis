package innopolis.entidades;

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

	//bi-directional many-to-one association to Usuario
	@ManyToOne
	@JoinColumn(name="id_usr")
	private Usuario usuario;

	public Serviciosvirtregi() {
	}

	public Integer getIdSvr() {
		return this.idSvr;
	}

	public void setIdSvr(Integer idSvr) {
		this.idSvr = idSvr;
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

	public Usuario getUsuario() {
		return this.usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

}