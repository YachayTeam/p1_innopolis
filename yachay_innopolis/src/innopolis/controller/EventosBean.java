package innopolis.controller;

import java.util.Date;

import innopolis.entities.Solicicabecera;
import innopolis.entities.Tipoevento;
import innopolis.manager.ManagerInnopolis;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

@SessionScoped
@ManagedBean
public class EventosBean {
	private ManagerInnopolis minnopolis= new ManagerInnopolis();
	private Long idEvento;
	private Long idTipoEvento;
	private Long idSolicitudCab;
	private String nombre;
	private String descripcion;
	private double costo;
	private Date fechaI;
	private Date fechaF;
	private String imagen;
	private String lugar;
	private Long getIdEvento() {
		return idEvento;
	}
	private void setIdEvento(Long idEvento) {
		this.idEvento = idEvento;
	}
	private Long getIdTipoEvento() {
		return idTipoEvento;
	}
	private void setIdTipoEvento(Long idTipoEvento) {
		this.idTipoEvento = idTipoEvento;
	}
	private Long getIdSolicitudCab() {
		return idSolicitudCab;
	}
	private void setIdSolicitudCab(Long idSolicitudCab) {
		this.idSolicitudCab = idSolicitudCab;
	}
	private String getNombre() {
		return nombre;
	}
	private void setNombre(String nombre) {
		this.nombre = nombre;
	}
	private String getDescripcion() {
		return descripcion;
	}
	private void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}
	private double getCosto() {
		return costo;
	}
	private void setCosto(double costo) {
		this.costo = costo;
	}
	private Date getFechaI() {
		return fechaI;
	}
	private void setFechaI(Date fechaI) {
		this.fechaI = fechaI;
	}
	private Date getFechaF() {
		return fechaF;
	}
	private void setFechaF(Date fechaF) {
		this.fechaF = fechaF;
	}
	private String getImagen() {
		return imagen;
	}
	private void setImagen(String imagen) {
		this.imagen = imagen;
	}
	private String getLugar() {
		return lugar;
	}
	private void setLugar(String lugar) {
		this.lugar = lugar;
	}
	
}
