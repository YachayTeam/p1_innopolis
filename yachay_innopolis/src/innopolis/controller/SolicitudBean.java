package innopolis.controller;

import java.sql.Time;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import innopolis.entidades.Recurso;
import innopolis.entidades.Solicicabecera;
import innopolis.entidades.Solicidetalle;
import innopolis.entidades.Soliciestado;
import innopolis.entidades.Usuario;
import innopolis.entidades.help.UsuarioHelp;
import innopolis.manager.ManagerBuscar;
import innopolis.manager.ManagerLogin;
import innopolis.manager.ManagerReservas;
import innopolis.manager.Validacion;
import innopolis.model.generic.Mensaje;

import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.model.SelectItem;

import org.primefaces.model.DefaultScheduleEvent;
import org.primefaces.model.ScheduleEvent;
import org.primefaces.model.ScheduleModel;

@SessionScoped
@ManagedBean
public class SolicitudBean {
	private ManagerReservas manager;
	private ManagerLogin managerlog;

	@EJB
	private ManagerBuscar mb;

	// Atributo de solicitud
	// Cabecera
	private String direccion;
	private String actividad;
	private String justificacion;
	private String objetivo;
	private String notificacion;
	// Manejo detalles
	private Date recursofecha;
	private Timestamp h_inicio;
	private Timestamp h_fin;
	private List<SelectItem> select;
	// Cambios solicitud
	private List<Solicidetalle> listDetalles;
	private Soliciestado estadoSol;
	private Integer id_sol;

	// calendario
	private Date date = new Date();
	private ScheduleModel eventModel;
	private ScheduleEvent event = new DefaultScheduleEvent();
	// calendario
	private Date fi;
	private Date ff;

	private Time horafin;
	private Time horainicio;

	// Detalles
	private Integer id_recurso;
	private Recurso recurso;
	private Integer capacidad_recurso;
	private Solicicabecera solicitudCabTem;
	private boolean solicitudCabTmpGuardada;
	private List<Solicicabecera> listadoSolCab;

	// tiporecurso
	private Integer id_recursotipo;
	private List<SelectItem> select2;
	private UsuarioHelp session;

	// envio de correo a los administradores
	private String smscoradmin;
	private String smscorusu;
	private String correosadmin;

	// sacar la descripcion del tipo de ubicacion
	private boolean mostrar;
	private String descripcionubicacion;
	private String stock;
	private String imagen;

	private boolean agregardetalle;

	int contador = 0;

	public SolicitudBean() {
		session = SessionBean.verificarSession();
		manager = new ManagerReservas();
		managerlog = new ManagerLogin();
		// Select Vacio
		select2 = new ArrayList<SelectItem>();
		select = new ArrayList<SelectItem>();
		direccion = session.getNombre();
		justificacion = session.getApellido();
		solicitudCabTem = null;
		mostrar = false;
		agregardetalle = true;
		actividad = "";
		objetivo = "";
		contador = 0;
		capacidad_recurso = null;
		descripcionubicacion = "Descripción del Recurso";
		stock = "stock";
		imagen = "300.jpg";
	}

	public Time getHorafin() {
		return horafin;
	}

	public void setHorafin(Time horafin) {
		this.horafin = horafin;
	}

	public Time getHorainicio() {
		return horainicio;
	}

	public void setHorainicio(Time horainicio) {
		this.horainicio = horainicio;
	}

	/**
	 * @return the agregardetalle
	 */
	public boolean isAgregardetalle() {
		return agregardetalle;
	}

	/**
	 * @param agregardetalle
	 *            the agregardetalle to set
	 */
	public void setAgregardetalle(boolean agregardetalle) {
		this.agregardetalle = agregardetalle;
	}

	public Recurso getRecurso() {
		return recurso;
	}

	public void setRecurso(Recurso recurso) {
		this.recurso = recurso;
	}

	// Metodos Get y Set
	public ManagerReservas getManager() {
		return manager;
	}

	public Date getFi() {
		return fi;
	}

	public void setFi(Date fi) {
		this.fi = fi;
	}

	public Date getFf() {
		return ff;
	}

	public void setFf(Date ff) {
		this.ff = ff;
	}

	/**
	 * @return the date
	 */
	public Date getDate() {
		return date;
	}

	/**
	 * @param date
	 *            the date to set
	 */
	public void setDate(Date date) {
		this.date = date;
	}

	/**
	 * @return the eventModel
	 */
	public ScheduleModel getEventModel() {
		return eventModel;
	}

