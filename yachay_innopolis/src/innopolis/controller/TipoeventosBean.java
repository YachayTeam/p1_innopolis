package innopolis.controller;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import innopolis.manager.ManagerEvento;
import innopolis.entidades.Coloreve;
import innopolis.entidades.Tipoevento;
import innopolis.entidades.help.UsuarioHelp;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;
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
	private Integer idcolor;
	
	private Coloreve color;
	private UsuarioHelp session;

	public TipoeventosBean() {
		session = SessionBean.verificarSession();
		manager = new ManagerEvento();
		color = new Coloreve();
	}
	
	/**
	 * @return the color
	 */
	public Coloreve getColor() {
		return color;
	}
	/**
	 * @param color the color to set
	 */
	public void setColor(Coloreve color) {
		this.color = color;
	}

	/**
	 * @return the idcolor
	 */
	public Integer getIdcolor() {
		return idcolor;
	}
	/**
	 * @param idcolor the idcolor to set
	 */
	public void setIdcolor(Integer idcolor) {
		this.idcolor = idcolor;
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
	
	public List<Coloreve> getlistacolor() {
		return manager.findAllColoreves();
	}
	/**
	 * @return the session
	 */
	public UsuarioHelp getSession() {
		return session;
	}

	/**
	 * @param session the session to set
	 */
	public void setSession(UsuarioHelp session) {
		this.session = session;
	}

	public String accioninsertarTipoEvento() {
//		if(this.ccolor(idcolor)==true)
//		{
//			FacesContext context = FacesContext.getCurrentInstance();
//	        context.addMessage(null, new FacesMessage("Color Repetido..!!!",  "El color ya está siendo utilizado") );
//		}	
//		else
//		{
		try {
			manager.insertarTipoEvento(tipo, descripcion);
			//limpiar datos
			tipo="";
			descripcion="";
			idcolor=0;
			color = new Coloreve();
			FacesContext context = FacesContext.getCurrentInstance();
			context.addMessage(null, new FacesMessage("Registrado tipo evento creado",null));
		} catch (Exception e) {
			FacesContext context = FacesContext.getCurrentInstance();
			context.addMessage(null, new FacesMessage("Error tipo evento no creado",null));
		}
		//}		
		return "evetipo";
	}

	public String accionCargarTipoEvento(Tipoevento t) {
		id_tipoevento = t.getIdTipoEvento();
		tipo = t.getTipo();
		descripcion = t.getDescripcion();
		//idcolor=t.getColoreve().getIdcoloreve();
		return "";
	}

	public String accionActualizarTipoEvento() throws Exception {
		
		try {
			manager.editarTipoEvento(id_tipoevento,tipo, descripcion/*, idcolor*/);
			//limpiar datos
			tipo="";
			descripcion="";
			//idcolor=null;			
			FacesContext context = FacesContext.getCurrentInstance();
			context.addMessage(null, new FacesMessage("Actualizado tipo evento editado",null));
		} catch (Exception e) {
			FacesContext context = FacesContext.getCurrentInstance();
			context.addMessage(null, new FacesMessage("Error tipo evento no editado",null));
		}
		return "";
	}
	
	public String irTeventos(){
		FacesContext context = FacesContext.getCurrentInstance();
        context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Actualización cancelada",null));
      //limpiamos los datos
		tipo="";
		descripcion="";
		//idcolor=0;
		return "";					
	}
	
	//////////////
	// metodo para mostrar los EventosTipos en Eventos
	public List<SelectItem> getListacolorid() {
		List<SelectItem> listadoSI = new ArrayList<SelectItem>();
		List<Coloreve> tipocolor = manager.findAllColoreves();
		for (Coloreve t : tipocolor) {
			SelectItem item = new SelectItem(t.getIdcoloreve(), t.getColor());
			listadoSI.add(item);
		}
		return listadoSI;
	}

	// metodo para asignar el TipoEvento al Evento
			public String asignarTipocolor() {
				manager.asignarTipocolor(idcolor);
				return "";
			}	
	
	// metodo para comprobar el ccolor
			public boolean ccolor(Integer id_color){
				List<Tipoevento> u = manager.findAllTipoEventos();
				for (Tipoevento us : u) {
					if (id_color.equals(us.getColoreve().getIdcoloreve())){
						return true;
					}
				}
				return false;
			}
	
}