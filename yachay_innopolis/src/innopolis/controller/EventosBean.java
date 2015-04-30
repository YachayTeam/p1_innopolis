package innopolis.controller;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.Serializable;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import innopolis.entities.Evento;
import innopolis.entities.Tipoevento;
import innopolis.manager.ManagerInnopolis;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.faces.model.SelectItem;

import org.apache.commons.io.FilenameUtils;
import org.apache.commons.io.IOUtils;


import org.apache.myfaces.custom.fileupload.UploadedFile;
import org.primefaces.event.ScheduleEntryMoveEvent;
import org.primefaces.event.ScheduleEntryResizeEvent;
import org.primefaces.event.SelectEvent;
import org.primefaces.model.DefaultScheduleEvent;
import org.primefaces.model.DefaultScheduleModel;
import org.primefaces.model.ScheduleEvent;
import org.primefaces.model.ScheduleModel;

@SuppressWarnings("serial")
@SessionScoped
@ManagedBean
public class EventosBean implements Serializable {
	private ManagerInnopolis minnopolis = new ManagerInnopolis();
	private Integer idEvento;
	private Integer idTipoEvento;
	private Integer idSolCabecera;
	private String nombre;
	private String descripcion;
	private Double costo = 0.00;
	private Timestamp fechaI;
	private Timestamp fechaF;
	private String imagen;
	private String lugar;
	private String pago;
	private ScheduleModel eventModel;
	private ScheduleEvent event = new DefaultScheduleEvent();

	public Integer getIdEvento() {
		return idEvento;
	}

	public void setIdEvento(Integer idEvento) {
		this.idEvento = idEvento;
	}

	public Integer getIdTipoEvento() {
		return idTipoEvento;
	}

	public void setIdTipoEvento(Integer idTipoEvento) {
		this.idTipoEvento = idTipoEvento;
	}
	
	public Integer getIdSolCabecera() {
		return idSolCabecera;
	}

	public void setIdSolCabecera(Integer idSolCabecera) {
		this.idSolCabecera = idSolCabecera;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public Double getCosto() {
		return costo;
	}

	public void setCosto(Double costo) {
		this.costo = costo;
	}

	public Timestamp getFechaI() {
		return fechaI;
	}

	public void setFechaI(Timestamp fechaI) {
		this.fechaI = fechaI;
	}

	public Timestamp getFechaF() {
		return fechaF;
	}

	public void setFechaF(Timestamp fechaF) {
		this.fechaF = fechaF;
	}

	public String getImagen() {
		return imagen;
	}

	public void setImagen(String imagen) {
		this.imagen = imagen;
	}

	public String getLugar() {
		return lugar;
	}

	public void setLugar(String lugar) {
		this.lugar = lugar;
	}

	public String getPago() {
		return pago;
	}

	public void setPago(String pago) {
		this.pago = pago;
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
	
	@PostConstruct
	public void init() {
		eventModel = new DefaultScheduleModel();
		List<Evento> listEventos = getListaEventos();
		for (Evento evento : listEventos) {
			eventModel.addEvent(new DefaultScheduleEvent(evento.getNombre(),
					evento.getFechaI(), evento.getFechaF()));
		}
	}

	public List<Evento> getListaEventos() {
		return minnopolis.findAllEventos();
	}

	public String accionInsertarEvento() {
		Evento evento = new Evento();
		evento.setNombre(nombre);
		evento.setDescripcion(descripcion);
		evento.setImagen(imagen);
		evento.setFechaI(fechaI);
		evento.setFechaF(fechaF);
		evento.setCosto(costo);
		evento.setLugar(lugar);
		try {
			evento.setTipoevento(minnopolis.findTipoEventoById(1));
			//evento.setSolicicabecera(minnopolis.findSolicitudCabeceraById(idSolCabecera));
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		try {
			minnopolis.insertarEvento(evento);
			idEvento = null;
			idTipoEvento = null;
			idSolCabecera = null;
			nombre = null;
			descripcion = null;
			imagen = null;
			fechaI = null;
			fechaF = null;
			costo = null;
			lugar = null;
		} catch (Exception e) {
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(e.getMessage()));
			e.printStackTrace();
		}
		return "";
	}

	public String accionEliminarEvento(Evento t) {
		try {
			minnopolis.eliminarEvento(t.getIdEvento());
		} catch (Exception e) {
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(e.getMessage()));
			e.printStackTrace();
		}
		return "";
	}

	public String accionActualizarEvento() throws Exception {
		Evento evento = minnopolis.findEventoById(idEvento);
		try {
			evento.setTipoevento(minnopolis.findTipoEventoById(idTipoEvento));
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		evento.setNombre(nombre);
		evento.setDescripcion(descripcion);
		evento.setImagen(imagen);
		evento.setFechaI(fechaI);
		evento.setFechaF(fechaF);
		evento.setCosto(costo);
		evento.setLugar(lugar);
		try {
			minnopolis.actualizarEvento(evento);
			idEvento = null;
			idTipoEvento = null;
			nombre = null;
			descripcion = null;
			imagen = null;
			fechaI = null;
			fechaF = null;
			costo = null;
			lugar = null;
		} catch (Exception e) {
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(e.getMessage()));
			e.printStackTrace();
		}
		return "";
	}

	public List<SelectItem> getListaTipoEvento() {
		List<SelectItem> listadoSI = new ArrayList<SelectItem>();
		List<Tipoevento> listadoTipoEvento = minnopolis.findAllTipoEventos();
		for (Tipoevento c : listadoTipoEvento) {
			SelectItem item = new SelectItem(c.getIdTipoEvento(), c.getTipo());
			listadoSI.add(item);
		}
		return listadoSI;
	}

	public String accionCargarEvento(Evento t) {
		idEvento = t.getIdEvento();
		nombre = t.getNombre();
		descripcion = t.getDescripcion();
		costo = t.getCosto();
		fechaF = t.getFechaF();
		fechaI = t.getFechaI();
		lugar = t.getLugar();
		imagen = t.getImagen();
		idTipoEvento = t.getTipoevento().getIdTipoEvento();
		return "";
	}

	public String accionCargarEvento2(Evento t) {
		idEvento = t.getIdEvento();
		nombre = t.getNombre();
		descripcion = t.getDescripcion();
		costo = t.getCosto();
		fechaF = t.getFechaF();
		fechaI = t.getFechaI();
		lugar = t.getLugar();
		imagen = t.getImagen();
		idTipoEvento = t.getTipoevento().getIdTipoEvento();
		return "inscripciones";
	}
	
	//calendario
	public void addEvent(ActionEvent actionEvent) {
		if (event.getId() == null)
			eventModel.addEvent(event);
		else
			eventModel.updateEvent(event);

		event = new DefaultScheduleEvent();
	}

	public void onEventSelect(SelectEvent selectEvent) {
		event = (ScheduleEvent) selectEvent.getObject();
	}

	public void onDateSelect(SelectEvent selectEvent) {
		event = new DefaultScheduleEvent("", (Date) selectEvent.getObject(),
				(Date) selectEvent.getObject());
	}

	public void onEventMove(ScheduleEntryMoveEvent event) {
		FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO,
				"Event moved", "Day delta:" + event.getDayDelta()
						+ ", Minute delta:" + event.getMinuteDelta());
		addMessage(message);
	}

