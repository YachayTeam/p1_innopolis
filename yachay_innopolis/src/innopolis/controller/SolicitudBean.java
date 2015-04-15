package innopolis.controller;

import java.sql.Time;
import java.util.Date;
import java.util.List;

import innopolis.entities.Solicicabecera;
import innopolis.manager.ManagerReservas;

import javax.annotation.ManagedBean;
import javax.faces.bean.SessionScoped;

@SessionScoped
@ManagedBean
public class SolicitudBean {
	private ManagerReservas manager;
	
	//Atributo de solicitud
	//Cabecera
	private String direccion;
	private String actividad; 
	private Date fecha; 
	private Time horafin; 
	private Time horainicio;
	
	//Detalles
	private Integer id_recurso;
	private Integer cantidad_recurso;
	private Solicicabecera solicitudCabTem;
	private boolean solicitudCabTmpGuardada;
	private List<Solicicabecera> listadoSolCab;
	
	public SolicitudBean() {
		manager = new ManagerReservas();
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

	public Integer getCantidad_recurso() {
		return cantidad_recurso;
	}

	public void setCantidad_recurso(Integer cantidad_recurso) {
		this.cantidad_recurso = cantidad_recurso;
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
	
	//Metodos proceso de ejecucion
	public String crearNuevaSolicitud(){
		solicitudCabTem = manager.crearSolicitudTmp(getDireccion(), getActividad(), getFecha(), getHorafin(), getHorainicio()); 
		id_recurso=0; 
		cantidad_recurso = 0;
		solicitudCabTmpGuardada=false;
		return "";
	}
	
	
	
	
	
	

}
