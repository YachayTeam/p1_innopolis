package innopolis.controller;

import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;

import innopolis.entities.Recursotipo;
import innopolis.manager.ManagerReservas;

@ManagedBean
@SessionScoped
public class RecursotipoBean {
	private ManagerReservas manager;
	
	private Integer idRectipo;
	private String tipo;
	private List<Recursotipo> listado;
	
	public RecursotipoBean() {
		manager  = new ManagerReservas();
	}
	
	public Integer getIdRectipo() {
		return idRectipo;
	}
	
	public String getTipo() {
		return tipo;
	}
	
	public void setIdRectipo(Integer idRectipo) {
		this.idRectipo = idRectipo;
	}
	
	public void setTipo(String tipo) {
		this.tipo = tipo;
	}
	
	public List<Recursotipo> getListado() {
		listado = manager.findAllTipoRecurso();
		return listado;
	}
	
	public String crearNuevoRecursoTipo(){
		try {
			manager.insertarTipoRecurso(getTipo());
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Creado correctamente",null));
		} catch (Exception e) {
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,"Error al ingresar nuevo tipo",null));
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,e.getMessage(),null));
		}
		return "";
	}
	
	public String cargarDatosRecTipo(Recursotipo tipo){
		setIdRectipo(tipo.getIdRectipo());
		setTipo(tipo.getTipo());
		return "";
	} 
	
	public String modificarRecursoTipo(){
		try {
			manager.editarRecursoTipo(getIdRectipo(), getTipo());
			setTipo("");setIdRectipo(0);
		} catch (Exception e) {
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,"Error al modificar tipo de recurso",null));
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,e.getMessage(),null));
		}
		
		return "";
	}
	
	public String cancelarModificacion(){
		setTipo("");setIdRectipo(0);
		return "";
	}
}
