package innopolis.manager;

import java.sql.Time;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.ArrayList;

import innopolis.entidades.*;
import innopolis.model.generic.Mensaje;

public class ManagerReservas {

	private ManagerDAO mDAO;

	// Solicitud Temporal
	private Solicicabecera soliTemp;

	// Almacenar tipos y estados
	private static Recursotipo rt;
	private static Recursodisponible rd;
	private static Recurso r;
	// private static Colorrec ce;
	private static Colorsala cs;

	String h = "";

	private ManagerCarga mc;

	public ManagerReservas() {
		mDAO = new ManagerDAO();
		mc = new ManagerCarga();
	}

	// ------RECURSOS-------

	// listar todos los recursos
	@SuppressWarnings("unchecked")
	public List<Recurso> findAllRecurso() {
		return mDAO.findAll(Recurso.class);
	}

	// listar todos los recursos
	@SuppressWarnings("unchecked")
	public List<Recursosactivo> findAllRecursoActivo() {
		return mDAO.findAll(Recursosactivo.class);
	}

	// listar todos los RecursosDisponibles
	@SuppressWarnings("unchecked")
	public List<Recursodisponible> findAllRecursoDisponibles() {
		return mDAO.findAll(Recursodisponible.class);
	}

	// buscar recurso por ID
	public Recurso findRecursoByID(Integer id_recurso) throws Exception {
		return (Recurso) mDAO.findById(Recurso.class, id_recurso);
	}

	// buscar RecursoDisponible por ID
	public Recursodisponible findRecursoDisponibleByID(Integer id_rec_disponible)
			throws Exception {
		return (Recursodisponible) mDAO.findById(Recursodisponible.class,
				id_rec_disponible);
	}

	// insertar los recursos
	public void insertarRecurso(Integer capacidad, String descripcion,
			String lugar, String nombre, String imagen/* ,Integer stock */)
			throws Exception {
		Recurso r = new Recurso();
		r.setCapacidad(capacidad);
		r.setDescripcion(descripcion);
		r.setLugar(lugar);
		r.setNombre(nombre);
		r.setImagen(imagen);
		// r.setStock(stock);
		// r.setRecursotipo(rt);
		r.setRecursodisponible(this.findRecursoDisponibleByID(1));
		mDAO.insertar(r);
	}

