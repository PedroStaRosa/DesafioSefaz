package controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;

import dao.UsuarioDAO;
import dao.UsuarioDAOImpl;
import entity.Telefone;
import entity.Usuario;
import util.JpaUtil;

@ManagedBean(name = "UsuarioBean")
@SessionScoped
public class UsuarioBean {

	private Usuario usuario;
	private Telefone telefone;
	private List<Usuario> listaUsuarios;
	private String emailUsuarioSelecionado;
	private long idTelefoneSelecionado; // PARA IMPLEMENTAR CASO USUARIO QUERIA EXCLUIR UM TELEFONE DE SUA LISTA( EM DESENV. )

	private UsuarioDAO usuarioDAO;
	private String mensagem;
	private Usuario usuarioExiste; // SERÁ PREENCHIDO NO INSERIR CASO cpf SEJA ENCONTRADO, VALIDA CPF EXISTENTE

	private static final String INSERIR = "inserirUsuario.xhtml";
	private static final String PESQUISAR = "carregarUsuarios.xhtml";
	private static final String LISTATELEFONE = "listaTelefone.xhtml";
	private static final String EDITAR = "editarUsuario.xhtml";

	public UsuarioBean() {
		this.usuario = new Usuario();
		this.usuario.setTelefones(new ArrayList<Telefone>());
		this.telefone = new Telefone();
		this.listaUsuarios = new ArrayList<Usuario>();
		this.usuarioDAO = new UsuarioDAOImpl();
		this.listaUsuarios = this.usuarioDAO.listarTodos();
		if (this.listaUsuarios.isEmpty()) {
			this.mensagem = "Lista vazia - Nenhum Usuario Cadastrado";
		}
	}

	public void pesquisarTodos() {
		this.emailUsuarioSelecionado = "";
		addMessage("Lista Carregada com Sucesso", "Sucesso");
		this.listaUsuarios = this.usuarioDAO.listarTodos();
	}

	public void pesquisarUsuario() {

		Usuario usuarioPesquisar = this.usuarioDAO.pesquisar(emailUsuarioSelecionado);
		List<Usuario> usuarioSelecionado = new ArrayList<Usuario>();
		usuarioSelecionado.add(usuarioPesquisar);
		this.listaUsuarios = usuarioSelecionado;
		if (usuarioPesquisar == null) {
			addMessageError("Nenhum usuario encontrado", "Não encontrado");
		} else {
			addMessage("Buscar realizada com sucesso", "Sucesso!!");
		}
	}
	
	public void salvarUsuario() throws IOException {

		if (this.usuarioDAO.inserir(this.usuario)) {
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_INFO, "", "Sucesso !!!"));
			abrirPesquisarUsuario();

		} else {
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR, "", "Erro ao inserir !!!"));
		}

	}

	public void removerUsuario() {

		this.usuarioDAO.remover(emailUsuarioSelecionado);
		this.listaUsuarios = this.usuarioDAO.listarTodos();
		limpar();
		addMessage("* Usuário deletado com sucesso", "Lista recarregada");
		

	}

	public void editarUsuario() throws IOException {

		Usuario usuarioEditar = this.usuarioDAO.pesquisar(emailUsuarioSelecionado);
		this.usuario = usuarioEditar;
		abrirEditarUsuario();

	}

	public void inserirUsuario() throws IOException {

		this.limparMensagem();
		abrirInserirPessoa();
	}

	public void inserirTelefones() {
		if (!this.validaExisteTelefone(this.telefone)) {

			Telefone novoTelefone = new Telefone();

			novoTelefone.setDdd(this.telefone.getDdd());
			novoTelefone.setNumero(this.telefone.getNumero());
			novoTelefone.setTipo(this.telefone.getTipo());
			novoTelefone.setUsuario(this.usuario);

			this.usuario.getTelefones().add(novoTelefone);

			this.telefone = new Telefone();
		} else {
			addMessageError("Telefone ja cadastrado em sua lista.", "Telefone já cadastrado");
		}
	}

	private boolean validaExisteTelefone(Telefone telefone) {

		boolean retorno = false;
		for (Telefone telefoneLista : this.usuario.getTelefones()) {
			if (telefoneLista.getDdd() == telefone.getDdd() && telefoneLista.getNumero().equals(telefone.getNumero())) {
				retorno = true;
			}
		}
		return retorno;

	}

	public void novoUsuario() throws IOException {
		// VALIDA EMAIL EXISTENTE PARA CADASTRO DE NOVO USUÁRIO.
		usuarioExiste = this.usuarioDAO.pesquisar(this.usuario.getEmail());
		if (usuarioExiste == null) {
			salvarUsuario();
		} else {
			addMessageError("Email já cadastrado.", "Insira um email diferente.");
		}

	}

	public void listaTelefoneCad() throws IOException {
		Usuario usuarioSelecionado = this.usuarioDAO.pesquisar(emailUsuarioSelecionado);
		this.usuario = usuarioSelecionado;
		abrirListaTelefone();
	}
	
	public void limparMensagem() {
		this.mensagem = "";
	}

	public void limpar() {
		this.listaUsuarios = new ArrayList<Usuario>();
		this.usuario = new Usuario();
		this.usuario.setTelefones(new ArrayList<Telefone>());
		this.telefone = new Telefone();
		this.mensagem = "";
		this.emailUsuarioSelecionado = "";
	}

	// ADICIONA MENSAGENS
	public void addMessage(String titulo, String detalhes) {

		FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, titulo, detalhes);
		FacesContext.getCurrentInstance().addMessage(null, message);

	}

	public void addMessageError(String titulo, String detalhes) {

		FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, titulo, detalhes);
		FacesContext.getCurrentInstance().addMessage(null, message);

	}

	// DIRECIONAR PAGINAS
	public void abrirPesquisarUsuario() throws IOException {
		this.limpar();
		FacesContext.getCurrentInstance().getExternalContext().redirect(PESQUISAR);
	}

	public void abrirInserirPessoa() throws IOException {
		FacesContext.getCurrentInstance().getExternalContext().redirect(INSERIR);
	}

	public void abrirEditarUsuario() throws IOException {
		FacesContext.getCurrentInstance().getExternalContext().redirect(EDITAR);
	}

	public void abrirListaTelefone() throws IOException {
		FacesContext.getCurrentInstance().getExternalContext().redirect(LISTATELEFONE);
	}
	
	// GETTERS AND SETTERS
	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public Telefone getTelefone() {
		return telefone;
	}

	public void setTelefone(Telefone telefone) {
		this.telefone = telefone;
	}

	public List<Usuario> getListaUsuarios() {
		return listaUsuarios;
	}

	public void setListaUsuarios(List<Usuario> listaUsuarios) {
		this.listaUsuarios = listaUsuarios;
	}

	public String getEmailUsuarioSelecionado() {
		return emailUsuarioSelecionado;
	}

	public void setEmailUsuarioSelecionado(String emailUsuarioSelecionado) {
		this.emailUsuarioSelecionado = emailUsuarioSelecionado;
	}

	public long getIdTelefoneSelecionado() {
		return idTelefoneSelecionado;
	}

	public void setIdTelefoneSelecionado(long idTelefoneSelecionado) {
		this.idTelefoneSelecionado = idTelefoneSelecionado;
	}

	public String getMensagem() {
		return mensagem;
	}

	public void setMensagem(String mensagem) {
		this.mensagem = mensagem;
	}

}