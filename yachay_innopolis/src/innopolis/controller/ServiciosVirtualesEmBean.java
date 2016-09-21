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
import innopolis.manager.ManagerLogin;
import innopolis.manager.ManagerRecursosVirtuales;
import innopolis.model.generic.Mensaje;

import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;

@SessionScoped
@ManagedBean
public class ServiciosVirtualesEmBean implements Serializable {
	private static final long serialVersionUID = 1L;
	private ManagerRecursosVirtuales managerservirt;
	private ManagerLogin managerlog;

	@EJB
	private ManagerBuscar mb;

	private Integer idSvr;
	private Integer idestado;
	private Integer idservi;
	private Integer idestadotipser;

	private Tipoestado tipoestado;
	private Tiposervicio tiposervicio;
	private Estadotiposervicio estadotipser;
	private Usuario usuario;

	private Integer idusr;
	private String apellidos;
	private String cedula;
	private String correo;
	private String nombres;
	private String tema;
	private String sms;

	private String nombreestipser;
	private String url;

	// envio de correo a los administradores
	private String smscoradmin;
	private String smscorusu;
	private String correosadmin;

	private List<Serviciosvirtregi> liservicioreg;
	private List<Tiposervicio> tiposervli;
	private List<Tipoestado> tipoestli;
	private String nomservicio;

	private UsuarioHelp session;