	/**
	 * @param eventModel
	 *            the eventModel to set
	 */
	public void setEventModel(ScheduleModel eventModel) {
		this.eventModel = eventModel;
	}

	/**
	 * @return the event
	 */
	public ScheduleEvent getEvent() {
		return event;
	}

	/**
	 * @param event
	 *            the event to set
	 */
	public void setEvent(ScheduleEvent event) {
		this.event = event;
	}

	/**
	 * @return the id_recursotipo
	 */
	public Integer getId_recursotipo() {
		return id_recursotipo;
	}

	/**
	 * @param id_recursotipo
	 *            the id_recursotipo to set
	 */
	public void setId_recursotipo(Integer id_recursotipo) {
		this.id_recursotipo = id_recursotipo;
	}

	/**
	 * @return the select2
	 */
	public List<SelectItem> getSelect2() {
		return select2;
	}

	public void setManager(ManagerReservas manager) {
		this.manager = manager;
	}

	public Integer getId_recurso() {
		return id_recurso;
	}

	public void setId_recurso(Integer id_recurso) {
		this.id_recurso = id_recurso;
	}

	public Integer getcapacidad_recurso() {
		return capacidad_recurso;
	}

	public void setcapacidad_recurso(Integer capacidad_recurso) {
		this.capacidad_recurso = capacidad_recurso;
	}

	public Solicicabecera getSolicitudCabTem() {
		return solicitudCabTem;
	}

	public void setSolicitudCabTem(Solicicabecera solicitudCabTem) {
		this.solicitudCabTem = solicitudCabTem;
	}

	public boolean isSolicitudCabTmpGuardada() {
		return solicitudCabTmpGuardada;
	}

	public void setSolicitudCabTmpGuardada(boolean solicitudCabTmpGuardada) {
		this.solicitudCabTmpGuardada = solicitudCabTmpGuardada;
	}

	public List<Solicicabecera> getListadoSolCab() {
		listadoSolCab = manager.findAllSolicitudCabecera();
		return listadoSolCab;
	}

	public String getDireccion() {
		return direccion;
	}

	public void setDireccion(String direccion) {
		this.direccion = direccion;
	}

	public String getActividad() {
		return actividad;
	}

	public void setActividad(String actividad) {
		this.actividad = actividad;
	}

	public String getObjetivo() {
		return objetivo;
	}

	public void setObjetivo(String objetivo) {
		this.objetivo = objetivo;
	}

	public String getJustificacion() {
		return justificacion;
	}

	public void setJustificacion(String justificacion) {
		this.justificacion = justificacion;
	}

	public String getNotificacion() {
		return notificacion;
	}

	public void setNotificacion(String notificacion) {
		this.notificacion = notificacion;
	}

	/**
	 * @return the h_inicio
	 */
	public Timestamp getH_inicio() {
		return h_inicio;
	}

	/**
	 * @param h_inicio
	 *            the h_inicio to set
	 */
	public void setH_inicio(Timestamp h_inicio) {
		this.h_inicio = h_inicio;
	}

	/**
	 * @return the h_fin
	 */
	public Timestamp getH_fin() {
		return h_fin;
	}

	/**
	 * @param h_fin
	 *            the h_fin to set
	 */
	public void setH_fin(Timestamp h_fin) {
		this.h_fin = h_fin;
	}

	public List<SelectItem> getSelect() {
		return select;
	}

	public List<Solicidetalle> getListDetalles() {
		return listDetalles;
	}

	public void setListDetalles(List<Solicidetalle> listDetalles) {
		this.listDetalles = listDetalles;
	}

	public Soliciestado getEstadoSol() {
		return estadoSol;
	}

	public void setEstadoSol(Soliciestado estadoSol) {
		this.estadoSol = estadoSol;
	}

	public Integer getId_sol() {
		return id_sol;
	}

	public void setId_sol(Integer id_sol) {
		this.id_sol = id_sol;
	}

	public Date getRecursofecha() {
		return recursofecha;
	}

