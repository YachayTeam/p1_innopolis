package innopolis.controller;

import innopolis.entities.Evento;
import innopolis.entities.Inscripcione;
import innopolis.manager.ManagerEvento;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

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


	public void setEvento(Evento evento) {
		this.evento = evento;
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

	
	
	
}
