package innopolis.controller;

import java.util.Date;

import innopolis.entities.Serviciosvirtregi;
import innopolis.entities.Tipoestado;
import innopolis.entities.Tiposervicio;
import innopolis.manager.ManagerRecursosVirtuales;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

@SessionScoped
@ManagedBean
public class ServiciosVirtualesBean {
	private ManagerRecursosVirtuales managerservirt= new ManagerRecursosVirtuales();
	private Integer idSvr;
	private Integer idtipoestado;
	private Integer idtiposervicio;
	private String apellidos;
	private Long cedula;
	private String correo;
	private String nombres;
	private String tema;
	
	public Integer getIdSvr() {
		return idSvr;
	}
	public void setIdSvr(Integer idSvr) {
		this.idSvr = idSvr;
	}
	public Integer getIdtipoestado() {
		return idtipoestado;
	}
	public void setIdtipoestado(Integer idtipoestado) {
		this.idtipoestado = idtipoestado;
	}
	public Integer getIdtiposervicio() {
		return idtiposervicio;
	}
	public void setIdtiposervicio(Integer idtiposervicio) {
		this.idtiposervicio = idtiposervicio;
	}
	public String getApellidos() {
		return apellidos;
	}
	public void setApellidos(String apellidos) {
		this.apellidos = apellidos;
	}
	public Long getCedula() {
		return cedula;
	}
	public void setCedula(Long cedula) {
		this.cedula = cedula;
	}
	public String getCorreo() {
		return correo;
	}
	public void setCorreo(String correo) {
		this.correo = correo;
	}
	public String getNombres() {
		return nombres;
	}
	public void setNombres(String nombres) {
		this.nombres = nombres;
	}
	public String getTema() {
		return tema;
	}
	public void setTema(String tema) {
		this.tema = tema;
	}	
	
}
