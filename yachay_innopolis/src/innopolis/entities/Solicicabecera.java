package innopolis.entities;

import java.io.Serializable;
import javax.persistence.*;
import java.sql.Time;
import java.util.Date;
import java.util.List;


/**
 * The persistent class for the solicicabecera database table.
 * 
 */
@Entity
@NamedQuery(name="Solicicabecera.findAll", query="SELECT s FROM Solicicabecera s")
public class Solicicabecera implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="id_solcab")
	private Integer idSolcab;

	private String actividad;

	private Integer capacidad;

	private String direccion;

	@Temporal(TemporalType.DATE)
	private Date fecha;

	private Time horafin;

	private Time horainicio;

	private String justificacion;

	private String objetivo;

	private String sms;

	//bi-directional many-to-one association to Evento
	@OneToMany(mappedBy="solicicabecera")
	private List<Evento> eventos;

	//bi-directional many-to-one association to Soliciestado
	@ManyToOne
	@JoinColumn(name="id_solest")
	private Soliciestado soliciestado;

	//bi-directional many-to-one association to Solicidetalle
	@OneToMany(mappedBy="solicicabecera", cascade=CascadeType.ALL)
	private List<Solicidetalle> solicidetalles;

	public Solicicabecera() {
	}

	public Integer getIdSolcab() {
		return this.idSolcab;
	}

	public void setIdSolcab(Integer idSolcab) {
		this.idSolcab = idSolcab;
	}

	public String getActividad() {
		return this.actividad;
	}

	public void setActividad(String actividad) {
		this.actividad = actividad;
	}

	public Integer getCapacidad() {
		return this.capacidad;
	}

	public void setCapacidad(Integer capacidad) {
		this.capacidad = capacidad;
	}

	public String getDireccion() {
		return this.direccion;
	}

	public void setDireccion(String direccion) {
		this.direccion = direccion;
	}

	public Date getFecha() {
		return this.fecha;
	}

	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}

	public Time getHorafin() {
		return this.horafin;
	}

	public void setHorafin(Time horafin) {
		this.horafin = horafin;
	}

	public Time getHorainicio() {
		return this.horainicio;
	}

	public void setHorainicio(Time horainicio) {
		this.horainicio = horainicio;
	}

	public String getJustificacion() {
		return this.justificacion;
	}

	public void setJustificacion(String justificacion) {
		this.justificacion = justificacion;
	}

	public String getObjetivo() {
		return this.objetivo;
	}

	public void setObjetivo(String objetivo) {
		this.objetivo = objetivo;
	}

	public String getSms() {
		return this.sms;
	}

	public void setSms(String sms) {
		this.sms = sms;
	}

	public List<Evento> getEventos() {
		return this.eventos;
	}

	public void setEventos(List<Evento> eventos) {
		this.eventos = eventos;
	}

	public Evento addEvento(Evento evento) {
		getEventos().add(evento);
		evento.setSolicicabecera(this);

		return evento;
	}

	public Evento removeEvento(Evento evento) {
		getEventos().remove(evento);
		evento.setSolicicabecera(null);

		return evento;
	}

	public Soliciestado getSoliciestado() {
		return this.soliciestado;
	}

	public void setSoliciestado(Soliciestado soliciestado) {
		this.soliciestado = soliciestado;
	}

	public List<Solicidetalle> getSolicidetalles() {
		return this.solicidetalles;
	}

	public void setSolicidetalles(List<Solicidetalle> solicidetalles) {
		this.solicidetalles = solicidetalles;
	}

	public Solicidetalle addSolicidetalle(Solicidetalle solicidetalle) {
		getSolicidetalles().add(solicidetalle);
		solicidetalle.setSolicicabecera(this);

		return solicidetalle;
	}

	public Solicidetalle removeSolicidetalle(Solicidetalle solicidetalle) {
		getSolicidetalles().remove(solicidetalle);
		solicidetalle.setSolicicabecera(null);

		return solicidetalle;
	}

}