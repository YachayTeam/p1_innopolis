package innopolis.controller;

import innopolis.entidades.Evento;
import innopolis.entidades.Inscripcione;
import innopolis.entidades.help.UsuarioHelp;
import innopolis.manager.ManagerBuscar;
import innopolis.manager.ManagerEvento;
import innopolis.model.generic.Mensaje;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.List;

import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

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
		return managerEv.findAllInscripciones();
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
			Mensaje.crearMensajeINFO("La inscripción fue aprobada y notificada");
		} else if (estado.equals("sin aprobar")) {
			Mensaje.crearMensajeWARN("La inscripción no ha sido aprobada o negada para notificarla");
		} else {
			try {
				mb.envioMailWS(correo, "Petición de Inscripcion YACHAY/REGECE",
						smscor);
				managerEv.notificarInscripcion(idInscripcion, "notificada");
				Mensaje.crearMensajeINFO("Notificación correcta");
			} catch (Exception e) {
				Mensaje.crearMensajeWARN("Error al enviar notificación");
			}
		}
		return "";
	}

	public String aprobarInscrito(Inscripcione inscripcion) {
		try {
			Evento ev = inscripcion.getEvento();
			if (!managerEv.superaInscritosEvento(ev)) {
				Mensaje.crearMensajeWARN("Evento supera los inscritos");
			} else {
				managerEv.modificarInscripcion(inscripcion.getIdInscripcion(),
						"aprobada");
				Mensaje.crearMensajeINFO("Se ha aprobado al inscrito");
				if (inscripcion.getSms().equals("notificada")) {
					managerEv.notificarInscripcion(
							inscripcion.getIdInscripcion(), "no notificada");
					Mensaje.crearMensajeWARN("Debe volver a notificar la inscripción");
				}
			}
		} catch (Exception e) {
			Mensaje.crearMensajeWARN("Error al aprobar inscripción");
		}
		return "";
	}

	public String negarInscrito(Inscripcione inscripcion) {
		if (inscripcion.getEstado().equals("aprobada")
				&& inscripcion.getSms().equals("notificada")) {
			Mensaje.crearMensajeWARN("La inscripción yá fue aprobada y notificada, no se puede negar");
		} else {
			try {
				if (inscripcion.getEstado().equals("aprobada")) {
					managerEv.modificarInscripcion(
							inscripcion.getIdInscripcion(), "negada");
					Mensaje.crearMensajeINFO("Inscripción negada");
				} else if(inscripcion.getEstado().equals("negada")) {
					Mensaje.crearMensajeINFO("Inscripción se encuentra negada");
				}
			} catch (Exception e) {
				Mensaje.crearMensajeWARN("Error al negar inscripción");
			}
		}
		return "";
	}

	public String cambioEnvioSms() {
		Mensaje.crearMensajeINFO("Notificación aceptada");
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
		direccion = "";
		telefono = "";
		celular = "";
		observacion = "";
		sms = "";
		smscor = "";
		return "";
	}
}
