package innopolis.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import innopolis.entidades.Actividad;
import innopolis.entidades.Inter;
import innopolis.entidades.Interes;
import innopolis.entidades.Interesesmid;
import innopolis.entidades.Tipo;
import innopolis.entidades.Usuario;
import innopolis.entidades.help.UsuarioHelp;
import innopolis.entidades.help.Utilidades;
import innopolis.manager.ManagerBuscar;
import innopolis.manager.ManagerLogin;
import innopolis.model.generic.Mensaje;

import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;
import javax.servlet.http.HttpSession;

@SessionScoped
@ManagedBean
public class SessionBean {
	private UsuarioHelp session;
	private ManagerLogin manager;

	@EJB
	private ManagerBuscar mb;
	// log
	private String Cedula;
	private String pass;

	// devolver contraseña
	private String correocontra;
	String smscor = "";

	// mostrar
	private String nom;

	private List<Actividad> la;

	private Usuario usr;

	/* Perfil de Usuario */
	private String nombre, apellido, password, correo, direccion, telefono,
			celular, cedula, alias, empreestu, cargptitu, tipousuario;
	private boolean principal;
	private Integer idUsr;
	private Integer[] arrayTipoLogin;
	private Interes intereses;

	public SessionBean() {
		manager = new ManagerLogin();
		usr = new Usuario();
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
	 * @return the arrayTipoLogin
	 */
	public Integer[] getArrayTipoLogin() {
		return arrayTipoLogin;
	}

	/**
	 * @param arrayTipoLogin
	 *            the arrayTipoLogin to set
	 */
	public void setArrayTipoLogin(Integer[] arrayTipoLogin) {
		this.arrayTipoLogin = arrayTipoLogin;
	}

	public Integer getIdUsr() {
		return idUsr;
	}

	public void setIdUsr(Integer idUsr) {
		this.idUsr = idUsr;
	}

	public String getNick() {
		return Cedula;
	}

	public String getPass() {
		return pass;
	}

	public UsuarioHelp getSession() {
		return session;
	}

	public List<Actividad> getLa() {
		return la;
	}

	public void setLa(List<Actividad> la) {
		this.la = la;
	}

	public String getNom() {
		return nom;
	}

	public void setNom(String nom) {
		this.nom = nom;
	}

	public void setNick(String nick) {
		this.Cedula = nick;
	}

	public void setPass(String pass) {
		this.pass = pass;
	}

	/* Perfil Usuario */
	public String getApellido() {
		return apellido;
	}

	public void setApellido(String apellido) {
		this.apellido = apellido;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
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

	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}

	public String getTelefono() {
		return telefono;
	}

	public String getCelular() {
		return celular;
	}

	public void setCelular(String celular) {
		this.celular = celular;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getCedula() {
		return cedula;
	}

	public void setCedula(String cedula) {
		this.cedula = cedula;
	}

	/**
	 * @return the correocontra
	 */
	public String getCorreocontra() {
		return correocontra;
	}

	/**
	 * @param correocontra
	 *            the correocontra to set
	 */
	public void setCorreocontra(String correocontra) {
		this.correocontra = correocontra;
	}

	/**
	 * @return the alias
	 */
	public String getAlias() {
		return alias;
	}

	/**
	 * @param alias
	 *            the alias to set
	 */
	public void setAlias(String alias) {
		this.alias = alias;
	}

	public boolean isPrincipal() {
		return principal;
	}

	public void setPrincipal(boolean principal) {
		this.principal = principal;
	}

	public String getEmpreestu() {
		return empreestu;
	}

	public void setEmpreestu(String empreestu) {
		this.empreestu = empreestu;
	}

	public String getCargptitu() {
		return cargptitu;
	}

	public void setCargptitu(String cargptitu) {
		this.cargptitu = cargptitu;
	}

	public String getTipousuario() {
		return tipousuario;
	}
	
	public void setTipousuario(String tipousuario) {
		this.tipousuario = tipousuario;
	}
	
	// login
	public void veri() {
		int t = 0;
		List<Usuario> a = manager.findAllUsuarios();
		for (Usuario u : a) {
			if ((u.getCedula().equals(Cedula) || u.getCorreo().equals(Cedula))
					&& u.getTipoestadousr().getIdTipoestadousr() == 2) {
				t = 100;
			}
		}
		if (t != 100) {
			Mensaje.crearMensajeWARN("Usuario Desactivado");
		}
	}

	// metodo para ingresar al sistema
	public String login() {
		String r = "";
		Integer t = 0;
		List<Usuario> u = manager.findAllUsuarios();
		try {
			for (Usuario y : u) {
				System.out.println("avr "
						+ Utilidades.Encriptar(pass).toString());
				if (y.getCedula().equals(Cedula)
						&& y.getPassword().equals(Utilidades.Encriptar(pass))
						&& y.getTipoestadousr().getIdTipoestadousr() == 2) {
					session = new UsuarioHelp(y.getIdUsr(), y.getAlias(),
							y.getApellido(), y.getCorreo(), y.getDireccion(),
							y.getTelefono(), y.getCelular(), y.getNombre(), y
									.getTipo().getTipo(), y.getCedula(),
							y.getPassword(), y.getPrincipal(),
							y.getEmpresestdu(), y.getCargotitulo());
					nom = y.getNombre() + " " + y.getApellido() + " : "
							+ y.getTipo().getTipo();
					usr = y;
					perfilUsuario();
					r = "home?faces-redirect=true";
					t = 1;
				} else if (y.getCorreo().equals(Cedula)
						&& y.getPassword().equals(Utilidades.Encriptar(pass))
						&& y.getTipoestadousr().getIdTipoestadousr() == 2) {
					session = new UsuarioHelp(y.getIdUsr(), y.getAlias(),
							y.getApellido(), y.getCorreo(), y.getDireccion(),
							y.getTelefono(), y.getCelular(), y.getNombre(), y
									.getTipo().getTipo(), y.getCedula(),
							y.getPassword(), y.getPrincipal(),
							y.getEmpresestdu(), y.getCargotitulo());
					nom = y.getNombre() + " " + y.getApellido() + " : "
							+ y.getTipo().getTipo();
					usr = y;
					perfilUsuario();
					r = "home?faces-redirect=true";
					t = 1;
				} else if (y.getCedula().equals(Cedula)
						&& y.getPassword().equals(Utilidades.Encriptar(pass))
						&& y.getTipoestadousr().getIdTipoestadousr() == 1) {
					Mensaje.crearMensajeWARN("El usuario no ha sido validado aún");
					t = 2;
					break;
				}
			}
			if (t == 0) {
				Mensaje.crearMensajeWARN("Error, Usuario o Contrasena incorrecta");
			}
			this.activacion();

		} catch (Exception e) {

		}
		return r;
	}

	public void activacion() {
		la = new ArrayList<Actividad>();
		Tipo t = new Tipo();
		String ps = "";
		try {
			ps = Utilidades.Encriptar(pass);
		} catch (Exception e) {
			e.printStackTrace();
		}
		if (usr.getCedula().equals(Cedula) && usr.getPassword().equals(ps)) {
			t = usr.getTipo();
		} else if (usr.getCorreo().equals(Cedula)
				&& usr.getPassword().equals(ps)) {
			t = usr.getTipo();
		}
		List<Inter> i = manager.findAllInter();
		List<Inter> n = new ArrayList<Inter>();
		for (Inter h : i) {
			if (t.getIdTipo() == h.getTipo().getIdTipo()) {
				n.add(h);
			}
		}
		List<Actividad> a = manager.findAllActividades();
		for (Actividad c : a) {
			for (Inter k : n) {
				if (k.getActividad().getIdActividad() == c.getIdActividad()) {
					la.add(c);
				}
			}
		}
		System.out.println(la.size() + "si hay datos");
	}

	// metodo para salir de el sistema
	public String logout() {
		HttpSession session = (HttpSession) FacesContext.getCurrentInstance()
				.getExternalContext().getSession(false);
		session.invalidate();
		nom = "";
		correo = "";
		pass = "";
		direccion = "";
		principal = false;
		password = "";
		telefono = "";
		celular = "";
		Cedula = "";
		tipousuario ="";
		System.out.println("si salio");
		return "";
	}

	// metodo para salir de el sistema
	public String perfilUsuario() {
		String r = "";
		try {
			cargarDatosPerfil();
		} catch (Exception e) {
			Mensaje.crearMensajeWARN("Error al  cargar sus datos personales");
		}
		return r;
	}

	/**
	 * Método para verifiar la existencia de la sesión
	 * 
	 * @param rol
	 *            de usuario
	 * @return Clase Usuario
	 */
	public static UsuarioHelp verificarSession() {
		HttpSession session = (HttpSession) FacesContext.getCurrentInstance()
				.getExternalContext().getSession(false);
		SessionBean user = (SessionBean) session.getAttribute("sessionBean");
		if (user == null || user.getSession() == null) {
			try {
				FacesContext.getCurrentInstance().getExternalContext()
						.redirect("../index.xhtml");
			} catch (IOException ex) {
				Mensaje.crearMensajeWARN("Error");
			}
			return null;
		} else {
			return user.getSession();
		}
	}

	public String cargarDatosPerfil() {
		String pag = "";
		if (session != null) {
			try {
				System.out.println(session.getIdUsr());
				Usuario usr = manager.UsuarioByID(session.getIdUsr());
				setIdUsr(usr.getIdUsr());
				setCedula(usr.getCedula());
				setApellido(usr.getApellido());
				setNombre(usr.getNombre());
				setCorreo(usr.getCorreo());
				setDireccion(usr.getDireccion());
				setTelefono(usr.getTelefono());
				setCelular(usr.getCelular());
				setPassword(Utilidades.Desencriptar(usr.getPassword()));
				setPrincipal(usr.getPrincipal());
				setEmpreestu(usr.getEmpresestdu());
				setTipousuario(usr.getTipo().getTipo());
				setCargptitu(usr.getCargotitulo());
			} catch (Exception e) {
				Mensaje.crearMensajeWARN("Error al cargar sus datos personales");
			}
		}
		return pag;
	}

	// accion para llamar al manager
	public String actualizarUsuario() {
		try {
			manager.editarusuarioperfil(idUsr, nombre.trim(),
					Utilidades.Encriptar(password), apellido.trim(),
					correo.trim(), direccion.trim(), telefono.trim(),
					celular.trim(), empreestu.trim(), cargptitu.trim());
			Mensaje.crearMensajeINFO("Perfil modificado");
		} catch (Exception e) {
			Mensaje.crearMensajeWARN("Error al modificar usuario");
			e.printStackTrace();
		}
		return "";
	}

	public List<String> interesedicion() {
		List<String> v = new ArrayList<String>();
		List<Interesesmid> i = manager.findAllInteresesmid();
		List<Interesesmid> o = new ArrayList<Interesesmid>();
		for (Interesesmid p : i) {
			if (p.getUsuario().getIdUsr().equals(idUsr)) {
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

	public void cambioDatosPerfil() {
		try {
			manager.modificarDatosUSR(session.getIdUsr(), getCedula(),
					getNombre(), getApellido(), getCorreo());
		} catch (Exception e) {
			Mensaje.crearMensajeWARN("Error al cambiar sus datos personales");
		}
	}

	public String regresarHomeUser() {
		return "home?faces-redirect=true";
	}

	// metodo para enviar el correo
	public String devolvercontra() {
		String r = "";
		Integer t = 0;
		List<Usuario> u = manager.findAllUsuarios();
		for (Usuario y : u) {
			if (y.getCorreo().equals(correocontra)) {
				System.out.println("si entra1");
				enviarmensajerecuperarcontra(y);
				Mensaje.crearMensajeINFO("Enviado correctamente a su correo");
				t = 1;
			}
		}
		if (t == 0) {
			Mensaje.crearMensajeWARN("Error, El correo no existe o es incorrecto.");
		}
		return r;
	}
	String correoveri = "";

	// Tomar el id de estado general id_estadoSolicitud
	public String enviarmensajerecuperarcontra(Usuario usr) {
		try {
			if (!correocontra.equals(correoveri)) {
				String passwordnuevo;
				cedula = usr.getCedula();
				nombre = usr.getNombre();
				apellido = usr.getApellido();
				correo = usr.getCorreo();
				direccion = usr.getDireccion();
				telefono = usr.getTelefono();
				celular = usr.getCelular();
				alias = usr.getAlias();
				password = usr.getPassword();
				passwordnuevo = Utilidades.Desencriptar(password);
				smscor = "<!DOCTYPE html><html lang='es'><head><meta http-equiv='Content-Type' content='text/html; charset=utf-8' />"
						+ "<meta name='viewport' content='width=device-width'></head><body>"
						+ "Sr/ra. "
						+ nombre
						+ " "
						+ apellido
						+ ",sus datos son los siguientes: "
						+ "<br/> C&eacute;dula: "
						+ cedula
						+ ""
						+ "<br/> Nombre: "
						+ nombre
						+ ""
						+ "<br/> Apellido: "
						+ apellido
						+ ""
						+ "<br/> Correo: "
						+ correo
						+ ""
						+ "<br/> Direcci&oacute;n: "
						+ direccion
						+ ""
						+ "<br/> Tel&eacute;fono: "
						+ telefono
						+ ""
						+ "<br/> Celular: "
						+ celular
						+ ""
						+ "<br/> para ingresar su usuario es: "
						+ cedula
						+ " o su correo "
						+ correo
						+ ", y su contrase&ntildea es: "
						+ passwordnuevo
						+ "<br/> Saludos cordiales, "
						+ "<br/> Sistema de REGECE Yachay EP"
						+ "<br/><em><strong>NOTA:</strong> Este correo es generado autom&aacute;ticamente por el sistema favor no responder al mismo.</em></body></html>";
				mb.envioMailWS(correo,
						"Recuperación de contraseña YACHAY/REGECE ", smscor);
				cedula = "";
				nombre = "";
				apellido = "";
				correoveri = correo;
				correo = "";
				direccion = "";
				telefono = "";
				celular = "";
				alias = "";
				password = "";
				passwordnuevo = "";
				smscor = "";
				Mensaje.crearMensajeINFO("Enviado correctamente al correo su contraseña");
			} else if (correoveri.equals(correocontra)) {
				Mensaje.crearMensajeINFO("Ya se ha enviado al correo su contraseña");
			}
		} catch (Exception e) {
			Mensaje.crearMensajeINFO("Error al enviar correo");
			e.printStackTrace();
		}
		return "index?faces-redirect=true";
	}

	private String panel1 = "Show-Panel1";
	private String panel2 = "Show-Panel2";

	public void setPanel1(String panel1) {
		this.panel1 = panel1;
	}

	public String getPanel1() {
		return this.panel1;
	}

	public void setPanel2(String panel2) {
		this.panel2 = panel2;
	}

	public String getPanel2() {
		return this.panel2;
	}
}
