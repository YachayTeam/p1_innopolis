package innopolis.controller;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Time;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import innopolis.entidades.*;
import innopolis.entidades.help.UsuarioHelp;
import innopolis.manager.ManagerBuscar;
import innopolis.manager.ManagerReservas;
import innopolis.manager.Validacion;
import innopolis.model.generic.Mensaje;

import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;
import javax.servlet.ServletContext;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;

import org.primefaces.model.DefaultScheduleEvent;
import org.primefaces.model.ScheduleEvent;
import org.primefaces.model.ScheduleModel;

@SessionScoped
@ManagedBean
public class SolicitudApBean {

	private ManagerReservas manager;

	@EJB
	private ManagerBuscar mb;

	// Atributo de solicitud
	// Cabecera
	private String direccion;
	private String actividad;
	private String justificacion;
	private String objetivo;
	private String notificacion;
	private String sms;
	private Date fecha;
	// Manejo detalles
	private Date recursofecha;
	private Timestamp h_inicio;
	private Timestamp h_fin;
	private Timestamp fechaInicio;
	private Timestamp fechaFin;
	private List<SelectItem> select;
	// Cambios solicitud
	private List<Solicidetalle> listDetalles;
	private Soliciestado estadoSol;
	private Integer id_sol;
	// +/- Detalles
	private ArrayList<Solicidetalle> list_mas;
	private ArrayList<Solicidetalle> list_menos;

	// Detalles
	private Integer id_recurso;
	private Integer capacidad_recurso;
	private boolean solicitudCabTmpGuardada;
	private List<Solicicabecera> listadoSolCab;

	// correo del usuario
	private String correo;
	private Integer id_recursotipo;
	private List<SelectItem> select2;
	private UsuarioHelp session;

	private Date fi;
	private Date ff;

	private Time horafin;
	private Time horainicio;

	private boolean agregardetalle;
	private boolean agregarcontrol;
	private boolean controlsicabecera;

	// sacar la descripcion del tipo de ubicacion
	private boolean mostrar;
	private String descripcionubicacion;
	private String stock;
	int contador = 0;
	private String imagen;
	// calendario
	private Date date = new Date();
	private ScheduleModel eventModel;
	private ScheduleEvent event = new DefaultScheduleEvent();

