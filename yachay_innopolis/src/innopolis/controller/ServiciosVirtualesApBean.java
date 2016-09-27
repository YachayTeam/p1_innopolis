package innopolis.controller;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import innopolis.entidades.Estadotiposervicio;
import innopolis.entidades.Serviciosvirtregi;
import innopolis.entidades.Tipoestado;
import innopolis.entidades.Tiposervicio;
import innopolis.entidades.Usuario;
import innopolis.entidades.help.UsuarioHelp;
import innopolis.manager.ManagerBuscar;
import innopolis.manager.ManagerRecursosVirtuales;
import innopolis.model.generic.Mensaje;

import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.model.SelectItem;

@SessionScoped
@ManagedBean
public class ServiciosVirtualesApBean implements Serializable {
	private static final long serialVersionUID = 1L;
	private ManagerRecursosVirtuales managerservirt;
	private Integer idSvr;
	private Integer idestado;
	private Integer idservi;
	private Integer idestadotipser;
	private String smscor;

	private Tipoestado tipoestado;
	private Tiposervicio tiposervicio;
	private Estadotiposervicio estadotipser;
	private Usuario usuario;

	private String apellidos;
	private String cedula;
	private String correo;
	private String nombres;
	private String tema;
	private String sms;

	private String nombreestipser;

	@EJB
	private ManagerBuscar mb;

	private List<Serviciosvirtregi> liservicioreg;
	private List<Tiposervicio> tiposervli;
	private List<Tipoestado> tipoestli;
	private List<Usuario> usu;

	private String nomservicio;

	private UsuarioHelp session;

	public ServiciosVirtualesApBean() {
		session = SessionBean.verificarSession();
		managerservirt = new ManagerRecursosVirtuales();
		tiposervicio = new Tiposervicio();
		estadotipser = new Estadotiposervicio();
		usuario = new Usuario();
	}

	/**
	 * @return the usu
	 */
	public List<Usuario> getUsu() {
		return usu;
	}

	/**
	 * @param usu
	 *            the usu to set
	 */
	public void setUsu(List<Usuario> usu) {
		this.usu = usu;
	}

	/**
	 * @return the idestadotipser
	 */
	public Integer getIdestadotipser() {
		return idestadotipser;
	}

	/**
	 * @param idestadotipser
	 *            the idestadotipser to set
	 */
	public void setIdestadotipser(Integer idestadotipser) {
		this.idestadotipser = idestadotipser;
	}

	/**
	 * @return the estadotipser
	 */
	public Estadotiposervicio getEstadotipser() {
		return estadotipser;
	}

	/**
	 * @param estadotipser
	 *            the estadotipser to set
	 */
	public void setEstadotipser(Estadotiposervicio estadotipser) {
		this.estadotipser = estadotipser;
	}

	/**
	 * @return the nombreestipser
	 */
	public String getNombreestipser() {
		return nombreestipser;
	}

	/**
	 * @param nombreestipser
	 *            the nombreestipser to set
	 */
	public void setNombreestipser(String nombreestipser) {
		this.nombreestipser = nombreestipser;
	}

	/**
	 * @return the usuario
	 */
	public Usuario getUsuario() {
		return usuario;
	}

	/**
	 * @param usuario
	 *            the usuario to set
	 */
	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
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

	/**
	 * @return the smscor
	 */
	public String getSmscor() {
		return smscor;
	}

	/**
	 * @param smscor
	 *            the smscor to set
	 */
	public void setSmscor(String smscor) {
		this.smscor = smscor;
	}

	public String getSms() {
		return sms;
	}

	public void setSms(String sms) {
		this.sms = sms;
	}

	public Integer getIdestado() {
		return idestado;
	}

	public void setIdestado(Integer idestado) {
		this.idestado = idestado;
	}

	public Integer getIdservi() {
		return idservi;
	}

	public void setIdservi(Integer idservi) {
		this.idservi = idservi;
	}

	public List<Serviciosvirtregi> getServicioreg() {
		return liservicioreg;
	}

	public void setServicioreg(List<Serviciosvirtregi> liservicioreg) {
		this.liservicioreg = liservicioreg;
	}

	public Integer getIdSvr() {
		return idSvr;
	}

	public void setIdSvr(Integer idSvr) {
		this.idSvr = idSvr;
	}

	public String getApellidos() {
		return apellidos;
	}

	public void setApellidos(String apellidos) {
		this.apellidos = apellidos;
	}

	public String getCedula() {
		return cedula;
	}

	public void setCedula(String cedula) {
		this.cedula = cedula;
	}

