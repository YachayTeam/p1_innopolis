package innopolis.entidades;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;


/**
 * The persistent class for the tipo database table.
 * 
 */
@Entity
@NamedQuery(name="Tipo.findAll", query="SELECT t FROM Tipo t")
public class Tipo implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="TIPO_IDTIPO_GENERATOR", sequenceName="SEQ_TIPO", allocationSize=1)
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="TIPO_IDTIPO_GENERATOR")
	@Column(name="id_tipo")
	private Integer idTipo;

	private String descripcion;

	private String tipo;

	//bi-directional many-to-one association to Inter
	@OneToMany(mappedBy="tipo")
	private List<Inter> inters;

	//bi-directional many-to-one association to Usuario
	@OneToMany(mappedBy="tipo")
	private List<Usuario> usuarios;

	public Tipo() {
	}

	public Integer getIdTipo() {
		return this.idTipo;
	}

	public void setIdTipo(Integer idTipo) {
		this.idTipo = idTipo;
	}

	public String getDescripcion() {
		return this.descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public String getTipo() {
		return this.tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	public List<Inter> getInters() {
		return this.inters;
	}

	public void setInters(List<Inter> inters) {
		this.inters = inters;
	}

	public Inter addInter(Inter inter) {
		getInters().add(inter);
		inter.setTipo(this);

		return inter;
	}

	public Inter removeInter(Inter inter) {
		getInters().remove(inter);
		inter.setTipo(null);

		return inter;
	}

	public List<Usuario> getUsuarios() {
		return this.usuarios;
	}

	public void setUsuarios(List<Usuario> usuarios) {
		this.usuarios = usuarios;
	}

	public Usuario addUsuario(Usuario usuario) {
		getUsuarios().add(usuario);
		usuario.setTipo(this);

		return usuario;
	}

	public Usuario removeUsuario(Usuario usuario) {
		getUsuarios().remove(usuario);
		usuario.setTipo(null);

		return usuario;
	}

}