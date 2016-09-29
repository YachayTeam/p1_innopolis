package innopolis.controller;

import innopolis.entidades.Interes;
import innopolis.entidades.Interesesmid;
import innopolis.entidades.Sugerencia;
import innopolis.entidades.Tipo;
import innopolis.entidades.Tipoestadousr;
import innopolis.entidades.Usuario;
import innopolis.entidades.help.UsuarioHelp;
import innopolis.entidades.help.Utilidades;
import innopolis.manager.ManagerBuscar;
import innopolis.manager.ManagerLogin;
import innopolis.model.generic.Mensaje;

import java.io.Serializable;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;

import org.primefaces.context.RequestContext;

@SessionScoped
@ManagedBean
public class UsuariosBean implements Serializable {
	private static final long serialVersionUID = 1L;
	private ManagerLogin manager;
	private ManagerLogin managerlog;

	@EJB
	private ManagerBuscar mb;

	private Integer idUsuario;
	private String alias;
	private String apellido;
	private String cedula;
	private String correo;
	private String direccion;
	private String telefono;
	private String celular;
	private String interes;
	private String nombre;
	private String password;
	private String rcorreo;
	private String rpassword;
	private Integer tipo;
	private String tipoest;
	private Tipoestadousr tipoestusr;
	private Usuario usu;
	private String empreestu;
	private String cargptitu;
	private boolean principal;

	// para saber si enviarmensaje

	// sugerencia
	private Sugerencia sugere;
	private Integer idsuge;
	private String sugerenciatext;
	private Usuario usuario;
	private Timestamp sugerenciafecha;
	private Date fi;
	private Integer idusrsug;
	private String smscorreosugerenc;
	private String nombresug;
	private String apellidosug;
	private String correosug;
	private String smssuge;

	// envio de correo a los administradores
	private String smscoradmin;
	private String correosadmin;

	// envio de mensaje
	private String smscor;
	private String sms;

	// Session
	private UsuarioHelp session;

	// intereses de usuario
	private Integer[] arrayTipoLogin;
	private Interes intereses;

	public UsuariosBean() {
		managerlog = new ManagerLogin();
		session = SessionBean.verificarSession();
		usuario = new Usuario();
		manager = new ManagerLogin();
		if (session == null) {
			FacesContext context = FacesContext.getCurrentInstance();
			context.addMessage(null, new FacesMessage("error usuarios vacios",
					null));
		} else {
			idusrsug = session.getIdUsr();
			nombresug = session.getNombre();
			apellidosug = session.getApellido();
			correosug = session.getCorreo();
		}
	}

	public Integer[] getArrayTipoLogin() {
		return arrayTipoLogin;
	}

	public void setArrayTipoLogin(Integer[] arrayTipoLogin) {
		this.arrayTipoLogin = arrayTipoLogin;
	}

	/**
	 * @return the intereses
	 */
	public Interes getIntereses() {
		return intereses;
	}

