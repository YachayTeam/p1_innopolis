package innopolis.controller;

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

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;
import javax.servlet.ServletContext;

import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.UploadedFile;

import innopolis.entidades.Colorrec;
import innopolis.entidades.Colorsala;
import innopolis.entidades.Sala;
import innopolis.entidades.help.UsuarioHelp;
import innopolis.manager.ManagerReservas;
import innopolis.model.generic.Mensaje;

@ManagedBean
@SessionScoped
public class SalasBean implements Serializable {

	private static final long serialVersionUID = 1L;

	private ManagerReservas manager;

	private Integer idRectipo;
	private String tiponom;
	private String descripcion;
	private String capacidad;
	private Colorrec color;
	private List<Sala> listado;
	private Integer idcolor;
	private String imagen;
	private String imgMost;
	private Integer rd;

	private UsuarioHelp session;

	// imagenes
	private UploadedFile file;
	private String g;

	public SalasBean() {
		session = SessionBean.verificarSession();
		imagen = "300.jpg";
		capacidad = "";
		imgMost = "300.jpg";
		manager = new ManagerReservas();
		color = new Colorrec();
	}

	/**
	 * @return the idcolor
	 */
	public Integer getIdcolor() {
		return idcolor;
	}

	/**
	 * @param idcolor
	 *            the idcolor to set
	 */
	public void setIdcolor(Integer idcolor) {
		this.idcolor = idcolor;
	}

	/**
	 * @return the color
	 */
	public Colorrec getColor() {
		return color;
	}

	/**
	 * @return the capacidad
	 */
	public String getCapacidad() {
		return capacidad;
	}

	/**
	 * @param capacidad
	 *            the capacidad to set
	 */
	public void setCapacidad(String capacidad) {
		this.capacidad = capacidad;
	}

	/**
	 * @param color
	 *            the color to set
	 */
	public void setColor(Colorrec color) {
		this.color = color;
	}

	public Integer getIdRectipo() {
		return idRectipo;
	}

	public void setIdRectipo(Integer idRectipo) {
		this.idRectipo = idRectipo;
	}

	/**
	 * @return the tiponom
	 */
	public String getTiponom() {
		return tiponom;
	}

	/**
	 * @param tiponom
	 *            the tiponom to set
	 */
	public void setTiponom(String tiponom) {
		this.tiponom = tiponom;
	}

	/**
	 * @return the imagen
	 */
	public String getImagen() {
		return imagen;
	}

	/**
	 * @param imagen
	 *            the imagen to set
	 */
	public void setImagen(String imagen) {
		this.imagen = imagen;
	}

	/**
	 * @return the imgMost
	 */
	public String getImgMost() {
		return imgMost;
	}

	/**
	 * @param imgMost
	 *            the imgMost to set
	 */
	public void setImgMost(String imgMost) {
		this.imgMost = imgMost;
	}

	/**
	 * @return the file
	 */
	public UploadedFile getFile() {
		return file;
	}

	/**
	 * @param file
	 *            the file to set
	 */
	public void setFile(UploadedFile file) {
		this.file = file;
	}

	public List<Sala> getListado() {
		listado = manager.findAllSalas();
		return listado;
	}

	public Integer getRd() {
		return rd;
	}

