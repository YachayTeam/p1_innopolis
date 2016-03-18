package innopolis.entidades;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;


/**
 * The persistent class for the coloreve database table.
 * 
 */
@Entity
@NamedQuery(name="Coloreve.findAll", query="SELECT c FROM Coloreve c")
public class Coloreve implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="COLOREVE_IDCOLOREVE_GENERATOR" )
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="COLOREVE_IDCOLOREVE_GENERATOR")
	private Integer idcoloreve;

	private String color;

	private String colorcod;

	//bi-directional many-to-one association to Tipoevento
	@OneToMany(mappedBy="coloreve")
	private List<Tipoevento> tipoeventos;

	public Coloreve() {
	}

	public Integer getIdcoloreve() {
		return this.idcoloreve;
	}

	public void setIdcoloreve(Integer idcoloreve) {
		this.idcoloreve = idcoloreve;
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

	public List<Tipoevento> getTipoeventos() {
		return this.tipoeventos;
	}

	public void setTipoeventos(List<Tipoevento> tipoeventos) {
		this.tipoeventos = tipoeventos;
	}

	public Tipoevento addTipoevento(Tipoevento tipoevento) {
		getTipoeventos().add(tipoevento);
		tipoevento.setColoreve(this);

		return tipoevento;
	}

	public Tipoevento removeTipoevento(Tipoevento tipoevento) {
		getTipoeventos().remove(tipoevento);
		tipoevento.setColoreve(null);

		return tipoevento;
	}

}