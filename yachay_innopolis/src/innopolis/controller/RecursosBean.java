package innopolis.controller;

import innopolis.entidades.Recurso;
import innopolis.entidades.Recursodisponible;
import innopolis.entidades.Recursotipo;
import innopolis.entidades.Sala;
import innopolis.entidades.help.UsuarioHelp;
import innopolis.manager.ManagerReservas;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Serializable;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;
import javax.servlet.ServletContext;

import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.UploadedFile;

@SessionScoped
@ManagedBean
public class RecursosBean implements Serializable {

	private static final long serialVersionUID = 1L;

	private ManagerReservas manager;
	private Integer idRecurso;
	private String capacidad;
	private String descripcion;
	private String imagen;
	private String lugar;
	private Integer stock;
	private String nombre;
	private Integer rd;
	private Integer rt;

	private UsuarioHelp session;

	// imagenes
	private UploadedFile file;
	private String g;

	public RecursosBean() {
		/* Session */
		session = SessionBean.verificarSession();
		manager = new ManagerReservas();
		imagen = "300.jpg";
		capacidad = "";
		stock = 0;
	}

	public Integer getStock() {
		return stock;
	}

	public void setStock(Integer stock) {
		this.stock = stock;
	}

	public Integer getIdRecurso() {
		return idRecurso;
	}

	public void setIdRecurso(Integer idRecurso) {
		this.idRecurso = idRecurso;
	}

	public String getCapacidad() {
		return capacidad;
	}

	public void setCapacidad(String capacidad) {
		this.capacidad = capacidad;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public String getImagen() {
		return imagen;
	}

	public void setImagen(String imagen) {
		this.imagen = imagen;
	}

	public String getLugar() {
		return lugar;
	}

	public void setLugar(String lugar) {
		this.lugar = lugar;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public Integer getRd() {
		return rd;
	}

	public void setRd(Integer rd) {
		this.rd = rd;
	}

	public Integer getRt() {
		return rt;
	}

	public void setRt(Integer rt) {
		this.rt = rt;
	}

	public List<Recurso> getListRegistro() {
		return manager.findAllRecurso();
	}

	/**
	 * @return the session
	 */
	public UsuarioHelp getSession() {
		return session;
	}

	/**
	 * @param session
	 *            the session to set
	 */
	public void setSession(UsuarioHelp session) {
		this.session = session;
	}

	// accion para invocar el manager y crear recurso
	public String crearRecurso() {
		/*
		 * if(rt.equals(-1)){ FacesContext.getCurrentInstance().addMessage(null,
		 * new
		 * FacesMessage(FacesMessage.SEVERITY_WARN,"Seleccione tipo recurso",
		 * null)); }else{
		 */
		try {
			if (isNumeric(capacidad)) {
				manager.insertarRecurso(Integer.parseInt(capacidad),
						descripcion.trim(), lugar.trim(), nombre.trim(), imagen/*
																				 * ,
																				 * stock
																				 */);
				// reiniciamos datos (limpiamos el formulario)
				capacidad = "";
				descripcion = "";
				lugar = "";
				// stock=0;
				nombre = "";
				imagen = "300.jpg";
				rd = 1;
				rt = 0;
				FacesContext
						.getCurrentInstance()
						.addMessage(
								null,
								new FacesMessage(
										"Registrado..!!! Recurso creado", null));
			} else {
				FacesContext.getCurrentInstance().addMessage(
						null,
						new FacesMessage(FacesMessage.SEVERITY_ERROR,
								"La cantidad debe ser numérica", null));
			}
		} catch (Exception e) {
			FacesContext.getCurrentInstance().addMessage(
					null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR,
							"Error al crear el recurso", null));
			FacesContext.getCurrentInstance().addMessage(
					null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR, e
							.getMessage(), null));
		}
		return "";
	}

	public static boolean isNumeric(String str) {
		return str.matches("[+-]?\\d*(\\.\\d+)?");
	}

	// metodo para mostrar los RecursosTipos en Recursos
	public List<SelectItem> getListaRecTipo() {
		List<SelectItem> listadoSI = new ArrayList<SelectItem>();
		List<Recursotipo> listadoRecursos = manager.findAllTipoRecurso();

		for (Recursotipo t : listadoRecursos) {
			SelectItem item = new SelectItem(t.getIdRectipo(), t.getTipo());
			listadoSI.add(item);
		}
		return listadoSI;
	}

	// metodo para asignar el RecursoTipo al Recurso
	public String asignarRecTipo() {
		manager.asignarRecursoTipo(rt);
		return "";
	}

	// metodo para mostrar los RecursosDisponibles en Recursos
	public List<SelectItem> getListaRecDisponibles() {
		List<SelectItem> listadoSI = new ArrayList<SelectItem>();
		List<Recursodisponible> listadoRecursos = manager
				.findAllRecursoDisponibles();

		for (Recursodisponible t : listadoRecursos) {
			SelectItem item = new SelectItem(t.getIdRecdisponible(),
					t.getDisponible());
			listadoSI.add(item);
		}
		return listadoSI;
	}

