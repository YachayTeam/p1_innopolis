package innopolis.controller;

import java.util.Date;
import java.util.List;

import innopolis.entities.Evento;
import innopolis.manager.ManagerInnopolis;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;

@SessionScoped
@ManagedBean
public class EventosBean {
	private ManagerInnopolis minnopolis = new ManagerInnopolis();
	private Integer idEvento;
	private Integer idTipoEvento;
	private Integer idSolicitudCab;
	private String nombre;
	private String descripcion;
	private Double costo;
	private Date fechaI;
	private Date fechaF;
	private String imagen;
	private String lugar;

	private Integer getIdEvento() {
		return idEvento;
	}

	private void setIdEvento(Integer idEvento) {
		this.idEvento = idEvento;
	}

	private Integer getIdTipoEvento() {
		return idTipoEvento;
	}

	private void setIdTipoEvento(Integer idTipoEvento) {
		this.idTipoEvento = idTipoEvento;
	}

	private Integer getIdSolicitudCab() {
		return idSolicitudCab;
	}

	private void setIdSolicitudCab(Integer idSolicitudCab) {
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

	private Double getCosto() {
		return costo;
	}

	private void setCosto(Double costo) {
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
	
	public List<Evento> getListaEventos()
	{
		return minnopolis.findAllEventos();
	}
	
	public String accioninsertarEvento() {
		Evento evento = new Evento();
		try {
			evento.setSolicicabecera(minnopolis.findSolicitudCabeceraById(idSolicitudCab));
			evento.setTipoevento(minnopolis.findTipoEventoById(idTipoEvento));
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}		
		evento.setNombre(nombre);
		evento.setDescripcion(descripcion);
		evento.setImagen(imagen);
		evento.setFechaI(fechaI);
		evento.setFechaF(fechaF);
		evento.setCosto(costo);
		evento.setLugar(lugar);
		try {
			minnopolis.insertarEvento(evento);
			idEvento=null;
			idSolicitudCab=null;
			idTipoEvento=null;
			nombre=null;
			descripcion=null;
			imagen=null;
			fechaI=null;
			fechaF=null;
			costo=null;
			lugar=null;
		} catch (Exception e) {
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(e.getMessage()));
			e.printStackTrace();
		}
		return "";
	}
	
	public String accionEliminarEvento(Evento t) {

		try {
			minnopolis.eliminarEvento(t.getIdEvento());
		} catch (Exception e) {
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(e.getMessage()));
			e.printStackTrace();
		}
		return "";
	}
	
	public String accionActualizarEvento() throws Exception {
		Evento evento = minnopolis.findEventoById(idEvento);
		try {
			evento.setSolicicabecera(minnopolis.findSolicitudCabeceraById(idSolicitudCab));
			evento.setTipoevento(minnopolis.findTipoEventoById(idTipoEvento));
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}		
		evento.setNombre(nombre);
		evento.setDescripcion(descripcion);
		evento.setImagen(imagen);
		evento.setFechaI(fechaI);
		evento.setFechaF(fechaF);
		evento.setCosto(costo);
		evento.setLugar(lugar);
		try {
			minnopolis.actualizarEvento(evento);
			idEvento=null;
			idSolicitudCab=null;
			idTipoEvento=null;
			nombre=null;
			descripcion=null;
			imagen=null;
			fechaI=null;
			fechaF=null;
			costo=null;
			lugar=null;
		} catch (Exception e) {
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(e.getMessage()));
			e.printStackTrace();
		}
		return "";
	}


}