	public void onEventResize(ScheduleEntryResizeEvent event) {
		FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO,
				"Event resized", "Day delta:" + event.getDayDelta()
						+ ", Minute delta:" + event.getMinuteDelta());
		addMessage(message);
	}

	private void addMessage(FacesMessage message) {
		FacesContext.getCurrentInstance().addMessage(null, message);
	}
	
	//IMAGEN
	  private UploadedFile uploadedFile;
	    private String fileName;

	    // Actions ------------------------------------------------------------------------------------

	    public String subir() {

	   //     // Just to demonstrate what information you can get from the uploaded file.
	     //   System.out.println("File type: " + uploadedFile.getContentType());
	  //      System.out.println("File name: " + uploadedFile.getName());
	    //    System.out.println("File size: " + uploadedFile.getSize() + " bytes");

	        // Prepare filename prefix and suffix for an unique filename in upload folder.
	        String prefix = FilenameUtils.getBaseName(uploadedFile.getName());
	        String suffix = FilenameUtils.getExtension(uploadedFile.getName());
	        
	        // Prepare file and outputstream.
	        File file = null;
	        OutputStream output = null;
	        
	        try {
	            // Create file with unique name in upload folder and write to it.
	            file = File.createTempFile(prefix + "_", "." + suffix, new File("c:/imagen"));
	            output = new FileOutputStream(file);
	            IOUtils.copy(uploadedFile.getInputStream(), output);
	            fileName = file.getName();

	            // Show succes message.
	            FacesContext.getCurrentInstance().addMessage("uploadForm", new FacesMessage(
	                FacesMessage.SEVERITY_INFO, "File upload succeed!", null));
	        } catch (IOException e) {
	            // Cleanup.
	            if (file != null) file.delete();

	            // Show error message.
	            FacesContext.getCurrentInstance().addMessage("uploadForm", new FacesMessage(
	                FacesMessage.SEVERITY_ERROR, "File upload failed with I/O error.", null));

	            // Always log stacktraces (with a real logger).
	            e.printStackTrace();
	        } finally {
	            IOUtils.closeQuietly(output);
	        }
	        return "";
	    }

	    // Getters ------------------------------------------------------------------------------------

	    public UploadedFile getUploadedFile() {
	        return uploadedFile;
	    }

	    public String getFileName() {
	        return fileName;
	    }

	    // Setters ------------------------------------------------------------------------------------

	    public void setUploadedFile(UploadedFile uploadedFile) {
	        this.uploadedFile = uploadedFile;
	    }
}
