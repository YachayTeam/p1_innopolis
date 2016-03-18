package innopolis.manager;

import innopolis.entidades.Actividad;
import innopolis.entidades.Camposnuevo;
import innopolis.entidades.Inscripcione;
import innopolis.entidades.Inter;
import innopolis.entidades.Interinscam;
import innopolis.entidades.Tipo;

import java.util.ArrayList;
import java.util.List;

public class ManagerInscripedit {
	private ManagerDAO mDAO;
	
	
	private static Camposnuevo camnu;
	private static Inscripcione ins;
	
	public ManagerInscripedit() {
		mDAO = new ManagerDAO();
		
	}
	
	// ------camposnuevo-------

			// listar todos los campos
			@SuppressWarnings("unchecked")
			public List<Camposnuevo> findAllcamposnuevo() {
				return mDAO.findAll(Camposnuevo.class);
			}

			// buscar camposnuevo por ID
			public Camposnuevo camposmnuevoByID(Integer id_camnu) throws Exception {
				return (Camposnuevo) mDAO.findById(Camposnuevo.class, id_camnu);
			}

			// insertar los camposnuevo
			public void insertarcampos(String etiqueta, String campo)
					throws Exception {				
				try {
					Camposnuevo r = new Camposnuevo();
					r.setEtiqueta(etiqueta);
					r.setCampo(campo);
					mDAO.insertar(r);
			 		//insertar en la media
					//insertarInterinscam(r,inscri);					
					System.out.println("Bien_insertar_campo");
				} catch (Exception e) {
					System.out.println("Error_insertar_campo");
					e.printStackTrace();
				}				
			}
			//insertar tipousr aun no
			public void insertarInterinscam(Camposnuevo t, Inscripcione a) throws Exception{
				Interinscam tu = new Interinscam();
				tu.setCamposnuevo(t);
				tu.setInscripcione(a);				
				mDAO.insertar(tu);
			}	
			
			// editar los camponuevo
			public void editarcampos(Integer id_, String etiqueta,String campo) throws Exception {
				try {
					Camposnuevo  r = this.camposmnuevoByID(id_);
					r.setIdCampo(id_);
					r.setEtiqueta(etiqueta);
					r.setCampo(campo);					
					mDAO.actualizar(r);
					System.out.println("bien_mod_evento");
				} catch (Exception e) {
					System.out.println("Error_mod_evento");
					e.printStackTrace();
				}
			}
			//metdodo eliminar servicio
			public void eliminarcampo(Integer id_campo) throws Exception {
				try
				{
				  Camposnuevo camposnu = camposmnuevoByID(id_campo);
				  if(camposnu.getInterinscams().isEmpty())
					  mDAO.eliminar(Camposnuevo.class, id_campo);
				  else 
				  throw new Exception("No se elminio");
				}
				catch (Exception e)
				{
					throw e;
				}
			}

			
			
			// ------INTERINSCAM--------			
			 // listar todas las interinscam
			@SuppressWarnings("unchecked")
			public List<Interinscam> findAllInterinscam() {
				return mDAO.findAll(Interinscam.class);
			}

			// buscar interinscam por ID
			public Interinscam InterinscamByID(Integer id) throws Exception {
				return (Interinscam) mDAO.findById(Interinscam.class, id);
			}
			
			//Buscar interinscam
			public Interinscam findByInterinteinscam(Integer id_campo, Integer id_inscripcione){
				Interinscam resp = null;
				List<Interinscam> listado = findAllInterinscam();
				for (Interinscam i : listado) {
					if(i.getInscripcione().getIdInscripcion().equals(id_inscripcione) && i.getCamposnuevo().getIdCampo().equals(id_campo)){
						resp = i;
						break;
					}
				}
				return resp;
			}
					
			//Lista x Interinscam
			public List<Interinscam> findAllInterXcampo(Integer id_insc){
				List<Interinscam> listado = new ArrayList<Interinscam>();
				List<Interinscam> todos = findAllInterinscam();
					for (Interinscam inscripcione : todos) {
						if(inscripcione.getInscripcione().equals(id_insc)){
							listado.add(inscripcione);
						}
					}
				return listado;
			}
			
			
			//metodo para asignar la camponuevo al Interinscam
		 	public Camposnuevo asignacamnuevo(Integer id) {
		 		try {
					camnu=camposmnuevoByID(id);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		 		return camnu;
			}
		
			
			//insertar tipousr aun no
			public void insertarInter(Tipo t, Actividad a) throws Exception{
				Inter tu = new Inter();
				tu.setActividad(a);
				tu.setTipo(t);;				
				mDAO.insertar(tu);
			}	
		 	
		 	
		 	
		 	//editar en intermedia
		 	public void mas(Camposnuevo t ,Inscripcione  u){
				Interinscam r = new Interinscam();
				r.setCamposnuevo(t); 
				r.setInscripcione(u);
				try {
					mDAO.insertar(r);
				} catch (Exception e) {
					System.out.print("metodo_mas_mal");
				}
			}
			//eliminar campo tal ves se use
		 	public void menos(Integer id){
				try {
					mDAO.eliminar(Interinscam.class, id);
				} catch (Exception e) {
					System.out.print("metodo_menos_mal");
				}
			}
			
			//ingresar camponuevo al interinscam
			public void mas(){
				Interinscam r = new Interinscam();
				r.setCamposnuevo(camnu);
				r.setInscripcione(ins);
				System.out.println("hasta aca llega:metodo mas");
				try {
					mDAO.insertar(r);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			
			//metodo para deliliminar camponuevo al inter
			public void menos(){
					try {
						mDAO.eliminar(Interinscam.class, findbyidext().getIdInterinscam());
					} catch (Exception e) {
						System.out.print("metodo_menos_mal");
					}
				}
			
			//metodo para buscar por id_act y id_tipo en Inter
			public Interinscam findbyidext(){
				List<Interinscam> s = findAllInterinscam();
				Interinscam u= new Interinscam();
				for (Interinscam t : s){
					if (t.getInscripcione().getIdInscripcion()==ins.getIdInscripcion() && t.getCamposnuevo().getIdCampo()==camnu.getIdCampo()){
						u=t;
					}
				}
				System.out.println("si llega aca");
				return u;
			}
			
			public Integer[] tiposDeInscr(Integer id_insc){
				ArrayList<Integer> resp = new ArrayList<Integer>();
				List<Interinscam> lista = findAllInterXcampo(id_insc);
				for (Interinscam tipousr : lista) {
					resp.add(tipousr.getIdInterinscam());
				}
				return (Integer[]) resp.toArray();
			}	
			
			
}
