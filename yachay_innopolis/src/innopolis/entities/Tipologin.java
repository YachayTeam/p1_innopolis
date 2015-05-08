package innopolis.entities;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;


/**
 * The persistent class for the tipologin database table.
 * 
 */
@Entity
@NamedQuery(name="Tipologin.findAll", query="SELECT t FROM Tipologin t")
public class Tipologin implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="TIPOLOGIN_IDTIPOLOGIN_GENERATOR", sequenceName="SEQ_TIPOLOGIN", allocationSize=1)
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="TIPOLOGIN_IDTIPOLOGIN_GENERATOR")
	@Column(name="id_tipologin")
	private Integer idTipologin;

	private String descripcion;

	private String tipologin;

	//bi-directional many-to-one association to Tipousr
	@OneToMany(mappedBy="tipologin")
	private List<Tipousr> tipousrs;

	public Tipologin() {
	}

	public Integer getIdTipologin() {
		return this.idTipologin;
	}

	public void setIdTipologin(Integer idTipologin) {
		this.idTipologin = idTipologin;
	}

	public String getDescripcion() {
		return this.descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public String getTipologin() {
		return this.tipologin;
	}

	public void setTipologin(String tipologin) {
		this.tipologin = tipologin;
	}

	public List<Tipousr> getTipousrs() {
		return this.tipousrs;
	}

	public void setTipousrs(List<Tipousr> tipousrs) {
		this.tipousrs = tipousrs;
	}

	public Tipousr addTipousr(Tipousr tipousr) {
		getTipousrs().add(tipousr);
		tipousr.setTipologin(this);

		return tipousr;
	}

	public Tipousr removeTipousr(Tipousr tipousr) {
		getTipousrs().remove(tipousr);
		tipousr.setTipologin(null);

		return tipousr;
	}

}