	public ServiciosVirtualesEmBean() {
		managerlog = new ManagerLogin();
		session = SessionBean.verificarSession();
		managerservirt = new ManagerRecursosVirtuales();
		idusr = session.getIdUsr();
		tiposervicio = new Tiposervicio();
		cedula = session.getCedula();
		nombres = session.getNombre();
		apellidos = session.getApellido();
		correo = session.getCorreo();
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
	 * @return the url
	 */
	public String getUrl() {
		return url;
	}

	/**
	 * @param url
	 *            the url to set
	 */
	public void setUrl(String url) {
		this.url = url;
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

	/**
	 * @return the smscoradmin
	 */
	public String getSmscoradmin() {
		return smscoradmin;
	}

	/**
	 * @param smscoradmin
	 *            the smscoradmin to set
	 */
	public void setSmscoradmin(String smscoradmin) {
		this.smscoradmin = smscoradmin;
	}

	/**
	 * @return the smscorusu
	 */
	public String getSmscorusu() {
		return smscorusu;
	}

	/**
	 * @param smscorusu
	 *            the smscorusu to set
	 */
	public void setSmscorusu(String smscorusu) {
		this.smscorusu = smscorusu;
	}

	/**
	 * @return the correosadmin
	 */
	public String getCorreosadmin() {
		return correosadmin;
	}

	/**
	 * @param correosadmin
	 *            the correosadmin to set
	 */
	public void setCorreosadmin(String correosadmin) {
		this.correosadmin = correosadmin;
	}

	/**
	 * @return the idusr
	 */
	public Integer getIdusr() {
		return idusr;
	}

	/**
	 * @param idusr
	 *            the idusr to set
	 */
	public void setIdusr(Integer idusr) {
		this.idusr = idusr;
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

	// metodo para asignar el TipoServicio al registro
	public String asignarTiposerv() {
		managerservirt.asignarTiposerv(tiposervicio.getIdTp());
		return "";
	}

	// metodo para asignar el TipoServicio al registro
	public String Tiposervlistlink() {
		llenar();
		try {
			url = managerservirt.asignarTiposervurl(tiposervicio.getIdTp())
					.getUrl();
			Mensaje.crearMensajeINFO("Servicio asignado");
		} catch (Exception e) {
			url = "";
			Mensaje.crearMensajeWARN("No tiene permisos para ingresar");
		}
		return "home";
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

	// Cargar datos usuario
	public String llenar() {
		cedula = session.getCedula();
		nombres = session.getNombre();
		apellidos = session.getApellido();
		correo = session.getCorreo();
		return "serviciovirtual?faces-redirect=true";
	}

	// metodo para crear registros
	public String crearRegistroServ() {
		String resp = "";
		try {
			if (managerservirt.usuarioReg(idusr, tiposervicio.getIdTp())
					.equals(true)) {
				Mensaje.crearMensajeINFO("Registrado, espera al mensaje de confirmación del servicio");
			} else {
				if (!tiposervicio.equals(-1)) {
					managerservirt.insertarserviciovirtual(idusr, tema);
					smscoradmin = "<!DOCTYPE html><html lang='es'><head><meta http-equiv='Content-Type' content='text/html; charset=utf-8' />"
							+ "<meta name='viewport' content='width=device-width'></head><body>"
							+ "El Sr/ra. "
							+ session.getNombre()
							+ " "
							+ session.getApellido()
							+ ", envi&oacute; una solicitud para un Servicio Virtual; Requiere la aprobaci&oacute;n o negaci&oacute;n.; <br/>"
							+ "Los datos del usuario son:"
							+ "<br/> C&eacute;dula: "
							+ session.getCedula()
							+ ""
							+ "<br/> Nombre: "
							+ session.getNombre()
							+ ""
							+ "<br/> Apellido: "
							+ session.getApellido()
							+ ""
							+ "<br/> Correo: "
							+ session.getCorreo()
							+ ""
							+ "<br/> Tema: "
							+ tema
							+ "<br/> Saludos cordiales, "
							+ "<br/> Sistema de REGECE Yachay EP"
							+ "<br/><em><strong>NOTA:</strong> Este correo es generado automáticamente por el sistema favor no responder al mismo.</em></body></html>";

					smscorusu = "<!DOCTYPE html><html lang='es'><head><meta http-equiv='Content-Type' content='text/html; charset=utf-8' />"
							+ "<meta name='viewport' content='width=device-width'></head><body>"
							+ "Sr/ra.  "
							+ session.getNombre()
							+ " "
							+ session.getApellido()
							+ ", su petici&oacute;n de solicitud del Servicio que brinda el sistema REGECE (Reservas de Espacios y Gesti&oacute;n de Eventos del Centro de Emprendimiento), ser&aacute; verificado por los administradores, espere al mensaje de confirmaci&oacute;n. <br/>"
							+ "Sus datos del usuario son:"
							+ "<br/> C&eacute;dula: "
							+ session.getCedula()
							+ ""
							+ "<br/> Nombre: "
							+ session.getNombre()
							+ ""
							+ "<br/> Apellido: "
							+ session.getApellido()
							+ ""
							+ "<br/> Correo: "
							+ session.getCorreo()
							+ ""
							+ "<br/> Tema: "
							+ tema
							+ "<br/> Saludos cordiales, "
							+ "<br/> Sistema de REGECE Yachay EP"
							+ "<br/><em><strong>NOTA:</strong> Este correo es generado automáticamente por el sistema favor no responder al mismo.</em></body></html>";
					getcorreosusu();
					System.out.println(correosadmin);
					mb.envioMailWS(correosadmin,
							"Notificación de YACHAY/REGECE", smscoradmin);
					mb.envioMailWS(correo, "Notificación de YACHAY/REGECE",
							smscorusu);
					correosadmin = "";
					smscoradmin = "";
					smscorusu = "";
					tema = "";
					sms = "";
					tipoestado = managerservirt.findEstadoTipoByID(1);
					tiposervicio = new Tiposervicio();
					idSvr = null;
					Mensaje.crearMensajeINFO("Registro Creado, espere al mensaje de confirmación");
					resp = "home?faces-redirect=true";
				} else {
					Mensaje.crearMensajeWARN("Debe escoger un servicio");
				}
			}

		} catch (Exception e) {
			Mensaje.crearMensajeWARN("Registro no creado escoga un servicio");
		}
		return resp;
	}

	// metodo para listar correos de ususariosadmin
	public String getcorreosusu() {
		try {
			List<Usuario> a = managerlog.findUsrsPrincipal();
			correosadmin = "";
			for (Usuario u : a) {
				correosadmin += u.getCorreo() + ",";
			}
			int max = correosadmin.length();
			correosadmin = correosadmin.substring(0, max - 1).trim();
		} catch (Exception e) {
			Mensaje.crearMensajeWARN("No se encuentran usuarios administradores");
			e.printStackTrace();
		}
		return correosadmin;
	}

	// metodo para modificar los registros
	public String actualizarRegistro() {
		managerservirt.editarserviciovirtual(idSvr, tema,
				tipoestado.getIdEstado(), tiposervicio.getIdTp(), sms);
		tema = "";
		sms = "";
		try {
			tipoestado = managerservirt.findEstadoTipoByID(1);
		} catch (Exception e) {
			e.printStackTrace();
		}
		tiposervicio = null;
		idSvr = null;
		Mensaje.crearMensajeINFO("Registro actualizado");
		return "aprovadorserviciovirtual?faces-redirect=true";
	}

	// metodo para cargar la lista de servicios virtuales
	public String Cargarregistros(Serviciosvirtregi serv) {
		idSvr = serv.getIdSvr();
		idusr = serv.getUsuario().getIdUsr();
		tipoestado = serv.getTipoestado();
		tiposervicio = serv.getTiposervicio();
		sms = serv.getSms();
		return "modservedi?faces-redirect=true";
	}

	// metodo cargar todos los tiposervicios
	public String cargarDatostiposerv(Tiposervicio tiposerv) {
		idservi = tiposerv.getIdTp();
		nomservicio = tiposerv.getNombreServicio();
		nombreestipser = tiposerv.getEstadotiposervicio().getEts();
		return "editarserv?faces-redirect=true";
	}

	// metodo para listar los TiposServicio
	public List<SelectItem> getListaTiposerv() {
		List<SelectItem> listadoTS = new ArrayList<SelectItem>();
		try {
			List<Tiposervicio> listadoServicio = managerservirt
					.findAllTipoServicio();
			for (Tiposervicio t : listadoServicio) {
				SelectItem item = new SelectItem(t.getIdTp(),
						t.getNombreServicio(), t.getUrl());
				listadoTS.add(item);
			}
		} catch (Exception e) {
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(e.getMessage()));
			e.printStackTrace();
		}
		return listadoTS;
	}

	// metodo para listar los TiposServicio
	public List<SelectItem> getListaTiposervxusr2() {
		List<SelectItem> listadoTS = new ArrayList<SelectItem>();
		List<Serviciosvirtregi> a = managerservirt.findAllRServiciosVirtuales();
		List<Tiposervicio> b = managerservirt.findAllTipoServicio();
		try {
			for (Serviciosvirtregi t : a) {
				for (Tiposervicio s : b) {
					if (t.getUsuario().getIdUsr().equals(session.getIdUsr())
							&& t.getTipoestado().getIdEstado() == 2
							&& t.getTiposervicio().getIdTp() == s.getIdTp()) {
						System.out.println(s.getIdTp() + " "
								+ s.getNombreServicio() + " " + s.getUrl());
						SelectItem item = new SelectItem(s.getIdTp(),
								s.getNombreServicio(), s.getUrl());
						listadoTS.add(item);
					}
				}
			}
		} catch (Exception e) {
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(e.getMessage()));
			e.printStackTrace();
		}
		return listadoTS;
	}

	// eliminar un servicio
	public String EliminarServicio(Tiposervicio tpser) {
		try {
			managerservirt.eliminarServicio(tpser.getIdTp());
		} catch (Exception e) {
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

	public String cambiarEstadoServ(Tiposervicio ser) {
		try {
			String resultado = managerservirt
					.cambioEstadotiposer(ser.getIdTp());
			Mensaje.crearMensajeINFO(resultado);
		} catch (Exception e) {
			Mensaje.crearMensajeWARN("Error al cambiar el estado");
			e.printStackTrace();
		}
		return "";
	}

	// ------ Envios paginas--------//
	public String irAprovador() {
		return "home?faces-redirect=true";
	}

	public String irAprovadorpag() {
		// limpiamos los datos
		try {
			tiposervicio = managerservirt.findServicioTipoByID(1);
			Mensaje.crearMensajeWARN("Actualización cancelada");
		} catch (Exception e) {
			e.printStackTrace();
		}
		tiposervicio = new Tiposervicio();
		idSvr = 0;
		idusr = 0;
		;
		nomservicio = "";
		return "crudservicio";
	}

	// correo
	// Tomar el id de estado general id_estadoSolicitud
	public String enviarmensaje(Serviciosvirtregi serv) {
		try {
			if (serv.getSms().equals("No Notificado")) {
				managerservirt.cambioSMSenvio(serv.getIdSvr());
				mb.envioMailWS(
						serv.getUsuario().getCorreo(),
						"Notificación de YACHAY/REGECE",
						"<!DOCTYPE html><html lang='es'><head><meta http-equiv='Content-Type' content='text/html; charset=utf-8' />"
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
								+ "<br/><em><strong>NOTA:</strong> Este correo es generado automáticamente por el sistema favor no responder al mismo.</em></body></html>");
				Mensaje.crearMensajeINFO("Enviado correctamente al correo");
			} else {
				Mensaje.crearMensajeWARN("Error, no se envió al correo");
			}
		} catch (Exception e) {
			Mensaje.crearMensajeWARN("Error al enviar correo");
		}
		return "aprovadorserviciovirtual";
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