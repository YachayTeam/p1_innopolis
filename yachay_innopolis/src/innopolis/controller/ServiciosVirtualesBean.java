package innopolis.controller;

import java.util.ArrayList;
import java.util.List;

import innopolis.entities.Serviciosvirtregi;
import innopolis.entities.Tipoestado;
import innopolis.entities.Tiposervicio;
import innopolis.manager.ManagerRecursosVirtuales;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;


@SessionScoped
@ManagedBean
public class ServiciosVirtualesBean {
	private ManagerRecursosVirtuales managerservirt= new ManagerRecursosVirtuales();
	private Integer idSvr;
	private Integer idtipoestado;
	private Integer idtiposervicio;
	private String apellidos;
	private int cedula;
	private String correo;
	private String nombres;
	private String tema;
	private List<Serviciosvirtregi> liservicioreg;
	private List<Tiposervicio> tiposervli;
	private List<Tipoestado> tipoestli;
	
	
	public ServiciosVirtualesBean()
	{
		liservicioreg = new ArrayList<Serviciosvirtregi>();
		
	}
	public void guardarregistros()
	{		
			Serviciosvirtregi serv = new Serviciosvirtregi();
			serv.setNombres(nombres);
			serv.setApellidos(apellidos);
			serv.setCedula(cedula);
			serv.setCorreo(correo);
			serv.setTema(tema);			
			try {
				Tipoestado te = managerservirt.EstadoByID(idtipoestado);
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			try {
				Tiposervicio ts = managerservirt.ServicioTipoByID(idtiposervicio);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}			
			//serv.setTipoestado(idtipoestado);
			//serv.setTiposervicio(idtiposervicio);
			//no se como insertar de tipo est o tiposer		
			
	}
	public String Cargarregistros(Serviciosvirtregi serv)
	{
		idSvr= serv.getIdSvr();
		cedula= serv.getCedula();
		nombres= serv.getNombres();
		apellidos= serv.getApellidos();
		correo= serv.getCorreo();
		tema= serv.getTema();
		idtipoestado= serv.getTipoestado().getIdEstado();
		idtiposervicio= serv.getTiposervicio().getIdTp();
		return "registros_cargar";
				
	}		
	
	public ManagerRecursosVirtuales getManagerservirt() {
		return managerservirt;
	}
	public void setManagerservirt(ManagerRecursosVirtuales managerservirt) {
		this.managerservirt = managerservirt;
	}
	public List<Serviciosvirtregi> getServicioreg() {
		return liservicioreg;
	}
	public void setServicioreg(List<Serviciosvirtregi> liservicioreg) {
		this.liservicioreg = liservicioreg;
	}		
	public Integer getIdSvr() {
		return idSvr;
	}
	public void setIdSvr(Integer idSvr) {
		this.idSvr = idSvr;
	}
	public Integer getIdtipoestado() {
		return idtipoestado;
	}
	public void setIdtipoestado(Integer idtipoestado) {
		this.idtipoestado = idtipoestado;
	}
	public Integer getIdtiposervicio() {
		return idtiposervicio;
	}
	public void setIdtiposervicio(Integer idtiposervicio) {
		this.idtiposervicio = idtiposervicio;
	}
	public String getApellidos() {
		return apellidos;
	}
	public void setApellidos(String apellidos) {
		this.apellidos = apellidos;
	}
	public int getCedula() {
		return cedula;
	}
	public void setCedula(int cedula) {
		this.cedula = cedula;
	}
	public String getCorreo() {
		return correo;
	}
	public void setCorreo(String correo) {
		this.correo = correo;
	}
	public String getNombres() {
		return nombres;
	}
	public void setNombres(String nombres) {
		this.nombres = nombres;
	}
	public String getTema() {
		return tema;
	}
	public void setTema(String tema) {
		this.tema = tema;
	}	
	public List<Tiposervicio> getTiposervli() {
		return tiposervli;
	}
	public void setTiposervli(List<Tiposervicio> tiposervli) {
		this.tiposervli = tiposervli;
	}
	public List<Tipoestado> getTipoestli() {
		return tipoestli;
	}
	public void setTipoestli(List<Tipoestado> tipoestli) {
		this.tipoestli = tipoestli;
	}
	
	
	
	
	
	
	
}
