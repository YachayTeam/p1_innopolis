package innopolis.entidades;

import java.io.Serializable;
import javax.persistence.*;
import java.sql.Timestamp;


/**
 * The persistent class for the sugerencias database table.
 * 
 */
@Entity
@Table(name="sugerencias")
@NamedQuery(name="Sugerencia.findAll", query="SELECT s FROM Sugerencia s")
public class Sugerencia implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="SUGERENCIAS_IDSUGERENCIA_GENERATOR", sequenceName="SEQ_SUGERENCIA", allocationSize=1)
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="SUGERENCIAS_IDSUGERENCIA_GENERATOR")
	@Column(name="id_sugerencia")
	private Integer idSugerencia;

	private Timestamp fecha;

	private String sms;

	private String sugerencia;

	//bi-directional many-to-one association to Usuario
	@ManyToOne
	@JoinColumn(name="id_usr")
	private Usuario usuario;

	public Sugerencia() {
	}

	public Integer getIdSugerencia() {
		return this.idSugerencia;
	}

	public void setIdSugerencia(Integer idSugerencia) {
		this.idSugerencia = idSugerencia;
	}

	public Timestamp getFecha() {
		return this.fecha;
	}

	public void setFecha(Timestamp fecha) {
		this.fecha = fecha;
	}

	public String getSms() {
		return this.sms;
	}

	public void setSms(String sms) {
		this.sms = sms;
	}

	public String getSugerencia() {
		return this.sugerencia;
	}

	public void setSugerencia(String sugerencia) {
		this.sugerencia = sugerencia;
	}

	public Usuario getUsuario() {
		return this.usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

}