	public String getCorreo() {
		return correo;
	}

	public void setCorreo(String correo) {
		this.correo = correo;
	}

	public String getNombres() {
		return nombres;
	}

	public void setNombres(String nombres) {
		this.nombres = nombres;
	}

	public String getTema() {
		return tema;
	}

	public void setTema(String tema) {
		this.tema = tema;
	}

	public List<Tiposervicio> getTiposervli() {
		return tiposervli;
	}

	public void setTiposervli(List<Tiposervicio> tiposervli) {
		this.tiposervli = tiposervli;
	}

	public List<Tipoestado> getTipoestli() {
		return tipoestli;
	}

	public void setTipoestli(List<Tipoestado> tipoestli) {
		this.tipoestli = tipoestli;
	}

	public Tipoestado getTipoestado() {
		return tipoestado;
	}

	public void setTipoestado(Tipoestado tipoestado) {
		this.tipoestado = tipoestado;
	}

	public Tiposervicio getTiposervicio() {
		return tiposervicio;
	}

	public void setTiposervicio(Tiposervicio tiposervicio) {
		this.tiposervicio = tiposervicio;
	}

	public String getNomservicio() {
		return nomservicio;
	}

	public void setNomservicio(String nomservicio) {
		this.nomservicio = nomservicio;
	}

	// metodo para asignar el TipoServicio al registro
	public String asignarTiposerv() {
		managerservirt.asignarTiposerv(tiposervicio.getIdTp());
		return "";
	}

	// metodo para asignar el Tipoestado al registro
	public String asignarTipoest() {
		managerservirt.asignarTipoest(tipoestado.getIdEstado());
		return "";
	}

	// metodo para listar los registros
	public List<Serviciosvirtregi> getListRegServi() {
		return managerservirt.findAllRServiciosVirtuales();
	}

	// metodo para listar los servicios
	public List<Tiposervicio> getListServicios() {
		return managerservirt.findAllTipoServicio();
	}

	// metodo para cargar la lista de servicios virtuales
	public String Cargarregistros(Serviciosvirtregi serv) {
		idSvr = serv.getIdSvr();
		cedula = serv.getUsuario().getCedula();
		nombres = serv.getUsuario().getNombre();
		apellidos = serv.getUsuario().getApellido();
		correo = serv.getUsuario().getCorreo();
		tema = serv.getTema();
		tipoestado = serv.getTipoestado();
		tiposervicio = serv.getTiposervicio();
		sms = serv.getSms();
		return "";
	}

	// metodo cargar todos los tiposervicios
	public String cargarDatostiposerv(Tiposervicio tiposerv) {
		idservi = tiposerv.getIdTp();
		nomservicio = tiposerv.getNombreServicio();
		nombreestipser = tiposerv.getEstadotiposervicio().getEts();
		return "editarserv";
	}

	// metodo para listar los TiposServicio
	public List<SelectItem> getListaTiposerv() {
		List<SelectItem> listadoTS = new ArrayList<SelectItem>();
		List<Tiposervicio> listadoServicio = managerservirt
				.findAllTipoServicio();

		for (Tiposervicio t : listadoServicio) {
			SelectItem item = new SelectItem(t.getIdTp(),
					t.getNombreServicio(), t.getNombreServicio());
			listadoTS.add(item);
		}
		return listadoTS;
	}

	// eliminar un servicio
	public String EliminarServicio(Tiposervicio tpser) {

		try {
			managerservirt.eliminarServicio(tpser.getIdTp());
			Mensaje.crearMensajeINFO("Servicio eliminado");
		} catch (Exception e) {
			Mensaje.crearMensajeWARN("Servicio no eliminado");
			e.printStackTrace();
		}
		return "";
	}

	// metodo para mostrar los TiposEstado
	public List<SelectItem> getListaTipoestado() {
		List<SelectItem> listadoTE = new ArrayList<SelectItem>();
		List<Tipoestado> listadoEstado = managerservirt.findAllTipoEstado();
		for (Tipoestado t : listadoEstado) {
			SelectItem item = new SelectItem(t.getIdEstado(),
					t.getNombreestado());
			listadoTE.add(item);
		}
		return listadoTE;
	}

	public String aprobarSolicitud(Serviciosvirtregi ser) {
		try {
			if (ser.getTipoestado().getNombreestado().equals(("Aprobado"))) {
				Mensaje.crearMensajeINFO("La inscripción se encuentra aprobada");
			} else {
				String resultado = managerservirt.cambioDisEstadoapro(ser
						.getIdSvr());
				managerservirt.cambioSMS(ser.getIdSvr());
				Mensaje.crearMensajeINFO(resultado);

			}
		} catch (Exception e) {
			Mensaje.crearMensajeWARN("Error al aprobar inscripción");
			e.printStackTrace();
		}
		return "";
	}