	// editar los recursos
	public void editarRecurso(Integer idRecurso, Integer capacidad,
			String descripcion, String lugar, String nombre, String imagen/*
																		 * ,
																		 * Integer
																		 * stock
																		 */) {
		try {
			Recurso r = this.findRecursoByID(idRecurso);
			r.setCapacidad(capacidad);
			r.setDescripcion(descripcion);
			r.setLugar(lugar);
			r.setNombre(nombre);
			r.setImagen(imagen);
			// r.setStock(stock);
			// rt=findRecursoTipoByID(ret);
			// r.setRecursotipo(rt);
			mDAO.actualizar(r);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// metodo para asignar el RecursoTipo al Recurso
	public Recursotipo asignarRecursoTipo(Integer idRecTipo) {
		try {
			rt = findRecursoTipoByID(idRecTipo);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return rt;
	}

	// metodo para asignar el RecursoDisponible al Recurso
	public Recursodisponible asignarRecursoDisponible(Integer idRecDisponible) {
		try {
			rd = findRecursoDisponibleByID(idRecDisponible);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return rd;
	}

	// desactivar y activar Recurso
	public String cambioDisRecurso(Integer id) throws Exception {
		List<Recursosactivo> lista = findAllRecursosSolicitados();
		int p = 0;
		String h = "";
		for (Recursosactivo ra : lista) {
			if (ra.getIdRecurso().equals(id)) {
				p = 1;
			}
		}
		Recurso recurso = findRecursoByID(id);
		Recursodisponible t = new Recursodisponible();
		if (recurso.getRecursodisponible().getDisponible().equals("Activado")
				&& p == 0) {
			t.setIdRecdisponible(2);
			t.setDisponible("Desactivado");
			recurso.setRecursodisponible(t);
			h = "Estado del recurso modificado";
		} else if (recurso.getRecursodisponible().getDisponible()
				.equals("Activado")
				&& p == 1) {
			h = "Recurso ocupado no puede ser modificado";
		} else if (recurso.getRecursodisponible().getDisponible()
				.equals("Desactivado")) {
			t.setIdRecdisponible(1);
			t.setDisponible("Activado");
			recurso.setRecursodisponible(t);
			h = "Estado del recurso modificado";
		}
		mDAO.actualizar(recurso);
		return h;
	}

	// ------SOLICITUDES-------EMPRENDEDOR------------------------------------------------------------------//
	// CONTADOR TABLA AUXILIAR
	public int getContadorSolicitud() throws Exception {
		int contSolicitud = 0;
		Contadore cont = null;
		try {
			cont = (Contadore) mDAO.findById(Contadore.class, 1);
		} catch (Exception e) {
			Mensaje.crearMensajeWARN("Revise el parametro 'contador solicitud': "
					+ e.getMessage());
			e.printStackTrace();
		}
		contSolicitud = cont.getValor();
		return contSolicitud;
	}

	public void actualizarContadorSolicitud(int valor) throws Exception {
		Contadore cont = null;
		try {
			cont = (Contadore) mDAO.findById(Contadore.class, 1);
			cont.setValor(valor);
			mDAO.actualizar(cont);
		} catch (Exception e) {
			Mensaje.crearMensajeWARN("Revise el parametro 'contador solicitud': "
					+ e.getMessage());
			e.printStackTrace();
		}
	}

	// SolicitudEstado
	@SuppressWarnings("unchecked")
	public List<Soliciestado> findAllSolicitudEstado() {
		return mDAO.findAll(Soliciestado.class);
	}

	public Soliciestado findSolicitudEstadoByID(Integer pID) throws Exception {
		return (Soliciestado) mDAO.findById(Soliciestado.class, pID);
	}

	public void cambioSMS(Integer id_soli) throws Exception {
		try {
			Solicicabecera svt = this.findSolicitudCabeceraById(id_soli);
			if (svt.getSms().equals("notificada")) {
				svt.setSms("no notificado");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// Temporales
	public Solicicabecera crearSolicitudTmp(String direccion, String actividad,
			String objetivo, String justificacion, Date fecha, Integer id_usr)
			throws Exception {
		soliTemp = new Solicicabecera();
		soliTemp.setActividad(actividad);
		soliTemp.setDireccion(direccion);
		soliTemp.setObjetivo(objetivo);
		soliTemp.setJustificacion(justificacion);
		soliTemp.setSms("sin notificar");
		soliTemp.setFecha(fecha);
		soliTemp.setIdusr(id_usr);
		soliTemp.setSoliciestado(findSolicitudEstadoByID(1));
		soliTemp.setSolicidetalles(new ArrayList<Solicidetalle>());
		return soliTemp;
	}

	public void agregarSolicitudDetalleTmp(Integer id_recurso,
			Integer cantidad, Timestamp horaFin, Timestamp horaInicio)
			throws Exception {
		Solicidetalle det;
		r = this.findRecursoByID(id_recurso);
		if (soliTemp == null)
			Mensaje.crearMensajeWARN("Error primero debe crear y guardar una solicitud en el formulario de datos");
		if (esRecursoAnadido(id_recurso, soliTemp, horaInicio, horaFin))
			Mensaje.crearMensajeWARN("El recurso ya se encuentra agregado dentro del horario");
		det = new Solicidetalle();
		det.setRecurso(r);
		det.setCapacidad(cantidad);
		det.setHoraInicio(horaInicio);
		det.setHoraFin(horaFin);
		soliTemp.getSolicidetalles().add(det);
	}

	public boolean controlarcantidadmanager(Integer id_recurso,
			Integer cantidad, Timestamp horaFin, Timestamp horaInicio)
			throws Exception {
		r = this.findRecursoByID(id_recurso);
		int cantidadrecurso = this.valorrestarectrusoactivo(r.getIdRecurso(),
				horaInicio, horaFin);
		System.out.println(cantidad + " entra al agregar cantidad");
		System.out.println(cantidadrecurso
				+ " entra al agregar cantidad recurso");
		if (cantidad > cantidadrecurso)
			return false;
		else
			return true;
	}

	// Carga de recursos disponibles verificando si esta ocupado
	public Integer valorrestarectrusoactivo(Integer id_recurso,
			Timestamp hora_inicio, Timestamp hora_fin) {
		Recurso resultados;
		int contador = 0;
		int sumatoria = 0;
		try {
			resultados = this.findRecursoByID(id_recurso);
			List<Recursosactivo> listadoRecursoactivo = this
					.findAllRecursoOcupadoByFecha(hora_fin);
			contador = resultados.getCapacidad();
			for (Recursosactivo c : listadoRecursoactivo) {
				if (resultados.getIdRecurso().equals(c.getIdRecurso())) {
					sumatoria += c.getCantidad();
					System.out.println(c.getCantidad());
				}
			}
			contador = contador - sumatoria;
			System.out.println("sumatoria: " + sumatoria);
			System.out.println("contador: " + contador);
			if (contador == 0) {
				System.out.println("no agrego este:" + resultados.getNombre());
			}
			if (contador < resultados.getCapacidad()) {
				System.out.println("si se agrega si es menor");
			} else if (listadoRecursoactivo.isEmpty()) {
				System.out.println("si se agrega si ta vacio menor");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return contador;
	}

	public void agregarSolicitudDetalleTmplista(Integer id_recurso,
			Integer cantidad, Timestamp horaFin, Timestamp horaInicio)
			throws Exception {
		Solicidetalle det;
		Recurso rec;
		if (soliTemp == null)
			Mensaje.crearMensajeWARN("Error primero debe crear y guardar una solicitud en el formulario de datos");
		if (id_recurso == null || id_recurso == -1)
			Mensaje.crearMensajeWARN("Error debe especificar el recurso");
		rec = this.findRecursoByID(id_recurso);
		if (esRecursoAnadido(id_recurso, soliTemp, horaInicio, horaFin))
			Mensaje.crearMensajeWARN("El recurso ya se encuentra agregado dentro del horario");
		det = new Solicidetalle();
		det.setRecurso(rec);
		det.setCapacidad(cantidad);
		det.setHoraInicio(horaInicio);
		det.setHoraFin(horaFin);
		soliTemp.getSolicidetalles().add(det);
	}

	public void quitarDetalleSolicitudTem(Solicidetalle sd) {
		soliTemp.removeSolicidetalle(sd);
	}

	// Guardar Solicitud Temporal
	public int guardarSolicitudTemporal(Solicicabecera soliTmp)
			throws Exception {
		if (soliTmp == null)
			Mensaje.crearMensajeWARN("Debe crear una solicitud primero");
		int contSolicitud = this.getContadorSolicitud();
		contSolicitud++;
		soliTmp.setIdSolcab(contSolicitud);
		ArrayList<Recursosactivo> listRecursos = new ArrayList<Recursosactivo>();
		for (Solicidetalle det : soliTmp.getSolicidetalles()) {
			det.setSolicicabecera(soliTmp);
			Recursosactivo recAct = new Recursosactivo();
			recAct.setIdSolicitud(contSolicitud);
			recAct.setFecha(det.getFechadet());
			recAct.setHoraInicio(det.getHoraInicio());
			recAct.setHoraFin(det.getHoraFin());
			recAct.setIdRecurso(det.getRecurso().getIdRecurso());
			recAct.setCantidad(det.getCapacidad());
			listRecursos.add(recAct);
		}
		mDAO.insertar(soliTmp);
		for (Recursosactivo recursosactivo : listRecursos) {
			mDAO.insertar(recursosactivo);
		}
		actualizarContadorSolicitud(contSolicitud);
		soliTmp = null;
		return contSolicitud;
	}

	// Guardar Solicitud Temporal
	public void guardarSolicitudTemporalsinev(Solicicabecera soliTmp)
			throws Exception {
		if (soliTmp == null)
			Mensaje.crearMensajeWARN("Debe crear una solicitud primero");
		if (soliTmp.getSolicidetalles() == null
				|| soliTmp.getSolicidetalles().size() == 0)
			Mensaje.crearMensajeWARN("Debe ingresar los recursos en la solicitud");
		int contSolicitud;
		contSolicitud = this.getContadorSolicitud();
		contSolicitud++;
		soliTmp.setIdSolcab(contSolicitud);
		ArrayList<Recursosactivo> listRecursos = new ArrayList<Recursosactivo>();
		for (Solicidetalle det : soliTmp.getSolicidetalles()) {
			det.setSolicicabecera(soliTmp);
			Recursosactivo recAct = new Recursosactivo();
			recAct.setIdSolicitud(contSolicitud);
			recAct.setHoraInicio(det.getHoraInicio());
			recAct.setHoraFin(det.getHoraFin());
			recAct.setIdRecurso(det.getRecurso().getIdRecurso());
			recAct.setCantidad(det.getCapacidad());
			listRecursos.add(recAct);
		}
		mDAO.insertar(soliTmp);
		for (Recursosactivo recursosactivo : listRecursos) {
			mDAO.insertar(recursosactivo);
		}
		actualizarContadorSolicitud(contSolicitud);
		soliTmp = null;
	}

	// Recurso ya Añadido
	public boolean esRecursoAnadido(Integer id_recurso,
			Solicicabecera solicitud, Timestamp horaInicio, Timestamp horaFin) {
		List<Solicidetalle> listado = solicitud.getSolicidetalles();
		boolean resp = false;
		for (Solicidetalle solicidetalle : listado) {
			if (solicidetalle.getRecurso().getIdRecurso().intValue() == id_recurso
					.intValue()
					&& (Validacion.horaEntreDos(solicidetalle.getHoraInicio(),
							horaInicio, horaFin) || Validacion.horaEntreDos(
							solicidetalle.getHoraFin(), horaInicio, horaFin))) {
				resp = true;
			}
		}
		return resp;
	}

	// Tabla RECURSOACTIVO para reservaciones realizadas
	@SuppressWarnings("unchecked")
	public List<Recursosactivo> findAllRecursosSolicitados() {
		return mDAO.findAll(Recursosactivo.class);
	}

	public void insertarRecursoSolicitado(Integer idSolicitud,
			Timestamp hora_inicio, Timestamp hora_fin, Integer id_recurso,
			Integer cantidad) throws Exception {
		Recursosactivo recact = new Recursosactivo();
		recact.setIdSolicitud(idSolicitud);
		recact.setHoraInicio(hora_inicio);
		recact.setHoraFin(hora_fin);
		recact.setIdRecurso(id_recurso);
		recact.setCantidad(cantidad);
		mDAO.insertar(recact);
	}

	public void editarRecursoSolicitado(Integer idSolicitud,
			Timestamp hora_inicio, Timestamp hora_fin) throws Exception {
		List<Recursosactivo> recactivo = this
				.recursoActivoByIdSolicitud(idSolicitud);
		for (Recursosactivo reca : recactivo) {
			reca.setHoraInicio(hora_inicio);
			reca.setHoraFin(hora_fin);
			mDAO.actualizar(reca);
		}
	}

	@SuppressWarnings("unchecked")
	public List<Recursosactivo> recursoActivoByIdSolicitud(Integer id)
			throws Exception {
		return (List<Recursosactivo>) mDAO.findWhere(Recursosactivo.class,
				" o.idSolicitud = " + id + " ", "o.fecha ");
	}

	public void eliminarRecursoSolicitado(Integer id_tabla) throws Exception {
		mDAO.eliminar(Recursosactivo.class, id_tabla);
	}

	// Busca recurso por ID SOL Y REC
	public Recursosactivo findByIdSoliciYRecurso(Integer idSolicitud,
			Integer idRecurso) {
		Recursosactivo resp = null;
		List<Recursosactivo> list = findAllRecursosSolicitados();
		for (Recursosactivo recursosactivo : list) {
			if (recursosactivo.getIdSolicitud() == idSolicitud
					&& recursosactivo.getIdRecurso() == idRecurso) {
				resp = recursosactivo;
			}
		}
		return resp;
	}

	// Agregar Quitar LISTADO DE RECURSOS a Tabla Auxiliar
	public void agregarListaRecursoActivo(List<Solicidetalle> listado,
			Integer id_solicitud) throws Exception {
		for (Solicidetalle recurso : listado) {
			insertarRecursoSolicitado(id_solicitud, recurso.getHoraInicio(),
					recurso.getHoraFin(), recurso.getRecurso().getIdRecurso(),
					recurso.getCapacidad());
		}
	}

	public void quitarListaRecursoActivo(List<Recurso> listado,
			Integer id_solicitud) throws Exception {
		for (Recurso recurso : listado) {
			Recursosactivo recAct = findByIdSoliciYRecurso(id_solicitud,
					recurso.getIdRecurso());
			if (recAct != null) {
				eliminarRecursoSolicitado(recAct.getIdRecact());
			}
		}
	}

	public void quitarRecursoActivoBySol(Integer id_solicitud) throws Exception {
		List<Recursosactivo> listado = findAllRecursosSolicitados();
		for (Recursosactivo recursosactivo : listado) {
			if (recursosactivo.getIdSolicitud() == id_solicitud) {
				eliminarRecursoSolicitado(recursosactivo.getIdRecact());
			}
		}
	}

	public void modificarSolCabEvento(Integer id_solicitud, String direccion,
			String objetivo, String justificacion) throws Exception {
		Solicicabecera sol = findSolicitudByID(id_solicitud);
		sol.setDireccion(direccion);
		sol.setObjetivo(objetivo);
		sol.setJustificacion(justificacion);
		mDAO.actualizar(sol);
	}

	// RECURSOS LIBRES --> REVISAR
	// HORA FIN NECESITO PARA CALCULAR LA PROXIMA HORA INICIO
	// RecursosXFecha
	// Devuelve todos recursos que se encuentran ocupados en esa fecha
	public ArrayList<Recursosactivo> findAllRecursoOcupadoByFecha(
			Date fecha_seleccionadaini) {
		ArrayList<Recursosactivo> resultado = new ArrayList<Recursosactivo>();
		List<Recursosactivo> listado = this.findAllRecursosSolicitados();
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

		for (Recursosactivo recursosactivo : listado) {
			System.out.println(dateFormat.format(
					recursosactivo.getHoraFin().getTime()).toString()
					+ "aca1");
			System.out.println(dateFormat.format(fecha_seleccionadaini)
					.toString() + "aca2");
			if (dateFormat
					.format(recursosactivo.getHoraFin().getTime())
					.toString()
					.equals(dateFormat.format(fecha_seleccionadaini.getTime())
							.toString())) {
				resultado.add(recursosactivo);
			}
		}
		System.out.println("OcupadosByFecha " + resultado.size());
		return resultado;
	}

	// Carga de recursos disponibles verificando si esta ocupado
	public List<Recurso> findAllRecursosDisponibles(Timestamp hora_inicio,
			Timestamp hora_fin, Time horainicio, Time horafin) {
		List<Recurso> listado = this.findAllRecurso();
		List<Recurso> resultados = this.findAllRecurso();
		List<Recursosactivo> listadoRecursoactivo = this
				.findAllRecursoOcupadoByHorario(hora_inicio, hora_fin,
						horainicio, horafin);
		int sumatoria = 0;
		for (Recurso recurso : listado) {
			int contador = recurso.getCapacidad();
			if (this.findRecursosSolicitadosLibreByHorario(
					recurso.getIdRecurso(), hora_inicio, hora_fin, horainicio,
					horafin/* recurso.getCapacidad(), */)
					&& this.esRecursoDesactivado(recurso.getIdRecurso())) {
				for (Recursosactivo c : listadoRecursoactivo) {
					if (recurso.getIdRecurso().equals(c.getIdRecurso())) {
						sumatoria += c.getCantidad();
						System.out.println(c.getCantidad());
					}
				}
				contador = contador - sumatoria;
				System.out.println("sumatoria: " + sumatoria);
				System.out.println("contador: " + contador);

				if (contador == 0) {
					System.out.println("no agrego este:" + recurso.getNombre());
					resultados.remove(recurso);
					System.out.println("quita " + recurso.getNombre());
				}
				if (contador < recurso.getCapacidad()) {
					System.out.println("si se agrega si es menor");
				}
			} else if (listadoRecursoactivo.isEmpty()) {
				System.out.println("si se agrega si ta vacio menor");
			}
		}
		return resultados;
	}

	// Devuelve contador de un recursos disponibles verificando si esta ocupado
	public Integer findContadorRecurso(Timestamp hora_inicio,
			Timestamp hora_fin, Integer id_recurso) {
		int contador = 0;
		System.out.println("entra al contador");
		try {
			Recurso rec = this.findRecursoByID(id_recurso);
			contador = rec.getCapacidad();
			List<Recursosactivo> listadoRecursoactivo = this
					.findAllRecursoOcupadoByFecha(hora_fin);
			int sumatoria = 0;
			for (Recursosactivo c : listadoRecursoactivo) {
				if (rec.getIdRecurso().equals(c.getIdRecurso())) {
					sumatoria += c.getCantidad();
				}
			}
			contador = contador - sumatoria;
			System.out.println("sumatoria: " + sumatoria);
			System.out.println("contador: " + contador);
		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println(contador + "total rec");
		return contador;
	}

	// Carga de recursos disponibles verificando si la cantidad es cero
	public List<Recurso> findAllRecursosDisponibleseliminar(Integer idrec) {
		List<Recurso> listado = this.findAllRecurso();
		List<Recurso> resultados = this.findAllRecurso();
		for (Recurso recurso : listado) {
			resultados.remove(recurso);
			System.out.println("quita " + recurso.getNombre());
		}
		return resultados;
	}

	// Devuelve un valor booleano para conocer si se encuentra ocupado o no el
	// recurso
	public boolean findRecursosSolicitadosLibreByHorario(Integer id_recurso,
			Timestamp hora_inicio, Timestamp hora_fin, Time horainicio,
			Time horafin) {
		List<Recursosactivo> listado = this.findAllRecursoOcupadoByHorario(
				hora_inicio, hora_fin, horainicio, horafin);
		System.out.println("listado " + listado.size());
		for (Recursosactivo recursosactivo : listado) {
			if (recursosactivo.getIdRecurso().intValue() == id_recurso
					.intValue()) {
				return true;
			}
		}
		return false;
	}

	// RecursosXHora
	// Devuelve los recursos que esten ocupados de una fecha en un horario
	public ArrayList<Recursosactivo> findAllRecursoOcupadoByHorario(
			Timestamp fecha_inicio, Timestamp fecha_fin, Time horainicio,
			Time horafin) {
		ArrayList<Recursosactivo> resultado = new ArrayList<Recursosactivo>();
		List<Recursosactivo> listado = this
				.findAllRecursoOcupadoByFecha(fecha_fin);
		for (Recursosactivo recursosactivo : listado) {
			System.out.println("entra");
			System.out.println(horainicio + " y "
					+ Validacion.fechaAtiempo(recursosactivo.getHoraInicio()));
			System.out.println(horafin + " y "
					+ Validacion.fechaAtiempo(recursosactivo.getHoraFin()));
			if ((horainicio.before(Validacion.fechaAtiempo(recursosactivo
					.getHoraInicio())) && horainicio.after(Validacion
					.fechaAtiempo(recursosactivo.getHoraFin())))
					|| (horafin.before(Validacion.fechaAtiempo(recursosactivo
							.getHoraInicio())) && horafin.after(Validacion
							.fechaAtiempo(recursosactivo.getHoraFin())))) {
				System.out.println("entra aca1");
				System.out.println(recursosactivo.getIdSolicitud());
				resultado.add(recursosactivo);
			}
		}
		System.out.println("OcupadosByHorario " + resultado.size());
		return resultado;
	}

	// Recursos Activados
	public boolean esRecursoDesactivado(Integer id_recurso) {
		Recurso rec = new Recurso();
		try {
			rec = this.findRecursoByID(id_recurso);
		} catch (Exception e) {
			e.printStackTrace();
		}
		if (rec.getRecursodisponible().getIdRecdisponible().equals(1)) {
			return false;
		} else {
			return true;
		}
	}

	// metodo para asignar el Recurso a la Solicitud
	public Recurso asignarRecurso(Integer idRecurso) {
		try {
			System.out.println(idRecurso);
			r = findRecursoByID(idRecurso);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return r;
	}

	// -----------------------------APROBADOR------------------------------------------------------//
	// Listar solicitudes
	@SuppressWarnings("unchecked")
	public List<Solicicabecera> findAllSolicitudCabecera() {
		return mDAO.findAll(Solicicabecera.class);
	}

	// Listar solicitudes
	@SuppressWarnings("unchecked")
	public List<Solicicabecera> findAllSolicitudCabeceraOrdenada() {
		return mDAO.findWhere(Solicicabecera.class, " 1=1 ", "o.fecha desc");
	}

	public Solicicabecera findSolicitudCabeceraById(Integer id)
			throws Exception {
		return (Solicicabecera) mDAO.findById(Solicicabecera.class, id);
	}

	@SuppressWarnings("unchecked")
	public List<Solicicabecera> findSolicitudCabeceraByIdEvento(Integer id)
			throws Exception {
		return (List<Solicicabecera>) mDAO.findWhere(Solicicabecera.class,
				" o.idSolcab = " + id + " ", " o.fecha ");
	}

	// Cambio de estados
	// Pide Id Estado, Busca estado y lo cambia dentro de la solicitud
	// seleccionada
	public void cambiarEstadoSolicitud(Integer id_solicitud, Soliciestado estado)
			throws Exception {
		Solicicabecera solicitud = this.findSolicitudCabeceraById(id_solicitud);
		solicitud.setSoliciestado(estado);
		mDAO.actualizar(solicitud);
		ArrayList<Recurso> listRec = new ArrayList<Recurso>();
		for (Solicidetalle det : solicitud.getSolicidetalles()) {
			listRec.add(det.getRecurso());
		}
		List<Solicidetalle> listRecDet = solicitud.getSolicidetalles();
		if (estado.equals("negado")) {
			quitarListaRecursoActivo(listRec, id_solicitud);
		} else if (solicitud.getSoliciestado().getEstado().equals("negado")
				&& estado.equals("aprobado")) {
			agregarListaRecursoActivo(listRecDet, id_solicitud);
		}

	}

	// SolicitudDetalle por id_solicitud
	@SuppressWarnings("unchecked")
	public List<Solicidetalle> findAllDetallesSolicitud() {
		return mDAO.findAll(Solicidetalle.class);
	}

	@SuppressWarnings("unchecked")
	public List<Solicidetalle> findSolicitudDetalleByCabeceraId(Integer id)
			throws Exception {
		List<Solicidetalle> resul = mDAO.findWhere(Solicidetalle.class,
				" o.solicicabecera.idSolcab = " + id + " ", " o.horaInicio ");
		if (resul.isEmpty()) {
			return null;
		}
		return resul;
	}

	@SuppressWarnings("unchecked")
	public List<Evento> findEventoByCabeceraId(Integer id) throws Exception {
		return mDAO.findWhere(Evento.class, " o.solicicabecera.idSolcab = "
				+ id + " ", " o.fechaInicio ");
	}

	public Solicidetalle findSolicitudDetalleById(Integer id) throws Exception {
		return (Solicidetalle) mDAO.findById(Solicidetalle.class, id);
	}

	public void eliminarSoliciDetalleByID(Integer id_detalle) throws Exception {
		mDAO.eliminar(Solicidetalle.class, id_detalle);
	}

	public void insertarSoliciDatalle(Solicidetalle detalle) throws Exception {
		mDAO.insertar(detalle);
	}

	@SuppressWarnings("unchecked")
	public List<Solicidetalle> findAllSolicituddetalleBycabeceraId(
			Integer idCabecera) throws Exception {
		return (List<Solicidetalle>) mDAO.findWhere(Recursosactivo.class,
				" o.idSolicitud = " + idCabecera + " ", "o.horaInicio");
	}

	public ArrayList<Solicidetalle> findDetallesSolicitud(Integer id_solicitud) {
		List<Solicidetalle> todo = findAllDetallesSolicitud();
		ArrayList<Solicidetalle> resp = new ArrayList<Solicidetalle>();
		for (Solicidetalle solicidetalle : todo) {
			if (solicidetalle.getSolicicabecera().getIdSolcab() == id_solicitud) {
				resp.add(solicidetalle);
			}
		}
		return resp;
	}

	public void quitarSolDetBySolicitud(Integer id_solicitud) throws Exception {
		List<Solicidetalle> todos = findAllDetallesSolicitud();
		for (Solicidetalle solicidetalle : todos) {
			if (solicidetalle.getSolicicabecera().getIdSolcab() == id_solicitud) {
				eliminarSoliciDetalleByID(solicidetalle.getIdSoldet());
			}
		}
	}

	// Modificacion de Solicitudes
	// SOLO TOMO EN CUENTA AGREGAR Y QUITAR RECURSOS
	public void editarDetallesSolicitud(Integer id_solicitud,
			ArrayList<Solicidetalle> agregados,
			ArrayList<Solicidetalle> eliminados) throws Exception {
		ArrayList<Solicidetalle> actual = findDetallesSolicitud(id_solicitud);
		for (Solicidetalle solicidetalle : actual) {
			for (Solicidetalle eliminado : eliminados) {
				if (solicidetalle.getIdSoldet().equals(eliminado.getIdSoldet())) {
					eliminarSoliciDetalleByID(solicidetalle.getIdSoldet());
				}
			}
		}
		for (Solicidetalle detalle : agregados) {
			insertarRecursoSolicitado(id_solicitud, detalle.getHoraInicio(),
					detalle.getHoraFin(), detalle.getRecurso().getIdRecurso(),
					detalle.getCapacidad());
		}
	}

	public void editarDetalleSolicitud(Integer id_solicitud,
			Timestamp horaInicio, Timestamp horaFin) throws Exception {
		Solicidetalle soldet = findSolicitudDetalleById(id_solicitud);
		soldet.setHoraInicio(horaInicio);
		soldet.setHoraFin(horaFin);
		mDAO.actualizar(soldet);
	}

	public void editarDetallesSolicitudRecAct(Integer id_solicitud,
			Solicidetalle detalle) throws Exception {
		insertarRecursoSolicitado(id_solicitud, detalle.getHoraInicio(),
				detalle.getHoraFin(), detalle.getRecurso().getIdRecurso(),
				detalle.getCapacidad());
	}

	// MODIFICAR ESTADO SOLICITUD
	public void aprobarSolicitudMOD(Integer id_solicitud) throws Exception {
		Solicicabecera sol = findSolicitudCabeceraById(id_solicitud);
		sol.setSoliciestado(findSolicitudEstadoByID(3));
		mDAO.actualizar(sol);
	}

	// NOTIFICAR SOLICITUD
	public void notificarSolicitud(Integer id_solicitud) throws Exception {
		Solicicabecera sol = findSolicitudCabeceraById(id_solicitud);
		sol.setSms("notificada");
		mDAO.actualizar(sol);
	}

	// buscar servicio por ID
	public Solicicabecera findSolicitudByID(int id_Srv) throws Exception {
		return (Solicicabecera) mDAO.findById(Solicicabecera.class, id_Srv);
	}

	// metodo para enviar el estado del mensaje si se envio
	public String cambioSMSenvio(Integer id_soli) throws Exception {
		try {
			h = "";
			Solicicabecera soli = this.findSolicitudByID(id_soli);
			if (soli.getSms().equals("no notificado")) {
				soli.setSms("notificada");
				h = "El usuario a sido notificado por correo";
			} else if (soli.getSms().equals("notificada")) {
				h = "Ya se ha enviado al correo la notificación";
			}
		} catch (Exception e) {
			Mensaje.crearMensajeWARN("No se envió el mensaje");
		}
		return h;
	}

	@SuppressWarnings("unchecked")
	public List<Usuario> findAllUsuarios() {
		return mDAO.findAll(Usuario.class);
	}

	// /////////////////////Tipos Recursos//////////////////////////////////////
	// listar todos los TipoRecursos
	@SuppressWarnings("unchecked")
	public List<Recursotipo> findAllTipoRecurso() {
		return mDAO.findAll(Recursotipo.class);
	}

	// buscar RecursoTipo por ID
	public Recursotipo findRecursoTipoByID(Integer id_rec_tipo)
			throws Exception {
		return (Recursotipo) mDAO.findById(Recursotipo.class, id_rec_tipo);
	}

	// insertar tipo de recurso
	public void insertarTipoRecurso(String tipo, String descripcion,
			String imagen) throws Exception {
		Recursotipo rt = new Recursotipo();
		rt.setTipo(tipo);
		rt.setDescripcion(descripcion);
		rt.setImagen(imagen);
		mDAO.insertar(rt);
	}

	// Modificar
	public void editarRecursoTipo(Integer id_recursotipo, String tipo,
			String descripcion, String imagen/* ,Integer idcolor */)
			throws Exception {
		Recursotipo rec = findRecursoTipoByID(id_recursotipo);
		rec.setTipo(tipo);
		rec.setDescripcion(descripcion);
		rec.setImagen(imagen);
		mDAO.actualizar(rec);
	}

	//
	public Colorrec findcolorxid(Integer a) {
		Colorrec p = new Colorrec();
		try {
			p = (Colorrec) mDAO.findById(Colorrec.class, a);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return p;
	}

	// colores de tiporecurso listar todos los colores
	@SuppressWarnings("unchecked")
	public List<Coloreve> findAllColoreves() {
		return mDAO.findAll(Coloreve.class);
	}

	// buscar color por ID
	public Colorrec ColorByID(Integer id_color) throws Exception {
		return (Colorrec) mDAO.findById(Colorrec.class, id_color);
	}

	// metodo para asignar el color al tipo Evento
	public Recursotipo asignarTipocolor(Integer idcolor) {
		try {
			// ce=this.ColorByID(idcolor);
			System.out.println("si relizo la entrada");
		} catch (Exception e) {
			System.out.println("asignacion de tipo erroneo");
			e.printStackTrace();
		}
		return rt;
	}

	// ////////////////////eliminar recursos activos desde
	public void quitarrecursosactivos() throws Exception {
		java.util.Date actualfecha = new Date();
		Timestamp fecha = new Timestamp(actualfecha.getTime());
		List<Recursosactivo> todos = findAllRecursoActivo();
		for (Recursosactivo recact : todos) {
			if (recact.getHoraFin().before(fecha)) {
				eliminarRecursoActivos(recact.getIdRecact());
			}
		}
	}

	public void eliminarRecursoActivos(Integer idreac) throws Exception {
		mDAO.eliminar(Recursosactivo.class, idreac);
	}

	@SuppressWarnings("unchecked")
	public List<Recursosactivo> findRecursosactivoByRecursoId(
			Integer idDetalle, Integer idRecurso) throws Exception {
		return (List<Recursosactivo>) mDAO.findWhere(Recursosactivo.class,
				" o.idSolicitud = " + idDetalle + " and o.idRecurso = "
						+ idRecurso + " ", "o.horaInicio");
	}

	// /////////////////////Salas//////////////////////////////////////
	// listar todos los TipoRecursos
	@SuppressWarnings("unchecked")
	public List<Sala> findAllSalas() {
		return mDAO.findAll(Sala.class);
	}

	// listar todos los recursos
	@SuppressWarnings("unchecked")
	public List<Salasactiva> findAllSalasActivas() {
		return mDAO.findAll(Salasactiva.class);
	}

	// buscar RecursoTipo por ID
	public Sala findSalaByID(Integer id_sala_tipo) throws Exception {
		return (Sala) mDAO.findById(Sala.class, id_sala_tipo);
	}

	// buscar RecursoDisponible por ID
	public Saladisponible findSalaDisponibleByID(Integer id_sala_disponible)
			throws Exception {
		return (Saladisponible) mDAO.findById(Saladisponible.class,
				id_sala_disponible);
	}

	// insertar tipo de recurso
	public void insertarSala(String tipo, String descripcion, String imagen,
			Integer capacidad) throws Exception {
		Sala rt = new Sala();
		rt.setTipo(tipo);
		rt.setDescripcion(descripcion);
		rt.setImagen(imagen);
		rt.setCapacidad(capacidad);
		rt.setColorsala(cs);
		rt.setSaladisponible(this.findSalaDisponibleByID(1));
		mDAO.insertar(rt);
	}

	// Modificar
	public void editarSala(Integer id_recursotipo, String tipo,
			String descripcion, String imagen, Integer idcolor,
			Integer capacidad) throws Exception {
		Sala rec = findSalaByID(id_recursotipo);
		rec.setTipo(tipo);
		rec.setDescripcion(descripcion);
		rec.setImagen(imagen);
		rec.setCapacidad(capacidad);
		cs = findcolorxidsala(idcolor);
		rec.setColorsala(cs);
		mDAO.actualizar(rec);
	}

	//
	public Colorsala findcolorxidsala(Integer a) {
		Colorsala p = new Colorsala();
		try {
			p = (Colorsala) mDAO.findById(Colorsala.class, a);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return p;
	}

	// colores de sala listar todos los colores
	@SuppressWarnings("unchecked")
	public List<Colorsala> findAllColorsala() {
		return mDAO.findAll(Colorsala.class);
	}

	// buscar color por ID
	public Colorsala ColorsalaByID(Integer id_color) throws Exception {
		return (Colorsala) mDAO.findById(Colorsala.class, id_color);
	}

	// metodo para asignar el color al tipo Evento
	public Recursotipo asignarTipocolorsala(Integer idcolor) {
		try {
			cs = this.ColorsalaByID(idcolor);
			System.out.println("si relizo la entrada");
		} catch (Exception e) {
			System.out.println("asignacion de tipo erroneo");
			e.printStackTrace();
		}
		return rt;
	}

	public List<Sala> findAllSalasDisponibles(Timestamp fecha_inicio,
			Timestamp fecha_fin) {
		List<Sala> resultados = this.findAllSalas();
		try {
			List<DatosSalas> salasActivosFechas = mc.findAllSalasActivos(
					fecha_inicio, fecha_fin);
			if (!salasActivosFechas.isEmpty()) {
				List<Sala> listado = this.findAllSalas();
				for (Sala sala : listado) {
					for (DatosSalas salasActivas : salasActivosFechas) {
						if (sala.getIdSala().equals(salasActivas.getId_sala())) {
							System.out.println("no agrego este:"
									+ sala.getTipo());
							resultados.remove(sala);
							System.out.println("quita " + sala.getTipo());
						}
					}
				}
			} else {
				System.out.println("si se agrega si ta vacio menor");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return resultados;
	}

	// Devuelve un valor booleano para conocer si se encuentra ocupado o no el
	// recurso
	public boolean findSalasSolicitadosLibreByHorario(Integer id_sala,
			Integer capacidad, Timestamp hora_inicio, Timestamp hora_fin,
			Time horainicio, Time horafin) {
		List<Salasactiva> listado = this.findAllSalaOcupadoByHorario(
				hora_inicio, hora_fin, horainicio, horafin);
		for (Salasactiva salasactivo : listado) {
			if (salasactivo.getIdSala().intValue() == id_sala.intValue()) {
				return true;
			}
		}
		return false;
	}

	// SalasXHora
	// Devuelve las salas que esten ocupados de una fecha en un horario
	public ArrayList<Salasactiva> findAllSalaOcupadoByHorario(
			Timestamp fecha_inicio, Timestamp fecha_fin, Time horainicio,
			Time horafin) {
		ArrayList<Salasactiva> resultado = new ArrayList<Salasactiva>();
		List<Salasactiva> listado = this.findAllSalasOcupadoByFecha(fecha_fin);
		for (Salasactiva salasactivo : listado) {
			System.out.println(horainicio + " y "
					+ Validacion.fechaAtiempo(salasactivo.getHoraInicio()));
			System.out.println(horafin + " y "
					+ Validacion.fechaAtiempo(salasactivo.getHoraFin()));
			if ((horainicio.before(Validacion.fechaAtiempo(salasactivo
					.getHoraInicio())) || horainicio.after(Validacion
					.fechaAtiempo(salasactivo.getHoraFin())))
					&& (horafin.before(Validacion.fechaAtiempo(salasactivo
							.getHoraInicio())) || horafin.after(Validacion
							.fechaAtiempo(salasactivo.getHoraFin())))) {
				System.out.println("entra aca1");
				System.out.println(salasactivo.getIdSala());
				resultado.add(salasactivo);
			}
		}
		return resultado;
	}

	// RECURSOS LIBRES --> REVISAR
	// HORA FIN NECESITO PARA CALCULAR LA PROXIMA HORA INICIO
	// RecursosXFecha
	// Devuelve todos recursos que se encuentran ocupados en esa fecha
	public ArrayList<Salasactiva> findAllSalasOcupadoByFecha(
			Date fecha_seleccionada) {
		ArrayList<Salasactiva> resultado = new ArrayList<Salasactiva>();
		List<Salasactiva> listado = this.findAllSalasSolicitados();
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		for (Salasactiva salasactivo : listado) {
			System.out.println(dateFormat.format(salasactivo.getHoraFin())
					.toString() + " aca1");
			System.out.println(dateFormat.format(fecha_seleccionada).toString()
					+ " aca2");
			if (dateFormat
					.format(salasactivo.getHoraFin().getTime())
					.toString()
					.equals(dateFormat.format(fecha_seleccionada.getTime())
							.toString())) {
				resultado.add(salasactivo);
			}
		}
		System.out.println("OcupadosByFecha " + resultado.size());
		return resultado;
	}

	// Tabla RECURSOACTIVO para reservaciones realizadas
	@SuppressWarnings("unchecked")
	public List<Salasactiva> findAllSalasSolicitados() {
		return mDAO.findAll(Salasactiva.class);
	}

	public void quitarsalasactivas() throws Exception {
		java.util.Date actualfecha = new Date();
		Timestamp fecha = new Timestamp(actualfecha.getTime());
		List<Salasactiva> todos = findAllSalasActivas();
		for (Salasactiva recact : todos) {
			System.out.println(fecha);
			System.out.println(recact.getHoraFin());
			if (recact.getHoraFin().before(fecha)) {
				eliminarSalasActivas(recact.getIdSalact());
			}
		}
	}

	public void eliminarSalasActivas(Long idreac) throws Exception {
		mDAO.eliminar(Salasactiva.class, idreac);
	}

	// Recursos Activados
	public boolean esSalaDesactivado(Integer id_sala) {
		Sala rec = new Sala();
		try {
			rec = this.findSalaByID(id_sala);
		} catch (Exception e) {
			e.printStackTrace();
		}
		if (rec.getSaladisponible().getIdsaladisponible().equals(1)) {
			return false;
		} else {
			return true;
		}
	}

	// desactivar y activar Recurso
	public String cambioDisSala(Integer id) throws Exception {
		List<Salasactiva> lista = findAllSalasActivas();
		int p = 0;
		String h = "";
		for (Salasactiva ra : lista) {
			if (ra.getIdSala().equals(id)) {
				p = 1;
			}
		}
		Sala recurso = findSalaByID(id);
		Saladisponible t = new Saladisponible();
		if (recurso.getSaladisponible().getDisponible().equals("Activado")
				&& p == 0) {
			t.setIdsaladisponible(2);
			t.setDisponible("Desactivado");
			recurso.setSaladisponible(t);
			h = "Estado del recurso modificado";
		} else if (recurso.getSaladisponible().getDisponible()
				.equals("Activado")
				&& p == 1) {
			h = "Recurso ocupado no puede ser modificado";
		} else if (recurso.getSaladisponible().getDisponible()
				.equals("Desactivado")) {
			t.setIdsaladisponible(1);
			t.setDisponible("Activado");
			recurso.setSaladisponible(t);
			h = "Estado del recurso modificado";
		}
		mDAO.actualizar(recurso);
		return h;
	}

	// salasActivas
	@SuppressWarnings("unchecked")
	public List<Salasactiva> findSalasActivasByIdEvento(Integer id)
			throws Exception {
		return (List<Salasactiva>) mDAO.findWhere(Salasactiva.class,
				" o.idEvento = " + id + " ", " o.horaInicio ");
	}

	// Modificar
	public void editarSalaActivas(Long id_salasact, Timestamp hora_inicio,
			Timestamp hora_fin) throws Exception {
		Salasactiva salaact = findSalaActivaByID(id_salasact);
		salaact.setHoraInicio(hora_inicio);
		salaact.setHoraFin(hora_fin);
		mDAO.actualizar(salaact);
	}

	// buscar salaActiva por ID
	public Salasactiva findSalaActivaByID(Long id_sala) throws Exception {
		return (Salasactiva) mDAO.findById(Salasactiva.class, id_sala);
	}
}