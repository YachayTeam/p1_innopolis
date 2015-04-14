package innopolis.manager;

import java.sql.Time;
import java.util.Date;
import java.util.List;
import java.util.ArrayList;

import innopolis.entities.*;

public class ManagerInnopolis {
	
	private ManagerDAO mDAO;
	
	//Solicitud Temporal
	private Solicicabecera soliTemp;
	
	
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
	
	//SolicitudEstado
		@SuppressWarnings("unchecked")
		public List<Soliciestado> findAllSolicitudEstado(){
			return mDAO.findAll(Soliciestado.class);
		}
		
		//Solicitud Cabecera
		@SuppressWarnings("unchecked")
		public List<Solicicabecera> findAllSolicitudCabecera(){
			return mDAO.findAll(Solicicabecera.class);
		}
		
		public Solicicabecera findSolicitudCabeceraById(Integer id) throws Exception{
			return (Solicicabecera) mDAO.findById(Solicicabecera.class, id);
		}
		
		//Temporales
		public Solicicabecera crearSolicitudTmp(String direccion, String actividad, Date fecha, Time horafin, Time horainicio){
			soliTemp=new Solicicabecera();
			soliTemp.setActividad(actividad);soliTemp.setDireccion(direccion);soliTemp.setFecha(fecha);
			soliTemp.setHorainicio(horainicio);soliTemp.setHorafin(horafin);
			soliTemp.setSolicidetalles(new ArrayList<Solicidetalle>());
			return soliTemp;
		}
		
		public void agregarSolicitudDetalleTmp(){
			
		}
		
		//Solicitud Detalle
		
		
		//Eventos
		@SuppressWarnings("unchecked")
		public List<Evento> findAllEventos() {
			return mDAO.findAll(Evento.class, "o.nombre");
		}

		public Evento findEventoById(Integer id_evento) throws Exception {
			return (Evento) mDAO.findById(Evento.class, id_evento);
		}

		public void insertarEvento(Evento r) throws Exception {
			mDAO.insertar(r);
		}

	//	public void eliminarEvento(Integer id_receta) throws Exception {
		//	mDAO.eliminar(Receta.class, id_receta);
	//	}

//		public void actualizarReceta(Receta receta) throws Exception {
	//		mDAO.actualizar(receta);
//		}

}
