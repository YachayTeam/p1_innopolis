package innopolis.controller;

import innopolis.manager.ManagerInnopolis;

import java.util.Date;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;


@SessionScoped
@ManagedBean
public class RecursosBean {
	
	private ManagerInnopolis manager;
	
	private Long idRecurso;

	private String campoact;

	private String descripcion;

	private String lugar;

	private String nombre;
	
	public RecursosBean(){
		manager = new ManagerInnopolis();
	}

	public Long getIdRecurso() {
		return idRecurso;
	}

	public void setIdRecurso(Long idRecurso) {
		this.idRecurso = idRecurso;
	}

	public String getCampoact() {
		return campoact;
	}

	public void setCampoact(String campoact) {
		this.campoact = campoact;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public String getLugar() {
		return lugar;
	}

	public void setLugar(String lugar) {
		this.lugar = lugar;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}


	
	
	
}
