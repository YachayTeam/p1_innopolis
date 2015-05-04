package innopolis.manager;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Pattern;

public class Validacion {
	
	//Validaciones Expresiones Regulares
	public static boolean isOnlyString(String cadena){
		//return Pattern.matches("[A-Za-z .!$'¡¿?íóúéá]+",cadena);
		return Pattern.matches("[A-Za-z ñíóúéáÑÁÉÍÓÚ]+",cadena);
	}
	
	public static boolean isOnlyStringDescription(String cadena){
		return Pattern.matches("[A-Za-z ñíóúéáÑÁÉÍÓÚ-:;,.[0-9]]+",cadena);
	}
	
	public static boolean isNumber(String cadena){
		return Pattern.matches("[0-9]+",cadena);
	}
	
	//FECHA MAYOR IGUAL Q HOY
	public static boolean fechaMayorIgual(Date fecha_propuesta){
		boolean resp = false;
		Date fecha_actual = new Date();
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		if ( fecha_propuesta.after(fecha_actual) || dateFormat.format(fecha_actual).equals(dateFormat.format(fecha_propuesta)) ){
			resp = true;
		}
		return resp;
	}

}
