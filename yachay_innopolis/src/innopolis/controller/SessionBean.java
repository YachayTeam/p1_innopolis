package innopolis.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import innopolis.entidades.Actividad;
import innopolis.entidades.Inter;
import innopolis.entidades.Tipo;
import innopolis.entidades.Usuario;
import innopolis.entidades.help.UsuarioHelp;
import innopolis.entidades.help.Utilidades;
import innopolis.manager.EnvioMensaje;
import innopolis.manager.ManagerLogin;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;

@SessionScoped
@ManagedBean
public class SessionBean {
	private UsuarioHelp session;
    private ManagerLogin manager;
    //log
    private String Cedula;
    private String pass;
    
    //devolver contraseña
    private String correocontra;
    String smscor="";

    //mostrar
    private String nom;
    
    private List<Actividad> la;
    
    private Usuario usr;
    
    /*Perfil de Usuario*/
    private String nombre, apellido, password, correo, cedula,alias; 
    
    public SessionBean() {
		manager = new ManagerLogin();
		usr=new Usuario();
	}
    
    public String getNick() {
		return Cedula;
	}
    
    public String getPass() {
		return pass;
	}
    
    public UsuarioHelp getSession() {
		return session;
	}
    
    public List<Actividad> getLa() {
		return la;
	}

	public void setLa(List<Actividad> la) {
		this.la = la;
	}

	public String getNom() {
		return nom;
	}

	public void setNom(String nom) {
		this.nom = nom;
	}

	public void setNick(String nick) {
		this.Cedula = nick;
	}
    
    public void setPass(String pass) {
		this.pass = pass;
	}
    
    /*Perfil Usuario*/
    public String getApellido() {
		return apellido;
	}
    
    public void setApellido(String apellido) {
		this.apellido = apellido;
	}
    
    public String getNombre() {
		return nombre;
	}
    
    public void setNombre(String nombre) {
		this.nombre = nombre;
	}
    
    public String getCorreo() {
		return correo;
	}
    
    public void setCorreo(String correo) {
		this.correo = correo;
	}
    
    public String getPassword() {
		return password;
	}
    
    public void setPassword(String password) {
		this.password = password;
	}
    
    public String getCedula() {
		return cedula;
	}
    
    public void setCedula(String cedula) {
		this.cedula = cedula;
	}
    /**
	 * @return the correocontra
	 */
	public String getCorreocontra() {
		return correocontra;
	}

	/**
	 * @param correocontra the correocontra to set
	 */
	public void setCorreocontra(String correocontra) {
		this.correocontra = correocontra;
	}
 /**
	 * @return the alias
	 */
	public String getAlias() {
		return alias;
	}

	/**
	 * @param alias the alias to set
	 */
	public void setAlias(String alias) {
		this.alias = alias;
	}

