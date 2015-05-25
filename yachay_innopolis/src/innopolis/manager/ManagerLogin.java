package innopolis.manager;

import innopolis.entities.Contadore;
import innopolis.entities.Tipoestadousr;
import innopolis.entities.Tipologin;
import innopolis.entities.Tipousr;
import innopolis.entities.Usuario;

import java.io.Serializable;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;

public class ManagerLogin implements Serializable{
	
	private static final long serialVersionUID = 1L;

	private ManagerDAO mDAO;
	
	//Registro Temporal
		private static Tipoestadousr tipoestadousr;
		private static Tipologin tipologin;
		int p=0;
		String h="";
		
		//temporales
				private static Tipologin tl;
				private static Usuario tu;
				
	public ManagerLogin() {
		mDAO= new ManagerDAO();
	}
		
	//insertar los usuarios
	public void insertarusuarios(String alias, String apellido, String correo,String nombre, String password, Integer[] listadoLogin) throws Exception{
	    Usuario usr = new Usuario(); 
		usr.setAlias(alias);
	    usr.setApellido(apellido);
	    usr.setCorreo(correo);
	    usr.setNombre(nombre);
	    usr.setPassword(password);
	    usr.setTipoestadousr(this.EstadoByID(1));//Toma Activado 
	    //Asignar ID USR
	    int contadorUsuario = this.getContUsr();
	    contadorUsuario++;
	    usr.setIdUsr(contadorUsuario);
	    //Insertar USUARIO
		mDAO.insertar(usr);
		//Actualiza el contador
		this.actualizarContadorUSR(contadorUsuario);
		//Asignar ROLES
		for (Integer i : listadoLogin) {
			this.insertarTipoUsr(usr, TipoLoginByID(i));
		}
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
	public void registrarUsuario(String alias, String apellido, String correo,String nombre, String password) throws Exception{
		Usuario usr = new Usuario(); 
		usr.setAlias(alias);
	    usr.setApellido(apellido);
	    usr.setCorreo(correo);
	    usr.setNombre(nombre);
	    usr.setPassword(password);
	    usr.setTipoestadousr(this.EstadoByID(1));//Toma Activado 
	    //Asignar ID USR
	    int contadorUsuario = this.getContUsr();
	    contadorUsuario++;
	    usr.setIdUsr(contadorUsuario);
	    //Insertar USUARIO
		mDAO.insertar(usr);
		//Actualiza el contador
		this.actualizarContadorUSR(contadorUsuario);
		//insertar ROL
		this.insertarTipoUsr(usr, (Tipologin) mDAO.findById(Tipologin.class, 4));
	}
	
	//insertar tipologin
	public void insertarTipologin(String tipologin, String descripcion) throws Exception{
		Tipologin tl = new Tipologin();
		tl.setTipologin(tipologin);
		tl.setDescripcion(descripcion);				
		mDAO.insertar(tl);
	}	
			
	//insertar tipousr aun no
	public void insertarTipoUsr(Usuario usr, Tipologin tl) throws Exception{
		Tipousr tu = new Tipousr();
		tu.setUsuario(usr);
		tu.setTipologin(tl);				
		mDAO.insertar(tu);
	}			
			
	//editar los usuarios FALTA
	public void editarusuario(Integer id_usr, String correo, String password/*, Integer[] listTipo*/) throws Exception{
		Usuario usr = this.UsuarioByID(id_usr); ;
		usr.setCorreo(correo);
		usr.setPassword(password);
		mDAO.actualizar(usr);
		
		/*/Actualizar tipos usuarios eliminar diferente, añadir nuevos
	    //tomo actuales
		List<Tipousr> listado = findAllTipoUsrXUser(id_usr);
		Integer[] actuales = new Integer[listado.size()];
		actuales = listado.toArray(actuales);
		//Saco listas
		ArrayList<Integer> menos = noExisteAenB(actuales, listTipo);
		for (Integer id_menos : menos) {
			mDAO.eliminar(Tipousr.class, findByUsrTipo(id_usr, id_menos).getIdTipusr());
		}
		
		ArrayList<Integer> mas = noExisteAenB(listTipo, actuales);
		for (Integer id_mas : mas) {
			insertarTipoUsr(usr, TipoLoginByID(id_mas));
		}*/    
		    
	}
	
	public void editarusuario(Integer id_usr, String correo) throws Exception{
		Usuario usr = this.UsuarioByID(id_usr); ;
		usr.setCorreo(correo);
		mDAO.actualizar(usr);
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
	
	//Buscar tipousr
	public Tipousr findByUsrTipo(Integer id_usr, Integer id_tipo){
		Tipousr resp = null;
		List<Tipousr> listado = findAllTipoUsr();
		for (Tipousr tipousr : listado) {
			if(tipousr.getUsuario().getIdUsr().equals(id_usr) && tipousr.getTipologin().getTipologin().equals(id_tipo)){
				resp = tipousr;
				break;
			}
		}
		return resp;
	}
	
	//Lista x Usuario
	public List<Tipousr> findAllTipoUsrXUser(Integer id_usr){
		List<Tipousr> listado = findAllTipoUsr();
		List<Tipousr> todos = findAllTipoUsr();
			for (Tipousr tipousr : todos) {
				if(!tipousr.getIdTipusr().equals(id_usr)){
					listado.remove(tipousr);
				}
			}
		return listado;
	}
	
	public Integer[] tiposDeUsuario(Integer id_usr){
		ArrayList<Integer> resp = new ArrayList<Integer>();
		List<Tipousr> lista = findAllTipoUsrXUser(id_usr);
		for (Tipousr tipousr : lista) {
			resp.add(tipousr.getIdTipusr());
		}
		return (Integer[]) resp.toArray();
	}

	//editar los tipologin
	public void editartipologin(Integer id_tl,String descripcion, String tipologin) throws Exception
	{
		try{					
			Tipologin tl = this.TipoLoginByID(id_tl);
			tl.setTipologin(tipologin);
			tl.setDescripcion(descripcion);						
			mDAO.actualizar(tl);
		} catch (Exception e) {
			System.out.println("Error_mod_tipologin");
			e.printStackTrace();
		}
	}	
	
			
	// listar todos los usuarios 
	@SuppressWarnings("unchecked")
	public List<Usuario> findAllUsuarios(){
		return mDAO.findAll(Usuario.class);
	}
	
	// listar todos los tipologin 
	@SuppressWarnings("unchecked")
	public List<Tipologin> findAllTipoLogin(){
		return mDAO.findAll(Tipologin.class);
	}
			
	//listar todos los tipoEstado 
	@SuppressWarnings("unchecked")
	public List<Tipoestadousr> findAllTipoEstadousr(){
		return mDAO.findAll(Tipoestadousr.class);
	}		
			
	// listar todos los tipousr
	@SuppressWarnings("unchecked")
	public List<Tipousr> findAllTipoUsr(){
		return mDAO.findAll(Tipousr.class);
	}	
			
	//buscar tipo estadousr por ID
	public Tipoestadousr EstadoByID(int id_Estado) throws Exception{
		return (Tipoestadousr) mDAO.findById(Tipoestadousr.class, id_Estado);
	}

	//buscar usuarios por ID
	public Usuario UsuarioByID(int id_usr) throws Exception{
		return (Usuario) mDAO.findById(Usuario.class, id_usr);
	}
		
	//buscar tipologin por ID
	public Tipologin TipoLoginByID(int id_tipologin) throws Exception{
		return (Tipologin) mDAO.findById(Tipologin.class, id_tipologin);
	}		
	
	//buscar tipousr por ID
	public Tipousr TipoUsrByID(int id_tipousr) throws Exception{
		return (Tipousr) mDAO.findById(Tipousr.class, id_tipousr);
	}		
						

	//metdodo eliminar servicio
	public void eliminarTipologin(Integer id_tipologin) throws Exception {
		try
		{
		  tipologin = TipoLoginByID(id_tipologin);				  
		  if(tipologin.getTipousrs().isEmpty())
		mDAO.eliminar(Tipologin.class, id_tipologin);
		  else 
		  throw new Exception("No se elminio");
		}
		catch (Exception e)
		{
			throw e;
		}
	}		
	
	//metodo para asignar el Tiposestado al servivirtual
 	public Tipoestadousr asignarTipoestusr(Integer idtipoestusr) {
 		try {
 			tipoestadousr = EstadoByID(idtipoestusr);	 				
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
 		return tipoestadousr;
	}
 	
 	//metodo para asignar el Tiposervicio al registro
 	 	public Tipologin asignarTipologin(Integer idtipologin) {
 	 		try {
 	 			tipologin = TipoLoginByID(idtipologin);			
 			} catch (Exception e) {
 				// TODO Auto-generated catch block
 				e.printStackTrace();
 			}
 	 		return tipologin;
 		}
	
	//desactivar y activar estado	
	public String cambioDisEstadousr(Integer id) throws Exception{
		List<Tipoestadousr> lista= findAllTipoEstadousr();
		
		String h="";
		for (Tipoestadousr ta: lista){
			if (ta.getIdTipoestadousr().equals(id)){
				p=1;
			}
		}
		Usuario usr = UsuarioByID(id);				
		Tipoestadousr tipestusr = new Tipoestadousr();
		
		if(usr.getTipoestadousr().getNombreestado().equals("Activado")){
			tipestusr.setIdTipoestadousr(2);
			tipestusr.setNombreestado("Desactivado");				
			usr.setTipoestadousr(tipestusr);				
			h="Estado del Registro Modificado";
		}
		else if(usr.getTipoestadousr().getNombreestado().equals("Desactivado")){
			tipestusr.setIdTipoestadousr(1);
			tipestusr.setNombreestado("Activado");				
			usr.setTipoestadousr(tipestusr);				
			h="Estado del Registro Modificado";
		}
		mDAO.actualizar(usr);
		return h;
		}		
	
	public void mas(Tipologin t ,Usuario  u){
		Tipousr r = new Tipousr();
		r.setTipologin(t);
		r.setUsuario(u);
		try {
			mDAO.insertar(r);
		} catch (Exception e) {
			System.out.print("metodo_mas_mal");
		}
	}
	
	public void menos(Integer id){
		try {
			mDAO.eliminar(Tipousr.class, id);
		} catch (Exception e) {
			System.out.print("metodo_menos_mal");
		}
	}
	
	/**
	 * Cambiar datos de perfil usuario
	 * @param id_usr
	 * @param nombre
	 * @param apellido
	 * @param correo
	 * @throws Exception
	 */
	public void modificarDatosUSR(Integer id_usr,String nombre, String apellido, String correo) throws Exception{
		Usuario usr = this.UsuarioByID(id_usr);
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
	public Tipologin findTipoLoginByID(Integer id_tipologin) throws Exception{
		try {
			return (Tipologin) mDAO.findById(Tipologin.class, id_tipologin);
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
			e.printStackTrace();
			throw e;
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
		List<Tipousr> listado = findAllTipoUsrXUser(id_usr);
		for (Tipousr tipousr : listado) {
			if(tipousr.getIdTipusr().equals(id_rol)){
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
	
	public void mas(){
		Tipousr r = new Tipousr();
		r.setTipologin(tl);
		r.setUsuario(tu);
		System.out.println("hasta aca llega");
		try {
			mDAO.insertar(r);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
}

public Tipousr findbyidext(){
	List<Tipousr> s = findAllTipoUsr();
	Tipousr u= new Tipousr();
	for (Tipousr t : s){
		if (t.getTipologin().getIdTipologin()==tl.getIdTipologin() && t.getUsuario().getIdUsr()==tu.getIdUsr()){
			u=t;
		}
	}
	System.out.println("si llega aca");
	return u;
}

public void menos(){
	try {
		mDAO.eliminar(Tipousr.class, findbyidext().getIdTipusr());
	} catch (Exception e) {
		System.out.print("metodo_menos_mal");
	}
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
 	
 	//metodo para asignar el Login al Tipo
 	public Tipologin asignarT(Integer idTipo) {
 		try {
			tl=TipoLoginByID(idTipo);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
 		return tl;
	}
 	//
 	
 	
 	public List<Tipousr> reload2 (Integer h){
 		List<Tipousr> c= findAllTipoUsr();
 		List<Tipousr> j= new ArrayList<Tipousr>();
 		for (Tipousr ti : c) {
 			if (h==ti.getUsuario().getIdUsr()){
 				j.add(ti);
 			}
		}
 		return j;
 	}
}
