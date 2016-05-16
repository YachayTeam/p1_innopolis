package innopolis.controller;

import innopolis.entidades.Evento;
import innopolis.entidades.Inscripcione;
import innopolis.entidades.help.UsuarioHelp;
import innopolis.manager.Mail;
import innopolis.manager.ManagerEvento;

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
	private Integer idInscripcion;
	private String apellido;
	private String correo;
	private String estado;
	private Timestamp fechaInscripcion;
	private Integer idUsuario;
	private String imagenPago;
	private String nombre;
	private String observacion;
	private Evento evento;
	private String sms;
	private String smscor;
	private Integer id_evento;
	private List<Inscripcione> listadoInscripciones;
	
	
	private UsuarioHelp session;
	
	//Imagenes
	private UploadedFile file;
	
	public InscripcionApBean() {
		/*Session*/
		session = SessionBean.verificarSession();
		managerEv=new ManagerEvento();
		imagenPago = "sin_pago.jpg";
	}


	/**
	 * @return the sms
	 */
	public String getSms() {
		return sms;
	}


	/**
	 * @param sms the sms to set
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
	 * @param smscor the smscor to set
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
	
	/*SESSION*/
	public UsuarioHelp getSession() {
		return session;
	}
	
	//editar imagen
	public void changeImg(Inscripcione ins){
		setImagenPago(ins.getImagenPago());
		System.out.println(getImagenPago());
	}
	
	public String asignarsms(Inscripcione inscripcion){
		idInscripcion=inscripcion.getIdInscripcion();
		apellido=inscripcion.getApellido();
		correo=inscripcion.getCorreo();
		estado= inscripcion.getEstado();
		fechaInscripcion=inscripcion.getFechaInscripcion();
		idUsuario=inscripcion.getIdUsuario();		
		nombre=inscripcion.getNombre();
		observacion=inscripcion.getObservacion();
		evento=inscripcion.getEvento();
		sms=inscripcion.getSms();		
		smscor="Le informamos que la inscripcion de: "+inscripcion.getEvento().getNombre()+" ,fue "+inscripcion.getEstado();
		return "";
	}

	
	public String notificarInscripcion(Inscripcione inscripcion){
		if(estado.equals("aprobada") && sms.equals("notificada")){
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN,"La inscripcion ya fue aprobada y notificada", null));
		}else if(estado.equals("sin aprobar")){
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN,"La inscripcion no ha sido aprobada o negada para poder notificarla", null));
		}else{
			try {
				Mail.sendMailsolousr(correo, "Petición de Inscripcion YACHAY/REGECE  ", smscor);
				managerEv.notificarInscripcion(idInscripcion, "notificada");
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
				if(inscripcion.getSms().equals("notificada")){
					managerEv.notificarInscripcion(inscripcion.getIdInscripcion(), "no notificada");
					FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN,"Debe volver a notificar la inscripcion", null));
				}
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
	
	public String cambioEnvioSms(){
		FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,"Notificación Aceptada ", null));
		return "";
	}
	
	//------ Envios paginas--------//				
			public String irAprobador(){		
				idInscripcion=null;
				apellido="";
				correo="";
				estado="";
				fechaInscripcion=null;
				idUsuario=null;		
				nombre="";
				observacion="";				
				sms="";		
				smscor="";
				FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,"Volver", null));
				return "/aprobador/inscripciones";					
			}
}
