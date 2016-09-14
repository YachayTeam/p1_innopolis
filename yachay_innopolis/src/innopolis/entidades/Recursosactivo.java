package innopolis.entidades;

import java.io.Serializable;
import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Date;


/**
 * The persistent class for the recursosactivos database table.
 * 
 */
@Entity
@Table(name="recursosactivos")
@NamedQuery(name="Recursosactivo.findAll", query="SELECT r FROM Recursosactivo r")
public class Recursosactivo implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="RECURSOSACTIVOS_IDRECACT_GENERATOR", sequenceName="SEQ_RECURSOACTIVO", allocationSize=1)
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="RECURSOSACTIVOS_IDRECACT_GENERATOR")
	@Column(name="id_recact")
	private Integer idRecact;

	private Integer cantidad;

	@Temporal(TemporalType.DATE)
	private Date fecha;

	@Column(name="hora_fin")
	private Timestamp horaFin;

	@Column(name="hora_inicio")
	private Timestamp horaInicio;

	@Column(name="id_recurso")
	private Integer idRecurso;

	@Column(name="id_solicitud")
	private Integer idSolicitud;

	public Recursosactivo() {
	}

	public Integer getIdRecact() {
		return idRecact;
	}
	
	public void setIdRecact(Integer idRecact) {
		this.idRecact = idRecact;
	}

	public Integer getCantidad() {
		return this.cantidad;
	}

	public void setCantidad(Integer cantidad) {
		this.cantidad = cantidad;
	}

	public Date getFecha() {
		return this.fecha;
	}

	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}

	public Timestamp getHoraFin() {
		return this.horaFin;
	}

	public void setHoraFin(Timestamp horaFin) {
		this.horaFin = horaFin;
	}

	public Timestamp getHoraInicio() {
		return this.horaInicio;
	}

	public void setHoraInicio(Timestamp horaInicio) {
		this.horaInicio = horaInicio;
	}

	public Integer getIdRecurso() {
		return this.idRecurso;
	}

	public void setIdRecurso(Integer idRecurso) {
		this.idRecurso = idRecurso;
	}

	public Integer getIdSolicitud() {
		return this.idSolicitud;
	}

	public void setIdSolicitud(Integer idSolicitud) {
		this.idSolicitud = idSolicitud;
	}

}