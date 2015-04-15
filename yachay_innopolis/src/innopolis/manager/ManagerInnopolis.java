package innopolis.manager;

import java.util.List;


import innopolis.entities.*;

public class ManagerInnopolis {
	
	private ManagerDAO mDAO;
	

	public ManagerInnopolis()
	{
		mDAO= new ManagerDAO();
	}
	
	
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

		public void eliminarEvento(Integer id_evento) throws Exception {
			mDAO.eliminar(Evento.class, id_evento);
		}

		public void actualizarEvento(Evento evento) throws Exception {
			mDAO.actualizar(evento);
		}

}
