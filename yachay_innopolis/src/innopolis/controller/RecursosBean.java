package innopolis.controller;

import innopolis.entities.Recurso;
import innopolis.entities.Recursodisponible;
import innopolis.entities.Recursotipo;
import innopolis.manager.ManagerReservas;

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
public class RecursosBean implements Serializable{
	
	private static final long serialVersionUID = 1L;

	private ManagerReservas manager;
	
	private Integer idRecurso;

	private Integer capacidad;

	private String descripcion;

	private String imagen;

	private String lugar;

	private String nombre;
	
	private Integer rd;
	
	private Integer rt;
	
	public RecursosBean(){
		manager = new ManagerReservas();
		imagen = "";
	}
	
	public Integer getIdRecurso() {
		return idRecurso;
	}

	public void setIdRecurso(Integer idRecurso) {
		this.idRecurso = idRecurso;
	}

	public Integer getCapacidad() {
		return capacidad;
	}

	public void setCapacidad(Integer capacidad) {
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

	public List<Recurso> getListRegistro(){
		return manager.findAllRecurso();
	}

	//accion para invocar el manager y crear recurso
	public String crearRecurso(){
		if(getRt().equals(-1) || getRt().equals(0) || getRd()==null){
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN,"Seleccione tipo recurso",null));
		}else{
			try {
				manager.insertarRecurso(capacidad, descripcion, lugar, nombre, imagen);
				//reiniciamos datos (limpiamos el formulario)
				capacidad=0;descripcion="";lugar="";nombre="";imagen="";rd=1;rt=0;
				FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Registrado..!!!",  "Recurso Almacenado ") );
			} catch (Exception e) {
				FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,"Error al crear recurso",null));
				FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,e.getMessage(),null));
			};
		}
		return "";
	}
		
	//metodo para mostrar los RecursosTipos en Recursos
	public List<SelectItem> getListaRecTipo(){
		List<SelectItem> listadoSI=new ArrayList<SelectItem>();
		List<Recursotipo> listadoRecursos=manager.findAllTipoRecurso();
		
		for(Recursotipo t:listadoRecursos){
			SelectItem item=new SelectItem(t.getIdRectipo(),t.getTipo());
			listadoSI.add(item);
		}
		return listadoSI;
	}

	//metodo para asignar el RecursoTipo al Recurso
	public String asignarRecTipo(){
		manager.asignarRecursoTipo(rt);
		return "";
	}
	
	//metodo para mostrar los RecursosDisponibles en Recursos
	public List<SelectItem> getListaRecDisponibles(){
		List<SelectItem> listadoSI=new ArrayList<SelectItem>();
		List<Recursodisponible> listadoRecursos=manager.findAllRecursoDisponibles();
		
		for(Recursodisponible t:listadoRecursos){
			SelectItem item=new SelectItem(t.getIdRecdisponible(),t.getDisponible());
			listadoSI.add(item);
		}
		return listadoSI;
	}

	//metodo para asignar el RecursoDisponible al Recurso
	public String asignarRecDisponible(){
		manager.asignarRecursoDisponible(rd);
		return "";
	}
	
	//accion para cargar los datos en el formulario
	public String cargarRecursos(Recurso t){
		idRecurso=t.getIdRecurso();
		capacidad=t.getCapacidad();
		nombre= t.getNombre();
		lugar=t.getLugar();
		descripcion=t.getDescripcion();
		imagen=t.getImagen();
		rt=t.getRecursotipo().getIdRectipo();
		return "";
	}
	
	//accion para modificar los recursos
	public String actualizarRecurso(){
		manager.editarRecurso(idRecurso, capacidad, descripcion, lugar, nombre);
		//limpiamos los datos
		capacidad=0;descripcion="";lugar="";nombre="";imagen="";rd=1;rt=0;
		FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Actualizado..!!!",  "Recurso Actualizado ") );
		return "";
		
	}
	
	//activar y desactivar
	public String cambiarEstado(Recurso r){
		try {
			FacesContext context = FacesContext.getCurrentInstance();
	        context.addMessage(null, new FacesMessage("INFORMACION",manager.cambioDisRecurso(r.getIdRecurso())));
			
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		return "";
	}
	
	//------ traslados--------
	
	public String irRecurso(){
		FacesContext context = FacesContext.getCurrentInstance();
        context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Cancelado!", "Actualizacion Cancelada"));
      //limpiamos los datos
		capacidad=0;
		descripcion="";
		lugar="";
		nombre="";
		imagen="";
		rd=1;
		rt=0;
		return "";					
	}
	
	public String irRec(){
		return "recurso";
	}
	
	public String irTrecurso(){
		return "rectipo";
	}
				
			
	
}
