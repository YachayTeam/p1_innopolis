package innopolis.entidades;

import java.sql.Timestamp;

public class DatosRecursos {
	
	private Integer id_recact;
	private Integer id_solicitud;
	private Timestamp hora_inicio;
	private Timestamp hora_fin;
	private Integer id_recurso;
	private Integer cantidad;
	
	public DatosRecursos() {}
	
	public DatosRecursos(Integer id_recact){
		this.id_recact = id_recact;
	}
	
	public DatosRecursos(Integer id_recact, Integer id_solicitud, Timestamp hora_inicio,
			Timestamp hora_fin, Integer id_recurso, Integer cantidad) {
		this.id_recact = id_recact;
		this.id_solicitud = id_solicitud;
		this.hora_inicio = hora_inicio;
		this.hora_fin = hora_fin;
		this.id_recurso = id_recurso;
		this.cantidad = cantidad;
	}
	
	public Integer getId_recact() {
		return id_recact;
	}
	
	public void setId_recact(Integer id_recact) {
		this.id_recact = id_recact;
	}
	
	public Integer getId_solicitud() {
		return id_solicitud;
	}
	
	public void setId_solicitud(Integer id_solicitud) {
		this.id_solicitud = id_solicitud;
	}
	
	public Timestamp getHora_inicio() {
		return hora_inicio;
	}
	
	public void setHora_inicio(Timestamp hora_inicio) {
		this.hora_inicio = hora_inicio;
	}
	
	public Timestamp getHora_fin() {
		return hora_fin;
	}
	
	public void setHora_fin(Timestamp hora_fin) {
		this.hora_fin = hora_fin;
	}
	
	public Integer getId_recurso() {
		return id_recurso;
	}
	
	public void setId_recurso(Integer id_recurso) {
		this.id_recurso = id_recurso;
	}
	
	public Integer getCantidad() {
		return cantidad;
	}
	
	public void setCantidad(Integer cantidad) {
		this.cantidad = cantidad;
	}
}
