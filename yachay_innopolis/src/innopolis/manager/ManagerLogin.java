package innopolis.manager;

import innopolis.entities.Tipoestadousr;
import innopolis.entities.Tipologin;
import innopolis.entities.Tipousr;
import innopolis.entities.Usuario;

import java.io.Serializable;
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
			public void insertarusuarios(String alias, String apellido, String correo,String nombre, String password) throws Exception{
			    Usuario usr = new Usuario(); 
				usr.setAlias(alias);
			    usr.setApellido(apellido);
			    usr.setCorreo(correo);
			    usr.setNombre(nombre);
			    usr.setPassword(password);
			    usr.setTipoestadousr(this.EstadoByID(1));
				mDAO.insertar(usr);
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
			
	//editar los usuarios
			public void editarusuario(Integer id_usr,String alias, String apellido, String correo,String nombre, String password,Integer id_Estadousr){
				try{
					Usuario usr = this.UsuarioByID(id_usr); 
					usr.setAlias(alias);
				    usr.setApellido(apellido);
				    usr.setCorreo(correo);
				    usr.setNombre(nombre);
				    usr.setPassword(password);
				    usr.setTipoestadousr(this.asignarTipoestusr(id_Estadousr));
				    mDAO.actualizar(usr);
				} catch (Exception e) {
					System.out.println("Error_mod_usuario");
					e.printStackTrace();
				}
			}

	//editar los tipologin
			public void editartipologin(Integer id_tipologin,String nomtipolog, String descripcion)
			{
				try{					
					Tipologin tipologin = this.TipoLoginByID(id_tipologin);
					tipologin.setTipologin(nomtipolog);
					tipologin.setDescripcion(descripcion);					
					mDAO.actualizar(tipologin);
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
	 			
				if(usr.getTipoestadousr().getNombreestado().equals("Pendiente")){
					tipestusr.setIdTipoestadousr(2);
					tipestusr.setNombreestado("Aprobado");				
					usr.setTipoestadousr(tipestusr);				
					h="Estado del Registro Modificado";
	 			}
				else if(usr.getTipoestadousr().getNombreestado().equals("Aprobado")){
					tipestusr.setIdTipoestadousr(3);
					tipestusr.setNombreestado("Negado");				
					usr.setTipoestadousr(tipestusr);				
					h="Estado del Registro Modificado";
	 			}
				else if(usr.getTipoestadousr().getNombreestado().equals("Negado")){
					tipestusr.setIdTipoestadousr(2);
					tipestusr.setNombreestado("Aprobado");				
					usr.setTipoestadousr(tipestusr);				
					h="Estado del Registro Modificado";
	 			}
				mDAO.actualizar(usr);
				return h;
				}
			
	
}
