package innopolis.entidades;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;


/**
 * The persistent class for the usuario database table.
 * 
 */
@Entity
@NamedQuery(name="Usuario.findAll", query="SELECT u FROM Usuario u")
public class Usuario implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="USUARIO_IDUSR_GENERATOR" )
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="USUARIO_IDUSR_GENERATOR")
	@Column(name="id_usr")
	private Integer idUsr;

	private String alias;

	private String apellido;

	private String cargotitulo;

	private String cedula;

	private String correo;
	
	private String direccion;
	
	private String telefono;
	
	private String celular;

	private String empresestdu;

	private String interes;

	private String nombre;

	private String password;

	private Boolean principal;

	private String sms;

	//bi-directional many-to-one association to Evento
	@OneToMany(mappedBy="usuario")
	private List<Evento> eventos;

	//bi-directional many-to-one association to Interesesmid
	@OneToMany(mappedBy="usuario")
	private List<Interesesmid> interesesmids;

	//bi-directional many-to-one association to Serviciosvirtregi
	@OneToMany(mappedBy="usuario")
	private List<Serviciosvirtregi> serviciosvirtregis;

	//bi-directional many-to-one association to Sugerencia
	@OneToMany(mappedBy="usuario")
	private List<Sugerencia> sugerencias;

	//bi-directional many-to-one association to Tipo
	@ManyToOne
	@JoinColumn(name="id_tipo")
	private Tipo tipo;

	//bi-directional many-to-one association to Tipoestadousr
	@ManyToOne
	@JoinColumn(name="id_tipoestadousr")
	private Tipoestadousr tipoestadousr;

	public Usuario() {
	}

	public Integer getIdUsr() {
		return this.idUsr;
	}

	public void setIdUsr(Integer idUsr) {
		this.idUsr = idUsr;
	}

	public String getAlias() {
		return this.alias;
	}

	public void setAlias(String alias) {
		this.alias = alias;
	}

	public String getApellido() {
		return this.apellido;
	}

	public void setApellido(String apellido) {
		this.apellido = apellido;
	}

	public String getCargotitulo() {
		return this.cargotitulo;
	}

	public void setCargotitulo(String cargotitulo) {
		this.cargotitulo = cargotitulo;
	}

	public String getCedula() {
		return this.cedula;
	}

	public void setCedula(String cedula) {
		this.cedula = cedula;
	}

	public String getCorreo() {
		return this.correo;
	}

	public void setCorreo(String correo) {
		this.correo = correo;
	}

	public String getEmpresestdu() {
		return this.empresestdu;
	}

	public void setEmpresestdu(String empresestdu) {
		this.empresestdu = empresestdu;
	}

	public String getInteres() {
		return this.interes;
	}

	public void setInteres(String interes) {
		this.interes = interes;
	}

	public String getNombre() {
		return this.nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getPassword() {
		return this.password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Boolean getPrincipal() {
		return this.principal;
	}

	public void setPrincipal(Boolean principal) {
		this.principal = principal;
	}

	public String getSms() {
		return this.sms;
	}

	public void setSms(String sms) {
		this.sms = sms;
	}

	public List<Evento> getEventos() {
		return this.eventos;
	}

	public void setEventos(List<Evento> eventos) {
		this.eventos = eventos;
	}

	public Evento addEvento(Evento evento) {
		getEventos().add(evento);
		evento.setUsuario(this);

		return evento;
	}

	public Evento removeEvento(Evento evento) {
		getEventos().remove(evento);
		evento.setUsuario(null);

		return evento;
	}
	
	public String getDireccion() {
		return direccion;
	}
	
	public void setDireccion(String direccion) {
		this.direccion = direccion;
	}
	
	public String getTelefono() {
		return telefono;
	}
	
	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}
	
	public String getCelular() {
		return celular;
	}
	
	public void setCelular(String celular) {
		this.celular = celular;
	}

	public List<Interesesmid> getInteresesmids() {
		return this.interesesmids;
	}

	public void setInteresesmids(List<Interesesmid> interesesmids) {
		this.interesesmids = interesesmids;
	}

	public Interesesmid addInteresesmid(Interesesmid interesesmid) {
		getInteresesmids().add(interesesmid);
		interesesmid.setUsuario(this);

		return interesesmid;
	}

	public Interesesmid removeInteresesmid(Interesesmid interesesmid) {
		getInteresesmids().remove(interesesmid);
		interesesmid.setUsuario(null);

		return interesesmid;
	}

	public List<Serviciosvirtregi> getServiciosvirtregis() {
		return this.serviciosvirtregis;
	}

	public void setServiciosvirtregis(List<Serviciosvirtregi> serviciosvirtregis) {
		this.serviciosvirtregis = serviciosvirtregis;
	}

	public Serviciosvirtregi addServiciosvirtregi(Serviciosvirtregi serviciosvirtregi) {
		getServiciosvirtregis().add(serviciosvirtregi);
		serviciosvirtregi.setUsuario(this);

		return serviciosvirtregi;
	}

	public Serviciosvirtregi removeServiciosvirtregi(Serviciosvirtregi serviciosvirtregi) {
		getServiciosvirtregis().remove(serviciosvirtregi);
		serviciosvirtregi.setUsuario(null);

		return serviciosvirtregi;
	}

	public List<Sugerencia> getSugerencias() {
		return this.sugerencias;
	}

	public void setSugerencias(List<Sugerencia> sugerencias) {
		this.sugerencias = sugerencias;
	}

	public Sugerencia addSugerencia(Sugerencia sugerencia) {
		getSugerencias().add(sugerencia);
		sugerencia.setUsuario(this);

		return sugerencia;
	}

	public Sugerencia removeSugerencia(Sugerencia sugerencia) {
		getSugerencias().remove(sugerencia);
		sugerencia.setUsuario(null);

		return sugerencia;
	}

	public Tipo getTipo() {
		return this.tipo;
	}

	public void setTipo(Tipo tipo) {
		this.tipo = tipo;
	}

	public Tipoestadousr getTipoestadousr() {
		return this.tipoestadousr;
	}

	public void setTipoestadousr(Tipoestadousr tipoestadousr) {
		this.tipoestadousr = tipoestadousr;
	}

}