package innopolis.entidades;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;


/**
 * The persistent class for the salas database table.
 * 
 */
@Entity
@Table(name="salas")
@NamedQuery(name="Sala.findAll", query="SELECT s FROM Sala s")
public class Sala implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="SALAS_IDSALA_GENERATOR", sequenceName="SEQ_SALAS", allocationSize=1)
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="SALAS_IDSALA_GENERATOR")
	@Column(name="id_sala")
	private Integer idSala;

	private Integer capacidad;

	private String descripcion;

	private String imagen;

	private String tipo;

	//bi-directional many-to-one association to Evento
	@OneToMany(mappedBy="sala")
	private List<Evento> eventos;

	//bi-directional many-to-one association to Colorsala
	@ManyToOne
	@JoinColumn(name="id_colorsala")
	private Colorsala colorsala;

	//bi-directional many-to-one association to Saladisponible
	@ManyToOne
	@JoinColumn(name="idsaladisponible")
	private Saladisponible saladisponible;

	public Sala() {
	}

	public Integer getIdSala() {
		return this.idSala;
	}

	public void setIdSala(Integer idSala) {
		this.idSala = idSala;
	}

	public Integer getCapacidad() {
		return this.capacidad;
	}

	public void setCapacidad(Integer capacidad) {
		this.capacidad = capacidad;
	}

	public String getDescripcion() {
		return this.descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public String getImagen() {
		return this.imagen;
	}

	public void setImagen(String imagen) {
		this.imagen = imagen;
	}

	public String getTipo() {
		return this.tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	public List<Evento> getEventos() {
		return this.eventos;
	}

	public void setEventos(List<Evento> eventos) {
		this.eventos = eventos;
	}

	public Evento addEvento(Evento evento) {
		getEventos().add(evento);
		evento.setSala(this);

		return evento;
	}

	public Evento removeEvento(Evento evento) {
		getEventos().remove(evento);
		evento.setSala(null);

		return evento;
	}

	public Colorsala getColorsala() {
		return this.colorsala;
	}

	public void setColorsala(Colorsala colorsala) {
		this.colorsala = colorsala;
	}

	public Saladisponible getSaladisponible() {
		return this.saladisponible;
	}

	public void setSaladisponible(Saladisponible saladisponible) {
		this.saladisponible = saladisponible;
	}

}