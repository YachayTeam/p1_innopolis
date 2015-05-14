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
	private String nomtipolog;
	private String descripcion;
	
	private List<Usuario> usuarioli;
	private List<Tipologin> tipologinli;
	private List<Tipousr> tipousrli;
	private List<Tipoestadousr> tipoestusrli;
	
	private Integer[] arrayTipoLogin;
	
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
	public String getNomtipolog() {
		return nomtipolog;
	}
	public void setNomtipolog(String nomtipolog) {
		this.nomtipolog = nomtipolog;
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
	
	public Integer[] getArrayTipoLogin() {
		return arrayTipoLogin;
	}
	
	public void setArrayTipoLogin(Integer[] arrayTipoLogin) {
		this.arrayTipoLogin = arrayTipoLogin;
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
				managerlogin.insertarusuarios(alias, apellido, correo, nombre, password, arrayTipoLogin);
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
	
	//metodo para registrar usuariosexternos a la funcionalidad
		public String registrarUsr(){
				try {
					managerlogin.registrarUsuario(alias, apellido, correo, nombre, password);
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
		public String actualizarusuario() throws Exception{
			managerlogin.editarusuario(idUsr, correo, password, arrayTipoLogin);
			//limpiamos los datos
			alias="";
			nombre="";
			correo="";			
			apellido="";
			password="";			
			try {
				tipoestusr = managerlogin.EstadoByID(1);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} 
			idUsr=null;
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
			//List<Tipousr> lst = usr.getTipousrs();
			//Integer [] arreglo = new Integer[lst.size()];
			//arreglo = lst.toArray(arreglo);
			//arrayTipoLogin = arreglo;
			return "modusr";
		}
			
	  //metodo para crear nuevo tipologin
		public String crearTipoLogin(){
			try {
			    	tipologin = null;			    	
					managerlogin.insertarTipologin(getNomtipolog(), getDescripcion());			    						
			    	FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Creado correctamente",null));
			    } catch (Exception e) {
					FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,"Error al ingresar nuevo tipo",null));
						FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,e.getMessage(),null));
				}
					return "";
				}
		
		//metodo para modificar los usuarios
		public String actualizarTipologin(){
			String resp ="";
			try {
				managerlogin.editartipologin(id_tipologin,descripcion,nomtipolog);				
				id_tipologin=null;
				descripcion="";
			    nomtipolog="";				
				//setNomtipolog("");
				//setDescripcion("");
				//setId_tipologin(0);				
				FacesContext context = FacesContext.getCurrentInstance();
		        context.addMessage(null, new FacesMessage("Actualizado..!!!",  "Tipo login Actualizado ") );
		        resp = "Crudtipologin";
				}
			catch (Exception e) {
				FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,"Error al modificar servicio",null));
				FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,e.getMessage(),null));
			}
		return resp;
			
		}		
		
		
      //metodo para cargar la lista de tipologin
		public String Cargartipologin(Tipologin tplog)
			{
			id_tipologin= tplog.getIdTipologin();
			nomtipolog= tplog.getTipologin();
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
	
	
	//metodo para mostrar los TiposEstado
	public List<SelectItem> getListaTipoLogin(){
		List<SelectItem> listadoTEU=new ArrayList<SelectItem>();
		List<Tipologin> listadoEstadoU=managerlogin.findAllTipoLogin();
			for(Tipologin t:listadoEstadoU){
				SelectItem item=new SelectItem(t.getIdTipologin(),t.getTipologin());
				listadoTEU.add(item);
			}
				return listadoTEU;
	}		
		//metodo para cambiar el estado del usuarios
				public String cambiarEstado(Usuario usr){
					try {													
							FacesContext context = FacesContext.getCurrentInstance();
				        	context.addMessage(null, new FacesMessage("INFORMACION",managerlogin.cambioDisEstadousr(usr.getIdUsr())));
						} catch (Exception e) {
							System.out.println(e.getMessage());
						}
					return "AdministracionUsuarios";
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
	
				public String prueba(){
		for (int element : arrayTipoLogin) {
			System.out.println(element);
		}
		return "";
	}
	
	//metodo para asignar el TipoServicio al registro
		public String asignarTipoLogin(){
			managerlogin.asignarTipologin(tipologin.getIdTipologin());			
				return "";
			}
		
		//------ Envios paginas--------//				
		public String irRegistropag()
		{		   
			//limpiamos los datos			
					return "ingresousuario";					
		}
		
		public List<Tipologin> out(){
			List<Tipologin> u = new ArrayList<Tipologin>();
			List<Tipologin> a = managerlogin.findAllTipoLogin();
			for (Integer e : arrayTipoLogin){
				for (Tipologin t : a) {
					if (e==t.getIdTipologin()){
						u.add(t);
					}
				}
			}
			return u;
			
		}
		
		public List<Tipologin> in(){
			List<Tipologin> u = new ArrayList<Tipologin>();
			List<Tipologin> a = managerlogin.findAllTipoLogin();
			for (Tipologin e : a){
				int r = 0;
				for (Tipologin t : out()) {
					if (t.getIdTipologin()==e.getIdTipologin()){
						r=1;
					}
				}
				if (r==0){
					u.add(e);
				}
			}
			return u;	
		}
		
		
				public List<SelectItem> getListlibre(){
					List<SelectItem> listadoTEU=new ArrayList<SelectItem>();
					List<Tipologin> listadoEstadoU=in();
						for(Tipologin t:listadoEstadoU){
							SelectItem item=new SelectItem(t.getIdTipologin(),t.getTipologin());
							listadoTEU.add(item);
						}
							return listadoTEU;
				}
		
				public List<SelectItem> getListocupado(){
					List<SelectItem> listadoTEU=new ArrayList<SelectItem>();
					List<Tipologin> listadoEstadoU=out();
						for(Tipologin t:listadoEstadoU){
							SelectItem item=new SelectItem(t.getIdTipologin(),t.getTipologin());
							listadoTEU.add(item);
						}
							return listadoTEU;
				}
				//------ Envios paginas--------//				
						public String irLoginpag()
						{		   
							//limpiamos los datos			
									return "loginusr";					
						}
}
