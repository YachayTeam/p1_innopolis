package innopolis.entidades;

import java.io.Serializable;

import javax.persistence.*;

import java.util.List;


/**
 * The persistent class for the saladisponible database table.
 * 
 */
@Entity
@NamedQuery(name="Saladisponible.findAll", query="SELECT s FROM Saladisponible s")
public class Saladisponible implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="SALADISPONIBLE_IDSALADISPONIBLE_GENERATOR", sequenceName="SEQ_SALA_DISPONIBLE", allocationSize=1)
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="SALADISPONIBLE_IDSALADISPONIBLE_GENERATOR")
	@Column(name="idsaladisponible")
	private Integer idsaladisponible;

	private String disponible;

	//bi-directional many-to-one association to Sala
	@OneToMany(mappedBy="saladisponible")
	private List<Sala> salas;

	public Saladisponible() {
	}

	public Integer getIdsaladisponible() {
		return this.idsaladisponible;
	}

	public void setIdsaladisponible(Integer idsaladisponible) {
		this.idsaladisponible = idsaladisponible;
	}

	public String getDisponible() {
		return this.disponible;
	}

	public void setDisponible(String disponible) {
		this.disponible = disponible;
	}

	public List<Sala> getSalas() {
		return this.salas;
	}

	public void setSalas(List<Sala> salas) {
		this.salas = salas;
	}

	public Sala addSala(Sala sala) {
		getSalas().add(sala);
		sala.setSaladisponible(this);

		return sala;
	}

	public Sala removeSala(Sala sala) {
		getSalas().remove(sala);
		sala.setSaladisponible(null);

		return sala;
	}

}