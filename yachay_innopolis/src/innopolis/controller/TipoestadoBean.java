package innopolis.controller;


import innopolis.manager.ManagerRecursosVirtuales;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

@SessionScoped
@ManagedBean
public class TipoestadoBean {
	private ManagerRecursosVirtuales managerservirt= new ManagerRecursosVirtuales();
	private Integer idEstado;
	private String nombreestado;
	public Integer getIdEstado() {
		return idEstado;
	}
	public void setIdEstado(Integer idEstado) {
		this.idEstado = idEstado;
	}
	public String getNombreestado() {
		return nombreestado;
	}
	public void setNombreestado(String nombreestado) {
		this.nombreestado = nombreestado;
	}	
	
}
