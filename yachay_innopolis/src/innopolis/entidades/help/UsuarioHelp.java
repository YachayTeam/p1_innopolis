package innopolis.entidades.help;

public class UsuarioHelp {
	private Integer idUsr;
	private String alias;
	private String apellido;
	private String correo;
	private String nombre;
	private String rol;
	private String cedula;
	
	public UsuarioHelp(Integer idUsr, String alias, String apellido,
			String correo, String nombre, String rol, String cedula) {
		super();
		this.idUsr = idUsr;
		this.alias = alias;
		this.apellido = apellido;
		this.correo = correo;
		this.nombre = nombre;
		this.rol = rol;
		this.cedula = cedula;
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

	public String getRol() {
		return rol;
	}

	public String getCedula() {
		return cedula;
	}

	
	
	
}
