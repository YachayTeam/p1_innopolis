package innopolis.manager;

import java.sql.Time;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.ArrayList;
import java.util.Properties;

import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

import innopolis.entities.*;

public class ManagerReservas {
	
private ManagerDAO mDAO;
	
	//Solicitud Temporal
	private Solicicabecera soliTemp;
	
	//Almacenar tipos y estados
	private static Recursotipo rt;
	private static Recursodisponible rd;
	private static Recurso r;
	String h="";
	
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
		Contadore cont = null;
		try {
			cont = (Contadore) mDAO.findById(Contadore.class, 1);
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception("Revise el parametro 'contador solicitud': "+e.getMessage());
		}
		contSolicitud = cont.getValor();
		return contSolicitud;
	}
	
	public void actualizarContadorSolicitud(int valor) throws Exception{
		Contadore cont = null;
		try {
			cont = (Contadore) mDAO.findById(Contadore.class, 1);
			cont.setValor(valor);
			mDAO.actualizar(cont);
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception("Error al actualizar el parametro 'contador solicitud': "+e.getMessage());
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
		soliTemp.setObjetivo(objetivo);soliTemp.setJustificacion(justificacion);
		soliTemp.setSms("sin notificar");soliTemp.setFecha(fecha);
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
		//Validar recursos existente
		if(esRecursoAnadido(id_recurso,soliTemp))
			throw new Exception("El recurso ya se encuentra agregado");
		
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
	public void guardarSolicitudTemporal(Solicicabecera soliTmp) throws Exception{
		
		if(soliTmp==null)
			throw new Exception("Debe crear una solicitud primero.");
		if(soliTmp.getSolicidetalles()==null || soliTmp.getSolicidetalles().size()==0)
			throw new Exception("Debe ingresar los recursos en la solicitud.");
		
		//Agregar contador
		int contSolicitud; 
		contSolicitud = this.getContadorSolicitud();
		contSolicitud++;
		soliTmp.setIdSolcab(contSolicitud);
		
		//ARRAY PARA RECURSOS ACTIVOS
		ArrayList<Recursosactivo> listRecursos = new ArrayList<Recursosactivo>();
		
		//DETALLES
		for(Solicidetalle det : soliTmp.getSolicidetalles()){
			//Combinamos la relacion bidireccional
			det.setSolicicabecera(soliTmp);
			//Agregamos al listado de recursos activos
			Recursosactivo recAct = new Recursosactivo();
			recAct.setIdSolicitud(contSolicitud);
			recAct.setFecha(soliTmp.getFecha());
			recAct.setHoraInicio(soliTmp.getHorainicio());
			recAct.setHoraFin(soliTmp.getHorafin());
			recAct.setIdRecurso(det.getRecurso().getIdRecurso());
			listRecursos.add(recAct);
		}
		
		//Insertamos los datosa la bdd
		mDAO.insertar(soliTmp);
		
		//INSERTAR EN TABLA AYUDA
		for (Recursosactivo recursosactivo : listRecursos) {
			mDAO.insertar(recursosactivo);
		}		
		
		//actualizamos los parametros Contadore
		actualizarContadorSolicitud(contSolicitud);
		
		soliTmp = null;
	}
	
	//Recurso ya Añadido
	public boolean esRecursoAnadido(Integer id_recurso, Solicicabecera solicitud){
		List<Solicidetalle> listado = solicitud.getSolicidetalles();
		boolean resp = false;
		for (Solicidetalle solicidetalle : listado) {
			if(solicidetalle.getRecurso().getIdRecurso() == id_recurso){
				resp = true;
			}
		}
		return resp;
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
	
	//Busca recurso por ID SOL Y REC
	public Recursosactivo findByIdSoliciYRecurso(Integer idSolicitud, Integer idRecurso){
		Recursosactivo resp = null;
		List<Recursosactivo> list = findAllRecursosSolicitados();
		for (Recursosactivo recursosactivo : list) {
			if(recursosactivo.getIdSolicitud()==idSolicitud && recursosactivo.getIdRecurso() == idRecurso){
				resp = recursosactivo;
			}
		}
		return resp;
	}
	
	//Agregar Quitar LISTADO DE RECURSOS a Tabla Auxiliar
	public void agregarListaRecursoActivo(List<Recurso> listado,Integer id_solicitud) throws Exception{
		Solicicabecera solicitud = findSolicitudCabeceraById(id_solicitud);
		
		for (Recurso recurso : listado) {
			insertarRecursoSolicitado(id_solicitud, solicitud.getFecha(), solicitud.getHorainicio(), solicitud.getHorafin(), recurso.getIdRecurso());
		}
	}
	
	public void quitarListaRecursoActivo(List<Recurso> listado, Integer id_solicitud) throws Exception{
		for (Recurso recurso : listado) {
			Recursosactivo recAct = findByIdSoliciYRecurso(id_solicitud, recurso.getIdRecurso());
			if(recAct!=null){
				eliminarRecursoSolicitado(recAct.getIdRecact());
			}
		}
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
	public void cambiarEstadoSolicitud(Integer id_solicitud, Soliciestado estado) throws Exception{
		Solicicabecera solicitud = this.findSolicitudCabeceraById(id_solicitud);
		solicitud.setSoliciestado(estado);
		mDAO.actualizar(solicitud);
		//LISTADO
		ArrayList<Recurso> listRec = new ArrayList<Recurso>();
		for (Solicidetalle det : solicitud.getSolicidetalles()) {
			listRec.add(det.getRecurso());
		}
		//Al cambiar de pendiente a negado borra recursos de RecursoActivo
		if(estado.equals("negado")){
			quitarListaRecursoActivo(listRec, id_solicitud);
	    //Al cambiar de negado a aprobado agrega recursos a RecusroActivo
		}else if(solicitud.getSoliciestado().getEstado().equals("negado") && estado.equals("aprobado")){
			agregarListaRecursoActivo(listRec, id_solicitud);		
		}
		
	}
	
	//SolicitudDetalle por id_solicitud
	@SuppressWarnings("unchecked")
	public List<Solicidetalle> findAllDetallesSolicitud(){
		return mDAO.findAll(Solicidetalle.class);
	}
	
	public void eliminarSoliciDetalleByID(Integer id_detalle) throws Exception{
		mDAO.eliminar(Solicidetalle.class, id_detalle);
	}
	
	public void insertarSoliciDatalle(Solicidetalle detalle) throws Exception{
		mDAO.insertar(detalle);
	}
	
	public ArrayList<Solicidetalle> findDetallesSolicitud(Integer id_solicitud){
		List<Solicidetalle> todo = findAllDetallesSolicitud();
		ArrayList<Solicidetalle> resp = new ArrayList<Solicidetalle>();
		for (Solicidetalle solicidetalle : todo) {
			if(solicidetalle.getSolicicabecera().getIdSolcab()==id_solicitud){
				resp.add(solicidetalle);
			}
		}
		return resp;
	}
	
 	//Modificacion de Solicitudes
	//SOLO TOMO EN CUENTA AGREGAR Y QUITAR RECURSOS
	public void editarDetallesSolicitud(Integer id_solicitud, ArrayList<Solicidetalle> agregados, ArrayList<Solicidetalle> eliminados) throws Exception{
		ArrayList<Solicidetalle> actual = findDetallesSolicitud(id_solicitud);
		Solicicabecera sol = findSolicitudCabeceraById(id_solicitud);
		//ELIMINAR
		for (Solicidetalle solicidetalle : actual) {
			for (Solicidetalle eliminado : eliminados) {
				if(solicidetalle.getIdSoldet().equals(eliminado.getIdSoldet())){
					eliminarSoliciDetalleByID(solicidetalle.getIdSoldet());
					//Tabla Extra
					eliminarRecursoSolicitado(findByIdSoliciYRecurso(id_solicitud, solicidetalle.getRecurso().getIdRecurso()).getIdRecact());
				}
			}
		}
		//AGREGAR
		for (Solicidetalle detalle : agregados) {
			insertarSoliciDatalle(detalle);
			//Tabla extra
			insertarRecursoSolicitado(id_solicitud, sol.getFecha(), sol.getHorainicio(), sol.getHorafin(), detalle.getRecurso().getIdRecurso());
		}		
	}
	
	//MODIFICAR ESTADO SOLICITUD
	public void aprobarSolicitudMOD(Integer id_solicitud) throws Exception{
		Solicicabecera sol = findSolicitudCabeceraById(id_solicitud);
		sol.setSoliciestado(findSolicitudEstadoByID(3));//VER SOL ESTADO APROBADO
		mDAO.actualizar(sol);
	}
	
	//NOTIFICAR SOLICITUD
	public void notificarSolicitud(Integer id_solicitud) throws Exception{
		Solicicabecera sol = findSolicitudCabeceraById(id_solicitud);
		sol.setSms("notificada");
		mDAO.actualizar(sol);
	}
	
    
    public void cambioSMS(Integer id_soli)  throws Exception{		
		try
		{
			Solicicabecera svt = this.findSolicitudCabeceraById(id_soli);
			if(svt.getSms().equals("notificada"))	
			{
				svt.setSms("no notificado");					
			}
		}
		catch (Exception e)
		{
			throw new Exception("error");	
		}	
    }
	
	////METODO DE ENVIO DE CORREO
	public boolean sendMail(String origen,String clave,String destinatario, String asunto, String mensaje) throws Exception{
        try
        {
            Properties props = new Properties();
            props.put("mail.smtp.host", "smtp.gmail.com");
            props.setProperty("mail.smtp.starttls.enable", "true");
            props.setProperty("mail.smtp.port", "587");
            props.setProperty("mail.smtp.user", origen);
            props.setProperty("mail.smtp.auth", "true");

            Session session = Session.getDefaultInstance(props, null);
            BodyPart texto = new MimeBodyPart();
            texto.setText(mensaje);

            MimeMultipart multiParte = new MimeMultipart();
            multiParte.addBodyPart(texto);

            MimeMessage message = new MimeMessage(session);
            message.setFrom(new InternetAddress(origen));
            message.addRecipient(
                Message.RecipientType.TO,
                new InternetAddress(destinatario));
                message.setSubject(asunto);
            message.setContent(multiParte);

            Transport t = session.getTransport("smtp");
            t.connect(origen, clave);
            t.sendMessage(message, message.getAllRecipients());
            t.close();
            return true;
        }
        catch (Exception e)
        {
            e.printStackTrace();
            return false;
        }             
    }
	
	//buscar servicio por ID
	public Solicicabecera findSolicitudByID(int id_Srv) throws Exception{
		return (Solicicabecera) mDAO.findById(Solicicabecera.class, id_Srv);
	}	
	
	//metodo para enviar el estado del mensaje si se envio
	public String cambioSMSenvio(Integer id_soli)  throws Exception{		
				try
				{
					h="";
					Solicicabecera soli = this.findSolicitudByID(id_soli);
					if(soli.getSms().equals("no notificado"))	
					{
						soli.setSms("notificada");				
						h="El usuario a sido notificado por correo";		
					}
					else if(soli.getSms().equals("notificada"))
					{
						
						h="Ya se ha enviado al correo la notificación";
					}
				}
				catch (Exception e)
				{
					throw new Exception("No se envio el mensaje");	
				}					
				return h;
			}

}