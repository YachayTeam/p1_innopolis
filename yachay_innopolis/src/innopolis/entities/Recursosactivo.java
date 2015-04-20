package innopolis.entities;

import java.io.Serializable;
import javax.persistence.*;
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
	private long idRecact;

	@Temporal(TemporalType.DATE)
	private Date fecha;

	@Temporal(TemporalType.DATE)
	@Column(name="hora_fin")
	private Date horaFin;

	@Temporal(TemporalType.DATE)
	@Column(name="hora_inicio")
	private Date horaInicio;

	@Column(name="id_recurso")
	private Integer idRecurso;

	@Column(name="id_solicitud")
	private Integer idSolicitud;

	public Recursosactivo() {
	}

	public long getIdRecact() {
		return this.idRecact;
	}

	public void setIdRecact(long idRecact) {
		this.idRecact = idRecact;
	}

	public Date getFecha() {
		return this.fecha;
	}

	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}

	public Date getHoraFin() {
		return this.horaFin;
	}

	public void setHoraFin(Date horaFin) {
		this.horaFin = horaFin;
	}

	public Date getHoraInicio() {
		return this.horaInicio;
	}

	public void setHoraInicio(Date horaInicio) {
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