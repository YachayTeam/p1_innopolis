package innopolis.entities.help;

public class UsuarioHelp {
	private Integer idUsr;
	private String alias;
	private String apellido;
	private String correo;
	private String nombre;
	private String password;
	private String rol;
	
	public UsuarioHelp(Integer idUsr, String alias, String apellido,
			String correo, String nombre, String password, String rol) {
		super();
		this.idUsr = idUsr;
		this.alias = alias;
		this.apellido = apellido;
		this.correo = correo;
		this.nombre = nombre;
		this.password = password;
		this.rol = rol;
	}

	public Integer getIdUsr() {
		return idUsr;
	}

	public String getAlias() {
		return alias;
	}

	public String getApellido() {
		return apellido;
	}

	public String getCorreo() {
		return correo;
	}

	public String getNombre() {
		return nombre;
	}

	public String getPassword() {
		return password;
	}

	public String getRol() {
		return rol;
	}
	
}
