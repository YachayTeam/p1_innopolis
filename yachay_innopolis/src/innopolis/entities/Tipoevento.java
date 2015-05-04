package innopolis.entities;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;


/**
 * The persistent class for the tipoevento database table.
 * 
 */
@Entity
@NamedQuery(name="Tipoevento.findAll", query="SELECT t FROM Tipoevento t")
public class Tipoevento implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="TIPOEVENTO_IDTIPOEVENTO_GENERATOR", sequenceName="SEQ_TIPOEVENTO", allocationSize=1)
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="TIPOEVENTO_IDTIPOEVENTO_GENERATOR")
	@Column(name="id_tipo_evento")
	private Integer idTipoEvento;

	private String descripcion;

	private String tipo;

	//bi-directional many-to-one association to Evento
	@OneToMany(mappedBy="tipoevento")
	private List<Evento> eventos;

	public Tipoevento() {
	}

	public Integer getIdTipoEvento() {
		return this.idTipoEvento;
	}

	public void setIdTipoEvento(Integer idTipoEvento) {
		this.idTipoEvento = idTipoEvento;
	}

	public String getDescripcion() {
		return this.descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
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
		evento.setTipoevento(this);

		return evento;
	}

	public Evento removeEvento(Evento evento) {
		getEventos().remove(evento);
		evento.setTipoevento(null);

		return evento;
	}

}