package entity;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "USUARIO")
public class Usuario {

	@Id
	@Column(name = "email")
	private String email;

	@Column(name = "nome")
	private String nome;

	@Column(name = "senha")
	private String senha;
	
	@Column (name = "areaprofissional")
	private String areaProfissional;

	@OneToMany(mappedBy = "usuario", cascade = CascadeType.ALL)
	private List<Telefone> telefones;

	public Usuario() {

	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getSenha() {
		return senha;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}

	public List<Telefone> getTelefones() {
		return telefones;
	}

	public void setTelefones(List<Telefone> telefones) {
		this.telefones = telefones;
	}
	

	public String getAreaProfissional() {
		return areaProfissional;
	}

	public void setAreaProfissional(String areaProfissional) {
		this.areaProfissional = areaProfissional;
	}

	@Override
	public String toString() {
		return "Usuario [email=" + email + ", nome=" + nome + ", senha=" + senha + ", areaProfissional="
				+ areaProfissional + ", telefones=" + telefones + "]";
	}


}
