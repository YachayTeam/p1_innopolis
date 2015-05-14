package innopolis.manager;

import innopolis.entities.Contadore;
import innopolis.entities.Tipoestadousr;
import innopolis.entities.Tipologin;
import innopolis.entities.Tipousr;
import innopolis.entities.Usuario;

import java.io.Serializable;
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
	public void editarusuario(Integer id_usr, String correo, String password, Integer[] listTipo) throws Exception{
		Usuario usr = this.UsuarioByID(id_usr); ;
		usr.setCorreo(correo);;
		usr.setPassword(password);
		mDAO.actualizar(usr);
		
		//Actualizar tipos usuarios eliminar diferente, añadir nuevos
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
		}    
		    
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
}
