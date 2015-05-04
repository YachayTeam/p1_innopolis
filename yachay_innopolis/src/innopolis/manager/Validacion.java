package innopolis.manager;

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

}
