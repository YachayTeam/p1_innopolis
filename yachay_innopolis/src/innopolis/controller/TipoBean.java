package innopolis.controller;

import innopolis.entidades.Actividad;
import innopolis.entidades.Inter;
import innopolis.entidades.Tipo;
import innopolis.entidades.help.UsuarioHelp;
import innopolis.manager.ManagerLogin;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;

@SessionScoped
@ManagedBean
public class TipoBean implements Serializable {
	private static final long serialVersionUID = 1L;
	private ManagerLogin manager;

	private Integer idTipo;
	private String descripcion;
	private String tipo;
	private Actividad actividad;

	// valores de almacenaniento de actividades
	private Integer add;
	private Integer del;
	private Integer t;
	private String res;

	private List<Inter> ocupados;
	private List<Actividad> o1;

	private Integer[] arrayTipoLogin;

	private UsuarioHelp session;

	public TipoBean() {
		session = SessionBean.verificarSession();
		manager = new ManagerLogin();
		ocupados = new ArrayList<Inter>();
		o1 = new ArrayList<Actividad>();
		res = "";
	}

	public Integer getAdd() {
		return add;
	}

	public List<Inter> getOcupados() {
		return ocupados;
	}

	public void setOcupados(List<Inter> ocupados) {
		this.ocupados = ocupados;
	}

	public Integer[] getArrayTipoLogin() {
		return arrayTipoLogin;
	}

	public String getRes() {
		return res;
	}

	public void setRes(String res) {
		this.res = res;
	}

	public UsuarioHelp getSession() {
		return session;
	}

	public void setSession(UsuarioHelp session) {
		this.session = session;
	}

	public void setArrayTipoLogin(Integer[] arrayTipoLogin) {
		this.arrayTipoLogin = arrayTipoLogin;
	}

	public Actividad getActividad() {
		return actividad;
	}

	public void setActividad(Actividad actividad) {
		this.actividad = actividad;
	}

	public void setAdd(Integer add) {
		this.add = add;
	}

	public Integer getDel() {
		return del;
	}

	public void setDel(Integer del) {
		this.del = del;
	}

	public Integer getIdTipo() {
		return idTipo;
	}

