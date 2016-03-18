package innopolis.controller;


import innopolis.entidades.Interes;
import innopolis.entidades.Tipoestadousr;
import innopolis.entidades.Usuario;
import innopolis.entidades.help.Utilidades;
import innopolis.manager.EnvioMensaje;
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
public class RegistroBean implements Serializable {
	private static final long serialVersionUID = 1L;
	private ManagerLogin manager;
	private Integer idUsuario;
	private String alias;
	private String apellido;
	private String cedula;
	private String correo;
	private String interes;
	private String nombre;
	private String password;
	private String rcorreo;
	private String rpassword;
	private Integer tipo;
	private String tipoest;
	private Tipoestadousr tipoestusr;
	private String sms;
	private String empreestu;
	private String cargptitu;	
	
	
	//envio de correo a los administradores
	private String smscoradmin; 
	private String smscorusu;
	private String correosadmin;

	
	//intereses
	private Integer[] arrayTipoLogin;
	private Interes intereses;
	
	//empresa cargo titulo
	private String valor;
	
	public RegistroBean() {
		manager = new ManagerLogin();
	}
	
	public Integer getIdUsuario() {
		return idUsuario;
	}

	public Integer getTipo() {
		return tipo;
	}

	public void setTipo(Integer tipo) {
		this.tipo = tipo;
	}
	
	public void setIdUsuario(Integer idUsuario) {
		this.idUsuario = idUsuario;
	}

	public String getRcorreo() {
		return rcorreo;
	}

	public void setRcorreo(String rcorreo) {
		this.rcorreo = rcorreo;
	}

	public String getRpassword() {
		return rpassword;
	}

