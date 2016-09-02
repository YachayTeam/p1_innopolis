package innopolis.entidades.help;

public class UsuarioHelp {
	private Integer idUsr;
	private String alias;
	private String apellido;
	private String correo;
	private String nombre;
	private String password;
	private String rol;
	private String cedula;
	private String direccion;
	private String telefono;
	private String celular;
	private boolean principal;
	private String empreestu;
	private String cargotitulo;
	
	public UsuarioHelp(Integer idUsr, String alias, String apellido,
			String correo,String direccion,String telefono,String celular, String nombre, String rol, String cedula,String password,boolean principal, String empresestu,String cargotitulo) {
		super();
		this.idUsr = idUsr;
		this.alias = alias;
		this.apellido = apellido;
		this.correo = correo;
		this.direccion = direccion;
		this.telefono = telefono;
		this.celular = celular;
		this.nombre = nombre;
		this.rol = rol;
		this.cedula = cedula;
		this.password = password;
		this.principal = principal;
		this.empreestu = empresestu;
		this.cargotitulo = cargotitulo;
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

	public String getDireccion() {
		return direccion;
	}

	public String getTelefono() {
		return telefono;
	}
	
	public String getCelular() {
		return celular;
	}
	
	public String getPassword() {
		return password;
	}
	
	public void setPassword(String password) {
		this.password = password;
	}
	
	public boolean isPrincipal() {
		return principal;
	}
	
	public void setPrincipal(boolean principal) {
		this.principal = principal;
	}
	
	public String getEmpreestu() {
		return empreestu;
	}
	
	public void setEmpreestu(String empreestu) {
		this.empreestu = empreestu;
	}
	
	public String getCargotitulo() {
		return cargotitulo;
	}
	
	public void setCargotitulo(String cargotitulo) {
		this.cargotitulo = cargotitulo;
	}
}
