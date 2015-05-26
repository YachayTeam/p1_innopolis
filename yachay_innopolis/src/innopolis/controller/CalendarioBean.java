package innopolis.controller;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import innopolis.entities.*;
import innopolis.manager.ManagerEvento;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.event.ActionEvent;

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
		private ScheduleModel eventModel;
		private ScheduleEvent event = new DefaultScheduleEvent();
		
		public CalendarioBean(){
			manager = new ManagerEvento();
		}
		
		public List<Evento> getListEvento() {
			return manager.findAllEventos();
		}
	// ////////////////////////////////////////////////////////////CALENDARIO//////////////////////////////////////////////////////////////////

		// metodo para listar las fechas desde el dia actual en adelante
		public List<Evento> actual() {
			List<Evento> le = new ArrayList<Evento>();
			for (Evento e : getListEvento()) {
				if (e.getFecha().after(new Date())) {
					le.add(e);
				}
			}
			return le;
		}
		
		//metodo para listar los eventos
		public List<Evento> getListRegEventos(){
			return manager.findAllEventos();
		}

		@PostConstruct
		public void init() {
			eventModel = new DefaultScheduleModel();
			for (Evento e : actual()) {
				event = new DefaultScheduleEvent(e.getNombre(), e.getFecha(),
						e.getFecha(), e);
				eventModel.addEvent(event);
			}
		}

		public void addEvent(ActionEvent actionEvent) {
			if (event.getId() == null)
				eventModel.addEvent(event);
			else
				eventModel.updateEvent(event);

			event = new DefaultScheduleEvent();
		}

		public void onEventSelect(SelectEvent selectEvent) {
			event = (ScheduleEvent) selectEvent.getObject();
		}
		
		
		//IR A INSCRIPCION
		public String irInscripcion(){
			manager.seleccionEventoAinscribirse((Evento) event.getData());
			return "frm_ins?faces-redirect=true";
		} 

	}