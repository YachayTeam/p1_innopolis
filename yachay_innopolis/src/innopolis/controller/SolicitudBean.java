package innopolis.controller;

import java.sql.Time;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import innopolis.entities.Recurso;
import innopolis.entities.Solicicabecera;
import innopolis.entities.Solicidetalle;
import innopolis.entities.Soliciestado;
import innopolis.manager.ManagerReservas;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;

@SessionScoped
@ManagedBean
public class SolicitudBean {
	private ManagerReservas manager;
	
	//Atributo de solicitud
	//Cabecera
	private String direccion;
	private String actividad;
	private String justificacion;
	private String objetivo;
	private Date fecha; 
	private Time horafin; 
	private Time horainicio;
	//Extra Manejo de Horas
	private Date h_inicio;
	private Date h_fin;
	private List<SelectItem> select;
	//Cambios solicitud
	private List<Solicidetalle> listDetalles;
	private Soliciestado estadoSol;
	private Integer id_sol;
	//+/- Detalles
	private ArrayList<Solicidetalle> list_mas;
	private ArrayList<Solicidetalle> list_menos;
	
	//Detalles
	private Integer id_recurso;
	private Integer capacidad_recurso;
	private Solicicabecera solicitudCabTem;
	private boolean solicitudCabTmpGuardada;
	private List<Solicicabecera> listadoSolCab;
	
	public SolicitudBean() {
		manager = new ManagerReservas();
		//Select todos
		select = getlistaRecursos();
	}

	//Metodos Get y Set
	public ManagerReservas getManager() {
		return manager;
	}

	public void setManager(ManagerReservas manager) {
		this.manager = manager;
	}

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
		listadoSolCab = manager.findAllSolicitudCabecera();
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

	public Date getFecha() {
		return fecha;
	}

	public void setFecha(Date fecha) {
		this.fecha = fecha;
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
	
	public List<Solicidetalle> getListDetalles() {
		return listDetalles;
	}
	
	public void setListDetalles(List<Solicidetalle> listDetalles) {
		this.listDetalles = listDetalles;
	}
	
	public Soliciestado getEstadoSol() {
		return estadoSol;
	}
	
	public void setEstadoSol(Soliciestado estadoSol) {
		this.estadoSol = estadoSol;
	}
	
	public Integer getId_sol() {
		return id_sol;
	}
	
	public void setId_sol(Integer id_sol) {
		this.id_sol = id_sol;
	}
	
	//Metodos proceso de ejecucion
	public String crearNuevaSolicitud(){
		String resp="";
		try {
			//Modificacion de Horas
			setHorainicio(this.fechaAtiempo(getH_inicio()));
			setHorafin(this.fechaAtiempo(getH_fin()));
			//SolicitudTemporal
			solicitudCabTem = manager.crearSolicitudTmp(getDireccion(), getActividad(), getObjetivo(), getJustificacion(), getFecha(), getHorafin(), getHorainicio()); 
			id_recurso=0; 
			capacidad_recurso=0;
			solicitudCabTmpGuardada=false;
			//Cargar Listado----
			select = this.getlistaRecursosLibres();
			resp="soldet";
		} catch (Exception e) {
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error al crear la solicitud.", null));
		}
		return resp;
	}
	
	public String insertarDetalleSolicitud(){
		if(solicitudCabTmpGuardada==true){
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("La solicitud ya fue guardada."));
			return "";
		}
		
		try {
			manager.agregarSolicitudDetalleTmp(getId_recurso(), getcapacidad_recurso());
			id_recurso=0; 
			capacidad_recurso=0;
		} catch (Exception e) {
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(e.getMessage()));
		}
						
