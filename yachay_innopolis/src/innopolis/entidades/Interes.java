package innopolis.entidades;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;


/**
 * The persistent class for the intereses database table.
 * 
 */
@Entity
@Table(name="intereses")
@NamedQuery(name="Interes.findAll", query="SELECT i FROM Interes i")
public class Interes implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="INTERESES_IDINTERES_GENERATOR", sequenceName="SEQ_INTERESES", allocationSize=1)
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="INTERESES_IDINTERES_GENERATOR")
	@Column(name="id_interes")
	private Integer idInteres;

	@Column(name="nombre_int")
	private String nombreInt;

	//bi-directional many-to-one association to Interesesmid
	@OneToMany(mappedBy="interes")
	private List<Interesesmid> interesesmids;

	public Interes() {
	}

	public Integer getIdInteres() {
		return this.idInteres;
	}

	public void setIdInteres(Integer idInteres) {
		this.idInteres = idInteres;
	}

	public String getNombreInt() {
		return this.nombreInt;
	}

	public void setNombreInt(String nombreInt) {
		this.nombreInt = nombreInt;
	}

	public List<Interesesmid> getInteresesmids() {
		return this.interesesmids;
	}

	public void setInteresesmids(List<Interesesmid> interesesmids) {
		this.interesesmids = interesesmids;
	}

	public Interesesmid addInteresesmid(Interesesmid interesesmid) {
		getInteresesmids().add(interesesmid);
		interesesmid.setInteres(this);

		return interesesmid;
	}

	public Interesesmid removeInteresesmid(Interesesmid interesesmid) {
		getInteresesmids().remove(interesesmid);
		interesesmid.setInteres(null);

		return interesesmid;
	}

}