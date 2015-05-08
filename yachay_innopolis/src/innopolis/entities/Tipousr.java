package innopolis.entities;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the tipousr database table.
 * 
 */
@Entity
@NamedQuery(name="Tipousr.findAll", query="SELECT t FROM Tipousr t")
public class Tipousr implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="TIPOUSR_IDTIPUSR_GENERATOR", sequenceName="SEQ_TIPOUSR", allocationSize=1)
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="TIPOUSR_IDTIPUSR_GENERATOR")
	@Column(name="id_tipusr")
	private Integer idTipusr;

	//bi-directional many-to-one association to Tipologin
	@ManyToOne
	@JoinColumn(name="id_tipologin")
	private Tipologin tipologin;

	//bi-directional many-to-one association to Usuario
	@ManyToOne
	@JoinColumn(name="id_usr")
	private Usuario usuario;

	public Tipousr() {
	}

	public Integer getIdTipusr() {
		return this.idTipusr;
	}

	public void setIdTipusr(Integer idTipusr) {
		this.idTipusr = idTipusr;
	}

	public Tipologin getTipologin() {
		return this.tipologin;
	}

	public void setTipologin(Tipologin tipologin) {
		this.tipologin = tipologin;
	}

	public Usuario getUsuario() {
		return this.usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

}