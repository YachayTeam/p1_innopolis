package innopolis.manager;

import java.io.Serializable;
import java.util.List;


import innopolis.entidades.*;

public class ManagerRecursosVirtuales implements Serializable{
	
	private static final long serialVersionUID = 1L;

	private ManagerDAO mDAO;
	
	//Registro Temporal
	private static Usuario usuario;
	private static Tiposervicio tiposerv;
	private static Tipoestado tipoesta;
	
	int p=0;
	String h="";
			
	public ManagerRecursosVirtuales()
	{
		mDAO= new ManagerDAO();
	}	
	
// ------ServiciosVirtuales-------
	//metodo para asignar el Tiposervicio al registro
 	public Tiposervicio asignarTiposerv(Integer idtiposervicio) {
 		try {
			tiposerv = findServicioTipoByID(idtiposervicio);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
 		return tiposerv;
	}	
 	
 	public Tiposervicio asignarTiposervurl(Integer idtiposervicio) throws Exception{
 		try {
 			if(usuarioRegurl(idtiposervicio)){
 				System.out.println("no esta registrado para este servicio");
 				throw new Exception("El usuario no se encuentra registrado en este servicio");
			}
 			else{
			tiposerv = findServicioTipoByID(idtiposervicio);
 				}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			throw new Exception("Error al registrarse "+e.getMessage());
		}
 		return tiposerv;
	}	
 	
 	public Boolean usuarioRegurl(Integer id_tp){
		boolean resp=true;
		List<Serviciosvirtregi> serli = findAllRServiciosVirtuales();
		for (Serviciosvirtregi sv : serli) {
			if(sv.getTiposervicio().getIdTp().equals(id_tp) && sv.getTipoestado().getIdEstado().equals(2))
			{
				resp=false;
			}
		}
		return resp;	
	}
 	
	public Usuario asignarUsuario(Integer idusu) {
 		try {
			usuario = findServiciousrByID(idusu);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
 		return usuario;
	}	
 	
 	
 	//metodo para asignar el Tiposestado al servivirtual
 	 	public Tipoestado asignarTipoest(Integer idtipoest) {
 	 		try {
 				tipoesta = EstadoByID(idtipoest);
 			} catch (Exception e) {
 				// TODO Auto-generated catch block
 				e.printStackTrace();
 			}
 	 		return tipoesta;
 		}
		
// listar todos los serviciosvirtuales 
	@SuppressWarnings("unchecked")
	public List<Serviciosvirtregi> findAllRServiciosVirtuales(){
		return mDAO.findAll(Serviciosvirtregi.class);
	}
	
//listar todos los tipoEstado 
	@SuppressWarnings("unchecked")
	public List<Tipoestado> findAllTipoEstado(){
		return mDAO.findAll(Tipoestado.class);
	}		
	
//listar todos los tipoServicio 
		@SuppressWarnings("unchecked")
		public List<Tiposervicio> findAllTipoServicio(){
			return mDAO.findAll(Tiposervicio.class);
		}

//listar todos los estadostipoServicio 
		@SuppressWarnings("unchecked")
		public List<Estadotiposervicio> findAllEstadotipoServicio(){
			return mDAO.findAll(Estadotiposervicio.class);
		}	
			
	
//buscar servicio por ID
		public Serviciosvirtregi ServicioVirtualByID(int id_Srv) throws Exception{
			return (Serviciosvirtregi) mDAO.findById(Serviciosvirtregi.class, id_Srv);
		}
//buscar tipo estado por ID
		public Tipoestado EstadoByID(int id_Estado) throws Exception{
			return (Tipoestado) mDAO.findById(Tipoestado.class, id_Estado);
		}
//ServicioTipo por ID
		public Tiposervicio findServicioTipoByID(Integer id_Tp) throws Exception{
			return (Tiposervicio) mDAO.findById(Tiposervicio.class, id_Tp);
		}
//EstadoTipo por ID
		public Tipoestado findEstadoTipoByID(Integer id_Ep) throws Exception{
			return (Tipoestado) mDAO.findById(Tipoestado.class, id_Ep);
		}
//EstadoTipoServicio por ID
		public Estadotiposervicio findEstadoTiposervByID(Integer id_Etp) throws Exception{
			return (Estadotiposervicio) mDAO.findById(Estadotiposervicio.class, id_Etp);
		}	
//ServicioTipo por ID
		public Usuario findServiciousrByID(Integer id_usu) throws Exception{
			return (Usuario) mDAO.findById(Usuario.class, id_usu);
		}

//insertar los serviciosvirtuales
		public void insertarserviciovirtual(Integer idusr,String tema) throws Exception{
			System.out.println(idusr);
			System.out.println(tema);
			System.out.println(tiposerv.getIdTp());
			try {
					Serviciosvirtregi svt = new Serviciosvirtregi();
					svt.setTema(tema);
					svt.setSms("No Notificado");
					svt.setTipoestado(this.EstadoByID(1));
					svt.setUsuario(findServiciousrByID(idusr));
					svt.setTiposervicio(tiposerv);
					mDAO.insertar(svt);				
			} catch (Exception e) {
				throw new Exception("Error al registrarse "+e.getMessage());
			}
		}
		
		public Boolean usuarioReg(Integer id_usr, Integer id_tp){
			boolean resp=false;
			List<Serviciosvirtregi> serli = findAllRServiciosVirtuales();
			for (Serviciosvirtregi sv : serli) {
				if(sv.getUsuario().getIdUsr().equals(id_usr) && sv.getTiposervicio().getIdTp().equals(id_tp))
				{
					resp=true;
				}
			}
			return resp;	
		}
	
//insertar tipo de estado
		public void insertarTipoEstado(String nombreestado) throws Exception{
			Tipoestado te = new Tipoestado();
			te.setNombreestado(nombreestado);
			mDAO.insertar(te);
		}
	
//insertar tipo de servicio
	public void insertarTipoServicio(Integer idtipser,String nombreservicio, String url) throws Exception{
		Tiposervicio ts = new Tiposervicio();
		ts.setNombreServicio(nombreservicio);
		ts.setUrl(url);
		ts.setIdTp(idtipser);			
		ts.setEstadotiposervicio(this.findEstadoTiposervByID(1));
		mDAO.insertar(ts);
	}

	//editar los serviciosvirtuales
	public void editarserviciovirtual(Integer id_Srv, String tema,Integer id_Estado, Integer id_serv, String sms){
		try{
		Serviciosvirtregi svt = this.ServicioVirtualByID(id_Srv);
		svt.setTema(tema);
		svt.setSms(sms);
		//svt.setTipoestado(tipoesta);
		//svt.setTiposervicio(tiposerv);
		svt.setTiposervicio(this.findServicioTipoByID(1));
		svt.setTipoestado(this.asignarTipoest(id_Estado));
		mDAO.actualizar(svt);
		} catch (Exception e) {
			System.out.println("Error_mod_servicio");
			e.printStackTrace();
		}
	}
		
	//editar los tipos de estado
	public void editartipoestado(int idEstado, String nombreestado)
	{
		try
		{
			Tipoestado tes = this.EstadoByID(idEstado);
			tes.setNombreestado(nombreestado);
			mDAO.actualizar(tes);
		} catch (Exception e) {
			System.out.println("Error_mod_recurso");
			e.printStackTrace();
			}		
	}
	
	//editar los tipos de servicio
	public void editartiposervicio(int id_Tpser, String nombreServicio, String url)
	{
		try
		{
			Tiposervicio ts = this.findServicioTipoByID(id_Tpser);
			ts.setNombreServicio(nombreServicio);
			ts.setUrl(url);
			mDAO.actualizar(ts);
		} catch (Exception e) {
			System.out.println("Error_mod_recurso");
			e.printStackTrace();
			}				
	}
	
	//metdodo eliminar servicio
	public void eliminarServicio(Integer id_servicio) throws Exception {
		try
		{
		  Tiposervicio tpser = findServicioTipoByID(id_servicio);
		  if(tpser.getServiciosvirtregis().isEmpty())
		mDAO.eliminar(Tiposervicio.class, id_servicio);
		  else 
		  throw new Exception("No se elminio");
		}
		catch (Exception e)
		{
			throw e;
		}
	}
	
	//metodo para enviar el estado del mensaje si se envio
	public String cambioSMSenvio(Integer id_Srv)  throws Exception{		
		
		h="";
		try
		{				
			Serviciosvirtregi svt = this.ServicioVirtualByID(id_Srv);
			if(svt.getSms().equals("No Notificado"))	
			{				
				svt.setSms("Notificado");						
			}
		}
		catch (Exception e)
		{
			throw new Exception("No se envio el mensaje");	
		}					
		return h;
	}
	
	public void cambioSMS(Integer id_Srv)  throws Exception{		
		try
		{
			Serviciosvirtregi svt = this.ServicioVirtualByID(id_Srv);
			if(svt.getSms().equals("Notificado"))	
			{
				svt.setSms("No Notificado");				
				
			}
		}
		catch (Exception e)
		{
			throw new Exception("error");	
		}	
	}
	
	 //desactivar y activar estado	
		public String cambioDisEstadoapro(Integer id) throws Exception{
			List<Tipoestado> lista= findAllTipoEstado();
			p=0;
			h="";
			for (Tipoestado ta: lista){
				if (ta.getIdEstado().equals(id)){
					p=1;
				}
			}
			Serviciosvirtregi ser = ServicioVirtualByID(id);
 			Tipoestado est = new Tipoestado(); 			
			if(ser.getTipoestado().getNombreestado().equals("Pendiente")){
				est.setIdEstado(2);
				est.setNombreestado("Aprobado");				
				ser.setTipoestado(est);				
				h="Estado del Registro Modificado";
 			}
			else if(ser.getTipoestado().getNombreestado().equals("Negado")){
				est.setIdEstado(2);
				est.setNombreestado("Aprobado");				
				ser.setTipoestado(est);				
				h="Estado del Registro Modificado";
 			}			
			else if(ser.getTipoestado().getNombreestado().equals("Aprobado")){
					h="Estado del Registro ya está aprobado";
 			}
			mDAO.actualizar(ser);
			return h;
			}
		
		
		//desactivar y activar Recurso	
				public String cambioDisEstadonega(Integer id) throws Exception{
					List<Tipoestado> lista= findAllTipoEstado();
					p=0;
					h="";					
					for (Tipoestado ta: lista){
						if (ta.getIdEstado().equals(id)){
							p=1;
						}
					}
					Serviciosvirtregi ser = ServicioVirtualByID(id);
		 			Tipoestado est = new Tipoestado();
		 			
					if(ser.getTipoestado().getNombreestado().equals("Pendiente")){
						est.setIdEstado(3);
						est.setNombreestado("Negado");				
						ser.setTipoestado(est);				
						h="Estado del Registro Modificado";
		 			}
					else if(ser.getTipoestado().getNombreestado().equals("Aprobado")){
						est.setIdEstado(3);
						est.setNombreestado("Negado");				
						ser.setTipoestado(est);				
						h="Estado del Registro Modificado";
		 			}
					else if(ser.getTipoestado().getNombreestado().equals("Negado")){
						h="Estado del Registro ya está negado";
		 			}
					mDAO.actualizar(ser);
					return h;
					}
		
		
		//desactivar y activar serviciovirtual		
		public String cambioEstadotiposer(Integer id) throws Exception{
			List<Serviciosvirtregi> lista= findAllRServiciosVirtuales();
			int p=0;
			h="";			
			for (Serviciosvirtregi ta: lista){
				if (ta.getTiposervicio().getIdTp().equals(id)){
					p=1;
				}
			}
			Tiposervicio tipser = findServicioTipoByID(id);				
			Estadotiposervicio tipestusr = new Estadotiposervicio();			
			if(tipser.getEstadotiposervicio().getEts().equals("Activado") && p==0){
				tipestusr.setIdEts(2);
				tipestusr.setEts("Desactivado");				
				tipser.setEstadotiposervicio(tipestusr);				
				h="Estado del Registro Modificado";
			}
			else if(tipser.getEstadotiposervicio().getEts().equals("Activado") && p==1) {
				h="Servicio ocupado no puede ser modificado";
 			}
			else if(tipser.getEstadotiposervicio().getEts().equals("Desactivado")){
				tipestusr.setIdEts(1);
				tipestusr.setEts("Activado");				
				tipser.setEstadotiposervicio(tipestusr);				
				h="Estado del Registro Modificado";
			}
			mDAO.actualizar(tipser);
			return h;
			}

	
//	////METODO DE ENVIO DE CORREO
//		public boolean sendMail(String origen,String clave,String destinatario, String asunto, String mensaje) throws Exception{
//			p=0;
//			
//			try
//	        {	        	
//	            Properties props = new Properties();
//	            props.put("mail.smtp.host", "smtp.gmail.com");
//	            props.setProperty("mail.smtp.starttls.enable", "true");
//	            props.setProperty("mail.smtp.port", "587");
//	            props.setProperty("mail.smtp.user", origen);
//	            props.setProperty("mail.smtp.auth", "true");
//
//	            Session session = Session.getDefaultInstance(props, null);
//	            BodyPart texto = new MimeBodyPart();
//	            texto.setText(mensaje);
//
//	            MimeMultipart multiParte = new MimeMultipart();
//	            multiParte.addBodyPart(texto);
//
//	            MimeMessage message = new MimeMessage(session);
//	            message.setFrom(new InternetAddress(origen));
//	            message.addRecipient(
//	                Message.RecipientType.TO,
//	                new InternetAddress(destinatario));
//	                message.setSubject(asunto);
//	            message.setContent(multiParte);
//
//	            Transport t = session.getTransport("smtp");
//	            t.connect(origen, clave);
//	            t.sendMessage(message, message.getAllRecipients());
//	            t.close();
//	            h="Enviado correctamente la notificacion";
//	            return true;
//	        }
//	        catch (Exception e)
//	        {	        	
//	            e.printStackTrace();
//	            h="Error al  enviar la notificacion";
//	            return false;
//	        }        
//	    }				
}