	// login
	public void veri(){
 		System.out.println(Cedula);
 		int t=0;
 		List<Usuario> a = manager.findAllUsuarios();
 		for (Usuario u : a) {
 			if ((u.getCedula().equals(Cedula) || u.getCorreo().equals(Cedula)) && u.getTipoestadousr().getIdTipoestadousr()==2 ){
 				t=100;
 			}
 		}
 		if (t!=100){
 		FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,"Alias Inexistente o Usuario Desactivado", null));
 		}
 	}	
 	
 	//metodo para ingresar al sistema	
 	public String login(){
 		String r="";
 		Integer t=0;
 		List<Usuario> u = manager.findAllUsuarios();
 		try {
 		for (Usuario y :u){
 			System.out.println("avr "+Utilidades.Encriptar(pass).toString());
 			if (y.getCedula().equals(Cedula) && y.getPassword().equals(Utilidades.Encriptar(pass)) && y.getTipoestadousr().getIdTipoestadousr()==2){
 				session = new UsuarioHelp(y.getIdUsr(), y.getAlias(), y.getApellido(),y.getCorreo() ,y.getNombre() , y.getTipo().getTipo(), y.getCedula());
 				nom=y.getNombre()+" "+y.getApellido()+" : "+y.getTipo().getTipo();
 				usr=y;
 				r="home?faces-redirect=true";
 				t=1;
 			}
 			else if (y.getCorreo().equals(Cedula) && y.getPassword().equals(Utilidades.Encriptar(pass)) && y.getTipoestadousr().getIdTipoestadousr()==2){
 				session = new UsuarioHelp(y.getIdUsr(), y.getAlias(), y.getApellido(),y.getCorreo() ,y.getNombre() , y.getTipo().getTipo(), y.getCedula());
 				nom=y.getNombre()+" "+y.getApellido()+" : "+y.getTipo().getTipo();
 				usr=y;
 				r="home?faces-redirect=true";
 				t=1;
 			}
 		}
 		if (t==0){ 			
 			FacesContext context = FacesContext.getCurrentInstance();
 			context.addMessage(null, new FacesMessage("Error..!!!",
 					"Usuario o Contrasena Incorrecta "));
 		}

 		this.activacion();
 		
 	}
		catch (Exception e)
		{
		
		}
 		
 		return r;
 	}

 	public void activacion(){
 		la= new ArrayList<Actividad>();
 		Tipo t= new Tipo();
 		String ps ="";
 		try {
			ps = Utilidades.Encriptar(pass);
		} catch (Exception e) {
			e.printStackTrace();
		}
 		if (usr.getCedula().equals(Cedula) && usr.getPassword().equals(ps)){
 				t=usr.getTipo();
 		}
 		else if (usr.getCorreo().equals(Cedula) && usr.getPassword().equals(ps))
 		{
 			t=usr.getTipo();
 		}
 		List<Inter> i= manager.findAllInter();
 		List<Inter> n = new ArrayList<Inter>();
 		for (Inter h:i){
 			if (t.getIdTipo()==h.getTipo().getIdTipo()){
 				n.add(h);
 			}
 		}
 		List<Actividad> a = manager.findAllActividades();
 		for (Actividad c: a){
 			for (Inter k : n){
 				if (k.getActividad().getIdActividad()==c.getIdActividad()){
 					la.add(c);
 				}
 			}
 		}
 		System.out.println(la.size()+"si hay datos");
 	}
 	//metodo para salir de el sistema
 	public String logout(){
 		HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);
         session.invalidate();
 		nom="";
 		correo="";
 		pass="";
 		Cedula="";
 		System.out.println("si salio");
		FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Salió",null));
 		return "";
 	}
 	
 	 /**
      * Método para verifiar la existencia de la sesión
      * @param rol de usuario
      * @return Clase Usuario
      */
     public static UsuarioHelp verificarSession(){
     	HttpSession session = (HttpSession) FacesContext.getCurrentInstance()
                 .getExternalContext().getSession(false);
         SessionBean user = (SessionBean) session.getAttribute("sessionBean");
         if (user==null || user.getSession() == null) {
             try {
                 FacesContext.getCurrentInstance().getExternalContext().redirect("../index.xhtml");
             } catch (IOException ex) {
             	FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, ex.getMessage(),null));
             }
             return null;
         } else {
                 return user.getSession();
             } 
         }
     
    public String cargarDatosPerfil(){
    	String pag ="";
    	if(session != null){
    		try {
    			Usuario usr = manager.UsuarioByID(session.getIdUsr());
    			setApellido(usr.getApellido());
    			setNombre(usr.getNombre());
    			setCorreo(usr.getCorreo());
    			setCedula(usr.getCedula());
			} catch (Exception e) {
				FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error al  cargar sus datos personales",null));
			}
    	}
    	return pag;
    }
    
    public void cambioDatosPerfil(){
    	try {
			manager.modificarDatosUSR(session.getIdUsr(),getCedula(), getNombre(), getApellido(), getCorreo());
		} catch (Exception e) {
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error al cambiar sus datos personales",null));
		}
    }
    
    public void cambioClavePerfil(){
    	try {
			
		} catch (Exception e) {
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error al cambiar su password",null));
		}
    }
    
    public String regresarHomeUser(){
    	return "home?faces-redirect=true";
    }
    
    
    //metodo para enviar el correo
    public String devolvercontra(){
 		String r="";
 		Integer t=0;
 		List<Usuario> u = manager.findAllUsuarios();
 		for (Usuario y :u){
 			if (y.getCorreo().equals(correocontra)){
 				System.out.println("si entra1");
 				enviarmensajerecuperarcontra(y); 				
 				FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,"Enviado correctamente a su correo", null));	  						  							

 				t=1;
 			}
 		}
 		if (t==0){ 			
 			FacesContext context = FacesContext.getCurrentInstance();
 			context.addMessage(null, new FacesMessage("Error..!!!","Su correo no existe o es incorrecto."));
 		} 		
 		return r;
 	}
    String correoveri="";
    //Tomar el id de estado general id_estadoSolicitud
		public String enviarmensajerecuperarcontra(Usuario usr){
		try {
			if(!correocontra.equals(correoveri))
			{
					String passwordnuevo;
					cedula = usr.getCedula();
					nombre = usr.getNombre();
					apellido = usr.getApellido();
					correo = usr.getCorreo();
					alias = usr.getAlias();
					password = usr.getPassword();
					passwordnuevo=Utilidades.Desencriptar(password);	
					
					smscor = "Sr/ra. "+nombre+" "+apellido+",sus datos son los siguientes: "
				             + "<br/> C&eacute;dula: "+cedula+""
				             + "<br/> Nombre: "+nombre+""
				             + "<br/> Apellido: "+apellido+""
				             + "<br/> Correo: "+correo+""				             
							 + "<br/> para ingresar su usuario es: "+cedula+" o su correo "+correo+", y su contrase&ntildea es: "+passwordnuevo+"";
	
					EnvioMensaje.sendMailsolousr(correo, "Recuperación de contraseña YACHAY/REGECE  ", smscor);
				    //limpiamos los datos
					cedula="";
			        nombre="";
			        apellido="";
			        correoveri=correo;
					correo="";
					alias="";
					password="";	
					passwordnuevo="";
					smscor="";
					FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,"Enviado correctamente al correo su contraseña", null));
			}
			else if(correoveri.equals(correocontra))
			{
				FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,"Ya se ha enviado al correo su contraseña", null));				
			}
			} catch (Exception e) {
				e.printStackTrace();
				FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,"Error al enviar correo", null));
			}
    		return "index?faces-redirect=true";
		}			    
}
