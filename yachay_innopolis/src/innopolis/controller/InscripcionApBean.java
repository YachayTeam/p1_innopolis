package innopolis.controller;

import innopolis.entidades.Evento;
import innopolis.entidades.Inscripcione;
import innopolis.entidades.help.UsuarioHelp;
import innopolis.manager.ManagerBuscar;
import innopolis.manager.ManagerEvento;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.List;

import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;

import org.primefaces.model.UploadedFile;

@ManagedBean
@SessionScoped
public class InscripcionApBean implements Serializable {

	private static final long serialVersionUID = 1L;
	private ManagerEvento managerEv;
	private Integer idInscripcion;
	private String apellido;
	private String correo;
	private String estado;
	private Timestamp fechaInscripcion;
	private Integer idUsuario;
	private String imagenPago;
	private String nombre;
	private String observacion;
	private String direccion;
	private String telefono;
	private String celular;
	private Evento evento;
	private String sms;
	private String smscor;
	private Integer id_evento;
	private List<Inscripcione> listadoInscripciones;

	@EJB
	private ManagerBuscar mb;

	private UsuarioHelp session;

	// Imagenes
	private UploadedFile file;

	public InscripcionApBean() {
		/* Session */
		session = SessionBean.verificarSession();
		managerEv = new ManagerEvento();
		imagenPago = "sin_pago.jpg";
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

	public String getEstado() {
		return estado;
	}

	public void setEstado(String estado) {
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

	public List<Inscripcione> getListadoInscripciones() {
		listadoInscripciones = managerEv.findAllInscripciones();
		return listadoInscripciones;
	}

	public UploadedFile getFile() {
		return file;
	}

	public void setFile(UploadedFile file) {
		this.file = file;
	}

	/* SESSION */
	public UsuarioHelp getSession() {
		return session;
	}

	// editar imagen
	public void changeImg(Inscripcione ins) {
		setImagenPago(ins.getImagenPago());
	}

	public String cargarInscripcion(Inscripcione i) {
		try {
			idInscripcion = i.getIdInscripcion();
			apellido = i.getApellido();
			correo = i.getCorreo();
			estado = i.getEstado();
			fechaInscripcion = i.getFechaInscripcion();
			idUsuario = i.getIdUsuario();
			nombre = i.getNombre();
			observacion = i.getObservacion();
			evento = i.getEvento();
			sms = i.getSms();
			direccion = i.getDireccion();
			telefono = i.getTelefono();
			celular = i.getCelular();
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}

		return "";
	}

	public String asignarsms(Inscripcione inscripcion) {
		idInscripcion = inscripcion.getIdInscripcion();
		apellido = inscripcion.getApellido();
		correo = inscripcion.getCorreo();
		estado = inscripcion.getEstado();
		fechaInscripcion = inscripcion.getFechaInscripcion();
		idUsuario = inscripcion.getIdUsuario();
		nombre = inscripcion.getNombre();
		observacion = inscripcion.getObservacion();
		evento = inscripcion.getEvento();
		sms = inscripcion.getSms();
		smscor = "<!DOCTYPE html><html lang='es'><head><meta http-equiv='Content-Type' content='text/html; charset=utf-8' />"
				+ "<meta name='viewport' content='width=device-width'></head><body>"
				+ "Le informamos que la inscripcion de: "
				+ inscripcion.getEvento().getNombre()
				+ " ,fue "
				+ inscripcion.getEstado()
				+ "<br/> Saludos cordiales, "
				+ "<br/> Sistema de REGECE Yachay EP"
				+ "<br/><em><strong>NOTA:</strong> Este correo es generado automáticamente por el sistema favor no responder al mismo.</em></body></html>";
		;
		return "";
	}

	public String notificarInscripcion(Inscripcione inscripcion) {
		if (estado.equals("aprobada") && sms.equals("notificada")) {
			FacesContext.getCurrentInstance().addMessage(
					null,
					new FacesMessage(FacesMessage.SEVERITY_WARN,
							"La inscripción fue aprobada y notificada", null));
		} else if (estado.equals("sin aprobar")) {
			FacesContext
					.getCurrentInstance()
					.addMessage(
							null,
							new FacesMessage(
									FacesMessage.SEVERITY_WARN,
									"La inscripción no ha sido aprobada o negada para notificarla",
									null));
		} else {
			try {
				// Mail.sendMailsolousr(correo,
				// "Petición de Inscripcion YACHAY/REGECE  ", smscor);
				mb.envioMailWS(correo, "Petición de Inscripcion YACHAY/REGECE",
						smscor);

				managerEv.notificarInscripcion(idInscripcion, "notificada");
				FacesContext.getCurrentInstance().addMessage(
						null,
						new FacesMessage(FacesMessage.SEVERITY_INFO,
								"Notificación correcta", null));
			} catch (Exception e) {
				FacesContext.getCurrentInstance().addMessage(
						null,
						new FacesMessage(FacesMessage.SEVERITY_ERROR,
								"Error al enviar notificación", null));
			}
		}
		return "";
	}

	public String aprobarInscrito(Inscripcione inscripcion) {
		try {
			Evento ev = inscripcion.getEvento();
			if (managerEv.superaInscritosEvento(ev)) {
				FacesContext.getCurrentInstance().addMessage(
						null,
						new FacesMessage(FacesMessage.SEVERITY_WARN,
								"Evento supera los inscritos", null));
			} else {
				managerEv.modificarInscripcion(inscripcion.getIdInscripcion(),
						"aprobada");
				FacesContext.getCurrentInstance().addMessage(
						null,
						new FacesMessage(FacesMessage.SEVERITY_INFO,
								"Aprobación exitosa", null));
				if (inscripcion.getSms().equals("notificada")) {
					managerEv.notificarInscripcion(
							inscripcion.getIdInscripcion(), "no notificada");
					FacesContext.getCurrentInstance().addMessage(
							null,
							new FacesMessage(FacesMessage.SEVERITY_WARN,
									"Debe volver a notificar la inscripción",
									null));
				}
			}
		} catch (Exception e) {
			FacesContext.getCurrentInstance().addMessage(
					null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR,
							"Error al aprobar inscripción", null));
		}
		return "";
	}

	public String negarInscrito(Inscripcione inscripcion) {
		if (inscripcion.getEstado().equals("aprobada")
				&& inscripcion.getSms().equals("notificada")) {
			FacesContext
					.getCurrentInstance()
					.addMessage(
							null,
							new FacesMessage(
									FacesMessage.SEVERITY_WARN,
									"La inscripción ya fue aprobada y notificada, no se puede negar",
									null));
		} else {
			try {
				managerEv.modificarInscripcion(inscripcion.getIdInscripcion(),
						"negada");
				FacesContext.getCurrentInstance().addMessage(
						null,
						new FacesMessage(FacesMessage.SEVERITY_INFO,
								"Inscripción negada", null));
			} catch (Exception e) {
				FacesContext.getCurrentInstance().addMessage(
						null,
						new FacesMessage(FacesMessage.SEVERITY_ERROR,
								"Error al negar inscripción", null));
			}
		}
		return "";
	}

	public String cambioEnvioSms() {
		FacesContext.getCurrentInstance().addMessage(
				null,
				new FacesMessage(FacesMessage.SEVERITY_INFO,
						"Notificación aceptada", null));
		return "";
	}

	// ------ Envios paginas--------//
	public String irAprobador() {
		idInscripcion = null;
		apellido = "";
		correo = "";
		estado = "";
		fechaInscripcion = null;
		idUsuario = null;
		nombre = "";
		direccion="";
		telefono="";
		celular="";
		observacion = "";
		sms = "";
		smscor = "";
		return "/aprobador/inscripciones";
	}
}