	public void setRpassword(String rpassword) {
		this.rpassword = rpassword;
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

	public String getInteres() {
		return interes;
	}

	public void setInteres(String interes) {
		this.interes = interes;
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

	
	public Tipoestadousr getTipoestusr() {
		return tipoestusr;
	}

	public void setTipoestusr(Tipoestadousr tipoestusr) {
		this.tipoestusr = tipoestusr;
	}	
	/**
	 * @return the arrayTipoLogin
	 */
	public Integer[] getArrayTipoLogin() {
		return arrayTipoLogin;
	}

	/**
	 * @param arrayTipoLogin the arrayTipoLogin to set
	 */
	public void setArrayTipoLogin(Integer[] arrayTipoLogin) {
		this.arrayTipoLogin = arrayTipoLogin;
	}

	/**
	 * @return the intereses
	 */
	public Interes getIntereses() {
		return intereses;
	}

	/**
	 * @param intereses the intereses to set
	 */
	public void setIntereses(Interes intereses) {
		this.intereses = intereses;
	}

	/**
	 * @return the empreestu
	 */
	public String getEmpreestu() {
		return empreestu;
	}

	/**
	 * @param empreestu the empreestu to set
	 */
	public void setEmpreestu(String empreestu) {
		this.empreestu = empreestu;
	}

	/**
	 * @return the cargptitu
	 */
	public String getCargptitu() {
		return cargptitu;
	}

	/**
	 * @param cargptitu the cargptitu to set
	 */
	public void setCargptitu(String cargptitu) {
		this.cargptitu = cargptitu;
	}

	/**
	 * @return the correosadmin
	 */
	public String getCorreosadmin() {
		return correosadmin;
	}

	/**
	 * @param correosadmin the correosadmin to set
	 */
	public void setCorreosadmin(String correosadmin) {
		this.correosadmin = correosadmin;
	}

	/**
	 * @return the smscorusu
	 */
	public String getSmscorusu() {
		return smscorusu;
	}

	/**
	 * @param smscorusu the smscorusu to set
	 */
	public void setSmscorusu(String smscorusu) {
		this.smscorusu = smscorusu;
	}


	/**
	 * @return the smscor
	 */
	public String getSmscor() {
		return smscoradmin;
	}

	/**
	 * @param smscor the smscor to set
	 */
	public void setSmscor(String smscor) {
		this.smscoradmin = smscor;
	}

	/**
	 * @return the sms
	 */
	public String getSms() {
		return sms;
	}

	/**
	 * @param sms the sms to set
	 */
	public void setSms(String sms) {
		this.sms = sms;
	}

	/**
	 * @return the tipoest
	 */
	public String getTipoest() {
		return tipoest;
	}

	/**
	 * @param tipoest the tipoest to set
	 */
	public void setTipoest(String tipoest) {
		this.tipoest = tipoest;
	}

	public List<Usuario> getListUsuarios(){
		return manager.findAllUsuarios();
	}
	/**
	 * @return the valor
	 */
	public String getValor() {
		return valor;
	}

	/**
	 * @param valor the valor to set
	 */
	public void setValor(String valor) {
		this.valor = valor;
	}

	// Usuarios
	// metodo para comprobar el alias
	public boolean ccedula(String cedula){
		List<Usuario> u = manager.findAllUsuarios();
		for (Usuario us : u) {
			if (cedula.equals(us.getCedula())){
				return true;
			}
		}
		return false;
	}
	// metodo para comprobar el alias
	public boolean calias(String alias){
		List<Usuario> u = manager.findAllUsuarios();
		for (Usuario us : u) {
			if (alias.equals(us.getAlias())){
				return true;
			}
		}
		return false;
	}
	
	// metodo para comprobar repetidos
	public boolean repetidosc(){
		if (correo.equals(rcorreo)){
			return true;
		}
		return false;
	}
	
	// metodo para comprobar repetidos
	public boolean repetidosp(){
		if (password.equals(rpassword)){
			return true;
		}
		return false;
	}
	// metodo para comprobar el correo
	public boolean ccorreo(String correo){
		List<Usuario> u = manager.findAllUsuarios();
		for (Usuario us : u) {
			if (correo.equals(us.getCorreo())){
				return true;
			}
		}
				return false;
			}
	
	// accion para invocar el manager y crear evento
	public String crearUsuario() {
		String a="ingresousuario";
		if (this.repetidosc()==false){
			FacesContext context = FacesContext.getCurrentInstance();
	        context.addMessage(null, new FacesMessage("Correo Erroneo..!!!",  "Los correos ingresados no coinciden") );
		}else if (this.repetidosp()==false){
			FacesContext context = FacesContext.getCurrentInstance();
	        context.addMessage(null, new FacesMessage("Contrase�a Erronea..!!!",  "Las contrase�as ingresadas no coinciden") );
		//}else if (this.calias(alias)==true){
		//	FacesContext context = FacesContext.getCurrentInstance();
	    //   context.addMessage(null, new FacesMessage("Alias Repetido..!!!",  "El Alias ya esta siendo utilizado") );
		}else if(this.ccedula(cedula)){
			FacesContext context = FacesContext.getCurrentInstance();
	        context.addMessage(null, new FacesMessage("C�dula Repetido..!!!",  "La c�dula/pasaporte ya esta siendo utilizada") );	
		}else if(this.ccorreo(correo)){
			FacesContext context = FacesContext.getCurrentInstance();
	        context.addMessage(null, new FacesMessage("Correo Repetido..!!!",  "El correo ya esta siendo utilizado") );
		}else
			{
		try{
			//if(valida(cedula)==true)
		//	{		
			setPassword(Utilidades.Encriptar(getPassword()));//MD5 PASS
			manager.registrarUsuario(cedula, alias, apellido, correo, nombre, password,sms,empreestu,cargptitu,arrayTipoLogin);
			//agregarlistadecorreos principal
			smscoradmin = "El usuario con apellido "+apellido+" con nombre "+nombre+"; Requiere la aprobaci�n o negaci�n del registro al sistema; \n"
		             +"los datos del usuario son:"
		             + "\n C�dula: "+cedula+""
		             + "\n Nombre: "+nombre+""
		             + "\n Apellido: "+apellido+""
		             + "\n Correo: "+correo+"";
			
			smscorusu = "Sr/ra.  "+nombre+" "+apellido+", su petici�n de acceso al sistema REGECE (Reservas de Espacios y Gesti�n de Eventos del Centro de Emprendimiento), ser� verificado por los administradores, espere al mensaje de confirmaci�n. \n"
					 +"sus datos son: "
		             + "\n C�dula: "+cedula+""
		             + "\n Nombre: "+nombre+""
		             + "\n Apellido: "+apellido+""
		             + "\n Correo: "+correo+"";
			
			getcorreosusu();
			EnvioMensaje.sendMail(correosadmin, "Notificaci�n de YACHAY/REGECE  ", smscoradmin);
			EnvioMensaje.sendMailsolousr(correo, "Notificaci�n de YACHAY/REGECE  ", smscorusu);
			cedula="";
			alias="";
			nombre="";
			apellido="";
			correo="";
			rcorreo="";
			password="";
			rpassword="";
			interes="";
			tipo=0;
			sms="";
			smscoradmin="";
			idUsuario=null;
			correosadmin="";
			empreestu="";
			cargptitu="";
			smscorusu="";
			tipoestusr = manager.EstadoByID(1);
				FacesContext context = FacesContext.getCurrentInstance();
				context.addMessage(null, new FacesMessage("Registrado..!!!",
						"Usuario Creado espere la activacion del mismo"));
				a="index";
			//}
		//	else{
		//		FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,"C�dula no valida",null));
		//	}
		
		} catch (Exception e) {
			FacesContext context = FacesContext.getCurrentInstance();
			context.addMessage(null, new FacesMessage("Error..!!!",
					"Usuario no pudo ser Creado "));
			e.printStackTrace();
		}
		}
		return a;
	}
	
	//metodo para listar correos de ususariosadmin
	public String getcorreosusu(){			
		try
		{
		List<Usuario> a = manager.findUsrsPrincipal();
		correosadmin="";
		for (Usuario u : a) {
			correosadmin+=u.getCorreo()+",";
		}
		int max = correosadmin.length();
		correosadmin = correosadmin.substring(0, max-1).trim(); 
		}
		catch (Exception e) {
		FacesContext context = FacesContext.getCurrentInstance();
		context.addMessage(null, new FacesMessage("Error..!!!",
				"No se encuentran usuarios administradores"));
		e.printStackTrace();
	}
		return correosadmin;
	}
	
	//metodo para mostrar los Actividades
	public List<SelectItem> getListaIntereses(){
		List<SelectItem> listadoTEU=new ArrayList<SelectItem>();
		List<Interes> listadoEstadoU=manager.findAllIntereses();
			for(Interes t:listadoEstadoU){
				SelectItem item=new SelectItem(t.getIdInteres(),t.getNombreInt());
				listadoTEU.add(item);
			}
				return listadoTEU;
	}	
	
	private String panel1 = "Show-Panel1";
	private String panel2 = "Show-Panel2";
	public void setPanel1(String panel1){
	this.panel1 = panel1;
	}

	public String getPanel1(){
	return this.panel1;
	}
	public void setPanel2(String panel2){
	this.panel2 = panel2;
	}

	public String getPanel2(){
	return this.panel2;
	}	
	
	
	
	/*//validar cedula
	  public static boolean valida(String x){
	    int suma=0;
	    if(x.length()==9){
	      System.out.println("Ingrese su cedula de 10 digitos");
	      return false;
	    }else{
	      int a[]=new int [x.length()/2];
	      int b[]=new int [(x.length()/2)];
	      int c=0;
	      int d=1;
	      for (int i = 0; i < x.length()/2; i++) {
	        a[i]=Integer.parseInt(String.valueOf(x.charAt(c)));
	        c=c+2;
	        if (i < (x.length()/2)-1) {
	          b[i]=Integer.parseInt(String.valueOf(x.charAt(d)));
	          d=d+2;
	        }
	      }
	    
	      for (int i = 0; i < a.length; i++) {
	        a[i]=a[i]*2;
	        if (a[i] >9){
	          a[i]=a[i]-9;
	        }
	        suma=suma+a[i]+b[i];
	      } 
	      int aux=suma/10;
	      int dec=(aux+1)*10;
	      if ((dec - suma) == Integer.parseInt(String.valueOf(x.charAt(x.length()-1))))
	        return true;
	      else
	        if(suma%10==0 && x.charAt(x.length()-1)=='0'){
	          return true;
	        }else{
	          return false;
	        }
	    }
	  }*/
}