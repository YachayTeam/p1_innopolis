package innopolis.manager;

import innopolis.entidades.*;

import java.io.Serializable;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;


public class ManagerLogin implements Serializable{
	
	private static final long serialVersionUID = 1L;

	private ManagerDAO mDAO;
	
	//Registro Temporal
		private static Tipoestadousr tipoestadousr;
		private Tipo t;
		private static Actividad act;
		private static Tipo tip;
		private static Usuario tu;
		private static 
		
		int p=0;
		String h="";		
				
	public ManagerLogin() {
		mDAO= new ManagerDAO();
	}
		// USUARIOS
	@SuppressWarnings("unchecked")
	public List<Usuario> findUsrsPrincipal(){
		return mDAO.findWhere(Usuario.class, "o.principal=1 ", null);
	}
	
	// listar todos los usuarios 
		@SuppressWarnings("unchecked")
		public List<Usuario> findAllUsuarios(){
			return mDAO.findAll(Usuario.class);
		}
		
		//buscar usuarios por ID
			public Usuario UsuarioByID(int id_usr) throws Exception{
				return (Usuario) mDAO.findById(Usuario.class, id_usr);
			}	
	
	//insertar los usuarios
	public void insertarusuarios(String cedula,String alias, String apellido, String correo,
			String nombre, String password, String empresinsti, String cargotitulo, Integer[] listado) throws Exception{
	    Usuario usr = new Usuario(); 
	    usr.setCedula(cedula);
		usr.setAlias(alias);
	    usr.setApellido(apellido);
	    usr.setCorreo(correo);
	    usr.setNombre(nombre);
	    usr.setPassword(password);
	    usr.setTipoestadousr(this.EstadoByID(2));//Toma Activado 
	    usr.setSms("No Notificado");
	    usr.setTipo(t);
	    usr.setPrincipal(false);
	    usr.setEmpresestdu(empresinsti); 
	    usr.setCargotitulo(cargotitulo);
	    //Asignar ID USR
	    int contadorUsuario = this.getContUsr();
	    contadorUsuario++;
	    usr.setIdUsr(contadorUsuario);
	    //Insertar USUARIO
		mDAO.insertar(usr);
		
		for (Integer i : listado) {
			this.insertarInter(usr, InteresyID(i));
		}
		//Actualiza el contador
		this.actualizarContadorUSR(contadorUsuario);		
	}
	
	// buscar interes por ID
	public Interes InteresyID(Integer id) throws Exception {
	return (Interes) mDAO.findById(Interes.class, id);
	}
	//insertar interes aun no
	public void insertarInter(Usuario t, Interes a) throws Exception{
		Interesesmid tu = new Interesesmid();
		tu.setUsuario(t);
		tu.setInteres(a);						
		mDAO.insertar(tu);
	}		
	
	//ID_USR
	public int getContUsr() throws Exception{
		int contUSR = 0;
		Contadore cont = null;
		try {
			cont = (Contadore) mDAO.findById(Contadore.class, 2);
			contUSR = cont.getValor();
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception("Revise el parametro contador 'usuario': "+e.getMessage());
		}	
		return contUSR;
	}
	//metodo para actualizar el contador del usuario
	public void actualizarContadorUSR(int valor)throws Exception{
		Contadore cont = null;
		try {
			cont = (Contadore) mDAO.findById(Contadore.class, 2);
			cont.setValor(valor);
			mDAO.actualizar(cont);
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception("Error al actualizar el contador 'usuario': "+e.getMessage());
		}
	}
	
