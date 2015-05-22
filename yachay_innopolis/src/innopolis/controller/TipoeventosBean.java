package innopolis.controller;

import java.io.Serializable;
import java.util.List;

import innopolis.manager.ManagerEvento;
import innopolis.entities.Tipoevento;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.faces.application.FacesMessage;

@SessionScoped
@ManagedBean
public class TipoeventosBean implements Serializable{
	//https://github.com/YachayTeam/p1_innopolis/blob/32169dd0b6e81dad04a03a741b89a956e0527ffa/yachay_innopolis/src/innopolis/manager/ManagerInnopolis.java
	private static final long serialVersionUID = 1L;
	private ManagerEvento manager = new ManagerEvento();
	private Integer id_tipoevento;
	private String tipo;
	private String descripcion;

	public TipoeventosBean() {
		manager = new ManagerEvento();
	}
	
	public Integer getId_tipoevento() {
		return id_tipoevento;
	}

	public void setId_tipoevento(Integer id_tipoevento) {
		this.id_tipoevento = id_tipoevento;
	}

	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	public String getdescripcion() {
		return descripcion;
	}

	public void setdescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public List<Tipoevento> getlistaTipoevento() {
		return manager.findAllTipoEventos();
	}
	
	public String accioninsertarTipoEvento() {
		try {
			manager.insertarTipoEvento(tipo, descripcion);
			//limpiar datos
			tipo="";
			descripcion="";
			FacesContext context = FacesContext.getCurrentInstance();
			context.addMessage(null, new FacesMessage("Registrado..!!!",
					"Evento Tipo Creado "));
		} catch (Exception e) {
			FacesContext context = FacesContext.getCurrentInstance();
			context.addMessage(null, new FacesMessage("Registrado..!!!",
					"Evento Tipo NO Creado "));
		}
		
		return "";
	}

	public String accionCargarTipoEvento(Tipoevento t) {
		id_tipoevento = t.getIdTipoEvento();
		tipo = t.getTipo();
		descripcion = t.getDescripcion();
		return "";
	}

	public String accionActualizarTipoEvento() throws Exception {
		try {
			manager.editarTipoEvento(id_tipoevento,tipo, descripcion);
			//limpiar datos
			tipo="";
			descripcion="";
			FacesContext context = FacesContext.getCurrentInstance();
			context.addMessage(null, new FacesMessage("Actualizado..!!!",
					"Evento Tipo Editado "));
		} catch (Exception e) {
			FacesContext context = FacesContext.getCurrentInstance();
			context.addMessage(null, new FacesMessage("Registrado..!!!",
					"Evento Tipo NO Editado "));
		}
		return "";
	}
	
	public String irTeventos(){
		FacesContext context = FacesContext.getCurrentInstance();
        context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Cancelado!", "Actualizacion Cancelada"));
      //limpiamos los datos
      //limpiar datos
		tipo="";
		descripcion="";
		return "";					
	}
}