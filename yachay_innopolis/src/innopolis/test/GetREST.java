package innopolis.test;

import innopolis.manager.ManagerLogin;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;

public class GetREST {
	public static void main(String[] args) {
		 
		String url = "http://10.2.2.80/prueba/endpoint_services/user.json";
		  
		try {
			String getJSON = getRest(url);
			System.out.println(getJSON);
			//LEER JSON
			System.out.println("--------------------------------------------------LEER---------------------------------------------------");
			JSONArray array=(JSONArray) JSONValue.parse(getJSON);
			//SIZE -1
			System.out.println("Tamaño:"+array.size());
			//OBJETO DEL ARRAY
			JSONObject obj=(JSONObject)array.get(2);
			System.out.println(obj.get("name"));
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		ManagerLogin m = new ManagerLogin();
		System.out.println(m.getMD5("admin.12345"));
	 
	}
	
	public static String getRest(String url) throws Exception{
		String resp = "";
		
		try {
			 
			URL url_serv = new URL(url);
			HttpURLConnection conn = (HttpURLConnection) url_serv.openConnection();
			conn.setRequestMethod("GET");
			conn.setRequestProperty("Accept", "application/json");
	 
			if (conn.getResponseCode() != 200) {
				throw new RuntimeException("Failed: HTTP error: "+ conn.getResponseCode());
			}
	 
			BufferedReader br = new BufferedReader(new InputStreamReader(
				(conn.getInputStream())));
	 
			String output;
			while ((output = br.readLine()) != null) {
				resp += output;
			}
	 
			conn.disconnect();
	 
		} catch (MalformedURLException e) {
			throw new RuntimeException("MalformedURLException: "+ e.getMessage());
		} catch (IOException e) {
			throw new RuntimeException("IOException: "+ e.getMessage());
		}
		
		return resp;
	}
}