	public String negarSolicitud(Serviciosvirtregi ser) {
		try {
			if (ser.getTipoestado().getNombreestado().equals(("Negado"))) {
				Mensaje.crearMensajeWARN("La inscripción se encuentra negada");
			} else {
				String resultado = managerservirt.cambioDisEstadonega(ser.getIdSvr());
				managerservirt.cambioSMS(ser.getIdSvr());
				Mensaje.crearMensajeINFO(resultado);
			}
		} catch (Exception e) {
			Mensaje.crearMensajeWARN("Error al negar inscripción");
			e.printStackTrace();
		}
		return "";
	}

	public String cambiarEstadoServ(Tiposervicio ser) {
		try {
			String resultado = managerservirt.cambioEstadotiposer(ser.getIdTp());
			Mensaje.crearMensajeINFO(resultado);		
		} catch (Exception e) {
			Mensaje.crearMensajeWARN("Error al cambiar el estado");
			e.printStackTrace();
		}
		return "";
	}

	// ------ Envios paginas--------//
	public String irAprovador() {
		cedula = "";
		nombres = "";
		correo = "";
		tema = "";
		apellidos = "";
		try {
			tipoestado = managerservirt.findEstadoTipoByID(1);
		} catch (Exception e) {
			e.printStackTrace();
		}
		tiposervicio = new Tiposervicio();
		idSvr = 0;
		return "";
	}

	public String irAprovadorpag() {
		try {
			tiposervicio = managerservirt.findServicioTipoByID(1);
			Mensaje.crearMensajeINFO("Actualización cancelada");
		} catch (Exception e) {
			e.printStackTrace();
		}
		tiposervicio = new Tiposervicio();
		idSvr = 0;
		nomservicio = "";
		return "/admin/crudservicio";
	}

	public String cambioEnvioSms() {
		Mensaje.crearMensajeINFO("Notificación aceptada");
		return "";
	}

	public String asignarsms(Serviciosvirtregi serv) {
		idSvr = serv.getIdSvr();
		tema = serv.getTema();
		tipoestado = serv.getTipoestado();
		tiposervicio = serv.getTiposervicio();
		usuario = serv.getUsuario();
		sms = serv.getSms();
		correo = serv.getUsuario().getCorreo();
		smscor = "<!DOCTYPE html><html lang='es'><head><meta http-equiv='Content-Type' content='text/html; charset=utf-8' />"
				+ "<meta name='viewport' content='width=device-width'></head><body>"
				+ "El usuario con apellido "
				+ serv.getUsuario().getApellido()
				+ " con nombre "
				+ serv.getUsuario().getNombre()
				+ "; le informamos que su petición de registro a "
				+ serv.getTiposervicio().getNombreServicio()
				+ " fue "
				+ serv.getTipoestado().getNombreestado()
				+ "."
				+ "<br/> Saludos cordiales, "
				+ "<br/> Sistema de REGECE Yachay EP"
				+ "<br/><em><strong>NOTA:</strong> Este correo es generado automáticamente por el sistema favor no responder al mismo.</em></body></html>";
		return "";
	}

	// correo
	// Tomar el id de estado general id_estadoSolicitud
	public String enviarmensaje(Serviciosvirtregi serv) {
		try {
			if (sms.equals("No Notificado")) {
				if (tipoestado.getNombreestado().equals("Pendiente")) {
					Mensaje.crearMensajeWARN("Indique si se aprueba o niega la solicitud a un servicio virtual");
				} else {
					managerservirt.cambioSMSenvio(idSvr);
					mb.envioMailWS(correo,"Petición de Solicitud a Servicios Virtuales YACHAY/REGECE",
							smscor);
					Mensaje.crearMensajeINFO("Enviado correctamente al correo");
				}
			} else {
				Mensaje.crearMensajeWARN("Yá se ha enviado al correo la notificación");
			}

		} catch (Exception e) {
			Mensaje.crearMensajeWARN("Error al enviar correo");
		}
		return "/aprobador/aprovadorserviciovirtual";
	}

	// metodo para deshabilitar botones
	private boolean disable;

	public void MainTable() {
		disable = false;
	}

	public boolean isDisable() {
		return disable;
	}

	public void setDisable(boolean disable) {
		this.disable = disable;
	}
}