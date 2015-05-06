package innopolis.controller;

import java.io.Serializable;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

@SessionScoped
@ManagedBean
public class TipoestadoBean implements Serializable{
	private static final long serialVersionUID = 1L;
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