		return "";
	}
	
	public String quitarDetalleSolicitud(Solicidetalle det){
		if(solicitudCabTmpGuardada==true){
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("La solicitud ya fue guardada."));
			return "";
		}
		
		try {
			manager.quitarDetalleSolicitudTem(det);
		} catch (Exception e) {
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(e.getMessage()));
		}
		
		return "";
	}
	
	public String guardarSolicitud(){
		if(solicitudCabTmpGuardada==true){
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("La solicitud ya fue guardada."));
			return "";
		}
		
		try {
			manager.guardarSolicitudTemporal(solicitudCabTem);
			solicitudCabTmpGuardada=true;
		} catch (Exception e) {
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(e.getMessage()));
		}
		
		return "";
	}
	
	//LISTADO DE RECURSOS
	public List<SelectItem> getlistaRecursosLibres(){
		List<SelectItem> listadoSI=new ArrayList<SelectItem>();
		List<Recurso> listadoRecurso= manager.findAllRecursosDisponibles(fecha, horainicio, horafin);
		for(Recurso p:listadoRecurso){
			SelectItem item=new SelectItem(p.getIdRecurso(), p.getNombre()+" - "+p.getCapacidad());
			listadoSI.add(item);
		}
		return listadoSI;
	}
	
	public List<SelectItem> getlistaRecursos(){
		List<SelectItem> listadoSI=new ArrayList<SelectItem>();
		List<Recurso> listadoRecurso= manager.findAllRecurso();
		for(Recurso p:listadoRecurso){
			SelectItem item=new SelectItem(p.getIdRecurso(), p.getNombre()+" - "+p.getCapacidad());
			listadoSI.add(item);
		}
		return listadoSI;
	}
	
	//metodo para asignar el RecursoTipo al Recurso
	public String asignarRecLibre(){
		manager.asignarRecurso(id_recurso);
		return "";
	}
	
	//JAVA.DATE TO SQL.TIME
	@SuppressWarnings("deprecation")
	public Time fechaAtiempo(Date fecha){
		DateFormat dateFormatH = new SimpleDateFormat("HH:mm");
		String hora = dateFormatH.format(fecha).toString();
		String [] array = hora.split(":");
		Time resp = new Time(Integer.parseInt(array[0]), Integer.parseInt(array[1]), 00);
		return resp;	
	}
	
	//-----------------------------APROBADOR------------------------------------------------------//
	//Tomar el id de estado general id_estadoSolicitud
	public String aprobarEstado(Solicicabecera solicitud){
		try {
			Soliciestado estado = manager.findSolicitudEstadoByID(3);//APROBADO
			manager.cambiarEstadoSolicitud(solicitud.getIdSolcab(), estado);
			manager.sendMail("juank20097@gmail.com", "xkalrbyylkkzfpnf", "nyqivessalo-6115@yopmail.com", "Peticion de Solicitud YACHAY/INNOPOLIS  ", "Le informamos que su solicitud de recursos SI fue Aprobada");
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,"Cambio correcto de estado", null));
		} catch (Exception e) {
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,"Error al cambiar el estado", null));
		}
		
		return "";	
	}
	
	public String negarEstado(Solicicabecera solicitud){
		try {
			Soliciestado estado = manager.findSolicitudEstadoByID(4);//NEGADO
			manager.cambiarEstadoSolicitud(solicitud.getIdSolcab(), estado);
			manager.sendMail("juank20097@gmail.com", "xkalrbyylkkzfpnf", "nyqivessalo-6115@yopmail.com", "Peticion de Solicitud YACHAY/INNOPOLIS  ", "Le informamos que su solicitud de recursos NO fue Aprobada");
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,"Cambio correcto de estado", null));
		} catch (Exception e) {
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,"Error al cambiar el estado", null));
		}
		
		return "";	
	}
	
	//CargarSolicitud
	public String cargarDatosSolicitud(Solicicabecera solicitud){
		direccion = solicitud.getDireccion();
		actividad = solicitud.getActividad();
		objetivo = solicitud.getObjetivo();
		justificacion = solicitud.getJustificacion();
		fecha = solicitud.getFecha();
		horainicio = solicitud.getHorainicio();
		horafin = solicitud.getHorafin();
		listDetalles = solicitud.getSolicidetalles();
		estadoSol = solicitud.getSoliciestado();
		//Cargar Listado----
		select = this.getlistaRecursosLibres();
		//Listas
		list_mas = new ArrayList<Solicidetalle>();
		list_menos = new ArrayList<Solicidetalle>();
		return "editsol?faces-redirect=true";
	}
	
	//Agregar quitar detalle
	public void agregarDetalle(){
		try {
			Solicidetalle  det = new Solicidetalle();
			det.setSolicicabecera(manager.findSolicitudCabeceraById(getId_sol()));
			det.setCapacidad(getcapacidad_recurso());
			det.setRecurso(manager.findRecursoByID(getId_recurso()));
			listDetalles.add(det);
			list_mas.add(det);
		} catch (Exception e) {
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,"No se pudo agregar el recurso", null));
		}
	}
	
	public void quitarDetalle(Solicidetalle detalle){
		try {
			listDetalles.remove(detalle);
			list_menos.add(detalle);
		} catch (Exception e) {
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,"No se pudo quitar el recurso", null));
		}
	}
	
	//guardar detalles
	public String finalizarSolicitudED(){
		String resp="";
		try {
			manager.editarDetallesSolicitud(id_sol, list_mas, list_menos);
			manager.aprobarSolicitudMOD(id_sol);
			resp = "";//FALTA DONDE VA XHTML
		} catch (Exception e) {
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,"Edicion err�nea", null));
		}
		return resp; 
	}
}
