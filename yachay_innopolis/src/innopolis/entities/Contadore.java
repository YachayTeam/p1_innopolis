package innopolis.entities;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the contadores database table.
 * 
 */
@Entity
@Table(name="contadores")
@NamedQuery(name="Contadore.findAll", query="SELECT c FROM Contadore c")
public class Contadore implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="CONTADORES_IDCONTADOR_GENERATOR", sequenceName="SEQ_CONTADORES", allocationSize=1)
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="CONTADORES_IDCONTADOR_GENERATOR")
	@Column(name="id_contador")
	private Integer idContador;

	private String tipo;

	private Integer valor;

	public Contadore() {
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