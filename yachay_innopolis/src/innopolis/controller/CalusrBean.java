package innopolis.controller;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Serializable;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import innopolis.entidades.Camposnuevo;
import innopolis.entidades.Evento;
import innopolis.entidades.Inscripcione;
import innopolis.entidades.Recurso;
import innopolis.entidades.Recursotipo;
import innopolis.entidades.Sala;
import innopolis.entidades.Solicicabecera;
import innopolis.entidades.Solicidetalle;
import innopolis.entidades.Tipoevento;
import innopolis.entidades.Usuario;
import innopolis.manager.ManagerBuscar;
import innopolis.manager.ManagerEvento;
import innopolis.manager.ManagerInscripedit;
import innopolis.manager.ManagerLogin;
import innopolis.manager.ManagerReservas;

import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;
import javax.servlet.ServletContext;

import org.primefaces.event.FileUploadEvent;
import org.primefaces.event.SelectEvent;
import org.primefaces.model.DefaultScheduleEvent;
import org.primefaces.model.DefaultScheduleModel;
import org.primefaces.model.ScheduleEvent;
import org.primefaces.model.ScheduleModel;
import org.primefaces.model.UploadedFile;

@SessionScoped
@ManagedBean
public class CalusrBean implements Serializable {
	private static final long serialVersionUID = 1L;
	private ManagerEvento manager;
	ManagerReservas managerre;
	private ManagerLogin managerlog;
	
	@EJB
	private ManagerBuscar mb;

	private boolean interno;

	private Evento e;
	private ScheduleModel eventModel;
	private ScheduleEvent event = new DefaultScheduleEvent();

	// buscar por tipo evento
	private Integer te;

	// buscar por ubicacion
	private Integer sala;

	// buscar por recurso
	private Integer rec;

	// camposnuevos
	ManagerInscripedit managerins;

	private Integer id_campo;
	private String etiqueta;
	private String campo;

	// envio de correo a los administradores
	private String smscoradmin;
	private String smscorusu;
	private String correosadmin;

	private Timestamp hora_fin;

	public void setHora_fin(Timestamp hora_fin) {
		this.hora_fin = hora_fin;
	}

	public Timestamp getHora_fin() {
		return hora_fin;
	}

	/**
	 * @return the te
	 */
	public Integer getTe() {
		return te;
	}

	public boolean isInterno() {
		return interno;
	}

