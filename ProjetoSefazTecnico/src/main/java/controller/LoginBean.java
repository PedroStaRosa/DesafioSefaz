package controller;

import java.io.IOException;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;

import dao.UsuarioDAO;
import dao.UsuarioDAOImpl;
import entity.Usuario;
import util.JpaUtil;

@ManagedBean(name = "LoginBean")
@RequestScoped
public class LoginBean {
	// USUARIO PADRAO PARA INICIO DA APLICAÇÃO
	final private String usuarioAdm = "admin";
	final private String senhaAdm = "admin";

	// RECEBE VALORES DE ACESSO DA LOGIN.XHTML
	private String usuarioInput, senhaInput;
	private UsuarioDAO usuarioDao;
	private String mensagem;

	private static final String PESQUISAR = "carregarUsuarios.xhtml";

	public LoginBean() {
		this.usuarioDao = new UsuarioDAOImpl();
	}

	public void entrar() {

		try {
			if (this.usuarioInput.equals(this.usuarioAdm) && this.senhaInput.equals(this.senhaAdm)) {
				FacesContext.getCurrentInstance().getExternalContext().redirect(PESQUISAR);
			} else {
				Usuario usuarioPesquisa = this.usuarioDao.pesquisar(this.usuarioInput);
				if (usuarioPesquisa != null) {
					if (usuarioPesquisa.getSenha().equals(this.senhaInput)) {
						FacesContext.getCurrentInstance().getExternalContext().redirect(PESQUISAR);
					} else {
						this.mensagem = "Usuario ou Senha inválida."; // SERÁ DISPARADA CASO ENCONTRE UM USUÁRIO VALIDO
																		// COM SENHA INCORRETA.
					}
				} else {
					this.mensagem = "Usuario ou Senha inválida.";// SERÁ DISPARADA CASO NÃO ENCONTRE UM USUÁRIO VALIDO.
				}

			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	// GETTERS AND SETTERS, CONSTRUCT
	public String getUsuarioInput() {
		return usuarioInput;
	}

	public void setUsuarioInput(String usuarioInput) {
		this.usuarioInput = usuarioInput;
	}

	public String getSenhaInput() {
		return senhaInput;
	}

	public void setSenhaInput(String senhaInput) {
		this.senhaInput = senhaInput;
	}

	public String getMensagem() {
		return mensagem;
	}

	public void setMensagem(String mensagem) {
		this.mensagem = mensagem;
	}

}
