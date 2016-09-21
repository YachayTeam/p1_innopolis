package innopolis.controller;

import innopolis.entidades.Camposnuevo;
import innopolis.entidades.Inscripcione;
import innopolis.entidades.Interinscam;
import innopolis.entidades.help.Input;
import innopolis.entidades.help.UsuarioHelp;
import innopolis.manager.ManagerEvento;
import innopolis.manager.ManagerInscripedit;
import innopolis.model.generic.Mensaje;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

@ManagedBean
@SessionScoped
public class inscripediBean implements Serializable {

	private static final long serialVersionUID = 1L;
	ManagerInscripedit manager;
	// private List<Camposnuevo> inputs;
	Input input;

	private ManagerEvento managerEv;
	private ManagerInscripedit managerinscripedi;

	private Integer idInscripcion;
	private Integer id_evento;
	private List<Inscripcione> listadoInscripciones;

	private Camposnuevo camposnuevo;
	// valores de almacenaniento de campos nuevos
	private Integer add;
	private Integer del;
	private Integer t;
	private String res;

	private Integer id_campo;
	private String etiqueta;
	private String campo;

	private List<Interinscam> listaintermedia;
	private List<Input> inputs1;

	private UsuarioHelp session;

	/* SESSION */
	public UsuarioHelp getSession() {
		return session;
	}

	@PostConstruct
	public void init() {
		input = new Input();
		// inputs = new ArrayList<Camposnuevo>();
		manager = new ManagerInscripedit();
		session = SessionBean.verificarSession();
		listaintermedia = new ArrayList<Interinscam>();
		inputs1 = new ArrayList<Input>();
		res = "";
	}

	/**
	 * @return the id_campo
	 */
	public Integer getId_campo() {
		return id_campo;
	}

	/**
	 * @param id_campo
	 *            the id_campo to set
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
	 * @param etiqueta
	 *            the etiqueta to set
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
	 * @param campo
	 *            the campo to set
	 */
	public void setCampo(String campo) {
		this.campo = campo;
	}

	public Integer getIdInscripcion() {
		return idInscripcion;
	}

	public void setIdInscripcion(Integer idInscripcion) {
		this.idInscripcion = idInscripcion;
	}

	public Integer getId_evento() {
		return id_evento;
	}

	public void setId_evento(Integer id_evento) {
		this.id_evento = id_evento;
	}

	/**
	 * @return the managerEv
	 */
	public ManagerEvento getManagerEv() {
		return managerEv;
	}

	/**
	 * @param managerEv
	 *            the managerEv to set
	 */
	public void setManagerEv(ManagerEvento managerEv) {
		this.managerEv = managerEv;
	}

	/**
	 * @return the managerinscripedi
	 */
	public ManagerInscripedit getManagerinscripedi() {
		return managerinscripedi;
	}

	/**
	 * @param managerinscripedi
	 *            the managerinscripedi to set
	 */
	public void setManagerinscripedi(ManagerInscripedit managerinscripedi) {
		this.managerinscripedi = managerinscripedi;
	}

	/**
	 * @return the camposnuevo
	 */
	public Camposnuevo getCamposnuevo() {
		return camposnuevo;
	}

	/**
	 * @param camposnuevo
	 *            the camposnuevo to set
	 */
	public void setCamposnuevo(Camposnuevo camposnuevo) {
		this.camposnuevo = camposnuevo;
	}

	/**
	 * @return the add
	 */
	public Integer getAdd() {
		return add;
	}

	/**
	 * @param add
	 *            the add to set
	 */
	public void setAdd(Integer add) {
		this.add = add;
	}

	/**
	 * @return the del
	 */
	public Integer getDel() {
		return del;
	}

	/**
	 * @param del
	 *            the del to set
	 */
	public void setDel(Integer del) {
		this.del = del;
	}

	/**
	 * @return the t
	 */
	public Integer getT() {
		return t;
	}

	/**
	 * @param t
	 *            the t to set
	 */
	public void setT(Integer t) {
		this.t = t;
	}

	/**
	 * @return the res
	 */
	public String getRes() {
		return res;
	}

	/**
	 * @param res
	 *            the res to set
	 */
	public void setRes(String res) {
		this.res = res;
	}

	/**
	 * @return the listaintermedia
	 */
	public List<Interinscam> getListaintermedia() {
		return listaintermedia;
	}

	/**
	 * @param listaintermedia
	 *            the listaintermedia to set
	 */
	public void setListaintermedia(List<Interinscam> listaintermedia) {
		this.listaintermedia = listaintermedia;
	}

	/**
	 * @param listadoInscripciones
	 *            the listadoInscripciones to set
	 */
	public void setListadoInscripciones(List<Inscripcione> listadoInscripciones) {
		this.listadoInscripciones = listadoInscripciones;
	}

	/**
	 * @param session
	 *            the session to set
	 */
	public void setSession(UsuarioHelp session) {
		this.session = session;
	}

	public List<Inscripcione> getListadoInscripciones() {
		listadoInscripciones = managerEv.findAllInscripciones();
		return listadoInscripciones;
	}

	// accion para invocar el manager y crear campo nuevo
	public String crearcampo() {
		try {
			if (!input.getValue().equals("")) {
				etiqueta = input.getValue();
				manager.insertarcampos(etiqueta, campo);
				input.setValue("");
				id_campo = 0;
				Mensaje.crearMensajeINFO("Campo almacenado correctamente");
			} else {
				Mensaje.crearMensajeWARN("Cree un nuevo campo");
			}
		} catch (Exception e) {
			Mensaje.crearMensajeWARN("Error al almacenar campo");
		}
		;
		return "";
	}

	// metodo para cargar campos
	public String cargarcampo(Camposnuevo camnu) {
		id_campo = camnu.getIdCampo();
		campo = camnu.getCampo();
		etiqueta = camnu.getEtiqueta();
		return "";
	}

	public String actualizarcampo() {
		try {
			manager.editarcampos(id_campo, etiqueta, campo);
			id_campo = null;
			etiqueta = "";
			campo = "";
		} catch (Exception e) {
			e.printStackTrace();
		}

		return "frm_insedit";
	}

	public void add() {
		input = new Input();
		input.setLabel("Ingrese nombre de campo a crear");
		input.setValue("");
		inputs1.add(input);
		Mensaje.crearMensajeINFO("Ingrese uno por uno los campos");
	}

	public List<Input> getInputs() {
		return inputs1;
	}

	// metodo para listar los registros
	public List<Camposnuevo> getListcamponuevo() {
		List<Camposnuevo> a = manager.findAllcamposnuevo();
		List<Camposnuevo> l1 = new ArrayList<Camposnuevo>();
		for (Camposnuevo t : a) {
			l1.add(t);
		}
		return l1;
	}

	// eliminar un servicio
	public String eliminarcamponu(Camposnuevo camponu) {
		try {
			manager.eliminarcampo(camponu.getIdCampo());
			Mensaje.crearMensajeINFO("El campo ha sido eliminado");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "";
	}

	// ingresar
	public String ingresareninterinscam() {
		manager.mas();
		return "";
	}

	public String irEvento() {
		id_campo = null;
		etiqueta = "";
		campo = "";
		Mensaje.crearMensajeINFO("El campo ha sido eliminado");
		return "frm_insedit";
	}

}
