package innopolis.manager;

import innopolis.entities.Evento;

public class ManagerInnopolis {
	@SuppressWarnings("unused")
	private ManagerDAO mDAO;
	private Evento evento;
	
	public ManagerInnopolis()
	{
		mDAO= new ManagerDAO();
	}
	private Evento getEvento() {
		return evento;
	}
	private void setEvento(Evento evento) {
		this.evento = evento;
	}
	
	
}
