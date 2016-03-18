package innopolis.manager;

import java.sql.Time;
import java.sql.Timestamp;
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
	
	//FECHA IGUAL PARA COMPARAR HORAS
	public static boolean fechaIgualActual(Date fecha_propuesta){
		boolean resp = false;
		Date fecha_actual = new Date();
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		if (dateFormat.format(fecha_actual).equals(dateFormat.format(fecha_propuesta))){
			resp = true;
		}
		return resp;
	}
	
	@SuppressWarnings("deprecation")
	public static Time fechaAtiempo(Date fecha){
		DateFormat dateFormatH = new SimpleDateFormat("HH:mm");
		String hora = dateFormatH.format(fecha).toString();
		String [] array = hora.split(":");
		Time resp = new Time(Integer.parseInt(array[0]), Integer.parseInt(array[1]), 00);
		return resp;	
	}
	
	//HORA MAYOR IGUAL QUE HORA ACTUAL
	public static boolean horaMayorIgual(Time hora){
		boolean resp = false;
		Date fecha_actual = new Date();
		Time tiempo_actual = fechaAtiempo(fecha_actual); 
		System.out.println(hora.getTime());
		System.out.println(tiempo_actual.getTime());
		if(hora.getTime()>=tiempo_actual.getTime()){
			resp = true;
		}
		return resp;
	}
	
	//Fecha igual
	public static boolean fechaIgual(Date fecha_actual, Date fecha_propuesta){
		boolean resp = false;
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		if ( dateFormat.format(fecha_actual).equals(dateFormat.format(fecha_propuesta)) ){
			resp = true;
		}
		return resp;
	}
	
	//Hora entre 2 horas
	public static boolean horaEntreDos(Timestamp hora, Timestamp horaInicio, Timestamp HoraFin){
		boolean resp = false;
		if(hora.getTime()>=horaInicio.getTime() && hora.getTime()<=HoraFin.getTime()){
			resp = true;
		}
		return resp;
	}
	
	//Fecha entre 2 fechas
	public static boolean fechaEntreDos(Timestamp fecha, Timestamp fechaInicio, Timestamp fechaFin){
		boolean resp = false;
		if(fecha.getTime()>=fechaInicio.getTime() && fecha.getTime()<=fechaFin.getTime()){
			resp = true;
		}
		return resp;
	}

}
