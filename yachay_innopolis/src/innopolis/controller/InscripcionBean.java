package innopolis.controller;

import innopolis.entities.Evento;
import innopolis.entities.Inscripcione;
import innopolis.manager.ManagerEvento;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Calendar;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;

@ManagedBean
@SessionScoped
public class InscripcionBean implements Serializable{

	private static final long serialVersionUID = 1L;
	private ManagerEvento managerEv;
	
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
	
	
	public InscripcionBean() {
		managerEv=new ManagerEvento();
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

	public String inscribirse(){
		String resp ="";
		try {
			if(getImagenPago()==null){
				setImagenPago("sin_pago.jpg");
			}
			if(getObservacion()==null){
				setObservacion("sin observacion");
			}
			//FECHA Y HORA ACTUAL
			Calendar fecha_hora = Calendar.getInstance();
			setFechaInscripcion( new Timestamp(fecha_hora.getTimeInMillis()));
			//Ingreso
			managerEv.insertarInscripcion(getFechaInscripcion(), 0, getNombre(), getApellido(), getCorreo(), 0, getImagenPago(), getObservacion());
			resp="";//Enviar a un resumen de inscripcion o pagina de exito
		} catch (Exception e) {
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,"Error al intentar inscribirse al evento", null));
		}
		return resp;
	}
	
	
	
	
}
