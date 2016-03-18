package innopolis.entidades.help;

import innopolis.entidades.Recurso;
import innopolis.manager.ManagerReservas;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

@FacesConverter("recursoConverter")
public class RecursoConverter implements Converter{

	@Override
	public Object getAsObject(FacesContext arg0, UIComponent arg1, String valor) {
		if (valor != null && valor.trim().length() > 0) {
			ManagerReservas m = new ManagerReservas();
			try {
				return m.findRecursoByID(Integer.parseInt(valor));
			} catch (Exception e) {
				e.printStackTrace();
				return null;
			}
		}else{
			return null;
		}
	}

	@Override
	public String getAsString(FacesContext arg0, UIComponent arg1, Object object) {
		if(object != null)
			return ((Recurso) object).getIdRecurso()+"";
		else
			return null;
	}

	
}