	/**
	 * @param intereses
	 *            the intereses to set
	 */
	public void setIntereses(Interes intereses) {
		this.intereses = intereses;
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

	public Integer getIdUsuario() {
		return idUsuario;
	}

	/**
	 * @return the idusr
	 */
	public Integer getIdusrsug() {
		return idusrsug;
	}

	/**
	 * @param idusr
	 *            the idusr to set
	 */
	public void setIdusr(Integer idusrsug) {
		this.idusrsug = idusrsug;
	}

	public Integer getTipo() {
		return tipo;
	}

	public UsuarioHelp getSession() {
		return session;
	}

	public void setSession(UsuarioHelp session) {
		this.session = session;
	}

	public void setTipo(Integer tipo) {
		this.tipo = tipo;
	}

	public void setIdUsuario(Integer idUsuario) {
		this.idUsuario = idUsuario;
	}

	public String getRcorreo() {
		return rcorreo;
	}

	public void setRcorreo(String rcorreo) {
		this.rcorreo = rcorreo;
	}

	public String getRpassword() {
		return rpassword;
	}

	public void setRpassword(String rpassword) {
		this.rpassword = rpassword;
	}

	public String getAlias() {
		return alias;
	}

	public void setAlias(String alias) {
		this.alias = alias;
	}

	public String getApellido() {
		return apellido;
	}

	public void setApellido(String apellido) {
		this.apellido = apellido;
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

	public String getDireccion() {
		return direccion;
	}

	public void setDireccion(String direccion) {
		this.direccion = direccion;
	}

	public String getTelefono() {
		return telefono;
	}

	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}

	public String getCelular() {
		return celular;
	}

	public void setCelular(String celular) {
		this.celular = celular;
	}

	public String getInteres() {
		return interes;
	}

	public void setInteres(String interes) {
		this.interes = interes;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Tipoestadousr getTipoestusr() {
		return tipoestusr;
	}

	public void setTipoestusr(Tipoestadousr tipoestusr) {
		this.tipoestusr = tipoestusr;
	}

	/**
	 * @return the principal
	 */
	public boolean isPrincipal() {
		return principal;
	}

	/**
	 * @param principal
	 *            the principal to set
	 */
	public void setPrincipal(boolean principal) {
		this.principal = principal;
	}

	/**
	 * @return the empreestu
	 */
	public String getEmpreestu() {
		return empreestu;
	}

	/**
	 * @param empreestu
	 *            the empreestu to set
	 */
	public void setEmpreestu(String empreestu) {
		this.empreestu = empreestu;
	}

	/**
	 * @return the cargptitu
	 */
	public String getCargptitu() {
		return cargptitu;
	}

	/**
	 * @param cargptitu
	 *            the cargptitu to set
	 */
	public void setCargptitu(String cargptitu) {
		this.cargptitu = cargptitu;
	}

	/**
	 * @return the usu
	 */
	public Usuario getUsu() {
		return usu;
	}

	/**
	 * @param usu
	 *            the usu to set
	 */
	public void setUsu(Usuario usu) {
		this.usu = usu;
	}

	/**
	 * @return the idsuge
	 */
	public Integer getIdsuge() {
		return idsuge;
	}

	/**
	 * @param idsuge
	 *            the idsuge to set
	 */
	public void setIdsuge(Integer idsuge) {
		this.idsuge = idsuge;
	}

	/**
	 * @return the sugerenciatext
	 */
	public String getSugerenciatext() {
		return sugerenciatext;
	}

	/**
	 * @param sugerenciatext
	 *            the sugerenciatext to set
	 */
	public void setSugerenciatext(String sugerenciatext) {
		this.sugerenciatext = sugerenciatext;
	}

	/**
	 * @return the sugere
	 */
	public Sugerencia getSugere() {
		return sugere;
	}

	/**
	 * @param sugere
	 *            the sugere to set
	 */
	public void setSugere(Sugerencia sugere) {
		this.sugere = sugere;
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

	/**
	 * @return the sms
	 */
	public String getSms() {
		return sms;
	}

	/**
	 * @param sms
	 *            the sms to set
	 */
	public void setSms(String sms) {
		this.sms = sms;
	}

	/**
	 * @return the nombresug
	 */
	public String getNombresug() {
		return nombresug;
	}

	/**
	 * @param nombresug
	 *            the nombresug to set
	 */
	public void setNombresug(String nombresug) {
		this.nombresug = nombresug;
	}

	/**
	 * @return the apellidosug
	 */
	public String getApellidosug() {
		return apellidosug;
	}

	/**
	 * @param apellidosug
	 *            the apellidosug to set
	 */
	public void setApellidosug(String apellidosug) {
		this.apellidosug = apellidosug;
	}

	/**
	 * @param idusrsug
	 *            the idusrsug to set
	 */
	public void setIdusrsug(Integer idusrsug) {
		this.idusrsug = idusrsug;
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
	 * @return the correosug
	 */
	public String getCorreosug() {
		return correosug;
	}

	/**
	 * @param correosug
	 *            the correosug to set
	 */
	public void setCorreosug(String correosug) {
		this.correosug = correosug;
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
	 * @return the tipoest
	 */
	public String getTipoest() {
		return tipoest;
	}

	/**
	 * @param tipoest
	 *            the tipoest to set
	 */
	public void setTipoest(String tipoest) {
		this.tipoest = tipoest;
	}

	public List<Usuario> getListUsuarios() {
		return manager.findAllUsuarios();
	}

	/**
	 * @return the smssuge
	 */
	public String getSmssuge() {
		return smssuge;
	}

	/**
	 * @param smssuge
	 *            the smssuge to set
	 */
	public void setSmssuge(String smssuge) {
		this.smssuge = smssuge;
	}

	/**
	 * @return the sugerenciafecha
	 */
	public Timestamp getSugerenciafecha() {
		return sugerenciafecha;
	}

	/**
	 * @param sugerenciafecha
	 *            the sugerenciafecha to set
	 */
	public void setSugerenciafecha(Timestamp sugerenciafecha) {
		this.sugerenciafecha = sugerenciafecha;
	}

	/**
	 * @return the fi
	 */
	public Date getFi() {
		return fi;
	}

	/**
	 * @param fi
	 *            the fi to set
	 */
	public void setFi(Date fi) {
		this.fi = fi;
	}

	/**
	 * @return the smscorreosugerenc
	 */
	public String getSmscorreosugerenc() {
		return smscorreosugerenc;
	}

	/**
	 * @param smscorreosugerenc
	 *            the smscorreosugerenc to set
	 */
	public void setSmscorreosugerenc(String smscorreosugerenc) {
		this.smscorreosugerenc = smscorreosugerenc;
	}

	// Usuarios
	// metodo para comprobar el alias
	public boolean ccedula(String cedula) {
		List<Usuario> u = manager.findAllUsuarios();
		for (Usuario us : u) {
			if (cedula.equals(us.getCedula())) {
				return true;
			}
		}
		return false;
	}

	// metodo para comprobar el correo
	public boolean ccorreo(String correo) {
		List<Usuario> u = manager.findAllUsuarios();
		for (Usuario us : u) {
			if (correo.equals(us.getCorreo())) {
				return true;
			}
		}
		return false;
	}

	// metodo para comprobar repetidos
	public boolean repetidosc() {
		if (correo.equals(rcorreo)) {
			return true;
		}
		return false;
	}

	// metodo para comprobar repetidos
	public boolean repetidosp() {
		if (password.equals(rpassword)) {
			return true;
		}
		return false;
	}

	// metodo para listar los registros
	public List<Usuario> getListRegServi() {
		List<Usuario> a = manager.findAllUsuarios();
		List<Usuario> l1 = new ArrayList<Usuario>();
		for (Usuario t : a) {
			if (!t.getNombre().equals("root")
					&& !t.getApellido().equals("root")) {
				l1.add(t);
			}
		}
		return l1;
	}

	// accion para invocar el manager y crear evento
	public String crearUsuario() {
		if (this.repetidosc() == false) {
			Mensaje.crearMensajeWARN("Correo Erroneo..!!! Los correos ingresados no coinciden");
		} else if (this.repetidosp() == false) {
			Mensaje.crearMensajeWARN("Contraseña Erronea..!!! Las contraseñas ingresadas no coinciden");
		} else if (this.ccedula(cedula)) {
			Mensaje.crearMensajeWARN("Cédula Repetida..!!! La cédula ya esta siendo utilizada");
		} else if (this.ccorreo(correo)) {
			Mensaje.crearMensajeWARN("Correo Repetido..!!! El correo ya esta siendo utilizado");
		} else {
			try {
				interes = "Ninguno";
				setPassword(Utilidades.Encriptar(getPassword()));
				manager.insertarusuarios(cedula.trim(), alias.trim(),
						apellido.trim(), correo.trim(), direccion.trim(),
						telefono.trim(), celular.trim(), nombre.trim(),
						password.trim(), empreestu, cargptitu, arrayTipoLogin);
				cedula = "";
				alias = "";
				nombre = "";
				apellido = "";
				correo = "";
				direccion = "";
				telefono = "";
				celular = "";
				rcorreo = "";
				password = "";
				rpassword = "";
				tipo = 0;
				empreestu = "";
				cargptitu = "";
				idUsuario = null;
				tipoestusr = manager.EstadoByID(1);
				Mensaje.crearMensajeINFO("Usuario creado");
			} catch (Exception e) {
				Mensaje.crearMensajeWARN("Usuario no pudo ser creado");
				e.printStackTrace();
			}
		}
		return "usuario";
	}

	public String cambiarEstado(Usuario usr) {
		if (usr.getTipo().getIdTipo() == 2) {
			Mensaje.crearMensajeWARN("No se ha añadido ningun tipo al usuario");
		} else {
			try {
				manager.cambioDisEstadousr(usr.getIdUsr());
				Mensaje.crearMensajeINFO("Estado del Registro Modificado");
			} catch (Exception e) {
				System.out.println(e.getMessage());
			}
		}
		return "administracionusuarios";
	}

	// accion para cargar los datos en el formulario
	public String cargarUsuario(Usuario u) {
		try {
			setIdUsuario(u.getIdUsr());
			setNombre(u.getNombre());
			setApellido(u.getApellido());
			setCorreo(u.getCorreo());
			setDireccion(u.getDireccion());
			setTelefono(u.getTelefono());
			setCelular(u.getCelular());
			setPassword(Utilidades.Desencriptar(u.getPassword()));
			setAlias(u.getAlias());
			setCedula(u.getCedula());
			setTipo(u.getTipo().getIdTipo());
			setTipoest(u.getTipoestadousr().getNombreestado());
			setInteres(u.getInteres());
			setSms(u.getSms());
			setEmpreestu(u.getEmpresestdu());
			setCargptitu(u.getCargotitulo());
			setPrincipal(u.getPrincipal());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "";
	}

	// accion para llamar al manager
	public void actualizarUsuario() {
		try {
			manager.cambio(idUsuario, principal);
			manager.editarusuario(idUsuario, cedula.trim(), nombre.trim(),
					apellido.trim(), Utilidades.Encriptar(password),
					correo.trim(), direccion.trim(), telefono.trim(),
					celular.trim(), empreestu, cargptitu, principal, tipo);
			cedula = "";
			alias = "";
			nombre = "";
			apellido = "";
			correo = "";
			direccion = "";
			telefono = "";
			celular = "";
			password = "";
			interes = "";
			tipo = 0;
			empreestu = "";
			cargptitu = "";
			idUsuario = null;
			tipoestusr = manager.EstadoByID(1);
			Mensaje.crearMensajeINFO("Usuario modificado");
		} catch (Exception e) {
			e.printStackTrace();
			Mensaje.crearMensajeWARN("Usuario no modificado");
		}
	}

	public String irAprobador() {
		Mensaje.crearMensajeWARN("Actualización cancelada");
		cedula = "";
		alias = "";
		nombre = "";
		apellido = "";
		correo = "";
		direccion = "";
		telefono = "";
		celular = "";
		rcorreo = "";
		password = "";
		rpassword = "";
		tipo = 0;
		empreestu = "";
		cargptitu = "";
		idUsuario = null;
		// tipoestusr = manager.EstadoByID(1);
		return "";
	}

	public String irAprovador1() {
		try {
			cedula = "";
			alias = "";
			nombre = "";
			apellido = "";
			correo = "";
			direccion = "";
			telefono = "";
			celular = "";
			rcorreo = "";
			password = "";
			rpassword = "";
			tipo = 0;
			empreestu = "";
			cargptitu = "";
			idUsuario = null;
			tipoestusr = manager.EstadoByID(1);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "administracionusuarios";
	}

	// metodo para mostrar los Tipos en Usuarios
	public List<SelectItem> getListaTipo() {
		List<SelectItem> listadoSI = new ArrayList<SelectItem>();
		List<Tipo> tipo = manager.findAllTipo();
		for (Tipo t : tipo) {
			if (t.getTipo().equals("root") || t.getTipo().equals("pendiente")) {
			} else {
				SelectItem item = new SelectItem(t.getIdTipo(), t.getTipo());
				listadoSI.add(item);
			}
		}
		return listadoSI;
	}

	// metodo para asignar el Tipo al Usuario
	public String asignarTipo() {
		manager.asignarTipo(tipo);
		return "";
	}

	// metodo para ir a Tipo
	public String irTipo() {
		return "tipo?faces-redirect=true";
	}

	public String irinteres() {
		return "intereses?faces-redirect=true";
	}

	// metodo para ir a Tipo
	public String irUsuario() {
		return "administracionusuarios?faces-redirect=true";
	}

	// metodo para ir a Tipo
	public String irLog() {
		return "login?faces-redirect=true";
	}

	// metodo para ir a Tipo
	public String irHome() {
		return "home?faces-redirect=true";
	}

	// envio de mensajeria
	public String asignarsms(Usuario usr) {
		if(usr.getTipoestadousr().getIdTipoestadousr() != 1){
			idUsuario = usr.getIdUsr();
			cedula = usr.getCedula();
			nombre = usr.getNombre();
			apellido = usr.getApellido();
			correo = usr.getCorreo();
			direccion = usr.getDireccion();
			telefono = usr.getTelefono();
			celular = usr.getCelular();
			alias = usr.getAlias();
			sms = usr.getSms();
			password = usr.getPassword();
			interes = usr.getInteres();
			tipoest = usr.getTipoestadousr().getNombreestado();
			try {
				if (tipoest.equals("Activado")) {
					smscor = "<!DOCTYPE html><html lang='es'><head><meta http-equiv='Content-Type' content='text/html; charset=utf-8' />"
							+ "<meta name='viewport' content='width=device-width'></head><body>"
							+ "Sr/ra.  "
							+ nombre
							+ " "
							+ apellido
							+ ",le informamos que su petici&oacute;n de registro al sistema "
							+ "REGECE (Reservas de Espacios y Gesti&oacute;n de Eventos del Centro de Emprendimiento) con los datos"
							+ "<br/> C&eacute;dula:"
							+ cedula
							+ ""
							+ "<br/> Nombre:"
							+ nombre
							+ ""
							+ "<br/> Apellido:"
							+ apellido
							+ ""
							+ "<br/> Correo:"
							+ correo
							+ ""
							+ "<br/> Dirección:"
							+ direccion
							+ ""
							+ "<br/> Teléfono:"
							+ telefono
							+ ""
							+ "<br/> Celular:"
							+ celular
							+ ""
							+ "<br/> fue "
							+ tipoest
							+ ";<br/> para ingresar su usuario es: "
							+ cedula
							+ " o su correo "
							+ correo
							+ ", y su contrase&ntildea es:"
							+ "<br/> Saludos cordiales, "
							+ "<br/> Sistema de REGECE Yachay EP"
							+ "<br/><em><strong>NOTA:</strong> Este correo es generado automáticamente por el sistema favor no responder al mismo.</em></body></html>";
					
					RequestContext.getCurrentInstance().execute("PF('dlg2').show();");
				} else if (tipoest.equals("Desactivado")) {
					smscor = "<!DOCTYPE html><html lang='es'><head><meta http-equiv='Content-Type' content='text/html; charset=utf-8' />"
							+ "<meta name='viewport' content='width=device-width'></head><body>"
							+ "Sr/ra.  "
							+ nombre
							+ " "
							+ apellido
							+ ",le informamos que su petici&oacute;n de registro al sistema "
							+ "REGECE (Reservas de Espacios y Gesti&oacute;n de Eventos del Centro de Emprendimiento se encuentra "
							+ tipoest
							+ "."
							+ "<br/> Saludos cordiales, "
							+ "<br/> Sistema de REGECE Yachay EP"
							+ "<br/><em><strong>NOTA:</strong> Este correo es generado automáticamente por el sistema favor no responder al mismo.</em></body></html>";
					RequestContext.getCurrentInstance().execute("PF('dlg2').show();");
				}
				} catch (Exception e) {
					e.printStackTrace();
				}
		}else{
			smscor="";
			Mensaje.crearMensajeWARN("El usuario esta pendiente");
		}
		return "";
	}

	// Tomar el id de estado general id_estadoSolicitud
	public String enviarmensaje(Usuario usr) {
		try {
			if (sms.equals("No Notificado")) {
				if (tipoest.equals("Pendiente")) {
					Mensaje.crearMensajeWARN("Indique si se activa o niega el estado del usuario");
				} else if (tipoest.equals("Activado")) {
					manager.cambioSMSenvio(idUsuario);
					mb.envioMailWS(
							correo,
							"Autorización a YACHAY/REGECE ",
							smscor
									+ " "
									+ Utilidades.Desencriptar(password)
									+ " <br/> la URL del sistema REGECE: http://regece.yachay.gob.ec:8080/");
					cedula = "";
					alias = "";
					nombre = "";
					apellido = "";
					correo = "";
					direccion = "";
					telefono = "";
					celular = "";
					rcorreo = "";
					password = "";
					rpassword = "";
					tipo = 0;
					empreestu = "";
					cargptitu = "";
					idUsuario = null;
					tipoestusr = manager.EstadoByID(1);
					Mensaje.crearMensajeINFO("Enviado correctamente al correo");

				} else if (tipoest.equals("Desactivado")) {
					manager.cambioSMSenvio(idUsuario);
					mb.envioMailWS(correo, "Autorización a YACHAY/REGECE",
							smscor);
					cedula = "";
					alias = "";
					nombre = "";
					apellido = "";
					correo = "";
					direccion = "";
					telefono = "";
					celular = "";
					rcorreo = "";
					password = "";
					rpassword = "";
					tipo = 0;
					empreestu = "";
					cargptitu = "";
					idUsuario = null;
					tipoestusr = manager.EstadoByID(1);
					Mensaje.crearMensajeINFO("Enviado correctamente al correo");
				}
			} else {
				Mensaje.crearMensajeINFO("Se ha enviado al correo la notificación");
			}

		} catch (Exception e) {
			Mensaje.crearMensajeWARN("Error al enviar correo");
		}
		return "";
	}

	public String cambioEnvioSms() {
		Mensaje.crearMensajeINFO("Notificación aceptada");
		return "";
	}

	// ////////////////////////////////////sugerencias////////////////////////////////////////////////////////////////////////
	// metodo para cargar la lista de sugerencias
	public String Cargarsugerencia(Sugerencia suge) {
		idsuge = suge.getIdSugerencia();
		usu = suge.getUsuario();
		idUsuario = suge.getUsuario().getIdUsr();
		nombre = suge.getUsuario().getNombre();
		apellido = suge.getUsuario().getApellido();
		smssuge = suge.getSms();
		correo = suge.getUsuario().getCorreo();
		cedula = suge.getUsuario().getCedula();
		DateFormat date = new SimpleDateFormat("dd/MM/yyyy");
		sugerenciafecha = suge.getFecha();
		date.format(sugerenciafecha).toString();
		sugerenciatext = suge.getSugerencia();
		return "";
	}

	public String ahome() {
		sugerenciatext = "";
		return "home?faces-redirect=true";
	}

	// ir a sugerencias lista
	public String irsugerencia() {
		idsuge = null;
		usu = null;
		idUsuario = null;
		nombre = "";
		apellido = "";
		smssuge = "";
		smscor = "";
		correo = "";
		cedula = "";
		sugerenciafecha = null;
		sugerenciatext = "";
		empreestu = "";
		cargptitu = "";
		return "sugerencialist?faces-redirect=true";
	}

	// metodo para listar las sugerencias
	public List<Sugerencia> getListSugerencia() {
		return manager.findAllSugerencias();
	}

	// accion para invocar el manager y crear sugerencia
	public void crearsugerencia() {
		try {
			if (sugerenciatext.isEmpty() || sugerenciatext.equals("")) {
				Mensaje.crearMensajeWARN("Indique su sugerencia");
			} else {
				cargasuge();
				DateFormat date = new SimpleDateFormat("dd/MM/yyyy");
				Usuario usr = manager.UsuarioByID(idusrsug);
				manager.insertarSugerencia(idusrsug, getSugerenciatext(),
						sugerenciafecha);
				smscoradmin = "<!DOCTYPE html><html lang='es'><head><meta http-equiv='Content-Type' content='text/html; charset=utf-8' />"
						+ "<meta name='viewport' content='width=device-width'></head><body>"
						+ "El Sr/ra. "
						+ usr.getNombre()
						+ " "
						+ usr.getApellido()
						+ ", envi&oacute; una sugerencia.; <br/>"
						+ "Los datos del usuario son:"
						+ "<br/> C&eacute;dula: "
						+ usr.getCedula()
						+ ""
						+ "<br/> Nombre: "
						+ usr.getNombre()
						+ ""
						+ "<br/> Apellido: "
						+ usr.getApellido()
						+ ""
						+ "<br/> Correo: "
						+ usr.getCorreo()
						+ ""
						+ "<br/> Dirección: "
						+ usr.getDireccion()
						+ ""
						+ "<br/> Teléfono: "
						+ usr.getTelefono()
						+ ""
						+ "<br/> Celular: "
						+ usr.getCelular()
						+ ""
						+ "<br/> Fecha: "
						+ date.format(sugerenciafecha).toString()
						+ ""
						+ "<br/> Sugerencia: "
						+ sugerenciatext
						+ "<br/> Saludos cordiales, "
						+ "<br/> Sistema de REGECE Yachay EP"
						+ "<br/><em><strong>NOTA:</strong> Este correo es generado automáticamente por el sistema favor no responder al mismo.</em></body></html>";

				smscorreosugerenc = "<!DOCTYPE html><html lang='es'><head><meta http-equiv='Content-Type' content='text/html; charset=utf-8'/>"
						+ "<meta name='viewport' content='width=device-width'></head><body>"
						+ "El usuario con apellido "
						+ usr.getApellido()
						+ " con nombre "
						+ usr.getNombre()
						+ "; le informamos que su sugerencia al sistema REGECE ser&aacute; revisada.<br/> "
						+ "Su sugerencia es: "
						+ sugerenciatext
						+ "<br/> Saludos cordiales, "
						+ "<br/> Sistema de REGECE Yachay EP"
						+ "<br/><em><strong>NOTA:</strong> Este correo es generado automáticamente por el sistema favor no responder al mismo.</em></body></html>";

				getcorreosusu();
				mb.envioMailWS(correosadmin, "Notificación de YACHAY/REGECE ",
						smscoradmin);
				mb.envioMailWS(usr.getCorreo(),
						"Envío de Sugerencia a YACHAY/REGECE",
						smscorreosugerenc);
				sugerenciatext = "";
				correosadmin = "";
				sugerenciafecha = null;
				idsuge = null;
				Mensaje.crearMensajeINFO("La sugerencia se guardó correctamente");
			}
		} catch (Exception e) {
			Mensaje.crearMensajeWARN("No se pudo guardar la sugerencia");
			e.printStackTrace();
		}
	}

	// envio de mensajeria
	public String asignarsmssugerencia(Sugerencia suge) {
		idsuge = suge.getIdSugerencia();
		usu = suge.getUsuario();
		idUsuario = suge.getUsuario().getIdUsr();
		nombre = suge.getUsuario().getNombre();
		apellido = suge.getUsuario().getApellido();
		smssuge = suge.getSms();
		correo = suge.getUsuario().getCorreo();
		telefono = suge.getUsuario().getTelefono();
		celular = suge.getUsuario().getCelular();
		cedula = suge.getUsuario().getCedula();
		sugerenciafecha = suge.getFecha();
		DateFormat date = new SimpleDateFormat("dd/MM/yyyy");
		date.format(sugerenciafecha).toString();
		sugerenciatext = suge.getSugerencia();
		try {
			smscor = "<!DOCTYPE html><html lang='es'><head><meta http-equiv='Content-Type' content='text/html; charset=utf-8' />"
					+ "<meta name='viewport' content='width=device-width'></head><body>"
					+ "Sr/ra.  "
					+ nombre
					+ " "
					+ apellido
					+ ",le informamos que su sugerencia al sistema "
					+ "REGECE (Reservas de Espacios y Gesti&oacute;n de Eventos del Centro de Emprendimiento) con los datos"
					+ "<br/> C&eacute;dula:"
					+ cedula
					+ ""
					+ "<br/> Nombre:"
					+ nombre
					+ ""
					+ "<br/> Apellido:"
					+ apellido
					+ ""
					+ "<br/> Correo:"
					+ correo
					+ ""
					+ "<br/> Teléfono:"
					+ telefono
					+ ""
					+ "<br/> Celular:"
					+ celular
					+ ""
					+ "<br/> En la fecha:"
					+ sugerenciafecha
					+ ""
					+ "<br/> fue revisada por los administradores, agradecemos de antemano su sugerencia."
					+ "<br/> Saludos cordiales, "
					+ "<br/> Sistema de REGECE Yachay EP"
					+ "<br/><em><strong>NOTA:</strong> Este correo es generado automáticamente por el sistema favor no responder al mismo.</em></body></html>";
		} catch (Exception e) {
			e.printStackTrace();
		}
		return smscor;
	}

	// Tomar el id de estado general id_estadoSolicitud
	public String enviarmensajesugerencia(Sugerencia suge) {
		try {
			if (smssuge.equals("No Revisada")) {
				manager.cambioSMSenviosugerencia(idsuge);
				mb.envioMailWS(correo, "Sugerencia a YACHAY/REGECE", smscor);
				// limpiamos los datos notificaciones.inno@gmail.com
				// innopolisyachay2015@gmail.com
				cedula = "";
				alias = "";
				nombre = "";
				apellido = "";
				correo = "";
				direccion = "";
				telefono = "";
				celular = "";
				rcorreo = "";
				password = "";
				rpassword = "";
				tipo = 0;
				empreestu = "";
				cargptitu = "";
				idUsuario = null;
				tipoestusr = manager.EstadoByID(1);
				Mensaje.crearMensajeINFO("Enviado correctamente al correo");
			} else {
				Mensaje.crearMensajeINFO("Yá se ha enviado la notificación");
			}

		} catch (Exception e) {
			Mensaje.crearMensajeWARN("Error al enviar correo");
		}
		return "";
	}

	// ir a sugerencias lista
	public String irsugerenciavolver() {
		idsuge = null;
		usu = null;
		idUsuario = null;
		nombre = "";
		apellido = "";
		smssuge = "";
		correo = "";
		direccion = "";
		telefono = "";
		celular = "";
		smscor = "";
		cedula = "";
		sugerenciafecha = null;
		sugerenciatext = "";
		empreestu = "";
		cargptitu = "";
		Mensaje.crearMensajeWARN("Actualización cancelada");
		return "sugerencialist?faces-redirect=true";
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

	// Cargar datos fecha
	public void cargasuge() {
		Calendar calendar = Calendar.getInstance();
		java.sql.Timestamp ourJavaTimestampObject = new java.sql.Timestamp(
				calendar.getTime().getTime());
		sugerenciafecha = ourJavaTimestampObject;
	}

	// metodo lista de aprobados solicitudes con recursos por usuario
	public List<Sugerencia> getListadoSugerencias() {
		List<Sugerencia> a = manager.findAllSugerenciasOrdenadas();
		List<Sugerencia> l1 = new ArrayList<Sugerencia>();
		try {
			for (Sugerencia t : a) {
				l1.add(t);
			}
		} catch (Exception e) {
			Mensaje.crearMensajeWARN("Error no carga la lista");
		}
		return l1;
	}

	// metodo para mostrar los Actividades
	public List<SelectItem> getListaIntereses() {
		List<SelectItem> listadoTEU = new ArrayList<SelectItem>();
		List<Interes> listadoEstadoU = manager.findAllIntereses();
		for (Interes t : listadoEstadoU) {
			SelectItem item = new SelectItem(t.getIdInteres(), t.getNombreInt());
			listadoTEU.add(item);
		}
		return listadoTEU;
	}

	// metodo para listar los intereses de cada usuario
	public List<String> interes(Usuario t) {
		List<String> v = new ArrayList<String>();
		if (t == null) {

		} else {

			List<Interesesmid> i = manager.findAllInteresesmid();
			List<Interesesmid> o = new ArrayList<Interesesmid>();
			for (Interesesmid p : i) {
				if (p.getUsuario().getIdUsr().equals(t.getIdUsr())) {
					o.add(p);
				}
			}
			List<Interes> la = manager.findAllIntereses();
			for (Interes a : la) {
				for (Interesesmid g : o) {
					if (a.getIdInteres().equals(g.getInteres().getIdInteres())) {
						v.add(g.getInteres().getNombreInt());
					}
				}
			}
		}
		return v;
	}

	// metodo para listar los intereses de cada usuario
	public List<String> interesedicion() {
		List<String> v = new ArrayList<String>();
		List<Interesesmid> i = manager.findAllInteresesmid();
		List<Interesesmid> o = new ArrayList<Interesesmid>();
		for (Interesesmid p : i) {
			if (p.getUsuario().getIdUsr().equals(idUsuario)) {
				o.add(p);
			}
		}
		List<Interes> la = manager.findAllIntereses();
		for (Interes a : la) {
			for (Interesesmid g : o) {
				if (a.getIdInteres().equals(g.getInteres().getIdInteres())) {
					v.add(g.getInteres().getNombreInt());
				}
			}
		}
		return v;
	}
}