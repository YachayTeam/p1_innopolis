package innopolis.manager;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

import innopolis.entities.*;

public class ManagerEvento {

	private ManagerDAO mDAO;
	
	//Temporales
	private Evento eventoTmp;
	private Evento eventoInscripcion;
	
	//Almacenar tipos y estados
		private static Tipoevento te;
		private static Solicicabecera sc;

	public ManagerEvento() {
		mDAO = new ManagerDAO();
	}

	// ------Eventos-------

		// listar todos los eventos
		@SuppressWarnings("unchecked")
		public List<Evento> findAllEventos() {
			return mDAO.findAll(Evento.class);
		}

		// buscar evento por ID
		public Evento EventoByID(Integer id_evento) throws Exception {
			return (Evento) mDAO.findById(Evento.class, id_evento);
		}

		// insertar los eventos
		public void insertarEvento(String nombre, String descripcion,
				String lugar, String imagen, Date fecha, float costo, Integer cantidad)
				throws Exception {
			try {
				Evento r = new Evento();
				r.setNombre(nombre);
				r.setDescripcion(descripcion);
				r.setLugar(lugar);
				r.setImagen(imagen);
				r.setFecha(fecha);
				r.setCosto(costo);
				r.setCantidad(cantidad);
				r.setTipoevento(te);
				r.setSolicicabecera(sc);
				mDAO.insertar(r);
				System.out.println("Bien_insertar_evento");
				sc=null;
			} catch (Exception e) {
				System.out.println("Error_insertar_evento");
				e.printStackTrace();
			}

		}
		
		//Temporales
			public Evento crearEventoTmp(String nombre, String descripcion,
					String lugar, String imagen, Date fecha, float costo, Integer cantidad)
							throws Exception {
				eventoTmp=new Evento();
				eventoTmp.setNombre(nombre);
				eventoTmp.setDescripcion(descripcion);
				eventoTmp.setLugar(lugar);
				eventoTmp.setImagen(imagen);
				eventoTmp.setFecha(fecha);
				eventoTmp.setCosto(costo);
				eventoTmp.setCantidad(cantidad);
				eventoTmp.setSolicicabecera(sc);
				return eventoTmp;
			}
			
		//guardar evento temporal
			public void insertarEventoTem() throws Exception {
				try {
					 //eventTemp.setSolicicabecera();            
					mDAO.insertar(eventoTmp);
					System.out.println("Bien_insertar_eventoTemp");
				} catch (Exception e) {
					System.out.println("Error_insertar_eventoTemp");
					e.printStackTrace();
				}

			}
		// editar los eventos
		public void editarEventos(Integer id_evento, String nombre,
				String descripcion, String lugar, String imagen, Date fecha,
				float costo, Integer cantidad) throws Exception {
			try {
				Evento r = this.EventoByID(id_evento);
				r.setNombre(nombre);
				r.setDescripcion(descripcion);
				r.setLugar(lugar);
				r.setImagen(imagen);
				r.setFecha(fecha);
				r.setCosto(costo);
				r.setCantidad(cantidad);
				mDAO.actualizar(r);
				System.out.println("bien_mod_evento");
			} catch (Exception e) {
				System.out.println("Error_mod_evento");
				e.printStackTrace();
			}
		}
		
		// buscar sol por ID
			public Solicicabecera SolByID(Integer sol) throws Exception {
				return (Solicicabecera) mDAO.findById(Solicicabecera.class, sol);
			}
		
		//metodo para asignar el SolicitudCabecera al Evento
		 	public Tipoevento asignarSolcab(Integer sol) {
		 		try {
					sc=this.SolByID(sol);
					System.out.println("si relizo la entrada");
				} catch (Exception e) {
					System.out.println("asignacion de tipo erroneo");
					e.printStackTrace();
				}
		 		return te;
			}
		 	

		// ------TipoEventos-------

		// listar todos los tiposeventos
		@SuppressWarnings("unchecked")
		public List<Tipoevento> findAllTipoEventos() {
			return mDAO.findAll(Tipoevento.class);
		}

