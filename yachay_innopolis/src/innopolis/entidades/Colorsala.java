package innopolis.entidades;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;


/**
 * The persistent class for the colorsala database table.
 * 
 */
@Entity
@NamedQuery(name="Colorsala.findAll", query="SELECT c FROM Colorsala c")
public class Colorsala implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="COLORSALA_IDCOLORSALA_GENERATOR" )
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="COLORSALA_IDCOLORSALA_GENERATOR")
	@Column(name="id_colorsala")
	private Integer idColorsala;

	private String color;

	private String colorcod;

	//bi-directional many-to-one association to Sala
	@OneToMany(mappedBy="colorsala")
	private List<Sala> salas;

	public Colorsala() {
	}

	public Integer getIdColorsala() {
		return this.idColorsala;
	}

	public void setIdColorsala(Integer idColorsala) {
		this.idColorsala = idColorsala;
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

	public List<Sala> getSalas() {
		return this.salas;
	}

	public void setSalas(List<Sala> salas) {
		this.salas = salas;
	}

	public Sala addSala(Sala sala) {
		getSalas().add(sala);
		sala.setColorsala(this);

		return sala;
	}

	public Sala removeSala(Sala sala) {
		getSalas().remove(sala);
		sala.setColorsala(null);

		return sala;
	}

}