package innopolis.entities;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;


/**
 * The persistent class for the tipoestadousr database table.
 * 
 */
@Entity
@NamedQuery(name="Tipoestadousr.findAll", query="SELECT t FROM Tipoestadousr t")
public class Tipoestadousr implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="TIPOESTADOUSR_IDTIPOESTADOUSR_GENERATOR", sequenceName="SEQ_TIPOESTADOUSR", allocationSize=1)
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="TIPOESTADOUSR_IDTIPOESTADOUSR_GENERATOR")
	@Column(name="id_tipoestadousr")
	private Integer idTipoestadousr;

	private String nombreestado;

	//bi-directional many-to-one association to Usuario
	@OneToMany(mappedBy="tipoestadousr")
	private List<Usuario> usuarios;

	public Tipoestadousr() {
	}

	public Integer getIdTipoestadousr() {
		return this.idTipoestadousr;
	}

	public void setIdTipoestadousr(Integer idTipoestadousr) {
		this.idTipoestadousr = idTipoestadousr;
	}

	public String getNombreestado() {
		return this.nombreestado;
	}

	public void setNombreestado(String nombreestado) {
		this.nombreestado = nombreestado;
	}

	public List<Usuario> getUsuarios() {
		return this.usuarios;
	}

	public void setUsuarios(List<Usuario> usuarios) {
		this.usuarios = usuarios;
	}

	public Usuario addUsuario(Usuario usuario) {
		getUsuarios().add(usuario);
		usuario.setTipoestadousr(this);

		return usuario;
	}

	public Usuario removeUsuario(Usuario usuario) {
		getUsuarios().remove(usuario);
		usuario.setTipoestadousr(null);

		return usuario;
	}

}