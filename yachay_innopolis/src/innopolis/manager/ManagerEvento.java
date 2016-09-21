package innopolis.manager;

import java.sql.Timestamp;
import java.util.List;

import innopolis.entidades.*;

public class ManagerEvento {

	private ManagerDAO mDAO;

	// Temporales
	private Evento eventoTmp;

	// Almacenar tipos y estados
	private static Tipoevento te;
	private static Solicicabecera sc;
	private static Coloreve ce;
	private static Usuario usr;
	private static Sala sala;

	// MENSAJE
	int p = 0;
	String h = "";

	public ManagerEvento() {
		mDAO = new ManagerDAO();
	}

	// ------Eventos-------

	// listar todos los eventos
	@SuppressWarnings("unchecked")
	public List<Evento> findAllEventos() {
		return mDAO.findAll(Evento.class);
	}

	// listar todos los eventos en ordenados
	@SuppressWarnings("unchecked")
	public List<Evento> findAllEventosOrdenados() {
		return mDAO.findWhere(Evento.class, "1=1", "o.fechaInicio desc");
	}

	// buscar evento por ID
	public Evento EventoByID(Integer id_evento) throws Exception {
		return (Evento) mDAO.findById(Evento.class, id_evento);
	}

	@SuppressWarnings("unchecked")
	public List<Evento> findEventoByCabeceraId(Integer id) throws Exception {
		return mDAO.findWhere(Evento.class, " o.solicicabecera.idSolcab = "+ id + " ", " o.fechaInicio ");
	}

	// insertar los eventos
	public void insertarEvento(String nombre, String descripcion,
			String imagen, Timestamp fecha_inicio, Timestamp fecha_fin,
			float costo, Integer cantidad, String estadoeven) throws Exception {
		try {
			Evento r = new Evento();
			r.setNombre(nombre);
			r.setDescripcion(descripcion);
			// r.setLugar(lugar);
			r.setImagen(imagen);
			r.setFechaInicio(fecha_inicio);
			r.setFechaFin(fecha_fin);
			r.setCosto(costo);
			r.setCantidad(cantidad);
			r.setTipoevento(te);
			r.setSolicicabecera(sc);
			r.setSala(sala);
			r.setEstado(estadoeven);
			r.setUsuario(usr);
			r.setInterno(false);
			r.setSms("No Notificado");

			mDAO.insertar(r);

			Salasactiva salaAct = new Salasactiva();
			salaAct.setIdEvento(r.getIdEvento());
			salaAct.setHoraInicio(r.getFechaInicio());
			salaAct.setHoraFin(r.getFechaFin());
			salaAct.setIdSala(r.getSala().getIdSala());

			mDAO.insertar(salaAct);

			System.out.println("Bien_insertar_evento");
			sc = null;
		} catch (Exception e) {
			System.out.println("Error_insertar_evento");
			e.printStackTrace();
		}

	}

	// Temporales
	public Evento crearEventoTmp(String nombre, String descripcion /*
																	 * , String
																	 * lugar
																	 */,
			String imagen, Timestamp fecha_inicio, Timestamp fecha_fin,
			float costo, Integer cantidad) throws Exception {
		eventoTmp = new Evento();
		eventoTmp.setNombre(nombre);
		eventoTmp.setDescripcion(descripcion);
		// eventoTmp.setLugar(lugar);
		eventoTmp.setImagen(imagen);
		eventoTmp.setFechaInicio(fecha_inicio);
		eventoTmp.setFechaFin(fecha_fin);
		eventoTmp.setCosto(costo);
		eventoTmp.setCantidad(cantidad);
		eventoTmp.setSolicicabecera(sc);
		eventoTmp.setTipoevento(te);
		eventoTmp.setSala(sala);
		eventoTmp.setEstado("Pendiente");
		eventoTmp.setUsuario(usr);
		eventoTmp.setSms("No Notificado");
		eventoTmp.setInterno(false);
		return eventoTmp;
	}

