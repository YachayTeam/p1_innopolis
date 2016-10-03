package innopolis.controller;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Serializable;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import innopolis.entidades.Camposnuevo;
import innopolis.entidades.Evento;
import innopolis.entidades.Inscripcione;
import innopolis.entidades.Sala;
import innopolis.entidades.Tipoevento;
import innopolis.entidades.Usuario;
import innopolis.entidades.help.UsuarioHelp;
import innopolis.manager.ManagerBuscar;
import innopolis.manager.ManagerEvento;
import innopolis.manager.ManagerInscripedit;
import innopolis.manager.ManagerLogin;
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
import org.primefaces.event.SelectEvent;
import org.primefaces.model.DefaultScheduleEvent;
import org.primefaces.model.DefaultScheduleModel;
import org.primefaces.model.ScheduleEvent;
import org.primefaces.model.ScheduleModel;
import org.primefaces.model.UploadedFile;

@SessionScoped
@ManagedBean
public class CalusrBeanlogeado implements Serializable {
	private static final long serialVersionUID = 1L;
	private ManagerEvento manager;
	private ManagerLogin managerlog;

	@EJB
	private ManagerBuscar mb;

	private boolean interno;

	private Evento e;
	private ScheduleModel eventModel;
	private ScheduleEvent event = new DefaultScheduleEvent();

	// camposnuevos
	ManagerInscripedit managerins;

	private Integer id_campo;
	private String etiqueta;
	private String campo;

	// buscar por tipo evento
	private Integer te;

	// buscar por ubicacion
	private Integer sala;

	// envio de correo a los administradores
	private String smscoradmin;
	private String smscorusu;
	private String correosadmin;

	// calendario
	private Date date;
	private Date fi;
	private Date ff;

	private Timestamp hora_fin;

	public void setHora_fin(Timestamp hora_fin) {
		this.hora_fin = hora_fin;
	}

	public Timestamp getHora_fin() {
		return hora_fin;
	}

	public Date getDate() {
		sala = 0;
		date = new Date();
		return date;
	}

	public boolean isInterno() {
		return interno;
	}