	// metodo para asignar el RecursoDisponible al Recurso
	public String asignarRecDisponible() {
		manager.asignarRecursoDisponible(rd);
		return "";
	}

	// accion para cargar los datos en el formulario
	public String cargarRecursos(Recurso t) {
		idRecurso = t.getIdRecurso();
		capacidad = t.getCapacidad().toString();
		nombre = t.getNombre();
		asignarNombreImagen();
		lugar = t.getLugar();
		descripcion = t.getDescripcion();
		imagen = t.getImagen();
		// stock=t.getStock();
		// rt=t.getRecursotipo().getIdRectipo();
		return "";
	}

	// accion para modificar los recursos
	public void actualizarRecurso() {
		try {
			if (isNumeric(capacidad)) {
			manager.editarRecurso(idRecurso, Integer.parseInt(capacidad),
					descripcion.trim(), lugar.trim(), nombre.trim(), imagen/* ,stock, rt */);
			FacesContext.getCurrentInstance().addMessage(
					null,
					new FacesMessage("Actualizado..!!! Recurso actualizado",
							null));
			} else {
				FacesContext.getCurrentInstance().addMessage(
						null,
						new FacesMessage(FacesMessage.SEVERITY_ERROR,
								"La cantidad debe ser numérica", null));
			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}

	// activar y desactivar
	public String cambiarEstado(Recurso r) {
		try {
			FacesContext context = FacesContext.getCurrentInstance();
			context.addMessage(
					null,
					new FacesMessage("Información "
							+ manager.cambioDisRecurso(r.getIdRecurso()), null));

		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		return "";
	}

	// ------ traslados--------

	public void irRecurso() {
		// limpiamos los datos
		idRecurso = 0;
		capacidad = "";
		descripcion = "";
		lugar = "";
		// stock=0;
		nombre = "";
		setImagen("300.jpg");
		rd = 1;
		rt = 0;
	}

	public String irRec() {
		return "recurso";
	}

	public String irTrecurso() {
		return "rectipo?faces-redirect=true";
	}

	public String irEvento() {
		return "eventos";
	}

	// metodo para guardar la imagen en el servidor
	public void ImagenServ(FileUploadEvent event) throws IOException {
		file = event.getFile();
		InputStream inputStream = null;
		OutputStream outputStream = null;

		if (file != null) {
			try {
				// Tomar PAD REAL
				ServletContext servletContext = (ServletContext) FacesContext
						.getCurrentInstance().getExternalContext().getContext();
				// String carpetaImagenes = (String) servletContext
				// .getRealPath(File.separatorChar + "imgevent");
				String carpetaImagenes = "/opt/wildfly/standalone/img/img_regece/imgevent/";
				setImagen(g);
				System.out.println("PAD------> " + carpetaImagenes);
				System.out.println("name------> " + getImagen());
				outputStream = new FileOutputStream(new File(carpetaImagenes
						+ File.separatorChar + getImagen()));
				inputStream = file.getInputstream();

				int read = 0;
				byte[] bytes = new byte[1024];

				while ((read = inputStream.read(bytes)) != -1) {
					outputStream.write(bytes, 0, read);
				}

				FacesContext.getCurrentInstance().addMessage(
						null,
						new FacesMessage(FacesMessage.SEVERITY_INFO,
								"Correcto: Carga correcta", null));

			} catch (Exception e) {
				FacesContext.getCurrentInstance().addMessage(
						null,
						new FacesMessage(FacesMessage.SEVERITY_ERROR,
								"Error: No se pudo subir la imagen", null));
				e.printStackTrace();
			} finally {
				if (inputStream != null) {
					inputStream.close();
				}

				if (outputStream != null) {
					outputStream.close();
				}
			}
		} else {
			FacesContext.getCurrentInstance().addMessage(
					null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR,
							"Error: No se pudo seleccionar la imagen", null));
		}
	}

	// metodo para poner el nombre a la imagen
	public void asignarNombreImagen() {
		if (getNombre().trim().isEmpty()) {
		} else {
			DateFormat dateFormat = new SimpleDateFormat("_ddMMyyyyHHmm");
			g = "img_" + getNombre() + dateFormat.format(new Date()) + ".jpg";
			System.out.println(g);
		}
	}

	// metodo para poner el nombre a la imagen
	public void nombreImagen(String n) {
		List<Sala> li = manager.findAllSalas();
		for (Sala e : li) {
			if (e.getImagen().contains(n)) {
				g = e.getImagen();
			}
		}
		System.out.println(g);
	}

	// envios
	// ir a sugerencias lista
	public String ircreacioneventos() {
		return "evetipo?faces-redirect=true";
	}

	public String irsalas() {
		return "salas?faces-redirect=true";
	}

}
