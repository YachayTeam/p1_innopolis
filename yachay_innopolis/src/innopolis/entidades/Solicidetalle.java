package innopolis.entidades;

import java.io.Serializable;
import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Date;


/**
 * The persistent class for the solicidetalle database table.
 * 
 */
@Entity
@NamedQuery(name="Solicidetalle.findAll", query="SELECT s FROM Solicidetalle s")
public class Solicidetalle implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="SOLICIDETALLE_IDSOLDET_GENERATOR", sequenceName="SEQ_SOLICIDETALLE", allocationSize=1)
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="SOLICIDETALLE_IDSOLDET_GENERATOR")
	@Column(name="id_soldet")
	private Integer idSoldet;

	private Integer capacidad;

	@Temporal(TemporalType.DATE)
	private Date fechadet;

	@Column(name="hora_fin")
	private Timestamp horaFin;

	@Column(name="hora_inicio")
	private Timestamp horaInicio;

	//bi-directional many-to-one association to Recurso
	@ManyToOne
	@JoinColumn(name="id_recurso")
	private Recurso recurso;

	//bi-directional many-to-one association to Solicicabecera
	@ManyToOne
	@JoinColumn(name="id_solcab")
	private Solicicabecera solicicabecera;

	public Solicidetalle() {
	}

	public Integer getIdSoldet() {
		return this.idSoldet;
	}

	public void setIdSoldet(Integer idSoldet) {
		this.idSoldet = idSoldet;
	}

	public Integer getCapacidad() {
		return this.capacidad;
	}

	public void setCapacidad(Integer capacidad) {
		this.capacidad = capacidad;
	}

	public Date getFechadet() {
		return this.fechadet;
	}

	public void setFechadet(Date fechadet) {
		this.fechadet = fechadet;
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

	public Recurso getRecurso() {
		return this.recurso;
	}

	public void setRecurso(Recurso recurso) {
		this.recurso = recurso;
	}

	public Solicicabecera getSolicicabecera() {
		return this.solicicabecera;
	}

	public void setSolicicabecera(Solicicabecera solicicabecera) {
		this.solicicabecera = solicicabecera;
	}

}