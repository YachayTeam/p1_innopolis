package innopolis.controller;

import java.io.Serializable;
import java.util.List;

import innopolis.manager.ManagerLogin;
import innopolis.entidades.Interes;
import innopolis.entidades.help.UsuarioHelp;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.faces.application.FacesMessage;

@SessionScoped
@ManagedBean
public class InteresesBean implements Serializable {
	// https://github.com/YachayTeam/p1_innopolis/blob/32169dd0b6e81dad04a03a741b89a956e0527ffa/yachay_innopolis/src/innopolis/manager/ManagerInnopolis.java
	private static final long serialVersionUID = 1L;
	private ManagerLogin manager;
	private Integer id_interes;
	private String nombreinteres;
	private UsuarioHelp session;

	public InteresesBean() {
		session = SessionBean.verificarSession();
		manager = new ManagerLogin();
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
		if (this.ccinteres(nombreinteres) == true) {
			FacesContext context = FacesContext.getCurrentInstance();
			context.addMessage(
					null,
					new FacesMessage(
							"Interes Repetido..!!! El interés ya está siendo utilizado",
							null));
		} else {
			try {
				manager.insertarInteres(nombreinteres);
				// limpiar datos
				nombreinteres = "";
				FacesContext context = FacesContext.getCurrentInstance();
				context.addMessage(null, new FacesMessage(
						"Registrado..!!! Interés creado", null));
			} catch (Exception e) {
				FacesContext context = FacesContext.getCurrentInstance();
				context.addMessage(null, new FacesMessage(
						"No registrado..!!! Interés no creado", null));
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
			FacesContext context = FacesContext.getCurrentInstance();
			context.addMessage(null, new FacesMessage("Actualizado..!!! Interés editado",null));
		} catch (Exception e) {
			FacesContext context = FacesContext.getCurrentInstance();
			context.addMessage(null, new FacesMessage("No Registrado..!!! Interés no editado",null));
		}
		return "";
	}

	public String irintereses() {
		FacesContext context = FacesContext.getCurrentInstance();
		context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,
				"Cancelado, actualización cancelada",null));
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