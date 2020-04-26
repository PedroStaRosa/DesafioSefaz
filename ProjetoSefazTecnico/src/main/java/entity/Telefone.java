package entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "TELEFONE")
public class Telefone {

	@Id
	@Column(name = "id")
	@GeneratedValue
	private long id;

	@Column(name = "numero")
	private String numero;

	@Column(name = "tipo")
	private String tipo;

	@Column(name = "ddd")
	private int ddd;

	@ManyToOne
	@JoinColumn(name = "email_usuario_FK", referencedColumnName = "email", nullable = false)
	private Usuario usuario;

	public Telefone() {

	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getNumero() {
		return numero;
	}

	public void setNumero(String numero) {
		this.numero = numero;
	}

	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	public int getDdd() {
		return ddd;
	}

	public void setDdd(int ddd) {
		this.ddd = ddd;
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

}
