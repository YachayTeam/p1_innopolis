package innopolis.manager;

import java.sql.Time;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
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
			private static Recurso r;
		
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
		
		// listar todos los RecursosDisponibles 
		@SuppressWarnings("unchecked")
		public List<Recursodisponible> findAllRecursoDisponibles(){
			return mDAO.findAll(Recursodisponible.class);		
		}
					
	   //buscar recurso por ID
		public Recurso findRecursoByID(Integer id_recurso) throws Exception{
			return (Recurso) mDAO.findById(Recurso.class, id_recurso);
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
 		public String cambioDisRecurso(Integer id) throws Exception{
			List<Recursosactivo> lista= findAllRecursosSolicitados();
			int p=0;
			String h="";
			for (Recursosactivo ra: lista){
				if (ra.getIdRecurso().equals(id)){
					p=1;
				}
			}
				Recurso r = findRecursoByID(id);
	 			Recursodisponible t= new Recursodisponible();
				if(r.getRecursodisponible().getDisponible().equals("Activado") && p==0){
					t.setIdRecdisponible(2);
					t.setDisponible("Desactivado");
					r.setRecursodisponible(t);
					h="Estado del recurso modificado";
	 			}
				else if(r.getRecursodisponible().getDisponible().equals("Activado") && p==1) {
					h="Recurso ocupado no puede ser modificado";
	 			}
				else if(r.getRecursodisponible().getDisponible().equals("Desactivado")){
					t.setIdRecdisponible(1);
					t.setDisponible("Activado");
					r.setRecursodisponible(t);
					h="Estado del recurso modificado";
	 			}
				mDAO.actualizar(r);
				return h;
	}
	 		
    //Tipos Recursos
	// listar todos los TipoRecursos 
	@SuppressWarnings("unchecked")
	public List<Recursotipo> findAllTipoRecurso(){
		return mDAO.findAll(Recursotipo.class);
	}		
	 		
	//buscar RecursoTipo por ID
	public Recursotipo findRecursoTipoByID(Integer id_rec_tipo) throws Exception{
		return (Recursotipo) mDAO.findById(Recursotipo.class, id_rec_tipo);
	}
	
	//insertar tipo de recurso
	public void insertarTipoRecurso(String tipo) throws Exception{
		Recursotipo rt = new Recursotipo();
		rt.setTipo(tipo);
		mDAO.insertar(rt);
	}
	
	//Modificar
	public void editarRecursoTipo(Integer id_recursotipo, String tipo) throws Exception{
		Recursotipo rec = findRecursoTipoByID(id_recursotipo);
		rec.setTipo(tipo);
		mDAO.actualizar(rec);
	}
		 		
	// ------SOLICITUDES-------EMPRENDEDOR------------------------------------------------------------------//
	//CONTADOR TABLA AUXILIAR
	public int getContadorSolicitud() throws Exception{
		int contSolicitud = 0;
		Contadores cont = null;
		try {
			cont = (Contadores) mDAO.findById(Contadores.class, 1);
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception("Revise el parametro 'contontador solicitud': "+e.getMessage());
		}
		contSolicitud = cont.getValor();
		return contSolicitud;
	}
	
	public void actualizarContadorSolicitud(int valor) throws Exception{
		Contadores cont = null;
		try {
			cont = (Contadores) mDAO.findById(Contadores.class, 1);
			cont.setValor(valor);
			mDAO.actualizar(cont);
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception("Error al actualizar el parametro 'contontador solicitud': "+e.getMessage());
		}
	}
	
	//SolicitudEstado
	@SuppressWarnings("unchecked")
	public List<Soliciestado> findAllSolicitudEstado(){
		return mDAO.findAll(Soliciestado.class);
	}
	
	public Soliciestado findSolicitudEstadoByID(Integer pID) throws Exception{
		return (Soliciestado) mDAO.findById(Soliciestado.class, pID);
	}
	
	//Temporales
	public Solicicabecera crearSolicitudTmp(String direccion, String actividad, String objetivo, String justificacion,Date fecha, Time horafin, Time horainicio) throws Exception{
		soliTemp=new Solicicabecera();
		soliTemp.setActividad(actividad);soliTemp.setDireccion(direccion);
		soliTemp.setObjetivo(objetivo);soliTemp.setJustificacion(justificacion);soliTemp.setFecha(fecha);
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
		
		if(solicitud==null)
			throw new Exception("Debe crear una solicitud primero.");
		if(solicitud.getSolicidetalles()==null || solicitud.getSolicidetalles().size()==0)
			throw new Exception("Debe ingresar los recursos en la solicitud.");
		
		//Agregar contador
		int contSolicitud; 
		contSolicitud = this.getContadorSolicitud();
		contSolicitud++;
		solicitud.setIdSolcab(contSolicitud);
		
		//ARRAY PARA RECURSOS ACTIVOS
		ArrayList<Recursosactivo> listRecursos = new ArrayList<Recursosactivo>();
		
		//DETALLES
		for(Solicidetalle det : solicitud.getSolicidetalles()){
			//Combinamos la relacion bidireccional
			det.setSolicicabecera(solicitud);
			//Agregamos al listado de recursos activos
			Recursosactivo recAct = new Recursosactivo();
			recAct.setIdSolicitud(contSolicitud);
			recAct.setFecha(solicitud.getFecha());
			recAct.setHoraInicio(solicitud.getHorainicio());
			recAct.setHoraFin(solicitud.getHorafin());
			recAct.setIdRecurso(det.getRecurso().getIdRecurso());
			listRecursos.add(recAct);
		}
		
		//Insertamos los datosa la bdd
		mDAO.insertar(solicitud);
		
		//INSERTAR EN TABLA AYUDA
		for (Recursosactivo recursosactivo : listRecursos) {
			mDAO.insertar(recursosactivo);
		}		
		
		//actualizamos los parametros contadores
		actualizarContadorSolicitud(contSolicitud);
		
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
				
	//RECURSOS LIBRES --> REVISAR
	//HORA FIN NECESITO PARA CALCULAR LA PROXIMA HORA INICIO
	//RecursosXFecha
	//Devuelve todos recursos que se encuentran ocupados en esa fecha
	public ArrayList<Recursosactivo> findAllRecursoOcupadoByFecha(Date fecha_seleccionada){
		ArrayList<Recursosactivo> resultado = new ArrayList<Recursosactivo>();
		List<Recursosactivo> listado = this.findAllRecursosSolicitados();
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		
		for (Recursosactivo recursosactivo : listado) {
			if(dateFormat.format(recursosactivo.getFecha()).toString().equals(dateFormat.format(fecha_seleccionada).toString())){
				resultado.add(recursosactivo);
			}
		}
		
		return resultado;
	}
	
	//RecursosXHora
	//Devuelve los recursos que esten ocupados de una fecha en un horario
	public ArrayList<Recursosactivo> findAllRecursoOcupadoByHorario(Date fecha_seleccionada, Time hora_inicio, Time hora_fin){
		ArrayList<Recursosactivo> resultado = new ArrayList<Recursosactivo>();
		List<Recursosactivo> listado = this.findAllRecursoOcupadoByFecha(fecha_seleccionada);
		
		for (Recursosactivo recursosactivo : listado) {
			if( (hora_inicio.getTime()>=recursosactivo.getHoraInicio().getTime() && hora_inicio.getTime()<recursosactivo.getHoraFin().getTime()) || 
			(hora_fin.getTime()>recursosactivo.getHoraInicio().getTime() && hora_fin.getTime()<=recursosactivo.getHoraFin().getTime()) ){
				resultado.add(recursosactivo);
			}
		}
		return resultado;
	}	
	
	//Devuelve un valor booleano para conocer si se encuentra ocupado o no el recurso
	public boolean findRecursosSolicitadosLibreByHorario(Integer id_recurso, Date fecha, Time hora_inicio, Time hora_fin){
		List<Recursosactivo> listado = this.findAllRecursoOcupadoByHorario(fecha, hora_inicio, hora_fin);
		
		for (Recursosactivo recursosactivo : listado) {
			if(recursosactivo.getIdRecurso().equals(recursosactivo)){
				return true;
			}
		}
		
		return false;
	}
	
	//Carga de recursos disponibles verificando si esta ocupado
		public List<Recurso> findAllRecursosDisponibles(Date fecha, Time hora_inicio, Time hora_fin){
			List<Recurso> listado = this.findAllRecurso();
			List<Recurso> resultados = this.findAllRecurso();
			
			for (Recurso recurso : listado) {
				if(this.findRecursosSolicitadosLibreByHorario(recurso.getIdRecurso(), fecha, hora_inicio, hora_fin) || this.esRecursoDesactivado(recurso.getIdRecurso())){
					resultados.remove(recurso);
				}
			}
			
			return resultados;
		}
		
		//Recursos Activados
		public boolean esRecursoDesactivado(Integer id_recurso){
			Recurso rec = new Recurso();
			try {
				rec = this.findRecursoByID(id_recurso);
			} catch (Exception e) {
				e.printStackTrace();
			}
			if(rec.getRecursodisponible().getIdRecdisponible().equals(1)){
				return false;
			}else{
				return true;
			}	
		}
		
		//metodo para asignar el Recurso a la Solicitud
	 	public Recurso asignarRecurso(Integer idRecurso) {
	 		try {
			r=findRecursoByID(idRecurso);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	 		return r;
		}
	 	
	 	//-----------------------------APROBADOR------------------------------------------------------//
	 	//Listar solicitudes
		@SuppressWarnings("unchecked")
		public List<Solicicabecera> findAllSolicitudCabecera(){
			return mDAO.findAll(Solicicabecera.class);
		}
		
		public Solicicabecera findSolicitudCabeceraById(Integer id) throws Exception{
			return (Solicicabecera) mDAO.findById(Solicicabecera.class, id);
		}
	 	 	
	 	//Cambio de estados
		//Pide Id Estado, Busca estado y lo cambia dentro de la solicitud seleccionada
		public void cambiarEstadoSolicitud(Integer id_solicitud, Integer id_estado) throws Exception{
			Soliciestado estado = this.findSolicitudEstadoByID(id_estado);
			Solicicabecera solicitud = this.findSolicitudCabeceraById(id_solicitud);
			solicitud.setSoliciestado(estado);
			mDAO.actualizar(solicitud);
		}
	 	
	 	//Modificacion de Solicitudes
		

}