	public void setInterno(boolean interno) {
		this.interno = interno;
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
	 * @return the rec
	 */
	public Integer getRec() {
		return rec;
	}

	/**
	 * @param rec
	 *            the rec to set
	 */
	public void setRec(Integer rec) {
		this.rec = rec;
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

	// INSCRIPCIONES///
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
	private String Ubicacion;
	private UploadedFile file;
	private Integer id_evento;
	private List<Inscripcione> listadoInscripciones;

	public CalusrBean() {
		managerlog = new ManagerLogin();
		manager = new ManagerEvento();
		eventModel = new DefaultScheduleModel();
		managerins = new ManagerInscripedit();
		managerre = new ManagerReservas();
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
		return "index?faces-redirect=true";
	}

	public void onEventSelect(SelectEvent selectEvent) {
		event = (ScheduleEvent) selectEvent.getObject();

	}

	// /////////////////////////////Eventos///////////////////////////////////////////////
	// metodo para mostrar los EventosTipos en Eventos
	public List<SelectItem> getListaEveTipo() {
		List<SelectItem> listadoevetipo = new ArrayList<SelectItem>();
		List<Tipoevento> tipoevento = manager.findAllTipoEventos();
		for (Tipoevento t : tipoevento) {
			SelectItem item = new SelectItem(t.getIdTipoEvento(), t.getTipo());
			listadoevetipo.add(item);
		}
		return listadoevetipo;
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
						"Calendario actualizado", null));
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
						"Calendario actualizado", null));
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
						"Calendario actualizado", null));
		return "";
	}

	// ///////////////////////////////////////////////////////Recursos//////////////////////////////////////////////////////////////////////
	// metodo para mostrar los EventosTipos en Eventos
	public List<SelectItem> getListaRecursoTipo() {
		List<SelectItem> listadoSI = new ArrayList<SelectItem>();
		List<Recursotipo> tiporecurso = managerre.findAllTipoRecurso();
		for (Recursotipo t : tiporecurso) {
			SelectItem item = new SelectItem(t.getIdRectipo(), t.getTipo());
			listadoSI.add(item);
		}
		return listadoSI;
	}

	// metodo para asignar el TipoEvento al Evento
	public void asignarTiporec() {
		managerre.asignarRecurso(rec);
		eventModel = new DefaultScheduleModel();
		List<Evento> listado = mayorActual();
		List<Solicicabecera> solilist = managerre.findAllSolicitudCabecera();
		List<Solicidetalle> solidetlist = managerre.findAllDetallesSolicitud();
		List<Recurso> recursolist = managerre.findAllRecurso();
		List<Recursotipo> recursotipolist = managerre.findAllTipoRecurso();

		System.out.print("tam " + listado.size());
		for (Evento e : listado) {
			if (e.getEstado().equals("Activado")) {
				event = new DefaultScheduleEvent(e.getNombre(),
						e.getFechaInicio(), e.getFechaFin(), e);
				System.out.print("tam " + solilist.size());

				for (Solicicabecera s : solilist) {
					if (e.getSolicicabecera().equals(s.getIdSolcab())) {
						for (Solicidetalle sd : solidetlist) {
							if (s.getSolicidetalles().equals(sd.getIdSoldet())) {
								for (Recurso rec : recursolist) {
									if (sd.getRecurso().equals(
											rec.getIdRecurso())) {
										for (Recursotipo rectipo : recursotipolist) {
											if (rec.getRecursotipo().equals(
													rectipo.getIdRectipo())) {
												((DefaultScheduleEvent) event)
														.setStyleClass(rectipo
																.getColorrec()
																.getColor());// dependiendo
																				// del
																				// to
																				// cambio
																				// de
																				// style,
																				// algo1,algo2
												eventModel.addEvent(event);
											}
										}
									}
								}
							}
						}
					}

				}

			} else if (rec == 0) {
				refresh();
			}
		}
		FacesContext.getCurrentInstance().addMessage(
				null,
				new FacesMessage(FacesMessage.SEVERITY_INFO,
						"Calendario actualizado", null));
	}

	// escoger el tipo de evento a mostrar
	public String actucalendarec() {
		eventModel = new DefaultScheduleModel();
		List<Evento> listado = mayorActual();
		System.out.print("tam " + listado.size());
		for (Evento e : listado) {
			if (e.getEstado().equals("Activado")) {
				if (e.getTipoevento().getIdTipoEvento() == te) {
					event = new DefaultScheduleEvent(e.getNombre(),
							e.getFechaInicio(), e.getFechaFin(), e);
					// ((DefaultScheduleEvent)
					// event).setStyleClass(e.getTipoevento().getColoreve().getColor());//dependiendo
					// del to cambio de style, algo1,algo2
					eventModel.addEvent(event);
				} else if (te == 0) {
					refresh();
				}
			}
		}
		FacesContext.getCurrentInstance().addMessage(
				null,
				new FacesMessage(FacesMessage.SEVERITY_INFO,
						"Calendario actualizado", null));
		return "";
	}

	// /////////////////////////////////////////////////////////////////INSCRIPCIONES
	// USUARIO///////////////////////////////////////////////////////////////
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

	/**
	 * @return the ubicacion
	 */
	public String getUbicacion() {
		return Ubicacion;
	}

	/**
	 * @param ubicacion
	 *            the ubicacion to set
	 */
	public void setUbicacion(String ubicacion) {
		Ubicacion = ubicacion;
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
		return "formulario?faces-redirect=true";
		// return "frm_ins?faces-redirect=true";
	}

	// IR A INSCRIPCION
	public String irInscripcion1(Evento ev) {
		String r = "";
		imagenPago = "sin_pago.jpg";
		if (ev.getInterno()) {
			FacesContext.getCurrentInstance().addMessage(
					null,
					new FacesMessage(FacesMessage.SEVERITY_WARN,
							"El evento es privado", null));
		} else if (!ev.getInterno()) {
			r = "formulario?faces-redirect=true";
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
				smscoradmin = "<!DOCTYPE html><html lang='es'><head><meta http-equiv='Content-Type' content='text/html; charset=utf-8' />"
						 + "<meta name='viewport' content='width=device-width'></head><body>"
						+ "El Sr/ra. "
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
						+ "<br/> Obervaci&oacute;n : " + getObservacion()+ ""
						+ "<br/> Saludos cordiales, "
				  	    + "<br/> Sistema de REGECE Yachay EP"
		                + "<br/><em><strong>NOTA:</strong> Este correo es generado automáticamente por el sistema favor no responder al mismo.</em></body></html>";
				
		        smscorusu = "<!DOCTYPE html><html lang='es'><head><meta http-equiv='Content-Type' content='text/html; charset=utf-8' />"
					    + "<meta name='viewport' content='width=device-width'></head><body>"
						+ "Sr/ra.  "
						+ getNombre()
						+ " "
						+ getApellido()
						+ ", su petici&oacute;n de solicitud de Inscripci&oacute;n para un Evento del sistema REGECE (Reservas de Espacios y Gesti&oacute;n de Eventos del Centro de Emprendimiento), ser&aacute; verificado por los administradores, espere al mensaje de confirmaci&oacute;n. <br/>"
						+ "Sus datos de Inscripci&oacute;n son:"
						+ "<br/> Nombre: " + getNombre() + ""
						+ "<br/> Apellido: " + getApellido() + ""
						+ "<br/> Nombre del Evento: " + getEvento().getNombre()
						+ "" + "<br/> Fecha de Inscripci&oacute;n: "
						+ date.format(getFechaInscripcion()).toString() + ""
						+ "<br/> Obervaci&oacute;n : " + getObservacion()+""
						+ "<br/> Saludos cordiales, "
				  	    + "<br/> Sistema de REGECE Yachay EP"
		                + "<br/><em><strong>NOTA:</strong> Este correo es generado automáticamente por el sistema favor no responder al mismo.</em></body></html>";;

				getcorreosusu();
				System.out.println(correosadmin);
				
//				Mail.generateAndSendEmail(correosadmin,
//						"Notificación de YACHAY/REGECE  ", smscoradmin);
//				Mail.sendMailsolousr(getCorreo(),
//						"Notificación de YACHAY/REGECE  ", smscorusu);

				mb.envioMailWS(correosadmin, "Notificación de YACHAY/REGECE", smscoradmin);
				mb.envioMailWS(getCorreo(), "Notificación de YACHAY/REGECE", smscorusu);

				correosadmin = "";
				smscoradmin = "";
				smscorusu = "";

				// managerins.insertarcampos(etiqueta, campo);
				setNombre("");
				setApellido("");
				setCorreo("");
				setObservacion("");
				setImagenPago("sin_pago.jpg");
				campo = "";
				resp = "index?faces-redirect=true";// Enviar a un resumen de
													// inscripcion o pagina de
													// exito
				FacesContext
						.getCurrentInstance()
						.addMessage(
								null,
								new FacesMessage(FacesMessage.SEVERITY_INFO,
										"Ingresado",
										"Se registro correctamente, espere el mensaje de confirmación"));
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
			context.addMessage(null, new FacesMessage("No se encuentran usuarios administradores",null));
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
//				String carpetaImagenes = (String) servletContext
//						.getRealPath(File.separatorChar + "imgevent");
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

				FacesContext.getCurrentInstance().addMessage(
						null,
						new FacesMessage(FacesMessage.SEVERITY_INFO,
								"Correcto: carga correcta",null));

			} catch (Exception e) {
				FacesContext.getCurrentInstance().addMessage(
						null,
						new FacesMessage(FacesMessage.SEVERITY_ERROR, "No se pudo subir la imagen",null));
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
					new FacesMessage(FacesMessage.SEVERITY_ERROR, "No se pudo seleccionar la imagen",null));
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

	public String ircontraseña() {
		return "recu_contra?faces-redirect=true";
	}

	// metodo para listar los eventos
	public List<Evento> getListRegEventos() {
		return manager.findAllEventos();
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
}
