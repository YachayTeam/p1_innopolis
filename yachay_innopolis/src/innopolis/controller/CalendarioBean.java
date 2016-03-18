package innopolis.controller;


import java.io.Serializable;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import innopolis.entidades.Evento;
import innopolis.entidades.help.UsuarioHelp;
import innopolis.manager.ManagerEvento;
import innopolis.manager.ManagerInscripedit;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import org.primefaces.event.SelectEvent;
import org.primefaces.model.DefaultScheduleEvent;
import org.primefaces.model.DefaultScheduleModel;
import org.primefaces.model.ScheduleEvent;
import org.primefaces.model.ScheduleModel;


@SessionScoped
@ManagedBean
public class CalendarioBean implements Serializable {
	private static final long serialVersionUID = 1L;
	private ManagerEvento manager;	
	private Evento e;
	private ScheduleModel eventModel;
	private ScheduleEvent event = new DefaultScheduleEvent();
	private UsuarioHelp session;

	//camposnuevos
		ManagerInscripedit managerins;	
		
		private Integer id_campo;
		private String etiqueta;
		private String campo;
	
	/**
		 * @return the id_campo
		 */
		public Integer getId_campo() {
			return id_campo;
		}

		/**
		 * @param id_campo the id_campo to set
		 */
		public void setId_campo(Integer id_campo) {
			this.id_campo = id_campo;
		}

		/**
		 * @return the etiqueta
		 */
		public String getEtiqueta() {
			return etiqueta;
		}

		/**
		 * @param etiqueta the etiqueta to set
		 */
		public void setEtiqueta(String etiqueta) {
			this.etiqueta = etiqueta;
		}

		/**
		 * @return the campo
		 */
		public String getCampo() {
			return campo;
		}

		/**
		 * @param campo the campo to set
		 */
		public void setCampo(String campo) {
			this.campo = campo;
		}

		
	public CalendarioBean() {
		manager = new ManagerEvento();
		eventModel = new DefaultScheduleModel();
		List<Evento> listado = eventos();
		if(listado.size()>0){
			for (Evento e : listado) {
				if (e.getEstado().equals("Activado")){
					event = new DefaultScheduleEvent(e.getNombre(),e.getFechaInicio(),
							e.getFechaFin(), e);
					eventModel.addEvent(event);
					}
					else
					{
						
					}
			 	}				
		}
	}

	public ScheduleModel getEventModel() {
		return eventModel;
	}

	public void setEventModel(ScheduleModel eventModel) {
		this.eventModel = eventModel;
	}

	public ScheduleEvent getEvent() {
		return event;
	}

	public void setEvent(ScheduleEvent event) {
		this.event = event;
	}

	public Evento getE() {
		return e;
	}

	public void setE(Evento e) {
		this.e = e;
	}

	public List<Evento> getListEvento() {
		return manager.findAllEventos();
	}
	
	public UsuarioHelp getSession() {
		return session;
	}
	// ////////////////////////////////////////////////////////////CALENDARIO//////////////////////////////////////////////////////////////////

	
	public List<Evento> eventos(){
		List<Evento> le = new ArrayList<Evento>();
		Date date= new Date();
		Timestamp fecha_actual= new Timestamp(date.getTime());
		System.out.println("cas: "+fecha_actual.toString());
		for (Evento e : getListEvento()) {
			if ( e.getFechaInicio().after(fecha_actual) || e.getFechaFin().after(fecha_actual) || e.getFechaInicio().compareTo(fecha_actual)==0 ){
				le.add(e);
			}
		}
		return le;
	}
	
	
	public String refresh(){
		eventModel = new DefaultScheduleModel();
		List<Evento> listado = eventos();
		System.out.print("tam "+listado.size());
		for (Evento e : listado) {
			event = new DefaultScheduleEvent(e.getNombre(), e.getFechaInicio(),
					e.getFechaFin(), e);
			eventModel.addEvent(event);
		}
		return "calendario?faces-redirect=true";
	}
	

	public void onEventSelect(SelectEvent selectEvent) {
		event = (ScheduleEvent) selectEvent.getObject();
	}

}
