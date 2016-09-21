package innopolis.manager;

import innopolis.entidades.DatosRecursos;
import innopolis.entidades.DatosSalas;
import innopolis.general.connection.SingletonJDBC;
import innopolis.model.generic.Funciones;

import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import javax.ejb.EJB;

/**
 * Contiene la lógica de negocio para realizar el proceso de reserva de sitios
 * 
 * @author
 * 
 */

public class ManagerCarga {

	private SingletonJDBC conDao;

	@EJB
	private ManagerDAO mDAO;

	public ManagerCarga() {
		conDao = SingletonJDBC.getInstance();
	}

	/**
	 * Devuelve las recursos activos en esa fechas
	 * 
	 * @return Novedades
	 * @throws Exception
	 */
	public List<DatosRecursos> FindAllRecursosActivos(Timestamp hora_inicio,Timestamp hora_fin, Integer id_solicitud) throws Exception {
		DatosRecursos f = null;
		List<DatosRecursos> filterUsers = new ArrayList<DatosRecursos>();
		ResultSet consulta = conDao
				.consultaSQL("select s.id_recact as id_recact, s.id_solicitud as id_solicitud, s.hora_inicio as hora_inicio,"
						+ "s.hora_fin as hora_fin, s.id_recurso as id_recurso, s.cantidad as cantidad  from recursosactivos s "
						+ "where (( s.hora_inicio >= '"+hora_inicio+"' and s.hora_inicio <= '"+hora_fin+"' ) "
						+ "or (( s.hora_fin >= '"+hora_inicio+"' and s.hora_fin <= '"+hora_fin+"' ) or s.hora_fin >= '"+hora_fin+"' ))"
						+ "AND s.id_solicitud not in ("+id_solicitud+")");
		if (consulta != null) {
			while (consulta.next()) {
				f = new DatosRecursos();
				f.setId_recact(Integer.parseInt(consulta.getString("id_recact")));
				f.setId_solicitud(Integer.parseInt(consulta.getString("id_solicitud")));
				f.setHora_inicio(new Timestamp(Funciones.stringToDate(
						consulta.getString("hora_inicio")).getTime()));
				f.setHora_fin(new Timestamp(Funciones.stringToDate(
						consulta.getString("hora_fin")).getTime()));
				f.setId_recurso(Integer.parseInt(consulta.getString("id_recurso")));
				f.setCantidad(Integer.parseInt(consulta.getString("cantidad")));
				filterUsers.add(f);
			}
		}
		return filterUsers;
	}
	
	/**
	 * Devuelve las recursos activos en esa fechas
	 * 
	 * @return Novedades
	 * @throws Exception
	 */
	public List<DatosSalas> findAllSalasActivos(Timestamp hora_inicio,Timestamp hora_fin) throws Exception {
		DatosSalas f = null;
		List<DatosSalas> filterUsers = new ArrayList<DatosSalas>();
		ResultSet consulta = conDao
				.consultaSQL("select s.id_salact as id_salact, s.id_evento as id_evento, s.hora_inicio as hora_inicio, "
						+ "s.hora_fin as hora_fin, s.id_sala as id_sala from salasactivas s "
						+ "where (( s.hora_inicio >= '"+hora_inicio+"' and s.hora_inicio <= '"+hora_fin+"' ) "
						+ " or (( s.hora_fin >= '"+hora_inicio+"' and s.hora_fin <= '"+hora_fin+"' ) or s.hora_fin >= '"+hora_fin+"' ))");
		if (consulta != null) {
			while (consulta.next()) {
				f = new DatosSalas();
				f.setId_salact(Integer.parseInt(consulta.getString("id_salact")));
				f.setId_evento(Integer.parseInt(consulta.getString("id_evento")));
				f.setHora_inicio(new Timestamp(Funciones.stringToDate(
						consulta.getString("hora_inicio")).getTime()));
				f.setHora_fin(new Timestamp(Funciones.stringToDate(
						consulta.getString("hora_fin")).getTime()));
				f.setId_sala(Integer.parseInt(consulta.getString("id_sala")));
				filterUsers.add(f);
			}
		}
		return filterUsers;
	}
}
