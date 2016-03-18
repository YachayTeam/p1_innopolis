package innopolis.entidades;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;


/**
 * The persistent class for the camposnuevos database table.
 * 
 */
@Entity
@Table(name="camposnuevos")
@NamedQuery(name="Camposnuevo.findAll", query="SELECT c FROM Camposnuevo c")
public class Camposnuevo implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="CAMPOSNUEVOS_IDCAMPO_GENERATOR", sequenceName="SEQ_CAMPOSNUEVOS", allocationSize=1)
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="CAMPOSNUEVOS_IDCAMPO_GENERATOR")
	@Column(name="id_campo")
	private Integer idCampo;

	private String campo;

	private String etiqueta;

	//bi-directional many-to-one association to Interinscam
	@OneToMany(mappedBy="camposnuevo")
	private List<Interinscam> interinscams;

	public Camposnuevo() {
	}

	public Integer getIdCampo() {
		return this.idCampo;
	}

	public void setIdCampo(Integer idCampo) {
		this.idCampo = idCampo;
	}

	public String getCampo() {
		return this.campo;
	}

	public void setCampo(String campo) {
		this.campo = campo;
	}

	public String getEtiqueta() {
		return this.etiqueta;
	}

	public void setEtiqueta(String etiqueta) {
		this.etiqueta = etiqueta;
	}

	public List<Interinscam> getInterinscams() {
		return this.interinscams;
	}

	public void setInterinscams(List<Interinscam> interinscams) {
		this.interinscams = interinscams;
	}

	public Interinscam addInterinscam(Interinscam interinscam) {
		getInterinscams().add(interinscam);
		interinscam.setCamposnuevo(this);

		return interinscam;
	}

	public Interinscam removeInterinscam(Interinscam interinscam) {
		getInterinscams().remove(interinscam);
		interinscam.setCamposnuevo(null);

		return interinscam;
	}

}