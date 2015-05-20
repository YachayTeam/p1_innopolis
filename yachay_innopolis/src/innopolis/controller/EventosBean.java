package innopolis.controller;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Serializable;
import java.sql.Time;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import innopolis.entities.Evento;
import innopolis.entities.Recurso;
import innopolis.entities.Solicicabecera;
import innopolis.entities.Solicidetalle;
import innopolis.entities.Tipoevento;
import innopolis.manager.ManagerEvento;
import innopolis.manager.ManagerReservas;
import innopolis.manager.Validacion;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
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
public class EventosBean implements Serializable {
	private static final long serialVersionUID = 1L;
	private ManagerEvento manager;
	private ManagerReservas manager1;
	private Integer idEvento;
	private float costo;
	private String descripcion;
	private Date fecha;
	private String imagen;
	private String imgMost;
	private String lugar;
	private String nombre;
	private Integer cantidad;
	private Integer sc;
	private Integer te;
	private String g; 
	// solicitud
	private List<SelectItem> select;

	private UploadedFile file;

	// calendario
	private Evento e;
	private ScheduleModel eventModel;
	private ScheduleEvent event = new DefaultScheduleEvent();

	// temporales
	private Evento eventotemp;
	private Tipoevento tipoevento;
	private Boolean esave;
	private boolean eTem;

	public EventosBean() {
		manager = new ManagerEvento();
		manager1 = new ManagerReservas();
		select = getlistaRecursos();
		esave=false;
		imagen="300.jpg";
		imgMost="300.jpg";
	}

	public ScheduleModel getEventModel() {
		return eventModel;
	}

	public Boolean isEsave() {
		return esave;
	}