	public void setInterno(boolean interno) {
		this.interno = interno;
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
	 * @return the te
	 */
	public Integer getTe() {
		return te;
	}

	/**
	 * @param te
	 *            the te to set
	 */
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

	// INSCRIPCIONES usuario no logeado///
	private Integer idInscripcion;
	private String apellido;
	private String correo;
	private String direccion;
	private String telefono;
	private String celular;
	private Integer estado;
	private Timestamp fechaInscripcion;
	private Integer idUsuario;
	private String imagenPago;
	private String nombre;
	private String observacion;
	private Evento evento;
	private UploadedFile file;
	private Integer id_evento;
	private List<Inscripcione> listadoInscripciones;
	private String imagen;

	private UsuarioHelp session;

	public CalusrBeanlogeado() {
		try {
			managerlog = new ManagerLogin();
			session = SessionBean.verificarSession();
			nombre = session.getNombre();
			apellido = session.getApellido();
			correo = session.getCorreo();
			direccion = session.getDireccion();
			telefono = session.getTelefono();
			celular = session.getCelular();
			manager = new ManagerEvento();
			eventModel = new DefaultScheduleModel();
			managerins = new ManagerInscripedit();
			imagen = "300.jpg";
			List<Evento> listado = mayorActual();
			for (Evento e : listado) {
				if (e.getEstado().equals("Activado")) {
					Usuario u = managerlog.findususarioByID(session.getIdUsr());
					System.out.println("tipo de usuario"
							+ u.getTipo().getIdTipo());
					if (u.getIdUsr() == e.getUsuario().getIdUsr()
							|| u.getTipo().getIdTipo() == 4) {
						event = new DefaultScheduleEvent(e.getNombre(),
								e.getFechaInicio(), e.getFechaFin(), e);
						((DefaultScheduleEvent) event).setStyleClass(e
								.getSala().getColorsala().getColor());
						eventModel.addEvent(event);
					} else if (e.getInterno()) {
						event = new DefaultScheduleEvent("Reunión Privada",
								e.getFechaInicio(), e.getFechaFin(), e);
						((DefaultScheduleEvent) event).setStyleClass(e
								.getSala().getColorsala().getColor());
						eventModel.addEvent(event);
					} else if (!e.getInterno()) {
						event = new DefaultScheduleEvent(e.getNombre(),
								e.getFechaInicio(), e.getFechaFin(), e);
						((DefaultScheduleEvent) event).setStyleClass(e
								.getSala().getColorsala().getColor());
						eventModel.addEvent(event);
					}
				} else {
					System.out.println("no hay datos");
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public String valorInterno(Evento ev) {
		Usuario u;
		String nombre = "";
		try {
			u = managerlog.findususarioByID(session.getIdUsr());
			if (u.getIdUsr() == ev.getUsuario().getIdUsr()
					|| u.getTipo().getIdTipo() == 4) {
				nombre = ev.getNombre();
			} else if (ev.getInterno()) {
				nombre = "Reunión Privada";
			} else if (!ev.getInterno()) {
				nombre = ev.getNombre();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return nombre;
	}

	// ////////////
	// metodo para mostrar los EventosTipos en Eventos
	public List<SelectItem> getListaEveTipo() {
		List<SelectItem> listadoSI = new ArrayList<SelectItem>();
		List<Tipoevento> tipoevento = manager.findAllTipoEventos();
		for (Tipoevento t : tipoevento) {
			SelectItem item = new SelectItem(t.getIdTipoEvento(), t.getTipo());
			listadoSI.add(item);
		}
		return listadoSI;
	}

	// metodo para mostrar sala en Eventos
	public List<SelectItem> getListaUbicacion() {
		List<SelectItem> listadoSI = new ArrayList<SelectItem>();
		List<Sala> sala = manager.findAllSalas();
		for (Sala t : sala) {
			SelectItem item = new SelectItem(t.getIdSala(), t.getTipo());
			listadoSI.add(item);
		}
		return listadoSI;
	}

	// metodo para asignar el TipoEvento al Evento
	public void asignarTipoeve() {
		manager.asignarTipoevento(te);
		eventModel = new DefaultScheduleModel();
		List<Evento> listado = mayorActual();
		System.out.print("tam " + listado.size());
		for (Evento e : listado) {
			if (e.getEstado().equals("Activado")) {
				if (e.getTipoevento().getIdTipoEvento() == te) {
					event = new DefaultScheduleEvent(e.getNombre(),
							e.getFechaInicio(), e.getFechaFin(), e);
					((DefaultScheduleEvent) event).setStyleClass(e.getSala()
							.getColorsala().getColor());
					eventModel.addEvent(event);
				} else if (te == 0) {
					refresh();
				}
			}
		}
		Mensaje.crearMensajeINFO("Calendario Actualizado");
	}

	// metodo para asignar el TipoEvento al Evento
	public void asignarsala() {
		manager.asignarSala(sala);
		eventModel = new DefaultScheduleModel();
		List<Evento> listado = mayorActual();
		System.out.print("tam " + listado.size());
		for (Evento e : listado) {
			if (e.getEstado().equals("Activado")) {
				if (e.getSala().getIdSala() == sala) {
					event = new DefaultScheduleEvent(e.getNombre(),
							e.getFechaInicio(), e.getFechaFin(), e);
					((DefaultScheduleEvent) event).setStyleClass(e.getSala()
							.getColorsala().getColor());
					eventModel.addEvent(event);
				} else if (sala == 0) {
					refresh();
				}
			}
		}
		Mensaje.crearMensajeINFO("Calendario Actualizado");
	}

	// metodo para asignar el TipoEvento al Evento
	public void asignarsalaimp() {
		manager.asignarSala(sala);
	}

	// escoger el tipo de evento a mostrar
	public String actucalenda() {
		eventModel = new DefaultScheduleModel();
		List<Evento> listado = mayorActual();
		System.out.print("tam " + listado.size());
		for (Evento e : listado) {
			if (e.getEstado().equals("Activado")) {
				if (e.getTipoevento().getIdTipoEvento() == te) {
					event = new DefaultScheduleEvent(e.getNombre(),
							e.getFechaInicio(), e.getFechaFin(), e);
					((DefaultScheduleEvent) event).setStyleClass(e.getSala()
							.getColorsala().getColor());
					eventModel.addEvent(event);
				} else if (te == 0) {
					refresh();
				}
			}
		}
		Mensaje.crearMensajeINFO("Calendario Actualizado");
		return "";
	}

	public ScheduleModel getEventModel() {
		return eventModel;
	}

	public void setEventModel(ScheduleModel eventModel) {
		this.eventModel = eventModel;
	}

	public ScheduleEvent getEvent() {
		return event;
	}

	public void setEvent(ScheduleEvent event) {
		this.event = event;
	}

	public Evento getE() {
		return e;
	}

	public void setE(Evento e) {
		this.e = e;
	}

	public List<Evento> getListEvento() {
		return manager.findAllEventos();
	}

	/* SESSION */
	public UsuarioHelp getSession() {
		return session;
	}

	// ////////////////////////////////////////////////////////////CALENDARIO//////////////////////////////////////////////////////////////////

	public List<Evento> mayorActual() {
		List<Evento> le = new ArrayList<Evento>();
		Date date = new Date();
		Timestamp fecha_actual = new Timestamp(date.getTime());
		for (Evento e : getListEvento()) {
			if (e.getFechaInicio().after(fecha_actual)
					|| e.getFechaFin().after(fecha_actual)
					|| e.getFechaInicio().compareTo(fecha_actual) == 0) {
				le.add(e);
			}
		}
		return le;
	}

	public String refresh() {
		eventModel = new DefaultScheduleModel();
		List<Evento> listado = mayorActual();
		System.out.print("tam " + listado.size());
		for (Evento e : listado) {
			if (e.getEstado().equals("Activado")) {
				event = new DefaultScheduleEvent(e.getNombre(),
						e.getFechaInicio(), e.getFechaFin(), e);
				((DefaultScheduleEvent) event).setStyleClass(e.getSala()
						.getColorsala().getColor());
				eventModel.addEvent(event);
			} else {
				System.out.println("no hay datos");
			}
		}
		return "calendario?faces-redirect=true";
	}

	public void onEventSelect(SelectEvent selectEvent) {
		event = (ScheduleEvent) selectEvent.getObject();
	}

	// /////////////////////////////////////////////////////////////////INSCRIPCIONES
	// USUARIO SIN
	// LOGEARSE///////////////////////////////////////////////////////////////
	public Integer getIdInscripcion() {
		return idInscripcion;
	}

	public void setIdInscripcion(Integer idInscripcion) {
		this.idInscripcion = idInscripcion;
	}

	public String getImagen() {
		return imagen;
	}

	public void setImagen(String imagen) {
		this.imagen = imagen;
	}

	public String getApellido() {
		return apellido;
	}

	public void setApellido(String apellido) {
		this.apellido = apellido;
	}

	public String getCorreo() {
		return correo;
	}

	public void setCorreo(String correo) {
		this.correo = correo;
	}

	public Integer getEstado() {
		return estado;
	}

	public void setEstado(Integer estado) {
		this.estado = estado;
	}

	public Timestamp getFechaInscripcion() {
		return fechaInscripcion;
	}

	public void setFechaInscripcion(Timestamp fechaInscripcion) {
		this.fechaInscripcion = fechaInscripcion;
	}

	public Integer getIdUsuario() {
		return idUsuario;
	}

	public void setIdUsuario(Integer idUsuario) {
		this.idUsuario = idUsuario;
	}

	public String getImagenPago() {
		return imagenPago;
	}

	public void setImagenPago(String imagenPago) {
		this.imagenPago = imagenPago;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getObservacion() {
		return observacion;
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

	public void setObservacion(String observacion) {
		this.observacion = observacion;
	}

	public Evento getEvento() {
		return evento;
	}

	public Integer getId_evento() {
		return id_evento;
	}

	public void setId_evento(Integer id_evento) {
		this.id_evento = id_evento;
	}

	public List<Inscripcione> getListadoInscripciones() {
		listadoInscripciones = manager.findAllInscripciones();
		return listadoInscripciones;
	}

	public UploadedFile getFile() {
		return file;
	}

	public void setFile(UploadedFile file) {
		this.file = file;
	}

	// editar imagen
	public void changeImg(Inscripcione ins) {
		setImagenPago(ins.getImagenPago());
		;
	}

	// IR A INSCRIPCION
	public String irInscripcion() {
		String r = "";
		imagenPago = "sin_pago.jpg";
		evento = (Evento) event.getData();
		if (evento.getInterno()) {
			Mensaje.crearMensajeWARN("El evento es privado");
		} else if (!evento.getInterno()) {
			if (manager.superaInscritosEvento(evento)) {
				r = "formulariousrlog?faces-redirect=true";
			} else {
				Mensaje.crearMensajeINFO("El número de inscritos esta completo");
			}
		}
		return r;
	}

	public String irInscripcion1(Evento ev) {
		String r = "";
		imagenPago = "sin_pago.jpg";
		if (ev.getInterno()) {
			Mensaje.crearMensajeWARN("El evento es privado");
		} else if (!ev.getInterno()) {
			if (manager.superaInscritosEvento(ev)) {
				r = "formulariousrlog?faces-redirect=true";
			} else {
				Mensaje.crearMensajeINFO("El número de inscritos esta completo");
			}
		}
		return r;
	}

	public String inscribirse() {
		String resp = "";
		if (getObservacion() == null) {
			setObservacion("sin observación");
		}
		if (getEvento().getCosto() > 0
				&& getImagenPago().equals("sin_pago.jpg")) {
			Mensaje.crearMensajeWARN("El evento posee pago y necesita imagen del comprobante");
		} else {
			try {
				if(manager.findUsuarioExisteById(session.getIdUsr()).size() == 0){
				// FECHA Y HORA ACTUAL
				Calendar fecha_hora = Calendar.getInstance();
				setFechaInscripcion(new Timestamp(fecha_hora.getTimeInMillis()));
				//Ingreso
				manager.insertarInscripcion(getEvento(), getFechaInscripcion(),
						session.getIdUsr(), getNombre().trim(), getApellido().trim(), getCorreo().trim(),
						getDireccion().trim(), getTelefono().trim(), getCelular().trim(),
						getImagenPago(), getObservacion().trim());
				DateFormat date = new SimpleDateFormat("dd/MM/yyyy");
				smscoradmin = "<!DOCTYPE html><html lang='es'><head><meta http-equiv='Content-Type' content='text/html; charset=utf-8' />"
						+ "<meta name='viewport' content='width=device-width'></head><body>"
						+ "El Sr/ra. "
						+ getNombre()
						+ " "
						+ getApellido()
						+ ", envi&oacute; una solicitud de Inscripci&oacute;n para un Evento; Requiere la aprobaci&oacute;n o negaci&oacute;n.; <br/>"
						+ "Los datos del usuario son:"
						+ "<br/> Nombre: "
						+ getNombre()
						+ ""
						+ "<br/> Apellido: "
						+ getApellido()
						+ ""
						+ "<br/> Direcci&oacute;n : "
						+ getDireccion()
						+ ""
						+ "<br/> Tel&eacute;fono : "
						+ getTelefono()
						+ ""
						+ "<br/> Cedula : "
						+ getCelular()
						+ ""
						+ "<br/> Nombre del Evento: "
						+ getEvento().getNombre()
						+ ""
						+ "<br/> Fecha de Inscripci&oacute;n: "
						+ date.format(getFechaInscripcion()).toString()
						+ ""
						+ "<br/> Obervaci&oacute;n : "
						+ getObservacion()
						+ ""
						+ "<br/> Saludos cordiales, "
						+ "<br/> Sistema de REGECE Yachay EP"
						+ "<br/><em><strong>NOTA:</strong> Este correo es generado automáticamente por el sistema favor no responder al mismo.</em></body></html>";

				smscorusu = "<!DOCTYPE html><html lang='es'><head><meta http-equiv='Content-Type' content='text/html; charset=utf-8' />"
						+ "<meta name='viewport' content='width=device-width'></head><body>"
						+ "Sr/ra.  "
						+ getNombre()
						+ " "
						+ getApellido()
						+ ", su petici&oacute;n de solicitud de Inscripci&oacute;n para un Evento del sistema REGECE (Reservas de Espacios y Gesti&oacute;n de Eventos del Centro de Emprendimiento), ser&oacute; verificado por los administradores, espere al mensaje de confirmaci&oacute;n. <br/>"
						+ "Sus datos de Inscripci&oacute;n son:"
						+ "<br/> Nombre: "
						+ getNombre()
						+ ""
						+ "<br/> Direcci&oacute;n: "
						+ getDireccion()
						+ ""
						+ "<br/> Tel&eacute;fono: "
						+ getTelefono()
						+ ""
						+ "<br/> Celular: "
						+ getCelular()
						+ ""
						+ "<br/> Nombre del Evento: "
						+ getEvento().getNombre()
						+ ""
						+ "<br/> Fecha de Inscripci&oacute;n: "
						+ date.format(getFechaInscripcion()).toString()
						+ ""
						+ "<br/> Obervaci&oacute;n : "
						+ getObservacion()
						+ ""
						+ "<br/> Saludos cordiales, "
						+ "<br/> Sistema de REGECE Yachay EP"
						+ "<br/><em><strong>NOTA:</strong> Este correo es generado automáticamente por el sistema favor no responder al mismo.</em></body></html>";
				getcorreosusu();
				System.out.println(correosadmin);
				mb.envioMailWS(correosadmin, "Notificación de YACHAY/REGECE",
						smscoradmin);
				mb.envioMailWS(getCorreo(), "Notificación de YACHAY/REGECE",
						smscorusu);
				correosadmin = "";
				smscoradmin = "";
				smscorusu = "";
				setObservacion("");
				setImagenPago("sin_pago.jpg");
				campo = "";
				resp = "calendario?faces-redirect=true";
				}else{
					Mensaje.crearMensajeINFO("Yá esta registrado a este evento");
				}
			} catch (Exception e) {
				Mensaje.crearMensajeWARN("Error al intentar inscribirse al evento");
			}
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

	public String cancelarIns() {
		setNombre("");
		setApellido("");
		setCorreo("");
		setDireccion("");
		setTelefono("");
		setCelular("");
		setObservacion("");
		setImagenPago("sin_pago.jpg");
		return "index?faces-redirect=true";
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
				// AsignacionDeNombreImagen
				DateFormat dateFormat = new SimpleDateFormat("ddMMyyyyHHmm");
				int int_nom = (int) Math.floor(Math.random() * 9999 + 1);
				String nombre_img = "img_" + int_nom
						+ dateFormat.format(new Date()) + ".jpg";
				setImagenPago(nombre_img);

				System.out.println("PAD------> " + carpetaImagenes);
				System.out.println("name------> " + getImagenPago());

				outputStream = new FileOutputStream(new File(carpetaImagenes
						+ File.separatorChar + getImagenPago()));
				inputStream = file.getInputstream();

				int read = 0;
				byte[] bytes = new byte[1024];

				while ((read = inputStream.read(bytes)) != -1) {
					outputStream.write(bytes, 0, read);
				}
				Mensaje.crearMensajeINFO("Carga correcta");
			} catch (Exception e) {
				Mensaje.crearMensajeWARN("no se pudo subir la imagen");
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
			Mensaje.crearMensajeWARN("no se pudo seleccionar la imagen");
		}
	}

	// metodo para listar los campos
	public List<Camposnuevo> getListcamponuevo() {
		List<Camposnuevo> a = managerins.findAllcamposnuevo();
		List<Camposnuevo> l1 = new ArrayList<Camposnuevo>();
		for (Camposnuevo t : a) {
			l1.add(t);
		}
		return l1;
	}

	/**
	 * Imprime un reporte de los datos de un contrato
	 */
	public void imprimirRptDocumento() {
		try {
			ServletContext servletContext = (ServletContext) FacesContext
					.getCurrentInstance().getExternalContext().getContext();

			String carpetaReportes = (String) servletContext
					.getRealPath(File.separatorChar + "reports");
			String rutaReporte = "";
			Connection conexion = DriverManager
					.getConnection("jdbc:postgresql://10.1.0.158:5432/bd_inno?user=adm_bicichay&password=y-4IO4SDwu_!");
			Map<String, Object> parametros = new HashMap<String, Object>();
			parametros.put("pFechaInicio", getFi());
			parametros.put("pFechaFin", getFf());
			if (sala != 0 && getFi() != null && getFf() != null) {
				parametros.put("pSala", getSala());
				rutaReporte = carpetaReportes + File.separatorChar
						+ "reporteeventostodoscon.jasper";

			} else if (sala == 0 && getFi() == null && getFf() == null) {
				rutaReporte = carpetaReportes + File.separatorChar
						+ "reporteeventostodos.jasper";
				parametros.put("pSala", getSala());
			} else if (sala == 0 && getFi() != null && getFf() != null) {
				rutaReporte = carpetaReportes + File.separatorChar
						+ "reporteeventostodossin.jasper";
			}
			parametros.put("pImagen", carpetaReportes + File.separatorChar
					+ "yachay-logo1.png");
			JasperPrint informe = JasperFillManager.fillReport(rutaReporte,
					parametros, conexion);

			HttpServletResponse response = (HttpServletResponse) FacesContext
					.getCurrentInstance().getExternalContext().getResponse();
			response.addHeader("Content-disposition",
					"attachment; filename=jsfReporte.pdf");
			ServletOutputStream stream = response.getOutputStream();

			JasperExportManager.exportReportToPdfStream(informe, stream);

			stream.flush();
			stream.close();
			FacesContext.getCurrentInstance().responseComplete();
			Mensaje.crearMensajeINFO("Se imprimió correctamente");
			rutaReporte = "";
			sala = null;
			fi = null;
			ff = null;
		} catch (Exception e) {
			Mensaje.crearMensajeWARN("Error al imprimir");
			e.printStackTrace();
		}
	}

	public String cancelarImpresion() {
		fi = null;
		ff = null;
		sala = null;
		Mensaje.crearMensajeINFO("Impresión cancelada");
		return "calendario";
	}

	// metodo para listar los registros
	public List<Evento> getListEvenAct() {
		List<Evento> a = manager.findAllEventosOrdenados();
		List<Evento> l1 = new ArrayList<Evento>();
		for (Evento t : a) {
			if (t.getEstado().equals("Activado")){
				l1.add(t);
			}
		}
		return l1;
	}

	// editar imagen
	public void verImagen(Evento eve) {
		setImagen(eve.getImagen());
	}
}
