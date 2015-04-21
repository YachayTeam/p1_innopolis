package innopolis.manager;

import java.util.List;

import innopolis.controller.ServiciosVirtualesBean;
import innopolis.entities.*;

public class ManagerRecursosVirtuales {
	
	private ManagerDAO mDAO;
	
	//Registro Temporal
	private static Tiposervicio tiposerv;
	private static Tipoestado tipoesta;
			
	public ManagerRecursosVirtuales()
	{
		mDAO= new ManagerDAO();
	}	
	
// ------ServiciosVirtuales-------
	//metodo para asignar el Tiposervicio al registro
 	public Tiposervicio asignarTiposerv(Integer idtiposervicio) {
 		try {
			tiposerv = findServicioTipoByID(idtiposervicio);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
 		return tiposerv;
	}
 	//metodo para asignar el Tiposestado al servivirtual
 	 	public Tipoestado asignarTipoest(Integer idtipoest) {
 	 		try {
 				tipoesta = EstadoByID(idtipoest);
 			} catch (Exception e) {
 				// TODO Auto-generated catch block
 				e.printStackTrace();
 			}
 	 		return tipoesta;
 		}
		
// listar todos los serviciosvirtuales 
	@SuppressWarnings("unchecked")
	public List<Serviciosvirtregi> findAllRServiciosVirtuales(){
		return mDAO.findAll(Serviciosvirtregi.class);
	}
	
//listar todos los tipoEstado 
	@SuppressWarnings("unchecked")
	public List<Tipoestado> findAllTipoEstado(){
		return mDAO.findAll(Tipoestado.class);
	}		
	
//listar todos los tipoServicio 
		@SuppressWarnings("unchecked")
		public List<Tiposervicio> findAllTipoServicio(){
			return mDAO.findAll(Tiposervicio.class);
		}	
	
//buscar servicio por ID
		public Serviciosvirtregi ServicioVirtualByID(int id_Srv) throws Exception{
			return (Serviciosvirtregi) mDAO.findById(Serviciosvirtregi.class, id_Srv);
		}
//buscar tipo estado por ID
		public Tipoestado EstadoByID(int id_Estado) throws Exception{
			return (Tipoestado) mDAO.findById(Tipoestado.class, id_Estado);
		}
//ServicioTipo por ID
		public Tiposervicio findServicioTipoByID(Integer id_Tp) throws Exception{
			return (Tiposervicio) mDAO.findById(Tiposervicio.class, id_Tp);
		}
		//EstadoTipo por ID
				public Tipoestado findEstadoTipoByID(Integer id_Ep) throws Exception{
					return (Tipoestado) mDAO.findById(Tipoestado.class, id_Ep);
				}
//insertar los serviciosvirtuales
		public void insertarserviciovirtual(int cedula, String nombres, String apellidos, String tema,String correo) throws Exception{
			Serviciosvirtregi svt = new Serviciosvirtregi();
		    svt.setCedula(cedula);
		    svt.setNombres(nombres);
		    svt.setApellidos(apellidos);
			svt.setCorreo(correo);
			svt.setTema(tema);
			//svt.setTipoestado(tipoesta);
			svt.setTipoestado(this.EstadoByID(1));
			svt.setTiposervicio(tiposerv);
			mDAO.insertar(svt);
		}
	
//insertar tipo de estado
		public void insertarTipoEstado(String nombreestado) throws Exception{
			Tipoestado te = new Tipoestado();
			te.setNombreestado(nombreestado);
			mDAO.insertar(te);
		}
	
//insertar tipo de servicio
	public void insertarTipoServicio(String nombreservicio) throws Exception{
		Tiposervicio ts = new Tiposervicio();
		ts.setNombreServicio(nombreservicio);
		mDAO.insertar(ts);
	}

	//editar los serviciosvirtuales
	public void editarserviciovirtual(Integer id_Srv,Integer cedula, String nombres, String apellidos, String tema,String correo,Integer id_Estado, Integer id_serv){
		try{
		Serviciosvirtregi svt = this.ServicioVirtualByID(id_Srv);
	    svt.setCedula(cedula);
	    svt.setNombres(nombres);
	    svt.setApellidos(apellidos);
		svt.setCorreo(correo);
		svt.setTema(tema);
		//svt.setTipoestado(tipoesta);
		//svt.setTiposervicio(tiposerv);
		svt.setTiposervicio(this.findServicioTipoByID(1));
		svt.setTipoestado(this.asignarTipoest(id_Estado));
		mDAO.actualizar(svt);
		} catch (Exception e) {
			System.out.println("Error_mod_recurso");
			e.printStackTrace();
		}
	}
		
	//editar los tipos de estado
	public void editartipoestado(int idEstado, String nombreestado)
	{
		try
		{
			Tipoestado tes = this.EstadoByID(idEstado);
			tes.setNombreestado(nombreestado);
			mDAO.actualizar(tes);
		} catch (Exception e) {
			System.out.println("Error_mod_recurso");
			e.printStackTrace();
			}		
	}
	
	//editar los tipos de servicio
	public void editartiposervicio(int id_Tp, String nombreServicio)
	{
		try
		{
			Tiposervicio ts = this.findServicioTipoByID(id_Tp);
			ts.setNombreServicio(nombreServicio);
			mDAO.actualizar(ts);
		} catch (Exception e) {
			System.out.println("Error_mod_recurso");
			e.printStackTrace();
			}				
	}
	// listar todos los registros 
	@SuppressWarnings("unchecked")
	public List<ManagerRecursosVirtuales> findAllRecurso(){
		return mDAO.findAll(ServiciosVirtualesBean.class);
	}
	
	 //desactivar y activar Recurso
		public String cambioDisServicio(Integer id) throws Exception{
			List<Tipoestado> lista= findAllTipoEstado();
			int p=0;
			String h="";
			for (Tipoestado ta: lista){
				if (ta.getIdEstado().equals(id)){
					p=1;
				}
			}
				Serviciosvirtregi ser = ServicioVirtualByID(id);
 			Tipoestado est = new Tipoestado();
 			
			if(ser.getTipoestado().getNombreestado().equals("Pendiente")){
				est.setIdEstado(2);
				est.setNombreestado("Aprobado");				
				ser.setTipoestado(est);				
				h="Estado del registro modificado";
 			}
			else if(ser.getTipoestado().getNombreestado().equals("Aprobado")){
				est.setIdEstado(1);
				est.setNombreestado("Pendiente");				
				ser.setTipoestado(est);				
				h="Estado del recurso modificado";
 			}
			mDAO.actualizar(ser);
			return h;
			}
	
}

