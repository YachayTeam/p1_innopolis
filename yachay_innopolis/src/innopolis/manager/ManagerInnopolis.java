package innopolis.manager;

import java.util.List;

import innopolis.entities.*;

public class ManagerInnopolis {

	private ManagerDAO mDAO;

	public ManagerInnopolis() {
		mDAO = new ManagerDAO();
	}

	// Eventos
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

	public void eliminarEvento(Integer id_evento) throws Exception {
		mDAO.eliminar(Evento.class, id_evento);
	}

	public void actualizarEvento(Evento evento) throws Exception {
		mDAO.actualizar(evento);
	}
	
	//tipo de eventos
	@SuppressWarnings("unchecked")
	public List<Tipoevento> findAllTipoEventos(){
		return mDAO.findAll(Tipoevento.class, "o.tipo");
	}
	public Tipoevento findTipoEventoById(Integer id_tipo_evento) throws Exception {
		return (Tipoevento) mDAO.findById(Tipoevento.class, id_tipo_evento);
	}

	public void insertarTipoEvento(Tipoevento r) throws Exception {
		mDAO.insertar(r);
	}

	public void eliminarTipoEvento(Integer id_tipo_evento) throws Exception {
		mDAO.eliminar(Tipoevento.class, id_tipo_evento);
	}

	public void actualizarTipoEvento(Tipoevento tipo_evento) throws Exception {
		mDAO.actualizar(tipo_evento);
	}
	
	//inscripciones
	
	@SuppressWarnings("unchecked")
	public List<Inscripcione> findAllInscripciones(){
		return mDAO.findAll(Inscripcione.class, "o.id_usuario");
	}
	public Inscripcione findInscripcionesById(Integer id_inscripciones) throws Exception {
		return (Inscripcione) mDAO.findById(Inscripcione.class, id_inscripciones);
	}

	public void insertarInscripciones(Inscripcione r) throws Exception {
		mDAO.insertar(r);
	}

	public void eliminarInscripciones(Integer id_inscripciones) throws Exception {
		mDAO.eliminar(Inscripcione.class, id_inscripciones);
	}

	public void actualizarInscripciones(Inscripcione id_inscripciones) throws Exception {
		mDAO.actualizar(id_inscripciones);
	}
	
	//solicitud cabecera
	public Solicicabecera findSolicitudCabeceraById(Integer id) throws Exception{
		return (Solicicabecera) mDAO.findById(Solicicabecera.class, id);
	}
}