	public void setRd(Integer rd) {
		this.rd = rd;
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

	/**
	 * @return the descripcion
	 */
	public String getDescripcion() {
		return descripcion;
	}

	/**
	 * @param descripcion
	 *            the descripcion to set
	 */
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	/**
	 * @return the g
	 */
	public String getG() {
		return g;
	}

	/**
	 * @param g
	 *            the g to set
	 */
	public void setG(String g) {
		this.g = g;
	}

	public String crearNuevoRecursoTipo() {
		try {
			if (isNumeric(capacidad)) {
				manager.insertarSala(getTiponom().trim(), getDescripcion()
						.trim(), getImagen(), Integer.parseInt(getCapacidad()));
				tiponom = "";
				imagen = "300.jpg";
				idcolor = 0;
				capacidad = null;
				descripcion = "";
				g = "";
				rd = 1;
				color = new Colorrec();
				Mensaje.crearMensajeINFO("Sala creada correctamente");
			} else {
				Mensaje.crearMensajeINFO("La cantidad debe ser numérica");
			}
		} catch (Exception e) {
			Mensaje.crearMensajeINFO("Error al ingresar nueva sala");
			e.printStackTrace();
		}
		return "";
	}

	public static boolean isNumeric(String str) {
		return str.matches("[+-]?\\d*(\\.\\d+)?");
	}

	public String cargarDatosRecTipo(Sala sala) {
		idRectipo = sala.getIdSala();
		tiponom = sala.getTipo();
		capacidad = sala.getCapacidad().toString();
		descripcion = sala.getDescripcion();
		imagen = sala.getImagen();
		idcolor = sala.getColorsala().getIdColorsala();
		asignarNombreImagen();
		return "";
	}

	public void modificarSala() {
		try {
			if (isNumeric(capacidad)) {
				manager.editarSala(getIdRectipo(), getTiponom().trim(),
						getDescripcion().trim(), getImagen(), getIdcolor(),
						Integer.parseInt(getCapacidad()));
				Mensaje.crearMensajeINFO("Sala modificada correctamente");
			} else {
				Mensaje.crearMensajeINFO("La cantidad debe ser numérica");
			}
		} catch (Exception e) {
			Mensaje.crearMensajeINFO("Error al modificar la Sala");
			e.printStackTrace();
		}
	}

	public void cancelarModificacion() {
		setTiponom("");
		setIdRectipo(0);
		idcolor = null;
		setDescripcion("");
		setImagen("300.jpg");
		capacidad = null;
		Mensaje.crearMensajeINFO("Actualización cancelada");
	}

	// ////////////
	// metodo para mostrar los EventosTipos en Eventos
	public List<SelectItem> getListacolorid() {
		List<SelectItem> listadoSI = new ArrayList<SelectItem>();
		List<Colorsala> tipocolor = manager.findAllColorsala();
		for (Colorsala t : tipocolor) {
			SelectItem item = new SelectItem(t.getIdColorsala(), t.getColor());
			listadoSI.add(item);
		}
		return listadoSI;
	}

	// metodo para asignar el TipoEvento al Evento
	public String asignarTipocolor() {
		manager.asignarTipocolorsala(idcolor);
		return "";
	}

	// metodo para comprobar el alias
	public boolean ccolor(Integer id_color) {
		List<Sala> u = manager.findAllSalas();
		for (Sala us : u) {
			if (id_color.equals(us.getColorsala().getIdColorsala())) {
				return true;
			}
		}
		return false;
	}

	// metodo para guardar la imagen en el servidor
	public void ImagenServ(FileUploadEvent event) throws IOException {
		file = event.getFile();
		InputStream inputStream = null;
		OutputStream outputStream = null;
		if (file != null) {
			try {
				ServletContext servletContext = (ServletContext) FacesContext
						.getCurrentInstance().getExternalContext().getContext();
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
				Mensaje.crearMensajeINFO("Carga correcta");
			} catch (Exception e) {
				Mensaje.crearMensajeWARN("No se pudo subir la imagen");
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
			Mensaje.crearMensajeWARN("No se pudo seleccionar la imagen");
		}
	}

	// metodo para poner el nombre a la imagen
	public void asignarNombreImagen() {
		if (getTiponom().trim().isEmpty()) {
			System.out.println("Vacio");
		} else {
			DateFormat dateFormat = new SimpleDateFormat("_ddMMyyyyHHmm");
			g = "img_" + getTiponom() + dateFormat.format(new Date()) + ".jpg";
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

	// activar y desactivar
	public String cambiarEstado(Sala r) {
		try {
			String resultado = manager.cambioDisSala(r.getIdSala());
			Mensaje.crearMensajeINFO(resultado);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "";
	}

	// editar imagen
	public void verImagen(Sala sala) {
		setImagen(sala.getImagen());
	}
}