		// buscar tipoevento por ID
		public Tipoevento TipoEventoByID(Integer id_tevento) throws Exception {
			return (Tipoevento) mDAO.findById(Tipoevento.class, id_tevento);
		}
		
		//insertar tipo de evento
			public void insertarTipoEvento(String tipo, String descripcion) throws Exception{
				Tipoevento te = new Tipoevento();
				te.setTipo(tipo);
				te.setDescripcion(descripcion);
				mDAO.insertar(te);
			}
			
			//Modificar el tipoevento
			public void editarTipoEvento(Integer id_tevent, String tipo, String descripcion) throws Exception{
				Tipoevento te = TipoEventoByID(id_tevent);
				te.setTipo(tipo);
				te.setDescripcion(descripcion);
				mDAO.actualizar(te);
			}
			
		//metodo para asignar el TipoEvento al Evento
	 	public Tipoevento asignarTipoevento(Integer idteve) {
	 		try {
				te=this.TipoEventoByID(idteve);
				System.out.println("si relizo la entrada");
			} catch (Exception e) {
				System.out.println("asignacion de tipo erroneo");
				e.printStackTrace();
			}
	 		return te;
		}

 	// ------Inscripciones-------

	// listar todas las inscripciones
	@SuppressWarnings("unchecked")
	public List<Inscripcione> findAllInscripciones() {
		return mDAO.findAll(Inscripcione.class);
	}

	// buscar incripciones por ID
	public Inscripcione findInscripcionesByID(Integer id_inscrip) throws Exception {
		return (Inscripcione) mDAO.findById(Inscripcione.class, id_inscrip);
	}
	
	public void insertarInscripcion(Timestamp fechaInscripcion, Integer idUsuario, String nombre, String apellido, String correo, String imagenPago, String observacion) throws Exception{
		Inscripcione ins = new Inscripcione();
		ins.setFechaInscripcion(fechaInscripcion);
		ins.setIdUsuario(idUsuario);ins.setNombre(nombre);ins.setApellido(apellido);ins.setCorreo(correo);
		ins.setEvento(eventoInscripcion);ins.setEstado("sin aprobar");ins.setSms("sin notificar");
		ins.setImagenPago(imagenPago);ins.setObservacion(observacion);
		mDAO.insertar(ins);
	}
	
	public void modificarInscripcion(Integer id_ins, String estado) throws Exception{
		Inscripcione i = findInscripcionesByID(id_ins);
		i.setEstado(estado);
		mDAO.actualizar(i);
	}
	
	public void seleccionEventoAinscribirse(Evento evento){
		eventoInscripcion = evento;
		System.out.println("Evento "+evento.getNombre());
	}
	
	public void notificarInscripcion(Integer id_ins, String notificacion) throws Exception{
		Inscripcione i = findInscripcionesByID(id_ins);
		i.setSms(notificacion);
		mDAO.actualizar(i);
	}
	
	//VALIDAR INSCRIPCIONES
	public List<Inscripcione> inscritosXEvento(Evento ev){
		List<Inscripcione> todas = findAllInscripciones();
		List<Inscripcione> listado = findAllInscripciones();
		
		for (Inscripcione inscripcione : todas) {
			if(inscripcione.getEvento().getIdEvento()!=ev.getIdEvento()){
				listado.remove(inscripcione);
			}
		}
		
		return listado;
	}
	
	public Integer cuantosInscritos(Evento event){
		List<Inscripcione> listado = inscritosXEvento(event);
		int cont = 0;
		for (Inscripcione inscripcione : listado) {
			if(inscripcione.getEstado().equals("aprobada")){
				cont++;
			}
		}	
		return cont;
	}
	
	public boolean superaInscritosEvento(Evento evento){
		boolean resp = false;
		
		int inscritos = cuantosInscritos(evento);
		if(inscritos>evento.getCantidad()){
			resp = true;
		}
		
		return resp;
	}


}
