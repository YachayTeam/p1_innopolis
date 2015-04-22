package innopolis.entities;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the contadores database table.
 * 
 */
@Entity
@NamedQuery(name="Contadores.findAll", query="SELECT c FROM Contadores c")
public class Contadores implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="CONTADORES_IDCONTADOR_GENERATOR", sequenceName="SEQ_CONTADORES", allocationSize=1)
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="CONTADORES_IDCONTADOR_GENERATOR")
	@Column(name="id_contador")
	private Integer idContador;

	private String tipo;

	private Integer valor;

	public Contadores() {
	}

	public Integer getIdContador() {
		return this.idContador;
	}

	public void setIdContador(Integer idContador) {
		this.idContador = idContador;
	}

	public String getTipo() {
		return this.tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	public Integer getValor() {
		return this.valor;
	}

	public void setValor(Integer valor) {
		this.valor = valor;
	}

}