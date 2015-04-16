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
			
		// listar todos los RecursosDisponibles 
						@SuppressWarnings("unchecked")
						public List<Recursodisponible> findAllRecursoDisponibles(){
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
				
			
				//buscar RecursoDisponible por ID
						public Recursodisponible findRecursoDisponibleByID(Integer id_rec_disponible) throws Exception{
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
				r.setRecursodisponible(this.findRecursoDisponibleByID(1));
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
		 	
		 	 	
		 	 //metodo para asignar el RecursoDisponible al Recurso
		 	 	public Recursodisponible asignarRecursoDisponible(Integer idRecDisponible) {
		 	 		try {
						rd=findRecursoDisponibleByID(idRecDisponible);
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
		 	 		return rd;
		 		}
		 	 	
		 	 	 //desactivar y activar Recurso
		 		public void cambioDisRecurso(Integer id) throws Exception{
		 			Recurso r = findRecursoByID(id);
		 			Recursodisponible t= new Recursodisponible();
		 			System.out.print(r.getIdRecurso());
					if(r.getRecursodisponible().getDisponible().equals("Activado")){
						t.setIdRecdisponible(2);
						t.setDisponible("Desactivado");
						r.setRecursodisponible(t);
		 			}else{
		 				t.setIdRecdisponible(1);
						t.setDisponible("Activado");
						r.setRecursodisponible(t);
		 			}
					System.out.println(r.getRecursodisponible().getDisponible());
					mDAO.actualizar(r);
		 		}
		 		
	// ------SOLICITUDES-------
	//Disponibilidad Libre y Activo
	
	//SolicitudEstado
	@SuppressWarnings("unchecked")
	public List<Soliciestado> findAllSolicitudEstado(){
		return mDAO.findAll(Soliciestado.class);
	}
	
	public Soliciestado findSolicitudEstadoByID(Integer pID) throws Exception{
		return (Soliciestado) mDAO.findById(Soliciestado.class, pID);
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
	public Solicicabecera crearSolicitudTmp(String direccion, String actividad, Date fecha, Time horafin, Time horainicio) throws Exception{
		soliTemp=new Solicicabecera();
		soliTemp.setActividad(actividad);soliTemp.setDireccion(direccion);soliTemp.setFecha(fecha);
		soliTemp.setHorainicio(horainicio);soliTemp.setHorafin(horafin);
		//OJO ESTADOS
		soliTemp.setSoliciestado(findSolicitudEstadoByID(1));//1 Pendiente
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
		//Busqueda Recurso Libre--- Cargar en el list recursos libres por fecha y Hora
		rec = this.findRecursoByID(id_recurso);
		//Validar cantidad
		if(cantidad>rec.getCapacidad())
			throw new Exception("La capacidad es mayor a la del recurso solicitado");
			
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
		
				
	//Tabla RECURSOACTIVO para reservaciones realizadas
	@SuppressWarnings("unchecked")
	public List<Recursosactivo> findAllRecursosSolicitados(){
		return mDAO.findAll(Recursosactivo.class);
	}
	
	public void insertarRecursoSolicitado(Integer idSolicitud, Date fecha, Time hora_inicio, Time hora_fin, Integer id_recurso)throws Exception{
		Recursosactivo recact = new Recursosactivo();
		recact.setIdSolicitud(idSolicitud);recact.setFecha(fecha);recact.setHoraInicio(hora_inicio);
		recact.setHoraFin(hora_fin);recact.setIdRecurso(id_recurso);
		mDAO.insertar(recact);
	}
	
	public void eliminarRecursoSolicitado(Long id_tabla) throws Exception{
		mDAO.eliminar(Recursosactivo.class, id_tabla);
	}
				
	//RECURSOS LIBRES --> NO TOME EN CUENTA HORA FIN
    //HORA FIN NECESITO PARA CALCULAR LA PROXIMA HORA INICIO
	//RecursosXFecha
	//Devuelve todos recursos que se encuentran ocupados en esa fecha
	public List<Recursosactivo> findAllRecursoLibreByFecha(Date fecha_seleccionada){
		List<Recursosactivo> resultado = this.findAllRecursosSolicitados();
		List<Recursosactivo> listado = this.findAllRecursosSolicitados();
		
		for (Recursosactivo recursosactivo : listado) {
			if(!recursosactivo.getFecha().equals(fecha_seleccionada)){
				resultado.remove(recursosactivo);
			}
		}
		
		return resultado;
	}
	
	//RecursosXHora
	//Devuelve los recursos que esten ocupados de una fecha en un horario
	public List<Recursosactivo> findAllRecursoLibreByHorario(Date fecha_seleccionada, Time hora_inicio){
		List<Recursosactivo> resultado = this.findAllRecursoLibreByFecha(fecha_seleccionada);
		List<Recursosactivo> listado = this.findAllRecursoLibreByFecha(fecha_seleccionada);
		
		for (Recursosactivo recursosactivo : listado) {
			if(!recursosactivo.getHoraInicio().equals(hora_inicio)){
				resultado.remove(recursosactivo);
			}
		}
		
		return resultado;
	}
	
	//Devuelve un valor booleano para conocer si se encuentra ocupado o no el recurso
	public boolean findRecursosSolicitadosLibreByHorario(Integer id_recurso, Date fecha, Time hora_inicio){
		List<Recursosactivo> listado = this.findAllRecursoLibreByHorario(fecha, hora_inicio);
		
		for (Recursosactivo recursosactivo : listado) {
			if(recursosactivo.getIdRecurso().equals(recursosactivo)){
				return true;
			}
		}
		
		return false;
	}
	
	//Carga de recursos disponibles
	public List<Recurso> findAllRecursosDisponibles(Date fecha, Time hora_inicio){
		List<Recurso> listado = this.findAllRecurso();
		List<Recurso> resultados = this.findAllRecurso();
		
		for (Recurso recurso : listado) {
			if(this.findRecursosSolicitadosLibreByHorario(recurso.getIdRecurso(), fecha, hora_inicio)){
				resultados.remove(recurso);
			}
		}
		
		return resultados;
	}	

}

