package innopolis.entities;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;
import java.util.List;


/**
 * The persistent class for the eventos database table.
 * 
 */
@Entity
@Table(name="eventos")
@NamedQuery(name="Evento.findAll", query="SELECT e FROM Evento e")
public class Evento implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="EVENTOS_IDEVENTO_GENERATOR", sequenceName="SEQ_EVENTOS", allocationSize=1)
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="EVENTOS_IDEVENTO_GENERATOR")
	@Column(name="id_evento")
	private Integer idEvento;

	private double costo;

	private String descripcion;

	@Temporal(TemporalType.DATE)
	@Column(name="fecha_f")
	private Date fechaF;

	@Temporal(TemporalType.DATE)
	@Column(name="fecha_i")
	private Date fechaI;

	private String imagen;

	private String lugar;

	private String nombre;

	//bi-directional many-to-one association to Solicicabecera
	@ManyToOne
	@JoinColumn(name="id_solcab")
	private Solicicabecera solicicabecera;

	//bi-directional many-to-one association to Tipoevento
	@ManyToOne
	@JoinColumn(name="id_tipo_evento")
	private Tipoevento tipoevento;

	//bi-directional many-to-one association to Inscripcione
	@OneToMany(mappedBy="evento")
	private List<Inscripcione> inscripciones;

	public Evento() {
	}

	public Integer getIdEvento() {
		return this.idEvento;
	}

	public void setIdEvento(Integer idEvento) {
		this.idEvento = idEvento;
	}

	public double getCosto() {
		return this.costo;
	}

	public void setCosto(double costo) {
		this.costo = costo;
	}

	public String getDescripcion() {
		return this.descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public Date getFechaF() {
		return this.fechaF;
	}

	public void setFechaF(Date fechaF) {
		this.fechaF = fechaF;
	}

	public Date getFechaI() {
		return this.fechaI;
	}

	public void setFechaI(Date fechaI) {
		this.fechaI = fechaI;
	}

	public String getImagen() {
		return this.imagen;
	}

	public void setImagen(String imagen) {
		this.imagen = imagen;
	}

	public String getLugar() {
		return this.lugar;
	}

	public void setLugar(String lugar) {
		this.lugar = lugar;
	}

	public String getNombre() {
		return this.nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public Solicicabecera getSolicicabecera() {
		return this.solicicabecera;
	}

	public void setSolicicabecera(Solicicabecera solicicabecera) {
		this.solicicabecera = solicicabecera;
	}

	public Tipoevento getTipoevento() {
		return this.tipoevento;
	}

	public void setTipoevento(Tipoevento tipoevento) {
		this.tipoevento = tipoevento;
	}

	public List<Inscripcione> getInscripciones() {
		return this.inscripciones;
	}

	public void setInscripciones(List<Inscripcione> inscripciones) {
		this.inscripciones = inscripciones;
	}

	public Inscripcione addInscripcione(Inscripcione inscripcione) {
		getInscripciones().add(inscripcione);
		inscripcione.setEvento(this);

		return inscripcione;
	}

	public Inscripcione removeInscripcione(Inscripcione inscripcione) {
		getInscripciones().remove(inscripcione);
		inscripcione.setEvento(null);

		return inscripcione;
	}

}