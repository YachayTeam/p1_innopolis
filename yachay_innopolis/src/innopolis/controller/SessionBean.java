package innopolis.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import innopolis.entities.Tipologin;
import innopolis.entities.Usuario;
import innopolis.entities.help.UsuarioHelp;
import innopolis.manager.ManagerLogin;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;
import javax.servlet.http.HttpSession;

@SessionScoped
@ManagedBean
public class SessionBean {
	private UsuarioHelp session;
    private ManagerLogin manager;

    private String nick;
    private String pass;
    private Integer idrol;
    
    public SessionBean() {
		manager = new ManagerLogin();
	}
    
    public String getNick() {
		return nick;
	}
    
    public String getPass() {
		return pass;
	}
    
    public UsuarioHelp getSession() {
		return session;
	}
    
    public void setNick(String nick) {
		this.nick = nick;
	}
    
    public void setPass(String pass) {
		this.pass = pass;
	}
    
    public Integer getIdrol() {
		return idrol;
	}
    
    public void setIdrol(Integer idrol) {
		this.idrol = idrol;
	}
    
    /**
     * Método de tipos de usuario
     * @return listado
     */
    public List<SelectItem> listaTipoLoginSI(){
    	List<SelectItem> listadoSI=new ArrayList<SelectItem>();
    	List<Tipologin> tipos = manager.findAllTipoLogin();
    	for (Tipologin tipologin : tipos) {
    		listadoSI.add(new SelectItem(tipologin.getIdTipologin(), tipologin.getTipologin()));
		}
    	return listadoSI;
    }
    
    /**
     * Método para ingresar al sistema
     * @return página xhtml
     */
    public String login(){
    	String resp="";
    	if(getIdrol()!=-1){
    		try {
    			Usuario usr = manager.findUserByAliasAndPass(getNick(), getPass());
    			if(manager.existeUsarioRol(usr.getIdUsr(), getIdrol())){
    				String rol = manager.findTipoLoginByID(getIdrol()).getTipologin();
    				session = new UsuarioHelp(usr.getIdUsr(), usr.getAlias(), usr.getApellido(), usr.getCorreo(), usr.getNombre(), usr.getPassword(), rol);
    				if(rol.equals("administrador")){
    					resp="/admin/home?faces-redirect=true";
    				}else if(rol.equals("emprendedor")){
    					resp="/emprendedor/home?faces-redirect=true";
    				}else if(rol.equals("aprobador")){
    					resp="/aprobador/home?faces-redirect=true";
    				}else{
    					resp="/usr/home?faces-redirect=true";
    				}
    			}else{
    				FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Rol no asignado",null));
    			}
    		} catch (Exception e) {
    			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error al intentar ingresar al sistema",null));
    		}
    	}else{
    		FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Seleccione Rol para continuar",null));
    	}
    	return resp;
    }
    
    /**
     * Método para salir del sistema
     * @return página xhtml
     */
    public String logout(){
    	HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);
        session.invalidate();        
    	return "/index?faces-redirect=true";
    }
    
    /**
     * Método para verifiar la existencia de la sesión
     * @param rol de usuario
     * @return Clase Usuario
     */
    public static UsuarioHelp verificarSession(String rol){
    	HttpSession session = (HttpSession) FacesContext.getCurrentInstance()
                .getExternalContext().getSession(false);
        SessionBean user = (SessionBean) session.getAttribute("sessionBean");
        if (user==null || user.getSession() == null) {
            try {
                FacesContext.getCurrentInstance().getExternalContext().redirect("/yachay_innopolis/");
            } catch (IOException ex) {
            	FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, ex.getMessage(),null));
            }
            return null;
        } else {
            if (user.getSession().getRol().equals(rol)) {
                return user.getSession();
            } else {
                try {
                    FacesContext.getCurrentInstance().getExternalContext().redirect("/yachay_innopolis/errorPermiso.xhtml");
                } catch (IOException ex) {
                	FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, ex.getMessage(),null));
                }
                return null;
            }
        }
    }
}