	// guardar evento temporal
	public void insertarEventoTem() throws Exception {
		try {
			// eventTemp.setSolicicabecera();
			mDAO.insertar(eventoTmp);
			System.out.println("Bien_insertar_eventoTemp");
		} catch (Exception e) {
			System.out.println("Error_insertar_eventoTemp");
			e.printStackTrace();
		}

	}

	// editar los eventos
	public void editarEventos(Integer id_evento, String nombre,
			String descripcion,/* String lugar, */String imagen,
			Timestamp fecha_inicio, Timestamp fecha_fin, float costo,
			Integer cantidad, Boolean interno) throws Exception {
		try {
			Evento r = this.EventoByID(id_evento);
			r.setIdEvento(id_evento);
			r.setNombre(nombre);
			r.setDescripcion(descripcion);
			// r.setLugar(lugar);
			r.setImagen(imagen);
			r.setFechaInicio(fecha_inicio);
			r.setFechaFin(fecha_fin);
			r.setCosto(costo);
			r.setCantidad(cantidad);
			r.setTipoevento(te);
			r.setSala(sala);
			r.setInterno(interno);
			mDAO.actualizar(r);
			System.out.println("bien_mod_evento");
		} catch (Exception e) {
			System.out.println("Error_mod_evento");
			e.printStackTrace();
		}
	}
	
