package innopolis.controller;

import java.io.Serializable;
import java.util.List;

import innopolis.manager.ManagerLogin;
import innopolis.model.generic.Mensaje;
import innopolis.entidades.Interes;
import innopolis.entidades.help.UsuarioHelp;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

@SessionScoped
@ManagedBean
public class InteresesBean implements Serializable {

	private static final long serialVersionUID = 1L;
	private ManagerLogin manager;
	private Integer id_interes;
	private String nombreinteres;
	private UsuarioHelp session;

	public InteresesBean() {
		session = SessionBean.verificarSession();
		manager = new ManagerLogin();
		nombreinteres="";
	}

	public Integer getId_interes() {
		return id_interes;
	}

	public void setId_interes(Integer id_interes) {
		this.id_interes = id_interes;
	}

	/**
	 * @return the nombreinteres
	 */
	public String getNombreinteres() {
		return nombreinteres;
	}

	/**
	 * @param nombreinteres
	 *            the nombreinteres to set
	 */
	public void setNombreinteres(String nombreinteres) {
		this.nombreinteres = nombreinteres;
	}

	public List<Interes> getlistainteres() {
		return manager.findAllIntereses();
	}

	/**
	 * @return the session
	 */
	public UsuarioHelp getSession() {
		return session;
	}

	/**
	 * @param session
	 *            the session to set
	 */
	public void setSession(UsuarioHelp session) {
		this.session = session;
	}

	public String accioninsertarInteres() {
		if(nombreinteres.equals("")){
			Mensaje.crearMensajeWARN("El nombre del interés esta vacio");
		}else if (this.ccinteres(nombreinteres) == true) {
			Mensaje.crearMensajeWARN("El interés ya está siendo utilizado");
		} else {
			try {
				manager.insertarInteres(nombreinteres);
				// limpiar datos
				nombreinteres = "";
				Mensaje.crearMensajeINFO("Interés creado");
			} catch (Exception e) {
				Mensaje.crearMensajeWARN("Interés no creado");
			}
		}
		return "intereses";
	}

	public String accionCargarInteres(Interes t) {
		id_interes = t.getIdInteres();
		nombreinteres = t.getNombreInt();
		return "";
	}

	public String accionActualizarInteres() throws Exception {

		try {
			manager.editarInteres(id_interes, nombreinteres);
			// limpiar datos
			id_interes = null;
			nombreinteres = "";
			Mensaje.crearMensajeINFO("Interés actualizado");
		} catch (Exception e) {
			Mensaje.crearMensajeWARN("Interés no editado");
		}
		return "";
	}

	public String irintereses() {
		Mensaje.crearMensajeWARN("Actualización cancelada");
		// limpiamos los datos
		nombreinteres = "";
		id_interes = null;
		return "";
	}

	// metodo para comprobar el alias
	public boolean ccinteres(String nombreinteres) {
		List<Interes> u = manager.findAllIntereses();
		for (Interes us : u) {
			if (nombreinteres.equals(us.getNombreInt())) {
				return true;
			}
		}
		return false;
	}
}