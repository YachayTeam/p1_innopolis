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
	private ManagerEvento minnopolis = new ManagerEvento();
	private Integer id_tipoevento;
	private String tipo;
	private String descripcion;

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
		return minnopolis.findAllTipoEventos();
	}
	
	public String accioninsertarTipoEvento() {
		Tipoevento tipoEvento = new Tipoevento();
		tipoEvento.setTipo(tipo);
		tipoEvento.setDescripcion(descripcion);
		try {
			//minnopolis.insertarTipoEvento(tipoEvento);
			id_tipoevento = null;
			tipo = null;
			descripcion = null;
		} catch (Exception e) {
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(e.getMessage()));
			e.printStackTrace();
		}
		return "se ingreso";
	}

	public String accionEliminarTipoEvento(Tipoevento t) {
		try {
			//minnopolis.eliminarTipoEvento(t.getIdTipoEvento());
		} catch (Exception e) {
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(e.getMessage()));
			e.printStackTrace();
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
		//Tipoevento t = minnopolis.findTipoEventoById(id_tipoevento);
		//t.setTipo(tipo);
		//t.setDescripcion(descripcion);
		try {
			//minnopolis.actualizarTipoEvento(t);
			id_tipoevento = null;
			tipo = null;
			descripcion = null;
		} catch (Exception e) {
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(e.getMessage()));
			e.printStackTrace();
		}
		return "";
	}
}