package innopolis.entities;

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
	@SequenceGenerator(name="USUARIO_IDUSR_GENERATOR", sequenceName="SEQ_USUARIO", allocationSize=1)
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="USUARIO_IDUSR_GENERATOR")
	@Column(name="id_usr")
	private Integer idUsr;

	private String alias;

	private String apellido;

	private String correo;

	private String nombre;

	private String password;

	//bi-directional many-to-one association to Tipousr
	@OneToMany(mappedBy="usuario")
	private List<Tipousr> tipousrs;

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

	public String getCorreo() {
		return this.correo;
	}

	public void setCorreo(String correo) {
		this.correo = correo;
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

	public List<Tipousr> getTipousrs() {
		return this.tipousrs;
	}

	public void setTipousrs(List<Tipousr> tipousrs) {
		this.tipousrs = tipousrs;
	}

	public Tipousr addTipousr(Tipousr tipousr) {
		getTipousrs().add(tipousr);
		tipousr.setUsuario(this);

		return tipousr;
	}

	public Tipousr removeTipousr(Tipousr tipousr) {
		getTipousrs().remove(tipousr);
		tipousr.setUsuario(null);

		return tipousr;
	}

}