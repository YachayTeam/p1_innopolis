package innopolis.controller;

import innopolis.entidades.Interes;
import innopolis.entidades.Tipo;
import innopolis.entidades.Tipoestadousr;
import innopolis.entidades.Usuario;
import innopolis.entidades.help.Utilidades;
import innopolis.manager.ManagerBuscar;
import innopolis.manager.ManagerLogin;
import innopolis.model.generic.Mensaje;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.model.SelectItem;

@SessionScoped
@ManagedBean
public class RegistroBean implements Serializable {
	private static final long serialVersionUID = 1L;
	private ManagerLogin manager;
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
	private String sms;
	private String empreestu;
	private String cargptitu;

	// envio de correo a los administradores
	private String smscoradmin;
	private String smscorusu;
	private String correosadmin;

	@EJB
	private ManagerBuscar mb;

	// intereses
	private Integer[] arrayTipoLogin;
	private Interes intereses;

	// empresa cargo titulo
	private String valor;

	public RegistroBean() {
		manager = new ManagerLogin();
	}

	public Integer getIdUsuario() {
		return idUsuario;
	}

	public Integer getTipo() {
		return tipo;
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
	 * @return the smscor
	 */
	public String getSmscor() {
		return smscoradmin;
	}

	/**
	 * @param smscor
	 *            the smscor to set
	 */
	public void setSmscor(String smscor) {
		this.smscoradmin = smscor;
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
	 * @return the valor
	 */
	public String getValor() {
		return valor;
	}

	/**
	 * @param valor
	 *            the valor to set
	 */
	public void setValor(String valor) {
		this.valor = valor;
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

	// metodo para comprobar el alias
	public boolean calias(String alias) {
		List<Usuario> u = manager.findAllUsuarios();
		for (Usuario us : u) {
			if (alias.equals(us.getAlias())) {
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

	// accion para invocar el manager y crear evento
	public String crearUsuario() {
		String a = "ingresousuario";
		asignarTipo();
		if (this.repetidosc() == false) {
			Mensaje.crearMensajeWARN("Los correos ingresados no coinciden");
		} else if (this.repetidosp() == false) {
			Mensaje.crearMensajeWARN("Las contraseñas ingresadas no coinciden");
		} else if (this.ccedula(cedula)) {
			Mensaje.crearMensajeWARN("La cédula/pasaporte ya está siendo utilizada");
		} else if (this.ccorreo(correo)) {
			Mensaje.crearMensajeWARN("El correo ya está siendo utilizado");
		} else if (tipo == null){
			Mensaje.crearMensajeWARN("Escoja el tipo de usuario referencial ");
		} else {
			try {
				setPassword(Utilidades.Encriptar(getPassword()));// MD5 PASS
				manager.registrarUsuario(cedula, alias, apellido, correo,
						direccion, telefono, celular, nombre, password, sms,
						empreestu, cargptitu, arrayTipoLogin);
				smscoradmin = "<!DOCTYPE html><html lang='es'><head><meta http-equiv='Content-Type' content='text/html; charset=utf-8' />"
						+ "<meta name='viewport' content='width=device-width'></head><body>"
						+ "El usuario con apellido "
						+ apellido
						+ " con nombre "
						+ nombre
						+ "; Requiere la aprobaci&oacute;n o negaci&oacute;n del registro al sistema; <br/>"
						+ "los datos del usuario son:"
						+ "<br/> C&eacute;dula: "
						+ cedula
						+ ""
						+ "<br/> Nombre: "
						+ nombre
						+ ""
						+ "<br/> Apellido: "
						+ apellido
						+ ""
						+ "\n Correo: "
						+ correo
						+ ""
						+ "\n Direcci&oacute;n: "
						+ direccion
						+ ""
						+ "\n Tel&eacute;fono: "
						+ telefono
						+ ""
						+ "\n Celular: "
						+ celular
						+ ""
						+ "<br/> Saludos cordiales, "
						+ "<br/> Sistema de REGECE Yachay EP"
						+ "<br/><em><strong>NOTA:</strong> Este correo es generado automáticamente por el sistema favor no responder al mismo.</em></body></html>";

				smscorusu = "<!DOCTYPE html><html lang='es'><head><meta http-equiv='Content-Type' content='text/html; charset=utf-8' />"
						+ "<meta name='viewport' content='width=device-width'></head><body>"
						+ "Sr/ra.  "
						+ nombre
						+ " "
						+ apellido
						+ ", su petici&oacute;n de acceso al sistema REGECE (Reservas de Espacios y Gesti&oacute;n de Eventos del Centro de Emprendimiento), ser&aacute; verificado por los administradores, espere al mensaje de confirmaci&oacute;n. <br/>"
						+ "sus datos son: "
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
						+ "\n Direcci&oacute;n: "
						+ direccion
						+ ""
						+ "\n Tel&eacute;fono: "
						+ telefono
						+ ""
						+ "\n Celular: "
						+ celular
						+ ""
						+ "<br/> Saludos cordiales, "
						+ "<br/> Sistema de REGECE Yachay EP"
						+ "<br/><em><strong>NOTA:</strong> Este correo es generado automáticamente por el sistema favor no responder al mismo.</em></body></html>";

				getcorreosusu();
				mb.envioMailWS(correosadmin, "Notificación de YACHAY/REGECE",
						smscoradmin);
				mb.envioMailWS(correo, "Notificación de YACHAY/REGECE",
						smscorusu);
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
				interes = "";
				tipo = null;
				sms = "";
				smscoradmin = "";
				idUsuario = null;
				correosadmin = "";
				empreestu = "";
				cargptitu = "";
				smscorusu = "";
				tipoestusr = manager.EstadoByID(1);
				Mensaje.crearMensajeINFO("Usuario creado espere la activación del mismo");
				a = "index";
			} catch (Exception e) {
				Mensaje.crearMensajeWARN("El usuario no pudo ser creado");
				e.printStackTrace();
			}
		}
		return a;
	}

	// metodo para listar correos de ususariosadmin
	public String getcorreosusu() {
		try {
			List<Usuario> a = manager.findUsrsPrincipal();
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

	// metodo para mostrar los Tipos en Usuarios
	public List<SelectItem> getListaTipo() {
		List<SelectItem> listadoSI = new ArrayList<SelectItem>();
		List<Tipo> tipo = manager.findAllTipo();
		for (Tipo t : tipo) {
			if (t.getTipo().equals("root") || t.getTipo().equals("pendiente")
					|| t.getTipo().equals("Administrador")
					|| t.getTipo().equals("Gestor y Aprobador")) {
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
}