package innopolis.controller;


import java.util.ArrayList;
import java.util.List;

import innopolis.entities.Recursotipo;
import innopolis.entities.Tiposervicio;
import innopolis.manager.ManagerRecursosVirtuales;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.model.SelectItem;

@SessionScoped
@ManagedBean
public class TiposervicioBean {
	private ManagerRecursosVirtuales managerservirt= new ManagerRecursosVirtuales();
	private Integer idTp;
	private String nombreServicio;
	
	public Integer getIdTp() {
		return idTp;
	}
	public void setIdTp(Integer idTp) {
		this.idTp = idTp;
	}
	public String getNombreServicio() {
		return nombreServicio;
	}
	public void setNombreServicio(String nombreServicio) {
		this.nombreServicio = nombreServicio;
	}

}