	public SolicitudApBean() {
		session = SessionBean.verificarSession();
		manager = new ManagerReservas();
		// Select todos
		select = new ArrayList<SelectItem>();
		select2 = new ArrayList<SelectItem>();
		mostrar = false;
		agregardetalle = true;
		controlsicabecera = false;
		agregarcontrol = false;
		descripcionubicacion = "Descripci�n del Recurso";
		stock = "stock";
		contador = 0;
		imagen = "300.jpg";
		capacidad_recurso = null;
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

	public ManagerReservas getManager() {
		return manager;
	}

	public Timestamp getFechaInicio() {
		return fechaInicio;
	}

	public void setFechaInicio(Timestamp fechaInicio) {
		this.fechaInicio = fechaInicio;
	}

	public Timestamp getFechaFin() {
		return fechaFin;
	}

	public void setFechaFin(Timestamp fechaFin) {
		this.fechaFin = fechaFin;
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
	 * @return the correo
	 */
	public String getCorreo() {
		return correo;
	}

	/**
	 * @param correo
	 *            the correo to set
	 */
	public void setCorreo(String correo) {
		this.correo = correo;
	}

	/**
	 * @return the date
	 */
	public Date getDate() {
		date = new Date();
		return date;
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

	public String getSms() {
		return sms;
	}

	public void setSms(String sms) {
		this.sms = sms;
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

	public Integer getcapacidad_recurso() {
		return capacidad_recurso;
	}

	public void setcapacidad_recurso(Integer capacidad_recurso) {
		this.capacidad_recurso = capacidad_recurso;
	}

	/**
	 * @return the select2
	 */
	public List<SelectItem> getSelect2() {
		return select2;
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

	public boolean isSolicitudCabTmpGuardada() {
		return solicitudCabTmpGuardada;
	}

	public void setSolicitudCabTmpGuardada(boolean solicitudCabTmpGuardada) {
		this.solicitudCabTmpGuardada = solicitudCabTmpGuardada;
	}

	public List<Solicicabecera> getListadoSolCab() {
		listadoSolCab = manager.findAllSolicitudCabeceraOrdenada();
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

	public Date getFecha() {
		return fecha;
	}

	public void setFecha(Date fecha) {
		this.fecha = fecha;
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

	public String getStock() {
		return stock;
	}

	public void setStock(String stock) {
		this.stock = stock;
	}

	public boolean isControlsicabecera() {
		return controlsicabecera;
	}

	public void setControlsicabecera(boolean controlsicabecera) {
		this.controlsicabecera = controlsicabecera;
	}

	/* SESSION */
	public UsuarioHelp getSession() {
		return session;
	}

	// metodo lista de aprobados solicitudes con recursos por usuario
	public List<Solicicabecera> getListadoaprob() {
		List<Solicicabecera> a = manager.findAllSolicitudCabecera();
		List<Solicicabecera> l1 = new ArrayList<Solicicabecera>();
		try {
			for (Solicicabecera t : a) {
				if (t.getSoliciestado().getIdSolest().equals(3)) {
					l1.add(t);
				}
			}
		} catch (Exception e) {
			Mensaje.crearMensajeWARN("Error");
		}
		return l1;
	}

	// metodo lista de aprobados solicitudes con recursos por usuario
	public List<Solicicabecera> getListadoaprobxus() {
		List<Solicicabecera> a = manager.findAllSolicitudCabeceraOrdenada();
		List<Solicicabecera> l1 = new ArrayList<Solicicabecera>();
		try {
			for (Solicicabecera t : a) {
				if (t.getSoliciestado().getIdSolest().equals(3)
						&& t.getIdusr().equals(session.getIdUsr())) {
					l1.add(t);
				}
			}
		} catch (Exception e) {
			Mensaje.crearMensajeWARN("Error");
		}
		return l1;
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

	// metodo para asignar el RecursoTipo al Recurso
	public String asignarRecLibre() {
		manager.asignarRecurso(id_recurso);
		return "";
	}

	// metodo para asignar el Tipo al Usuario
	public String asignarRecurso() {
		manager.asignarRecurso(id_recurso);
		return "";
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

	// Tomar el id de estado general id_estadoSolicitud
	public String aprobarEstado(Solicicabecera solicitud) {
			try {
				manager.cambioSMS(solicitud.getIdSolcab());
				Soliciestado estado = manager.findSolicitudEstadoByID(3);// APROBADO
				manager.cambiarEstadoSolicitud(solicitud.getIdSolcab(), estado);
				Mensaje.crearMensajeINFO("Cambio correcto de estado");
			} catch (Exception e) {
				Mensaje.crearMensajeWARN("Error al cambiar el estado");
			}
		return "";
	}

	public String negarEstado(Solicicabecera solicitud) {
			try {
				manager.cambioSMS(solicitud.getIdSolcab());
				Soliciestado estado = manager.findSolicitudEstadoByID(4);// NEGADO
				manager.cambiarEstadoSolicitud(solicitud.getIdSolcab(), estado);
				Mensaje.crearMensajeINFO("Cambio correcto de estado");
			} catch (Exception e) {
				Mensaje.crearMensajeWARN("Error al cambiar el estado");
			}
		return "";
	}

	public String asignarsms(Solicicabecera solicitud) {
		id_sol = solicitud.getIdSolcab();
		direccion = solicitud.getDireccion();
		actividad = solicitud.getActividad();
		objetivo = solicitud.getObjetivo();
		justificacion = solicitud.getJustificacion();
		notificacion = solicitud.getSms();
		fecha = solicitud.getFecha();
		listDetalles = solicitud.getSolicidetalles();
		estadoSol = solicitud.getSoliciestado();
		correo = usuarioxid(solicitud.getIdusr()).getCorreo();
		DateFormat date = new SimpleDateFormat("dd/MM/yyyy");
		sms = "<!DOCTYPE html><html lang='es'><head><meta http-equiv='Content-Type' content='text/html; charset=utf-8' />"
				+ "<meta name='viewport' content='width=device-width'></head><body>"
				+ "Le informamos que la solicitud de la fecha: "
				+ date.format(solicitud.getFecha()).toString()
				+ " con la actividad: "
				+ solicitud.getActividad()
				+ " ,fue "
				+ solicitud.getSoliciestado().getEstado()
				+ "<br/> Saludos cordiales, "
				+ "<br/> Sistema de REGECE Yachay EP"
				+ "<br/><em><strong>NOTA:</strong> Este correo es generado autom�ticamente por el sistema favor no responder al mismo.</em></body></html>";

		return "";
	}

	// Si el estado es pendiente o finalizado no se puede notificar
	public String notificarSolicitud(Solicicabecera solicitud) {
		if (estadoSol.getEstado().equals("finalizado")
				|| estadoSol.getEstado().equals("pendiente")) {
			Mensaje.crearMensajeINFO("La solicitud debe estar aprobada o negada para enviar una notificaci�n");
		} else {
			try {
				if (notificacion.equals("notificada")) {
					Mensaje.crearMensajeINFO("Se ha enviado al correo la notificaci�n anteriormente");
				} else {
					manager.cambioSMSenvio(id_sol);
					mb.envioMailWS(correo,
							"Peticion de Solicitud YACHAY/REGECE", sms);
					manager.notificarSolicitud(id_sol);
					Mensaje.crearMensajeINFO("Notificaci�n correcta");
				}
			} catch (Exception e) {
				Mensaje.crearMensajeWARN("Error al enviar notificaci�n");
			}
		}
		return "";
	}

	// CargarSolicitud
	public String cargarDatosSolicitud(Solicicabecera solicitud) {
		String resp = "";
		try {
				id_sol = solicitud.getIdSolcab();
				List<Evento> listEvento = manager
						.findEventoByCabeceraId(id_sol);
				List<Solicidetalle> listDetalles1 = manager
						.findSolicitudDetalleByCabeceraId(id_sol);
				if (!listDetalles1.isEmpty()) {
					for (Solicidetalle sol : listDetalles1) {
						fi = sol.getHoraInicio();
						ff = sol.getHoraFin();
						break;
					}
					fechaInicio = new Timestamp(fi.getTime());
					fechaFin = new Timestamp(ff.getTime());
					direccion = solicitud.getDireccion();
					actividad = solicitud.getActividad();
					objetivo = solicitud.getObjetivo();
					justificacion = solicitud.getJustificacion();
					notificacion = solicitud.getSms();
					fecha = solicitud.getFecha();
					listDetalles = solicitud.getSolicidetalles();
					estadoSol = solicitud.getSoliciestado();
					capacidad_recurso = null;
					list_mas = new ArrayList<Solicidetalle>();
					agregardetalle = true;
					list_menos = new ArrayList<Solicidetalle>();
					select = new ArrayList<SelectItem>();
					cargarRecursos();
					if (listEvento.isEmpty()) {
						controlsicabecera = false;
					} else {
						controlsicabecera = true;
					}
					resp = "editsol?faces-redirect=true";
				} else {
					for (Solicidetalle sol : listDetalles1) {
						fi = sol.getHoraInicio();
						ff = sol.getHoraFin();
						break;
					}
					if (fi == null || ff == null) {
						controlsicabecera = false;
						Date d = new Date();
						fi = new Timestamp(d.getTime());
						ff = new Timestamp(d.getTime());
						fechaInicio = new Timestamp(fi.getTime());
						fechaFin = new Timestamp(ff.getTime());
						direccion = solicitud.getDireccion();
						actividad = solicitud.getActividad();
						objetivo = solicitud.getObjetivo();
						justificacion = solicitud.getJustificacion();
						notificacion = solicitud.getSms();
						fecha = solicitud.getFecha();
						listDetalles = new ArrayList<Solicidetalle>();
						estadoSol = solicitud.getSoliciestado();
						capacidad_recurso = null;
						list_mas = new ArrayList<Solicidetalle>();
						agregardetalle = true;
						list_menos = new ArrayList<Solicidetalle>();
						select = new ArrayList<SelectItem>();
						cargarRecursos();
						for (Evento evento : listEvento) {
							if (evento.getSolicicabecera().getIdSolcab() == null) {
								controlsicabecera = true;
							} else {
								controlsicabecera = false;
							}
						}
						resp = "editsol?faces-redirect=true";
					} else {
						controlsicabecera = true;
						fechaInicio = new Timestamp(fi.getTime());
						fechaFin = new Timestamp(ff.getTime());
						direccion = solicitud.getDireccion();
						actividad = solicitud.getActividad();
						objetivo = solicitud.getObjetivo();
						justificacion = solicitud.getJustificacion();
						notificacion = solicitud.getSms();
						fecha = solicitud.getFecha();
						listDetalles = solicitud.getSolicidetalles();
						estadoSol = solicitud.getSoliciestado();
						capacidad_recurso = null;
						list_mas = new ArrayList<Solicidetalle>();
						agregardetalle = true;
						list_menos = new ArrayList<Solicidetalle>();
						select = new ArrayList<SelectItem>();
						cargarRecursos();
						resp = "editsol?faces-redirect=true";
					}
				}
		} catch (Exception e) {
			Mensaje.crearMensajeWARN("Error al ver la solicitud");
			e.printStackTrace();
		}
		return resp;
	}

	public String cambioEnvioSms() {
		Mensaje.crearMensajeINFO("Notificaci�n Aceptada");
		return "";
	}

	// Agregar quitar detalle
	public void agregarDetalle() {
		try {
			if (id_recurso == null || id_recurso == -1)
				Mensaje.crearMensajeINFO("Seleccione recurso adicional");
			else if (esRecursoAnadido(id_recurso, h_inicio, h_fin))
				Mensaje.crearMensajeINFO("El recurso se encuentra agregado dentro del horario");
			else {
				Recurso rec = manager.findRecursoByID(getId_recurso());
				Solicidetalle det = new Solicidetalle();
				det.setSolicicabecera(manager
						.findSolicitudCabeceraById(getId_sol()));
				det.setCapacidad(getcapacidad_recurso());
				det.setFechadet(getRecursofecha());
				det.setHoraInicio(h_inicio);
				det.setHoraFin(h_fin);
				det.setRecurso(rec);
				manager.insertarSoliciDatalle(det);
				manager.insertarRecursoSolicitado(manager
						.findSolicitudCabeceraById(getId_sol()).getIdSolcab(),
						det.getHoraInicio(), det.getHoraFin(), det.getRecurso()
								.getIdRecurso(), det.getCapacidad());
				listDetalles.add(det);
				capacidad_recurso = null;
				select = new ArrayList<SelectItem>();
				select2 = new ArrayList<SelectItem>();
				cargarRecursos();
				agregardetalle = true;
				agregarcontrol = true;
			}

		} catch (Exception e) {
			Mensaje.crearMensajeWARN("No se pudo agregar el recurso");
			cargarRecursos();
			capacidad_recurso = null;
			agregardetalle = true;
			select = new ArrayList<SelectItem>();
			select2 = new ArrayList<SelectItem>();
		}
	}

	// CARGAR toods los recursos LIBRES
	public void controlarcantidad() {
		try {
			if (id_recurso == -1) {
				Mensaje.crearMensajeINFO("Debe seleccionar el recurso a solicitar");
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
					Mensaje.crearMensajeINFO("Error debe especificar el recurso o la cantidad es mayor a la del recurso solicitado");
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// Recurso ya A�adido
	public boolean esRecursoAnadido(Integer id_recurso, Timestamp horaInicio,
			Timestamp horaFin) {
		List<Solicidetalle> listado = listDetalles;
		boolean resp = false;
		for (Solicidetalle solicidetalle : listado) {
			if (solicidetalle.getRecurso().getIdRecurso().intValue() == id_recurso
					.intValue()
					&& (Validacion.horaEntreDos(solicidetalle.getHoraInicio(),
							horaInicio, horaFin) || Validacion.horaEntreDos(
							solicidetalle.getHoraFin(), horaInicio, horaFin))) {
				resp = true;
			}
		}
		return resp;
	}

	// Agregar quitar detalle
	public void agregarDetalleubicacion() {
		try {
			if (id_recursotipo == null || id_recursotipo == -1) {
				Mensaje.crearMensajeWARN("Seleccione la ubicaci�n");
			}else {
				List<Recurso> listadoRecurso = manager
						.findAllRecursosDisponibles(h_inicio, h_fin,
								horainicio, horafin);
				for (Recurso p : listadoRecurso) {
					if (id_recursotipo
							.equals(p.getRecursotipo().getIdRectipo())) {
						Recurso rec = manager
								.findRecursoByID(getId_recursotipo());
						Solicidetalle det = new Solicidetalle();
						det.setSolicicabecera(manager
								.findSolicitudCabeceraById(getId_sol()));
						det.setCapacidad(getcapacidad_recurso());
						det.setFechadet(getRecursofecha());
						det.setHoraInicio(h_inicio);
						det.setHoraFin(h_fin);
						det.setRecurso(rec);
						listDetalles.add(det);
						list_mas.add(det);
						System.out.println(p.getIdRecurso()
								+ " CON NUM RECURSO "
								+ p.getRecursotipo().getIdRectipo());
						select = new ArrayList<SelectItem>();
						select2 = new ArrayList<SelectItem>();
					}
				}
			}
		} catch (Exception e) {
			Mensaje.crearMensajeWARN("No se pudo agregar el recurso");
			e.printStackTrace();
			capacidad_recurso = null;
			select = new ArrayList<SelectItem>();
			select2 = new ArrayList<SelectItem>();
		}
	}

	public void quitarDetalle(Solicidetalle detalle) {
		try {
			List<Recursosactivo> listrecact = manager
					.findRecursosactivoByRecursoId(detalle.getSolicicabecera()
							.getIdSolcab(), detalle.getRecurso().getIdRecurso());
			for (Recursosactivo recursosactivo : listrecact) {
				manager.eliminarRecursoActivos(recursosactivo.getIdRecact());
			}
			manager.eliminarSoliciDetalleByID(detalle.getIdSoldet());
			listDetalles.remove(detalle);
			cargarRecursos();
			Mensaje.crearMensajeINFO("Se elimin� el recurso");
			agregarcontrol = true;
		} catch (Exception e) {
			Mensaje.crearMensajeINFO("No se pudo quitar el recurso");
			e.printStackTrace();
		}
	}

	// guardar detalles
	public String finalizarSolicitudED() {
		String resp = "";
		try {
			if (!listDetalles.isEmpty()) {
				id_sol = null;
				descripcionubicacion = "Descripci�n de Recurso";
				direccion = "";
				stock = "stock";
				actividad = "";
				objetivo = "";
				justificacion = "";
				fechaFin = null;
				fechaInicio = null;
				notificacion = "";
				fecha = null;
				listDetalles = new ArrayList<Solicidetalle>();
				estadoSol = null;
				select = new ArrayList<SelectItem>();
				select2 = new ArrayList<SelectItem>();
				capacidad_recurso = null;
				list_mas = new ArrayList<Solicidetalle>();
				agregarcontrol = false;
				resp = "solicitudes?faces-redirect=true";
				Mensaje.crearMensajeINFO("Edici�n correcta");
			} else {
				FacesContext
						.getCurrentInstance()
						.addMessage(
								null,
								new FacesMessage(
										FacesMessage.SEVERITY_INFO,
										"La solicitud debe contener por lo menos un recurso",
										null));
			}
		} catch (Exception e) {
			FacesContext.getCurrentInstance().addMessage(
					null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR,
							"Edici�n err�nea", null));
		}
		return resp;
	}

	public String cancelarSolicitudED() {
		String r = "";
		if (agregarcontrol == false) {
			id_sol = null;
			direccion = "";
			stock = "stock";
			actividad = "";
			objetivo = "";
			justificacion = "";
			fechaFin = null;
			fechaInicio = null;
			notificacion = "";
			fecha = null;
			listDetalles = new ArrayList<Solicidetalle>();
			estadoSol = null;
			select = new ArrayList<SelectItem>();
			select2 = new ArrayList<SelectItem>();
			controlsicabecera = false;
			// Cargar datos recurso

			capacidad_recurso = null;
			// Listas
			list_mas = new ArrayList<Solicidetalle>();
			r = "solicitudes?faces-redirect=true";
		} else {
			FacesContext.getCurrentInstance().addMessage(
					null,
					new FacesMessage(FacesMessage.SEVERITY_WARN,
							"Debe dar click en actualizar", null));
		}
		return r;
	}

	public String irform() {

		return "frm_insedit?faces-redirect=true";
	}

	public Usuario usuarioxid(Integer id) {
		Usuario k = null;
		List<Usuario> u = manager.findAllUsuarios();
		for (Usuario usuario : u) {
			if (usuario.getIdUsr() == id) {
				k = usuario;
			}
		}
		return k;
	}

	public String cancelarenv() {
		id_sol = null;
		direccion = "";
		actividad = "";
		objetivo = "";
		justificacion = "";
		notificacion = "";
		fecha = null;
		fechaFin = null;
		fechaInicio = null;
		listDetalles = new ArrayList<Solicidetalle>();
		estadoSol = null;
		// Cargar datos recurso
		capacidad_recurso = null;
		// Listas
		list_mas = new ArrayList<Solicidetalle>();
		return "solicitudes?faces-redirect=true";
	}

	// CARGAR toods los recursos LIBRES
	public void todoslosrecursos() {
		try {
			manager.quitarrecursosactivos();
			cargarRecursos();
		} catch (Exception e) {
			// TODO Auto-generated catch block
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
			FacesContext.getCurrentInstance().addMessage(
					null,
					new FacesMessage(FacesMessage.SEVERITY_WARN,
							"Seleccione horario para continuar", null));
		} else {
			// Modificacion de Horas
			setHorainicio(this.fechaAtiempo(h_inicio));
			setHorafin(this.fechaAtiempo(h_fin));
			if (!Validacion.fechaMayorIgual(h_inicio)
					|| !Validacion.fechaMayorIgual(h_fin)) {
				FacesContext
						.getCurrentInstance()
						.addMessage(
								null,
								new FacesMessage(
										FacesMessage.SEVERITY_WARN,
										"La fecha de solicitud no debe ser menor a la actual",
										null));
			} else if (h_fin.getTime() <= h_inicio.getTime()) {
				FacesContext.getCurrentInstance().addMessage(
						null,
						new FacesMessage(FacesMessage.SEVERITY_WARN,
								"Verifique su horario de solicitud", null));
				// } else if ((!Validacion.horaMayorIgual(getHorainicio()) ||
				// !Validacion
				// .horaMayorIgual(getHorafin()))) {
				// FacesContext
				// .getCurrentInstance()
				// .addMessage(
				// null,
				// new FacesMessage(
				// FacesMessage.SEVERITY_WARN,
				// "La hora de solicitud no debe ser menor a la actual",
				// null));
			} else {
				select = this.getlistaRecursosLibres();
				// select2 = this.getlistaTipoRecursosLibres();
			}
		}
	}

	// //LISTADO DE RECURStipo
	// public List<SelectItem> getlistaTipoRecursosLibres(){
	// List<SelectItem> listadoSI=new ArrayList<SelectItem>();
	// //borre los recursos anteriores de la tabla recursos activos
	// List<Recursotipo> listadoTipoRecurso=
	// manager.findAllTipoRecursosDisponibles(h_inicio, h_fin);
	// for(Recursotipo p:listadoTipoRecurso){
	// SelectItem item=new SelectItem(p.getIdRectipo(),
	// p.getTipo(),p.getDescripcion());
	// listadoSI.add(item);
	// }
	// return listadoSI;
	// }

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
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * Imprime un reporte de los datos de un contrato
	 */
	public void imprimirRptDocumento(Solicicabecera sol) {

		if (!sol.getSoliciestado().getEstado().equals("pendiente")) {
			try {
				ServletContext servletContext = (ServletContext) FacesContext
						.getCurrentInstance().getExternalContext().getContext();

				String carpetaReportes = (String) servletContext
						.getRealPath(File.separatorChar + "reports");
				String rutaReporte = carpetaReportes + File.separatorChar
						+ "imrpimirsolicitud.jasper";
				// rutaReporte=
				// "reports"+File.separatorChar+"rptContratoBicicletas.jasper";

				Connection conexion = DriverManager
						.getConnection("jdbc:postgresql://10.1.0.158:5432/bd_inno?user=adm_bicichay&password=y-4IO4SDwu_!");

				Map<String, Object> parametros = new HashMap<String, Object>();
				System.out.println(carpetaReportes + File.separatorChar
						+ "yachay-logo1.png");
				System.out.println(sol.getIdSolcab());
				parametros.put("pIdsolcab", sol.getIdSolcab());
				parametros.put("pImagen", carpetaReportes + File.separatorChar
						+ "yachay-logo1.png");
				// parametros.put("SUBREPORT_DIR",
				// carpetaReportes+File.separatorChar+"");

				JasperPrint informe = JasperFillManager.fillReport(rutaReporte,
						parametros, conexion);
				HttpServletResponse response = (HttpServletResponse) FacesContext
						.getCurrentInstance().getExternalContext()
						.getResponse();
				response.addHeader("Content-disposition",
						"attachment; filename=jsfReporte.pdf");
				ServletOutputStream stream = response.getOutputStream();
				JasperExportManager.exportReportToPdfStream(informe, stream);
				stream.flush();
				stream.close();
				FacesContext.getCurrentInstance().responseComplete();
			} catch (Exception e) {
				FacesContext.getCurrentInstance().addMessage(
						null,
						new FacesMessage(FacesMessage.SEVERITY_WARN,
								"Error al crear reporte", null));
				e.printStackTrace();
			}
		} else {
			FacesContext.getCurrentInstance().addMessage(
					null,
					new FacesMessage(FacesMessage.SEVERITY_WARN,
							"A�n no se aprueba o niega la solicitud", null));
		}
	}

}
