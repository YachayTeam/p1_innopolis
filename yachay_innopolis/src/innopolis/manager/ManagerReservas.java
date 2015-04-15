package innopolis.manager;

import java.sql.Time;
import java.util.Date;
import java.util.List;
import java.util.ArrayList;

import innopolis.entities.*;

public class ManagerReservas {
	
	private ManagerDAO mDAO;
	
	//Solicitud Temporal
	private Solicicabecera soliTemp;
	
	//Almacenar tipos y estados
		private static Recursoestado re;
		private static Recursotipo rt;
		private static Recursodisponible rd;
	
	public ManagerReservas()
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
			public List<Recursotipo> findAllTipoRecurso(){
				return mDAO.findAll(Recursotipo.class);
			}
		
	// listar todos los RecursosEstados 
					@SuppressWarnings("unchecked")
					public List<Recursoestado> findAllRecursoEstado(){
						return mDAO.findAll(Recursoestado.class);
					}
	// listar todos los RecursosEstados 
					@SuppressWarnings("unchecked")
					public List<Recursoestado> findAllRecursoDisponibles(){
						return mDAO.findAll(Recursodisponible.class);		
					}
					
	//buscar recurso por ID
		public Recurso findRecursoByID(Integer id_recurso) throws Exception{
			return (Recurso) mDAO.findById(Recurso.class, id_recurso);
		}
		//buscar RecursoTipo por ID
			public Recursotipo findRecursoTipoByID(Integer id_rec_tipo) throws Exception{
				return (Recursotipo) mDAO.findById(Recursotipo.class, id_rec_tipo);
			}
			
			//buscar RecursoEstado por ID
			public Recursoestado findRecursoEstadoByID(Integer id_rec_estado) throws Exception{
				return (Recursoestado) mDAO.findById(Recursoestado.class, id_rec_estado);
			}
		
			//buscar RecursoDisponible por ID
					public Recursodisponible findRecursoDisponibleByID(Long id_rec_disponible) throws Exception{
						return (Recursodisponible) mDAO.findById(Recursodisponible.class, id_rec_disponible);
					}
	//insertar los recursos
		public void insertarRecurso(Integer capacidad, String descripcion, String lugar, String nombre, String imagen ) throws Exception{
			Recurso r = new Recurso();
			r.setCapacidad(capacidad);
			r.setDescripcion(descripcion);
			r.setLugar(lugar);
			r.setNombre(nombre);
			r.setImagen(imagen);
			r.setRecursotipo(rt);
			r.setRecursoestado(re);
			r.setRecursodisponible(rd);
			mDAO.insertar(r);
		}
		
	//insertar tipo de recurso
		public void insertarTipoRecurso(String tipo) throws Exception{
			Recursotipo rt = new Recursotipo();
			rt.setTipo(tipo);
			mDAO.insertar(rt);
		}

	//editar los recursos
		public void editarRecurso(Integer idRecurso, Integer capacidad, String descripcion, String lugar, String nombre){
			try {
				Recurso r = this.findRecursoByID(idRecurso);
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
		
		//metodo para asignar el RecursoTipo al Recurso
	 	public Recursotipo asignarRecursoTipo(Integer idRecTipo) {
	 		try {
				rt=findRecursoTipoByID(idRecTipo);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	 		return rt;
		}
	 	
	 	//metodo para asignar el RecursoEstado al Recurso
	 	 	public Recursoestado asignarRecursoEstado(Integer idRecEstado) {
	 	 		try {
					re=findRecursoEstadoByID(idRecEstado);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
	 	 		return re;
	 		}

	// ------SOLICITUDES-------
	
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
		
		public Solicicabecera findSolicitudCabeceraById(Long id) throws Exception{
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
				
				public void agregarSolicitudDetalleTmp(Integer id_recurso, Integer cantidad) throws Exception{
					Solicidetalle det;
					Recurso rec;
					
					//Validaciones de proceso
					if(soliTemp == null)
						throw new Exception("Error primero debe crear una solicitud.");
					if(id_recurso==null||id_recurso== -1)
						throw new Exception("Error debe especificar el recurso.");
					if(cantidad==null||cantidad.intValue()<=0)
						throw new Exception("Error debe especificar la cantidad del recurso.");
					//FALTA VALIDACIONES DE RECURSOS por fecha y capacidad
					
					//Busqueda Recurso Libre--- Cambiar Método
					rec = this.findRecursoByID(id_recurso);
					//Crear detalle
					det = new Solicidetalle();
					det.setRecurso(rec);
					det.setCapacidad(cantidad);
					//Agregar al la solicitud
					soliTemp.getSolicidetalles().add(det);
					
				}
				
				public void quitarDetalleSolicitudTem(Solicidetalle sd){
					soliTemp.removeSolicidetalle(sd);
				}
				
				//Guardar Solicitud Temporal
				public void guardarSolicitudTemporal(Solicicabecera solicitud) throws Exception{
					
					if(soliTemp==null)
						throw new Exception("Debe crear una solicitud primero.");
					if(soliTemp.getSolicidetalles()==null || soliTemp.getSolicidetalles().size()==0)
						throw new Exception("Debe ingresar los recursos en la solicitud.");
					
					for(Solicidetalle det : soliTemp.getSolicidetalles()){
						//Combinamos la relacion bidireccional
						det.setSolicicabecera(soliTemp);
					}
					
					//Insertamos los datosa la bdd
					mDAO.insertar(soliTemp);
					
					soliTemp = null;
				}
		
				
		

}

