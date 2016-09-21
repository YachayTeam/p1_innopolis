package innopolis.controller;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
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

import innopolis.entidades.DatosRecursos;
import innopolis.entidades.Evento;
import innopolis.entidades.Recurso;
import innopolis.entidades.Recursosactivo;
import innopolis.entidades.Sala;
import innopolis.entidades.Solicicabecera;
import innopolis.entidades.Solicidetalle;
import innopolis.entidades.Soliciestado;
import innopolis.entidades.Tipoevento;
import innopolis.entidades.Usuario;
import innopolis.entidades.help.UsuarioHelp;
import innopolis.manager.ManagerBuscar;
import innopolis.manager.ManagerEvento;
import innopolis.manager.ManagerLogin;
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

import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.UploadedFile;

import innopolis.manager.ManagerCarga;

@SessionScoped
@ManagedBean
public class EventosBean {
	// Evento
	private ManagerEvento mEvento;
	private ManagerReservas mReserv;
	private ManagerLogin manager;

	public Integer eventoidedicio;
	private Integer idEvento;
	private float costo;
	private String descripcion;
	private Timestamp fechaInicio;
	private Timestamp fechaFin;
	private String imagen;
	private String imgMost;
	private String lugar;
	private String nombre;
	private String cantidad;
	private Integer sc;
	private Integer te;
	private String g;
	private String estadoeven;
	private Integer idusr;
	private String sms;
	private Integer sala;
	private Boolean interno;

	int contador;
	private boolean agregarcontrol;

	/**** Mod Eventos *****/
	private Timestamp fActualInicio, fActualFin;
	private Evento modEv;
	private Integer idEvSol;
	private List<Solicidetalle> listDetSolEv;

	@EJB
	private ManagerBuscar mb;
	
	private ManagerCarga mc;

	// solicitud
	private List<SelectItem> select;

	private UploadedFile file;

	// calendario
	private Date date;
	private Date fi;
	private Date ff;

	// temporales
	private Evento eventotemp;
	private Tipoevento tipoevento;
	private Boolean esave;
	private boolean eTem;

	// tiporecurso
	private Integer id_recursotipo;
	private List<SelectItem> select2;

	// envio de mensaje solicitudrecurso
	private String smscor;
	private String correosadmin;

	private boolean agregardetalle;

	// envio de correo a los administradores solicitudevento
	private String smscoradminsoleve;
	private String smscorususoleve;
	private String correosadminsoleve;

	// envio de correo a los administradores solicitudevento
	private String smscoradminsolreceve;
	private String smscorususolreceve;
	private String correosadminsolreceve;

	// sacar la descripcion del tipo de ubicacion
	private boolean mostrar;
	private String descripcionubicacion;
	private String stock;
	private String imagentipo;

	private String descripcionrecurso;

	// +/- Detalles
	private ArrayList<Solicidetalle> list_mas;
	private ArrayList<Solicidetalle> list_menos;

	// imgen de salas
	private String imagensala;
	private String capacidad;

	// ///////////////////////////////////////////////////////////////////////////////////solicitud
	// //////////////////////////////////////////////////////////

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
	// Cambios solicitud
	private List<Solicidetalle> listDetalles;
	private Soliciestado estadoSol;
	private Integer id_sol;

	// Detalles
	private Integer id_recurso;
	private Integer capacidad_recurso;
	private Solicicabecera solicitudCabTem;
	private boolean solicitudCabTmpGuardada;
	private List<Solicicabecera> listadoSolCab;

	private String nombreusuario;
	private String apellidousuario;
	private UsuarioHelp session;

	private Time horafin;
	private Time horainicio;
	// variable para el control de vistas soldet
	public boolean solivalor;

	private boolean editarEventoSS;// editar el evento sin solicitud

	public EventosBean() {
		mc = new ManagerCarga();
		manager = new ManagerLogin();
		session = SessionBean.verificarSession();
		mEvento = new ManagerEvento();
		mReserv = new ManagerReservas();
		select = new ArrayList<SelectItem>();
		h_inicio = new Timestamp(new Date().getTime());
		h_fin = new Timestamp(new Date().getTime());
		esave = false;
		editarEventoSS = false;
		agregardetalle = true;
		imagen = "300.jpg";
		agregarcontrol = false;
		imagensala = "300.jpg";
		imgMost = "300.jpg";
		contador = 0;
		imagentipo = "300.jpg";
		direccion = session.getNombre();
		justificacion = session.getApellido();
		nombreusuario = "";
		apellidousuario = "";
		mostrar = false;
		interno = false;
		idusr = session.getIdUsr();
		descripcionubicacion = "Descripción de la Sala";
		descripcionrecurso = "Descripción de Recurso";
		stock = "stock";
		capacidad = "capacidad";
	}

	public void setDescripcionrecurso(String descripcionrecurso) {
		this.descripcionrecurso = descripcionrecurso;
	}

	public String getDescripcionrecurso() {
		return descripcionrecurso;
	}

	public String getStock() {
		return stock;
	}

	public void setStock(String stock) {
		this.stock = stock;
	}

	public String getCapacidad() {
		return capacidad;
	}

	public void setCapacidad(String capacidad) {
		this.capacidad = capacidad;
	}

	public Boolean getInterno() {
		return interno;
	}

