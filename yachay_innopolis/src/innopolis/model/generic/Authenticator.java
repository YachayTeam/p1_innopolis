package innopolis.model.generic;

import javax.mail.PasswordAuthentication;

public class Authenticator extends javax.mail.Authenticator {
	private PasswordAuthentication authentication;

	public Authenticator() {
		String username = "admin.innopolis";
		String password = "Andres151210";
		authentication = new PasswordAuthentication(username, password);
	}

	protected PasswordAuthentication getPasswordAuthentication() {
		return authentication;
	}
}
