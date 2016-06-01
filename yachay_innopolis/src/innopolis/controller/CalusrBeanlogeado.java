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
import innopolis.manager.Mail;
import innopolis.manager.ManagerEvento;
import innopolis.manager.ManagerInscripedit;
import innopolis.manager.ManagerLogin;

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

	private UsuarioHelp session;

	public CalusrBeanlogeado() {
		managerlog = new ManagerLogin();
		session = SessionBean.verificarSession();
		nombre = session.getNombre();
		apellido = session.getApellido();
		correo = session.getCorreo();

		manager = new ManagerEvento();
		eventModel = new DefaultScheduleModel();
		managerins = new ManagerInscripedit();
		List<Evento> listado = mayorActual();
		for (Evento e : listado) {
			if (e.getEstado().equals("Activado")) {
				if (e.getInterno()) {
					event = new DefaultScheduleEvent("Reunión Privada",
							e.getFechaInicio(), e.getFechaFin(), e);
					((DefaultScheduleEvent) event).setStyleClass(e.getSala()
							.getColorsala().getColor());// dependiendo del to
														// cambio de style,
														// algo1,algo2
					eventModel.addEvent(event);
				} else if (!e.getInterno()) {
					event = new DefaultScheduleEvent(e.getNombre(),
							e.getFechaInicio(), e.getFechaFin(), e);
					((DefaultScheduleEvent) event).setStyleClass(e.getSala()
							.getColorsala().getColor());// dependiendo del to
														// cambio de style,
														// algo1,algo2
					eventModel.addEvent(event);
				}
			} else {
				System.out.println("no hay datos");
			}
		}
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
							.getColorsala().getColor());// dependiendo del to
														// cambio de style,
														// algo1,algo2
					eventModel.addEvent(event);
				} else if (te == 0) {
					refresh();
				}
			}
		}
		FacesContext.getCurrentInstance().addMessage(
				null,
				new FacesMessage(FacesMessage.SEVERITY_INFO,
						"Calendario Actualizado", null));
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
							.getColorsala().getColor());// dependiendo del to
														// cambio de style,
														// algo1,algo2
					eventModel.addEvent(event);
				} else if (sala == 0) {
					refresh();
				}
			}
		}
		FacesContext.getCurrentInstance().addMessage(
				null,
				new FacesMessage(FacesMessage.SEVERITY_INFO,
						"Calendario Actualizado", null));
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
							.getColorsala().getColor());// dependiendo del to
														// cambio de style,
														// algo1,algo2
					eventModel.addEvent(event);
				} else if (te == 0) {
					refresh();
				}
			}
		}
		FacesContext.getCurrentInstance().addMessage(
				null,
				new FacesMessage(FacesMessage.SEVERITY_INFO,
						"Calendario Actualizado", null));
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
						.getColorsala().getColor());// dependiendo del to cambio
													// de style, algo1,algo2
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
		imagenPago = "sin_pago.jpg";
		evento = (Evento) event.getData();
		return "formulariousrlog?faces-redirect=true";
		// return "frm_ins?faces-redirect=true";
	}

	public String irInscripcion1(Evento ev) {
		String r = "";
		imagenPago = "sin_pago.jpg";
		if (ev.getInterno()) {
			FacesContext.getCurrentInstance().addMessage(
					null,
					new FacesMessage(FacesMessage.SEVERITY_WARN,
							"El evento es privado", null));
		} else if (!ev.getInterno()) {
			r = "formulariousrlog?faces-redirect=true";
		}

		return r;
		// return "frm_ins?faces-redirect=true";
	}

	public String inscribirse() {
		String resp = "";
		/*
		 * if(getImagenPago()==null){ setImagenPago("sin_pago.jpg"); }
		 */
		if (getObservacion() == null) {
			setObservacion("sin observacion");
		}

		if (getEvento().getCosto() > 0
				&& getImagenPago().equals("sin_pago.jpg")) {
			FacesContext
					.getCurrentInstance()
					.addMessage(
							null,
							new FacesMessage(
									FacesMessage.SEVERITY_WARN,
									"El evento posee pago y necesita imagen del comprobante",
									null));
		} else {
			try {
				// FECHA Y HORA ACTUAL
				Calendar fecha_hora = Calendar.getInstance();
				setFechaInscripcion(new Timestamp(fecha_hora.getTimeInMillis()));
				// Ingreso
				manager.insertarInscripcion(getEvento(), getFechaInscripcion(),
						0, getNombre(), getApellido(), getCorreo(),
						getImagenPago(), getObservacion());

				DateFormat date = new SimpleDateFormat("dd/MM/yyyy");
				smscoradmin = "El Sr/ra. "
						+ getNombre()
						+ " "
						+ getApellido()
						+ ", envi&oacute; una solicitud de Inscripci&oacute;n para un Evento; Requiere la aprobaci&oacute;n o negaci&oacute;n.; <br/>"
						+ "Los datos del usuario son:" + "<br/> Nombre: "
						+ getNombre() + "" + "<br/> Apellido: " + getApellido()
						+ "" + "<br/> Nombre del Evento: "
						+ getEvento().getNombre() + ""
						+ "<br/> Fecha de Inscripci&oacute;n: "
						+ date.format(getFechaInscripcion()).toString() + ""
						+ "<br/> Obervaci&oacute;n : " + getObservacion();
				smscorusu = "Sr/ra.  "
						+ getNombre()
						+ " "
						+ getApellido()
						+ ", su petici&oacute;n de solicitud de Inscripci&oacute;n para un Evento del sistema REGECE (Reservas de Espacios y Gesti&oacute;n de Eventos del Centro de Emprendimiento), ser&oacute; verificado por los administradores, espere al mensaje de confirmaci&oacute;n. <br/>"
						+ "Sus datos de Inscripci&oacute;n son:"
						+ "<br/> Nombre: " + getNombre() + ""
						+ "<br/> Apellido: " + getApellido() + ""
						+ "<br/> Nombre del Evento: " + getEvento().getNombre()
						+ "" + "<br/> Fecha de Inscripci&oacute;n: "
						+ date.format(getFechaInscripcion()).toString() + ""
						+ "<br/> Obervaci&oacute;n : " + getObservacion();

				getcorreosusu();
				System.out.println(correosadmin);

				Mail.generateAndSendEmail(correosadmin,
						"Notificación de YACHAY/REGECE  ", smscoradmin);
				Mail.generateAndSendEmail(getCorreo(),
						"Notificación de YACHAY/REGECE  ", smscorusu);

				correosadmin = "";
				smscoradmin = "";
				smscorusu = "";

				// managerins.insertarcampos(etiqueta, campo);
				setObservacion("");
				setImagenPago("sin_pago.jpg");
				campo = "";
				resp = "calendario?faces-redirect=true";// Enviar a un resumen
														// de inscripcion o
														// pagina de exito
			} catch (Exception e) {
				FacesContext
						.getCurrentInstance()
						.addMessage(
								null,
								new FacesMessage(
										FacesMessage.SEVERITY_ERROR,
										"Error al intentar inscribirse al evento",
										null));
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
			FacesContext context = FacesContext.getCurrentInstance();
			context.addMessage(null, new FacesMessage("Error..!!!",
					"No se encuentran usuarios administradores"));
			e.printStackTrace();
		}
		return correosadmin;
	}

	public String cancelarIns() {
		setNombre("");
		setApellido("");
		setCorreo("");
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
				String carpetaImagenes = (String) servletContext
						.getRealPath(File.separatorChar + "imgevent");

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

				FacesContext.getCurrentInstance().addMessage(
						null,
						new FacesMessage(FacesMessage.SEVERITY_INFO,
								"Correcto:", "Carga correcta"));

			} catch (Exception e) {
				FacesContext.getCurrentInstance().addMessage(
						null,
						new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error:",
								"no se pudo subir la imagen"));
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
			FacesContext.getCurrentInstance().addMessage(
					null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error:",
							"no se pudo seleccionar la imagen"));
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
			if (getSala() != 0 && getFi() != null && getFf() != null) {
				System.out.println(rutaReporte
						+ "   entra a el if con los 3 parametros");
				parametros.put("pSala", getSala());
				rutaReporte = carpetaReportes + File.separatorChar
						+ "reporteeventostodoscon.jasper";
			} else {
				rutaReporte = carpetaReportes + File.separatorChar
						+ "reporteeventostodos.jasper";
				System.out.println(rutaReporte);
				parametros.put("pSala", getSala());
			}
			parametros.put("pImagen", carpetaReportes + File.separatorChar
					+ "yachay-logo1.png");
			// parametros.put("SUBREPORT_DIR",
			// carpetaReportes+File.separatorChar+"");
			System.out.println(rutaReporte);
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
			FacesContext.getCurrentInstance().addMessage(
					null,
					new FacesMessage(FacesMessage.SEVERITY_WARN,
							"Se imprimio correctamente.", null));
			rutaReporte = "";
			sala = null;
			fi = null;
			ff = null;
		} catch (Exception e) {
			FacesContext.getCurrentInstance().addMessage(
					null,
					new FacesMessage(FacesMessage.SEVERITY_WARN,
							"error al imprimir.", null));
			e.printStackTrace();
		}
	}

	public String cancelarImpresion() {
		fi = null;
		ff = null;
		sala = null;
		FacesContext.getCurrentInstance().addMessage(
				null,
				new FacesMessage(FacesMessage.SEVERITY_INFO,
						"Impresión Cancelada", null));
		return "calendario";
	}

	// metodo para listar los registros
	public List<Evento> getListEvenAct() {
		List<Evento> a = manager.findAllEventosOrdenados();
		List<Evento> l1 = new ArrayList<Evento>();
		Date date1 = new Date();
		for (Evento t : a) {
			hora_fin = new Timestamp(date1.getTime());
			if (t.getEstado().equals("Activado")
					&& t.getFechaFin().after(hora_fin)) {
				l1.add(t);
			}
		}
		return l1;
	}

	public String valorInterno(Evento ev) {
		if (ev.getInterno())
			return "Reunión Privada";
		else
			return ev.getNombre();
	}

	// /////////////////////////////////////////////////////////////////INSCRIPCIONES
	// USUARIO
	// LOGEADO///////////////////////////////////////////////////////////////

}