	public void setInterno(Boolean interno) {
		this.interno = interno;
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

	public boolean isEditarEventoSS() {
		return editarEventoSS;
	}

	public void setEditarEventoSS(boolean editarEventoSS) {
		this.editarEventoSS = editarEventoSS;
	}

	/**
	 * @param agregardetalle
	 *            the agregardetalle to set
	 */
	public void setAgregardetalle(boolean agregardetalle) {
		this.agregardetalle = agregardetalle;
	}

	public Date getDate() {
		date = new Date();
		return date;
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

	public void setDate(Date date) {
		this.date = date;
	}

	public Boolean isEsave() {
		return esave;
	}

	public void setEsave(Boolean esave) {
		this.esave = esave;
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

	public UploadedFile getFile() {
		return file;
	}

	public String getCantidad() {
		return cantidad;
	}

	public void setCantidad(String cantidad) {
		this.cantidad = cantidad;
	}

	public void setFile(UploadedFile file) {
		this.file = file;
	}

	public boolean iseTem() {
		return eTem;
	}

	public void seteTem(boolean eTem) {
		this.eTem = eTem;
	}

	public Evento getEventotemp() {
		return eventotemp;
	}

	public void setEventotemp(Evento eventotemp) {
		this.eventotemp = eventotemp;
	}

	public Integer getIdEvento() {
		return idEvento;
	}

	public void setIdEvento(Integer idEvento) {
		this.idEvento = idEvento;
	}

	public float getCosto() {
		return costo;
	}

	public void setCosto(float costo) {
		this.costo = costo;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public String getImagen() {
		return imagen;
	}

	public void setImagen(String imagen) {
		this.imagen = imagen;
	}

	/**
	 * @return the imagensala
	 */
	public String getImagensala() {
		return imagensala;
	}

	/**
	 * @param imagensala
	 *            the imagensala to set
	 */
	public void setImagensala(String imagensala) {
		this.imagensala = imagensala;
	}

	public String getLugar() {
		return lugar;
	}

	public void setLugar(String lugar) {
		this.lugar = lugar;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public Integer getSc() {
		return sc;
	}

	public void setSc(Integer sc) {
		this.sc = sc;
	}

	public Integer getTe() {
		return te;
	}

	public void setTe(Integer te) {
		this.te = te;
	}

	/**
	 * @return the sala
	 */
	public Integer getSala() {
		return sala;
	}

	/**
	 * @param sala
	 *            the sala to set
	 */
	public void setSala(Integer sala) {
		this.sala = sala;
	}

	/**
	 * @return the nombreusuario
	 */
	public String getNombreusuario() {
		return nombreusuario;
	}

	/**
	 * @param nombreusuario
	 *            the nombreusuario to set
	 */
	public void setNombreusuario(String nombreusuario) {
		this.nombreusuario = nombreusuario;
	}

	/**
	 * @return the apellidousuario
	 */
	public String getApellidousuario() {
		return apellidousuario;
	}

	/**
	 * @param apellidousuario
	 *            the apellidousuario to set
	 */
	public void setApellidousuario(String apellidousuario) {
		this.apellidousuario = apellidousuario;
	}

	/**
	 * @return the estadoeven
	 */
	public String getEstadoeven() {
		return estadoeven;
	}

	/**
	 * @param estadoeven
	 *            the estadoeven to set
	 */
	public void setEstadoeven(String estadoeven) {
		this.estadoeven = estadoeven;
	}

	/**
	 * @return the select2
	 */
	public List<SelectItem> getSelect2() {
		return select2;
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

	public List<Evento> getListEvento() {
		return mEvento.findAllEventos();
	}

	public Tipoevento getTipoevento() {
		return tipoevento;
	}

	public void setTipoevento(Tipoevento tipoevento) {
		this.tipoevento = tipoevento;
	}

	public String getImgMost() {
		return imgMost;
	}

	public void setImgMost(String imgMost) {
		this.imgMost = imgMost;
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

	/****** MOD EVENTOS *****/
	public Timestamp getfActualInicio() {
		return fActualInicio;
	}

	public void setfActualInicio(Timestamp fActualInicio) {
		this.fActualInicio = fActualInicio;
	}

	public Timestamp getfActualFin() {
		return fActualFin;
	}

	public void setfActualFin(Timestamp fActualFin) {
		this.fActualFin = fActualFin;
	}

	public Evento getModEv() {
		return modEv;
	}

	public void setModEv(Evento modEv) {
		this.modEv = modEv;
	}

	public Integer getIdEvSol() {
		return idEvSol;
	}

	public void setIdEvSol(Integer idEvSol) {
		this.idEvSol = idEvSol;
	}

	public List<Solicidetalle> getListDetSolEv() {
		return listDetSolEv;
	}

	public void setListDetSolEv(List<Solicidetalle> listDetSolEv) {
		this.listDetSolEv = listDetSolEv;
	}

	/* Atributos de Acceso */

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
	 * @return the imagentipo
	 */
	public String getImagentipo() {
		return imagentipo;
	}

	/**
	 * @param imagentipo
	 *            the imagentipo to set
	 */
	public void setImagentipo(String imagentipo) {
		this.imagentipo = imagentipo;
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
		listadoSolCab = mReserv.findAllSolicitudCabecera();
		return listadoSolCab;
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
	 * @return the smscoradminsolreceve
	 */
	public String getSmscoradminsolreceve() {
		return smscoradminsolreceve;
	}

	/**
	 * @param smscoradminsolreceve
	 *            the smscoradminsolreceve to set
	 */
	public void setSmscoradminsolreceve(String smscoradminsolreceve) {
		this.smscoradminsolreceve = smscoradminsolreceve;
	}

	/**
	 * @return the smscorususolreceve
	 */
	public String getSmscorususolreceve() {
		return smscorususolreceve;
	}

	/**
	 * @param smscorususolreceve
	 *            the smscorususolreceve to set
	 */
	public void setSmscorususolreceve(String smscorususolreceve) {
		this.smscorususolreceve = smscorususolreceve;
	}

	/**
	 * @return the correosadminsolreceve
	 */
	public String getCorreosadminsolreceve() {
		return correosadminsolreceve;
	}

	/**
	 * @param correosadminsolreceve
	 *            the correosadminsolreceve to set
	 */
	public void setCorreosadminsolreceve(String correosadminsolreceve) {
		this.correosadminsolreceve = correosadminsolreceve;
	}

	public String getActividad() {
		return actividad;
	}

	public void setActividad(String actividad) {
		this.actividad = actividad;
	}

	/**
	 * @return the direccion
	 */
	public String getDireccion() {
		return direccion;
	}

	/**
	 * @param direccion
	 *            the direccion to set
	 */
	public void setDireccion(String direccion) {
		this.direccion = direccion;
	}

	/**
	 * @return the justificacion
	 */
	public String getJustificacion() {
		return justificacion;
	}

	/**
	 * @param justificacion
	 *            the justificacion to set
	 */
	public void setJustificacion(String justificacion) {
		this.justificacion = justificacion;
	}

	/**
	 * @return the objetivo
	 */
	public String getObjetivo() {
		return objetivo;
	}

	/**
	 * @param objetivo
	 *            the objetivo to set
	 */
	public void setObjetivo(String objetivo) {
		this.objetivo = objetivo;
	}

	public String getNotificacion() {
		return notificacion;
	}

	public void setNotificacion(String notificacion) {
		this.notificacion = notificacion;
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
	 * @return the smscoradminsoleve
	 */
	public String getSmscoradminsoleve() {
		return smscoradminsoleve;
	}

	/**
	 * @param smscoradminsoleve
	 *            the smscoradminsoleve to set
	 */
	public void setSmscoradminsoleve(String smscoradminsoleve) {
		this.smscoradminsoleve = smscoradminsoleve;
	}

	/**
	 * @return the smscorususoleve
	 */
	public String getSmscorususoleve() {
		return smscorususoleve;
	}

	/**
	 * @param smscorususoleve
	 *            the smscorususoleve to set
	 */
	public void setSmscorususoleve(String smscorususoleve) {
		this.smscorususoleve = smscorususoleve;
	}

	/**
	 * @return the correosadminsoleve
	 */
	public String getCorreosadminsoleve() {
		return correosadminsoleve;
	}

	/**
	 * @param correosadminsoleve
	 *            the correosadminsoleve to set
	 */
	public void setCorreosadminsoleve(String correosadminsoleve) {
		this.correosadminsoleve = correosadminsoleve;
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

	/* SESSION */
	public UsuarioHelp getSession() {
		return session;
	}

	// EVENTOS
	// accion para invocar el manager y crear evento
	public String crearEvento() {
		try {
			estadoeven = "Pendiente";
			if (te == -1 || te == null || te == 0) {
				Mensaje.crearMensajeWARN("Debe seleccionar un tipo de evento");
			} else if (fi.after(ff)) {
				Mensaje.crearMensajeWARN("La fecha inicio debe ser menor que la fecha Fin");
			} else {

				fechaInicio = new Timestamp(fi.getTime());
				fechaFin = new Timestamp(ff.getTime());
				mEvento.asignarUsuario(session.getIdUsr());
				mEvento.insertarEvento(nombre.trim(), descripcion.trim(),
						imagen, fechaInicio, fechaFin, costo,
						Integer.parseInt(cantidad), estadoeven);

				DateFormat date = new SimpleDateFormat("dd/MM/yyyy");
				smscoradminsoleve = "<!DOCTYPE html><html lang='es'><head><meta http-equiv='Content-Type' content='text/html; charset=utf-8' />"
						+ "<meta name='viewport' content='width=device-width'></head><body>"
						+ "El Sr/ra. "
						+ session.getNombre()
						+ " "
						+ session.getApellido()
						+ ", envi&oacute; una solicitud para un evento; Requiere la aprobaci&oacute;n o negaci&oacute;n.; <br/>"
						+ "los datos de la solicitud del evento son:"
						+ "<br/> Nombre: "
						+ nombre
						+ ""
						+ "<br/> Descripci&oacute;n: "
						+ descripcion
						+ ""
						// + "\n Lugar: "+lugar+""
						+ "<br/> Costo: "
						+ costo
						+ ""
						+ "<br/> Cantidad: "
						+ cantidad
						+ ""
						+ "<br/> Fecha de Inicio: "
						+ date.format(fi).toString()
						+ ""
						+ "<br/> Fecha de Fin: "
						+ date.format(ff).toString()
						+ "<br/> Saludos cordiales, "
						+ "<br/> Sistema de REGECE Yachay EP"
						+ "<br/><em><strong>NOTA:</strong> Este correo es generado automáticamente por el sistema favor no responder al mismo.</em></body></html>";

				smscorususoleve = "<!DOCTYPE html><html lang='es'><head><meta http-equiv='Content-Type' content='text/html; charset=utf-8' />"
						+ "<meta name='viewport' content='width=device-width'></head><body>"
						+ "Sr/ra.  "
						+ session.getNombre()
						+ " "
						+ session.getApellido()
						+ ", su petici&oacute;n de solicitud del evento al sistema REGECE (Reservas de Espacios y Gesti&oacute;n de Eventos del Centro de Emprendimiento), ser&aacute; verificado por los administradores, espere al mensaje de confirmaci&oacute;n. <br/>"
						+ "Sus datos de la solicitud del evento son:"
						+ "<br/> Nombre: "
						+ nombre
						+ ""
						+ "<br/> Descripci&oacute;n: "
						+ descripcion
						+ ""
						+ "<br/> Costo: "
						+ costo
						+ ""
						+ "<br/> Cantidad: "
						+ cantidad
						+ ""
						+ "<br/> Fecha de Inicio: "
						+ date.format(fi).toString()
						+ ""
						+ "<br/> Fecha de Fin: "
						+ date.format(ff).toString()
						+ "<br/> Saludos cordiales, "
						+ "<br/> Sistema de REGECE Yachay EP"
						+ "<br/><em><strong>NOTA:</strong> Este correo es generado automáticamente por el sistema favor no responder al mismo.</em></body></html>";

				getcorreosusub();
				mb.envioMailWS(correosadminsoleve,
						"Notificación de YACHAY/REGECE", smscoradminsoleve);
				mb.envioMailWS(session.getCorreo(),
						"Notificación de YACHAY/REGECE", smscorususoleve);

				eventoidedicio = null;
				correosadminsoleve = "";
				smscoradminsoleve = "";
				smscorususoleve = "";
				descripcionubicacion = "Descripción de la Sala";
				stock = "stock";
				capacidad = "capacidad";
				descripcionrecurso = "Descripción de Recurso";
				imagensala = "300.jpg";
				// reiniciamos datos (limpiamos el formulario)
				nombre = "";
				descripcion = "";
				lugar = "";
				imagen = "300.jpg";
				imagensala = "300.jpg";
				fi = null;
				ff = null;
				fechaInicio = null;
				fechaFin = null;
				costo = 0;
				cantidad = "";
				sc = 0;
				te = 0;
				sala = 0;
				idusr = 0;
				esave = false;
				Mensaje.crearMensajeINFO("Registrado el evento fue creado");
			}
		} catch (Exception e) {
			Mensaje.crearMensajeWARN("El evento no pudo ser creado");
			e.printStackTrace();
		}
		return "";
	}

	// metodo para listar correos de ususariosadmin
	public String getcorreosusub() {
		try {
			List<Usuario> a = manager.findUsrsPrincipal();
			correosadminsoleve = "";
			for (Usuario u : a) {
				correosadminsoleve += u.getCorreo() + ",";
			}
			int max = correosadminsoleve.length();
			correosadminsoleve = correosadminsoleve.substring(0, max - 1)
					.trim();
		} catch (Exception e) {
			Mensaje.crearMensajeWARN("No se encuentran usuarios administradores");
			e.printStackTrace();
		}
		return correosadminsoleve;
	}

	// metodo para poner el nombre a la imagen
	public void asignarNombreImagen() {
		if (getNombre().trim().isEmpty()) {
			System.out.println("Vacio");
		} else {
			DateFormat dateFormat = new SimpleDateFormat("_ddMMyyyyHHmm");
			g = "img_" + getNombre() + dateFormat.format(new Date()) + ".jpg";
			System.out.println(g);
		}

	}

	// metodo para poner el nombre a la imagen
	public void nombreImagen(String n) {
		List<Evento> li = mEvento.findAllEventos();
		for (Evento e : li) {
			if (e.getImagen().contains(n)) {
				g = e.getImagen();
			}
		}
		System.out.println(g);
	}

	// metodo para ir a solicitud y guardar el evento en un temporal
	public String irSolicitud(Evento ev) {
		System.out.println(ev.getEstado());
		if (esave == true) {
			Mensaje.crearMensajeWARN("El evento cuenta con una solicitud");
			return "";
		} else if (!Validacion.fechaMayorIgual(ev.getFechaFin())) {
			Mensaje.crearMensajeWARN("La fecha de solicitud no debe ser menor a la actual");
			return "";
		}
		try {
			date = new Date();
			idEvento = ev.getIdEvento();
			nombre = ev.getNombre();
			descripcion = ev.getDescripcion();
			// lugar = ev.getLugar();
			costo = ev.getCosto();
			fi = ev.getFechaInicio();
			ff = ev.getFechaFin();
			estadoeven = ev.getEstado();
			/***** MOD FECHAS ***/
			fActualInicio = ev.getFechaInicio();
			fActualFin = ev.getFechaFin();
			modEv = ev;
			te = ev.getTipoevento().getIdTipoEvento();
			idusr = ev.getUsuario().getIdUsr();
			cantidad = ev.getCantidad().toString();
			imagen = ev.getImagen();
			sala = ev.getSala().getIdSala();
			fechaInicio = new Timestamp(ev.getFechaInicio().getTime());
			fechaFin = new Timestamp(ev.getFechaFin().getTime());
			DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
			String fi1 = new String(dateFormat.format(fechaInicio).toString());
			String ff1 = new String(dateFormat.format(fechaFin).toString());
			setActividad(nombre + ", " + fi1 + " - " + ff1);
			setObjetivo(ev.getDescripcion());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "soldet2?faces-redirect=true";
	}

	// editar imagen
	public void verImagen(Evento eve) {
		setImagen(eve.getImagen());
	}

	// metodo para ir a solicitud y guardar el evento en un temporal
	public String irSolicitud1(Evento ev) {
		solivalor = false;
		String a = "";
		System.out.println(ev.getEstado());
		if (esave == true) {
			Mensaje.crearMensajeWARN("El evento cuenta con una solicitud");
			return "";
		}
		try {
			Solicicabecera solicitud = mReserv.findSolicitudByID(ev
					.getSolicicabecera().getIdSolcab());
			id_sol = solicitud.getIdSolcab();
			List<Solicidetalle> listDetalles1 = mReserv
					.findSolicitudDetalleByCabeceraId(id_sol);
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
			fechaInicio = ev.getFechaInicio();
			fechaFin = ev.getFechaInicio();
			justificacion = solicitud.getJustificacion();
			notificacion = solicitud.getSms();
			listDetalles = solicitud.getSolicidetalles();
			estadoSol = solicitud.getSoliciestado();
			setH_inicio(ev.getFechaInicio());
			setH_fin(ev.getFechaFin());
			// Cargar datos recurso
			capacidad_recurso = null;
			list_mas = new ArrayList<Solicidetalle>();
			agregardetalle = true;
			list_menos = new ArrayList<Solicidetalle>();
			select = new ArrayList<SelectItem>();
			cargarRecursos();
			// veri();
			a = "soldet2?faces-redirect=true";// edicion
			// select2 = new ArrayList<SelectItem>();
		} catch (Exception e) {
			fechaInicio = null;
			fechaFin = null;
			eventoidedicio = ev.getIdEvento();
			fechaInicio = ev.getFechaInicio();
			fechaFin = ev.getFechaInicio();
			idusr = session.getIdUsr();
			DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
			String fi1 = new String(dateFormat.format(fechaInicio).toString());
			String ff1 = new String(dateFormat.format(fechaFin).toString());
			setActividad(ev.getNombre() + ", " + fi1 + " - " + ff1);
			setObjetivo(ev.getDescripcion());
			setH_inicio(ev.getFechaInicio());
			setH_fin(ev.getFechaFin());
			veri();
			editarEventoSS = true;
			a = "soldet3?faces-redirect=true";// creacion
		}
		return a;

	}

	// metodo para ir a solicitud y guardar el evento en un temporal
	public String irSolicitud1admin(Evento ev) {
		solivalor = true;
		if (esave == true) {
			Mensaje.crearMensajeWARN("El evento cuenta con una solicitud");
			return "";
		} else {
			try {
				Solicicabecera solicitud = mReserv.findSolicitudByID(ev
						.getSolicicabecera().getIdSolcab());
				id_sol = solicitud.getIdSolcab();
				direccion = solicitud.getDireccion();
				actividad = solicitud.getActividad();
				objetivo = solicitud.getObjetivo();
				fi = ev.getFechaInicio();
				ff = ev.getFechaFin();
				h_inicio = ev.getFechaInicio();
				h_fin = ev.getFechaFin();
				justificacion = solicitud.getJustificacion();
				notificacion = solicitud.getSms();
				listDetalles = solicitud.getSolicidetalles();
				estadoSol = solicitud.getSoliciestado();
				// Cargar datos recurso
				// capacidad_recurso=1;
				// Listas
				list_mas = new ArrayList<Solicidetalle>();
				list_menos = new ArrayList<Solicidetalle>();
				select = new ArrayList<SelectItem>();
				editarEventoSS=true;
				veri();
				// select2 = new ArrayList<SelectItem>();
			} catch (Exception e) {
				eventoidedicio = ev.getIdEvento();
				fechaInicio = ev.getFechaInicio();
				fechaFin = ev.getFechaInicio();
				idusr = session.getIdUsr();
				DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
				String fi1 = new String(dateFormat.format(fechaInicio)
						.toString());
				String ff1 = new String(dateFormat.format(fechaFin).toString());
				setActividad(ev.getNombre() + ", " + fi1 + " - " + ff1);
				setObjetivo(ev.getDescripcion());
				setH_inicio(ev.getFechaInicio());
				setH_fin(ev.getFechaFin());
				setcapacidad_recurso(ev.getCantidad());
				veri();
				
				System.out.print("sin solicitud");
				return "soldet3?faces-redirect=true";
			}
		}
		return "soldet2?faces-redirect=true";
	}

	// metodo para ir a solicitud y guardar el evento en un temporal
	public String irSolicitudnuevoeveadmin() {
		solivalor = true;
		String a = "";
		if (esave == true) {
			Mensaje.crearMensajeWARN("El evento cuenta con una solicitud");
			return "";
		} else if (!Validacion.fechaMayorIgual(ff)) {
			Mensaje.crearMensajeWARN("La fecha de solicitud no debe ser menor a la actual");
			return "";
		} else {
			try {
				if (Integer.parseInt(cantidad) == 0) {
					Mensaje.crearMensajeWARN("Ingrese el número de personas mayor que 0");
				} else {
					if (isNumeric(cantidad)) {
						if (Integer.parseInt(cantidad) == 0) {
							Mensaje.crearMensajeWARN("Ingrese el número de personas mayor que 0");
						} else {
							int sala1 = mReserv.findSalaByID(sala)
									.getCapacidad();
							if (Integer.parseInt(cantidad) <= sala1) {
								fechaInicio = new Timestamp(fi.getTime());
								fechaFin = new Timestamp(ff.getTime());
								idusr = session.getIdUsr();
								DateFormat dateFormat = new SimpleDateFormat(
										"dd/MM/yyyy");
								String fi1 = new String(dateFormat.format(
										fechaInicio).toString());
								String ff1 = new String(dateFormat.format(
										fechaFin).toString());
								eventotemp = mEvento.crearEventoTmp(
										nombre.trim(), descripcion.trim(),
										imagen, fechaInicio, fechaFin, costo,
										Integer.parseInt(cantidad));
								setActividad(eventotemp.getNombre() + ", "
										+ fi1 + " - " + ff1);
								setObjetivo(eventotemp.getDescripcion());
								setH_inicio(eventotemp.getFechaInicio());
								setH_fin(eventotemp.getFechaFin());
								veri();
								a = "soldet3?faces-redirect=true";
							} else if (Integer.parseInt(cantidad) > sala1) {
								Mensaje.crearMensajeWARN("El número de personas excede la capacidad de la sala");
							}
						}
					} else {
						Mensaje.crearMensajeWARN("La cantidad debe ser numérica");
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
			return a;
		}
	}

	// metodo para ir a solicitud y guardar el evento en un temporal
	public String irSolicitudnuevoeve() {
		solivalor = false;
		String a = "";
		if (esave == true) {
			Mensaje.crearMensajeWARN("El evento cuenta con una solicitud");
			return a;
		} else if (!Validacion.fechaMayorIgual(ff)) {
			Mensaje.crearMensajeWARN("La fecha de solicitud no debe ser menor a la actual");
			return a;
		} else {
			try {
				if (isNumeric(cantidad)) {
					if (Integer.parseInt(cantidad) == 0) {
						Mensaje.crearMensajeWARN("Ingrese el número de personas mayor que 0");
					} else {
						int sala1 = mReserv.findSalaByID(sala).getCapacidad();
						if (Integer.parseInt(cantidad) <= sala1) {
							fechaInicio = new Timestamp(fi.getTime());
							fechaFin = new Timestamp(ff.getTime());
							idusr = session.getIdUsr();
							DateFormat dateFormat = new SimpleDateFormat(
									"dd/MM/yyyy");
							String fi1 = new String(dateFormat.format(
									fechaInicio).toString());
							String ff1 = new String(dateFormat.format(fechaFin)
									.toString());
							eventotemp = mEvento
									.crearEventoTmp(nombre.trim(),
											descripcion.trim(), imagen,
											fechaInicio, fechaFin, costo,
											Integer.parseInt(cantidad));
							setActividad(eventotemp.getNombre() + ", " + fi1
									+ " - " + ff1);
							setObjetivo(eventotemp.getDescripcion());
							setH_inicio(eventotemp.getFechaInicio());
							setH_fin(eventotemp.getFechaFin());
							veri();
							agregardetalle = true;
							a = "soldet3?faces-redirect=true";
						} else if (Integer.parseInt(cantidad) > sala1) {
							Mensaje.crearMensajeWARN("El número de personas excede la capacidad de la sala");
						}
					}
				} else {
					Mensaje.crearMensajeWARN("La cantidad debe ser numérica");
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
			return a;
		}
	}

	public static boolean isNumeric(String str) {
		return str.matches("[+-]?\\d*(\\.\\d+)?");
	}

	public String regresar() {
		String r = "";
		if (agregarcontrol == false) {
			if (solivalor == true) {
				descripcionrecurso = "Descripción de Recurso";
				stock = "stock";
				capacidad = "capacidad";
				imagen = "300.jpg";
				imagensala = "300.jpg";
				imgMost = "300.jpg";
				imagentipo = "300.jpg";
				listDetalles = new ArrayList<Solicidetalle>();
				select = new ArrayList<SelectItem>();
				select2 = new ArrayList<SelectItem>();
				capacidad_recurso = null;
				list_mas = new ArrayList<Solicidetalle>();
				r = "eventos?faces-redirect=true";
			} else {
				descripcionrecurso = "Descripción de Recurso";
				stock = "stock";
				capacidad = "capacidad";
				imagen = "300.jpg";
				imagensala = "300.jpg";
				imgMost = "300.jpg";
				imagentipo = "300.jpg";
				select = new ArrayList<SelectItem>();
				select2 = new ArrayList<SelectItem>();
				capacidad_recurso = null;
				list_mas = new ArrayList<Solicidetalle>();
				listDetalles = new ArrayList<Solicidetalle>();
				capacidad_recurso = null;
				r = "soleven?faces-redirect=true";
			}
		} else {
			Mensaje.crearMensajeWARN("Debe dar click en actualizar");
		}
		return r;
	}

	// este metodo me almacena el evento temporal en la base de datos
	public String guardarEventoTemp() {
		try {
			mEvento.insertarEventoTem();
			Mensaje.crearMensajeINFO("Evento creado correctamente");
		} catch (Exception e) {
			e.printStackTrace();
		}

		return "";
	}

	// EVENTOS
	// accion para invocar el manager y crear evento
	public String limpiareve() {
		try {
			// reiniciamos datos (limpiamos el formulario)
			nombre = "";
			descripcion = "";
			lugar = "";
			imagen = "300.jpg";
			descripcionubicacion = "Descripción de la Sala";
			descripcionrecurso = "Descripción de Recurso";
			imagensala = "300.jpg";
			fi = null;
			ff = null;
			fechaInicio = null;
			fechaFin = null;
			costo = 0;
			sala = 0;
			cantidad = "";
			sc = 0;
			te = 0;
			idusr = 0;
			esave = false;
			FacesContext context = FacesContext.getCurrentInstance();
			context.addMessage(null, new FacesMessage("Campos Limpios", null));
		} catch (Exception e) {
			FacesContext context = FacesContext.getCurrentInstance();
			context.addMessage(null, new FacesMessage(
					"El evento no pudo ser limpiado", null));
			e.printStackTrace();
		}

		return "evento";
	}

	// metodo para guardar la imagen en el servidor
	public void ImagenServ(FileUploadEvent event) throws IOException {
		file = event.getFile();
		InputStream inputStream = null;
		OutputStream outputStream = null;
		if (file != null) {
			try {
				// Tomar PAD REAL
				ServletContext servletContext = (ServletContext) FacesContext
						.getCurrentInstance().getExternalContext().getContext();
				String carpetaImagenes = "/opt/wildfly/standalone/img/img_regece/imgevent/";
				setImagen(g);
				System.out.println("PAD------> " + carpetaImagenes);
				System.out.println("name------> " + getImagen());
				outputStream = new FileOutputStream(new File(carpetaImagenes
						+ File.separatorChar + getImagen()));
				inputStream = file.getInputstream();

				int read = 0;
				byte[] bytes = new byte[1024];

				while ((read = inputStream.read(bytes)) != -1) {
					outputStream.write(bytes, 0, read);
				}
				Mensaje.crearMensajeINFO("Carga correcta");
			} catch (Exception e) {
				Mensaje.crearMensajeWARN("No se pudo subir la imagen");
				e.printStackTrace();
			} finally {
				if (inputStream != null) {
					inputStream.close();
				}

				if (outputStream != null) {
					outputStream.close();
				}
			}
		} else {
			Mensaje.crearMensajeWARN("No se pudo seleccionar la imagen");
		}
	}

	// metodo para mostrar los EventosTipos en Eventos
	public List<SelectItem> getListaEveTipo() {
		List<SelectItem> listadoSI = new ArrayList<SelectItem>();
		List<Tipoevento> tipoevento = mEvento.findAllTipoEventos();

		for (Tipoevento t : tipoevento) {
			SelectItem item = new SelectItem(t.getIdTipoEvento(), t.getTipo());
			listadoSI.add(item);
		}
		return listadoSI;
	}

	// metodo para asignar el TipoEvento al Evento
	public void asignarTipoeve() {
		mEvento.asignarTipoevento(te);
	}

	// /////////////////////////////////solicitudes//////////////////////////////////////////////////////////////////
	// Metodos proceso de ejecucion
	public String crearNuevaSolicitud() {
		String resp = "";
		try {
			// SolicitudTemporal
			solicitudCabTem = mReserv.crearSolicitudTmp(getDireccion(),
					getActividad(), getObjetivo(), getJustificacion(),
					new Date(), getSession().getIdUsr());
			id_recurso = -1;
			capacidad_recurso = null;
			solicitudCabTmpGuardada = false;
			notificacion = "";
			resp = "";
		} catch (Exception e) {
			e.printStackTrace();
			Mensaje.crearMensajeWARN("Error al crear la solicitud");
		}
		return resp;
	}

	public String insertarDetalleSolicitud() {
		if (solicitudCabTmpGuardada == true) {
			Mensaje.crearMensajeINFO("La solicitud fue guardada anteriormente");
			return "";
		}
		try {
			controlarcantidad();
			if (agregardetalle == true) {
				mReserv.agregarSolicitudDetalleTmp(getId_recurso(),
						getcapacidad_recurso(), h_fin, h_inicio);
				id_recurso = -1;
				agregardetalle = false;
				capacidad_recurso = null;
			}
			// LIMPIAR LISTADO
		} catch (Exception e) {
			e.printStackTrace();
			id_recurso = -1;
			agregardetalle = false;
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
				if (mReserv.controlarcantidadmanager(getId_recurso(),
						getcapacidad_recurso(), h_fin, h_inicio) == true) {
					agregardetalle = true;
				} else if (mReserv.controlarcantidadmanager(getId_recurso(),
						getcapacidad_recurso(), h_fin, h_inicio) == false) {
					agregardetalle = false;
					Mensaje.crearMensajeWARN("Error debe especificar el recurso o La cantidad es mayor a la del recurso solicitado");
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
		try {
			List<Recurso> listadoRecurso = mReserv.findAllRecursosDisponibles(
					h_inicio, h_fin, horainicio, horafin);
			for (Recurso p : listadoRecurso) {
				if (id_recursotipo.equals(p.getRecursotipo().getIdRectipo())) {
					mReserv.agregarSolicitudDetalleTmplista(p.getIdRecurso(),
							p.getCapacidad(), h_fin, h_inicio);
					System.out.println(p.getIdRecurso() + " CON NUM RECURSO "
							+ p.getRecursotipo().getIdRectipo());
				}
			}
			Mensaje.crearMensajeINFO("Los recursos se añadieron");
			id_recurso = -1;
			capacidad_recurso = null;
			// LIMPIAR LISTADO
			select = new ArrayList<SelectItem>();
			select2 = new ArrayList<SelectItem>();
			h_inicio = new Timestamp(new Date().getTime());
			h_fin = new Timestamp(new Date().getTime());
		} catch (Exception e) {
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(e.getMessage()));
			// LIMPIAR LISTADO
			id_recurso = -1;
			capacidad_recurso = null;
			// LIMPIAR LISTADO
			select = new ArrayList<SelectItem>();
			select2 = new ArrayList<SelectItem>();
			h_inicio = new Timestamp(new Date().getTime());
			h_fin = new Timestamp(new Date().getTime());
		}
		return "";
	}

	public void quitarDetalleSolicitud(Solicidetalle detalle) {
		try {
			List<Recursosactivo> listrecact = mReserv
					.findRecursosactivoByRecursoId(detalle.getSolicicabecera()
							.getIdSolcab(), detalle.getRecurso().getIdRecurso());
			for (Recursosactivo recursosactivo : listrecact) {
				mReserv.eliminarRecursoActivos(recursosactivo.getIdRecact());
			}
			mReserv.eliminarSoliciDetalleByID(detalle.getIdSoldet());
			listDetalles.remove(detalle);
			cargarRecursos();
			Mensaje.crearMensajeINFO("Se eliminó el recurso");
			agregarcontrol = true;
		} catch (Exception e) {
			Mensaje.crearMensajeWARN("No se pudo quitar el recurso");
		}
	}

	// metodo para guardar la solicitud
	public String guardarSolicitud() {
		String rsp = "";
		if (solicitudCabTmpGuardada == true) {
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage("La solicitud fue guardada", null));
			return "";
		}
		if (isEditarEventoSS()) {
			try {
				mReserv.guardarSolicitudTemporal(solicitudCabTem);
				mEvento.asignarSolcab(solicitudCabTem.getIdSolcab());
				Evento ev = mEvento.EventoByID(eventoidedicio);
				ev.setSolicicabecera(getSolicitudCabTem());
				solicitudCabTmpGuardada = true;
				DateFormat date = new SimpleDateFormat("dd/MM/yyyy");
				smscoradminsolreceve = "El Sr/ra. "
						+ direccion
						+ " "
						+ justificacion
						+ ", envi&oacute; una solicitud para un recurso; Requiere la aprobaci&oacute;n o negaci&oacute;n.; <br/>"
						+ "los datos de la solicitud son:"
						+ "<br/> Actividad: " + actividad + ""
						+ "<br/> Objetivo: " + objetivo + ""
						+ "<br/> Fecha de petici&oacute;n: "
						+ date.format(h_inicio).toString() + ""
						+ "<br/> Fecha de petici&oacute;n: "
						+ date.format(h_fin).toString() + "";

				smscorususolreceve = "Sr/ra.  "
						+ direccion
						+ " "
						+ justificacion
						+ ", su petici&oacute;n de solicitud del recurso del sistema REGECE (Reservas de Espacios y Gesti&oacute;n de Eventos del Centro de Emprendimiento), ser&aacute; verificado por los administradores, espere al mensaje de confirmaci&oacute;n. <br/>"
						+ "los datos su solicitud son:" + "<br/> Actividad: "
						+ actividad + "" + "<br/> Objetivo: " + objetivo + ""
						+ "<br/> Fecha de petici&oacute;n: "
						+ date.format(h_inicio).toString() + ""
						+ "<br/> Fecha de petici&oacute;n: "
						+ date.format(h_fin).toString() + "";

				getcorreosusuc();
				System.out.println(correosadminsolreceve);
				mb.envioMailWS(correosadminsolreceve,
						"Notificación de YACHAY/REGECE", smscoradminsolreceve);
				mb.envioMailWS(session.getCorreo(),
						"Notificación de YACHAY/REGECE", smscorususolreceve);
				correosadminsolreceve = "";
				smscoradminsolreceve = "";
				smscorususolreceve = "";
				solicitudCabTmpGuardada = true;
				actividad = "";
				objetivo = "";
				h_inicio = new Timestamp(new Date().getTime());
				h_fin = new Timestamp(new Date().getTime());
				eventoidedicio = 0;
				g = "";
				setActividad("");
				setObjetivo("");
				setEditarEventoSS(false);
				rsp = "";
				Mensaje.crearMensajeINFO("Su solicitud fue enviada espere el correo de confirmación");
			} catch (Exception e) {
				e.printStackTrace();
			}
		} else {
			if (solicitudCabTem.getSolicidetalles().size() == 0) {
				try {
					eventoidedicio = null;
					this.crearEvento();
					actividad = "";
					objetivo = "";
					h_inicio = new Timestamp(new Date().getTime());
					h_fin = new Timestamp(new Date().getTime());
					setActividad("");
					setObjetivo("");
					solicitudCabTem = null;
					eventoidedicio = null;
					rsp = "";
				} catch (Exception e) {
					e.printStackTrace();
				}
			} else
				try {
					Integer scID = mReserv
							.guardarSolicitudTemporal(solicitudCabTem);
					mEvento.asignarSolcab(scID);
					crearEvento();
					
					mEvento.editarEventoCreado(eventoidedicio, scID);
					solicitudCabTmpGuardada = true;
					DateFormat date = new SimpleDateFormat("dd/MM/yyyy");
					smscoradminsolreceve = "<!DOCTYPE html><html lang='es'><head><meta http-equiv='Content-Type' content='text/html; charset=utf-8' />"
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
							+ "<br/> Fecha de petici&oacute;n: "
							+ date.format(h_inicio).toString()
							+ ""
							+ "<br/> Fecha de petici&oacute;n: "
							+ date.format(h_fin).toString()
							+ "<br/> Saludos cordiales, "
							+ "<br/> Sistema de REGECE Yachay EP"
							+ "<br/><em><strong>NOTA:</strong> Este correo es generado automáticamente por el sistema favor no responder al mismo.</em></body></html>";

					smscorususolreceve = "<!DOCTYPE html><html lang='es'><head><meta http-equiv='Content-Type' content='text/html; charset=utf-8' />"
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
							+ "<br/> Fecha de petici&oacute;n: "
							+ date.format(h_inicio).toString()
							+ ""
							+ "<br/> Fecha de petici&oacute;n: "
							+ date.format(h_fin).toString()
							+ "<br/> Saludos cordiales, "
							+ "<br/> Sistema de REGECE Yachay EP"
							+ "<br/><em><strong>NOTA:</strong> Este correo es generado automáticamente por el sistema favor no responder al mismo.</em></body></html>";

					getcorreosusuc();
					System.out.println(correosadminsolreceve);
					mb.envioMailWS(correosadminsolreceve,
							"Notificación de YACHAY/REGECE",
							smscoradminsolreceve);
					mb.envioMailWS(session.getCorreo(),
							"Notificación de YACHAY/REGECE", smscorususolreceve);
					correosadminsolreceve = "";
					smscoradminsolreceve = "";
					smscorususolreceve = "";
					solicitudCabTmpGuardada = true;
					actividad = "";
					objetivo = "";
					h_inicio = new Timestamp(new Date().getTime());
					h_fin = new Timestamp(new Date().getTime());
					setActividad("");
					setObjetivo("");
					g = "";
					solicitudCabTem = null;
					eventoidedicio = null;
					rsp = "home";
					Mensaje.crearMensajeINFO("Su solicitud fue enviada espere el correo de confirmación");
				} catch (Exception e) {
					e.printStackTrace();
				}
		}

		return rsp;
	}

	// metodo para guardar la solicitud
	public String guardarSolicitude() {
		String rsp = "";
		if (solicitudCabTmpGuardada == true) {
			Mensaje.crearMensajeWARN("La solicitud ya fue guardada");
			return "";
		}
		if (solicitudCabTem.getSolicidetalles().size() == 0) {
			try {
				mReserv.guardarSolicitudTemporal(solicitudCabTem);
				mEvento.asignarSolcab(solicitudCabTem.getIdSolcab());
				actividad = "";
				objetivo = "";
				h_inicio = new Timestamp(new Date().getTime());
				h_fin = new Timestamp(new Date().getTime());
				setActividad("");
				setObjetivo("");
				rsp = "";
				g = "";
			} catch (Exception e) {
				e.printStackTrace();
			}
		} else
			try {
				mReserv.guardarSolicitudTemporal(solicitudCabTem);
				mEvento.asignarSolcab(solicitudCabTem.getIdSolcab());
				Evento ev = mEvento.EventoByID(eventoidedicio);
				ev.setSolicicabecera(getSolicitudCabTem());
				solicitudCabTmpGuardada = true;
				DateFormat date = new SimpleDateFormat("dd/MM/yyyy");
				smscoradminsolreceve = "<!DOCTYPE html><html lang='es'><head><meta http-equiv='Content-Type' content='text/html; charset=utf-8' />"
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
						+ "<br/> Fecha de petici&oacute;n: "
						+ date.format(h_inicio).toString()
						+ ""
						+ "<br/> Fecha de petici&oacute;n: "
						+ date.format(h_fin).toString()
						+ "<br/> Saludos cordiales, "
						+ "<br/> Sistema de REGECE Yachay EP"
						+ "<br/><em><strong>NOTA:</strong> Este correo es generado automáticamente por el sistema favor no responder al mismo.</em></body></html>";

				smscorususolreceve = "<!DOCTYPE html><html lang='es'><head><meta http-equiv='Content-Type' content='text/html; charset=utf-8' />"
						+ "<meta name='viewport' content='width=device-width'></head><body>"
						+ "Sr/ra.  "
						+ direccion
						+ " "
						+ justificacion
						+ ", su petici&oacute;n de solicitud del recurso del sistema REGECE (Reservas de Espacios y Gesti&oacute;n de Eventos del Centro de Emprendimiento), &aacute; verificado por los administradores, espere al mensaje de confirmaci&oacute;n. <br/>"
						+ "los datos su solicitud son:"
						+ "<br/> Actividad: "
						+ actividad
						+ ""
						+ "<br/> Objetivo: "
						+ objetivo
						+ ""
						+ "<br/> Fecha de petici&oacute;n: "
						+ date.format(h_inicio).toString()
						+ ""
						+ "<br/> Fecha de petici&oacute;n: "
						+ date.format(h_fin).toString()
						+ "<br/> Saludos cordiales, "
						+ "<br/> Sistema de REGECE Yachay EP"
						+ "<br/><em><strong>NOTA:</strong> Este correo es generado automáticamente por el sistema favor no responder al mismo.</em></body></html>";
				getcorreosusuc();
				mb.envioMailWS(correosadminsolreceve,
						"Notificación de YACHAY/REGECE", smscoradminsolreceve);
				mb.envioMailWS(session.getCorreo(),
						"Notificación de YACHAY/REGECE", smscorususolreceve);
				correosadminsolreceve = "";
				smscoradminsolreceve = "";
				smscorususolreceve = "";
				solicitudCabTmpGuardada = true;
				objetivo = "";
				h_inicio = new Timestamp(new Date().getTime());
				h_fin = new Timestamp(new Date().getTime());
				eventoidedicio = 0;
				g = "";
				setActividad("");
				setObjetivo("");
				rsp = "home";
				Mensaje.crearMensajeWARN("Su solicitud fue enviada espere el correo de confirmación");
			} catch (Exception e) {
				FacesContext.getCurrentInstance().addMessage(null,
						new FacesMessage(e.getMessage()));
			}

		return rsp;
	}

	// metodo para listar correos de ususariosadmin
	public String getcorreosusuc() {
		try {
			List<Usuario> a = manager.findUsrsPrincipal();
			correosadminsolreceve = "";
			for (Usuario u : a) {
				correosadminsolreceve += u.getCorreo() + ",";
			}
			int max = correosadminsolreceve.length();
			correosadminsolreceve = correosadminsolreceve.substring(0, max - 1)
					.trim();
			System.out.println(correosadminsolreceve);
		} catch (Exception e) {
			Mensaje.crearMensajeWARN("No se encuentran usuarios administradores");
			e.printStackTrace();
		}
		return correosadminsolreceve;
	}

	// CARGAR toods los recursos LIBRES
	public void todoslorecursos() {
		try {
			mReserv.quitarrecursosactivos();
			cargarTipoRecursos();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// CARGAR TIPORECURSOS LIBRES
	public void cargarTipoRecursos() {
		if (getH_fin() == null || getH_inicio() == null) {
			Mensaje.crearMensajeWARN("Seleccione horario para continuar");
		} else {
			// Modificacion de Horas
			setHorainicio(this.fechaAtiempo(h_inicio));
			setHorafin(this.fechaAtiempo(h_fin));
			System.out.println("cargartiporecursos hora inicio: " + h_inicio
					+ " hora fin" + h_fin);
			System.out.println("cargartiporecursos hora inicio: " + horainicio
					+ " hora fin" + horafin);
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

	// LISTADO DE RECURS
	public List<SelectItem> getlistaRecursosLibres() {
		List<SelectItem> listadoSI = new ArrayList<SelectItem>();
		List<Recurso> listadoRecurso = mReserv.findAllRecursosDisponibles(
				h_inicio, h_fin, horainicio, horafin);
		for (Recurso p : listadoRecurso) {
			int contador = mReserv.findContadorRecurso(h_inicio, h_fin,
					p.getIdRecurso());
			SelectItem item = new SelectItem(p.getIdRecurso(), p.getNombre());

			listadoSI.add(item);
		}
		System.out.println("------->TAMAÑO " + listadoSI.size());
		return listadoSI;
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

	// metodo para cargar eventos
	public String CargarEventos(Evento ev) {
		idEvento = ev.getIdEvento();
		nombre = ev.getNombre();
		asignarNombreImagen();
		descripcion = ev.getDescripcion();
		interno = ev.getInterno();
		// lugar = ev.getLugar();
		costo = ev.getCosto();
		fi = ev.getFechaInicio();
		ff = ev.getFechaFin();
		estadoeven = ev.getEstado();
		/***** MOD FECHAS ***/
		fechaInicio = new Timestamp(ev.getFechaInicio().getTime());
		fechaFin = new Timestamp(ev.getFechaFin().getTime());
		modEv = ev;
		sala = ev.getSala().getIdSala();
		te = ev.getTipoevento().getIdTipoEvento();
		cantidad = ev.getCantidad().toString();
		imagen = ev.getImagen();
		idusr = ev.getUsuario().getIdUsr();
		nombreusuario = ev.getUsuario().getNombre();
		apellidousuario = ev.getUsuario().getApellido();
		sms = ev.getSms();
		todaslasalas();
		return "";
	}

	// metodo para listar los eventos
	public List<Evento> getListRegEventos() {
		return mEvento.findAllEventos();
	}

	// metodo para listar los registros
	public List<Evento> getListEvenUsu() {
		// List<Evento> a = mEvento.findAllEventos();
		List<Evento> a = mEvento.findAllEventosOrdenados();
		List<Evento> l1 = new ArrayList<Evento>();
		for (Evento t : a) {
			if (t.getUsuario().getIdUsr().equals(session.getIdUsr())) {
				l1.add(t);
			}
		}
		return l1;
	}

	// /////////////////////////////////Traslados//////////////////////////////////////////////////////////////////
	public String irsolres2() {
		String r = "";
		if (solicitudCabTem.getSolicidetalles().size() > 0) {
			r = "solres2";
			System.out.print(r);
		} else {
			Mensaje.crearMensajeWARN("Debe seleccionar recursos");
			System.out.print(r);
		}
		return r;
	}

	public String irsolres3() {
		String r = "";
		r = "solres3";
		System.out.print(r);
		return r;
	}

	public String ireve() {
		nombre = "";
		descripcion = "";
		lugar = "";
		imagen = "300.jpg";
		descripcionubicacion = "Descripción de la Sala";
		descripcionrecurso = "Descripción de Recurso";
		imagensala = "300.jpg";
		fi = null;
		ff = null;
		sala = 0;
		fechaInicio = null;
		fechaFin = null;
		costo = 0;
		cantidad = "";
		sc = 0;
		te = 0;
		idusr = 0;
		esave = false;
		descripcionubicacion = "Descripción de la Ubicación";
		descripcionrecurso = "Descripción de Recurso";
		stock = "stock";
		imagen = "300.jpg";
		return "soleven?faces-redirect=true";
	}

	public String ireve2() {
		nombre = "";
		descripcion = "";
		lugar = "";
		imagen = "300.jpg";
		fi = null;
		ff = null;
		fechaInicio = null;
		fechaFin = null;
		costo = 0;
		sala = 0;
		cantidad = "";
		sc = 0;
		descripcionubicacion = "Descripción de la Ubicación";
		descripcionrecurso = "Descripción de Recurso";
		stock = "stock";
		capacidad = "capacidad";
		imagensala = "300.jpg";
		te = 0;
		idusr = 0;
		esave = false;
		return "eventos?faces-redirect=true";
	}

	public String irsoldet2() {
		return "soldet2?faces-redirect=true";
	}

	public String irsoldet3() {
		return "soldet3?faces-redirect=true";
	}

	public String irsoldet4() {
		return "soldet4?faces-redirect=true";
	}

	public void irEvento() {
		// limpiamos los datos
		nombre = "";
		descripcion = "";
		lugar = "";
		imagen = "300.jpg";
		fi = null;
		ff = null;
		fechaInicio = null;
		fechaFin = null;
		costo = 0;
		cantidad = "";
		sala = 0;
		sc = 0;
		sms="";
		te = 0;
		descripcionubicacion = "Descripción de la Ubicación";
		descripcionrecurso = "Descripción de Recurso";
		imagensala = "300.jpg";
		idusr = 0;
		esave = false;
		select = new ArrayList<SelectItem>();
		Mensaje.crearMensajeWARN("Actualización cancelada");
	}

	public String irEvento1() {
		// limpiamos los datos
		nombre = "";
		descripcion = "";
		lugar = "";
		imagen = "300.jpg";
		fi = null;
		ff = null;
		fechaInicio = null;
		fechaFin = null;
		descripcionubicacion = "Descripción de la Ubicación";
		descripcionrecurso = "Descripción de Recurso";
		imagensala = "300.jpg";
		costo = 0;
		cantidad = "";
		sc = 0;
		te = 0;
		sala = 0;
		nombreusuario = "";
		apellidousuario = "";
		idusr = 0;
		esave = false;
		select = new ArrayList<SelectItem>();
		Mensaje.crearMensajeWARN("Actualización cancelada");
		return "eventos?faces-redirect=true";
	}

	public String irEvento2() {
		return "eventos?faces-redirect=true";
	}

	// metodo para edicion de los eventos y solicitud
	public void editarEvento() {
		try {
			if (validaciones() == true) {
				asignarTipoeve();// guardar en un objeto sala y mandar como
									// valor al editar
				asignarsala();
				fechaInicio = new Timestamp(fi.getTime());
				fechaFin = new Timestamp(ff.getTime());
				int sala1 = mReserv.findSalaByID(sala).getCapacidad();
				if (Integer.parseInt(cantidad) <= sala1) {
					idusr = session.getIdUsr();
					if (!validarDisponibilidadRecursos(idEvento, fechaInicio,
							fechaFin) == true) {
						modificarRecursosDelEvento(idEvento, fechaInicio,
								fechaFin);
						mEvento.editarEventos(idEvento, nombre.trim(),
								descripcion.trim(), imagen, fechaInicio,
								fechaFin, costo, Integer.parseInt(cantidad),
								interno);
						Mensaje.crearMensajeINFO("Se modificó satisfactoriamente el evento");
					} else {
						Mensaje.crearMensajeWARN("Los recursos de la solicitud estan siendo usados");
					}

				} else if (Integer.parseInt(cantidad) > sala1) {
					Mensaje.crearMensajeWARN("El número de personas excede la capacidad de la sala que es: "+ sala1 + " ");
				}
			} else {
				Mensaje.crearMensajeWARN("Evento no pudo ser editado");
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private boolean validaciones() {
		boolean resultado = false;
		if (te == -1 || te == null || te == 0) {
			Mensaje.crearMensajeWARN("Debe seleccionar un tipo de evento");
		} else if (fi.after(ff)) {
			Mensaje.crearMensajeWARN("La fecha inicio debe ser menor que la fecha fin");
		} else if (sala == -1 || sala == null || sala == 0) {
			Mensaje.crearMensajeWARN("Debe seleccionar una sala");
		} else if (!isNumeric(cantidad)) {
			Mensaje.crearMensajeWARN("El campo se debe ingresar números");
		} else if (Integer.parseInt(cantidad) == 0) {
			Mensaje.crearMensajeWARN("Ingrese el número de personas mayor que 0");
		} else {
			resultado = true;
		}
		return resultado;
	}

	private boolean validarDisponibilidadRecursos(Integer EventoId,
			Timestamp fechaInicio, Timestamp fechaFin) {
		boolean resultado = false;
		try {
			Evento ev = mEvento.EventoByID(EventoId);
			h_inicio = new Timestamp(fi.getTime());
			h_fin = new Timestamp(ff.getTime());
			if (ev.getSolicicabecera().getIdSolcab() != null) {
				List<DatosRecursos> recursosActivosFechas = mc.FindAllRecursosActivos(fechaInicio, fechaFin, ev.getSolicicabecera().getIdSolcab());
				List<Solicidetalle> detalleCabeceraEvento = mReserv.findSolicitudDetalleByCabeceraId(ev.getSolicicabecera().getIdSolcab());
				for (Solicidetalle solicitudDetalle : detalleCabeceraEvento) {
					if (!recursosActivosFechas.isEmpty()) {
						for (DatosRecursos recursoActivo : recursosActivosFechas) {
							if (solicitudDetalle.getRecurso().getIdRecurso() == recursoActivo.getId_recurso()) {
								resultado = true;
								break;
							} else {
								resultado = false;
							}
						}
					} else {
						resultado = false;
					}
				}
			} else {
				resultado = false;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return resultado;
	}

	private void modificarRecursosDelEvento(Integer EventoId,
			Timestamp fechaInicio, Timestamp fechaFin) {
		try {
			Evento ev = mEvento.EventoByID(EventoId);
			h_inicio = new Timestamp(fi.getTime());
			h_fin = new Timestamp(ff.getTime());
			String actividad1 = "";
			if (ev.getSolicicabecera().getIdSolcab() != null) {
				List<Solicicabecera> solicitudCabeceraEvento = mReserv
						.findSolicitudCabeceraByIdEvento(ev.getSolicicabecera()
								.getIdSolcab());
				for (Solicicabecera solicitudCabecera : solicitudCabeceraEvento) {
					DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
					String fi1 = new String(dateFormat.format(fechaInicio)
							.toString());
					String ff1 = new String(dateFormat.format(fechaFin)
							.toString());
					actividad1 = nombre + ", " + fi1 + " - " + ff1 + "";
					mEvento.editarSolicitudCabecera(
							solicitudCabecera.getIdSolcab(), actividad1.trim(),
							descripcion);
					List<Solicidetalle> detalleDetalleCabecera = mReserv
							.findSolicitudDetalleByCabeceraId(solicitudCabecera
									.getIdSolcab());
					for (Solicidetalle detalleSolicitud : detalleDetalleCabecera) {
						mReserv.editarDetalleSolicitud(
								detalleSolicitud.getIdSoldet(), fechaInicio,
								fechaFin);
						mReserv.editarRecursoSolicitado(detalleSolicitud
								.getSolicicabecera().getIdSolcab(),
								fechaInicio, fechaFin);
					}
				}
			} else {

			}
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public String adicionarDetalles() {
		try {
			if (id_recurso == null || id_recurso == -1) {
				Mensaje.crearMensajeWARN("Seleccione recurso");
			} else if (capacidad_recurso == null
					|| capacidad_recurso.intValue() <= 0) {
				Mensaje.crearMensajeWARN("Escriba la cantidad del recurso");
			} else {
				Recurso rec = mReserv.findRecursoByID(getId_recurso());
				if (getcapacidad_recurso() <= rec.getCapacidad()) {
					Solicidetalle det = new Solicidetalle();
					det.setFechadet(getRecursofecha());
					det.setCapacidad(getcapacidad_recurso());
					det.setHoraFin(h_fin);
					det.setHoraInicio(h_inicio);
					det.setRecurso(rec);
					det.setSolicicabecera(mReserv
							.findSolicitudCabeceraById(idEvSol));
					// agregar a la lista
					listDetSolEv.add(det);
					// Setea Variables
					id_recurso = -1;
					capacidad_recurso = null;
					select = new ArrayList<SelectItem>();
					h_inicio = new Timestamp(new Date().getTime());
					h_fin = new Timestamp(new Date().getTime());
				} else {
					Mensaje.crearMensajeWARN("Verifique la cantidad del recurso");
				}
			}
		} catch (Exception e) {
			Mensaje.crearMensajeWARN("No se pudo agregar recurso");
		}
		return "";
	}

	public String guardarEvSol() {
		String resp = "";
		try {
			if (getListDetSolEv().size() == 0) {
				Mensaje.crearMensajeWARN("Debe agregar recursos");
			} else {
				// Editamos evento
				mEvento.editarEventos(idEvento, nombre.trim(),
						descripcion.trim(), imagen, fechaInicio,
						fechaFin, costo, Integer.parseInt(cantidad), interno);
				// reiniciamos datos de eventos
				idEvento = 0;
				nombre = "";
				descripcion = "";
				lugar = "";
				imagen = "300.jpg";
				fi = null;
				ff = null;
				idusr = null;
				fechaInicio = null;
				fechaFin = null;
				costo = 0;
				cantidad = "";
				sc = 0;
				interno = false;
				te = 0;
				idusr = 0;
				esave = false;
				sala = 0;
				// Modificar solicitud cabecera
				mReserv.modificarSolCabEvento(getIdEvSol(), getDireccion(),
						getObjetivo(), getJustificacion());
				// Agregar Detalles
				for (Solicidetalle det : listDetSolEv) {
					mReserv.insertarSoliciDatalle(det);
				}
				mReserv.agregarListaRecursoActivo(getListDetSolEv(),
						getIdEvSol());
				resp = "eventos";
				// Limpiar Datos de solicitud
				idEvSol = null;
				direccion = "";
				actividad = "";
				recursofecha = null;
				justificacion = "";
				objetivo = "";
				listDetSolEv = new ArrayList<Solicidetalle>();
				Mensaje.crearMensajeINFO("Se ha modificado el evento con su solicitud");
			}
		} catch (Exception e) {
			Mensaje.crearMensajeWARN("No se pudo guardar el evento con su solicitud");
		}
		return resp;
	}

	public void veri() {
		try {
			// SolicitudTemporal
			solicitudCabTem = mReserv.crearSolicitudTmp(getDireccion(),
					getActividad(), getObjetivo(), getJustificacion(),
					new Date(), getSession().getIdUsr());
			id_recurso = -1;
			capacidad_recurso = null;
			solicitudCabTmpGuardada = false;
			id_recurso = -1;
			// LIMPIAR LISTADO
			select = new ArrayList<SelectItem>();
			todoslorecursos();
			cargarRecursos();
			Mensaje.crearMensajeINFO("Datos almacenados correctamente");
		} catch (Exception e) {
			Mensaje.crearMensajeWARN("Error al crear la solicitud");
			e.printStackTrace();
		}
	}

	public String vericargar() {
		String resp = "";
		try {
			// SolicitudTemporal
			solicitudCabTem = mReserv.crearSolicitudTmp(getDireccion(),
					getActividad(), getObjetivo(), getJustificacion(),
					new Date(), getSession().getIdUsr());
			id_recurso = -1;
			capacidad_recurso = null;
			solicitudCabTmpGuardada = false;
			id_recurso = -1;
			// LIMPIAR LISTADO
			select = new ArrayList<SelectItem>();
			todoslorecursos();
			Mensaje.crearMensajeWARN("Datos almacenados");
		} catch (Exception e) {
			Mensaje.crearMensajeWARN("Error al crear la solicitud");
		}
		return resp;
	}

	// metodos para aprobar o negar el evento
	public String aprobarevento(Evento eve) {
		try {
			if (eve.getEstado().equals("Activado")) {
				Mensaje.crearMensajeINFO("La inscripción se encuentra activada");
			} else {
				mEvento.cambioSMS(eve.getIdEvento());
				eve.setEstado("Activado");
				Mensaje.crearMensajeINFO("Ha cambiado el estado del evento a Activado");
			}
		} catch (Exception e) {
			Mensaje.crearMensajeWARN("Error al activar la inscripción");
			e.printStackTrace();
		}
		return "";
	}

	public String negarevento(Evento eve) {
		try {
			if (eve.getEstado().equals(("Desactivado"))) {
				Mensaje.crearMensajeINFO("La inscripción se encuentra desactivada");
			} else {
				eve.setEstado("Desactivado");
				Mensaje.crearMensajeWARN("Ha cambiado el estado del evento a desactivado");
			}
		} catch (Exception e) {
			Mensaje.crearMensajeWARN("Error al desactivar la inscripción");
			e.printStackTrace();
		}
		return "";
	}

	public String negareventoxus(Evento eve) {
		try {
			if (eve.getEstado().equals(("Desactivado"))) {
				Mensaje.crearMensajeWARN("La inscripción se encuentra desactivada");
			} else if (eve.getEstado().equals("Activado")) {
				Mensaje.crearMensajeWARN("La inscripción se encuentra activada, envie el mensaje para cancelar el Evento");
			} else if (eve.getEstado().equals("Pendiente")) {
				eve.setEstado("Desactivado");
				Mensaje.crearMensajeWARN("Ha cambiado el estado del evento a desactivado");
			}
		} catch (Exception e) {
			Mensaje.crearMensajeWARN("Error al desactivar la inscripción");
		}
		return "";
	}

	// ///////////////////////////cancelacion de evento envio de mensaje

	// envio de mensajeria
	public String asignarsmscancelacion(Evento ev) {
		idEvento = ev.getIdEvento();
		nombre = ev.getNombre();
		descripcion = ev.getDescripcion();
		// lugar = ev.getLugar();
		costo = ev.getCosto();
		fi = ev.getFechaInicio();
		ff = ev.getFechaFin();
		sala = ev.getSala().getIdSala();
		estadoeven = ev.getEstado();
		/***** MOD FECHAS ***/
		fActualInicio = ev.getFechaInicio();
		fActualFin = ev.getFechaFin();
		modEv = ev;
		te = ev.getTipoevento().getIdTipoEvento();
		idusr = ev.getUsuario().getIdUsr();
		cantidad = ev.getCantidad().toString();
		imagen = ev.getImagen();
		try {
			smscor = "Sr/ra. Administrador/a, le informo que el Evento <br/>"
					+ "con nombre: "
					+ nombre
					+ ".<br/> "
					+ "con la descripci&oacute;n: "
					+ descripcion
					+ ".<br/>"
					// + "en el lugar: "+lugar+".\n"
					+ "con el tipo de evento: "
					+ ev.getTipoevento().getTipo()
					+ ".<br/>"
					+ "con fecha de inicio: "
					+ fi
					+ ".<br/>"
					+ "con fecha final: "
					+ ff
					+ ".<br/>"
					+ "con la cantidad de: "
					+ cantidad
					+ " personas.<br/>"
					+ "se desea Desactivarlo de manera urgente.<br/> "
					+ "Agradeciendole de antemano.<br/> "
					+ "Nombre: "
					+ session.getNombre()
					+ "<br/>"
					+ "Apellido: "
					+ session.getApellido() + "";
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "";
	}

	// envio de mensajeria notificacion
	public String asignarsms(Evento ev) {
		idEvento = ev.getIdEvento();
		nombre = ev.getNombre();
		descripcion = ev.getDescripcion();
		// lugar = ev.getLugar();
		costo = ev.getCosto();
		fi = ev.getFechaInicio();
		ff = ev.getFechaFin();
		estadoeven = ev.getEstado();
		/***** MOD FECHAS ***/
		fActualInicio = ev.getFechaInicio();
		fActualFin = ev.getFechaFin();
		modEv = ev;
		sala = ev.getSala().getIdSala();
		te = ev.getTipoevento().getIdTipoEvento();
		idusr = ev.getUsuario().getIdUsr();
		cantidad = ev.getCantidad().toString();
		imagen = ev.getImagen();
		idusr = ev.getUsuario().getIdUsr();
		sms = ev.getSms();
		try {
			Usuario u = manager.findususarioByID(idusr);
			smscor = "<!DOCTYPE html><html lang='es'><head><meta http-equiv='Content-Type' content='text/html; charset=utf-8' />"
					+ "<meta name='viewport' content='width=device-width'></head><body>"
					+ "Sr/ra. "
					+ u.getNombre()
					+ " "
					+ u.getApellido()
					+ ", le informo que el Evento <br/>"
					+ "con nombre: "
					+ nombre
					+ ".<br/> "
					+ "con la descripci&oacute;n: "
					+ descripcion
					+ ".<br/>"
					// + "en el lugar: "+lugar+".\n"
					+ "con el tipo de evento: "
					+ ev.getTipoevento().getTipo()
					+ ".<br/>"
					+ "con fecha de inicio: "
					+ fi
					+ ".<br/>"
					+ "con fecha final: "
					+ ff
					+ ".<br/>"
					+ "con la cantidad de: "
					+ cantidad
					+ " personas.<br/>"
					+ "ha sido "
					+ estadoeven
					+ ".<br/> "
					+ "<br/> Saludos cordiales, "
					+ "<br/> Sistema de REGECE Yachay EP"
					+ "<br/><em><strong>NOTA:</strong> Este correo es generado automáticamente por el sistema favor no responder al mismo.</em></body></html>";
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "";
	}

	// poner metodo de envio de solicitud de aprovacion de eventos
	// Envia el mensaje de cancelacion de evento
	public String enviarmensajeactivacionevento(Evento ev) {
		try {
			if (!estadoeven.equals("Pendiente") && sms.equals("No Notificado")) {
				mEvento.cambioSMSenvio(idEvento);
				Usuario u = manager.findususarioByID(idusr);
				System.out.println(u.getCorreo());
				mb.envioMailWS(u.getCorreo(), "Cancelación Evento/REGECE ",smscor);
				// limpiamos los datos notificaciones.inno@gmail.com
				// innopolisyachay2015@gmail.com
				idEvento = 0;
				nombre = "";
				descripcion = "";
				lugar = "";
				imagen = "300.jpg";
				fi = null;
				ff = null;
				fechaInicio = null;
				fechaFin = null;
				costo = 0;
				descripcionubicacion = "Descripción de la Ubicación";
				descripcionrecurso = "Descripción de Recurso";
				stock = "stock";
				capacidad = "capacidad";
				imagensala = "300.jpg";
				cantidad = "";
				sc = 0;
				sala = 0;
				idusr = null;
				te = 0;
				idusr = 0;
				smscor = "";
				correosadmin = "";
				Mensaje.crearMensajeINFO("Enviado correctamente al correo");
			} else if (estadoeven.equals("Pendiente")) {
				// limpiamos los datos
				nombre = "";
				descripcion = "";
				lugar = "";
				imagen = "300.jpg";
				fi = null;
				ff = null;
				descripcionubicacion = "Descripción de la Ubicación";
				descripcionrecurso = "Descripción de Recurso";
				stock = "stock";
				capacidad = "capacidad";
				imagensala = "300.jpg";
				fechaInicio = null;
				fechaFin = null;
				costo = 0;
				cantidad = "";
				sc = 0;
				sala = 0;
				idusr = null;
				te = 0;
				idusr = 0;
				smscor = "";
				correosadmin = "";
				Mensaje.crearMensajeINFO("Evento Pendiente indique si Activa o Desactiva");
			} else if (sms.equals("Notificado")) {
				// limpiamos los datos
				nombre = "";
				descripcion = "";
				lugar = "";
				imagen = "300.jpg";
				fi = null;
				ff = null;
				fechaInicio = null;
				fechaFin = null;
				descripcionubicacion = "Descripción de la Ubicación";
				descripcionrecurso = "Descripción de Recurso";
				stock = "stock";
				capacidad = "capacidad";
				imagensala = "300.jpg";
				costo = 0;
				cantidad = "";
				sc = 0;
				sala = 0;
				idusr = null;
				te = 0;
				idusr = 0;
				smscor = "";
				correosadmin = "";
				Mensaje.crearMensajeINFO("Ya se envió el mensaje");
			}

		} catch (Exception e) {
			Mensaje.crearMensajeWARN("Error al enviar correo");
		}
		return "";
	}

	// Envia el mensaje de cancelacion de evento
	public String enviarmensaje(Evento ev) {
		try {
			if (estadoeven.equals("Activado")) {
				getcorreosusua();
				mb.envioMailWS(session.getCorreo(),
						"Cancelación Evento/REGECE ", smscor);
				mb.envioMailWS(correosadmin, "Cancelación Evento/REGECE ",
						smscor);
				// limpiamos los datos notificaciones.inno@gmail.com
				// innopolisyachay2015@gmail.com
				idEvento = 0;
				nombre = "";
				descripcion = "";
				lugar = "";
				imagen = "300.jpg";
				descripcionubicacion = "Descripción de la Ubicación";
				descripcionrecurso = "Descripción de Recurso";
				stock = "stock";
				capacidad = "capacidad";
				imagensala = "300.jpg";
				fi = null;
				ff = null;
				fechaInicio = null;
				fechaFin = null;
				costo = 0;
				cantidad = "";
				sc = 0;
				te = 0;
				sala = 0;
				idusr = 0;
				smscor = "";
				correosadmin = "";
				Mensaje.crearMensajeINFO("Enviado correctamente al correo");
			} else {
				// limpiamos los datos
				nombre = "";
				descripcion = "";
				lugar = "";
				imagen = "300.jpg";
				fi = null;
				ff = null;
				descripcionubicacion = "Descripción de la Ubicación";
				descripcionrecurso = "Descripción de Recurso";
				stock = "stock";
				capacidad = "capacidad";
				imagensala = "300.jpg";
				fechaInicio = null;
				fechaFin = null;
				costo = 0;
				cantidad = "";
				sc = 0;
				sala = 0;
				te = 0;
				idusr = 0;
				smscor = "";
				correosadmin = "";
				Mensaje.crearMensajeWARN("Se encuentra Desactivado/Pendiente el Evento");
			}
		} catch (Exception e) {
			Mensaje.crearMensajeWARN("Error al enviar correo");
		}
		return "";
	}

	// metodo para listar correos de ususariosadmin
	public String getcorreosusua() {
		try {
			List<Usuario> a = manager.findUsrsPrincipal();
			correosadmin = "";
			for (Usuario u : a) {
				correosadmin += u.getCorreo() + ",";
			}
			int max = correosadmin.length();
			correosadmin = correosadmin.substring(0, max - 1).trim();
			System.out.println(correosadmin);
		} catch (Exception e) {
			Mensaje.crearMensajeWARN("No se encuentran usuarios administradores");
			e.printStackTrace();
		}
		return correosadmin;
	}

	public String irAprovador1() {
		nombre = "";
		descripcion = "";
		lugar = "";
		imagen = "300.jpg";
		fi = null;
		ff = null;
		fechaInicio = null;
		fechaFin = null;
		costo = 0;
		descripcionubicacion = "Descripción de la Ubicación";
		descripcionrecurso = "Descripción de Recurso";
		stock = "stock";
		capacidad = "capacidad";
		imagensala = "300.jpg";
		cantidad = "";
		sc = 0;
		te = 0;
		listDetalles = new ArrayList<Solicidetalle>();
		idusr = 0;
		sala = 0;
		smscor = "";
		correosadmin = "";
		Mensaje.crearMensajeWARN("Envío cancelado");
		return "soleven";
	}

	public String irAprovador2() {
		nombre = "";
		descripcion = "";
		lugar = "";
		imagen = "300.jpg";
		fi = null;
		ff = null;
		fechaInicio = null;
		fechaFin = null;
		costo = 0;
		cantidad = "";
		descripcionubicacion = "Descripción de la Ubicación";
		descripcionrecurso = "Descripción de Recurso";
		stock = "stock";
		capacidad = "capacidad";
		imagensala = "300.jpg";
		sc = 0;
		listDetalles = new ArrayList<Solicidetalle>();
		te = 0;
		idusr = 0;
		sala = 0;
		smscor = "";
		correosadmin = "";
		Mensaje.crearMensajeWARN("Envío cancelado");
		return "eventos";
	}

	public String irvolver() {
		String r = "soleven?faces-redirect=true";
		nombre = "";
		descripcion = "";
		lugar = "";
		imagen = "300.jpg";
		descripcionubicacion = "Descripción de la Ubicación";
		descripcionrecurso = "Descripción de Recurso";
		stock = "stock";
		capacidad = "capacidad";
		imagensala = "300.jpg";
		fi = null;
		ff = null;
		fechaInicio = null;
		fechaFin = null;
		costo = 0;
		cantidad = "";
		sc = 0;
		te = 0;
		idusr = 0;
		sala = 0;
		smscor = "";
		correosadmin = "";
		listDetalles = new ArrayList<Solicidetalle>();
		setId_recurso(-1);
		setActividad("");
		setObjetivo("");
		setRecursofecha(null);
		h_inicio = new Timestamp(new Date().getTime());
		h_fin = new Timestamp(new Date().getTime());
		select = new ArrayList<SelectItem>();
		select2 = new ArrayList<SelectItem>();
		descripcionubicacion = "Descripción de la Ubicación";
		descripcionrecurso = "Descripción de Recurso";
		stock = "stock";
		imagen = "300.jpg";
		solicitudCabTem = null;
		return r;
	}

	public void mostrara() {
		Sala buscarsala;
		try {
			buscarsala = mReserv.findSalaByID(sala);
			descripcionubicacion = buscarsala.getDescripcion();
			imagensala = buscarsala.getImagen();
			capacidad = "capacidad: " + buscarsala.getCapacidad().toString();
			mostrar = true;
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void mostrarb() {
		Recurso rec;
		try {
			rec = mReserv.findRecursoByID(id_recurso);
			descripcionrecurso = rec.getDescripcion();
			contador = mReserv.findContadorRecurso(h_inicio, h_fin,
					rec.getIdRecurso());
			stock = "En stock: " + contador;
			imagen = rec.getImagen();
			mostrar = true;
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void ireve1() {
		if (agregarcontrol == false) {
			nombre = "";
			descripcion = "";
			lugar = "";
			imagen = "300.jpg";
			descripcionubicacion = "Descripción de la Ubicación";
			imagensala = "300.jpg";
			fi = null;
			ff = null;
			fechaInicio = null;
			fechaFin = null;
			costo = 0;
			cantidad = "";
			sc = 0;
			capacidad = "capacidad";
			te = 0;
			idusr = 0;
			sala = 0;
			esave = false;
			smscor = "";
			correosadmin = "";
			listDetalles = new ArrayList<Solicidetalle>();
			setId_recurso(-1);
			setActividad("");
			setObjetivo("");
			setRecursofecha(null);
			h_inicio = new Timestamp(new Date().getTime());
			h_fin = new Timestamp(new Date().getTime());
			select = new ArrayList<SelectItem>();
			select2 = new ArrayList<SelectItem>();
			descripcionubicacion = "Descripción de la Ubicación";
			stock = "stock";
			imagen = "300.jpg";
			solicitudCabTem = null;
		} else {
			Mensaje.crearMensajeWARN("Debe dar click en actualizar");
		}
	}

	public String ireve5() {
		nombre = "";
		descripcion = "";
		lugar = "";
		imagen = "300.jpg";
		descripcionubicacion = "Descripción de la Ubicación";
		stock = "stock";
		imagensala = "300.jpg";
		h_inicio = new Timestamp(new Date().getTime());
		h_fin = new Timestamp(new Date().getTime());
		fechaInicio = null;
		fechaFin = null;
		costo = 0;
		cantidad = "";
		sc = 0;
		te = 0;
		idusr = 0;
		sala = 0;
		esave = false;
		smscor = "";
		correosadmin = "";
		capacidad = "capacidad";
		setId_recurso(-1);
		setActividad("");
		setObjetivo("");
		setRecursofecha(null);
		h_inicio = new Timestamp(new Date().getTime());
		h_fin = new Timestamp(new Date().getTime());
		select = new ArrayList<SelectItem>();
		select2 = new ArrayList<SelectItem>();
		listDetalles = new ArrayList<Solicidetalle>();
		descripcionubicacion = "Descripción de la Ubicación";
		imagen = "300.jpg";
		solicitudCabTem = null;
		return "eventos?faces-redirect=true";
	}

	// salas
	// metodo para mostrar los EventosTipos en Eventos
	public List<SelectItem> getListaSala() {
		List<SelectItem> listadoSI = new ArrayList<SelectItem>();
		List<Sala> sala = mEvento.findAllSalas();
		for (Sala t : sala) {
			SelectItem item = new SelectItem(t.getIdSala(), t.getTipo());
			listadoSI.add(item);
		}
		return listadoSI;
	}

	// metodo para asignar el TipoEvento al Evento
	public void asignarsala() {
		mEvento.asignarSala(sala);
	}

	// Agregar quitar detalle
	public void agregarDetalle() {
		try {
			if (id_recurso == null || id_recurso == -1)
				Mensaje.crearMensajeWARN("Seleccione recurso adicional");
			else if (esRecursoAnadido(id_recurso, h_inicio, h_fin))
				Mensaje.crearMensajeWARN("El recurso se encuentra agregado dentro del horario");
			else {
				Recurso rec = mReserv.findRecursoByID(getId_recurso());
				Solicidetalle det = new Solicidetalle();
				det.setSolicicabecera(mReserv
						.findSolicitudCabeceraById(getId_sol()));
				det.setCapacidad(getcapacidad_recurso());
				det.setHoraInicio(h_inicio);
				det.setHoraFin(h_fin);
				det.setRecurso(rec);
				mReserv.insertarSoliciDatalle(det);
				mReserv.insertarRecursoSolicitado(mReserv
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

	// Recurso ya añadido
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

	// quitar detalle de la lista como recurso activo
	public void quitarDetalle(Solicidetalle detalle) {
		try {
			List<Recursosactivo> listrecact = mReserv
					.findRecursosactivoByRecursoId(detalle.getSolicicabecera()
							.getIdSolcab(), detalle.getRecurso().getIdRecurso());
			for (Recursosactivo recursosactivo : listrecact) {
				mReserv.eliminarRecursoActivos(recursosactivo.getIdRecact());
			}
			// mReserv.quitarDetalleSolicitudTem(detalle);
			mReserv.eliminarSoliciDetalleByID(detalle.getIdSoldet());
			listDetalles.remove(detalle);
			cargarRecursos();
			Mensaje.crearMensajeINFO("Se eliminó el recurso");
			agregarcontrol = true;
		} catch (Exception e) {
			Mensaje.crearMensajeWARN("No se pudo quitar el recurso");
		}
	}

	// guardar detalles
	public String finalizarSolicitudED() {
		String resp = "";
		try {
			if (!listDetalles.isEmpty()) {
				descripcionrecurso = "Descripción de Recurso";
				id_sol = null;
				stock = "stock";
				capacidad = "capacidad";
				imagen = "300.jpg";
				imagensala = "300.jpg";
				imgMost = "300.jpg";
				imagentipo = "300.jpg";
				actividad = "";
				objetivo = "";
				justificacion = "";
				fechaFin = null;
				fi = null;
				ff = null;
				fechaInicio = null;
				notificacion = "";
				listDetalles = new ArrayList<Solicidetalle>();
				estadoSol = null;
				select = new ArrayList<SelectItem>();
				select2 = new ArrayList<SelectItem>();
				capacidad_recurso = null;
				list_mas = new ArrayList<Solicidetalle>();
				agregarcontrol = false;
				if (solivalor == true) {
					resp = "eventos?faces-redirect=true";
				} else {
					resp = "soleven?faces-redirect=true";
				}
				Mensaje.crearMensajeINFO("Edición correcta");
			} else {
				Mensaje.crearMensajeWARN("La solicitud debe contener por lo menos un recurso");
			}
		} catch (Exception e) {
			Mensaje.crearMensajeWARN("Edición errónea");
		}
		return resp;
	}

	// guardar detalles
	public String finalizarSolicitudEDadmin() {
		String resp = "";
		try {
			mReserv.editarDetallesSolicitud(id_sol, list_mas, list_menos);
			descripcionrecurso = "Descripción de Recurso";
			stock = "stock";
			imagen = "300.jpg";
			imagensala = "300.jpg";
			capacidad = "capacidad";
			imgMost = "300.jpg";
			imagentipo = "300.jpg";
			resp = "eventos";
		} catch (Exception e) {
			Mensaje.crearMensajeWARN("Edición errónea");
		}
		return resp;
	}

	// CARGAR toods las salas LIBRES
	public void todaslasalas() {
		try {
			mReserv.quitarsalasactivas();
			cargarSalas();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// CARGAR RECURSOS LIBRES
	public void cargarSalas() {
		h_inicio = new Timestamp(fi.getTime());
		h_fin = new Timestamp(ff.getTime());
		select.clear();
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
				select.clear();
				select = this.getlistaSalasLibres();
			}
		}
	}

	// LISTADO DE RECURS
	public List<SelectItem> getlistaSalasLibres() {
		List<SelectItem> listadoSI = new ArrayList<SelectItem>();
		List<Sala> listadoSalas = mReserv.findAllSalasDisponibles(h_inicio,h_fin);
		for (Sala p : listadoSalas) {
			SelectItem item = new SelectItem(p.getIdSala(), p.getTipo());
			listadoSI.add(item);
		}
		return listadoSI;
	}

	// Imprime un reporte de los datos de un contrato
	public void imprimirRptDocumento(Evento ev) {

		if (!ev.getEstado().equals("Pendiente")) {
			try {
				ServletContext servletContext = (ServletContext) FacesContext
						.getCurrentInstance().getExternalContext().getContext();
				String carpetaReportes = (String) servletContext
						.getRealPath(File.separatorChar + "reports");
				String rutaReporte = carpetaReportes + File.separatorChar
						+ "Imprimireventoreporte.jasper";
				Connection conexion = DriverManager
						.getConnection("jdbc:postgresql://10.1.0.158:5432/bd_inno?user=adm_bicichay&password=y-4IO4SDwu_!");
				Map<String, Object> parametros = new HashMap<String, Object>();
				System.out.println(carpetaReportes + File.separatorChar
						+ "yachay-logo1.png");
				System.out.println(ev.getIdEvento());
				parametros.put("pIdevento", ev.getIdEvento());
				parametros.put("pImagen", carpetaReportes + File.separatorChar
						+ "yachay-logo1.png");
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
				Mensaje.crearMensajeINFO("Se imprimió correctamente");
			} catch (Exception e) {
				Mensaje.crearMensajeWARN("Error al imprimir");
				e.printStackTrace();
			}
		} else {
			Mensaje.crearMensajeWARN("Aún no se aprueba o niega el evento");
		}
	}

	// metodo para asignar el Tipo al Usuario
	public String asignarRecurso() {
		mReserv.asignarRecurso(id_recurso);
		return "";
	}
}
