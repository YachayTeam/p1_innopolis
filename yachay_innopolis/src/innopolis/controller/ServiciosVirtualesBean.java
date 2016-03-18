package innopolis.controller;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import innopolis.entidades.Estadotiposervicio;
import innopolis.entidades.Serviciosvirtregi;
import innopolis.entidades.Tipoestado;
import innopolis.entidades.Tiposervicio;
import innopolis.entidades.help.UsuarioHelp;
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
	private Integer idestadotipser;
	
	private Tipoestado tipoestado;
	private Tiposervicio tiposervicio;
	private Estadotiposervicio estadotipser;
	
	private String apellidos;
	private int cedula;
	private String correo;
	private String nombres;
	private String tema;
	private String sms;
	
	private String nombreestipser;
	private String url;
	
	private List<Serviciosvirtregi> liservicioreg;
	private List<Tiposervicio> tiposervli;
	private List<Tipoestado> tipoestli;
	private String nomservicio;
	
	private UsuarioHelp session;
	
	public ServiciosVirtualesBean()  
	{
		session = SessionBean.verificarSession();
		managerservirt = new ManagerRecursosVirtuales();
		tiposervicio = new Tiposervicio();
		estadotipser = new Estadotiposervicio();	
	}	
	
	/**
	 * @return the idestadotipser
	 */
	public Integer getIdestadotipser() {
		return idestadotipser;
	}
	/**
	 * @param idestadotipser the idestadotipser to set
	 */
	public void setIdestadotipser(Integer idestadotipser) {
		this.idestadotipser = idestadotipser;
	}

	/**
	 * @return the estadotipser
	 */
	public Estadotiposervicio getEstadotipser() {
		return estadotipser;
	}

	/**
	 * @param estadotipser the estadotipser to set
	 */
	public void setEstadotipser(Estadotiposervicio estadotipser) {
		this.estadotipser = estadotipser;
	}

	/**
	 * @return the nombreestipser
	 */
	public String getNombreestipser() {
		return nombreestipser;
	}

	/**
	 * @param nombreestipser the nombreestipser to set
	 */
	public void setNombreestipser(String nombreestipser) {
		this.nombreestipser = nombreestipser;
	}

	
	/**
	 * @return the session
	 */
	public UsuarioHelp getSession() {
		return session;
	}

	/**
	 * @param session the session to set
	 */
	public void setSession(UsuarioHelp session) {
		this.session = session;
	}
	

	/**
	 * @return the url
	 */
	public String getUrl() {
		return url;
	}

	/**
	 * @param url the url to set
	 */
	public void setUrl(String url) {
		this.url = url;
	}


	public String getSms() {
		return sms;
	}

	public void setSms(String sms) {
		this.sms = sms;
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
	//metodo para cargar la lista de servicios virtuales
	public String Cargarregistros(Serviciosvirtregi serv)
		{
		idSvr= serv.getIdSvr();
		tema= serv.getTema();
		tipoestado= serv.getTipoestado();
		tiposervicio= serv.getTiposervicio();
		sms = serv.getSms(); 
		return "modservedi";
	}
		
		//metodo para crear nuevo tiposervicios
		public String crearNuevoTipoServicio(){
			try {
				if (this.cnombreestipser(nomservicio)==true){
					FacesContext context = FacesContext.getCurrentInstance();
			        context.addMessage(null, new FacesMessage("Nombre Repetido..!!!",  "El Nombre ya esta siendo utilizado") );
				}
				else{								
				managerservirt.insertarTipoServicio(getIdestadotipser(), nomservicio, url);
				tiposervicio = null;
				url="";
				//limpiar formulario
				estadotipser = managerservirt.findEstadoTiposervByID(1); // numero id del estado q quieres q sea
				setNomservicio("");
				FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Creado correctamente",null));
				}
			} catch (Exception e) {
				FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,"Error al ingresar nuevo tipo",null));
				FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,e.getMessage(),null));
			}
			
			return "";
			
		}
		
// metodo para comprobar el nombre servicio
			public boolean cnombreestipser(String nombretipser){
				List<Tiposervicio> u = managerservirt.findAllTipoServicio();
				for (Tiposervicio us : u) {
					if (nomservicio.equals(us.getNombreServicio())){
						return true;
					}
				}
				return false;
			}	
			
			
			
			
		//metodo cargar todos los tiposervicios
		public String cargarDatostiposerv(Tiposervicio  tiposerv){
			idservi= tiposerv.getIdTp();			
			nomservicio= tiposerv.getNombreServicio();
			url= tiposerv.getUrl();
			nombreestipser = tiposerv.getEstadotiposervicio().getEts();
			return "editarserv";
		}
				
		//metodo para listar los TiposServicio
		public List<SelectItem> getListaTiposerv(){
				List<SelectItem> listadoTS=new ArrayList<SelectItem>();
				List<Tiposervicio> listadoServicio=managerservirt.findAllTipoServicio();
				
				for(Tiposervicio t:listadoServicio){
					SelectItem item=new SelectItem(t.getIdTp(),t.getNombreServicio(),t.getNombreServicio());					
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
		
			
		public String cambiarEstadoServ(Tiposervicio ser){
			try {					
					FacesContext context = FacesContext.getCurrentInstance();
		        	context.addMessage(null, new FacesMessage("INFORMACION",managerservirt.cambioEstadotiposer(ser.getIdTp())));
				} catch (Exception e) {
					System.out.println(e.getMessage());
				}
			return "";
		}
		
		
		//accion para modificar los servicios
		public String ActualizarServicio(){
			String resp ="";
				try {
					managerservirt.editartiposervicio(getIdservi(),getNomservicio(), getUrl());
					setNomservicio("");
					setUrl("");
					url="";
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
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,"Volver", null));
				try {
						tipoestado = managerservirt.findEstadoTipoByID(1);						
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} // numero id del estado q quieres q sea;
			tiposervicio = new Tiposervicio();
			idSvr=0;
					
			return "/aprobador/aprovadorserviciovirtual";					
		}		
				
		public String irAprovadorpag(){		   
		//limpiamos los datos				        
				try {
						tiposervicio = managerservirt.findServicioTipoByID(1);
						FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,"Actualizacion Cancelada", null));
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} // numero id del estado q quieres q sea;
					tiposervicio = new Tiposervicio();
					idSvr=0;
					nomservicio="";
					url="";
				return "/admin/crudservicio";					
		}	
		
		//metodo para deshabilitar botones
		 private boolean disable;

		    public void MainTable()
		    {
		        disable = false;
		    }
		    public boolean isDisable()
		    {
		        return disable;
		    }

		    public void setDisable(boolean disable)
		    {
		        this.disable = disable;
		    }
}