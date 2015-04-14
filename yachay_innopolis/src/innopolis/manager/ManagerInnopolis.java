package innopolis.manager;

import java.util.List;

import innopolis.entities.Evento;
import innopolis.entities.Recurso;
import innopolis.entities.Recursoestado;
import innopolis.entities.Recursotipo;

public class ManagerInnopolis {
	
	private ManagerDAO mDAO;
	
	
	public ManagerInnopolis()
	{
		mDAO= new ManagerDAO();
	}
	
	
// ------RECURSOS-------
	
// listar todos los recursos 
	@SuppressWarnings("unchecked")
	public List<Recurso> findAllRecurso(){
		return mDAO.findAll(Recurso.class);
	}
	
// listar todos los TipoRecursos 
		@SuppressWarnings("unchecked")
		public List<Recurso> findAllTipoRecurso(){
			return mDAO.findAll(Recursotipo.class);
		}
	
//buscar recurso por ID
	public Recurso RecursoByID(Long id_recurso) throws Exception{
		return (Recurso) mDAO.findById(Recurso.class, id_recurso);
	}
	
//insertar los recursos
	public void insertarRecurso(Long capacidad, String descripcion, String lugar, String nombre,Recursotipo rt, Recursoestado re) throws Exception{
		Recurso r = new Recurso();
		r.setCapacidad(capacidad);
		r.setDescripcion(descripcion);
		r.setLugar(lugar);
		r.setNombre(nombre);
		r.setRecursotipo(rt);
		r.setRecursoestado(re);
		mDAO.insertar(r);
	}
	
//insertar tipo de recurso
	public void insertarTipoRecurso(String tipo) throws Exception{
		Recursotipo rt = new Recursotipo();
		rt.setTipo(tipo);
		mDAO.insertar(rt);
	}

//editar los recursos
	public void editarRecurso(Long idRecurso, Long capacidad, String descripcion, String lugar, String nombre, Recursotipo rt){
		try {
			Recurso r = this.RecursoByID(idRecurso);
			r.setCapacidad(capacidad);
			r.setDescripcion(descripcion);
			r.setLugar(lugar);
			r.setNombre(nombre);
			r.setRecursotipo(rt);
			mDAO.actualizar(r);
		} catch (Exception e) {
			System.out.println("Error_mod_recurso");
			e.printStackTrace();
		}
	}
}