	public void setEsave(Boolean esave) {
		this.esave = esave;
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

	public UploadedFile getFile() {
		return file;
	}

	public Integer getCantidad() {
		return cantidad;
	}

	public void setCantidad(Integer cantidad) {
		this.cantidad = cantidad;
	}

	public void setFile(UploadedFile file) {
		this.file = file;
	}

	public Evento getE() {
		return e;
	}

	public void setE(Evento e) {
		this.e = e;
	}

	public boolean iseTem() {
		return eTem;
	}

	public void seteTem(boolean eTem) {
		this.eTem = eTem;
	}

	public Evento getEventotemp() {
		return eventotemp;
	}

	public void setEventotemp(Evento eventotemp) {
		this.eventotemp = eventotemp;
	}

	public Integer getIdEvento() {
		return idEvento;
	}

	public void setIdEvento(Integer idEvento) {
		this.idEvento = idEvento;
	}

	public float getCosto() {
		return costo;
	}

	public void setCosto(float costo) {
		this.costo = costo;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public Date getFecha() {
		return fecha;
	}

	public void setFecha(Date fecha) {
		this.fecha = fecha;
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

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public Integer getSc() {
		return sc;
	}

	public void setSc(Integer sc) {
		this.sc = sc;
	}

	public Integer getTe() {
		return te;
	}

	public void setTe(Integer te) {
		this.te = te;
	}

	public List<Evento> getListEvento() {
		return manager.findAllEventos();
	}
	public Tipoevento getTipoevento() {
		return tipoevento;
	}

	public void setTipoevento(Tipoevento tipoevento) {
		this.tipoevento = tipoevento;
	}
	
	public String getImgMost() {
		return imgMost;
	}
	
	public void setImgMost(String imgMost) {
		this.imgMost = imgMost;
	}

	// EVENTOS
	// accion para invocar el manager y crear evento
	public String crearEvento() {
		try {
			manager.insertarEvento(nombre, descripcion, lugar, imagen, fecha,
					costo, cantidad);
			// reiniciamos datos (limpiamos el formulario)
			nombre = "";
			descripcion = "";
			lugar = "";
			imagen = "300.jpg";
			fecha = null;
			costo = 0;
			cantidad = 0;
			sc = 0;
			te = 0;
			esave=false;
			FacesContext context = FacesContext.getCurrentInstance();
			context.addMessage(null, new FacesMessage("Registrado..!!!",
					"Evento Creado "));
		} catch (Exception e) {
			FacesContext context = FacesContext.getCurrentInstance();
			context.addMessage(null, new FacesMessage("Error..!!!",
					"Evento no pudo ser Creado "));
			e.printStackTrace();
		}
		return "evento";
	}
	
	// accion para invocar el manager y crear evento
		public String EditarEvento() throws Exception {
			try {
				manager.editarEventos(idEvento, nombre, descripcion, lugar, imagen, fecha,
						costo, cantidad);
				// reiniciamos datos (limpiamos el formulario)
				nombre = "";
				descripcion = "";
				lugar = "";
				imagen = "300.jpg";
				fecha = null;
				costo = 0;
				cantidad = 0;
				sc = 0;
				te = 0;
				esave=false;
				FacesContext context = FacesContext.getCurrentInstance();
				context.addMessage(null, new FacesMessage("Editado..!!!",
						"Editado Creado "));
			} catch (Exception e) {
				FacesContext context = FacesContext.getCurrentInstance();
				context.addMessage(null, new FacesMessage("Error..!!!",
						"Evento no pudo ser Editado "));
				e.printStackTrace();
			}
			return "eventos";
		}
	
	
	
	
	// metodo para poner el nombre a la imagen
	public void asignarNombreImagen() {
		if (getNombre().trim().isEmpty()) {
			System.out.println("Vacio");
		} else {
			DateFormat dateFormat = new SimpleDateFormat("_ddMMyyyyHHmm");
			g="img_"+getNombre()+dateFormat.format(new Date())+".jpg";
			System.out.println(g);
		}

	}

	// metodo para ir a solicitud y guardar el evento en un temporal
	public String irSolicitud() {
		if (esave==true){
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("El evento ya cuenta con una solicitud."));
			return "";
		}else{
		try {
			eventotemp = manager.crearEventoTmp(nombre, descripcion, lugar,
					imagen, fecha, costo, cantidad);
		} catch (Exception e) {
			System.out.print("ir a solicitud no creo el evento temporal");
		}
		return "solcab2?faces-redirect=true";
	}
	}

	// este metodo me almacena el evento temporal en la base de datos
	public String guardarEventoTemp() {
		try {
			manager.insertarEventoTem();
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage("Evento Creado"));
		} catch (Exception e) {
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(e.getMessage()));
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
				setImagen(g);
				System.out.println("PAD------> " + carpetaImagenes);
				System.out.println("name------> " + getImagen());
				outputStream = new FileOutputStream(new File(carpetaImagenes
						+ File.separatorChar + getImagen()));
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

	// metodo para mostrar los EventosTipos en Eventos
	public List<SelectItem> getListaEveTipo() {
		List<SelectItem> listadoSI = new ArrayList<SelectItem>();
		List<Tipoevento> tipoevento = manager.findAllTipoEventos();

		for (Tipoevento t : tipoevento) {
			SelectItem item = new SelectItem(t.getIdTipoEvento(), t.getTipo());
			listadoSI.add(item);
		}
		return listadoSI;
	}

	// metodo para asignar el TipoEvento al Evento
	public String asignarTipoeve() {
		manager.asignarTipoevento(te);
		return "";
	}

	// ///////////////////////////////////////////////////////////////////////////////////solicitud
	// //////////////////////////////////////////////////////////

	// Atributo de solicitud
	// Cabecera
	private String direccion;
	private String actividad;
	private String justificacion;
	private String objetivo;
	private Time horafin;
	private Time horainicio;
	// Extra Manejo de Horas
	private Date h_inicio;
	private Date h_fin;
	// Cambio estados
	private Integer id_estadoSolicitud;
	// Cambios solicitud
	private List<Solicidetalle> listDetalles;

	// Detalles
	private Integer id_recurso;
	private Integer capacidad_recurso;
	private Solicicabecera solicitudCabTem;
	private boolean solicitudCabTmpGuardada;
	private List<Solicicabecera> listadoSolCab;

	// Metodos Get y Set

	public Integer getId_recurso() {
		return id_recurso;
	}

	public void setId_recurso(Integer id_recurso) {
		this.id_recurso = id_recurso;
	}

	public Integer getcapacidad_recurso() {
		return capacidad_recurso;
	}

	public void setcapacidad_recurso(Integer capacidad_recurso) {
		this.capacidad_recurso = capacidad_recurso;
	}

	public Solicicabecera getSolicitudCabTem() {
		return solicitudCabTem;
	}

	public void setSolicitudCabTem(Solicicabecera solicitudCabTem) {
		this.solicitudCabTem = solicitudCabTem;
	}

	public boolean isSolicitudCabTmpGuardada() {
		return solicitudCabTmpGuardada;
	}

	public void setSolicitudCabTmpGuardada(boolean solicitudCabTmpGuardada) {
		this.solicitudCabTmpGuardada = solicitudCabTmpGuardada;
	}

	public List<Solicicabecera> getListadoSolCab() {
		listadoSolCab = manager1.findAllSolicitudCabecera();
		return listadoSolCab;
	}

	public String getDireccion() {
		return direccion;
	}

	public void setDireccion(String direccion) {
		this.direccion = direccion;
	}

	public String getActividad() {
		return actividad;
	}

	public void setActividad(String actividad) {
		this.actividad = actividad;
	}

	public String getObjetivo() {
		return objetivo;
	}

	public void setObjetivo(String objetivo) {
		this.objetivo = objetivo;
	}

	public String getJustificacion() {
		return justificacion;
	}

	public void setJustificacion(String justificacion) {
		this.justificacion = justificacion;
	}

	public Time getHorafin() {
		return horafin;
	}

	public void setHorafin(Time horafin) {
		this.horafin = horafin;
	}

	public Time getHorainicio() {
		return horainicio;
	}

	public void setHorainicio(Time horainicio) {
		this.horainicio = horainicio;
	}

	public Date getH_fin() {
		return h_fin;
	}

	public void setH_fin(Date h_fin) {
		this.h_fin = h_fin;
	}

	public Date getH_inicio() {
		return h_inicio;
	}

	public void setH_inicio(Date h_inicio) {
		this.h_inicio = h_inicio;
	}

	public List<SelectItem> getSelect() {
		return select;
	}

	public Integer getId_estadoSolicitud() {
		return id_estadoSolicitud;
	}

	public void setId_estadoSolicitud(Integer id_estadoSolicitud) {
		this.id_estadoSolicitud = id_estadoSolicitud;
	}

	public List<Solicidetalle> getListDetalles() {
		return listDetalles;
	}

	public void setListDetalles(List<Solicidetalle> listDetalles) {
		this.listDetalles = listDetalles;
	}

	// Metodos proceso de ejecucion
	public String crearNuevaSolicitud() {
		try {
			// Modificacion de Horas
			setHorainicio(this.fechaAtiempo(getH_inicio()));
			setHorafin(this.fechaAtiempo(getH_fin()));
			if(!Validacion.fechaMayorIgual(getFecha())){	
				FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "La fecha de solicitud no debe ser menor a la actual.", null));
			}else if(getHorafin().getTime()<=getHorainicio().getTime()){
				FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Verifique su horario de solicitud.", null));
			}else if(Validacion.fechaIgualActual(getFecha()) && (!Validacion.horaMayorIgual(getHorainicio()) || !Validacion.horaMayorIgual(getHorafin()))){
				FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "La hora de solicitud no debe ser menor a la actual.", null));
			}else{
			// SolicitudTemporal
			solicitudCabTem = manager1.crearSolicitudTmp(getDireccion(),
					getActividad(), getObjetivo(), getJustificacion(),
					getFecha(), getHorafin(), getHorainicio());
			id_recurso = 0;
			capacidad_recurso = 0;
			solicitudCabTmpGuardada = false;
			// Cargar Listado----
			select = this.getlistaRecursosLibres();
			}
			} catch (Exception e) {
				FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error al crear la solicitud.", null));
			}
		return "soldet2?faces-redirect=true";
	}

	public String insertarDetalleSolicitud() {
		if (solicitudCabTmpGuardada == true) {
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage("La solicitud ya fue guardada."));
			return "";
		}

		try {
			manager1.agregarSolicitudDetalleTmp(getId_recurso(),
					getcapacidad_recurso());
			id_recurso = 0;
			capacidad_recurso = 0;
		} catch (Exception e) {
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(e.getMessage()));
		}

		return "";
	}

	public String quitarDetalleSolicitud(Solicidetalle det) {
		if (solicitudCabTmpGuardada == true) {
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage("La solicitud ya fue guardada."));
			return "";
		}

		try {
			manager1.quitarDetalleSolicitudTem(det);
		} catch (Exception e) {
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(e.getMessage()));
		}

		return "";
	}

	public String guardarSolicitud() {
		if (solicitudCabTmpGuardada == true) {
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage("La solicitud ya fue guardada."));
			return "";
		}

		try {
			esave=true;
			manager1.guardarSolicitudTemporal(solicitudCabTem);
			manager.asignarSolcab(solicitudCabTem.getIdSolcab());
			solicitudCabTmpGuardada = true;
			FacesContext
					.getCurrentInstance()
					.addMessage(
							null,
							new FacesMessage(
									"Su solicitud fue enviada espere el SMS de confirmacion de solicitud."));
			FacesContext.getCurrentInstance().addMessage(
					null,
					new FacesMessage(
							"Para finalizar el evento de click en guardar."));
		} catch (Exception e) {
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(e.getMessage()));
		}

		return "eventos?faces-redirect=true";
	}

	// LISTADO DE RECURSOS
	public List<SelectItem> getlistaRecursosLibres() {
		List<SelectItem> listadoSI = new ArrayList<SelectItem>();
		List<Recurso> listadoRecurso = manager1.findAllRecursosDisponibles(
				fecha, horainicio, horafin);
		for (Recurso p : listadoRecurso) {
			SelectItem item = new SelectItem(p.getIdRecurso(), p.getNombre()
					+ " - " + p.getCapacidad());
			listadoSI.add(item);
		}
		return listadoSI;
	}

	public List<SelectItem> getlistaRecursos() {
		List<SelectItem> listadoSI = new ArrayList<SelectItem>();
		List<Recurso> listadoRecurso = manager1.findAllRecurso();
		for (Recurso p : listadoRecurso) {
			SelectItem item = new SelectItem(p.getIdRecurso(), p.getNombre()
					+ " - " + p.getCapacidad());
			listadoSI.add(item);
		}
		return listadoSI;
	}

	// metodo para asignar el RecursoTipo al Recurso
	public String asignarRecLibre() {
		manager1.asignarRecurso(id_recurso);
		return "";
	}

	// JAVA.DATE TO SQL.TIME
	@SuppressWarnings("deprecation")
	public Time fechaAtiempo(Date fecha) {
		DateFormat dateFormatH = new SimpleDateFormat("HH:mm");
		String hora = dateFormatH.format(fecha).toString();
		String[] array = hora.split(":");
		Time resp = new Time(Integer.parseInt(array[0]),
				Integer.parseInt(array[1]), 00);
		return resp;
	}

	// metodo de revision de hora a ingresar
	public String control() {
		if (h_fin.getTime() <= h_inicio.getTime()) {
			System.out.println("si entra");
			FacesContext
					.getCurrentInstance()
					.addMessage(
							null,
							new FacesMessage(
									FacesMessage.SEVERITY_ERROR,
									"La hora fin es menor o igual a la hora inicio de peticion del recurso",
									null));
		}
		System.out.println("sera que ve el metodo");
		return "";
	}

	// -------traslados
	public String irsolres() {
		String r = "";
		if (solicitudCabTem.getSolicidetalles().size() > 0) {
			r = "solres2?faces-redirect=true";
			System.out.print(r);
		} else {
			FacesContext.getCurrentInstance().addMessage(
					null,
					new FacesMessage(FacesMessage.SEVERITY_WARN,
							"Debe seleccionar Recursos", null));
			System.out.print(r);
		}
		return r;
	}

	public String irsolcab() {
		return "solcab2?faces-redirect=true";
	}

	public String irsoldet() {
		return "soldet2?faces-redirect=true";
	}

	public String irevento(){
		nombre = "";
		descripcion = "";
		lugar = "";
		imagen = "300.jpg";
		fecha = null;
		costo = 0;
		cantidad = 0;
		sc = 0;
		te = 0;
		esave=false;
		return "eventos";
	}
	// ////////////////////////////////////////////////////////////CALENDARIO//////////////////////////////////////////////////////////////////

	// metodo para listar las fechas desde el dia actual en adelante
	public List<Evento> actual() {
		List<Evento> le = new ArrayList<Evento>();
		for (Evento e : getListEvento()) {
			if (e.getFecha().after(new Date())) {
				le.add(e);
			}
		}
		return le;
	}
	
	//metodo para listar los eventos
	public List<Evento> getListRegEventos(){
		return manager.findAllEventos();
	}
	
	//editar imagen
	public String changeImg(Evento ev){
		idEvento = ev.getIdEvento();
		imagen = ev.getImagen();
		setImagen(ev.getImagen());		
		setImgMost(ev.getImagen());
		FacesContext context = FacesContext.getCurrentInstance();
		context.addMessage(null, new FacesMessage("imagen mostrada correctamente...!!!"));
		FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("imagen mostrada correctamente."));
		System.out.println(ev);
		return "";
	}	

	@PostConstruct
	public void init() {
		eventModel = new DefaultScheduleModel();
		for (Evento e : actual()) {
			event = new DefaultScheduleEvent(e.getNombre(), e.getFecha(),
					e.getFecha(), e);
			eventModel.addEvent(event);
		}
	}

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
	
	public String irEvento(){								       
	      //limpiamos los datos
		nombre = "";
		descripcion = "";
		lugar = "";
		imagen = "300.jpg";
		fecha = null;
		costo = 0;
		cantidad = 0;
		sc = 0;
		te = 0;
		esave=false;				
			return "eventos";					
		}
	
	//metodo para cargar eventos
	public String CargarEventos(Evento ev)
		{
		idEvento = ev.getIdEvento();
		nombre = ev.getNombre();
		descripcion = ev.getDescripcion();
		lugar = ev.getLugar();
		costo = ev.getCosto();			
		fecha = ev.getFecha();
		tipoevento = ev.getTipoevento();
		cantidad = ev.getCantidad();
		imagen = ev.getImagen();	
		return "";
	}

}
