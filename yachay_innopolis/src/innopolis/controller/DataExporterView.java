package innopolis.controller;

import innopolis.entities.Serviciosvirtregi;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
 
 
@ManagedBean
public class DataExporterView implements Serializable {
     
    private List<Serviciosvirtregi> serv;
    
         
    @ManagedProperty("#{serviciosvirtuales}")
    private ServiciosVirtualesBean servirt;
     
    @PostConstruct
    public void init() {
    	serv = servirt.getListRegServi();
    }    
    
    public List<Serviciosvirtregi> getServ() {
		return serv;
	}


	public void setServ(List<Serviciosvirtregi> serv) {
		this.serv = serv;
	}


	public ServiciosVirtualesBean getServirt() {
		return servirt;
	}


	public void setServirt(ServiciosVirtualesBean servirt) {
		this.servirt = servirt;
	}



}