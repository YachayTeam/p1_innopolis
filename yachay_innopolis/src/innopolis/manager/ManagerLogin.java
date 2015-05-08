package innopolis.manager;

import innopolis.entities.Tipologin;
import innopolis.entities.Tipousr;
import innopolis.entities.Usuario;
import java.io.Serializable;
import java.util.List;

public class ManagerLogin implements Serializable{
	
	private static final long serialVersionUID = 1L;

	private ManagerDAO mDAO;
	
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
			public void editarusuario(Integer id_usr,String alias, String apellido, String correo,String nombre, String password){
				try{
					Usuario usr = new Usuario(); 
					usr.setAlias(alias);
				    usr.setApellido(apellido);
				    usr.setCorreo(correo);
				    usr.setNombre(nombre);
				    usr.setPassword(password);
				    mDAO.actualizar(usr);
				} catch (Exception e) {
					System.out.println("Error_mod_usuario");
					e.printStackTrace();
				}
			}

	//editar los tipologin
			public void editartipologin(Integer id_tipologin,String tipologinnom, String descripcion){
				try{
					Tipologin tipologin = new Tipologin();
					tipologin.setTipologin(tipologinnom);
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
			
	// listar todos los tipousr
			@SuppressWarnings("unchecked")
			public List<Tipousr> findAllTipoUsr(){
				return mDAO.findAll(Tipousr.class);
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
				  Tipologin tipologin = TipoLoginByID(id_tipologin);				  
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
	
	
	
	
	
}