	public void setIdTipo(Integer idTipo) {
		this.idTipo = idTipo;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	public List<Tipo> getListTipos() {
		return manager.findAllTipo();
	}

	// metodo para listar los registros
	public List<Tipo> getListTipo() {
		List<Tipo> a = manager.findAllTipo();
		List<Tipo> l1 = new ArrayList<Tipo>();
		for (Tipo t : a) {
			if (t.getIdTipo() == 1 || t.getIdTipo() == 2) {
			} else {
				l1.add(t);
			}
		}
		return l1;
	}

	// accion para invocar el manager y crear evento
	public String crearTipo() {
		try {
			manager.insertarTipo(tipo, descripcion, arrayTipoLogin);
			tipo = "";
			descripcion = "";
			FacesContext context = FacesContext.getCurrentInstance();
			context.addMessage(null, new FacesMessage(
					"Registrado..!!! Tipo creado", null));
		} catch (Exception e) {
			FacesContext context = FacesContext.getCurrentInstance();
			context.addMessage(null, new FacesMessage(
					"Error..!!! Tipo no pudo ser creado", null));
			e.printStackTrace();
		}
		return "tipo";
	}

	// accion para cargar los datos en el formulario
	public String cargarTipo(Tipo u) {
		idTipo = u.getIdTipo();
		tipo = u.getTipo();
		descripcion = u.getDescripcion();
		ocupados = manager.reload(idTipo);
		t = u.getIdTipo();
		this.asignarTipo();
		return "";
	}

	// accion para llamar al manager
	public String actualizarTipo() {
		try {
			manager.editarTipo(idTipo, tipo, descripcion);
			tipo = "";
			descripcion = "";
			FacesContext context = FacesContext.getCurrentInstance();
			context.addMessage(null, new FacesMessage("Realizado..!!! Tipo modificado",null));
		} catch (Exception e) {
			FacesContext context = FacesContext.getCurrentInstance();
			context.addMessage(null, new FacesMessage("Error..!!! Tipo no pudo ser modificado",null));
			e.printStackTrace();
		}
		return "tipo";
	}

	// metodos utilizados en el actualizar las Actividades

	// metodos de asignacion y cambios de tipos
	public List<Actividad> deocupados() {
		List<Actividad> a = manager.findAllActividades();
		for (Inter e : ocupados) {
			for (Actividad t : a) {
				if (e.getActividad().getIdActividad() == t.getIdActividad()) {
					o1.add(t);
				}
			}
		}
		return o1;
	}

	// metodo para obetener lista <Actividad> desde lista <Ocupados>
	public List<Actividad> delibres() {
		List<Actividad> a = manager.findAllActividades();
		List<Actividad> l1 = new ArrayList<Actividad>();
		for (Actividad t : a) {
			int r = 0;
			for (Actividad e : o1) {
				if (e.getActividad().equals(t.getActividad())) {
					r += 1;
				}
			}
			if (r == 0) {
				l1.add(t);
			}
		}
		o1 = new ArrayList<Actividad>();
		return l1;
	}

	// metodo SelectItem de las Actividades Libres
	public List<SelectItem> getListlibre() {
		List<SelectItem> ls = new ArrayList<SelectItem>();
		List<Actividad> listado = delibres();
		for (Actividad t : listado) {
			SelectItem item = new SelectItem(t.getIdActividad(),
					t.getActividad());
			ls.add(item);
		}
		return ls;
	}

	// metodo SelectItem de las Actividades Ocupadas
	public List<SelectItem> getListocupado() {
		List<SelectItem> ls = new ArrayList<SelectItem>();
		List<Actividad> listadoEstadoU = deocupados();
		for (Actividad t : listadoEstadoU) {
			SelectItem item = new SelectItem(t.getIdActividad(),
					t.getActividad());
			ls.add(item);
		}
		return ls;
	}

	// metodo para asignar el Usuario al tipo
	public String asignarActil() {
		manager.asignarT(add);
		return "";
	}

	// metodo para asignar el Usuario al tipo
	public String asignarTipo() {
		manager.asignarTipo2(t);
		return "";
	}

	// metodo para asignar el Usuario al tipo
	public String asignarActiO() {
		manager.asignarT(del);
		return "";
	}

	public String add() {
		manager.mas();
		ocupados = manager.reload(getIdTipo());
		return "";
	}

	public String del() {
		manager.menos();
		ocupados = manager.reload(getIdTipo());
		return "";
	}

	// metodo para mostrar los Actividades
	public List<SelectItem> getListaActividades() {
		List<SelectItem> listadoTEU = new ArrayList<SelectItem>();
		List<Actividad> listadoEstadoU = manager.findAllActividades();
		for (Actividad t : listadoEstadoU) {
			SelectItem item = new SelectItem(t.getIdActividad(),
					t.getActividad());
			listadoTEU.add(item);
		}
		return listadoTEU;
	}

	public String irTipo2() {
		// limpiamos los datos
		tipo = "";
		descripcion = "";
		FacesContext.getCurrentInstance().addMessage(
				null,
				new FacesMessage(FacesMessage.SEVERITY_INFO,
						"Actualización cancelada", null));
		return "tipo";
	}

	// metodo para listar las actividades de cada rol
	public List<String> permisos(Tipo t) {
		List<String> v = new ArrayList<String>();
		if (t == null) {
		} else {

			List<Inter> i = manager.findAllInter();
			List<Inter> o = new ArrayList<Inter>();
			for (Inter p : i) {
				if (p.getTipo().getIdTipo().equals(t.getIdTipo())) {
					o.add(p);
				}
			}
			List<Actividad> la = manager.findAllActividades();
			for (Actividad a : la) {
				for (Inter g : o) {
					if (a.getIdActividad().equals(
							g.getActividad().getIdActividad())) {
						v.add(g.getActividad().getActividad());
					}
				}
			}
		}
		return v;
	}

}