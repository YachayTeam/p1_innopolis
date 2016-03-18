package innopolis.entidades;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the interesesmid database table.
 * 
 */
@Entity
@NamedQuery(name="Interesesmid.findAll", query="SELECT i FROM Interesesmid i")
public class Interesesmid implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="INTERESESMID_IDINTMID_GENERATOR", sequenceName="SEQ_INTERESESMID", allocationSize=1)
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="INTERESESMID_IDINTMID_GENERATOR")
	@Column(name="id_intmid")
	private Integer idIntmid;

	//bi-directional many-to-one association to Interes
	@ManyToOne
	@JoinColumn(name="id_interes")
	private Interes interes;

	//bi-directional many-to-one association to Usuario
	@ManyToOne
	@JoinColumn(name="id_usr")
	private Usuario usuario;

	public Interesesmid() {
	}

	public Integer getIdIntmid() {
		return this.idIntmid;
	}

	public void setIdIntmid(Integer idIntmid) {
		this.idIntmid = idIntmid;
	}

	public Interes getInteres() {
		return this.interes;
	}

	public void setInteres(Interes interes) {
		this.interes = interes;
	}

	public Usuario getUsuario() {
		return this.usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

}