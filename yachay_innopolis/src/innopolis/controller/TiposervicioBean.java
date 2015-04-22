package innopolis.controller;


import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

@SessionScoped
@ManagedBean
public class TiposervicioBean {
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
