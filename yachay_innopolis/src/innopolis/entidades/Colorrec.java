package innopolis.entidades;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;


/**
 * The persistent class for the colorrec database table.
 * 
 */
@Entity
@NamedQuery(name="Colorrec.findAll", query="SELECT c FROM Colorrec c")
public class Colorrec implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="COLORREC_IDCOLOREC_GENERATOR" )
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="COLORREC_IDCOLOREC_GENERATOR")
	private Integer idcolorec;

	private String color;

	private String colorcod;

	//bi-directional many-to-one association to Recursotipo
	@OneToMany(mappedBy="colorrec")
	private List<Recursotipo> recursotipos;

	public Colorrec() {
	}

	public Integer getIdcolorec() {
		return this.idcolorec;
	}

	public void setIdcolorec(Integer idcolorec) {
		this.idcolorec = idcolorec;
	}

	public String getColor() {
		return this.color;
	}

	public void setColor(String color) {
		this.color = color;
	}

	public String getColorcod() {
		return this.colorcod;
	}

	public void setColorcod(String colorcod) {
		this.colorcod = colorcod;
	}

	public List<Recursotipo> getRecursotipos() {
		return this.recursotipos;
	}

	public void setRecursotipos(List<Recursotipo> recursotipos) {
		this.recursotipos = recursotipos;
	}

	public Recursotipo addRecursotipo(Recursotipo recursotipo) {
		getRecursotipos().add(recursotipo);
		recursotipo.setColorrec(this);

		return recursotipo;
	}

	public Recursotipo removeRecursotipo(Recursotipo recursotipo) {
		getRecursotipos().remove(recursotipo);
		recursotipo.setColorrec(null);

		return recursotipo;
	}

}