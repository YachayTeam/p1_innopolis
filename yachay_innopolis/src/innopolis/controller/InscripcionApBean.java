package innopolis.controller;

import innopolis.entities.Evento;
import innopolis.entities.Inscripcione;
import innopolis.manager.ManagerEvento;
import innopolis.manager.ManagerReservas;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import org.primefaces.model.UploadedFile;

@ManagedBean
@SessionScoped
public class InscripcionApBean implements Serializable{

	private static final long serialVersionUID = 1L;
	private ManagerEvento managerEv;
	private ManagerReservas managerReserv;
	
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
	
	private Integer id_evento;
	private List<Inscripcione> listadoInscripciones;
	
	//Imagenes
	private UploadedFile file;
	
	public InscripcionApBean() {
		managerEv=new ManagerEvento();
		managerReserv=new ManagerReservas();
		imagenPago = "sin_pago.jpg";
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
		listadoInscripciones = managerEv.findAllInscripciones();
		return listadoInscripciones;
	}
	
	public UploadedFile getFile() {
		return file;
	}
	
	public void setFile(UploadedFile file) {
		this.file = file;
	}
	
	//editar imagen
	public void changeImg(Inscripcione ins){
		setImagenPago(ins.getImagenPago());;
	}
	
	public String notificarInscripcion(Inscripcione inscripcion){
		if(inscripcion.getEstado().equals("aprobada") && inscripcion.getSms().equals("notificada")){
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN,"La inscripcion ya fue aprobada y notificada", null));
		}else if(inscripcion.getEstado().equals("sin aprobar")){
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN,"La inscripcion no ha sido aprobada o negada para poder notificarla", null));
		}else{
			String mensaje="Le informamos que la inscripcion de: "+inscripcion.getEvento().getNombre()+" ,fue "+inscripcion.getEstado();
			try {
				managerReserv.sendMail("juank20097@gmail.com", "xkalrbyylkkzfpnf", "nyqivessalo-6115@yopmail.com", "Peticion de Inscripcion YACHAY/INNOPOLIS  ", mensaje);
				managerEv.notificarInscripcion(inscripcion.getIdInscripcion(), "notificada");
				FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,"Notificacion correcta", null));
			} catch (Exception e) {
				FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,"Error al enviar notificacion", null));
			}
		}
		return "";
	}
	
	public String aprobarInscrito(Inscripcione inscripcion){
		try {
			Evento ev = inscripcion.getEvento();
			if(managerEv.superaInscritosEvento(ev)){
				FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN,"Evento ya supera inscritos", null));
			}else{
				managerEv.modificarInscripcion(inscripcion.getIdInscripcion(), "aprobada");
				FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,"Aprobacion Exitosa", null));
			}
		} catch (Exception e) {
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,"Error al aprobar inscripcion", null));
		}
		return "";
	}
	
	public String negarInscrito(Inscripcione inscripcion){
		if(inscripcion.getEstado().equals("aprobada") && inscripcion.getSms().equals("notificada")){
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN,"La inscripcion ya fue aprobada y notificada, no se puede negar", null));
		}else{
			try {
				managerEv.modificarInscripcion(inscripcion.getIdInscripcion(), "negada");
				FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,"Inscripcion negada", null));
			} catch (Exception e) {
				FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,"Error al negar inscripcion", null));
			}
		}
		return "";
	}
	
	
}
