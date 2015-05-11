package innopolis.controller;

import innopolis.entities.Tipoestadousr;
import innopolis.entities.Tipologin;
import innopolis.entities.Tipousr;
import innopolis.entities.Usuario;
import innopolis.manager.ManagerLogin;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;


@SessionScoped
@ManagedBean
public class LoginBean implements Serializable{
	private static final long serialVersionUID = 1L;
	
	private ManagerLogin managerlogin;
	private Tipologin tipologin;
	private Tipousr tipousuario;
	private Tipoestadousr tipoestusr;
	
	private Integer idUsr;
	private Integer id_tipologin;
	private Integer id_tipousr;	
	private Tipousr tipousr;	
	private String alias;
	private String apellido;
	private String correo;
	private String nombre;
	private String password;
	private String nomtipologin;
	private String descripcion;
	
	private List<Usuario> usuarioli;
	private List<Tipologin> tipologinli;
	private List<Tipousr> tipousrli;
	private List<Tipoestadousr> tipoestusrli;
	
		public LoginBean()  
	{
		managerlogin = new ManagerLogin();
		tipologin = new Tipologin();
		tipousuario = new Tipousr();	
		tipoestusr = new Tipoestadousr();
	}	
	
	
	public ManagerLogin getManagerlogin() {
		return managerlogin;
	}
	public void setManagerlogin(ManagerLogin managerlogin) {
		this.managerlogin = managerlogin;
	}
	public Tipologin getTipologin() {
		return tipologin;
	}
	public void setTipologin(Tipologin tipologin) {
		this.tipologin = tipologin;
	}
	public Tipousr getTipousuario() {
		return tipousuario;
	}
	public void setTipousuario(Tipousr tipousuario) {
		this.tipousuario = tipousuario;
	}	
	public Integer getIdUsr() {
		return idUsr;
	}
	public void setIdUsr(Integer idUsr) {
		this.idUsr = idUsr;
	}
	public Integer getId_tipologin() {
		return id_tipologin;
	}
	public void setId_tipologin(Integer id_tipologin) {
		this.id_tipologin = id_tipologin;
	}
	public Integer getId_tipousr() {
		return id_tipousr;
	}
	public void setId_tipousr(Integer id_tipousr) {
		this.id_tipousr = id_tipousr;
	}
	public Tipousr getTipousr() {
		return tipousr;
	}
	public void setTipousr(Tipousr tipousr) {
		this.tipousr = tipousr;
	}
	public String getAlias() {
		return alias;
	}
	public void setAlias(String alias) {
		this.alias = alias;
	}
	public String getApellido() {
		return apellido;
	}
	public void setApellido(String apellido) {
		this.apellido = apellido;
	}
	public String getCorreo() {
		return correo;
	}
	public void setCorreo(String correo) {
		this.correo = correo;
	}
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public List<Usuario> getUsuarioli() {
		return usuarioli;
	}
	public void setUsuarioli(List<Usuario> usuarioli) {
		this.usuarioli = usuarioli;
	}
	public List<Tipologin> getTipologinli() {
		return tipologinli;
	}
	public void setTipologinli(List<Tipologin> tipologinli) {
		this.tipologinli = tipologinli;
	}
	public List<Tipousr> getTipousrli() {
		return tipousrli;
	}
	public void setTipousrli(List<Tipousr> tipousrli) {
		this.tipousrli = tipousrli;
	}	
	public String getNomtipologin() {
		return nomtipologin;
	}
	public void setNomtipologin(String nomtipologin) {
		this.nomtipologin = nomtipologin;
	}
	public String getDescripcion() {
		return descripcion;
	}
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}
	public Tipoestadousr getTipoestusr() {
		return tipoestusr;
	}
	public void setTipoestusr(Tipoestadousr tipoestusr) {
		this.tipoestusr = tipoestusr;
	}
	public List<Tipoestadousr> getTipoestusrli() {
		return tipoestusrli;
	}
	public void setTipoestusrli(List<Tipoestadousr> tipoestusrli) {
		this.tipoestusrli = tipoestusrli;
	}
	
	//metodo para listar los registros
		public List<Usuario> getListRegServi(){
				return managerlogin.findAllUsuarios();
			}
		
	//metodo para listar los servicios
		public List<Tipologin> getListTipoLogin(){
				return managerlogin.findAllTipoLogin();
		}

	//metodo para crear usuarios
	public String crearUsuario(){
			try {
				managerlogin.insertarusuarios(alias, apellido, correo, nombre, password);
				//reiniciamos datos (limpiamos el formulario)
				alias="";				
				nombre="";
				apellido="";
				correo="";
				password="";					
				idUsr=null;
				tipoestusr = managerlogin.EstadoByID(1);
				FacesContext context = FacesContext.getCurrentInstance();
		        context.addMessage(null, new FacesMessage("Registrado..!!!",  "Usuario Almacenado") );
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			};
			return "pagina";
		}
	
	//metodo para modificar los usuarios
		public String actualizarRegistro(){
			managerlogin.editarusuario(idUsr, alias, apellido, correo, nombre, password, tipoestusr.getIdTipoestadousr());
			//limpiamos los datos
			alias="";
			nombre="";
			correo="";			
			apellido="";
			password="";
			idUsr=null;
			try {
				tipoestusr = managerlogin.EstadoByID(1);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} 
			FacesContext context = FacesContext.getCurrentInstance();
	        context.addMessage(null, new FacesMessage("Actualizado..!!!",  "Usuario Actualizado ") );
			return "AdministracionUsuarios";
		}

	//metodo para cargar la lista de usuarios
		public String Cargarusuarios(Usuario usr)
			{
			idUsr= usr.getIdUsr();			
			nombre= usr.getNombre();
			apellido= usr.getApellido();
			correo= usr.getCorreo();
			alias=usr.getAlias();
			password=usr.getPassword(); 
			tipoestusr= usr.getTipoestadousr();
			return "modusr";
		}
			
	  //metodo para crear nuevo tipologin
		public String crearTipoLogin(){
			try {
			    	tipologin = null;			    	
					managerlogin.insertarTipologin(getNomtipologin(), getDescripcion());			    						
			    	FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Creado correctamente",null));
			    } catch (Exception e) {
					FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,"Error al ingresar nuevo tipo",null));
						FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,e.getMessage(),null));
				}
					return "";
				}
		
		//metodo para modificar los usuarios
		public String actualizarTipologin(){
			managerlogin.editartipologin(id_tipologin, nomtipologin, descripcion);			
			//limpiamos los datos
			descripcion="";
			nomtipologin="";
			id_tipologin=null;			
			FacesContext context = FacesContext.getCurrentInstance();
	        context.addMessage(null, new FacesMessage("Actualizado..!!!",  "Tipologin Actualizado ") );
			return "AdministracionUsuarios";
			}
		
      //metodo para cargar la lista de tipologin
		public String Cargartipologin(Tipologin tplog)
			{
			nomtipologin= tplog.getTipologin();
			descripcion=  tplog.getDescripcion();	
			return "modtipolog";
		}
				
	  //eliminar un tipologin
		public String Eliminartipologin(Tipologin tplog) {

					try {				
						managerlogin.eliminarTipologin(tplog.getIdTipologin());									
					} catch (Exception e) {
						FacesContext.getCurrentInstance().addMessage(null,
								new FacesMessage(e.getMessage()));
						e.printStackTrace();
					}
					return "";
				}
	
	//metodo para mostrar los TiposEstado
		public List<SelectItem> getListaTipoestadousr(){
			List<SelectItem> listadoTEU=new ArrayList<SelectItem>();
			List<Tipoestadousr> listadoEstadoU=managerlogin.findAllTipoEstadousr();
				for(Tipoestadousr t:listadoEstadoU){
					SelectItem item=new SelectItem(t.getIdTipoestadousr(),t.getNombreestado());
					listadoTEU.add(item);
				}
					return listadoTEU;
		}
		
		
		//metodo para cambiar el estado del usuarios
				public String cambiarEstado(Usuario usr){
					try {
							managerlogin.cambioDisEstadousr(usr.getIdUsr());						
							FacesContext context = FacesContext.getCurrentInstance();
				        	context.addMessage(null, new FacesMessage("INFORMACION",managerlogin.cambioDisEstadousr(usr.getIdUsr())));
						} catch (Exception e) {
							System.out.println(e.getMessage());
						}
					return "";
				}
		//------ Envios paginas--------//				
				public String irAprovador(){								       
			      //limpiamos los datos
			        nombre="";
			        apellido="";
					correo="";
					alias="";
					password="";		
					try {
						tipologin = managerlogin.TipoLoginByID(1);						
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} // numero id del estado q quieres q sea;	
							tipousr = new Tipousr();
							id_tipousr=0;							
					return "AdministracionUsuarios";					
				}
		
				public String irAprovadorpag(){		   
					//limpiamos los datos				        
							try {
								tipologin = managerlogin.TipoLoginByID(1);
								} catch (Exception e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								} // numero id del estado q quieres q sea;
							tipologin = new Tipologin();						
							id_tipousr=0;						
							return "Crudtipologin";					
					}	
					
		
		
	
}
