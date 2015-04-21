package innopolis.controller;

import java.util.ArrayList;
import java.util.List;

import innopolis.entities.Recurso;
import innopolis.entities.Serviciosvirtregi;
import innopolis.entities.Tipoestado;
import innopolis.entities.Tiposervicio;
import innopolis.manager.ManagerRecursosVirtuales;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;


@SessionScoped
@ManagedBean
public class ServiciosVirtualesBean {
	private ManagerRecursosVirtuales managerservirt;
	private Integer idSvr;
	private Tipoestado tipoestado;
	private Tiposervicio tiposervicio;
	private String apellidos;
	private int cedula;
	private String correo;
	private String nombres;
	private String tema;
	private List<Serviciosvirtregi> liservicioreg;
	private List<Tiposervicio> tiposervli;
	private List<Tipoestado> tipoestli;
	private Tipoestado testado;
	private Tiposervicio tservicio;
	
	
	public Tipoestado getTestado() {
		return testado;
	}

	public void setTestado(Tipoestado testado) {
		this.testado = testado;
	}

	public Tiposervicio getTservicio() {
		return tservicio;
	}

	public void setTservicio(Tiposervicio tservicio) {
		this.tservicio = tservicio;
	}
	
	public ServiciosVirtualesBean()
	{
		managerservirt = new ManagerRecursosVirtuales();
		
	}
		
	public List<Serviciosvirtregi> getServicioreg() {
		return liservicioreg;
	}
	public void setServicioreg(List<Serviciosvirtregi> liservicioreg) {
		this.liservicioreg = liservicioreg;
	}		
	public Integer getIdSvr() {
		return idSvr;
	}
	public void setIdSvr(Integer idSvr) {
		this.idSvr = idSvr;
	}

	public String getApellidos() {
		return apellidos;
	}
	public void setApellidos(String apellidos) {
		this.apellidos = apellidos;
	}
	public int getCedula() {
		return cedula;
	}
	public void setCedula(int cedula) {
		this.cedula = cedula;
	}
	public String getCorreo() {
		return correo;
	}
	public void setCorreo(String correo) {
		this.correo = correo;
	}
	public String getNombres() {
		return nombres;
	}
	public void setNombres(String nombres) {
		this.nombres = nombres;
	}
	public String getTema() {
		return tema;
	}
	public void setTema(String tema) {
		this.tema = tema;
	}	
	public List<Tiposervicio> getTiposervli() {
		return tiposervli;
	}
	public void setTiposervli(List<Tiposervicio> tiposervli) {
		this.tiposervli = tiposervli;
	}
	public List<Tipoestado> getTipoestli() {
		return tipoestli;
	}
	public void setTipoestli(List<Tipoestado> tipoestli) {
		this.tipoestli = tipoestli;
	}
	
	
	
	public Tipoestado getTipoestado() {
		return tipoestado;
	}

	public void setTipoestado(Tipoestado tipoestado) {
		this.tipoestado = tipoestado;
	}

	public Tiposervicio getTiposervicio() {
		return tiposervicio;
	}

	public void setTiposervicio(Tiposervicio tiposervicio) {
		this.tiposervicio = tiposervicio;
	}

	//metodo para asignar el TipoServicio al registro
	public String asignarTiposerv(){
		managerservirt.asignarTiposerv(tiposervicio.getIdTp());	
			return "";
		}
	//metodo para asignar el Tipoestado al registro
	public String asignarTipoest(){
			managerservirt.asignarTipoest(tipoestado.getIdEstado());
			return "";
		}
	public List<Serviciosvirtregi> getListRegServi(){
			return managerservirt.findAllRServiciosVirtuales();
		}
	//accion para invocar el manager y crear registro
	public String crearRegistroServ(){
		try {
			managerservirt.insertarserviciovirtual(cedula, nombres, apellidos, tema, correo);
			//reiniciamos datos (limpiamos el formulario)
			cedula=0;
			nombres="";
			correo="";
			tema="";
			apellidos="";
			tipoestado = managerservirt.findEstadoTipoByID(1); // numero id del estado q quieres q sea
			tiposervicio = null;
			idSvr=null;
			FacesContext context = FacesContext.getCurrentInstance();
	        context.addMessage(null, new FacesMessage("Registrado..!!!",  "Registro Almacenado ") );
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		};
		return "Serviciovirual";
	}
	//accion para modificar los recursos
	public String actualizarRegistro(){
		managerservirt.editarserviciovirtual(idSvr, new Integer(cedula), nombres, apellidos, tema, correo, tipoestado.getIdEstado(), tiposervicio.getIdTp());
		//limpiamos los datos
		cedula=0;
		nombres="";
		correo="";
		tema="";
		apellidos="";
		try {
			tipoestado = managerservirt.findEstadoTipoByID(1);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} // numero id del estado q quieres q sea
		tiposervicio = null;
		idSvr=null;
		FacesContext context = FacesContext.getCurrentInstance();
        context.addMessage(null, new FacesMessage("Actualizado..!!!",  "Registro Actualizado ") );
		return "AprovadorServiciovirtual";
		
	}

		public String Cargarregistros(Serviciosvirtregi serv)
		{
		idSvr= serv.getIdSvr();
		cedula= serv.getCedula();
		nombres= serv.getNombres();
		apellidos= serv.getApellidos();
		correo= serv.getCorreo();
		tema= serv.getTema();
		tipoestado= serv.getTipoestado();
		tiposervicio= serv.getTiposervicio();
		return "modservedi";
				
		}
		//metodo para mostrar los TiposServicio
		public List<SelectItem> getListaTiposerv(){
				List<SelectItem> listadoTS=new ArrayList<SelectItem>();
				List<Tiposervicio> listadoServicio=managerservirt.findAllTipoServicio();
				
				for(Tiposervicio t:listadoServicio){
					SelectItem item=new SelectItem(t.getIdTp(),t.getNombreServicio());
					listadoTS.add(item);
				}
				return listadoTS;
			}
		//metodo para mostrar los TiposEstado
				public List<SelectItem> getListaTipoestado(){
						List<SelectItem> listadoTE=new ArrayList<SelectItem>();
						List<Tipoestado> listadoEstado=managerservirt.findAllTipoEstado();
						
						for(Tipoestado t:listadoEstado){
							SelectItem item=new SelectItem(t.getIdEstado(),t.getNombreestado());
							listadoTE.add(item);
						}
						return listadoTE;
					}
				
				public String cambiarServicio(Serviciosvirtregi ser){
					try {
						FacesContext context = FacesContext.getCurrentInstance();
				        context.addMessage(null, new FacesMessage("INFORMACION",managerservirt.cambioDisServicio(ser.getIdSvr())));
						
					} catch (Exception e) {
						System.out.println(e.getMessage());
					}
					return "";
				}
		//------ traslados--------
				
				public String irRecurso(){
					FacesContext context = FacesContext.getCurrentInstance();
			        context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Cancelado!", "Actualizacion Cancelada"));
			      //limpiamos los datos
			        cedula=0;
					nombres="";
					correo="";
					tema="";
					apellidos="";
					try {
						tipoestado = managerservirt.findEstadoTipoByID(1);
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} // numero id del estado q quieres q sea;
					tiposervicio = new Tiposervicio();
					idSvr=0;
					return "AprovadorServiciovirtual";					
				}
		}