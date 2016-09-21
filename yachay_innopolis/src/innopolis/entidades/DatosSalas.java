package innopolis.entidades;

import java.sql.Timestamp;

public class DatosSalas {
	
	private Integer id_salact;
	private Integer id_evento;
	private Timestamp hora_inicio;
	private Timestamp hora_fin;
	private Integer id_sala;
	
	public DatosSalas() {}
	
	public DatosSalas(Integer id_salact){
		this.id_salact = id_salact;
	}
	
	public DatosSalas(Integer id_salact, Integer id_evento, Timestamp hora_inicio,
			Timestamp hora_fin, Integer id_sala) {
		this.id_salact = id_salact;
		this.id_evento = id_evento;
		this.hora_inicio = hora_inicio;
		this.hora_fin = hora_fin;
		this.id_sala = id_sala;
	}
	
	public Integer getId_salact() {
		return id_salact;
	}
	
	public void setId_salact(Integer id_salact) {
		this.id_salact = id_salact;
	}
	
	public Integer getId_evento() {
		return id_evento;
	}
	
	public void setId_evento(Integer id_evento) {
		this.id_evento = id_evento;
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
	
	public Integer getId_sala() {
		return id_sala;
	}
	public void setId_sala(Integer id_sala) {
		this.id_sala = id_sala;
	}
	
	
	
}
