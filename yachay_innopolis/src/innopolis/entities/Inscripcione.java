package innopolis.entities;

import java.io.Serializable;
import javax.persistence.*;
import java.sql.Timestamp;


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

	private String apellido;

	private String correo;

	private Integer estado;

	@Column(name="fecha_inscripcion")
	private Timestamp fechaInscripcion;

	@Column(name="id_usuario")
	private Integer idUsuario;

	@Column(name="imagen_pago")
	private String imagenPago;

	private String nombre;

	private String observacion;

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

	public String getApellido() {
		return this.apellido;
	}

	public void setApellido(String apellido) {
		this.apellido = apellido;
	}

	public String getCorreo() {
		return this.correo;
	}

	public void setCorreo(String correo) {
		this.correo = correo;
	}

	public Integer getEstado() {
		return this.estado;
	}

	public void setEstado(Integer estado) {
		this.estado = estado;
	}

	public Timestamp getFechaInscripcion() {
		return this.fechaInscripcion;
	}

	public void setFechaInscripcion(Timestamp fechaInscripcion) {
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

	public String getNombre() {
		return this.nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getObservacion() {
		return this.observacion;
	}

	public void setObservacion(String observacion) {
		this.observacion = observacion;
	}

	public Evento getEvento() {
		return this.evento;
	}

	public void setEvento(Evento evento) {
		this.evento = evento;
	}

}