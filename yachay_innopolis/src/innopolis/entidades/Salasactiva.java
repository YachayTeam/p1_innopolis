package innopolis.entidades;

import java.io.Serializable;
import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Date;


/**
 * The persistent class for the salasactivas database table.
 * 
 */
@Entity
@Table(name="salasactivas")
@NamedQuery(name="Salasactiva.findAll", query="SELECT s FROM Salasactiva s")
public class Salasactiva implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="SALASACTIVAS_IDSALACT_GENERATOR", sequenceName="SEQ_SALASACTIVAS", allocationSize=1)
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="SALASACTIVAS_IDSALACT_GENERATOR")
	@Column(name="id_salact")
	private long idSalact;

	@Temporal(TemporalType.DATE)
	private Date fecha;

	@Column(name="hora_fin")
	private Timestamp horaFin;

	@Column(name="hora_inicio")
	private Timestamp horaInicio;

	@Column(name="id_evento")
	private Integer idEvento;

	@Column(name="id_sala")
	private Integer idSala;

	public Salasactiva() {
	}

	public long getIdSalact() {
		return this.idSalact;
	}

	public void setIdSalact(long idSalact) {
		this.idSalact = idSalact;
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

	public Integer getIdEvento() {
		return this.idEvento;
	}

	public void setIdEvento(Integer idEvento) {
		this.idEvento = idEvento;
	}

	public Integer getIdSala() {
		return this.idSala;
	}

	public void setIdSala(Integer idSala) {
		this.idSala = idSala;
	}

}