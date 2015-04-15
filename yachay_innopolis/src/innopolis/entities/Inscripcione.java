package innopolis.entities;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;


/**
 * The persistent class for the inscripciones database table.
 * 
 */
@Entity
@Table(name="inscripciones")
@NamedQuery(name="Inscripcione.findAll", query="SELECT i FROM Inscripcione i")
public class Inscripcione implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="INSCRIPCIONES_IDINSCRIPCION_GENERATOR", sequenceName="SEQ_INSCRIPCIONES", allocationSize=1)
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="INSCRIPCIONES_IDINSCRIPCION_GENERATOR")
	@Column(name="id_inscripcion")
	private Integer idInscripcion;

	private String descripcion;

	@Temporal(TemporalType.DATE)
	@Column(name="fecha_inscripcion")
	private Date fechaInscripcion;

	@Column(name="id_usuario")
	private Integer idUsuario;

	@Column(name="imagen_pago")
	private String imagenPago;

	//bi-directional many-to-one association to Evento
	@ManyToOne
	@JoinColumn(name="id_evento")
	private Evento evento;

	public Inscripcione() {
	}

	public Integer getIdInscripcion() {
		return this.idInscripcion;
	}

	public void setIdInscripcion(Integer idInscripcion) {
		this.idInscripcion = idInscripcion;
	}

	public String getDescripcion() {
		return this.descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public Date getFechaInscripcion() {
		return this.fechaInscripcion;
	}

	public void setFechaInscripcion(Date fechaInscripcion) {
		this.fechaInscripcion = fechaInscripcion;
	}

	public Integer getIdUsuario() {
		return this.idUsuario;
	}

	public void setIdUsuario(Integer idUsuario) {
		this.idUsuario = idUsuario;
	}

	public String getImagenPago() {
		return this.imagenPago;
	}

	public void setImagenPago(String imagenPago) {
		this.imagenPago = imagenPago;
	}

	public Evento getEvento() {
		return this.evento;
	}

	public void setEvento(Evento evento) {
		this.evento = evento;
	}

}