	public void setRecursofecha(Date recursofecha) {
		this.recursofecha = recursofecha;
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
	 * @return the mostrar
	 */
	public boolean isMostrar() {
		return mostrar;
	}

	/**
	 * @param mostrar
	 *            the mostrar to set
	 */
	public void setMostrar(boolean mostrar) {
		this.mostrar = mostrar;
	}

	/**
	 * @return the descripcionubicacion
	 */
	public String getDescripcionubicacion() {
		return descripcionubicacion;
	}

	/**
	 * @param descripcionubicacion
	 *            the descripcionubicacion to set
	 */
	public void setDescripcionubicacion(String descripcionubicacion) {
		this.descripcionubicacion = descripcionubicacion;
	}

	public String getStock() {
		return stock;
	}

	public void setStock(String stock) {
		this.stock = stock;
	}

	/**
	 * @return the imagen
	 */
	public String getImagen() {
		return imagen;
	}

	/**
	 * @param imagen
	 *            the imagen to set
	 */
	public void setImagen(String imagen) {
		this.imagen = imagen;
	}

	/* SESSION */
	public UsuarioHelp getSession() {
		return session;
	}

	// Metodos proceso de ejecucion
	public String crearNuevaSolicitud() {
		String resp = "";
		try {
			// SolicitudTemporal
			solicitudCabTem = manager.crearSolicitudTmp(getDireccion(),
					getActividad(), getObjetivo(), getJustificacion(),
					new Date(), getSession().getIdUsr());
			id_recurso = -1;
			capacidad_recurso = null;
			solicitudCabTmpGuardada = false;
			notificacion = "";
			resp = "";
		} catch (Exception e) {
			Mensaje.crearMensajeWARN("Error al crear la solicitud");
		}
		return resp;
	}

	public String insertarDetalleSolicitud() {
		if (solicitudCabTmpGuardada == true) {
			Mensaje.crearMensajeWARN("La solicitud fue guardada anteriormente");
			return "";
		} else if (fi.after(ff)) {
			Mensaje.crearMensajeWARN("La fecha inicio debe ser menor que la fecha fin");
		}
		try {// insertar
			controlarcantidad();
			if (agregardetalle == true) {
				manager.agregarSolicitudDetalleTmp(getId_recurso(),
						getcapacidad_recurso(), h_fin, h_inicio);
				id_recurso = -1;
				agregardetalle = false;
				capacidad_recurso = null;
			}
		} catch (Exception e) {
			e.printStackTrace();
			agregardetalle = false;
			id_recurso = -1;
			capacidad_recurso = null;
		}

		return "";
	}

	// CARGAR toods los recursos LIBRES
	public void controlarcantidad() {
		try {
			if (id_recurso == -1) {
				Mensaje.crearMensajeWARN("Debe seleccionar el recurso a solicitar");
				agregardetalle = false;
			} else {
				if (capacidad_recurso == null) {
					capacidad_recurso = -1;
				}
				if (manager.controlarcantidadmanager(getId_recurso(),
						getcapacidad_recurso(), h_fin, h_inicio) == true) {
					agregardetalle = true;
				} else if (manager.controlarcantidadmanager(getId_recurso(),
						getcapacidad_recurso(), h_fin, h_inicio) == false) {
					agregardetalle = false;
					Mensaje.crearMensajeWARN("Error debe especificar el recurso o la cantidad es mayor a la del recurso solicitado");
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public String insertarDetalleSolicitudlista() {
		if (solicitudCabTmpGuardada == true) {
			Mensaje.crearMensajeINFO("La solicitud fue guardada");
			return "";
		}
		try {// insertar
			List<Recurso> listadoRecurso = manager.findAllRecursosDisponibles(
					h_inicio, h_fin, horainicio, horafin);
			for (Recurso p : listadoRecurso) {
				if (id_recursotipo.equals(p.getRecursotipo().getIdRectipo())) {
					h_inicio = new Timestamp(fi.getTime());
					h_fin = new Timestamp(ff.getTime());
					manager.agregarSolicitudDetalleTmplista(p.getIdRecurso(),
							p.getCapacidad(), h_fin, h_inicio);
					System.out.println(p.getIdRecurso() + " CON NUM RECURSO "
							+ p.getRecursotipo().getIdRectipo());
				}
			}
			Mensaje.crearMensajeINFO("Los recursos se añadieron");
			id_recurso = -1;
			capacidad_recurso = null;
			select = new ArrayList<SelectItem>();
			select2 = new ArrayList<SelectItem>();
		} catch (Exception e) {
			e.printStackTrace();
			id_recurso = -1;
			capacidad_recurso = null;
			select = new ArrayList<SelectItem>();
			select2 = new ArrayList<SelectItem>();
		}
		return "";
	}

	// LISTADO DE getlistaTipoRecursosdeunrecursotipo
	public List<SelectItem> getlistaTipoRecursosdeunrecursotipo() {
		List<SelectItem> listadoSI = new ArrayList<SelectItem>();
		List<Recurso> listadoRecurso = manager.findAllRecursosDisponibles(
				h_inicio, h_fin, horainicio, horafin);
		for (Recurso p : listadoRecurso) {
			if (p.getRecursotipo().getIdRectipo().equals(id_recurso)) {
				SelectItem item = new SelectItem(p.getIdRecurso(),
						p.getNombre());
				listadoSI.add(item);
			}
		}
		return listadoSI;
	}

	public String quitarDetalleSolicitud(Solicidetalle det) {
		if (solicitudCabTmpGuardada == true) {
			Mensaje.crearMensajeINFO("La solicitud fue guardada");
			return "";
		}
		try {
			manager.quitarDetalleSolicitudTem(det);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return "";
	}

	public String guardarSolicitud() {
		if (solicitudCabTmpGuardada == true) {
			Mensaje.crearMensajeINFO("La solicitud fue guardada y enviada para aprobación");
			return "home";
		}
		try {
			manager.guardarSolicitudTemporalsinev(solicitudCabTem);
			DateFormat date = new SimpleDateFormat("dd/MM/yyyy");
			smscoradmin = "<!DOCTYPE html><html lang='es'><head><meta http-equiv='Content-Type' content='text/html; charset=utf-8' />"
					+ "<meta name='viewport' content='width=device-width'></head><body>"
					+ "El Sr/ra. "
					+ direccion
					+ " "
					+ justificacion
					+ ", envi&oacute; una solicitud para un recurso; Requiere la aprobaci&oacute;n o negaci&oacute;n.; <br/>"
					+ "los datos de la solicitud son:"
					+ "<br/> Actividad: "
					+ actividad
					+ ""
					+ "<br/> Objetivo: "
					+ objetivo
					+ ""
					+ "<br/> Fecha de Inicio: "
					+ date.format(fi).toString()
					+ ""
					+ "<br/> Fecha de Fin: "
					+ date.format(ff).toString()
					+ "<br/> Saludos cordiales, "
					+ "<br/> Sistema de REGECE Yachay EP"
					+ "<br/><em><strong>NOTA:</strong> Este correo es generado automáticamente por el sistema favor no responder al mismo.</em></body></html>";

			smscorusu = "<!DOCTYPE html><html lang='es'><head><meta http-equiv='Content-Type' content='text/html; charset=utf-8' />"
					+ "<meta name='viewport' content='width=device-width'></head><body>"
					+ "Sr/ra.  "
					+ direccion
					+ " "
					+ justificacion
					+ ", su petici&oacute;n de solicitud del recurso del sistema REGECE (Reservas de Espacios y Gesti&oacute;n de Eventos del Centro de Emprendimiento), ser&aacute; verificado por los administradores, espere al mensaje de confirmaci&oacute;n. <br/>"
					+ "los datos su solicitud son:"
					+ "<br/> Actividad: "
					+ actividad
					+ ""
					+ "<br/> Objetivo: "
					+ objetivo
					+ ""
					+ "<br/> Fecha de Inicio: "
					+ date.format(fi).toString()
					+ ""
					+ "<br/> Fecha de Fin: "
					+ date.format(ff).toString()
					+ "<br/> Saludos cordiales, "
					+ "<br/> Sistema de REGECE Yachay EP"
					+ "<br/><em><strong>NOTA:</strong> Este correo es generado automáticamente por el sistema favor no responder al mismo.</em></body></html>";
			getcorreosusu();
			mb.envioMailWS(correosadmin, "Notificación de YACHAY/REGECE",
					smscoradmin);
			mb.envioMailWS(session.getCorreo(),
					"Notificación de YACHAY/REGECE", smscorusu);
			solicitudCabTmpGuardada = true;
			correosadmin = "";
			smscoradmin = "";
			smscorusu = "";
			descripcionubicacion = "Descripción de la Ubicación";
			stock = "stock";
			imagen = "300.jpg";
			fi = null;
			ff = null;
			setActividad("");
			setObjetivo("");
			Mensaje.crearMensajeINFO("Su solicitud fue enviada, espere el mensaje de confirmación al correo.");
		} catch (Exception e) {
			Mensaje.crearMensajeWARN("Error en guardar solicitud");
			e.printStackTrace();
		}
		return "home";
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

	// CARGAR toods los recursos LIBRES
	public void todoslosrecursos() {
		try {
			manager.quitarrecursosactivos();
			cargarRecursos();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// CARGAR RECURSOS LIBRES
	public void cargarRecursos() {
		h_inicio = new Timestamp(fi.getTime());
		h_fin = new Timestamp(ff.getTime());
		select.clear();
		id_recurso = -1;
		select = new ArrayList<SelectItem>();
		select2 = new ArrayList<SelectItem>();
		if (h_fin == null || h_inicio == null) {
			Mensaje.crearMensajeWARN("Seleccione horario para continuar");
		} else {
			// Modificacion de Horas
			setHorainicio(this.fechaAtiempo(h_inicio));
			setHorafin(this.fechaAtiempo(h_fin));
			if (!Validacion.fechaMayorIgual(h_inicio)
					|| !Validacion.fechaMayorIgual(h_fin)) {
				Mensaje.crearMensajeWARN("La fecha de solicitud no debe ser menor a la actual");
			} else if (h_fin.getTime() <= h_inicio.getTime()) {
				Mensaje.crearMensajeWARN("Verifique su horario de solicitud");
			} else {
				select = this.getlistaRecursosLibres();
			}
		}
	}

	// LISTADO DE RECURSOS
	public List<SelectItem> getlistaRecursosLibres() {
		List<SelectItem> listadoSI = new ArrayList<SelectItem>();
		List<Recurso> listadoRecurso = manager.findAllRecursosDisponibles(
				h_inicio, h_fin, horainicio, horafin);
		for (Recurso p : listadoRecurso) {
			SelectItem item = new SelectItem(p.getIdRecurso(), p.getNombre());
			listadoSI.add(item);
		}
		return listadoSI;
	}

	public void selecionadorecuraso() {
		System.out.println(getId_recurso());

	}

	// JAVA.DATE TO SQL.TIME
	@SuppressWarnings("deprecation")
	public Time fechaAtiempo(Date fecha) {
		DateFormat dateFormatH = new SimpleDateFormat("HH:mm");
		String hora = dateFormatH.format(fecha).toString();
		String[] array = hora.split(":");
		Time resp = new Time(Integer.parseInt(array[0]),
				Integer.parseInt(array[1]), 00);
		return resp;
	}

	// -------traslados
	public String irsolres() {
		String r = "";
		if (solicitudCabTem == null) {
			Mensaje.crearMensajeWARN("Debe crear la solicitud indicando la actividad y objetivo");
		} else {
			if (solicitudCabTem.getSolicidetalles().size() > 0) {
				Mensaje.crearMensajeINFO("Continuar");
				r = "solres";
			} else {
				Mensaje.crearMensajeWARN("Debe seleccionar recursos");
			}
		}
		return r;
	}

	public String irsolcab() {
		return "solcab";
	}

	public String irsoldet() {
		solicitudCabTem.getSolicidetalles().clear();
		return "soldet";
	}

	public String irvolver() {
		String r = "eventos";
		setId_recurso(-1);
		setActividad("");
		setObjetivo("");
		setRecursofecha(null);
		setH_inicio(null);
		setH_fin(null);
		h_inicio = null;
		h_fin = null;
		fi = null;
		ff = null;
		descripcionubicacion = "Descripción de la Ubicación";
		stock = "stock";
		imagen = "300.jpg";
		select = new ArrayList<SelectItem>();
		select2 = new ArrayList<SelectItem>();
		solicitudCabTem = null;
		return r;
	}

	public void veri() {
		try {
			// SolicitudTemporal
			if (actividad.equals("") && objetivo.equals("")) {
				Mensaje.crearMensajeWARN("Error al crear la solicitud");
			} else {
				solicitudCabTem = manager.crearSolicitudTmp(getDireccion(),
						actividad, objetivo, getJustificacion(), new Date(),
						getSession().getIdUsr());
				solicitudCabTmpGuardada = false;
				id_recurso = -1;
				select = new ArrayList<SelectItem>();
				select2 = new ArrayList<SelectItem>();
				h_inicio = null;
				h_fin = null;
				Mensaje.crearMensajeINFO("Datos almacenados correctamente");
			}
		} catch (Exception e) {
			Mensaje.crearMensajeWARN("Error al crear la solicitud");
		}
	}

	public void veri1() {
		try {
			// SolicitudTemporal
			actividad = getActividad();
		} catch (Exception e) {
			Mensaje.crearMensajeWARN("Error al crear la solicitud");
		}
	}

	public void mostrara() {
		Recurso rec;
		try {
			rec = manager.findRecursoByID(id_recurso);
			descripcionubicacion = rec.getDescripcion();
			imagen = rec.getImagen();
			contador = manager.findContadorRecurso(h_inicio, h_fin,
					rec.getIdRecurso());
			stock = "En stock: " + contador;
			mostrar = true;
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// metodo para asignar el Tipo al Usuario
	public String asignarRecurso() {
		manager.asignarRecurso(id_recurso);
		return "";
	}
}