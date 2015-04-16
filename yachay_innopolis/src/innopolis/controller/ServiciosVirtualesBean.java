package innopolis.controller;

import java.util.ArrayList;
import java.util.List;

import innopolis.entities.Serviciosvirtregi;
import innopolis.entities.Tipoestado;
import innopolis.entities.Tiposervicio;
import innopolis.manager.ManagerRecursosVirtuales;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;


@SessionScoped
@ManagedBean
public class ServiciosVirtualesBean {
	private ManagerRecursosVirtuales managerservirt;
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
		managerservirt = new ManagerRecursosVirtuales();
		
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
	//metodo para asignar el TipoServicio al registro
	public String getasignarTiposerv(){
		managerservirt.asignarTiposerv(idtiposervicio);	
			return "";
		}
	//metodo para asignar el Tipoestado al registro
	public String getasignarTipoest(){
			managerservirt.asignarTipoest(idtipoestado);
			return "";
		}
	public List<Serviciosvirtregi> getListRegServi(){
			return managerservirt.findAllRServiciosVirtuales();
		}
	//accion para invocar el manager y crear registro
	public String crearRegistroServ(){
		try {
			managerservirt.insertarserviciovirtual(cedula, nombres, apellidos, tema, correo);
			//reiniciamos datos (limpiamos el formulario)
			cedula=0;
			nombres="";
			correo="";
			tema="";
			apellidos="";
			idtipoestado=1;
			idtiposervicio=0;
			idSvr=0;
			FacesContext context = FacesContext.getCurrentInstance();
	        context.addMessage(null, new FacesMessage("Registrado..!!!",  "Recurso Almacenado ") );
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		};
		return "servicio";
	}
	//accion para modificar los recursos
	public String actualizarRegistro(){
		managerservirt.editarserviciovirtual(idSvr, cedula, nombres, apellidos, tema, correo, idtipoestado, idtiposervicio);
		//limpiamos los datos
		cedula=0;
		nombres="";
		correo="";
		tema="";
		apellidos="";
		idtipoestado=0;
		idtiposervicio=0;
		idSvr=0;
		FacesContext context = FacesContext.getCurrentInstance();
        context.addMessage(null, new FacesMessage("Actualizado..!!!",  "Recurso Actualizado ") );
		return "recurso";
		
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
		//metodo para mostrar los TiposServicio
		public List<SelectItem> getListaTiposerv(){
				List<SelectItem> listadoTS=new ArrayList<SelectItem>();
				List<Tiposervicio> listadoServicio=managerservirt.findAllTipoServicio();
				
				for(Tiposervicio t:listadoServicio){
					SelectItem item=new SelectItem(t.getIdTp(),t.getNombreServicio());
					listadoTS.add(item);
				}
				return listadoTS;
			}
		//metodo para mostrar los TiposEstado
				public List<SelectItem> getListaTipoestado(){
						List<SelectItem> listadoTE=new ArrayList<SelectItem>();
						List<Tipoestado> listadoEstado=managerservirt.findAllTipoEstado();
						
						for(Tipoestado t:listadoEstado){
							SelectItem item=new SelectItem(t.getIdEstado(),t.getNombreestado());
							listadoTE.add(item);
						}
						return listadoTE;
					}
		}