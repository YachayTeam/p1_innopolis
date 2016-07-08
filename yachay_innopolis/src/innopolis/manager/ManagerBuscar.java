package innopolis.manager;

import innopolis.entidades.RegeceParametro;
import innopolis.model.generic.ConsumeREST;

import javax.ejb.Stateless;

import org.json.simple.JSONObject;

@Stateless
public class ManagerBuscar {

	private ManagerDAO mDAO;

	public ManagerBuscar() {
		mDAO = new ManagerDAO();
	}

	/**
	 * Método para enviar mensaje por WS
	 * 
	 * @param para
	 * @param asunto
	 * @param body
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public void envioMailWS(String para, String asunto, String body)
			throws Exception {
		RegeceParametro param = parametroByID("envio_mail");
		RegeceParametro idWS = parametroByID("id_ws_mail");
		JSONObject objSalida = new JSONObject();
		objSalida.put("id", idWS.getParValor());
		objSalida.put("para", para);
		objSalida.put("asunto", asunto);
		objSalida.put("body", body);
		System.out.println("Envio Mail ---> " + objSalida);
		String url = param.getParValor();
		JSONObject respuesta = ConsumeREST.postClientRestEasy(url, objSalida);
		if (!respuesta.get("respuesta").equals("OK"))
			throw new Exception("Error al enviar el correo. (WS)");
	}

	/**
	 * buscar los vehuculos por ID
	 * 
	 * @param vehi_id
	 * @throws Exception
	 */
	public RegeceParametro parametroByID(String parametro) throws Exception {
		return (RegeceParametro) mDAO.findById(RegeceParametro.class, parametro);
	}

}