	// editar los eventos
		public void editarEventoCreado(Integer id_evento, Integer cdId) throws Exception {
			try {
				Evento r = this.EventoByID(id_evento);
				r.setIdEvento(id_evento);
				r.setSolicicabecera(sc);
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

	// metodo para asignar el SolicitudCabecera al Evento
	public Solicicabecera asignarSolcab(Integer sol) {
		try {
			sc = this.SolByID(sol);
			System.out.println("si relizo la entrada");
		} catch (Exception e) {
			System.out.println("asignacion de tipo erroneo");
			e.printStackTrace();
		}
		return sc;
	}

	public void editarSolicitudCabecera(Integer idSolicitudCabecera,
			String actividad, String objetivo) throws Exception {
		try {
			Solicicabecera solcab = this.SolByID(idSolicitudCabecera);
			solcab.setActividad(actividad);
			solcab.setObjetivo(objetivo);
			mDAO.actualizar(solcab);
			System.out.println("Bien_mod_solicitudcab");
		} catch (Exception e) {
			System.out.println("Error_mod_solicitudcab");
			e.printStackTrace();
		}
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

	// buscar usuairo por ID
	public Usuario UsuarioByID(Integer id_usr) throws Exception {
		return (Usuario) mDAO.findById(Usuario.class, id_usr);
	}

	// buscar sala por ID
	public Sala SalaByID(Integer id_sala) throws Exception {
		return (Sala) mDAO.findById(Sala.class, id_sala);
	}

	// insertar tipo de evento
	public void insertarTipoEvento(String tipo, String descripcion)
			throws Exception {
		Tipoevento te = new Tipoevento();
		te.setTipo(tipo);
		te.setDescripcion(descripcion);
		// te.setColoreve(ce);
		mDAO.insertar(te);
	}

	// Modificar el tipoevento
	public void editarTipoEvento(Integer id_tevent, String tipo,
			String descripcion/* ,Integer idcolor */) throws Exception {
		Tipoevento te = TipoEventoByID(id_tevent);
		te.setTipo(tipo);
		te.setDescripcion(descripcion);
		// ce= findcolorxid(idcolor);
		// te.setColoreve(ce);
		mDAO.actualizar(te);
	}

	// metodo para buscar el tipo color por id
	public Coloreve findcolorxid(Integer a) {
		Coloreve p = new Coloreve();
		try {
			p = (Coloreve) mDAO.findById(Coloreve.class, a);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return p;
	}

	// metodo para asignar el TipoEvento al Evento
	public Tipoevento asignarTipoevento(Integer idteve) {
		try {
			te = this.TipoEventoByID(idteve);
			System.out.println("si relizo la entrada");
		} catch (Exception e) {
			System.out.println("asignacion de tipo erroneo");
			e.printStackTrace();
		}
		return te;
	}

	// metodo para asignar el TipoEvento al Evento
	public Usuario asignarUsuario(Integer id_usur) {
		try {
			usr = this.UsuarioByID(id_usur);
			System.out.println("si relizo la entrada");
		} catch (Exception e) {
			System.out.println("asignacion de usuario erroneo");
			e.printStackTrace();
		}
		return usr;
	}

	// metodo para asignar el TipoEvento al Evento
	public Sala asignarSala(Integer id_sala) {
		try {
			sala = this.SalaByID(id_sala);
			System.out.println("si relizo la entrada");
		} catch (Exception e) {
			System.out.println("asignacion de usuario erroneo");
			e.printStackTrace();
		}
		return sala;
	}

	// ------Salas-------

	// listar todos los tiposeventos
	@SuppressWarnings("unchecked")
	public List<Sala> findAllSalas() {
		return mDAO.findAll(Sala.class);
	}

	// insertar tipo de evento
	public void insertarSala(String tipo, String descripcion) throws Exception {
		Tipoevento te = new Tipoevento();
		te.setTipo(tipo);
		te.setDescripcion(descripcion);
		te.setColoreve(ce);
		mDAO.insertar(te);
	}

	// Modificar el tipoevento
	public void editarSala(Integer id_tevent, String tipo, String descripcion,
			Integer idcolor) throws Exception {
		Tipoevento te = TipoEventoByID(id_tevent);
		te.setTipo(tipo);
		te.setDescripcion(descripcion);
		ce = findcolorxid(idcolor);
		te.setColoreve(ce);
		mDAO.actualizar(te);
	}

	// metodo para buscar el tipo color por id
	public Colorsala findcolosalarxid(Integer a) {
		Colorsala p = new Colorsala();
		try {
			p = (Colorsala) mDAO.findById(Colorsala.class, a);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return p;
	}

	// ------Inscripciones-------

	// listar todas las inscripciones
	@SuppressWarnings("unchecked")
	public List<Inscripcione> findAllInscripciones() {
		return mDAO.findAll(Inscripcione.class);
	}

	// listar todas las inscripciones
	@SuppressWarnings("unchecked")
	public List<Inscripcione> findAllInscripcionesOrdenado() {
		return mDAO.findWhere(Inscripcione.class, "1=1",
				"o.fecha_inscripcion desc");
	}

	// buscar incripciones por ID
	public Inscripcione findInscripcionesByID(Integer id_inscrip)
			throws Exception {
		return (Inscripcione) mDAO.findById(Inscripcione.class, id_inscrip);
	}

	public void insertarInscripcion(Evento eventoInscripcion,
			Timestamp fechaInscripcion, Integer idUsuario, String nombre,
			String apellido, String correo, String direccion, String telefono,
			String celular, String imagenPago, String observacion)
			throws Exception {
		Inscripcione ins = new Inscripcione();
		ins.setFechaInscripcion(fechaInscripcion);
		ins.setIdUsuario(idUsuario);
		ins.setNombre(nombre);
		ins.setApellido(apellido);
		ins.setCorreo(correo);
		ins.setDireccion(direccion);
		ins.setTelefono(telefono);
		ins.setCelular(celular);
		ins.setEvento(eventoInscripcion);
		ins.setEstado("sin aprobar");
		ins.setSms("sin notificar");
		ins.setImagenPago(imagenPago);
		ins.setObservacion(observacion);
		mDAO.insertar(ins);
	}

	public void modificarInscripcion(Integer id_ins, String estado)
			throws Exception {
		Inscripcione i = findInscripcionesByID(id_ins);
		i.setEstado(estado);
		mDAO.actualizar(i);
	}

	public void notificarInscripcion(Integer id_ins, String notificacion)
			throws Exception {
		Inscripcione i = findInscripcionesByID(id_ins);
		i.setSms(notificacion);
		mDAO.actualizar(i);
	}

	// VALIDAR INSCRIPCIONES
	public List<Inscripcione> inscritosXEvento(Evento ev) {
		List<Inscripcione> todas = findAllInscripciones();
		List<Inscripcione> listado = findAllInscripciones();

		for (Inscripcione inscripcione : todas) {
			if (inscripcione.getEvento().getIdEvento() != ev.getIdEvento()) {
				listado.remove(inscripcione);
			}
		}

		return listado;
	}

	public Integer cuantosInscritos(Evento event) {
		List<Inscripcione> listado = inscritosXEvento(event);
		int cont = 0;
		for (Inscripcione inscripcione : listado) {
			if (inscripcione.getEstado().equals("aprobada")) {
				cont++;
			}
		}
		return cont;
	}

	public boolean superaInscritosEvento(Evento evento) {
		boolean resp = false;

		int inscritos = cuantosInscritos(evento);
		if (inscritos > evento.getCantidad()) {
			resp = true;
		}

		return resp;
	}

	// listar todos los colores
	@SuppressWarnings("unchecked")
	public List<Coloreve> findAllColoreves() {
		return mDAO.findAll(Coloreve.class);
	}

	// buscar color por ID
	public Coloreve ColorByID(Integer id_color) throws Exception {
		return (Coloreve) mDAO.findById(Coloreve.class, id_color);
	}

	// metodo para asignar el color al tipo Evento
	public Tipoevento asignarTipocolor(Integer idcolor) {
		try {
			ce = this.ColorByID(idcolor);
			System.out.println("si relizo la entrada");
		} catch (Exception e) {
			System.out.println("asignacion de tipo erroneo");
			e.printStackTrace();
		}
		return te;
	}

	// ////METODO DE ENVIO DE CORREO
	// public boolean sendMail(String origen,String clave,String destinatario,
	// String asunto, String mensaje) throws Exception{
	// p=0;
	//
	// try
	// {
	// Properties props = new Properties();
	// props.put("mail.smtp.host", "smtp.gmail.com");
	// props.setProperty("mail.smtp.starttls.enable", "true");
	// props.setProperty("mail.smtp.port", "587");
	// props.setProperty("mail.smtp.user", origen);
	// props.setProperty("mail.smtp.auth", "true");
	//
	// Session session = Session.getDefaultInstance(props, null);
	// BodyPart texto = new MimeBodyPart();
	// texto.setText(mensaje);
	//
	// MimeMultipart multiParte = new MimeMultipart();
	// multiParte.addBodyPart(texto);
	//
	// MimeMessage message = new MimeMessage(session);
	// message.setFrom(new InternetAddress(origen));
	// message.addRecipients(
	// Message.RecipientType.TO,
	// InternetAddress.parse(destinatario));
	// message.setSubject(asunto);
	// message.setContent(multiParte);
	//
	// Transport t = session.getTransport("smtp");
	// t.connect(origen, clave);
	// t.sendMessage(message, message.getAllRecipients());
	// t.close();
	// h="Enviado correctamente la notificacion";
	// return true;
	// }
	// catch (Exception e)
	// {
	// e.printStackTrace();
	// h="Error al  enviar la notificacion";
	// return false;
	// }
	// }

	// metodo para enviar el estado del mensaje si se envio
	public String cambioSMSenvio(Integer idevento) throws Exception {
		h = "";
		try {
			Evento ev = this.EventoByID(idevento);
			if (ev.getSms().equals("No Notificado")) {
				ev.setSms("Notificado");
			}
		} catch (Exception e) {
			throw new Exception("No se envio el mensaje");
		}
		return h;
	}

	// cambio de estado
	public void cambioSMS(Integer id_eve) throws Exception {
		try {
			Evento ev = this.EventoByID(id_eve);
			if (ev.getSms().equals("Notificado")) {
				ev.setSms("No Notificado");
			}
		} catch (Exception e) {
			throw new Exception("error");
		}
	}
}
