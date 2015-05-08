package innopolis.controller;

import innopolis.entities.Evento;
import innopolis.entities.Inscripcione;
import innopolis.manager.ManagerEvento;
import innopolis.manager.ManagerReservas;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Serializable;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.servlet.ServletContext;

import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.UploadedFile;

@ManagedBean
@SessionScoped
public class InscripcionBean implements Serializable{

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
	
	public InscripcionBean() {
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

	public String inscribirse(){
		String resp ="";
		try {
			/*if(getImagenPago()==null){
				setImagenPago("sin_pago.jpg");
			}*/
			if(getObservacion()==null){
				setObservacion("sin observacion");
			}
			//FECHA Y HORA ACTUAL
			Calendar fecha_hora = Calendar.getInstance();
			setFechaInscripcion( new Timestamp(fecha_hora.getTimeInMillis()));
			//Ingreso
			managerEv.insertarInscripcion(getFechaInscripcion(), 0, getNombre(), getApellido(), getCorreo(), getImagenPago(), getObservacion());
			resp="";//Enviar a un resumen de inscripcion o pagina de exito
		} catch (Exception e) {
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,"Error al intentar inscribirse al evento", null));
		}
		return resp;
	}
	
	public String notificarInscripcion(Inscripcione inscripcion){
		if(inscripcion.getSms().equals("notificada")){
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN,"La inscripcion ya fue notificacion", null));
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
				
				//AsignacionDeNombreImagen
				DateFormat dateFormat = new SimpleDateFormat("ddMMyyyyHHmmss");
				String nombre_img = "img_"+dateFormat.format(new Date())+".jpg";
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
	
}
