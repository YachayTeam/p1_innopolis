package innopolis.controller;

import java.util.Date;
import java.util.List;

import innopolis.entities.Inscripcione;
import innopolis.manager.ManagerInnopolis;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;

import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.UploadedFile;

@SessionScoped
@ManagedBean
public class InscripcionesBean {
	private ManagerInnopolis minnopolis = new ManagerInnopolis();
	
	private Integer idInscripcion;
	private String descripcion;
	private Date fechaInscripcion= new Date();
	private Integer idUsuario;
	private String imagenPago;
	private Integer idEvento;
	
	public Integer getIdInscripcion() {
		return idInscripcion;
	}
	public void setIdInscripcion(Integer idInscripcion) {
		this.idInscripcion = idInscripcion;
	}
	public String getDescripcion() {
		return descripcion;
	}
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}
	public Date getFechaInscripcion() {
		return fechaInscripcion;
	}
	public void setFechaInscripcion(Date fechaInscripcion) {
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
	public Integer getIdEvento() {
		return idEvento;
	}
	public void setIdEvento(Integer evento) {
		this.idEvento = evento;
	}
	public List<Inscripcione> getListaInscripciones()
	{
		return minnopolis.findAllInscripciones();
	}
	
	/*public String accionInsertarInscripciones()
	{
		Inscripcione insc= new Inscripcione();
		insc.setIdUsuario(idUsuario);
		insc.setDescripcion(descripcion);
		try {
			insc.setEvento(minnopolis.findEventoById(idEvento));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		insc.setFechaInscripcion(fechaInscripcion);
		insc.setImagenPago(imagenPago);
		try {
			minnopolis.insertarInscripciones(insc);
			idEvento=null;
			descripcion=null;
			idUsuario=null;
			fechaInscripcion=null;
			imagenPago=null;
			idInscripcion=null;
		} catch (Exception e) {
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(e.getMessage()));
			e.printStackTrace();
		}
		return "";
	}
	
	public String accionEliminarInscripciones(Inscripcione insc) {

		try {
			minnopolis.eliminarInscripciones(insc.getIdInscripcion());
		} catch (Exception e) {
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(e.getMessage()));
			e.printStackTrace();
		}
		return "";
	}
	
	public String accionActualizarInscripciones()throws Exception {
		Inscripcione insc= minnopolis.findInscripcionesById(idInscripcion);
		insc.setEvento(minnopolis.findEventoById(idEvento));
		insc.setIdUsuario(idUsuario);
		insc.setDescripcion(descripcion);
		insc.setFechaInscripcion(fechaInscripcion);
		insc.setImagenPago(imagenPago);
		try {
			minnopolis.actualizarInscripciones(insc);
			idEvento=null;
			descripcion=null;
			idUsuario=null;
			fechaInscripcion=null;
			imagenPago=null;
			idInscripcion=null;
		} catch (Exception e) {
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(e.getMessage()));
			e.printStackTrace();
		}
		return "";
	}
	 private UploadedFile file;

	    public UploadedFile getFile() {
	        return file;
	    }

	    public void setFile(UploadedFile file) {
	        this.file = file;
	    }

	    public void upload() {
	        if(file != null) {
	            FacesMessage msg = new FacesMessage("Succesful", file.getFileName() + " is uploaded.");
	            FacesContext.getCurrentInstance().addMessage(null, msg);
	        }
	    }

	    public void handleFileUpload(FileUploadEvent event) {
			FacesMessage msg = new FacesMessage("Succesful", event.getFile().getFileName() + " is uploaded.");
			FacesContext.getCurrentInstance().addMessage(null, msg);
		}
*/
}
