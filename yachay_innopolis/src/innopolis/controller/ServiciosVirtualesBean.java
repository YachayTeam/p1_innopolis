package innopolis.controller;

import java.io.Serializable;
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
public class ServiciosVirtualesBean implements Serializable{
	private static final long serialVersionUID = 1L;
	private ManagerRecursosVirtuales managerservirt;
	private Integer idSvr;
	private Integer idestado;
	private Integer idservi;
	private Tipoestado tipoestado;
	private Tiposervicio tiposervicio;	
	private String apellidos;
	private int cedula;
	private String correo;
	private String nombres;
	private String tema;
	private List<Serviciosvirtregi> liservicioreg;
	private List<Tiposervicio> tiposervli;
	private List<Tipoestado> tipoestli;
	private String nomservicio;
	
	public ServiciosVirtualesBean()  
	{
		managerservirt = new ManagerRecursosVirtuales();
		tiposervicio = new Tiposervicio();
				
	}
	public Integer getIdestado() {
		return idestado;
	}
	public void setIdestado(Integer idestado) {
		this.idestado = idestado;
	}
	public Integer getIdservi() {
		return idservi;
	}
	public void setIdservi(Integer idservi) {
		this.idservi = idservi;
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
	
	public Tipoestado getTipoestado() {
		return tipoestado;
	}
	public void setTipoestado(Tipoestado tipoestado) {
		this.tipoestado = tipoestado;
	}
	public Tiposervicio getTiposervicio() {
		return tiposervicio;
	}
	public void setTiposervicio(Tiposervicio tiposervicio) {
		this.tiposervicio = tiposervicio;
	}
	public String getNomservicio() {
		return nomservicio;
	}
	public void setNomservicio(String nomservicio) {
		this.nomservicio = nomservicio;
	}
	
	//metodo para asignar el TipoServicio al registro
	public String asignarTiposerv(){
		managerservirt.asignarTiposerv(tiposervicio.getIdTp());	
			return "";
		}
	
	//metodo para asignar el Tipoestado al registro
	public String asignarTipoest(){
			managerservirt.asignarTipoest(tipoestado.getIdEstado());
			return "";
		}
	
	//metodo para listar los registros
	public List<Serviciosvirtregi> getListRegServi(){
			return managerservirt.findAllRServiciosVirtuales();
		}
	
	//metodo para listar los servicios
	public List<Tiposervicio> getListServicios(){
			return managerservirt.findAllTipoServicio();
	}
	
	//metodo para crear registros
	public String crearRegistroServ(){
		try {
			managerservirt.insertarserviciovirtual(cedula, nombres, apellidos, tema, correo);
			//reiniciamos datos (limpiamos el formulario)
			cedula=0;
			nombres="";
			correo="";
			tema="";
			apellidos="";
			tipoestado = managerservirt.findEstadoTipoByID(1); // numero id del estado q quieres q sea
			tiposervicio = null;
			idSvr=null;
			FacesContext context = FacesContext.getCurrentInstance();
	        context.addMessage(null, new FacesMessage("Registrado..!!!",  "Registro Almacenado ") );
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		};
		return "Serviciovirual";
	}
	
	//metodo para modificar los registros
	public String actualizarRegistro(){
		managerservirt.editarserviciovirtual(idSvr, new Integer(cedula), nombres, apellidos, tema, correo, tipoestado.getIdEstado(), tiposervicio.getIdTp());
		//limpiamos los datos
		cedula=0;
		nombres="";
		correo="";
		tema="";
		apellidos="";
		try {
			tipoestado = managerservirt.findEstadoTipoByID(1);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		tiposervicio = null;
		idSvr=null;
		FacesContext context = FacesContext.getCurrentInstance();
        context.addMessage(null, new FacesMessage("Actualizado..!!!",  "Registro Actualizado ") );
		return "AprovadorServiciovirtual";
	}

	//metodo para cargar la lista de servicios virtuales
	public String Cargarregistros(Serviciosvirtregi serv)
		{
		idSvr= serv.getIdSvr();
		cedula= serv.getCedula();
		nombres= serv.getNombres();
		apellidos= serv.getApellidos();
		correo= serv.getCorreo();
		tema= serv.getTema();
		tipoestado= serv.getTipoestado();
		tiposervicio= serv.getTiposervicio();
		return "modservedi";
	}
		
		//metodo para crear nuevo tiposervicios
		public String crearNuevoTipoServicio(){
			try {
				tiposervicio = null;
				managerservirt.insertarTipoServicio(getNomservicio());						
				FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Creado correctamente",null));
			} catch (Exception e) {
				FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,"Error al ingresar nuevo tipo",null));
				FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,e.getMessage(),null));
			}
			return "";
		}
		
		//metodo cargar todos los tiposervicios
		public String cargarDatostiposerv(Tiposervicio  tiposerv){
			idservi= tiposerv.getIdTp();
			nomservicio= tiposerv.getNombreServicio();					
			return "editarserv";
		}
				
		//metodo para listar los TiposServicio
		public List<SelectItem> getListaTiposerv(){
				List<SelectItem> listadoTS=new ArrayList<SelectItem>();
				List<Tiposervicio> listadoServicio=managerservirt.findAllTipoServicio();
				
				for(Tiposervicio t:listadoServicio){
					SelectItem item=new SelectItem(t.getIdTp(),t.getNombreServicio());
					listadoTS.add(item);
				}
				return listadoTS;
			}
		
		//eliminar un servicio
		public String EliminarServicio(Tiposervicio tpser) {

			try {				
				managerservirt.eliminarServicio(tpser.getIdTp());				
			} catch (Exception e) {
				FacesContext.getCurrentInstance().addMessage(null,
						new FacesMessage(e.getMessage()));
				e.printStackTrace();
			}
			return "";
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
		
		//metodo para cambiar el estado del registro
		public String cambiarEstado(Serviciosvirtregi ser){
			try {
					FacesContext context = FacesContext.getCurrentInstance();
		        	context.addMessage(null, new FacesMessage("INFORMACION",managerservirt.cambioDisEstado(ser.getIdSvr())));
				} catch (Exception e) {
					System.out.println(e.getMessage());
				}
			return "";
		}
	
		//accion para modificar los servicios
		public String ActualizarServicio(){
			String resp ="";
				try {
					managerservirt.editartiposervicio(getIdservi(),getNomservicio());
					setNomservicio("");
					setIdservi(0);		
					resp = "CrudServicio";
					FacesContext context = FacesContext.getCurrentInstance();
			        context.addMessage(null, new FacesMessage("Actualizado..!!!",  "Servicio Actualizado ") );
					}
				catch (Exception e) {
					FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,"Error al modificar servicio",null));
					FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,e.getMessage(),null));
				}
			return resp;
		}	
				
		//------ Envios paginas--------//				
		public String irAprovador(){								       
	      //limpiamos los datos
	        cedula=0;
			nombres="";
			correo="";
			tema="";
			apellidos="";
				try {
						tipoestado = managerservirt.findEstadoTipoByID(1);
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} // numero id del estado q quieres q sea;
					tiposervicio = new Tiposervicio();
					idSvr=0;
			return "AprovadorServiciovirtual";					
		}		
				
		public String irAprovadorpag(){		   
		//limpiamos los datos				        
				try {
						tiposervicio = managerservirt.findServicioTipoByID(1);
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} // numero id del estado q quieres q sea;
					tiposervicio = new Tiposervicio();
					idSvr=0;
				return "CrudServicio";					
		}	
		
		
		//correo
		//Tomar el id de estado general id_estadoSolicitud
		public String enviarmensaje(Serviciosvirtregi serv){
			try {									
				managerservirt.sendMail("juank20097@gmail.com", "xkalrbyylkkzfpnf", serv.getCorreo(), "Notificación de Centro de Emprendimiento","El usuario con apellido "+serv.getApellidos()+" con nombre "+serv.getNombres()+"; le informamos que su petición de registro a "+serv.getTiposervicio().getNombreServicio()+" fue "+serv.getTipoestado().getNombreestado()+".");				
				FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,"Enviado correctamente al correo", null));
			} catch (Exception e) {
				FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,"Error al enviar correo", null));
			}
			
			return "";	
		}		
}