package innopolis.entities;

import java.io.Serializable;
import javax.persistence.*;


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

	public Tipoestadousr getTipoestadousr() {
		return this.tipoestadousr;
	}

	public void setTipoestadousr(Tipoestadousr tipoestadousr) {
		this.tipoestadousr = tipoestadousr;
	}

}