	//REGISTRARSE
	public void registrarUsuario(String cedula,String alias, String apellido, 
			String correo,String nombre, String password, String sms, 
			String empresestu, String cargoinstitu, Integer[] listado) throws Exception{
		Usuario usr = new Usuario(); 
		usr.setCedula(cedula);
		usr.setAlias(alias);
	    usr.setApellido(apellido);
	    usr.setCorreo(correo);
	    usr.setNombre(nombre);
	    usr.setPassword(password);
	    usr.setTipoestadousr(this.EstadoByID(1));//Toma Pendiente 
	    usr.setSms("No Notificado");
	    usr.setTipo(findtipoxid(2));
	    usr.setPrincipal(false);
	    usr.setEmpresestdu(empresestu);
	    usr.setCargotitulo(cargoinstitu);
	    //Asignar ID USR
	    int contadorUsuario = this.getContUsr();
	    contadorUsuario++;
	    usr.setIdUsr(contadorUsuario);
	    //Insertar USUARIO
		mDAO.insertar(usr);
		
		for (Integer i : listado) {
			this.insertarInter(usr, InteresyID(i));
		}
		
		//Actualiza el contador
		this.actualizarContadorUSR(contadorUsuario);
	}
		
	public Tipo findtipoxid(Integer a){
		Tipo p= new Tipo();
		try {
			p = (Tipo) mDAO.findById(Tipo.class, a);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return p;
	}
			
	/*/editar los usuarios 
	public void editarusuario(Integer id_usr, String correo, String password, Integer tipo /*, Integer[] listTipo) throws Exception{
		Usuario usr = this.UsuarioByID(id_usr); ;
		usr.setCorreo(correo);
		usr.setPassword(password);
		usr.setTipo(t);
		mDAO.actualizar(usr);
	}*/
	
	//editar los usuarios 
		public void editarusuario(Integer id_usr,String cedula,String nombre,String password,String apellido,
				String alias, String correo,String interes, String empresestu,String cargotitulo, boolean principal,Integer tipo /*, Integer[] listTipo*/) throws Exception{
			Usuario usr = this.UsuarioByID(id_usr); ;
			usr.setCedula(cedula);
			usr.setAlias(alias);
		    usr.setApellido(apellido);
		    usr.setCorreo(correo);
		    usr.setNombre(nombre);
		    usr.setPassword(password);
		    t=findtipoxid(tipo);
		    usr.setTipo(t);
			usr.setInteres(interes);
			usr.setEmpresestdu(empresestu);
			usr.setCargotitulo(cargotitulo);
			usr.setPrincipal(principal);
			mDAO.actualizar(usr);
		}
	
	//INTER
	
	 // listar todas las inter
	@SuppressWarnings("unchecked")
	public List<Inter> findAllInter() {
		return mDAO.findAll(Inter.class);
	}

	// buscar inter por ID
	public Inter InterByID(Integer id) throws Exception {
		return (Inter) mDAO.findById(Inter.class, id);
	}
	
	//Comparaciones
	public ArrayList<Integer> noExisteAenB(Integer[] a, Integer[] b){
		ArrayList<Integer> resp = new ArrayList<Integer>();
		int val = 0;
		for (Integer int_a : a) {
			for (Integer int_b : b) {
				if(int_a==int_b){
					val = 0;
					break;
				}else{
					val = int_a;
				}
			}
			if(val!=0){
				resp.add(int_a);
			}
		}
		return resp;
	}
	
	//Buscar inter
	public Inter findByInter(Integer id_act, Integer id_tipo){
		Inter resp = null;
		List<Inter> listado = findAllInter();
		for (Inter i : listado) {
			if(i.getActividad().getIdActividad().equals(id_act) && i.getTipo().getIdTipo().equals(id_tipo)){
				resp = i;
				break;
			}
		}
		return resp;
	}
	
	//Lista x Inter
	public List<Inter> findAllInterXTipo(Integer id_usr){
		List<Inter> listado = new ArrayList<Inter>();
		List<Inter> todos = findAllInter();
			for (Inter tipousr : todos) {
				if(tipousr.getTipo().getIdTipo().equals(id_usr)){
					listado.add(tipousr);
				}
			}
		return listado;
	}
	
	//metodo para asignar la Actividad al Inter
	 	public Actividad asignarT(Integer id) {
	 		try {
				act=ActividadByID(id);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	 		return act;
		}
	 		//edicion
	 	public void mas(Actividad t ,Tipo  u){
			Inter r = new Inter();
			r.setActividad(t); 
			r.setTipo(u);
			try {
				mDAO.insertar(r);
			} catch (Exception e) {
				System.out.print("metodo_mas_mal");
			}
		}
		
		public void menos(Integer id){
			try {
				mDAO.eliminar(Inter.class, id);
			} catch (Exception e) {
				System.out.print("metodo_menos_mal");
			}
		}
	
		//metodo para add actividad al inter ingreso
		public void mas(){
			Inter r = new Inter();
			r.setActividad(act);
			r.setTipo(tip);
			System.out.println("hasta aca llega:metodo mas");
			try {
				mDAO.insertar(r);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	
		//metodo para del actividad al inter
		public void menos(){
				try {
					mDAO.eliminar(Inter.class, findbyidext().getIdInter());
				} catch (Exception e) {
					System.out.print("metodo_menos_mal");
				}
			}

	//metodo para buscar por id_act y id_tipo en Inter
	public Inter findbyidext(){
		List<Inter> s = findAllInter();
		Inter u= new Inter();
		for (Inter t : s){
			if (t.getActividad().getIdActividad()==act.getIdActividad() && t.getTipo().getIdTipo()==tip.getIdTipo()){
				u=t;
			}
		}
		System.out.println("si llega aca");
		return u;
	}
	
	public Integer[] tiposDeTipo(Integer id_usr){
		ArrayList<Integer> resp = new ArrayList<Integer>();
		List<Inter> lista = findAllInterXTipo(id_usr);
		for (Inter tipousr : lista) {
			resp.add(tipousr.getIdInter());
		}
		return (Integer[]) resp.toArray();
	}	
	
	// ------Tipo-------

			// listar todos los tipos
			@SuppressWarnings("unchecked")
			public List<Tipo> findAllTipo() {
				return mDAO.findAll(Tipo.class);
			}

			// buscar tipoevento por ID
			public Tipo TipoByID(Integer id) throws Exception {
				return (Tipo) mDAO.findById(Tipo.class, id);
			}
			
			//insertar tipo 
				public void insertarTipo(String tipo, String descripcion, Integer[] listado) throws Exception{
					Tipo te = new Tipo();
					te.setTipo(tipo);
					te.setDescripcion(descripcion);
					mDAO.insertar(te);
					//Asignar ROLES
					for (Integer i : listado) {
						this.insertarInter(te, ActividadByID(i));
					}
					
				}
				
				//insertar tipousr aun no
				public void insertarInter(Tipo t, Actividad a) throws Exception{
					Inter tu = new Inter();
					tu.setActividad(a);
					tu.setTipo(t);;				
					mDAO.insertar(tu);
				}	
				
				//Modificar el tipo
				public void editarTipo(Integer id_tipo, String tipo, String descripcion) throws Exception{
					Tipo te = TipoByID(id_tipo);
					te.setTipo(tipo);
					te.setDescripcion(descripcion);
					mDAO.actualizar(te);
				}
				
				//metodo para asignar el Tipo al Usuario
			 	public Tipo asignarTipo(Integer id) {
			 		try {
						t=this.TipoByID(id);
						System.out.println("si relizo la entrada");
					} catch (Exception e) {
						System.out.println("asignacion de tipo erroneo");
						e.printStackTrace();
					}
			 		return t;
				}
			 	
			 	//metodo para asignar la Tipo al Inter
			 	public Tipo asignarTipo2(Integer id) {
			 		try {
						tip= TipoByID(id);
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
			 		return tip;
				}
			
	//listar todos los tipoEstado 
	@SuppressWarnings("unchecked")
	public List<Tipoestadousr> findAllTipoEstadousr(){
		return mDAO.findAll(Tipoestadousr.class);
	}		
			
	//buscar tipo estadousr por ID
	public Tipoestadousr EstadoByID(int id_Estado) throws Exception{
		return (Tipoestadousr) mDAO.findById(Tipoestadousr.class, id_Estado);
	}
	
	 // listar todas las actividades
	@SuppressWarnings("unchecked")
	public List<Actividad> findAllActividades() {
		return mDAO.findAll(Actividad.class);
	}

	// buscar Actividad por ID
	public Actividad ActividadByID(Integer id) throws Exception {
		return (Actividad) mDAO.findById(Actividad.class, id);
	}
	
	//metodo para asignar el Tiposestado al usuario
 	public Tipoestadousr asignarTipoestusr(Integer idtipoestusr) {
 		try {
 			tipoestadousr = EstadoByID(idtipoestusr);	 				
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
 		return tipoestadousr;
	}
 	
 	
	
	//desactivar y activar estado	
	public String cambioDisEstadousr(Integer id) throws Exception{
		List<Tipoestadousr> lista= findAllTipoEstadousr();
		
		String h="";
		for (Tipoestadousr ta: lista){
			if (ta.getIdTipoestadousr().equals(id)){
				setP(1);
			}
		}
		Usuario usr = UsuarioByID(id);				
		Tipoestadousr tipestusr = new Tipoestadousr();
		
		
		if(usr.getTipoestadousr().getNombreestado().equals("Pendiente")){
			tipestusr.setIdTipoestadousr(2);
			tipestusr.setNombreestado("Activado");				
			usr.setTipoestadousr(tipestusr);				
			h="Estado del Registro Modificado";
			}
		else if(usr.getTipoestadousr().getNombreestado().equals("Activado")){
			tipestusr.setIdTipoestadousr(3);
			tipestusr.setNombreestado("Desactivado");				
			usr.setTipoestadousr(tipestusr);
			usr.setSms("No Notificado");
			h="Estado del Registro Modificado";
		}
		else if(usr.getTipoestadousr().getNombreestado().equals("Desactivado")){
			tipestusr.setIdTipoestadousr(2);
			tipestusr.setNombreestado("Activado");				
			usr.setTipoestadousr(tipestusr);
			usr.setSms("No Notificado");
			h="Estado del Registro Modificado";
		}
		mDAO.actualizar(usr);
		return h;
		}		
	
	
	
	/**
	 * Cambiar datos de perfil usuario
	 * @param id_usr
	 * @param nombre
	 * @param apellido
	 * @param correo
	 * @throws Exception
	 */
	public void modificarDatosUSR(Integer id_usr,String cedula,String nombre, String apellido, String correo) throws Exception{
		Usuario usr = this.UsuarioByID(id_usr);
		usr.setCedula(cedula);
		usr.setNombre(nombre);
		usr.setApellido(apellido);
		usr.setCorreo(correo);
		mDAO.actualizar(usr);
	}
	
	/**
	 * Cambiar pass perfil usuario
	 * @param id_usr
	 * @param pass
	 * @throws Exception
	 */
	public void cambiarPassUSR(Integer id_usr, String pass) throws Exception{
		Usuario usr = this.UsuarioByID(id_usr);
		usr.setPassword(pass);
		mDAO.actualizar(usr);
	}
	
	/**
	 * Buscar Tipo Login por ID
	 * @param id_tipologin
	 * @return TipoLogin
	 * @throws Exception
	 */
	public Actividad findActividadByID(Integer id_tipologin) throws Exception{
		try {
			return (Actividad) mDAO.findById(Actividad.class, id_tipologin);
		} catch (Exception e) {
			throw e;
		}
	}
	
	/**
	 * Método que busca un usuario respecto a su nick y contraseña
	 * @param nick 
	 * @param pass
	 * @return Usuario o null
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public Usuario findUserByAliasAndPass(String alias, String pass)throws Exception{
		try {
			List<Usuario> listado = (List<Usuario>) mDAO.findByParam(Usuario.class, "o.alias", alias, null);
			if(listado == null || listado.isEmpty()){
				throw new Exception("No se encuentra el usuario."); 
			}
			Usuario u = listado.get(0);
			if(u.getTipoestadousr().equals("desactivado")){
				throw new Exception("Su usuario ha sido desactivado.");
			}
			if (u.getPassword().equals(getMD5(pass))) {//MD5 PASS
				return u;
			}else{
				throw new Exception("Usuario o contraseña invalidos");
			}		
		} catch (Exception e) {	
			throw new Exception("Error al intentar ingresar al sistema, "+e.getMessage());
		}
	}
	
	/**
	 * Método que verifica si el usario pertenece a ese rol
	 * @param id_usr
	 * @param id_rol
	 * @return boolean
	 */
	public boolean existeUsarioRol(Integer id_usr, Integer id_rol){
		boolean resp = false;
		List<Inter> listado = findAllInterXTipo(id_usr);
		System.out.println("tam "+listado.size());
		for (Inter tipousr : listado) {
			if(tipousr.getActividad().getIdActividad().equals(id_rol)){
				resp = true;
			}
		}
		return resp;
	}
	
	/**
	 * 
	 * @param input entrada de cadena para convertirla en MD5
	 * @return String MD5
	 */
	public String getMD5(String input) {
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            byte[] messageDigest = md.digest(input.getBytes());
            BigInteger number = new BigInteger(1, messageDigest);
            String hashtext = number.toString(16);
            while (hashtext.length() < 32) {
                hashtext = "0" + hashtext;
            }
            return hashtext;
        }catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }
	
	/**
	 * Verifica si el usuario esta activado
	 * @param u Usuario a analizar
	 * @return true o false
	 */
	public boolean esUsrActivo(Usuario u){
		boolean  resp = false;
		if(u.getTipoestadousr().getNombreestado().toLowerCase().equals("activado")){
			resp = true;
		}
		return resp;
	}
	
	/**
	 * Busca el usuario por alias
	 * @param alias nombre del usuario especifico para el acceso
	 * @return
	 */
	public Usuario findUserByAlias(String alias){
		Usuario  u = null;
		List<Usuario> usuarios = findAllUsuarios();
		for (Usuario usuario : usuarios) {
			if(usuario.getAlias().equals(alias)){
				u = usuario;
			}
		}
		return u;
	}
	
	/**
	 * Busca los tipos a los q pertenece el usuario
	 * @param alias nombre del usuario especifico para el acceso
	 * @return
	 */
	public List<Actividad> findActividadesXaliasUSR(String alias) throws Exception{
		List<Actividad> listado = new ArrayList<Actividad>();
		Usuario u = findUserByAlias(alias);
		if(u==null){
			throw new Exception("Usuario no existente");
		}else{
			try {
				List<Inter> todos = findAllInter();
				for (Inter tipousr : todos) {
					if(tipousr.getTipo().getIdTipo().equals(u.getIdUsr())){
						listado.add(findActividadByID(tipousr.getActividad().getIdActividad()));
					}
				}
			} catch (Exception e) {
				throw new Exception("Error al cargar los tipos de usuario pertenecientes");
			}
		}
		return listado;
	}
	
	
	
	

//metodo para asignar el Usuario a TipoU
 	public Usuario asignarU(Integer idUsr) {
 		try {
			tu=UsuarioByID(idUsr);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
 		return tu;
	}
 	
 	
 	//metodo para recargar Actividades ocupados
	public List<Inter> reload (Integer h){
 		List<Inter> c= findAllInter();
 		List<Inter> j= new ArrayList<Inter>();
 		for (Inter ti : c) {
 			if (h==ti.getTipo().getIdTipo()){
 				System.out.println("si llega esto");
 				j.add(ti);
 			}
		}
 		System.out.println(j.size()+"");
 		return j;
 	}
	
	//metodo para enviar el estado del mensaje si se envio
		public String cambioSMSenvio(Integer id_Usr)  throws Exception{		
			h="";
			try
			{				
				Usuario usr = this.UsuarioByID(id_Usr);
				if(usr.getSms().equals("No Notificado"))	
				{				
					usr.setSms("Notificado");						
				}
			}
			catch (Exception e)
			{
				throw new Exception("No se envio el mensaje");	
			}					
			return h;
		}
		
		
//////METODO DE ENVIO DE CORREO
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
//	            message.addRecipients(
//	                Message.RecipientType.TO,
//	                 InternetAddress.parse(destinatario));
//	                  message.setSubject(asunto);
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
		
		//sugerencias
		// listar todos las sugerencias 
		@SuppressWarnings("unchecked")
		public List<Sugerencia> findAllSugerencias(){
			return mDAO.findAll(Sugerencia.class);
		}
		
		// listar todos las sugerencias ordenadas
				@SuppressWarnings("unchecked")
				public List<Sugerencia> findAllSugerenciasOrdenadas(){
					return mDAO.findWhere(Sugerencia.class, "1=1", "o.fecha desc");
				}
		//sugerencia por ID
		public Sugerencia findsugerenciaByID(Integer id_sug) throws Exception{
			return (Sugerencia) mDAO.findById(Sugerencia.class, id_sug);
		}
		
		//ususario por ID
		public Usuario findususarioByID(Integer id_usu) throws Exception{
			return (Usuario) mDAO.findById(Usuario.class, id_usu);
		}
		
		//insertar sugerencia 
		public void insertarSugerencia(Integer id_usr,String sugerencia,Timestamp fecha) throws Exception{
			try
			{
			Sugerencia su = new Sugerencia();
			su.setUsuario(findususarioByID(id_usr));
			su.setSugerencia(sugerencia);
			su.setFecha(fecha);
			su.setSms("No Revisada");
			mDAO.insertar(su);			
		} catch (Exception e) {
			throw new Exception("Error al Crear Sugerencia "+e.getMessage());
		}
		}
		
		//metodo para enviar el estado del mensaje si se envio
				public String cambioSMSenviosugerencia(Integer id_suge)  throws Exception{		
					h="";
					try
					{				
						Sugerencia sug = this.findsugerenciaByID(id_suge);
						if(sug.getSms().equals("No Revisada"))	
						{				
							sug.setSms("Revisada");						
						}
					}
					catch (Exception e)
					{
						throw new Exception("No se envio el mensaje");	
					}					
					return h;
				}
		
		
		//metodo para qe el administrador pueda recivir mensajes de solicitudes como inscripciones
		public String cambio(Integer id_Usr, boolean principal)  throws Exception{		
			h="";
			try
			{				
				Usuario usr = this.UsuarioByID(id_Usr);
				if(usr.getPrincipal().equals(principal))
				{
					return h;
				}
				else if(usr.getPrincipal().equals(true))	
				{				
					usr.setPrincipal(true);						
				}
				else if(usr.getPrincipal().equals(false))	
				{
					usr.setPrincipal(false);
				}
			}
			catch (Exception e)
			{
				throw new Exception("No se cambio el estado");	
			}					
				return h;
			}
				///////////////////////////intereses
				// listar todos los tiposeventos
				@SuppressWarnings("unchecked")
				public List<Interes> findAllIntereses() {
					return mDAO.findAll(Interes.class);
				}

				// buscar tipoevento por ID
				public Interes InteresesByID(Integer id_int) throws Exception {
					return (Interes) mDAO.findById(Interes.class, id_int);
				}
				
				
				@SuppressWarnings("unchecked")
				public List<Interesesmid> findAllInteresesmid() {
					return mDAO.findAll(Interesesmid.class);
				}

				// buscar tipoevento por ID
				public Interesesmid InteresesmidByID(Integer id_int) throws Exception {
					return (Interesesmid) mDAO.findById(Interesesmid.class, id_int);
				}
				//insertar tipo de evento
				public void insertarInteres(String nombreinteres) throws Exception{
					Interes te = new Interes();
					te.setNombreInt(nombreinteres);
					mDAO.insertar(te);
				}
				
				//Modificar el tipoevento
				public void editarInteres(Integer id_interes, String nombreinteres) throws Exception{
					Interes te = InteresesByID(id_interes);
					te.setNombreInt(nombreinteres);
					mDAO.actualizar(te);
				}
				public static int getP() {
					return p;
				}
				public static void setP(int p) {
					ManagerLogin.p = p;
				}
}
