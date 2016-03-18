package innopolis.controller;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import innopolis.entidades.Estadotiposervicio;
import innopolis.entidades.Serviciosvirtregi;
import innopolis.entidades.Tipoestado;
import innopolis.entidades.Tiposervicio;
import innopolis.entidades.Usuario;
import innopolis.entidades.help.UsuarioHelp;
import innopolis.manager.EnvioMensaje;
import innopolis.manager.ManagerRecursosVirtuales;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;


@SessionScoped
@ManagedBean
public class ServiciosVirtualesApBean implements Serializable{
	private static final long serialVersionUID = 1L;
	private ManagerRecursosVirtuales managerservirt;
	private Integer idSvr;
	private Integer idestado;
	private Integer idservi;
	private Integer idestadotipser;
	private String smscor;
	
	private Tipoestado tipoestado;
	private Tiposervicio tiposervicio;
	private Estadotiposervicio estadotipser;
	private Usuario usuario;
	
	private String apellidos;
	private String cedula;
	private String correo;
	private String nombres;
	private String tema;
	private String sms;
	
	private String nombreestipser;
	
	
	private List<Serviciosvirtregi> liservicioreg;
	private List<Tiposervicio> tiposervli;
	private List<Tipoestado> tipoestli;
	private List<Usuario> usu;
	
	private String nomservicio;
	
	private UsuarioHelp session;
	
	public ServiciosVirtualesApBean()  
	{
		session = SessionBean.verificarSession();
		managerservirt = new ManagerRecursosVirtuales();
		tiposervicio = new Tiposervicio();
		estadotipser = new Estadotiposervicio();	
		usuario = new Usuario();
	}	
	/**
	 * @return the usu
	 */
	public List<Usuario> getUsu() {
		return usu;
	}

	/**
	 * @param usu the usu to set
	 */
	public void setUsu(List<Usuario> usu) {
		this.usu = usu;
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
	 * @return the usuario
	 */
	public Usuario getUsuario() {
		return usuario;
	}
	/**
	 * @param usuario the usuario to set
	 */
	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
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
	 * @return the smscor
	 */
	public String getSmscor() {
		return smscor;
	}

	/**
	 * @param smscor the smscor to set
	 */
	public void setSmscor(String smscor) {
		this.smscor = smscor;
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
	public String getCedula() {
		return cedula;
	}
	public void setCedula(String cedula) {
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
	public String Cargarregistros(Serviciosvirtregi serv){
		idSvr= serv.getIdSvr();
		cedula= serv.getUsuario().getCedula();
		nombres= serv.getUsuario().getNombre();
		apellidos=serv.getUsuario().getApellido();
		correo= serv.getUsuario().getCorreo();
		tema= serv.getTema();
		tipoestado= serv.getTipoestado();
		tiposervicio= serv.getTiposervicio();
		sms = serv.getSms(); 
		return "";
	}
	
		
		//metodo cargar todos los tiposervicios
		public String cargarDatostiposerv(Tiposervicio  tiposerv){
			idservi= tiposerv.getIdTp();			
			nomservicio= tiposerv.getNombreServicio();
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
		
		public String aprobarSolicitud(Serviciosvirtregi ser){
			try {
				if(ser.getTipoestado().getNombreestado().equals(("Aprobado")))
	        	{
					FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,"La inscripción ya esta aprobada", null));
     		
		        	}
		        	else
		        	{
				FacesContext context = FacesContext.getCurrentInstance();
	        	context.addMessage(null, new FacesMessage("INFORMACION",managerservirt.cambioDisEstadoapro(ser.getIdSvr())));		
	        	managerservirt.cambioSMS(ser.getIdSvr());
		        	}
			} catch (Exception e) {
				FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,"Error al aprobar inscripcion", null));
			}
			return "";
		}
		
		public String negarSolicitud(Serviciosvirtregi ser){
			try {
				if(ser.getTipoestado().getNombreestado().equals(("Negado")))
	        	{
					FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,"La inscripción ya esta negada", null));
     		
		        	}
		        	else
		        	{
		        	FacesContext context = FacesContext.getCurrentInstance();
			        context.addMessage(null, new FacesMessage("INFORMACION",managerservirt.cambioDisEstadonega(ser.getIdSvr())));
		        	managerservirt.cambioSMS(ser.getIdSvr());
		        	}
				} catch (Exception e) {
					FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,"Error al negar inscripcion", null));
				}			
			return "";
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
				
		//------ Envios paginas--------//				
		public String irAprovador(){								       
	      //limpiamos los datos
	        cedula="";
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
				return "/admin/crudservicio";					
		}	
		
		
		public String cambioEnvioSms(){
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,"Notificación Aceptada ", null));
			return "";
		}
		
		public String asignarsms(Serviciosvirtregi serv){
			idSvr= serv.getIdSvr();
			tema= serv.getTema();
			tipoestado= serv.getTipoestado();
			tiposervicio= serv.getTiposervicio();
			usuario = serv.getUsuario();
			sms = serv.getSms();
			correo= serv.getUsuario().getCorreo();
			smscor="El usuario con apellido "+serv.getUsuario().getApellido()+" con nombre "+serv.getUsuario().getNombre()+"; le informamos que su petición de registro a "+serv.getTiposervicio().getNombreServicio()+" fue "+serv.getTipoestado().getNombreestado()+".";
			return "";
		}
		//correo
		//Tomar el id de estado general id_estadoSolicitud
		public String enviarmensaje(Serviciosvirtregi serv){			
			try {
				if(sms.equals("No Notificado"))
				{
					if(tipoestado.getNombreestado().equals("Pendiente"))
					{
						FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,"Indique si se aprueba o niega la solicitud a un servicio virtual ", null));
					}
					else
					{
					managerservirt.cambioSMSenvio(idSvr);
					EnvioMensaje.sendMail(correo, "Petición de Solicitud a Servicios Virtuales YACHAY/REGECE  ", smscor);
					FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,"Enviado correctamente al correo", null));
					}
				}				
				else
				{
					FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,"Ya se ha enviado al correo la notificación", null));
				}				
					
			} catch (Exception e) {
				FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,"Error al enviar correo", null));
			}			
			return "/aprobador/aprovadorserviciovirtual";	
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