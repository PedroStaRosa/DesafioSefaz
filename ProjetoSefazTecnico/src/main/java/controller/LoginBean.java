package controller;

import java.io.IOException;
import java.util.ArrayList;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;

import org.primefaces.PrimeFaces;

import dao.UsuarioDAO;
import dao.UsuarioDAOImpl;
import entity.Telefone;
import entity.Usuario;

@ManagedBean(name = "loginBean")
@RequestScoped
public class LoginBean {
	

	// RECEBE VALORES DE ACESSO DA LOGIN.XHTML
	private String usuarioInput, senhaInput;
	private UsuarioDAO usuarioDao;
	private Usuario usuarioCadastro;
	private Telefone telefoneUsuarioCadastro;
	private String mensagem;

	private static final String PAINEL_ADM = "paginas/adm/painelAdm.xhtml";
	private static final String PAINEL_USER = "paginas/user/inicio.xhtml";
	// private static final String LOGIN = "login.xhtml";

	public LoginBean() {
		this.usuarioDao = new UsuarioDAOImpl();
		usuarioCadastro = new Usuario();
		this.usuarioCadastro.setTelefones(new ArrayList<Telefone>());
		telefoneUsuarioCadastro = new Telefone();
	}

	public void entrar() {

		try {
			Usuario usuarioPesquisa = this.usuarioDao.pesquisar(this.usuarioInput);
			if (usuarioPesquisa != null) {
				if (usuarioPesquisa.getSenha().equals(this.senhaInput)) {

					FacesContext facesContext = FacesContext.getCurrentInstance();
					facesContext.getExternalContext().getSessionMap().put("usuarioLogado", usuarioPesquisa);
					if (usuarioPesquisa.getEmail().equals("admin@admin.com")) {
						facesContext.getExternalContext().redirect(PAINEL_ADM);
					} else {
						facesContext.getExternalContext().redirect(PAINEL_USER);
					}

				} else {
					addMessageError("Usuario ou Senha inválida.");
					/*
					 * SERÁ DISPARADA CASO ENCONTRE UM USUÁRIO VALIDO COM SENHA INCORRETA.
					 */
				}
			} else {
				addMessageError("Usuario ou Senha inválida.");
				/*
				 * SERÁ DISPARADA CASO NÃO ENCONTRE UM USUÁRIO VALIDO.
				 */
			}

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void cadastrarNovoUsuario() {
		Usuario usuarioVerificarEmail = this.usuarioDao.pesquisar(this.usuarioCadastro.getEmail());
		if (usuarioVerificarEmail == null) {
			this.telefoneUsuarioCadastro.setUsuario(this.usuarioCadastro);
			this.usuarioCadastro.getTelefones().add(telefoneUsuarioCadastro);

			if (this.usuarioDao.salvar(this.usuarioCadastro)) {
				addMessage("Usuário cadastrado com Sucesso.");
			} else {

				addMessageError("Usuário cadastrado com Sucesso.");
			}
		} else {
			addMessageError("Email já cadastrado.");
		}
	}

	// ADICIONA MENSAGENS

	public void addMessage(String summary) {
		FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, summary, null);
		FacesContext.getCurrentInstance().addMessage(null, message);
	}

	public void addMessageError(String titulo) {

		FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, titulo, null);
		FacesContext.getCurrentInstance().addMessage(null, message);

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

	public Usuario getUsuarioCadastro() {
		return usuarioCadastro;
	}

	public void setUsuarioCadastro(Usuario usuarioCadastro) {
		this.usuarioCadastro = usuarioCadastro;
	}

	public Telefone getTelefoneUsuarioCadastro() {
		return telefoneUsuarioCadastro;
	}

	public void setTelefoneUsuarioCadastro(Telefone telefoneUsuarioCadastro) {
		this.telefoneUsuarioCadastro = telefoneUsuarioCadastro;
	}
}
